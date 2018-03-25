package com.example.wudelin.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.entity.User;
import com.example.wudelin.smartbutler.utils.Logger;
import com.example.wudelin.smartbutler.utils.ToastUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.ui
 * 创建者：   wdl
 * 创建时间： 2018/3/23 19:04
 * 描述：    注册
 */

public class RegisteredActivity extends BaseActivity implements View.OnClickListener{

    private EditText usernameReg;
    private EditText passwordReg;
    private EditText passwdReg;
    private EditText ageReg;
    private EditText descReg;
    private EditText emailReg;
    private RadioGroup radioGroup;
    private Button regBtn;
    //记录性别,默认为男
    private boolean isGen = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        initView();
    }

    private void initView() {
        usernameReg = findViewById(R.id.username_reg);
        passwordReg = findViewById(R.id.password_reg);
        passwdReg = findViewById(R.id.et_password_reg);
        ageReg = findViewById(R.id.age_reg);
        descReg = findViewById(R.id.desc_reg);
        emailReg = findViewById(R.id.email_reg);
        radioGroup = findViewById(R.id.mRadio);
        regBtn = findViewById(R.id.btnRegistered);
        regBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnRegistered:
                //获取控件的值
                String username = usernameReg.getText().toString().trim();
                String password = passwordReg.getText().toString().trim();
                String passwd = passwdReg.getText().toString().trim();
                String age = ageReg.getText().toString().trim();
                String desc = descReg.getText().toString().trim();
                String email = emailReg.getText().toString().trim();
                //进行判断是否为空
                if(!TextUtils.isEmpty(username)
                        &!TextUtils.isEmpty(password)
                        &!TextUtils.isEmpty(passwd)
                        &!TextUtils.isEmpty(age)
                        &!TextUtils.isEmpty(email)){
                    //判断两次输入的密码是否一致
                    if(password.equals(passwd)){
                        //监听RadioGroup,确定sex
                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if(checkedId == R.id.rb_boy){
                                    isGen = true;
                                }else if(checkedId == R.id.rb_girl){
                                    isGen = false;
                                }
                            }
                        });
                        if(TextUtils.isEmpty(desc)){
                           desc = getString(R.string.string_desc);
                        }
                        User user = new User();
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setAge(Integer.parseInt(age));
                        user.setEmail(email);
                        user.setSex(isGen);
                        user.setDesc(desc);
                        user.signUp(new SaveListener<User>() {
                            @Override
                            public void done(User user, BmobException e) {
                                if(e==null){
                                    ToastUtil.toast(RegisteredActivity.this,R.string.reg_success);
                                    finish();
                                }else{
                                    ToastUtil.toastBy(RegisteredActivity.this,getString(R.string.reg_defeat)+e.toString());
                                }
                            }
                        });


                    }else {
                        ToastUtil.toast(this,R.string.inconsistency_of_ciphers);
                    }

                }else{
                    ToastUtil.toast(this,R.string.et_nothing);
                }
                break;
            default:break;
        }
    }
}
