package com.example.wudelin.smartbutler.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.entity.User;
import com.example.wudelin.smartbutler.ui.AttributiveActivity;
import com.example.wudelin.smartbutler.ui.LoginActivity;
import com.example.wudelin.smartbutler.ui.LogisticsActivity;
import com.example.wudelin.smartbutler.utils.ToastUtil;
import com.example.wudelin.smartbutler.view.CustomDialog;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

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

    //CircleImage
    private CircleImageView circleImageView;

    private CustomDialog dialog;
    private Button btnCamera;
    private Button btnPhoto;
    private Button btnCancel;

    private TextView logistics;
    private TextView attributive;

    private File tempFile = null;
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

        //物流查询
        logistics = view.findViewById(R.id.btn_logistics);
        logistics.setOnClickListener(this);
        //归属地查询
        attributive = view.findViewById(R.id.btn_attributive);
        attributive.setOnClickListener(this);

        circleImageView = view.findViewById(R.id.profile_image);
        circleImageView.setOnClickListener(this);

        //初始化dialog
        dialog = new CustomDialog(getActivity(), 0, 0,
                R.layout.dialog_photo, R.style.pop_anim_style, Gravity.BOTTOM, 0);
        dialog.setCancelable(false);
        btnCamera = dialog.findViewById(R.id.btn_camera);
        btnPhoto = dialog.findViewById(R.id.btn_photo);
        btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnCamera.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

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
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            //跳转到相机
            case R.id.btn_camera:
                toCamera();
                break;
            //跳转到图库
            case R.id.btn_photo:
                toPhoto();
                break;
            //物流查询
            case R.id.btn_logistics:
                startActivity(new Intent(getActivity(), LogisticsActivity.class));
                break;
            //归属地查询
            case R.id.btn_attributive:
                startActivity(new Intent(getActivity(), AttributiveActivity.class));
                break;
            default:
                break;
        }
    }
    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    //相册
    private void toPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_REQUEST_CODE);
        dialog.dismiss();
    }
    //相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用,可用的话就进行储存
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=0){
            switch (requestCode) {
                //相册数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                //相机数据
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(),
                            PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:break;
                default:break;
            }
        }
    }
    private void startPhotoZoom(Uri uri){
        if(uri == null){
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop","true");
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",320);
        intent.putExtra("outputY",320);
        startActivityForResult(intent,RESULT_REQUEST_CODE);
    }
}
