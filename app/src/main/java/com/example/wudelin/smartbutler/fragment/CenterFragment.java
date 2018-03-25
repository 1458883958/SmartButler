package com.example.wudelin.smartbutler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.entity.User;
import com.example.wudelin.smartbutler.ui.LoginActivity;
import com.example.wudelin.smartbutler.utils.ToastUtil;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.fragment
 * 创建者：   wdl
 * 创建时间： 2018/3/22 13:13
 * 描述：    个人中心
 */

public class CenterFragment extends Fragment implements View.OnClickListener {

    //退出登录按钮
    private Button logOut;
    //编辑资料
    private TextView editCenter;
    //属性值
    private EditText cenName;
    private EditText cenAge;
    private EditText cenSex;
    private EditText cenDesc;
    //确认修改按钮
    private Button makeModify;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_center, null);
        findById(view);
        return view;
    }

    private void findById(View view) {
        logOut = view.findViewById(R.id.logout);
        logOut.setOnClickListener(this);
        makeModify = view.findViewById(R.id.make_modify);
        makeModify.setOnClickListener(this);
        editCenter = view.findViewById(R.id.edit_user);
        editCenter.setOnClickListener(this);
        cenName = view.findViewById(R.id.et_center_name);
        cenAge = view.findViewById(R.id.et_center_age);
        cenSex = view.findViewById(R.id.et_center_sex);
        cenDesc = view.findViewById(R.id.et_center_desc);

        //四个个人信息的输入框默认为不可点击
        setFoucs(false);
        //设置具体值
        setEnableValue();
    }

    private void setEnableValue() {
        User user = BmobUser.getCurrentUser(User.class);
        cenName.setText(user.getUsername());
        cenAge.setText(user.getAge() + "");
        cenSex.setText(user.isSex() ? "男" : "女");
        cenDesc.setText(user.getDesc());
    }

    private void setFoucs(boolean flag) {
        cenName.setEnabled(flag);
        cenAge.setEnabled(flag);
        cenSex.setEnabled(flag);
        cenDesc.setEnabled(flag);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //退出登录
            case R.id.logout:
                //清除缓存用户对象
                User.logOut();
                // 现在的currentUser是null了
                BmobUser currentUser = User.getCurrentUser();
                if (currentUser == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
                break;
            //编辑资料
            case R.id.edit_user:
                //设为可点击
                setFoucs(true);
                //确认修改按钮可见
                makeModify.setVisibility(View.VISIBLE);
                break;
            case R.id.make_modify:
                //取值 判空
                String name = cenName.getText().toString().trim();
                String sex = cenSex.getText().toString().trim();
                String age = cenAge.getText().toString().trim();
                String desc = cenDesc.getText().toString().trim();
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(sex)) {
                    //更新
                    User user = new User();
                    user.setUsername(name);
                    user.setAge(Integer.parseInt(age));
                    if (sex.equals("男")) {
                        user.setSex(true);
                    } else if (sex.equals("女")) {
                        user.setSex(false);
                    }
                    if (!TextUtils.isEmpty(desc)) {
                        user.setDesc(desc);
                    } else {
                        user.setDesc(getString(R.string.string_desc));
                    }
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //成功,失去焦点，按钮隐藏
                                setFoucs(false);
                                makeModify.setVisibility(View.GONE);
                                ToastUtil.toast(getActivity(), R.string.successfully_modified);
                            } else {
                                ToastUtil.toast(getActivity(), R.string.failed_modified);
                            }
                        }
                    });
                } else {
                    ToastUtil.toast(getActivity(), R.string.et_nothing);
                }
                break;
                default:break;
        }
    }
}
