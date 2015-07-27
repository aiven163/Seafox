package com.aiven.seafox.controller.log.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import android.os.Environment;

import com.aiven.seafox.controller.log.LogConfig;
import com.aiven.seafox.controller.log.model.LogMode;
import com.aiven.seafox.controller.log.util.LogTimeUtils;


/**
 * @author Aiven
 * @date 2014-6-3 下午6:11:55
 * @email aiven163@sina.com
 * @Description 日志写入文件工具类
 */
public class LogWriteUtils extends Thread {

	private PrintWriter mOs;
	private LogCheckListListener listener;

	public LogWriteUtils(LogCheckListListener listener) {
		this.listener = listener;
	}

	@Override
	public void run() {
		try {
			while (true) {
				LogMode mode = listener.backNextLog();
				writeLog(mode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	synchronized private void writeLog(LogMode mode) {
		if (mode == null)
			return;
		try {
			if (mOs == null || !checkFile()) {
				initOs();
			}
			if (mOs != null) {
				mOs.println(mode.getTime() + "  " + mode.getTag() + "  " + mode.getMsg());
				mOs.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			mOs = null;
		} finally {
			mode = null;
		}
	}

	private void initOs() {
		String filePath = LogConfig.SAVE_PATH + LogTimeUtils.getInstance().getData() + ".txt";
		File f = new File(filePath);
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			try {
				if (!f.exists()) {
					try {
						f.getParentFile().mkdirs();
					} catch (Exception e) {
						e.printStackTrace();
					}
					f.createNewFile();
				}
				mOs = new PrintWriter(new FileOutputStream(f, true));
			} catch (Exception e) {
				e.printStackTrace();
				mOs = null;
			}
		} else {
			mOs = null;
		}
	}

	private boolean checkFile() {
		String filePath = LogConfig.SAVE_PATH + LogTimeUtils.getInstance().getData() + ".txt";
		File f = new File(filePath);
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			try {
				if (!f.exists()) {
					return false;
				} else {
					return true;
				}
			} catch (Exception e) {
				return false;
			} finally {
				f = null;
			}
		} else {
			f = null;
		}
		return false;
	}
	public void startEngine() {
		start();
	}
}
