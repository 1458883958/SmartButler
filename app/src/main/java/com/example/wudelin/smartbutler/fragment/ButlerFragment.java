package com.example.wudelin.smartbutler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.adapter.ChatAdapter;
import com.example.wudelin.smartbutler.entity.ChatData;
import com.example.wudelin.smartbutler.utils.StaticClass;
import com.example.wudelin.smartbutler.utils.ToastUtil;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.fragment
 * 创建者：   wdl
 * 创建时间： 2018/3/22 13:04
 * 描述：    管家服务
 */

public class ButlerFragment extends Fragment implements View.OnClickListener {

    private Button btnSend;
    private EditText editText;
    private ListView listView;
    private List<ChatData> mList = new ArrayList<>();
    private ChatAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler,null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        btnSend = view.findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);
        editText = view.findViewById(R.id.ed_content);
        listView = view.findViewById(R.id.chat_listView);

        adapter = new ChatAdapter(getActivity(),mList);
        listView.setAdapter(adapter);
        addLeft("你好,我是小杰！！");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                //取值判断长度&&不能超过30
                String text = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(text)){
                    if(text.length()>30){
                        ToastUtil.toast(getActivity(),R.string.length_exceeds_limit);
                    }else{
                        //清空值
                        editText.setText("");
                        //增加item
                        addRight(text);
                        //请求数据Json
                        String url = "http://op.juhe.cn/robot/index?info="+text
                                +"&key="+ StaticClass.Q_A_robot_APPKEY;
                        RxVolley.get(url, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                //解析Json
                                parsingJson(t);
                            }
                        });
                    }
                }else{
                    ToastUtil.toast(getActivity(),R.string.et_nothing);
                }
                break;
            default:break;
        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject object = jsonObject.getJSONObject("result");
            String text = object.getString("text");
            addLeft(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addLeft(String text) {
        ChatData data = new ChatData();
        data.setType(ChatAdapter.VALUE_LEFT_TYPE);
        data.setText(text);
        mList.add(data);

        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        listView.setSelection(listView.getBottom());
    }
    private void addRight(String text) {
        ChatData data = new ChatData();
        data.setType(ChatAdapter.VALUE_RIGHT_TYPE);
        data.setText(text);
        mList.add(data);

        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        listView.setSelection(listView.getBottom());
    }
}
