package com.qz.lifehelper.ui;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.baidu.mapapi.SDKInitializer;
import com.qz.lifehelper.service.LeancloudConstant;

/**
 * 应用的全局appliaciton
 */
public class LifeHelperApplication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();

        //百度SDK的初始化
        SDKInitializer.initialize(this);

        //初始化Leancloud
        AVOSCloud.initialize(this, LeancloudConstant.LEANCLOUD_APP_ID, LeancloudConstant.LEANCLOUD_APP_KEY);
    }
}
