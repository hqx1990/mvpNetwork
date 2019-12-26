package com.example.myapplication.mvpnetworklibrary.network.okHttp.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;


import com.example.myapplication.mvpnetworklibrary.network.okHttp.builder.DeleteBuilder;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.builder.GetBuilder;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.builder.PostFileBuilder;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.builder.PostFormBuilder;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.builder.PostImageBuider;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.builder.PostStringBuilder;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.builder.PutStringBuilder;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.callback.OkCallback;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.cookie.SimpleCookieJar;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.https.HttpsUtils;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.request.RequestCall;
import com.example.myapplication.mvpnetworklibrary.util.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpUtils {


    public static final String TAG = "OkHttpUtils";
    public static long DEFAULT_MILLISECONDS = 60000;//设置超时时间 单位毫秒
    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private X509TrustManager x509m = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };
    private OkHttpUtils() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        //cookie enabled
        okHttpClientBuilder.cookieJar(new SimpleCookieJar());
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[] { x509m },
                   null);
            okHttpClientBuilder.sslSocketFactory( sslcontext.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        mDelivery = new Handler(Looper.getMainLooper());

        if (true) {
            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        }

        mOkHttpClient = okHttpClientBuilder.build();
    }
    private boolean debug;
    private String tag;

    public OkHttpUtils debug(String tag) {
        debug = true;
        this.tag = tag;
        return this;
    }


    public static OkHttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils();
                }
            }
        }
        return mInstance;
    }

    public Handler getDelivery() {
        return mDelivery;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }


    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }
    public static PostImageBuider postImage() {
        return new PostImageBuider();
    }

    public static PutStringBuilder putString() {
        return new PutStringBuilder();
    }

    public static DeleteBuilder delete()
    {
        return new DeleteBuilder();
    }


    public void execute(final RequestCall requestCall, OkCallback callback, final String flag) {
        if (debug) {
            if (TextUtils.isEmpty(tag)) {
                tag = TAG;
            }
            Logger.d(tag, "{method:" + requestCall.getRequest().method() + ", detail:" + requestCall.getOkHttpRequest().toString() + "}");
        }

        if (callback == null)
            callback = OkCallback.CALLBACK_DEFAULT;
        final OkCallback finalCallback = callback;

        final Call call = requestCall.getCall();

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call1, IOException e) {
                sendFailResultCallback(call, e, finalCallback, flag, -1);
            }

            @Override
            public void onResponse(Call call1, Response response)  {
                if (response.code() >= 400 && response.code() <= 599) {
                    try {
                        sendFailResultCallback(call, new RuntimeException(response.body().string()), finalCallback, flag, response.code());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                try {
                    Object o = finalCallback.parseNetworkResponse(response);
                    sendSuccessResultCallback(o, finalCallback, flag, response.code());
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback, flag, response.code());
                }
            }
        });


    }

    public void sendFailResultCallback(final Call call, final Exception e, final OkCallback callback, final String flag, final int code) {
        if (callback == null) return;

        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e, flag, code);
                callback.onAfter();
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final OkCallback callback, final String flag, final int code) {
        if (callback == null) return;
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object, flag ,code);
                callback.onAfter();
            }
        });
    }

    public void cancelTag(Object tag) {
        if(tag == null){
            mOkHttpClient.dispatcher().cancelAll();
            return;
        }
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public void setCertificates(InputStream... certificates) {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .sslSocketFactory(HttpsUtils.getSslSocketFactory(certificates, null, null))
                .build();
    }


    public void setConnectTimeout(int timeout, TimeUnit units) {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .connectTimeout(timeout, units)
                .build();
    }
}

