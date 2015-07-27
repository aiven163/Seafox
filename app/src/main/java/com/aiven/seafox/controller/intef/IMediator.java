package com.aiven.seafox.controller.intef;

import com.aiven.seafox.controller.cmd.Cmd;
import com.aiven.seafox.controller.cmd.observer.Observer;

import java.util.HashMap;


public interface IMediator {

	void init();

	/**
	 * 注册面板
	 * registPanel
	 * @param panel
	 */
	void registPanel(IPanel panel);

	/**
	 * 移除面板
	 * removePanel
	 * @param panelName
	 */
	void removePanel(String panelName);

	/**
	 * 根据命令名称获取所有的观察者
	 * getCmdObservers
	 * @param cmdId
	 * @return
	 */
	HashMap<String, Observer> getCmdObservers(int cmdId);

	/**
	 * 根据面板名称，查找面板
	 * findPanelByName
	 * @param panelName
	 * @return
	 */
	IPanel findPanelByName(String panelName);

	/**
	 * 发送命令
	 * sendCommand
	 * @param cmd
	 */
	void sendCommand(Cmd cmd);
}
