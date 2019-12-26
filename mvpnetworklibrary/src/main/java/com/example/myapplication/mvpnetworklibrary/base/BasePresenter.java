package com.example.myapplication.mvpnetworklibrary.base;


import com.example.myapplication.mvpnetworklibrary.network.okHttp.NetResultCallBack;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.utils.OkHttpUtils;

import java.lang.ref.WeakReference;


/**
 * 项目名称：
 * 项目作者：hqx
 * 创建日期：2017/6/3 9:55.
 * ----------------------------------------------------------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------------------------------------------------------
 */
public class BasePresenter<T extends BaseView> implements NetResultCallBack {
    /**
     * 弱引用
     */
    private WeakReference<T> reference;
    /**
     * IView
     */
    protected T view;

    /**
     * 构造方法
     * @param view
     */
    public BasePresenter(T view) {
        onAttach(view);
    }

    /**
     * 判断view是否为空 true空  false非空
     * @return
     */
    public boolean viewIsNull(){
        return view == null;
    }

    /**
     * 绑定View
     */
    public void onAttach(T view){
        if(!isAttached()){
            reference = new WeakReference<>(view);
            this.view = reference.get();
        }
    }
    /**
     * 判断弱引用是否为空
     */
    public boolean isAttached(){
        return reference != null && reference.get() != null;
    }
    /**
     * 在销毁的时候调用,解除绑定
     */
    public void onDettached(){
        OkHttpUtils.getInstance().cancelTag(this);
        /**
         * 如果判断弱引用非空则清除或设置为null
         */
        if(isAttached()){
            reference.clear();
        }
        reference = null;
        view = null;

    }

    @Override
    public void onSuccess(Object response, String flag) {

    }

    @Override
    public void onErr(String retFlag, Object response, Object retBody, String flag) {
        if (viewIsNull()){
            return;
        }
        view.onErr(retFlag, response, retBody, flag);
    }
}
