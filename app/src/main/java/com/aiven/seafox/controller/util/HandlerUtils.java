package com.aiven.seafox.controller.util;

import android.os.Handler;
import android.os.Looper;

public class HandlerUtils {

	private static HandlerUtils mIntance;
	private static Handler cmdHandler;
	private static Handler uiHandler;
	
	private HandlerUtils(){}
	
	public static HandlerUtils getInstance(){
		if(mIntance==null){
			mIntance=new HandlerUtils();
		}
		return mIntance;
	}
	
	public Handler getCmdHandler(){
		if(cmdHandler==null){
			cmdHandler=new Handler(Looper.getMainLooper());
		}
		return cmdHandler;
	}
	
	public Handler getUIHandler(){
		if(uiHandler==null){
			uiHandler=new Handler(Looper.getMainLooper());
		}
		return uiHandler;
	}
	
}
