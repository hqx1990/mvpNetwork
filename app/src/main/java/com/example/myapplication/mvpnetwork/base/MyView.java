package com.example.myapplication.mvpnetwork.base;

import com.example.myapplication.mvpnetworklibrary.base.BaseView;
import com.example.myapplication.mvpnetworklibrary.dialog.DialogListener;

public interface MyView extends BaseView {
    void showProgress(boolean flag);

    void showProgress(boolean flag, String msg);

    void showDialog(String msg);

    void showDialog(String msg, DialogListener listener);
}
