package com.example.wudelin.smartbutler.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.utils
 * 创建者：   wdl
 * 创建时间： 2018/3/22 18:07
 * 描述：     SharedPreferences封装
 *           三种数据类型的put/get，删除单个/全部
 */

public class ShareUtils {

    private static final String NAME = "config";
    //存
    public static void putString(Context mContext,String key,String value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value).apply();
    }
    public static void putInt(Context mContext,String key,int value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key,value).apply();
    }
    public static void putBoolean(Context mContext, String key, boolean value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key,value).apply();
    }

    public static String getString(Context mContext,String key,String defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }
    public static int getInt(Context mContext,String key,int defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }
    public static Boolean getBoolean(Context mContext,String key,Boolean defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static void delValue(Context mContext,String key){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key).apply();
    }

    public static void delAll(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().apply();
    }
}
