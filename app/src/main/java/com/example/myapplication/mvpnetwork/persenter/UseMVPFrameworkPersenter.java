package com.example.myapplication.mvpnetwork.persenter;


import com.example.myapplication.mvpnetwork.bean.GetDictBeanRtn;
import com.example.myapplication.mvpnetwork.model.UseMVPFrameworkModel;
import com.example.myapplication.mvpnetwork.view.UseMVPFrameworkView;
import com.example.myapplication.mvpnetworklibrary.base.BasePresenter;

public class UseMVPFrameworkPersenter extends BasePresenter<UseMVPFrameworkView> {
    /**
     * 构造方法
     *
     * @param view
     */
    public UseMVPFrameworkPersenter(UseMVPFrameworkView view) {
        super(view);
    }

    public void get(){
        new UseMVPFrameworkModel(this).get();
    }

    @Override
    public void onSuccess(Object response, String flag) {
        if (viewIsNull()){
            return;
        }
        view.showProgress(false);
        GetDictBeanRtn getDictBeanRtn = (GetDictBeanRtn) new UseMVPFrameworkModel(this).parseResponse(response.toString());
        view.verificationCodeView(getDictBeanRtn);
        view.onSuccess(response, flag);
    }
}
