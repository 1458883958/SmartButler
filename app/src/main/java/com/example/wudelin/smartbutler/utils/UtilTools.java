package com.example.wudelin.smartbutler.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.application.BaseApplication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.utils
 * 创建者：   wdl
 * 创建时间： 2018/3/22 11:24
 * 描述：    工具统一类
 */

public class UtilTools {

    //设置字体
    public static void setFont(Context mContent, TextView textView){
        Typeface fontType = Typeface.createFromAsset(mContent.getAssets(),"fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }

    //转码
    public static String encode(){
        String welfare = null;
        try {
            welfare = URLEncoder.encode(BaseApplication.context.getString(R.string.welfare),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return welfare;
    }

}
