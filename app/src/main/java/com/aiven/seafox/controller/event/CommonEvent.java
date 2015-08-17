package com.aiven.seafox.controller.event;

import com.aiven.seafox.controller.event.base.EventBase;
import com.aiven.seafox.model.controller.EventParam;

import java.util.ArrayList;


/**
 * The Common ime consuming Event
 * @ClassName   CommonEvent
 * @Author   Aiven
 * @Email  aiven163@sina.com
 * @CreateTime 2015-6-24 pm 4:56:37
 * @desc   TODO
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
	 * Task execution, and execution, in this thread for the returned data
	 * excuteBackData
	 * @param paramList
	 * @return
	 */
	public abstract Object excuteBackData(ArrayList<EventParam> paramList);

	/**
	 * exception
	 * error
	 * @param errorCode
	 * @param errorInfo
	 */
	final public void error(int errorCode, String errorInfo) {
		errorToUI(eventId, errorCode, errorInfo);
	}

}
