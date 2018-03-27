package com.example.wudelin.smartbutler.entity;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.entity
 * 创建者：   wdl
 * 创建时间： 2018/3/27 10:43
 * 描述：    聊天实体类
 */

public class ChatData {
    //判断左边还是右边的标记
    private int type;
    //内容
    private String text;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
