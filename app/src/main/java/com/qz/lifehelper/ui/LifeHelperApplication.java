package com.qz.lifehelper.ui;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
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

        //LeanCloud的初始化
        AVOSCloud.useAVCloudCN();
        // LeanCloud AVOS appid 和appKey.
        AVOSCloud.initialize(this,
                "70l90kzm53nplnu013ala0j8wipr594d36m5zuz94ukvmh5s",
                "lamrsuheyiaqcx4o7m3jaz4awaeukerit1mucnjwk7ibokfv");
    }
}
