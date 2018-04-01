package com.example.wudelin.smartbutler.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.service.SmsService;
import com.example.wudelin.smartbutler.utils.Logger;
import com.example.wudelin.smartbutler.utils.ShareUtils;
import com.example.wudelin.smartbutler.utils.ToastUtil;


/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.ui
 * 创建者：   wdl
 * 创建时间： 2018/3/22 14:08
 * 描述：    设置
 */

public class SettingActivity extends BaseActivity  {
    private SwitchCompat switchCompat;
    private SwitchCompat switch_Sms;
    public static final String TTS_CHECK = "tts_check";
    public static final String SMS_CHECK = "sms_check";

    //官方建议需要申请该权限时引导用户跳转到Setting中自己去开启权限开关
    //android.permission.SYSTEM_ALERT_WINDOW
    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_layout);
        initView();
    }

    private void initView() {
        switchCompat = findViewById(R.id.switch_button);
        switch_Sms = findViewById(R.id.switch_sms);
        //获取保存的变量值  赋值
        final boolean ttsIsCheck = ShareUtils.
                getBoolean(this,TTS_CHECK,false);
        final boolean smsIsCheck = ShareUtils.
                getBoolean(this,SMS_CHECK,false);
        switch_Sms.setChecked(smsIsCheck);
        switchCompat.setChecked(ttsIsCheck);
        requestDrawOverLays();
        switch_Sms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ShareUtils.putBoolean(SettingActivity.this,
                        SMS_CHECK,isChecked);
                Logger.d("sms_checked",""+isChecked);
                //permission();
                requestDrawOverLays();

            }
        });
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ShareUtils.putBoolean(SettingActivity.this,
                        TTS_CHECK,isChecked);
            }
        });

    }
    @TargetApi(Build.VERSION_CODES.M)
    public void requestDrawOverLays() {
        if(!Settings.canDrawOverlays(SettingActivity.this)){
            //跳转到设置页面
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + SettingActivity.this.getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        }else{
            permission();
        }
    }

    private void permission() {
        if(ContextCompat.checkSelfPermission(SettingActivity.this,
                Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SettingActivity.this,
                    new String[]{Manifest.permission.SEND_SMS},0);
        }else{
            judge(switch_Sms.isChecked());
        }
    }

    private void judge(boolean isChecked) {
        if (isChecked) {
            startService(new Intent(SettingActivity.this,
                    SmsService.class));
        } else {
            stopService(new Intent(SettingActivity.this,
                    SmsService.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 0:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED&&
                        grantResults.length>0){
                    judge(switch_Sms.isChecked());
                }else{
                    ToastUtil.toastBy(this,"Permission Denied");
                }
                break;
            default:break;
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                // SYSTEM_ALERT_WINDOW permission not granted...
                ToastUtil.toastBy(this,
                        "Permission Denieddd by user.Please Check it in Settings");
            } else {
                // Already hold the SYSTEM_ALERT_WINDOW permission, do addview or something.
                ToastUtil.toastBy(this,"Permission Allowed");
                permission();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
