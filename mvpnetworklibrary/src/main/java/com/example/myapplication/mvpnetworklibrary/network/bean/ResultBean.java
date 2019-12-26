package com.example.myapplication.mvpnetworklibrary.network.bean;

/**
 **********************
 * ResultBean.java
 * package com.example.ok;
 * com.example.ok
 *
 * hqx
 * 2019-12-26
 * public class ResultBean{ }
 * ResultBean
 *************************
 */
public class ResultBean {
	public HeadBean head;
	public Object body;
	
	public HeadBean getHead() {
		return head;
	}
	public void setHead(HeadBean head) {
		this.head = head;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}

}
