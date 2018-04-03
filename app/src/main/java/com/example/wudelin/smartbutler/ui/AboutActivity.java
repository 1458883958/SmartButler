package com.example.wudelin.smartbutler.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.wudelin.smartbutler.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.ui
 * 创建者：   wdl
 * 创建时间： 2018/4/3 21:24
 * 描述：    关于
 */

public class AboutActivity extends BaseActivity{
    private ListView listView;
    private List<String> list = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setElevation(0);
        initView();
    }

    private void initView() {
        listView = findViewById(R.id.about_listView);

        list.add("应用名:"+getString(R.string.app_name));
        list.add("版本号:"+getVersion());
        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
    }
    private  String getVersion(){
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(),0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "unknow";
        }
    }
}
