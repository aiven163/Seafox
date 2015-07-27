package com.aiven.seafox.controller.event.base;

import com.aiven.seafox.controller.Mediator;
import com.aiven.seafox.controller.cmd.Cmd;
import com.aiven.seafox.controller.intef.IPanel;
import com.aiven.seafox.controller.util.HandlerUtils;
import com.aiven.seafox.model.controller.EventParam;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 动作事件
 * 
 * @author Aiven
 * @email aiven163@sina.com
 * @date 2015-6-21 下午3:40:02
 * @project app框架
 * @desc
 */
public abstract class EventBase extends Thread implements Serializable {

	public static final long serialVersionUID = -2425922859787509064L;
	protected int eventId;
	protected String PanelName;
	protected int excuteOverCmd = -1;
	protected Object excuteOverCmdData = null;
	protected ArrayList<EventParam> params;

	/**
	 * 下一个任务
	 */
	protected EventBase nextEvent;

	public EventBase(int id) {
		this(id, null);
	}

	public EventBase(int id, String panelName) {
		this.eventId = id;
		this.PanelName = panelName;
		params = new ArrayList<>();
	}

	/**
	 * 增加下一个执行任务
	 * addNextEvent
	 * @param nextEvent
	 */
	public void addNextEvent(EventBase nextEvent) {
		this.nextEvent = nextEvent;

	}

	public void removeNextEvent() {
		this.nextEvent = null;
	}

	public String getPanelName() {
		return this.PanelName;
	}

	/**
	 * 设置任务完毕后所需要发送的命令，只有任务成功执行完毕后才会发送
	 * setExucteOverCmd
	 * @param cmdId
	 */
	public void setExucteOverCmd(int cmdId) {
		this.excuteOverCmd = cmdId;
	}

	/**
	 * 设置任务执行完毕后，所发命令携带信息
	 * setExcuteOverCmdData
	 * @param excuteOverCmdData
	 */
	public void setExcuteOverCmdData(Object excuteOverCmdData) {
		this.excuteOverCmdData = excuteOverCmdData;
	}

	public void addParam(String key, int value) {
		addParamObj(key, value);
	}

	public void addParam(String key, String value) {
		addParamObj(key, value);
	}

	public void addParam(String key, double value) {
		addParamObj(key, value);
	}

	public void addParam(String key, float value) {
		addParamObj(key, value);
	}

	public void addParam(String key, Serializable value) {
		addParamObj(key, value);
	}

	private void addParamObj(String key, Object obj) {
		if (params == null)
			params = new ArrayList<>();
		params.add(new EventParam(key, obj));
	}

	protected void addParam(EventParam param) {
		if (params == null)
			params = new ArrayList<>();
		params.add(param);
	}

	@Override
	public void run() {
		syncEventExcute(params);
	}

	public void excute() {
		start();
	}

	protected void eventOver(Object overData) {
		taskToUI(overData);
		if (excuteOverCmd != -1) {
			Cmd cmd = new Cmd(excuteOverCmd);
			cmd.setData(prepareCmdData(overData));
			sendCommand(cmd);
		}
		if (this.nextEvent != null) {
			ArrayList<EventParam> list = paramNextEventParam(nextEvent.params);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					this.nextEvent.addParam(list.get(i));
				}
			}
			this.nextEvent.excute();
		}
	}

	final private void taskToUI(final Object data) {
		if (PanelName == null)
			return;
		HandlerUtils.getInstance().getUIHandler().post(new Runnable() {

			@Override
			public void run() {
				IPanel Panel = Mediator.getInstance().findPanelByName(PanelName);
				if (Panel != null) {
					Panel.eventHandle(eventId, data);
				}
			}
		});
	}

	final protected void errorToUI(final int eventId, final int errorCode, final String reason) {
		if (PanelName == null)
			return;
		HandlerUtils.getInstance().getUIHandler().post(new Runnable() {

			@Override
			public void run() {
				IPanel Panel = Mediator.getInstance().findPanelByName(PanelName);
				if (Panel != null) {
					Panel.eventError(eventId, errorCode, reason);
				}
			}
		});
	}

	public Object prepareCmdData(Object eventOverData) {
		return eventOverData;
	}

	public void sendCommand(Cmd cmd) {
		Mediator.getInstance().sendCommand(cmd);
	}

	abstract public void syncEventExcute(ArrayList<EventParam> paramList);

	/**
	 * 准备下一个任务参数
	 * paramNextEventParam
	 * @return
	 */
	abstract public ArrayList<EventParam> paramNextEventParam(ArrayList<EventParam> params);

}
