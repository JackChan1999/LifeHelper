package com.qz.lifehelper.business;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * 这里实现的是和App相关的业务逻辑
 */

@EBean
public class AppBusiness {

    @RootContext
    Context context;

    /**
     * 获取当前应用的版本号
     */
    public String getVersionNumber() {
        PackageManager packageManager = context.getPackageManager();
        String version = "版本号：V1.0";
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo("com.qz.lifehelper", PackageManager.GET_ACTIVITIES);
            version = "版本号：" + packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
}
