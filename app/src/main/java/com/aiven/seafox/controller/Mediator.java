package com.aiven.seafox.controller;

import android.util.SparseArray;

import com.aiven.seafox.controller.cmd.Cmd;
import com.aiven.seafox.controller.cmd.CmdSender;
import com.aiven.seafox.controller.cmd.observer.Observer;
import com.aiven.seafox.controller.intef.IMediator;
import com.aiven.seafox.controller.intef.IPanel;
import com.aiven.seafox.controller.log.Logs;
import com.aiven.seafox.controller.util.Tags;

import java.util.HashMap;

/**
 * @类名称   Mediator
 * @创建人   Aiven
 * @Email  aiven163@sina.com
 * @创建时间 2015-6-25 下午1:52:34
 * @类描述   中介者
 */
public class Mediator implements IMediator {

	private static IMediator mInstance;

	private HashMap<String, IPanel> uiLinkMap = new HashMap<>();
	private SparseArray<HashMap<String, Observer>> cmdLinkMap = new SparseArray<>();

	private Mediator() {
	}

	public static IMediator getInstance() {
		if (mInstance == null) {
			mInstance = new Mediator();
			mInstance.init();
		}
		return mInstance;
	}

	public void init() {
		uiLinkMap = new HashMap<>();
		cmdLinkMap = new SparseArray<HashMap<String, Observer>>();
	}

	@Override
	public void registPanel(IPanel panel) {
		Logs.logV(Tags.TAG_FRAME_INIT, "regist Panel name --> " + panel.getPanelName());
		if (uiLinkMap == null) {
			uiLinkMap = new HashMap<>();
		}
		uiLinkMap.put(panel.getPanelName(), panel);
		int[] ids = panel.registReceiveCmdIds();
		registCmds(ids, panel.getPanelName());
	}

	@Override
	public void removePanel(String panelName) {
		if (uiLinkMap != null) {
			IPanel Panel = uiLinkMap.get(panelName);
			if (Panel != null) {
				Logs.logV(Tags.TAG_FRAME_INIT, "remove Panel name --> " + Panel.getPanelName());
				removeCommands(Panel.registReceiveCmdIds(), panelName);
				Panel.recyclePanel();
			}
		}
	}

	private void removeCommands(int[] ids, String panelName) {
		if (ids != null && ids.length > 0) {
			Logs.logV(Tags.TAG_FRAME_INIT, "remove all cmds in Panel --> " + panelName);
			for (int i = 0; i < ids.length; i++) {
				synchronized (cmdLinkMap) {
					HashMap<String, Observer> map = cmdLinkMap.get(ids[i]);
					if (map != null) {
						map.remove(panelName);
					}
					if (map.size() < 1) {
						Logs.logV(Tags.TAG_FRAME_INIT, "remove one type cmds id= --> " + ids[i]);
						cmdLinkMap.remove(ids[i]);
					}
				}
			}
		}
	}

	private void registCmds(int[] ids, String panelName) {
		if (ids == null)
			return;
		Logs.logV(Tags.TAG_FRAME_INIT, "regist cmds...");
		for (int i = 0; i < ids.length; i++) {
			HashMap<String, Observer> map = cmdLinkMap.get(ids[i]);
			if (map == null) {
				map = new HashMap<String, Observer>();
				cmdLinkMap.put(ids[i], map);
			}
			map.put(panelName, new Observer());
		}
	}

	@Override
	public HashMap<String, Observer> getCmdObservers(int cmdId) {
		if (cmdLinkMap != null && cmdLinkMap.size() > 0) {
			synchronized (cmdLinkMap) {
				return cmdLinkMap.get(cmdId);
			}
		} else {
			return null;
		}
	}

	@Override
	public IPanel findPanelByName(String panelName) {
		if (uiLinkMap != null) {
			synchronized (uiLinkMap) {
				return uiLinkMap.get(panelName);
			}
		}
		return null;
	}

	@Override
	public void sendCommand(Cmd cmd) {
		if (cmd != null) {
			CmdSender.getIntance().sendCmd(cmd);
		}
	}

}
