package com.example.wudelin.smartbutler.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.utils.Logger;
import com.example.wudelin.smartbutler.utils.StaticClass;
import com.example.wudelin.smartbutler.view.DispatchLinearLayout;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.service
 * 创建者：   wdl
 * 创建时间： 2018/3/30 16:53
 * 描述：    短信广播服务
 */

public class SmsService extends Service{

    //发件人
    private String smsPhone;
    //内容
    private String smsContent;
    private SmsReceiver smsReceiver;
    //窗口管理
    private WindowManager wm;
    //布局参数
    private WindowManager.LayoutParams layoutParams;
    //布局
    private DispatchLinearLayout mView;
    private TextView smsTvPhone;
    private TextView smsTvContent;
    private Button btnSend;

    //home键
    private HomeKey homeKey;

    public static final String SYSTEM_DIALOGS_RESON_KEY = "reason";
    public static final String SYSTEM_DIALOGS_HOME_KEY = "homekey";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    //初始化
    private void init() {
        Logger.d("service","init service");
        //动态注册
        smsReceiver = new SmsReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(StaticClass.SMS_ACTION);
        //设置优先值
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(smsReceiver,intentFilter);

        homeKey = new HomeKey();
        IntentFilter intent = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(homeKey,intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("service","destroy service");
        //注销
        unregisterReceiver(smsReceiver);
        unregisterReceiver(homeKey);
    }

    public class SmsReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(StaticClass.SMS_ACTION)){
                Logger.d("BroadcastReceiver","来短信了");
                //获取短信内容 返回的是一个Objects数组
                Object []objects = (Object[]) intent.getExtras().get("pdus");
                //遍历数组得到相关数据
                for (Object obj:objects){
                    //把数组转换成短信对象
                    SmsMessage message = SmsMessage.createFromPdu((byte[]) obj);
                    //发件人
                    smsPhone = message.getOriginatingAddress();
                    //短信内容
                    smsContent = message.getMessageBody();
                    Logger.d("service","发件人:"+smsPhone+"\n"+"内容:"+smsContent);

                    //弹窗提醒
                    showWindow();
                }
            }
        }
    }

    private void showWindow() {
        //后去系统服务
        wm = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        //获取布局参数
        layoutParams = new WindowManager.LayoutParams();
        //定义宽高
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        //定义标记
        layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //定义格式
        layoutParams.format = PixelFormat.TRANSLUCENT;
        //定义类型
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //加载布局
        mView = (DispatchLinearLayout) View.inflate(getApplicationContext(), R.layout.window_sms,null);

        smsTvPhone = mView.findViewById(R.id.sms_phone);
        smsTvContent = mView.findViewById(R.id.sms_content);
        btnSend = mView.findViewById(R.id.btn_send_sms);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
                //窗口消失
                if(mView.getParent()!=null){
                    wm.removeView(mView);
                }
            }
        });
        smsTvPhone.setText("发件人: "+smsPhone);
        smsTvContent.setText(smsContent);
        //添加view到窗口
        wm.addView(mView,layoutParams);
        mView.setDispatchKeyEventListener(mDispatchKeyEventListener);
    }

    private DispatchLinearLayout.DispatchKeyEventListener mDispatchKeyEventListener
             = new DispatchLinearLayout.DispatchKeyEventListener() {
        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            //判断是否按返回键
            if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
                //判断图层是否还在
                if(mView.getParent()!=null){
                    wm.removeView(mView);
                }
                return true;
            }
            return false;
        }
    };
    //回复短信
    private void sendSms() {
        Uri uri = Uri.parse("smsto:"+smsPhone);
        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
        //设置启动模式
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body","");
        startActivity(intent);

    }



    //监听home键的广播
    class HomeKey extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)){
                String reason = intent.
                        getStringExtra(SYSTEM_DIALOGS_RESON_KEY);
                if(SYSTEM_DIALOGS_HOME_KEY.equals(reason)){
                    if(mView.getParent()!=null){
                        wm.removeView(mView);
                    }
                }
            }
        }
    }
}
