package com.qz.lifehelper.ui;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by kohoh on 15/3/20.
 */
public class LifeHelperApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
    }
}
