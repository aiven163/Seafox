package com.aiven.seafox.model.controller;

import java.io.Serializable;

/**
 * 参数
 * @项目名称   你我金融 APP客户端(Android)
 * @类名称   EventParam  
 * @创建人   Aiven
 * @Email  aiven163@sina.com
 * @创建时间 2015-6-24 下午2:25:31 
 * @类描述   TODO
 */
public class EventParam implements Serializable {

	/**   
	 * serialVersionUID:TODO  
	 */

	private static final long serialVersionUID = -6577288217891006813L;

	private String key;
	private Object value;

	public EventParam(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
