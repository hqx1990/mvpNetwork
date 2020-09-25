package com.example.myapplication.mvpnetwork.network;


import android.telecom.Call;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.mvpnetwork.util.JsonUtils;
import com.example.myapplication.mvpnetworklibrary.R;
import com.example.myapplication.mvpnetworklibrary.base.BaseApplication;
import com.example.myapplication.mvpnetworklibrary.network.bean.ResultBean;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.NetResultCallBack;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.callback.StringCallback;
import com.example.myapplication.mvpnetworklibrary.util.Logger;



/**
 * hqx
 * 2019-12-26
 * ----------------------------------------------------------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------------------------------------------------------
 */
public class DaoStringCallBack extends StringCallback {
    public static final String SUCCESS = "00000";
    public static final String RETMSG = "retMsg";
    public static final String BODY = "json";
    public NetResultCallBack callBack;

    public DaoStringCallBack(NetResultCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onError(okhttp3.Call call, Exception e, String flag, int code) {
        Logger.e("网络错误", e.toString());
        try {
            if (null!=callBack){
//                try {
//                    if(NetWorkUtils.isNetWorkAvailable(BaseApplication.getInstance())){
//                        CrashReport.postCatchedException(new Exception("noConnect无网络链接标识："+flag+"状态码："+responseCode+"错误日志："+e.toString()));//上报日志，追踪原因
//                    }
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
                callBack.onErr(BaseApplication.getInstance().getResources().getString(R.string.noConnectionCode),
                        BaseApplication.getInstance().getResources().getString(R.string.noConnection).concat(getShowCode(code)),"", flag);
            }
        } catch (Exception ex) {
            e.printStackTrace();
        }
    }

    /**
     * 获取拼接显示的code。仅在responseCode取值【300,599】区间，且非生产环境配置时，返回拼接code
     *
     * @param responseCode 传入的resonse code
     * @return 返回“（404）”格式，或者返回空
     */
    private String getShowCode(int responseCode) {
        String code = "";
        if (responseCode >= 300 && responseCode <= 599) {
            code = String.format("(%s)", responseCode);
        }
        return code;
    }

    @Override
    public void onResponse(String response, String flag, int responseCode) {
        Logger.e(flag, "返回报文：" + response);
        if (TextUtils.isEmpty(response)) {
            if (callBack != null)
                callBack.onErr(SUCCESS,
                        BaseApplication.getInstance().getResources()
                                .getString(R.string.noResponse)
                                .concat(getShowCode(responseCode)),"", flag);
            return;
        }
        ResultBean bean = null;
        String responseFront;
        if (15 < response.length()) {
            responseFront = response.substring(0, 15);
            if (responseFront.contains("\"response\"")) {
                response = response.substring(12, response.length() - 1);
            }
        }
        try {
            bean = JsonUtils.json2Class(response, ResultBean.class);
        } catch (Exception e) {
            if (callBack != null)
                callBack.onErr(e.getMessage(),
                        BaseApplication.getInstance().getResources()
                                .getString(R.string.data_exception)
                                .concat(getShowCode(responseCode)),"", flag);
            return;
        }

        if (bean == null) {
            if (callBack != null)
                callBack.onErr(SUCCESS,
                        BaseApplication.getInstance().getResources()
                                .getString(R.string.noResponse)
                                .concat(getShowCode(responseCode)),"", flag);
            return;
        }

        if (bean.head == null) {
            if (callBack != null)
                callBack.onErr(SUCCESS,
                        BaseApplication.getInstance().getResources()
                                .getString(R.string.data_exception)
                                .concat(getShowCode(responseCode)),"", flag);
        } else {
            if (SUCCESS.equals(bean.head.retFlag)) {
                Object obj = bean.body;
                if (obj == null) {
                    if (response.contains("body")) {
                        if (callBack != null)
                            callBack.onErr(SUCCESS,
                                    BaseApplication.getInstance().getResources()
                                            .getString(R.string.data_exception)
                                            .concat(getShowCode(responseCode)),"", flag);

                    } else {
                        if (callBack != null)
                            try {
                                callBack.onSuccess(response, flag);
                            } catch (Exception e) {
                                Logger.e(flag, e.getMessage());
                                callBack.onErr(e.getMessage(),
                                        BaseApplication.getInstance().getResources()
                                                .getString(R.string.data_exception)
                                                .concat(getShowCode(responseCode)),"", flag);
                            }
                    }
                } else {
                    String json;
                    try {
                        json = JSON.toJSON(bean.body).toString();
                    } catch (Exception e) {
                        if (callBack != null)
                            callBack.onErr(e.getMessage(),
                                    BaseApplication.getInstance().getResources()
                                            .getString(R.string.data_exception).concat(getShowCode(responseCode)),"", flag);
                        return;
                    }
                    if (!TextUtils.isEmpty(json)) {
                        if (callBack != null) {
                            try {
                                callBack.onSuccess(json, flag);
                            } catch (Exception e) {
                                Logger.e(flag, e.getMessage());
                                callBack.onErr(e.getMessage(),
                                        BaseApplication.getInstance().getResources()
                                                .getString(R.string.data_exception)
                                                .concat(getShowCode(responseCode)),"", flag);
                            }
                        }
                    } else {
                        if (callBack != null)
                            callBack.onErr(SUCCESS,
                                    BaseApplication.getInstance().getResources()
                                            .getString(R.string.data_exception).concat(getShowCode(responseCode)),"", flag);
                    }
                }
            } else {
                if (callBack != null) {
                    bean.head.retMsg = bean.head.retMsg == null ? "" : bean.head.retMsg;
                    if (bean.body != null) {
                        String json = JSON.toJSON(bean.body).toString();
                        //增加过滤空body情况
                        if (!TextUtils.isEmpty(json) && !"\"\"".equals(json)) {
                            //目前仅有存在返回体的情况下retBody返回
                            callBack.onErr(bean.head.retFlag, bean.head.retMsg, json, flag);
                        } else {
                            callBack.onErr(bean.head.retFlag, bean.head.retMsg,"", flag);
                        }
                    } else {
                        callBack.onErr(bean.head.retFlag, bean.head.retMsg,"", flag);
                    }
                }
            }
        }
    }
}
