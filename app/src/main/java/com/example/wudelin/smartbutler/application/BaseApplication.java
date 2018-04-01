package com.example.wudelin.smartbutler.application;

import android.app.Application;
import android.content.Context;

import com.example.wudelin.smartbutler.utils.StaticClass;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.application
 * 创建者：   wdl
 * 创建时间： 2018/3/22 11:13
 * 描述：    Application
 */

public class BaseApplication extends Application{
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getBaseContext();
        //调试状态为true
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);

        //初始化Bmob
        Bmob.initialize(this,StaticClass.BMOB_APP_ID);
        // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
// 请勿在“=”与appid之间添加任何空字符或者转义符
         SpeechUtility.createUtility(context, SpeechConstant.APPID +"="+StaticClass.TTS_KEY);
    }
}
