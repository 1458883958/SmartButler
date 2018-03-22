package com.example.wudelin.smartbutler.utils;

import android.util.Log;

import javax.security.auth.login.LoginException;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.utils
 * 创建者：   wdl
 * 创建时间： 2018/3/22 14:13
 * 描述：     日志控制
 */

public class Logger {
    private static final Boolean FLAG = true;
    public static void e(String tag,String message){
        if(FLAG){
            Log.e(tag, message);
        }
    }
    public static void w(String tag,String message){
        if(FLAG){
            Log.w(tag, message);
        }
    }
    public static void i(String tag,String message){
        if(FLAG){
            Log.i(tag, message);
        }
    }
    public static void d(String tag,String message){
        if(FLAG){
            Log.d(tag, message);
        }
    }
    public static void v(String tag,String message){
        if(FLAG){
            Log.v(tag, message);
        }
    }
}
