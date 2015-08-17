package com.aiven.seafox.controller.event.base;

import com.aiven.seafox.controller.Mediator;
import com.aiven.seafox.controller.cmd.Cmd;
import com.aiven.seafox.controller.intef.IPanel;
import com.aiven.seafox.controller.util.HandlerUtils;
import com.aiven.seafox.model.controller.EventParam;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Event Base class
 * 
 * @author Aiven
 * @email aiven163@sina.com
 * @date 2015-6-21 pm 3:40:02
 * @desc
 */
public abstract class EventBase extends Thread implements Serializable {

	public static final long serialVersionUID = -2425922859787509064L;
	protected int eventId;
	protected String PanelName;
	protected int executeOverCmd = -1;
	protected Object executeOverCmdData = null;
	protected ArrayList<EventParam> params;

	/**
	 * next event
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
	 * add next action event
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
	 * Set up after the completion of the task needs to be sent by the command, sent only after the task successfully
	 * setExucteOverCmd
	 * @param cmdId
	 */
	public void setExucteOverCmd(int cmdId) {
		this.executeOverCmd = cmdId;
	}

	/**
	 * Set after the completion of the task, carry the information issued by the command
	 * setExcuteOverCmdData
	 * @param executeOverCmdData
	 */
	public void setExcuteOverCmdData(Object executeOverCmdData) {
		this.executeOverCmdData = executeOverCmdData;
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
		if (executeOverCmd != -1) {
			Cmd cmd = new Cmd(executeOverCmd);
			cmd.setData(prepareCmdData(overData));
			sendCommand(cmd);
		}
		if (this.nextEvent != null) {
			ArrayList<EventParam> list = nextEventParam(nextEvent.params);
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
	 * prepare next event's parameters
	 * paramNextEventParam
	 * @return
	 */
	abstract public ArrayList<EventParam> nextEventParam(ArrayList<EventParam> params);

}
