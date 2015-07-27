package com.aiven.seafox.controller.intef;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * @author Aiven
 * @email aiven163@sina.com
 * @date 2015-6-21 下午3:38:37
 * @project app框架
 * @desc 界面底层接口
 */
public interface IPanel {

    /**
     * 初始化界面控件
     *
     * @param view
     * @param onSaveInstance
     */
    public void initView(View view, Bundle onSaveInstance);

    /**
     * 初始化数据
     * @param onSaveInstance
     */
    public void initData(Bundle onSaveInstance);

    /**
     * 获取界面名称
     *
     * @return
     */
    public String getPanelName();

    /**
     * 构建界面名称
     */
    public void createPanelName();

    /**
     * 注册当前UI所需要接收的命令
     *
     * @return
     */
    public int[] registReceiveCmdIds();

    /**
     * 命令接收入口
     *
     * @param cmdId
     * @param data
     */
    public void onHandCmd(int cmdId, Object data);

    /**
     * 任务返回
     * eventHandle
     *
     * @param eventId
     * @param data
     */
    public void eventHandle(int eventId, Object data);

    /**
     * 失误失败错误
     * eventError
     *
     * @param eventId  任务ID号
     * @param errorCode  错误码
     * @param reason   错误原因
     */
    public void eventError(int eventId, int errorCode, String reason);

    public void showToast(int resId);

    public void showToast(String msg);

    /**
     * 界面跳转
     * startActivity
     *
     * @param cls
     * @param bd
     */
    public void startActivity(Class<? extends Activity> cls, Bundle bd);

    /**
     * 带返回的界面跳转
     * startActivityForResult
     *
     * @param cls
     * @param requestCode
     * @param bd
     */
    public void startActivityForResult(Class<? extends Activity> cls, int requestCode, Bundle bd);

    /**
     * 回收数据
     */
    public void recyclePanel();

}
