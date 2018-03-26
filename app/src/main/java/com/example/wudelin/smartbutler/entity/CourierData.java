package com.example.wudelin.smartbutler.entity;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.entity
 * 创建者：   wdl
 * 创建时间： 2018/3/26 16:39
 * 描述：    快递信息实体类
 */

public class CourierData {
    //时间
    private String datetime;
    //状态
    private String remark;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
