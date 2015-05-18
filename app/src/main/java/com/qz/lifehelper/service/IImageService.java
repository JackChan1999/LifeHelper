package com.qz.lifehelper.service;

import com.qz.lifehelper.entity.ImageBean;

import bolts.Task;

/**
 * 图片服务器接口
 */
public interface IImageService {

    /**
     * 将图片上传到七牛云服务器
     *
     * @param imageBean 里面应该包含图片本地的地址
     * @return 返回的应该包含图片的外链地址
     */
    public Task<ImageBean> uploadImageToQiniu(final ImageBean imageBean);

    /**
     * 将图片地址上传到leancloed
     *
     * @param imageBean 里面应该包含来图片的外链地址
     * @return 返回的应该包含leancloud的objectId
     */
    public Task<ImageBean> uploadImageToLeancloud(final ImageBean imageBean);

}
