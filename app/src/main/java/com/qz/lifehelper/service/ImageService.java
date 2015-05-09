package com.qz.lifehelper.service;

import com.avos.avoscloud.AVObject;
import com.qz.lifehelper.entity.ImageBean;

import org.androidannotations.annotations.EBean;

import java.util.concurrent.Callable;

import bolts.Task;

/**
 * 图片服务器
 */
@EBean
public class ImageService {

    public Task<ImageBean> uploadImage(final ImageBean imageBean) {
        return Task.callInBackground(new Callable<ImageBean>() {
            @Override
            public ImageBean call() throws Exception {
                AVObject imageObject = convertToImageObject(imageBean);
                imageObject.save();
                return convertToImageBean(imageObject);
            }
        });
    }

    private ImageBean convertToImageBean(AVObject imageObject) {
        String src = imageObject.getString(LeancloudConstant.IMAGE_COLUME);
        String id = imageObject.getObjectId();
        return ImageBean.generateImage(src, ImageBean.ImageType.QINIUYUN, id);
    }

    private AVObject convertToImageObject(ImageBean imageBean) {
        AVObject imageObject = new AVObject(LeancloudConstant.IMAGE_TABLE);
        imageObject.put(LeancloudConstant.IMAGE_COLUME, imageBean.imageSrc);
        imageObject.setObjectId(imageBean.id);
        return imageObject;
    }

}
