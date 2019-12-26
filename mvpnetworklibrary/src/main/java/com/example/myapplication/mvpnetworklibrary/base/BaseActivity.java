package com.example.myapplication.mvpnetworklibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.mvpnetworklibrary.network.okHttp.NetResultCallBack;
import com.example.myapplication.mvpnetworklibrary.util.taskbar.FitStateUI;
import com.example.myapplication.mvpnetworklibrary.util.taskbar.OSUtils;


/**
 * Created by Administrator on 2018/3/29.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, NetResultCallBack {


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        BaseApplication.getInstance().currentActivity=this;
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        BaseApplication.getInstance().currentActivity=this;
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        BaseApplication.getInstance().currentActivity=this;
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BaseApplication.getInstance().currentActivity=this;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        BaseApplication.getInstance().currentActivity=this;
    }
    @Override
    protected void onResume() {
        super.onResume();
        BaseApplication.getInstance().currentActivity=this;

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (OSUtils.getRomType() == OSUtils.ROM_TYPE.FLYME)
            FitStateUI.setMeizuStatusBarDarkIcon(this, true);
        if (OSUtils.getRomType() == OSUtils.ROM_TYPE.EMUI)
            FitStateUI.setImmersionStateMode(this);
        if (OSUtils.getRomType() == OSUtils.ROM_TYPE.MIUI)
            FitStateUI.setStatusBarFontIconDark(true, this);
        if (OSUtils.getRomType() == OSUtils.ROM_TYPE.OTHER)
            FitStateUI.setImmersionStateMode(this);
        FitStateUI.initStatusBar(this);

        BaseApplication.getActManger().pushActivity(this);//将activity加入统一管理类
        BaseApplication.getInstance().currentActivity = this;

    }

    @Override
    protected void onDestroy() {
        BaseApplication.getActManger().popActivity(this);//将activity移出栈
        BaseApplication.getInstance().currentActivity = null;
        destroyPresenter();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

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
     * 用于销毁presenter
     */
    protected abstract void destroyPresenter();


    public void toActivity(Class<?> cls, Bundle bundle, int requestCode){
        Intent intent = new Intent(this,cls);
        if(null != bundle) {
            intent.putExtras(bundle);
        }
        if(-1 != requestCode){
            startActivityForResult(intent,requestCode);
        }else{
            startActivity(intent);
        }

    }
    public void toActivity(Class<?> cls){
        toActivity(cls,null,-1);
    }
    public void toActivity(Class<?> cls, Bundle bundle){
        toActivity(cls,bundle,-1);
    }

    @Override
    public void onSuccess(Object response, String flag) {
    }

    @Override
    public void onErr(String retFlag, Object response, Object retBody, String flag) {

    }
}
