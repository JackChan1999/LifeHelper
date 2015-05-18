package com.qz.lifehelper.business;

import android.content.Context;

import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.service.IImageService;
import com.qz.lifehelper.service.ImageOnlineService_;
import com.qz.lifehelper.service.ImageOutlineService_;
import com.qz.lifehelper.ui.AppProfile;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import bolts.Task;

/**
 * 处理图片的业务逻辑
 */
@EBean
public class ImageBusiness implements IImageService {

    @RootContext
    Context context;

    private IImageService imageService;

    @AfterInject
    void setService() {
        if (AppProfile.dateSource.equals(AppProfile.DATE_SOURCE.ONLINE)) {
            imageService = ImageOnlineService_.getInstance_(context);
        } else {
            imageService = ImageOutlineService_.getInstance_(context);
        }
    }

    @Override
    public Task<ImageBean> uploadImageToQiniu(ImageBean imageBean) {
        return imageService.uploadImageToQiniu(imageBean);
    }

    @Override
    public Task<ImageBean> uploadImageToLeancloud(ImageBean imageBean) {
        return imageService.uploadImageToLeancloud(imageBean);
    }
}
