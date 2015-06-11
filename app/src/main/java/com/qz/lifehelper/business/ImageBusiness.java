package com.qz.lifehelper.business;

import android.content.Context;

import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.service.IImageService;
import com.qz.lifehelper.service.ImageOnlineService_;
import com.qz.lifehelper.service.ImageOutlineService_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import bolts.Task;

/**
 * 处理图片的业务逻辑
 *
 * 图片的主要上传逻辑是
 * 1. 先上传图片到七牛云
 * 2. 将七牛云的图片链接上传到leancloud
 */
@EBean
public class ImageBusiness implements IImageService {

    @RootContext
    Context context;

    private IImageService imageService;

    @Bean
    AppBusiness appBusiness;

    /**
     * 设置服务器，是采用在线服务器还是离线服务器
     */
    @AfterInject
    void setService() {
        if (appBusiness.getDateSourceType().equals(AppBusiness.DATE_SOURCE.ONLINE)) {
            imageService = ImageOnlineService_.getInstance_(context);
        } else {
            imageService = ImageOutlineService_.getInstance_(context);
        }
    }

    /**
     * 上传图片到七牛云
     */
    @Override
    public Task<ImageBean> uploadImageToQiniu(ImageBean imageBean) {
        return imageService.uploadImageToQiniu(imageBean);
    }

    /**
     * 上传图片到Leancloud
     */
    @Override
    public Task<ImageBean> uploadImageToLeancloud(ImageBean imageBean) {
        return imageService.uploadImageToLeancloud(imageBean);
    }
}
