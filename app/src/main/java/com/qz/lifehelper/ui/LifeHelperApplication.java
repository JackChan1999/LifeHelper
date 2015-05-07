package com.qz.lifehelper.ui;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.baidu.mapapi.SDKInitializer;

/**
 * 应用的全局appliaciton
 */
public class LifeHelperApplication extends Application {

    static private final String LEANCLOUD_APP_ID = "e00i97sdq8b33w61q5orbajd8clronr6xatdrfkssbysezx1";
    static private final String LEANCLOUD_APP_KEY = "ffl9odot7h406njnegopcpw4h5gnwhscpkarfphb3m964zwy";

    @Override
    public void onCreate() {
        super.onCreate();

        //百度SDK的初始化
        SDKInitializer.initialize(this);

        //初始化Leancloud
        AVOSCloud.initialize(this, LEANCLOUD_APP_ID, LEANCLOUD_APP_KEY);
    }
}
