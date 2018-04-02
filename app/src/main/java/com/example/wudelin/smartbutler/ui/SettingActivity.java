package com.example.wudelin.smartbutler.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.service.SmsService;
import com.example.wudelin.smartbutler.utils.Logger;
import com.example.wudelin.smartbutler.utils.ShareUtils;
import com.example.wudelin.smartbutler.utils.StaticClass;
import com.example.wudelin.smartbutler.utils.ToastUtil;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.ui
 * 创建者：   wdl
 * 创建时间： 2018/3/22 14:08
 * 描述：    设置
 */

public class SettingActivity extends BaseActivity  {
    private SwitchCompat switchCompat;
    private SwitchCompat switch_Sms;
    public static final String TTS_CHECK = "tts_check";
    public static final String SMS_CHECK = "sms_check";

    //官方建议需要申请该权限时引导用户跳转到Setting中自己去开启权限开关
    //android.permission.SYSTEM_ALERT_WINDOW
    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;

    //检测版本
    private TextView tvVersion;
    private LinearLayout linearLayout;

    private String versionName;
    private int versionCode;
    private String apkUrl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_layout);
        initView();
    }

    private void initView() {
        switchCompat = findViewById(R.id.switch_button);
        switch_Sms = findViewById(R.id.switch_sms);
        linearLayout = findViewById(R.id.ll_version);

        //检测更新
        /*
        * 1.请求服务器配置文件,拿到code
        * 2.比较
        * 3.dialog提示
        * 4.跳转更新界面，传入url
        * */
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxVolley.get(StaticClass.UPDATE_URL, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        Logger.d("update",t);
                        parsingJson(t);
                    }
                });
            }
        });
        tvVersion = findViewById(R.id.tv_version);

        //获取保存的变量值  赋值
        final boolean ttsIsCheck = ShareUtils.
                getBoolean(this,TTS_CHECK,false);
        final boolean smsIsCheck = ShareUtils.
                getBoolean(this,SMS_CHECK,false);
        switch_Sms.setChecked(smsIsCheck);
        switchCompat.setChecked(ttsIsCheck);
        requestDrawOverLays();
        switch_Sms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ShareUtils.putBoolean(SettingActivity.this,
                        SMS_CHECK,isChecked);
                Logger.d("sms_checked",""+isChecked);
                //permission();
                requestDrawOverLays();

            }
        });
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ShareUtils.putBoolean(SettingActivity.this,
                        TTS_CHECK,isChecked);
            }
        });

        getVersionName();
        if(versionName!=null) {
            tvVersion.setText(getString(R.string.detection_version)+":"+ versionName);
        }else{
            tvVersion.setText(getString(R.string.detection_version));
        }

    }

    //解析
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            int code = jsonObject.getInt("versionCode");
            apkUrl = jsonObject.getString("url");
            Logger.d("json",""+code);
            if(code>versionCode){
                showUpdateDialog(jsonObject.getString("content"));
            }else{
                ToastUtil.toast(this,R.string.latest_version);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //升级提示
    private void showUpdateDialog(String content) {
        new AlertDialog.Builder(this)
                .setTitle("有新版本啦！！！")
                .setMessage(content)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SettingActivity.this,
                                DownloadActivity.class);
                        intent.putExtra("apk_url",apkUrl);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //默认执行dismiss();
                    }
                })
                .show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestDrawOverLays() {
        if(!Settings.canDrawOverlays(SettingActivity.this)){
            //跳转到设置页面
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + SettingActivity.this.getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        }else{
            permission();
        }
    }

    private void permission() {
        if(ContextCompat.checkSelfPermission(SettingActivity.this,
                Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SettingActivity.this,
                    new String[]{Manifest.permission.SEND_SMS},0);
        }else{
            judge(switch_Sms.isChecked());
        }
    }

    private void judge(boolean isChecked) {
        if (isChecked) {
            startService(new Intent(SettingActivity.this,
                    SmsService.class));
        } else {
            stopService(new Intent(SettingActivity.this,
                    SmsService.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 0:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED&&
                        grantResults.length>0){
                    judge(switch_Sms.isChecked());
                }else{
                    ToastUtil.toastBy(this,"Permission Denied");
                }
                break;
            default:break;
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                // SYSTEM_ALERT_WINDOW permission not granted...
                ToastUtil.toastBy(this,
                        "Permission Denieddd by user.Please Check it in Settings");
            } else {
                // Already hold the SYSTEM_ALERT_WINDOW permission, do addview or something.
                ToastUtil.toastBy(this,"Permission Allowed");
                permission();
            }
        }
    }

    //获取系统版本
    private void getVersionName(){
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(),0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
