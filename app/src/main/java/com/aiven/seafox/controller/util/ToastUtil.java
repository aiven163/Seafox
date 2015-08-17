package com.aiven.seafox.controller.util;


import android.content.Context;
import android.widget.Toast;

import com.aiven.seafox.controller.BaseApplication;

/**
 * 
 * @ClassName   ToastUtil
 * @Author   Aiven
 * @Email  aiven163@sina.com
 * @CreateTime 2015-6-25 PM 2:06:06
 * @Desc  Toast util
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
