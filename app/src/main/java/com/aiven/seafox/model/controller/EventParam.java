package com.aiven.seafox.model.controller;

import java.io.Serializable;

/**
 * @ClassName   EventParam
 * @Author   Aiven
 * @Email  aiven163@sina.com
 * @CreateTime 2015-6-24 PM 2:25:31
 * @Desc   TODO
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
