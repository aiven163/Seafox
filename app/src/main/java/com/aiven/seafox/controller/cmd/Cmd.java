package com.aiven.seafox.controller.cmd;


import com.aiven.seafox.controller.intef.ICommand;

public class Cmd implements ICommand {

	/**
	 * @author Aiven
	 * @email aiven163@sina.com
	 * @date 2015-6-21 pm 4:50:09
	 * @desc
	 */
	private static final long serialVersionUID = -862357805282310897L;
	private String receivePanelName;
	private Object data;
	public final int cmdId;

	public Cmd(int cmdId) {
		this(cmdId, null);
	}

	public Cmd(int cmdId, String receivePanel) {
		this(cmdId, receivePanel, null);
	}

	public Cmd(int cmdId, String receivePanel, Object data) {
		this.cmdId = cmdId;
		this.receivePanelName = receivePanel;
		this.data = data;
	}

	@Override
	public Object getData() {
		return data;
	}

	public void setData(Object obj) {
		this.data = obj;
	}

	@Override
	public String getRecievePanelName() {
		return receivePanelName;
	}

	@Override
	public void setRecievePanel(String panelName) {
		this.receivePanelName = panelName;
	}

}
