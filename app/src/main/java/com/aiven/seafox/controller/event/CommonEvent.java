package com.aiven.seafox.controller.event;

import com.aiven.seafox.controller.event.base.EventBase;
import com.aiven.seafox.model.controller.EventParam;

import java.util.ArrayList;


/**
 * 普通耗时事件
 * @项目名称   你我金融 APP客户端(Android)
 * @类名称   CommonEvent  
 * @创建人   Aiven
 * @Email  aiven163@sina.com
 * @创建时间 2015-6-24 下午4:56:37 
 * @类描述   TODO
 */
public abstract class CommonEvent extends EventBase {

	/**   
	 * serialVersionUID:TODO  
	 */

	private static final long serialVersionUID = -5274734212464965339L;

	public CommonEvent(int id) {
		super(id);
	}

	public CommonEvent(int id, String panelName) {
		super(id, panelName);
	}

	@Override
	final public void syncEventExcute(ArrayList<EventParam> paramList) {
		Object object = excuteBackData(paramList);
		eventOver(object);
	}

	/**
	 * 任务执行，并且在本线程中执行，执行完毕返回数据
	 * excuteBackData
	 * @param paramList
	 * @return
	 */
	public abstract Object excuteBackData(ArrayList<EventParam> paramList);

	/**
	 * 异常
	 * error
	 * @param errorCode
	 * @param errorInfo
	 */
	final public void error(int errorCode, String errorInfo) {
		errorToUI(eventId, errorCode, errorInfo);
	}

}
