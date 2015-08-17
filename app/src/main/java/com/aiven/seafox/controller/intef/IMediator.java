package com.aiven.seafox.controller.intef;

import com.aiven.seafox.controller.cmd.Cmd;
import com.aiven.seafox.controller.cmd.observer.Observer;

import java.util.HashMap;


public interface IMediator {

	void init();

	/**
	 * regist the pannel
	 * registPanel
	 * @param panel
	 */
	void registPanel(IPanel panel);

	/**
	 * remove the pannel
	 * removePanel
	 * @param panelName
	 */
	void removePanel(String panelName);

	/**
	 * According to the command name for all observers
	 * getCmdObservers
	 * @param cmdId
	 * @return
	 */
	HashMap<String, Observer> getCmdObservers(int cmdId);

	/**
	 * According to the name of the panel,search the  panel
	 * findPanelByName
	 * @param panelName
	 * @return
	 */
	IPanel findPanelByName(String panelName);

	/**
	 * send command
	 * sendCommand
	 * @param cmd
	 */
	void sendCommand(Cmd cmd);
}
