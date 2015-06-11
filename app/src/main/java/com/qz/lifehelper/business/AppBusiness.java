package com.qz.lifehelper.business;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.qz.lifehelper.persist.AppPersist;

import org.androidannotations.annotations.Bean;
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

    /**
     * 数据来源
     */
    public enum DATE_SOURCE {
        //离线数据，在线数据
        OUTLINE, ONLINE,
    }

    @Bean
    AppPersist appPersist;

    /**
     * 获取应用的数据来源，是采用离线数据还是在线数据
     */
    public DATE_SOURCE getDateSourceType() {
        if (appPersist.isOnlineSource()) {
            return DATE_SOURCE.ONLINE;
        } else {
            return DATE_SOURCE.OUTLINE;
        }
    }

    /**
     * 设置应用的数据来源，是采用离线数据还是在线数据
     */
    public void setDateSourceType(DATE_SOURCE dateSourceType) {
        if (dateSourceType.equals(DATE_SOURCE.ONLINE)) {
            appPersist.setIsOnlineSource(true);
        } else {
            appPersist.setIsOnlineSource(false);
        }
    }

    /**
     * 判断是否是第一次打开APP
     */
    public boolean isFirstOpenApp() {
        return appPersist.isFirstOpne();
    }

    /**
     * 设置是否是第一次打开APP
     */
    public void setIsFirstOpenApp(boolean isFirstOpenApp) {
        appPersist.setIsFirstOpen(isFirstOpenApp);
    }
}
