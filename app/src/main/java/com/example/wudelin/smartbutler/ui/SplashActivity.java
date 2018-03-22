package com.example.wudelin.smartbutler.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.wudelin.smartbutler.MainActivity;
import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.utils.ShareUtils;
import com.example.wudelin.smartbutler.utils.StaticClass;
import com.example.wudelin.smartbutler.utils.UtilTools;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.ui
 * 创建者：   wdl
 * 创建时间： 2018/3/22 18:35
 * 描述：    闪屏页
 */

public class SplashActivity extends AppCompatActivity{

    /*
    * 1.延时2000
    * 2.判断程序是否第一次运行
    * 3.自定义字体
    * 4.全屏主题
    * */
    //闪屏页文字
    private TextView textView;
    @SuppressLint("HandlerLeak")
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否是第一次运行
                    if(isFisrtRun()){
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    }else{
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    }
                    finish();
                    break;
                default:break;
            }
        }
    };
    //是否为第一次运行程序的逻辑判断
    private boolean isFisrtRun() {
        Boolean isFirst = ShareUtils.getBoolean(this,StaticClass.IS_RUNNING_FIRST,true);
        if(isFirst){
            //标记已经已经启动过app
            ShareUtils.putBoolean(this,StaticClass.IS_RUNNING_FIRST,false);
            return true;
        }else{
            return false;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        initView();
    }

    private void initView() {
        //延时2000ms
        myHandler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);
        textView = findViewById(R.id.splash_text);

        //设置字体
        UtilTools.setFont(this,textView);
    }

    //禁止返回键
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
