package com.example.wudelin.smartbutler.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

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
}
