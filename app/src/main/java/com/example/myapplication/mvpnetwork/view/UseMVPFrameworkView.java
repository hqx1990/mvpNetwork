package com.example.myapplication.mvpnetwork.view;


import com.example.myapplication.mvpnetwork.base.MyView;
import com.example.myapplication.mvpnetwork.bean.GetDictBeanRtn;

public interface UseMVPFrameworkView extends MyView {
    void verificationCodeView(GetDictBeanRtn getDictBeanRtn);
}
