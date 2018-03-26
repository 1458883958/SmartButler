package com.example.wudelin.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.adapter.CourierAdapter;
import com.example.wudelin.smartbutler.entity.CourierData;
import com.example.wudelin.smartbutler.utils.Logger;
import com.example.wudelin.smartbutler.utils.StaticClass;
import com.example.wudelin.smartbutler.utils.ToastUtil;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.ui
 * 创建者：   wdl
 * 创建时间： 2018/3/26 15:49
 * 描述：    物流查询
 */

public class LogisticsActivity extends BaseActivity implements View.OnClickListener {

    private EditText edCompany;
    private EditText edNumber;
    private Button btnQuery;

    private ListView mListView;
    private List<CourierData> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logist);
        initView();
    }

    private void initView() {
        edCompany = findViewById(R.id.ed_company);
        edNumber = findViewById(R.id.ed_number);
        btnQuery = findViewById(R.id.btn_query);
        btnQuery.setOnClickListener(this);
        mListView = findViewById(R.id.logisticsView);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_query:
                //取值判空
                String company = edCompany.getText().toString().trim();
                String number = edNumber.getText().toString().trim();
                if(!TextUtils.isEmpty(company)&&!TextUtils.isEmpty(number)){
                    //URL
                    String url = "http://v.juhe.cn/exp/index?key="+
                            StaticClass.JUHE_DATA_APPKEY+"&com="+company+"&no="+number;
                    //根据Edit的值获取快递数据/json格式
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            super.onSuccess(t);
                            Logger.d("json",t);
                            //解析Json
                            parsingJson(t);
                        }
                    });

                }else{
                    ToastUtil.toast(this,R.string.et_nothing);
                }
                break;
            default:break;
        }
    }

    //解析数据
    private void parsingJson(String t)  {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject json = (JSONObject) jsonArray.get(i);
                CourierData courierData = new CourierData();
                courierData.setDatetime(json.getString("datetime"));
                courierData.setRemark(json.getString("remark"));
                mList.add(courierData);
            }
            //倒序
            Collections.reverse(mList);
            CourierAdapter adapter = new CourierAdapter(this,mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
