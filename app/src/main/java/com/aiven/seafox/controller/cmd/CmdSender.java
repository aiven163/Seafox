package com.aiven.seafox.controller.cmd;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import android.text.TextUtils;

import com.aiven.seafox.controller.Mediator;
import com.aiven.seafox.controller.cmd.observer.Observer;
import com.aiven.seafox.controller.intef.IPanel;

/**
 * 命令分发执行器
 *
 * @author Aiven
 * @email aiven163@sina.com
 * @date 2015-6-21 下午5:51:17
 * @project app框架
 * @desc
 */
public class CmdSender extends Thread {

    private static CmdSender mIntance;
    private static final int MAX_COUNT = 5;
    private ArrayBlockingQueue<Cmd> cmdQue;

    private CmdSender() {
    }

    public static CmdSender getIntance() {
        if (mIntance == null) {
            mIntance = new CmdSender();
            mIntance.init();
        }
        return mIntance;
    }

    public void init() {
        cmdQue = new ArrayBlockingQueue<>(MAX_COUNT);
        start();
    }

    @Override
    public void run() {
        while (true) {
            if (cmdQue == null) {
                cmdQue = new ArrayBlockingQueue<>(MAX_COUNT);
            }
            synchronized (cmdQue) {
                try {
                    Cmd cmd = cmdQue.take();
                    if (cmd != null) {
                        HashMap<String, Observer> obs = Mediator.getInstance().getCmdObservers(cmd.cmdId);
                        if (obs != null && obs.size() > 0) {
                            Observer dstObs;
                            IPanel Panel;
                            if (TextUtils.isEmpty(cmd.getRecievePanelName())) {
                                for (String key : obs.keySet()) {
                                    dstObs = obs.get(key);
                                    Panel = Mediator.getInstance().findPanelByName(key);
                                    if (dstObs != null && Panel != null) {
                                        dstObs.findCmd(Panel, cmd);
                                    }
                                }
                            } else {//指定发送给某一个Pannel
                                dstObs = obs.get(cmd.getRecievePanelName());
                                Panel = Mediator.getInstance().findPanelByName(cmd.getRecievePanelName());
                                if (dstObs != null && Panel != null) {
                                    dstObs.findCmd(Panel, cmd);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendCmd(Cmd cmd) {
        if (cmd == null) {
            return;
        }
        if (cmdQue == null) {
            cmdQue = new ArrayBlockingQueue<>(MAX_COUNT);
        }
        cmdQue.add(cmd);
    }
}
