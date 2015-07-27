package com.aiven.seafox.controller.log.model;


import com.aiven.seafox.controller.log.util.LogTimeUtils;

/**
 * @author Aiven
 * @date 2014-6-3  下午6:10:52
 * @email aiven163@sina.com
 * @Description 日志内容实体
 */
public class LogMode {
	/**
	 * 标签
	 */
	private String tag;
	/**
	 * 日志内容
	 */
	private String msg;
	/**
	 * 日志时间
	 */
	private String time;

	public LogMode(String tag, String msg) {
		super();
		this.tag = tag;
		this.msg = msg;
		this.time = LogTimeUtils.getInstance().getTime();
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
