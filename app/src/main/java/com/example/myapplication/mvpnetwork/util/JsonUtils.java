package com.example.myapplication.mvpnetwork.util;


import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by hackzhang on 2018/5/22.
 */

public class JsonUtils {
    //类转json
    public static <T> String class2Json(T cls) {
        if( null != cls){
            return JSON.toJSON(cls).toString();
        }else {
            return "";
        }
    }

    /**
     * json转类
     */
    public static <T> T json2Class(String json, Class<T> cls) {
        return  JSON.parseObject(json, cls);
    }

    //json转list
    public static <T> List<T> json2list(String json, Class<T> clazz) {
        return JSON.parseArray(json,clazz);
    }
}
