package com.qz.lifehelper.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 分装了P2P结果信息
 */
public class P2PItemBean implements Serializable {

    public String title;
    public String tel;
    public String detail;
    public String address;
    public String id;
    public String price;
    public ImageBean imageBean;
    public P2PCategoryBean categoryBean;
    public UserInfoBean userInfoBean;
    public Date createdAt;

    public P2PItemBean setCategoryBean(P2PCategoryBean categoryBean) {
        this.categoryBean = categoryBean;
        return this;
    }

    public P2PItemBean setTitle(String title) {
        this.title = title;
        return this;
    }

    public P2PItemBean setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public P2PItemBean setDetail(String detail) {
        this.detail = detail;
        return this;
    }

    public P2PItemBean setAddress(String address) {
        this.address = address;
        return this;
    }

    public P2PItemBean setId(String id) {
        this.id = id;
        return this;
    }

    public P2PItemBean setPrice(String price) {
        this.price = price;
        return this;
    }

    public P2PItemBean setImageBean(ImageBean imageBean) {
        this.imageBean = imageBean;
        return this;
    }

    public P2PItemBean setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
        return this;
    }

    public P2PItemBean setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
