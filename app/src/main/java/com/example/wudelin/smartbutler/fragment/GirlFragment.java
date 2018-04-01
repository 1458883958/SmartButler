package com.example.wudelin.smartbutler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.adapter.GirlAdapter;
import com.example.wudelin.smartbutler.entity.GirlData;
import com.example.wudelin.smartbutler.utils.Logger;
import com.example.wudelin.smartbutler.utils.PicassoUtil;
import com.example.wudelin.smartbutler.utils.StaticClass;
import com.example.wudelin.smartbutler.view.CustomDialog;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.fragment
 * 创建者：   wdl
 * 创建时间： 2018/3/22 13:12
 * 描述：    美女社区
 */

public class GirlFragment extends Fragment {
    private List<GirlData> mList = new ArrayList<>();
    private GridView gridView;
    private GirlAdapter adapter;
    //提示框
    private CustomDialog dialog;
    //预览图片
    private PhotoView viewGirl;
    private List<String> urlList = new ArrayList<>();
    private HashSet<String> setUrl = new HashSet<>();
    private HashSet<GirlData> set = new HashSet<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl,null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        gridView = view.findViewById(R.id.grid_view);
        //初始化预览弹窗
        dialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT
                ,LinearLayout.LayoutParams.MATCH_PARENT,
                R.layout.gril_photo,R.style.Theme_girl, Gravity.CENTER,R.style.pop_anim_style);
        viewGirl = dialog.findViewById(R.id.girl_photo);
        swipeRefreshLayout = view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //加载图片
                PicassoUtil.loadDefault(getActivity(),
                        urlList.get(position),viewGirl);
                dialog.show();
            }
        });
        if(mList.isEmpty())
            get();
    }

    private void get() {
        RxVolley.get(StaticClass.GIRL_API, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                Logger.d("girl",t);
                pasringJson(t);
            }
        });
    }

    private void pasringJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                GirlData data = new GirlData();
                String url = json.getString("url");
                data.setImageUrl(url);
                setUrl.add(url);
                set.add(data);
            }
            urlList.addAll(setUrl);
            mList.addAll(set);
            adapter = new GirlAdapter(getActivity(),mList);
            gridView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
