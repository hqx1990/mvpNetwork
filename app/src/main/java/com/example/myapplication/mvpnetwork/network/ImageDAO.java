package com.example.myapplication.mvpnetwork.network;

import android.content.pm.PackageManager;
import android.widget.Toast;


import com.example.myapplication.mvpnetwork.R;
import com.example.myapplication.mvpnetwork.util.BaseUrl;
import com.example.myapplication.mvpnetworklibrary.base.BaseApplication;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.NetResultCallBack;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.utils.MD5Utile;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.utils.OkHttpUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 项目名称：HFAndroid
 * 项目作者：hqx
 * 创建日期：2017/6/15 10:41.
 * ----------------------------------------------------------------------------------------------------
 * 文件描述：图片上传及删除
 * <p>
 * 该图片上传及删除仅适用于嗨付特定的上传报文类型
 * ----------------------------------------------------------------------------------------------------
 */
public class ImageDAO implements NetResultCallBack {

    public static final String FLAG_IMAGE_UPLOAD = "*imagePath_upload#";
    public static final String FLAG_IMAGE_DELETE = "*imagePath_delete#";
    private DaoStringCallBack callback;
    private NetResultCallBack netResultCallBack;
    private Object tag;

    private String appVersion;

    private final List<String> upLoadImage = new ArrayList<>();//上传图片的集合
    private final List<String> deleteImage = new ArrayList<>();//删除图片的集合

    public ImageDAO(NetResultCallBack netResultCallBack) {
        this.tag = netResultCallBack;
        this.netResultCallBack = netResultCallBack;
        callback = new DaoStringCallBack(this);

        try {
            appVersion = BaseApplication.getInstance().getPackageManager().getPackageInfo(BaseApplication.getInstance().getPackageName(), PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (Exception e) {
            appVersion = "";
        }
    }

    /**
     * 上传单张图片
     *
     * @param url          url地址
     * @param imagePath    文件地址(必须保证文件路径正确)
     * @param requestParam 请求参数，可为null
     * @param flag         分发标志
     */
    public void post(String url, String imagePath, Map<String, String> requestParam, String flag) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(imagePath);
        } catch (FileNotFoundException e) {
            Toast.makeText(BaseApplication.getInstance(), R.string.file_not_exsit_str, Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(imagePath);
        String md5Str = MD5Utile.md5sum(inputStream);
        if (requestParam == null) {
            requestParam = new HashMap<>();
        }
        requestParam.put("md5", md5Str);
        OkHttpUtils.postImage()
                .tag(tag)
                .params(requestParam)
                .addHeader("Connection", "close")
                .addHeader("APPVersion", "AND-P-" + appVersion)
                .addHeader("SysVersion", "AND-P-" + android.os.Build.VERSION.RELEASE)
                .addImage("multipartFile", file.getName(), file)
                .url(BaseUrl.baseUrl + url)
                .build()
                .execute(callback, flag);
    }

    /**
     * 上传多张图片
     *
     * @param url          上传图片地址
     * @param imagePaths   图片路径的集合
     * @param requestParam 请求参数
     * @param flag         分发标识，不能为""或者null
     */
    public void post(String url, List<String> imagePaths, Map<String, String> requestParam, String flag) {
        upLoadImage.clear();
        for (String path : imagePaths) {
            flag = path + FLAG_IMAGE_UPLOAD + flag;
            upLoadImage.add(path);
            post(url, path, requestParam, flag);
        }
    }

    /**
     * 删除图片文件
     *
     * @param url  url地址
     * @param id   要删除的文件id
     * @param flag 分发标识
     */
    public void delete(String url, String id, String flag) {
        OkHttpUtils.delete()
                .tag(tag)
                .content("")
                .url(BaseUrl.baseUrl + url + "?id=" + id)
                .addHeader("Connection", "close")
                .addHeader("APPVersion", "AND-P-" + appVersion)
                .addHeader("SysVersion", "AND-P-" + android.os.Build.VERSION.RELEASE)
                .build()
                .execute(callback, flag);
    }

    /**
     * 删除图片
     *
     * @param url  url地址
     * @param ids  文件id
     * @param flag 分发标识
     */
    public void delete(String url, List<String> ids, String flag) {
        deleteImage.clear();
        for (String id : ids) {
            flag = id + FLAG_IMAGE_UPLOAD + flag;
            deleteImage.add(id);
            delete(url, id, flag);
        }
    }

    @Override
    public void onSuccess(Object response, String flag) {
        if (flag.contains(FLAG_IMAGE_UPLOAD)) {
            //上传多张图片
            String path = flag.substring(0, flag.indexOf("*"));
            upLoadImage.remove(path);
            if (null == upLoadImage || upLoadImage.size() == 0) {
                if (netResultCallBack != null) {
                    flag = flag.substring(flag.indexOf("#") + 1);
                    netResultCallBack.onSuccess(response, flag);
                }
                upLoadImage.clear();
                deleteImage.clear();
            }
        } else if (flag.contains(FLAG_IMAGE_DELETE)) {
            //删除多张图片
            String id = flag.substring(0, flag.indexOf("*"));
            deleteImage.remove(id);
            if (null == deleteImage || deleteImage.size() == 0) {
                if (netResultCallBack != null) {
                    flag = flag.substring(flag.indexOf("#") + 1);
                    netResultCallBack.onSuccess(response, flag);
                }
                upLoadImage.clear();
                deleteImage.clear();
            }
        } else {
            upLoadImage.clear();
            deleteImage.clear();
            if (netResultCallBack != null) {
                netResultCallBack.onSuccess(response, flag);
            }
        }
    }

    @Override
    public void onErr(String retFlag, Object response, Object retBody, String flag) {
        if (flag.contains(FLAG_IMAGE_UPLOAD)) {
            //上传多张图片
            String path = flag.substring(0, flag.indexOf("*"));
            upLoadImage.remove(path);
            if (null == upLoadImage || upLoadImage.size() == 0) {
                if (netResultCallBack != null) {
                    flag = flag.substring(flag.indexOf("#") + 1);
                    netResultCallBack.onErr(retFlag, response, retBody, flag);
                }
            }
        } else if (flag.contains(FLAG_IMAGE_DELETE)) {
            //删除多张图片
            String id = flag.substring(0, flag.indexOf("*"));
            deleteImage.remove(id);
            if (null == deleteImage || deleteImage.size() == 0) {
                if (netResultCallBack != null) {
                    flag = flag.substring(flag.indexOf("#") + 1);
                    netResultCallBack.onErr(retFlag, response, retBody, flag);
                }
                upLoadImage.clear();
                deleteImage.clear();
            }
        } else {
            upLoadImage.clear();
            deleteImage.clear();
            if (netResultCallBack != null) {
                netResultCallBack.onErr(retFlag, response, retBody, flag);
            }
        }
    }
}
