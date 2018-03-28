package com.example.wudelin.smartbutler.application;

import android.app.Application;
import android.content.Context;

import com.example.wudelin.smartbutler.utils.StaticClass;
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
    }
}
