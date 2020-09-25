package com.example.myapplication.mvpnetwork.base;

import android.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.mvpnetworklibrary.base.BaseActivity;
import com.example.myapplication.mvpnetworklibrary.dialog.DialogListener;
import com.example.myapplication.mvpnetworklibrary.dialog.ShowDialog;
import com.example.myapplication.mvpnetworklibrary.util.Logger;

public class MyBaseActivity extends BaseActivity {
    @Override
    protected void destroyPresenter() {

    }

    public void showProgress(boolean flag) {
        ShowDialog.getInstance().showProgress(flag,"",this);
    }


    public void showProgress(boolean flag, String msg) {
        ShowDialog.getInstance().showProgress(flag,msg,this);
    }

    public void showDialog(String msg) {
        showProgress(false);
        dismissDialog();
        ShowDialog.getInstance().showDialog(this, msg, true, new DialogListener() {
            @Override
            public void dialogCallBack(AlertDialog dialog, TextView left, TextView right) {
                left.setVisibility(View.GONE);
                right.setText("知道了");
                right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissDialog();
                    }
                });
            }
        });
    }

    public void showDialog(String msg, DialogListener listener){
        showProgress(false);
        dismissDialog();
        ShowDialog.getInstance().showDialog(this, msg, true, listener);
    }

    private static Toast mToast;
    public void showToast(String str){
        if (mToast == null)
        {
            mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(str);
        mToast.show();
    }

    /**
     * 关闭弹框
     */
    public void dismissDialog(){
        ShowDialog.getInstance().dismissDialog();
    }


    @Override
    public void onErr(String retFlag, Object response, Object retBody, String flag) {
        showProgress(false);
        showDialog(response.toString());
        Logger.e("",flag+"返回报文："+retFlag+",\n"+response.toString());
    }

}
