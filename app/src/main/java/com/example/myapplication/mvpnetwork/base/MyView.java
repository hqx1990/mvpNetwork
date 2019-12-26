package com.example.myapplication.mvpnetwork.base;

import com.example.myapplication.mvpnetwork.interfaces.DialogListener;
import com.example.myapplication.mvpnetworklibrary.base.BaseView;

public interface MyView extends BaseView {
    void showProgress(boolean flag);

    void showProgress(boolean flag, String msg);

    void showDialog(String msg);

    void showDialog(String msg, DialogListener listener);
}
