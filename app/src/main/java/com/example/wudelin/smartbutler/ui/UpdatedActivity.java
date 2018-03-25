package com.example.wudelin.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.entity.User;
import com.example.wudelin.smartbutler.utils.Logger;
import com.example.wudelin.smartbutler.utils.ToastUtil;

import org.w3c.dom.Text;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.ui
 * 创建者：   wdl
 * 创建时间： 2018/3/24 16:36
 * 描述：    忘记密码
 */

public class UpdatedActivity extends BaseActivity implements View.OnClickListener {

    //控件
    private EditText oldPsw;
    private EditText newPsw;
    private EditText agaginPsw;
    private EditText etEmail;
    private Button updatedPsw;
    private Button forgotPsw;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updated);
        //初始化
        initView();
    }

    private void initView() {
        oldPsw = findViewById(R.id.et_old_psw);
        newPsw = findViewById(R.id.et_new_psw);
        agaginPsw = findViewById(R.id.et_new_again_psw);
        etEmail = findViewById(R.id.up_email);
        updatedPsw = findViewById(R.id.btn_updated_psw);
        forgotPsw = findViewById(R.id.btn_forgot_psw);
        updatedPsw.setOnClickListener(this);
        forgotPsw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_updated_psw:
                //取值，判空
                String oldPsd = oldPsw.getText().toString().trim();
                String newPsd = newPsw.getText().toString().trim();
                String againPsd = agaginPsw.getText().toString().trim();
                if(!TextUtils.isEmpty(oldPsd)&!TextUtils.isEmpty(newPsd)
                        &!TextUtils.isEmpty(againPsd)){
                    //判断两次密码是否相同
                    if(newPsd.equals(againPsd)){
                        //修改密码
                        User.updateCurrentUserPassword(oldPsd, newPsd, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null){
                                    ToastUtil.toast(UpdatedActivity.this,
                                            R.string.successfully_modified);
                                    finish();
                                }else {
                                    Logger.d("updated",e.toString()+" "+e.getErrorCode());
                                    ToastUtil.toastBy(UpdatedActivity.this,
                                            getString(R.string.failed_modified)+e.toString());
                                }
                            }
                        });
                    }else{
                        ToastUtil.toast(this,R.string.inconsistency_of_ciphers);
                    }
                }else {
                    ToastUtil.toast(this,R.string.et_nothing);
                }
                break;
            case R.id.btn_forgot_psw:
                //取值,判断是否为空
                String email = etEmail.getText().toString().trim();
                if(!TextUtils.isEmpty(email)){
                    //不为空进行邮箱重置逻辑
                    User.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                ToastUtil.toast(UpdatedActivity.this,
                                        R.string.reset_psw);
                                finish();
                            }else{
                                ToastUtil.toastBy(UpdatedActivity.this,
                                        getString(R.string.failed_to_send)+e.toString());
                            }
                        }
                    });
                }else {
                    ToastUtil.toast(this,R.string.et_nothing);
                }
                break;
            default:break;
        }
    }
}
