package com.example.myapplication.mvpnetwork;

import android.os.Bundle;

import com.example.myapplication.mvpnetwork.base.MyBaseActivity;
import com.example.myapplication.mvpnetwork.bean.GetDictBeanRtn;
import com.example.myapplication.mvpnetwork.persenter.UseMVPFrameworkPersenter;
import com.example.myapplication.mvpnetwork.view.UseMVPFrameworkView;
import com.example.myapplication.mvpnetworklibrary.util.Logger;

import android.view.View;
import android.widget.TextView;

public class MainActivity extends MyBaseActivity implements UseMVPFrameworkView {

    private UseMVPFrameworkPersenter useMVPFrameworkPersenter;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        findViewById(R.id.btmsg).setOnClickListener(this);

    }

    @Override
    protected void destroyPresenter() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btmsg:
                if(null == useMVPFrameworkPersenter){
                    useMVPFrameworkPersenter = new UseMVPFrameworkPersenter(this);

                }
                textView.setText("");
                showProgress(true);
                useMVPFrameworkPersenter.get();
                break;
        }
    }

    @Override
    public void onSuccess(Object response, String flag) {
        textView.setText(response.toString());
    }

    @Override
    public void verificationCodeView(GetDictBeanRtn getDictBeanRtn) {
        Logger.e("接口请求成功","接口请求成功");
        showProgress(false);
        showDialog("接口请求成功");
    }


}
