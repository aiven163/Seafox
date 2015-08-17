package com.aiven.seafox.controller.cmd.observer;


import com.aiven.seafox.controller.cmd.Cmd;
import com.aiven.seafox.controller.intef.IPanel;
import com.aiven.seafox.controller.util.HandlerUtils;

import java.io.Serializable;

public class Observer implements Serializable {

	/**
	 * @author Aiven
	 * @email aiven163@sina.com
	 * @date 2015-6-21 pm 4:10:11
	 * @desc
	 */
	private static final long serialVersionUID = -6004755843734333420L;


	public Observer() {
	}
	

	public void findCmd(final IPanel Panel, final Cmd cmd) {
		if (Panel == null || cmd == null)
			return;
		HandlerUtils.getInstance().getCmdHandler().post(new Runnable() {
			@Override
			public void run() {
				Panel.onHandCmd(cmd.cmdId, cmd.getData());
			}
		});
	}

}
