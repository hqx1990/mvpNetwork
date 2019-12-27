package com.example.myapplication.mvpnetwork.model;


import com.example.myapplication.mvpnetwork.bean.GetDictBeanRtn;
import com.example.myapplication.mvpnetwork.network.HttpDAO;
import com.example.myapplication.mvpnetwork.util.BaseUrl;
import com.example.myapplication.mvpnetwork.util.JsonUtils;
import com.example.myapplication.mvpnetworklibrary.base.BaseModel;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.NetResultCallBack;

import java.util.HashMap;

public class UseMVPFrameworkModel extends BaseModel {
    public UseMVPFrameworkModel(NetResultCallBack callBack) {
        super(callBack);
    }

    private HttpDAO httpDAO;
    public void get(){
        if(null == httpDAO){
            httpDAO = new HttpDAO(callBack());
        }
        HashMap<String, String> map = new HashMap<>();

        httpDAO.get(BaseUrl.URL_GETDICT,map,BaseUrl.URL_GETDICT);
    }

    @Override
    public Object parseResponse(String response) {
        GetDictBeanRtn getDictBeanRtn = JsonUtils.json2Class(response,GetDictBeanRtn.class);
        return getDictBeanRtn;
    }
}
