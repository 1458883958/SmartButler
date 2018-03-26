package com.example.wudelin.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.utils.StaticClass;
import com.example.wudelin.smartbutler.utils.ToastUtil;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.ui
 * 创建者：   wdl
 * 创建时间： 2018/3/26 19:58
 * 描述：    归属地查询
 */

public class AttributiveActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0, btn_del, btn_query;
    private EditText etNumber;
    private ImageView imageView;
    private TextView tvResult;

    //标记
    private boolean flag = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attributive);
        initView();
    }

    //初始化
    private void initView() {
        btn_0 = findViewById(R.id.btn_0);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_del = findViewById(R.id.btn_del);
        btn_query = findViewById(R.id.btn_phone_query);
        imageView = findViewById(R.id.iv_company);
        tvResult = findViewById(R.id.tv_result);
        etNumber = findViewById(R.id.et_number);
        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        //长按事件
        btn_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etNumber.setText("");
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        String phone = etNumber.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                if(!flag) {
                    flag = true;
                    etNumber.setText("");
                    phone = "";
                }
                etNumber.setText(phone + ((Button) v).getText());
                //设置光标
                etNumber.setSelection(phone.length() + 1);
                break;
            case R.id.btn_del:
                if (!TextUtils.isEmpty(phone) && phone.length() > 0) {
                    etNumber.setText(phone.substring(0, phone.length() - 1));
                    //设置光标
                    etNumber.setSelection(phone.length() - 1);
                }
                break;
            case R.id.btn_phone_query:
                if(!TextUtils.isEmpty(phone)){
                    getAttributive(phone);
                }else{
                    ToastUtil.toast(this,R.string.phone_nothing);
                }
                break;
        }
    }

    //获取归属地
    private void getAttributive(String phone) {
        //url
        String url = "http://apis.juhe.cn/mobile/get?phone="+phone
                +"&key="+ StaticClass.Attributive_APPKEY;

        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                ToastUtil.toastBy(AttributiveActivity.this,strMsg);
            }
        });

    }

    //解析数据
    private void parsingJson(String t) {

        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String province = jsonResult.getString("province");
            String city = jsonResult.getString("city");
            String areacode = jsonResult.getString("areacode");
            String zip = jsonResult.getString("zip");
            String company = jsonResult.getString("company");

            tvResult.setText("归属地:"+province+city+"\n"
                    +"区号:"+areacode+"\n"
                    +"邮编:"+zip+"\n"
                    +"运营商:"+company+"\n"
                    );

            switch (company){
                case "移动":
                    imageView.setBackgroundResource(R.drawable.china_mobile);
                    break;
                case "联通":
                    imageView.setBackgroundResource(R.drawable.china_unicom);
                    break;
                case "电信":
                    imageView.setBackgroundResource(R.drawable.china_telecom);
                    break;
                default:break;
            }
            flag = false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
