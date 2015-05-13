package com.qz.lifehelper.entity;

/**
 * 该类封装的是图片信息
 */
public class ImageBean {

    /**
     * 图片来源的类型
     */
    public enum ImageType {
        //本地缓存图片
        OUTLINE
        //七牛云图片
        , QINIUYUN
    }

    /**
     * 图片地址
     */
    public String imageSrc;
    /**
     * 图片来源类型
     */
    public ImageType imageType;

    public String id;

    public static final ImageBean generateImage(String imageSrc, ImageType imageType) {
        ImageBean imageBean = new ImageBean();
        imageBean.imageSrc = imageSrc;
        imageBean.imageType = imageType;
        return imageBean;
    }

    public static final ImageBean generateImage(String imageSrc, ImageType imageType, String id) {
        ImageBean imageBean = new ImageBean();
        imageBean.imageSrc = imageSrc;
        imageBean.imageType = imageType;
        imageBean.id = id;
        return imageBean;
    }
}
