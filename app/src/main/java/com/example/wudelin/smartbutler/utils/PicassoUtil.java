package com.example.wudelin.smartbutler.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.utils
 * 创建者：   wdl
 * 创建时间： 2018/3/28 13:20
 * 描述：    Picasso基本封装
 */

public class PicassoUtil {

    //默认方式加载
    public static void loadDefault(Context mContext, String url, ImageView imageView){
        Picasso.with(mContext).load(url).into(imageView);
    }

    //指定宽高 指定加载中  加载错误的图片
    public  static void loadDIY(Context mContext, String url,
                                int holder, int error,
                                int width,int height,
                                ImageView imageView){
        Picasso.with(mContext)
                .load(url)
                .config(Bitmap.Config.RGB_565)
                .placeholder(holder)
                .error(error)
                .resize(width,height)
                .into(imageView);
    }

    //指定宽高
    public  static void loadResize(Context mContext, String url,int width,int height,
                                   ImageView imageView){
        Picasso.with(mContext)
                .load(url)
                .resize(width,height)
                .into(imageView);

    }
    //指定默认图片
    public  static void loadHolder(Context mContext, String url,int holder,int error,
                                   ImageView imageView){
        Picasso.with(mContext)
                .load(url)
                .config(Bitmap.Config.RGB_565)
                .placeholder(holder)
                .error(error)
                .into(imageView);

    }
    //裁剪
    //指定默认图片
    public static void loadCrop(Context mContext, String url,
                                   ImageView imageView){
        Picasso.with(mContext)
                .load(url)
                .transform(new CropSquareTransformation())
                .into(imageView);

    }

    //按比例裁剪
    public static class CropSquareTransformation implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override public String key() { return "wdl"; }
    }
}
