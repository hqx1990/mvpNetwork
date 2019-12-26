package com.example.myapplication.mvpnetworklibrary.network.okHttp.callback;
/** 
 **********************
 * HttpCallback.java
 * package com.example.ok;
 * com.example.ok
 *
 * hqx
 * 2019-12-26
 * public class HttpCallback{ }
 * HttpCallback
 *************************
 */
public interface HttpCallback {
	void onNoConnection(String flag);
	void onSuccess(String response, String flag);
	void onErr(String error, String flag);
}
