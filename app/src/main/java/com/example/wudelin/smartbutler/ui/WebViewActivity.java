package com.example.wudelin.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.utils.Logger;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.ui
 * 创建者：   wdl
 * 创建时间： 2018/3/27 20:07
 * 描述：    新闻详情页
 */

public class WebViewActivity extends BaseActivity{
    public static final String TITLE = "title";
    public static final String URL = "url";
    private WebView webView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String titles = bundle.getString(TITLE);
        final String urls = bundle.getString(URL);
        Logger.d("webView",titles+"  "+urls);
        getSupportActionBar().setTitle(titles);
        webView = findViewById(R.id.news_webView);
        progressBar = findViewById(R.id.new_progress);

       //进行加载网页
        //支持JS
        webView.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //接口回调
        webView.setWebChromeClient(new WebViewClient());
        //加载
        webView.loadUrl(urls);
        //本地显示
        webView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(urls);
                return true;
            }
        });


    }
    public class WebViewClient extends WebChromeClient{

        //进度变化的监听

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress == 100){
                progressBar.setVisibility(View.GONE);
            }
        }
    }
}
