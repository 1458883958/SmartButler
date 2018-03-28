package com.example.wudelin.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wudelin.smartbutler.MainActivity;
import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.entity.User;
import com.example.wudelin.smartbutler.utils.ShareUtils;
import com.example.wudelin.smartbutler.utils.StaticClass;
import com.example.wudelin.smartbutler.utils.ToastUtil;
import com.example.wudelin.smartbutler.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

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
    //忘记密码
    private TextView forgotPsd;
    //账号密码
    private EditText loginUsername;
    private EditText loginPassword;
    //记住密码
    private CheckBox cbPsd;
    //Dialog
    private CustomDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        btnLogin = findViewById(R.id.btn_login);
        btnReg = findViewById(R.id.btn_register);
        forgotPsd = findViewById(R.id.forgot_psd);
        loginUsername = findViewById(R.id.username_login);
        loginPassword = findViewById(R.id.password_login);
        cbPsd = findViewById(R.id.cb_psd);
        forgotPsd.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnReg.setOnClickListener(this);
        dialog = new CustomDialog(this, 100, 100,
                R.layout.dialog_loading, R.style.Theme_girl,
                Gravity.CENTER,R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);
        //取出状态 设置选中状态（记住密码）
        boolean isCheck = ShareUtils.getBoolean(this,
                StaticClass.CHECK_REM_PASSWORD,false);
        cbPsd.setChecked(isCheck);
        //判断状态并做出行动
        if(cbPsd.isChecked()){
            loginUsername.setText(ShareUtils.getString(this,
                    StaticClass.USERNAME,""));
            loginPassword.setText(ShareUtils.getString(this,
                    StaticClass.PASSWORD,""));
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_login:
                dialog.show();
                //取值 判空
                final String username = loginUsername.getText().toString().trim();
                final String password = loginPassword.getText().toString().trim();
                if(!TextUtils.isEmpty(username)&!TextUtils.isEmpty(password)){
                    //登录
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.login(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            dialog.cancel();
                            //判断邮箱是否验证
                            if (e == null){
                                //激活成功
                                if(user.getEmailVerified()){
                                    startActivity(new Intent(LoginActivity.this,
                                            MainActivity.class));
                                    //保存选中状态
                                    ShareUtils.putBoolean(LoginActivity.this,
                                            StaticClass.CHECK_REM_PASSWORD,cbPsd.isChecked());
                                    //销毁activity前判断状态，如果选中则保存账号密码
                                    if(cbPsd.isChecked()){
                                        ShareUtils.putString(LoginActivity.this,
                                                StaticClass.USERNAME,username);
                                        ShareUtils.putString(LoginActivity.this,
                                                StaticClass.PASSWORD,password);
                                    }else{
                                        ShareUtils.delValue(LoginActivity.this,
                                                StaticClass.USERNAME);
                                        ShareUtils.delValue(LoginActivity.this,
                                                StaticClass.PASSWORD);
                                    }
                                    finish();
                                }else{
                                    ToastUtil.toast(LoginActivity.this,
                                            R.string.email_activation);
                                }
                            }else {
                                ToastUtil.toast(LoginActivity.this,
                                        R.string.login_failed);
                            }
                        }
                    });

                }else{
                    ToastUtil.toast(this,R.string.et_nothing);
                }
                break;
            case R.id.btn_register:
                startActivity(new Intent(this,RegisteredActivity.class));
                break;
            case R.id.forgot_psd:
                startActivity(new Intent(this,UpdatedActivity.class));
                break;
            default:break;
        }
    }
}
