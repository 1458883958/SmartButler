package com.example.wudelin.smartbutler.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.utils.ToastUtil;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.ui
 * 创建者：   wdl
 * 创建时间： 2018/4/3 11:00
 * 描述：    生成二维码
 */

public class QcodeActivity extends BaseActivity{

    private ImageView ivCode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        initView();
    }

    private void initView() {
        ivCode = findViewById(R.id.iv_code);
        String contentString ="http://172.25.41.51:8080/smwdl/app-debug.apk";
        int width = getResources().getDisplayMetrics().widthPixels;
        if (!contentString.equals("")) {
            //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小
            Bitmap qrCodeBitmap = EncodingUtils.createQRCode(contentString, width/2,
                    width/2,
                    BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            ivCode.setImageBitmap(qrCodeBitmap);
        } else {
            ToastUtil.toastBy(this,"Text can not be empty");
        }
    }
}
