package com.example.wudelin.smartbutler.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.entity
 * 创建者：   wdl
 * 创建时间： 2018/3/23 20:14
 * 描述：    用户实体类
 */

public class User extends BmobUser{

    private int age;
    private boolean sex;
    private String desc;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
