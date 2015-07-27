package com.aiven.seafox.controller.util;

import java.lang.reflect.Field;

import android.view.View;

import com.aiven.seafox.controller.intef.ELayout;
import com.aiven.seafox.controller.intef.EWidget;
import com.aiven.seafox.controller.log.LogConfig;
import com.aiven.seafox.controller.log.Logs;
import com.aiven.seafox.view.BaseActivity;
import com.aiven.seafox.view.BaseFragment;


/**
 * @author Aiven
 * @date 2013-5-20
 * @email aiven163@sina.com
 */
public class ViewUtil {

	public static int initWindow(BaseActivity activity) throws Exception {
		try {
			Class<? extends BaseActivity> cls = activity.getClass();
			ELayout eView = cls.getAnnotation(ELayout.class);
			if (eView != null) {
				int id = eView.Layout();
				activity.setContentView(id);
				return id;
			} else {
				throw new Exception("XML File Not Found!");
			}
		} catch (Exception e) {
			if (LogConfig.Debug)
				Logs.logE(e);
			throw new Exception("XML File Not Found!");
		}
	}

	public static int initFragement(BaseFragment fragment) {
		try {
			Class<? extends BaseFragment> cls = fragment.getClass();
			ELayout eView = cls.getAnnotation(ELayout.class);
			if (eView != null) {
				int id = eView.Layout();
				return id;
			} else {
				return -1;
			}
		} catch (Exception e) {
			return -1;
		}
	}

	public static void initWidget(BaseActivity activity) {
		Field[] fields = activity.getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				EWidget viewInject = field.getAnnotation(EWidget.class);
				if (viewInject != null) {
					int viewId = viewInject.id();
					int parent = viewInject.parentId();
					try {
						field.setAccessible(true);
						if (parent == 0) {
							field.set(activity, activity.findViewById(viewId));
						} else {
							field.set(activity, activity.findViewById(parent).findViewById(viewId));
						}
					} catch (Exception e) {
						if (LogConfig.Debug)
							Logs.logE(e);
					}
				}
			}
		}
	}

	public static void initWidget(BaseFragment fragment, View fragmentRootView) {
		Field[] fields = fragment.getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				EWidget viewInject = field.getAnnotation(EWidget.class);
				if (viewInject != null) {
					int viewId = viewInject.id();
					int parent = viewInject.parentId();
					try {
						field.setAccessible(true);
						if (parent == 0) {
							field.set(fragment, fragmentRootView.findViewById(viewId));
						} else {
							field.set(fragment, fragmentRootView.findViewById(parent).findViewById(viewId));
						}
					} catch (Exception e) {
						if (LogConfig.Debug)
							Logs.logE(e);
					}
				}
			}
		}
	}

}
