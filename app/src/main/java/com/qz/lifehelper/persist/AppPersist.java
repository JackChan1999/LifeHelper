package com.qz.lifehelper.persist;

import android.os.Environment;

import org.androidannotations.annotations.EBean;

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

}
