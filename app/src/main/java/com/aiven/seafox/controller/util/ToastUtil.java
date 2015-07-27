package com.aiven.seafox.controller.util;


import android.content.Context;
import android.widget.Toast;

import com.aiven.seafox.controller.BaseApplication;

/**
 * 
 * @类名称   ToastUtil  
 * @创建人   Aiven
 * @Email  aiven163@sina.com
 * @创建时间 2015-6-25 下午2:06:06 
 * @类描述  Toast工具类
 */
public class ToastUtil {

	public static void showToast(String msg) {
		if (BaseApplication.getInstance() != null) {
			Toast.makeText(BaseApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
		}
	}

	public static void showToast(int resId) {
		if (BaseApplication.getInstance() != null) {
			Toast.makeText(BaseApplication.getInstance(), resId, Toast.LENGTH_SHORT).show();
		}
	}

	public static void showToast(Context context, String msg) {
		if (context != null) {
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
	}

	public static void showToast(Context context, int resId) {
		if (context != null) {
			Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
		}
	}

}
