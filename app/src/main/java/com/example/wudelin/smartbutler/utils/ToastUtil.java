package com.example.wudelin.smartbutler.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.utils
 * 创建者：   wdl
 * 创建时间： 2018/3/23 20:07
 * 描述：    Toast简单封装
 */

public class ToastUtil {

    public static void toast(Context mContext,int id){
        Toast.makeText(mContext,id,Toast.LENGTH_SHORT).show();
    }
    public static void toastBy(Context mContext,String value){
        Toast.makeText(mContext,value,Toast.LENGTH_SHORT).show();
    }
}
