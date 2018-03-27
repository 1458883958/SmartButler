package com.example.wudelin.smartbutler.entity;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.entity
 * 创建者：   wdl
 * 创建时间： 2018/3/27 19:26
 * 描述：    微信精选实体类
 */

public class SelectData {
    //标题
    private String title;
    //来源
    private String source;
    //图片url
    private String imageUrl;
    //新闻Url
    private String newsUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
