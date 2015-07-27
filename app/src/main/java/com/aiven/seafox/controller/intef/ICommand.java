package com.aiven.seafox.controller.intef;

import java.io.Serializable;

public interface ICommand extends Serializable {

	
	String getRecievePanelName();
	Object getData();
	void setRecievePanel(String panelName);

}
