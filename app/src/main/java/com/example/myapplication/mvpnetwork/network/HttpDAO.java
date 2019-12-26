package com.example.myapplication.mvpnetwork.network;

import android.content.pm.PackageManager;


import com.example.myapplication.mvpnetwork.util.BaseUrl;
import com.example.myapplication.mvpnetworklibrary.base.BaseApplication;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.NetResultCallBack;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.builder.GetBuilder;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.builder.PostStringBuilder;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.builder.PutStringBuilder;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.utils.JsonUtils;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.utils.OkHttpUtils;

import java.util.Map;

/**
 * 项目名称：
 * 项目作者：hqx
 * 创建日期：2017/6/3 14:44.
 * ----------------------------------------------------------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------------------------------------------------------
 */
public class HttpDAO {
    //不需要token接口添加到此处
    public static String helpid = "";
    //不需要重发的接口添加到此处
    public static final String NO_RETRY = "";

    private DaoStringCallBack callback;
    private Object tag;

    private String appVersion;

    public HttpDAO(NetResultCallBack netResultCallBack) {
        this.tag = netResultCallBack;
        callback = new DaoStringCallBack(netResultCallBack);

        try {
            appVersion = BaseApplication.getInstance().getPackageManager().getPackageInfo(BaseApplication.getInstance().getPackageName(), PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (Exception e) {
            appVersion = "";
        }
    }

    /**
     * 可调用方法，requestBean是请求参数的封装类
     */
    public <E> void put(String url, E requestParam, String flag) {
        PutStringBuilder builder = OkHttpUtils.putString()
                .tag(tag)
                .url(BaseUrl.baseUrl + url)
                .content(JsonUtils.class2Json(requestParam))
                .addHeader("Connection", "close")
                .addHeader("APPVersion", "AND-P-" + appVersion)
                .addHeader("SysVersion", "AND-P-" + android.os.Build.VERSION.RELEASE);

        builder.build().execute(callback, flag);
    }



    public void get(String url, Map<String, String> requestParam, String flag) {
        GetBuilder builder = OkHttpUtils
                .get()
                .tag(tag)
                .url(BaseUrl.baseUrl + url)
                .params(requestParam)
                .addHeader("Connection", "close")
                .addHeader("APPVersion", "AND-P-" + appVersion);

        builder.build().execute(callback, flag);
    }


    public <E> void post(String url, E requestParam, String flag) {
        PostStringBuilder builder = OkHttpUtils
                .postString()
                .tag(tag)
                .url(BaseUrl.baseUrl + url)
                .content(JsonUtils.class2Json(requestParam))
                .addHeader("Connection", "close")
                .addHeader("APPVersion", "AND-P-" + appVersion);

        if (NO_RETRY.contains(url)) {
            builder.build(false).execute(callback, flag);
        } else {
            builder.build().execute(callback, flag);
        }
    }
}
