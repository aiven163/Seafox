package com.aiven.seafox.controller.intef;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * @author Aiven
 * @email aiven163@sina.com
 * @date 2015-6-21 pm 3:38:37
 * @desc the acivity or Fragment base pannel interface
 */
public interface IPanel {

    /**
     * Initialize the all view component
     *
     * @param view
     * @param onSaveInstance
     */
    public void initView(View view, Bundle onSaveInstance);

    /**
     * Initialize the data
     * @param onSaveInstance
     */
    public void initData(Bundle onSaveInstance);

    /**
     * get the pannel name
     *
     * @return
     */
    public String getPanelName();

    /**
     * create the pannel name
     */
    public void createPanelName();

    /**
     * Register the current UI needs to receive the order ids
     *
     * @return
     */
    public int[] registReceiveCmdIds();

    /**
     * the callback of command
     *
     * @param cmdId
     * @param data
     */
    public void onHandCmd(int cmdId, Object data);

    /**
     * the callback or event
     * eventHandle
     *
     * @param eventId
     * @param data
     */
    public void eventHandle(int eventId, Object data);

    /**
     * the callback or exception
     * eventError
     *
     * @param eventId  task id number
     * @param errorCode  error code
     * @param reason   the reason of the error
     */
    public void eventError(int eventId, int errorCode, String reason);

    public void showToast(int resId);

    public void showToast(String msg);

    /**
     * jump activity
     * startActivity
     *
     * @param cls
     * @param bd
     */
    public void startActivity(Class<? extends Activity> cls, Bundle bd);

    /**
     * jump activity with result data
     * startActivityForResult
     *
     * @param cls
     * @param requestCode
     * @param bd
     */
    public void startActivityForResult(Class<? extends Activity> cls, int requestCode, Bundle bd);

    /**
     * recycling the data
     */
    public void recyclePanel();

}
