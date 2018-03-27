package com.example.wudelin.smartbutler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.adapter.SelectAdapter;
import com.example.wudelin.smartbutler.entity.SelectData;
import com.example.wudelin.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.fragment
 * 创建者：   wdl
 * 创建时间： 2018/3/22 13:11
 * 描述：    微信精选
 */

public class WeChatFragment extends Fragment{
    private List<SelectData>mList = new ArrayList<>();
    private ListView mListView;
    private SelectAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat,null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mListView = view.findViewById(R.id.select_listView);

        String url = "http://v.juhe.cn/weixin/query?key="+ StaticClass.WECHAT_SELECT_APPKEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
            }
        });

    }

    //解析Json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for(int i = 0;i<jsonArray.length();i++){
                SelectData selecData = new SelectData();
                JSONObject json = (JSONObject) jsonArray.get(i);
                selecData.setTitle(json.getString("title"));
                selecData.setSource(json.getString("source"));
                selecData.setImageUrl(json.getString("firstImg"));
                selecData.setNewsUrl(json.getString("url"));
                mList.add(selecData);
            }
            adapter = new SelectAdapter(getActivity(),mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
