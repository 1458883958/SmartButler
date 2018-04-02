package com.example.wudelin.smartbutler.utils;


/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.utils
 * 创建者：   wdl
 * 创建时间： 2018/3/22 11:25
 * 描述：    数据/常量
 */

public class StaticClass {
    //闪屏页延时
    public static final int HANDLER_SPLASH = 1001;

    //判断程序是否第一次运行
    public static final String IS_RUNNING_FIRST = "isFirst";

    //Bugly APP key
    public static final String BUGLY_APP_ID = "9e06444156";

    //Bmob APP key
    public static final String BMOB_APP_ID = "db5bf43d85208c15099e2b16ba04dc8f";

    //记住密码框状态
    public static final String CHECK_REM_PASSWORD = "keep_password";
    //用户名
    public static final String USERNAME = "username";
    //密码
    public static final String PASSWORD = "password";

    //聚合数据 快递APPKEY
    public static final String JUHE_DATA_APPKEY = "9cb6eb84a7a0e0358f4fac19b17082fb";

    //聚合数据 归属地APPKEY
    public static final String Attributive_APPKEY = " 95a98c76cdea8f0452109df024fae9e7";

    //聚合数据 问答机器人APPKEY
    public static final String Q_A_robot_APPKEY = " 7a48539921338ef90866922b21e25f6d";

    //聚合数据 微信精选APPKEY
    public static final String WECHAT_SELECT_APPKEY = " 78f723dccf85aea324a3cf0daac97f35";

    //美女社区图片接口
    public static final String GIRL_API = "http://gank.io/api/random/data/"+UtilTools.encode()+"/50";

    //科大讯飞app key
    public static final String TTS_KEY = "5abc698c";

    //短信action
    public static final String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    //update url
    public static final String UPDATE_URL = "http://172.25.41.51:8080/smwdl/config.json";
}
