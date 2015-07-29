package com.aiven.seafox.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aiven.seafox.controller.Mediator;
import com.aiven.seafox.controller.cmd.Cmd;
import com.aiven.seafox.controller.intef.IPanel;
import com.aiven.seafox.controller.log.Logs;
import com.aiven.seafox.controller.util.HandlerUtils;
import com.aiven.seafox.controller.util.Tags;
import com.aiven.seafox.controller.util.ToastUtil;
import com.aiven.seafox.controller.util.ViewUtil;

/**
 * 所有Activity基类
 *
 * @author Aiven
 * @email aiven163@sina.com
 * @date 2014-11-19 下午2:32:08
 */
public abstract class BaseActivity extends AppCompatActivity implements IPanel {
    private View rootView = null;
    protected String panelName;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        try {
            createPanelName();
            ViewUtil.initWindow(this);
            rootView = getWindow().getDecorView();
            Mediator.getInstance().registPanel(this);
            ViewUtil.initWidget(BaseActivity.this);
            initView(rootView, arg0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        HandlerUtils.getInstance().getUIHandler().post(new Runnable() {
            @Override
            public void run() {
                initData(savedInstanceState);
            }
        });
    }

    @Override
    public String getPanelName() {
        return panelName;
    }

    @Override
    public void createPanelName() {
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

    @Override
    public void finish() {
        Logs.logV(Tags.TAG_FRAME_INIT, "finish activity ");
        Mediator.getInstance().removePanel(panelName);
        super.finish();
    }

    public void sendCommand(@NonNull Cmd cmd) {
        Mediator.getInstance().sendCommand(cmd);
    }

    @Override
    public void showToast(@StringRes int resId) {
        ToastUtil.showToast(this, resId);
    }

    @Override
    public void showToast(@NonNull String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void startActivity(Class<? extends Activity> cls, Bundle bd) {
        Intent intent = new Intent(this, cls);
        if (bd != null) {
            intent.putExtras(bd);
        }
        startActivity(intent);
    }

    @Override
    public void startActivityForResult(Class<? extends Activity> cls, int requestCode, Bundle bd) {
        Intent intent = new Intent(this, cls);
        if (bd != null) {
            intent.putExtras(bd);
        }
        startActivityForResult(intent, requestCode);
    }

}
