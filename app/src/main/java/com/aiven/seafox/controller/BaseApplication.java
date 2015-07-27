package com.aiven.seafox.controller;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.Configuration;

import java.io.File;

public class BaseApplication extends Application {

    public static BaseApplication mInstance;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mInstance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mInstance = this;
    }

    @SuppressLint("NewApi")
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        mInstance = this;
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }

    /**
     * 返回数据存储根目录
     *
     * @return
     */
    public String getBaseCacheDirPath() {
        File file = getExternalCacheDir();
        if (file == null || !file.exists()) {
            file=getCacheDir();
            if(file!=null && file.exists()){
                return file.getAbsolutePath();
            }else{
                return "";
            }
        }
        return file.getAbsolutePath();
    }

}
