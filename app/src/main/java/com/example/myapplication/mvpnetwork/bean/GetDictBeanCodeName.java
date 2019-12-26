package com.example.myapplication.mvpnetwork.bean;

import java.io.Serializable;

/**
 * Created by use on 2017/6/7.
 */

public class GetDictBeanCodeName implements Serializable {
    public String code;
    public String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
