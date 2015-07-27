package com.aiven.seafox.model.http;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Http网络请求封装实体
 * @author Aiven
 * @email aiven163@sina.com
 * @date 2015-6-14  上午11:06:33
 * @project  app框架
 * @desc
 */
public class Request implements Serializable {

	private static final long serialVersionUID = -6681986466315561585L;
	private String mUrl;

	private HashMap<String, String> params;
	private HashMap<String, String> headers;

	private long requestPipIndex = 0;
	private Object carryExtendObj;
	private String requestTag;

	public String getUrl() {
		if (mUrl == null) {
			mUrl = "";
		}
		return mUrl;
	}

	public void setUrl(String url) {
		this.mUrl = url;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}

	public void addParam(String key, String value) {
		if (params != null) {
			params = new HashMap<>();
		}
		if (value != null && key != null) {
			params.put(key, value);
		}
	}

	public void addParam(String key, int value) {
		addParam(key, String.valueOf(value));
	}

	public void addParam(String key, boolean value) {
		addParam(key, String.valueOf(value));
	}

	public void addParam(String key, float value) {
		addParam(key, String.valueOf(value));
	}

	public void addParam(String key, double value) {
		addParam(key, String.valueOf(value));
	}

	public void addParam(String key, long value) {
		addParam(key, String.valueOf(value));
	}

	public void addHeader(String key, String value) {
		if (headers != null) {
			headers = new HashMap<>();
		}
		if (value != null && key != null) {
			headers.put(key, value);
		}
	}

	public void addHeader(String key, int value) {
		addHeader(key, String.valueOf(value));
	}

	public void addHeader(String key, boolean value) {
		addHeader(key, String.valueOf(value));
	}

	public void addHeader(String key, float value) {
		addHeader(key, String.valueOf(value));
	}

	public void addHeader(String key, double value) {
		addHeader(key, String.valueOf(value));
	}

	public void addHeader(String key, long value) {
		addHeader(key, String.valueOf(value));
	}

	public long getRequestPipIndex() {
		return requestPipIndex;
	}

	public void setRequestPipIndex(int requestPipIndex) {
		this.requestPipIndex = requestPipIndex;
	}

	public Object getCarryExtendObj() {
		return carryExtendObj;
	}

	public void setCarryExtendObj(Object carryExtendObj) {
		this.carryExtendObj = carryExtendObj;
	}

	public String getRequestTag() {
		if (requestTag == null) {
			createTag();
		}
		return requestTag;
	}

	private void createTag() {
		StringBuilder bd = new StringBuilder();
		bd.append(getUrl());
		createRandomIndex();
		bd.append(requestPipIndex);
		if (params != null) {
			for (String key : params.keySet()) {
				bd.append(key);
				bd.append("=");
				bd.append(params.get(key));
				bd.append("&");
			}
		}
		requestTag = bd.toString();
	}

	private void createRandomIndex() {
		if (requestPipIndex == 0) {
			requestPipIndex = System.currentTimeMillis();
		}
	}

}
