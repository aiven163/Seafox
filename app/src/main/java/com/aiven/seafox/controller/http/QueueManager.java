package com.aiven.seafox.controller.http;

import com.aiven.seafox.controller.BaseApplication;
import com.aiven.seafox.controller.http.volley.RequestQueue;
import com.aiven.seafox.controller.http.volley.toolbox.Volley;

public class QueueManager {

	private static QueueManager mInstance;
	private RequestQueue mHttpQueue;

	private QueueManager() {
	}

	public static QueueManager getInstance() {
		if (mInstance == null) {
			mInstance = new QueueManager();
		}
		return mInstance;
	}

	public RequestQueue getHttpQueue() {
		if (mHttpQueue == null) {
			mHttpQueue = Volley.newRequestQueue(BaseApplication.getInstance());
		}
		return mHttpQueue;
	}

}
