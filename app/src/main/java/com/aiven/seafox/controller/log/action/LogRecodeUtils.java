package com.aiven.seafox.controller.log.action;

import com.aiven.seafox.controller.log.model.LogMode;

import java.util.concurrent.ArrayBlockingQueue;


/**
 * @author Aiven
 * @date 2014-6-3 下午6:11:10
 * @email aiven163@sina.com
 * @Description 日志记录工具类
 */
public class LogRecodeUtils implements LogCheckListListener {
	private static LogRecodeUtils mInstance;
	private ArrayBlockingQueue<LogMode> mLogsQueue;
	private LogWriteUtils mWriteUtils;

	private LogRecodeUtils() {
		mLogsQueue = new ArrayBlockingQueue<LogMode>(30);
	};

	public static LogRecodeUtils getInstance() {
		if (mInstance == null) {
			mInstance = new LogRecodeUtils();
			mInstance.mWriteUtils = new LogWriteUtils(mInstance);
			mInstance.mWriteUtils.startEngine();
		}
		return mInstance;
	}

	public void addLog(LogMode mode) {
		if (mLogsQueue == null) {
			mLogsQueue = new ArrayBlockingQueue<LogMode>(30);
		}
		mLogsQueue.add(mode);
		if (mWriteUtils == null) {
			mWriteUtils = new LogWriteUtils(this);
			mWriteUtils.startEngine();
		}
	}

	@Override
	public LogMode backNextLog() {
		if (mLogsQueue == null)
			return null;
		try {
			return mLogsQueue.take();
		} catch (InterruptedException e) {
			return null;
		}
	}
}
