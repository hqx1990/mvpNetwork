package com.example.myapplication.mvpnetworklibrary.base;



import com.example.myapplication.mvpnetworklibrary.network.okHttp.NetResultCallBack;

import java.lang.ref.WeakReference;

/**
 * 项目名称：
 * 项目作者：hqx
 * 创建日期：2017/6/4 20:13.
 * ----------------------------------------------------------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------------------------------------------------------
 */
public abstract class BaseModel
{
    private final WeakReference<NetResultCallBack> reference;

    public BaseModel(NetResultCallBack callBack)
    {
        reference = new WeakReference(callBack);
    }

    protected NetResultCallBack callBack()
    {
        if (reference != null)
        {
            return reference.get();
        }
        return null;
    }

    public abstract Object parseResponse(String response);
}
