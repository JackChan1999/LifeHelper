package com.qz.lifehelper.entity;

import java.io.Serializable;

/**
 * 分装的是P2P的分类信息
 */
public class P2PCategoryBean implements Serializable {
    public String title;
    public String content;
    public ImageBean imageBean;

    public P2PCategoryBean setContent(String content) {
        this.content = content;
        return this;
    }

    public P2PCategoryBean setImageBean(ImageBean imageBean) {
        this.imageBean = imageBean;
        return this;
    }

    public P2PCategoryBean setTitle(String category) {
        this.title = category;
        return this;
    }
}
