package com.qz.lifehelper.ui;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * 应用的全局appliaciton
 */
public class LifeHelperApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //百度SDK的初始化
        SDKInitializer.initialize(this);
    }
}
