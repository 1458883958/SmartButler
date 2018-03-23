package com.example.wudelin.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wudelin.smartbutler.R;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.ui
 * 创建者：   wdl
 * 创建时间： 2018/3/23 18:50
 * 描述：    登录
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //登录 注册按钮
    private Button btnLogin,btnReg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        btnLogin = findViewById(R.id.btn_login);
        btnReg = findViewById(R.id.btn_register);
        btnLogin.setOnClickListener(this);
        btnReg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_login:
                break;
            case R.id.btn_register:
                startActivity(new Intent(this,RegisteredActivity.class));
                break;
            default:break;
        }
    }
}
