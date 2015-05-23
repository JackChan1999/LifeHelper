package com.qz.lifehelper.persist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.File;

/**
 * 这里持续化了全局App使用的数据
 */
@EBean
public class AppPersist {

    /**
     * 获取存储公用数据的文件夹
     */
    public File getRootDir() {
        File rootDir = new File(Environment.getExternalStorageDirectory(), "Lifehelper");
        if (!rootDir.exists()) {
            rootDir.mkdir();
        }
        return rootDir;
    }

    @RootContext
    Context context;

    static private final String APP_SP = "APP_SP";

    static private final String IS_ONLINE_SOURCE = "IS_ONLINE_SOURCE";


    private SharedPreferences getAppSP() {
        return context.getSharedPreferences(APP_SP, Context.MODE_PRIVATE);
    }

    /**
     * 是否使用在线数据
     */
    public boolean isOnlineSource() {
        SharedPreferences sharedPreferences = getAppSP();
        return sharedPreferences.getBoolean(IS_ONLINE_SOURCE, false);
    }

    /**
     * 设置是否使用在线数据
     */
    public void setIsOnlineSource(boolean isOnlineSource) {
        SharedPreferences sharedPreferences = getAppSP();
        sharedPreferences.edit().putBoolean(IS_ONLINE_SOURCE, isOnlineSource).commit();
    }

    static private final String IS_FIRST_OPEN = "IS_FIRST_OPEN";

    /**
     * 判断是否是第一次打开应用
     */
    public boolean isFirstOpne() {
        SharedPreferences sharedPreferences = getAppSP();
        return sharedPreferences.getBoolean(IS_FIRST_OPEN, true);
    }

    /**
     * 设置是否是第一次打开应用
     */
    public void setIsFirstOpen(boolean isFirstOpen) {
        SharedPreferences sharedPreferences = getAppSP();
        sharedPreferences.edit().putBoolean(IS_FIRST_OPEN, isFirstOpen).commit();
    }
}
