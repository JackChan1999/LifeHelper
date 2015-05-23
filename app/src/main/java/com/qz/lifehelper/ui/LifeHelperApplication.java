package com.qz.lifehelper.ui;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.avos.avoscloud.AVOSCloud;
import com.baidu.mapapi.SDKInitializer;
import com.qz.lifehelper.business.AppBusiness;
import com.qz.lifehelper.dao.DaoMaster;
import com.qz.lifehelper.service.LeancloudConstant;
import com.qz.lifehelper.service.OutlineServiceConstant;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EApplication;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 应用的全局appliaciton
 */
@EApplication
public class LifeHelperApplication extends Application {

    @Bean
    AppBusiness appBusiness;

    @Override
    public void onCreate() {
        super.onCreate();

        //百度SDK的初始化
        SDKInitializer.initialize(this);

        //初始化Leancloud
        AVOSCloud.initialize(this, LeancloudConstant.LEANCLOUD_APP_ID, LeancloudConstant.LEANCLOUD_APP_KEY);


        if (appBusiness.isFirstOpenApp()) {
            appBusiness.setIsFirstOpenApp(false);
            try {
                InputStream dbIs = this.getAssets().open("db");
                DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, OutlineServiceConstant.BD_NAME, null);
                SQLiteDatabase database = devOpenHelper.getWritableDatabase();
                File dbFile = new File(database.getPath());
                dbFile.deleteOnExit();
                FileOutputStream dbOs = new FileOutputStream(dbFile);
                IOUtils.copy(dbIs, dbOs);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
