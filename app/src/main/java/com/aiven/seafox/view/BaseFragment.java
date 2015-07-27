package com.aiven.seafox.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiven.seafox.controller.Mediator;
import com.aiven.seafox.controller.cmd.Cmd;
import com.aiven.seafox.controller.intef.IPanel;
import com.aiven.seafox.controller.log.Logs;
import com.aiven.seafox.controller.util.HandlerUtils;
import com.aiven.seafox.controller.util.Tags;
import com.aiven.seafox.controller.util.ToastUtil;
import com.aiven.seafox.controller.util.ViewUtil;

/**
 * 所有Fragment基类
 *
 * @author Aiven
 * @email aiven163@sina.com
 * @date 2014-11-19 下午2:32:08
 * @desc
 */
public abstract class BaseFragment extends Fragment implements IPanel {
    protected String panelName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logs.logV(Tags.TAG_FRAME_INIT, "fragment: onCreate...");
        createPanelName();
        Mediator.getInstance().registPanel(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int id = ViewUtil.initFragement(this);
        if (id != -1) {
            View view = inflater.inflate(id, container, false);
            ViewUtil.initWidget(this, view);
            Logs.logV(Tags.TAG_FRAME_INIT, "fragment: onCreateView...");
            return view;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
        HandlerUtils.getInstance().getUIHandler().post(new Runnable() {
            @Override
            public void run() {
                initData(savedInstanceState);
            }
        });
    }


    @Override
    public void onDestroy() {
        Mediator.getInstance().removePanel(panelName);
        recyclePanel();
        super.onDestroy();
    }

    /**
     * 获取Mediator中间件的名称,这里的一个Mediator就是一个Activity
     */
    @Override
    public String getPanelName() {
        return panelName;
    }

    @Override
    synchronized final public void createPanelName() {
        StringBuilder bd = new StringBuilder();
        bd.append(this.getClass().getSimpleName());
        bd.append(System.currentTimeMillis());
        bd.append((int) (Math.random() * 1000));
        panelName = bd.toString();
        bd = null;
    }

    @Override
    public void recyclePanel() {

    }

    public void sendCommand(Cmd cmd) {
        Mediator.getInstance().sendCommand(cmd);
    }

    @Override
    public void showToast(int resId) {
        ToastUtil.showToast(resId);
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showToast(msg);
    }

    @Override
    public void startActivity(Class<? extends Activity> cls, Bundle bd) {
        Intent intent = new Intent(getActivity(), cls);
        if (bd != null) {
            intent.putExtras(bd);
        }
        startActivity(intent);
    }

    @Override
    public void startActivityForResult(Class<? extends Activity> cls, int requestCode, Bundle bd) {
        Intent intent = new Intent(getActivity(), cls);
        if (bd != null) {
            intent.putExtras(bd);
        }
        startActivityForResult(intent, requestCode);
    }

}
