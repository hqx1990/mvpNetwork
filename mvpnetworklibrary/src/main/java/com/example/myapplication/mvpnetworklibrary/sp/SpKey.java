package com.example.myapplication.mvpnetworklibrary.sp;

/**
 * Created by zhangjie on 2017/6/5.
 * 定义一个接口，用来存放存取SP时的key值
 * 外部使用时，直接 SpKey. 即可
 * ex：SpKey.LOGIN
 */

public interface SpKey {
    /**
     * 登录相关信息
     */
    String LOGIN = "LOGIN";
    String LOGIN_CLIENTSERCERT = "clientSecret";//请求token用
    String LOGIN_ACCESSTOKEN = "accessToken";//获取到的token值
    String LOGIN_THE_TEST_DATA1 = "LOGIN_THE_TEST_DATA1";//测试数据1
    String LOGIN_THE_TEST_DATA2 = "LOGIN_THE_TEST_DATA2";//测试数据1
    String LOGIN_THE_TEST_DATA3 = "LOGIN_THE_TEST_DATA3";//测试数据1
}
