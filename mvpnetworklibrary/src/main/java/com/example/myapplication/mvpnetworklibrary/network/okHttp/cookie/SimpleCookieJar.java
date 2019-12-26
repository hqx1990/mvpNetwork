package com.example.myapplication.mvpnetworklibrary.network.okHttp.cookie;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;


public final class SimpleCookieJar implements CookieJar
{
    private final List<Cookie> allCookies = new ArrayList<>();

    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies)
    {
        allCookies.addAll(cookies);
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url)
    {
        List<Cookie> result = new ArrayList<>();
        int size=allCookies.size();
        for(int i=0;i<size;i++){
            if (allCookies.get(i).matches(url)){
                result.add(allCookies.get(i));
            }
        }
        return result;
    }
}
