package com.example.myapplication.mvpnetwork.base;

import com.example.myapplication.mvpnetwork.interfaces.DialogListener;
import com.example.myapplication.mvpnetworklibrary.base.BaseActivity;
import com.example.myapplication.mvpnetworklibrary.util.Logger;

public class MyBaseActivity extends BaseActivity {
    @Override
    protected void destroyPresenter() {

    }

    public void showProgress(boolean flag) {

    }


    public void showProgress(boolean flag, String msg) {

    }

    public void showDialog(String msg) {

    }

    public void showDialog(String msg, DialogListener listener){

    }

    @Override
    public void onErr(String retFlag, Object response, Object retBody, String flag) {
        showProgress(false);
        showDialog(response.toString());
        Logger.e("",flag+"返回报文："+retFlag+",\n"+response.toString());
    }

}
