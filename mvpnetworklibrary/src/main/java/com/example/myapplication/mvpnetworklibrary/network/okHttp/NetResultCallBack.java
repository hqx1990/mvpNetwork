package com.example.myapplication.mvpnetworklibrary.network.okHttp;

/**
 * 项目名称：
 * 项目作者：hqx
 * 创建日期：2017/6/3 14:23.
 * ----------------------------------------------------------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------------------------------------------------------
 */
public interface NetResultCallBack {
    void onSuccess(Object response, String flag);
    void onErr(String retFlag, Object response, Object retBody, String flag);
}
