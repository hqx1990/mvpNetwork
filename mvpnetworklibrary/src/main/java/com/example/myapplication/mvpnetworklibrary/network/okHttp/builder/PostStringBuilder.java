package com.example.myapplication.mvpnetworklibrary.network.okHttp.builder;

import com.example.myapplication.mvpnetworklibrary.network.okHttp.request.PostStringRequest;
import com.example.myapplication.mvpnetworklibrary.network.okHttp.request.RequestCall;
import com.example.myapplication.mvpnetworklibrary.util.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;

/**
 * hqx
 * 2019-12-26
 */
public class PostStringBuilder extends OkHttpRequestBuilder
{
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content)
    {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        Logger.e("请求报文", url + ":" + content + "");
        return new PostStringRequest(url, tag, params, headers, content, mediaType).build();
    }
    public RequestCall build(boolean isRetry)
    {
        Logger.e("请求报文", url + ":" + content + "");
        if(!isRetry) {
            return new PostStringRequest(url, tag, params, headers, content, mediaType).build(isRetry);
        }
        return new PostStringRequest(url, tag, params, headers, content, mediaType).build();
    }

    @Override
    public PostStringBuilder url(String url)
    {
        this.url = url;
        return this;
    }

    @Override
    public PostStringBuilder tag(Object tag)
    {
        this.tag = tag;
        return this;
    }

    @Override
    public PostStringBuilder params(Map<String, String> params)
    {
        this.params = params;
        return this;
    }

    @Override
    public PostStringBuilder addParams(String key, String val)
    {
        if (this.params == null)
        {
            params = new LinkedHashMap<String, String>();
        }
        params.put(key, val);
        return this;
    }

    @Override
    public PostStringBuilder headers(Map<String, String> headers)
    {
        this.headers = headers;
        return this;
    }

    @Override
    public PostStringBuilder addHeader(String key, String val)
    {
        if (this.headers == null)
        {
            headers = new LinkedHashMap<String, String>();
        }
        headers.put(key, val);
        return this;
    }
}
