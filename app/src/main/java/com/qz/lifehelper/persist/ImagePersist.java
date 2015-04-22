package com.qz.lifehelper.persist;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.File;

/**
 * 这里持续化App中的图片
 */
@EBean
public class ImagePersist {

    public static final String IMAGE = "IMAGE";

    @Bean
    AppPersist appPersist;

    /**
     * 获取应用存取图片的文件夹
     */
    public File getImageDir() {
        File imageDir = new File(appPersist.getRootDir(), IMAGE);
        if (!imageDir.exists()) {
            imageDir.mkdir();
        }

        return imageDir;
    }
}
