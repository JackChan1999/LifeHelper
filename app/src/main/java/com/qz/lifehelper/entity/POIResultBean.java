package com.qz.lifehelper.entity;

import java.util.Date;

/**
 * 该类分装了POI搜索结果的信息
 */
public class POIResultBean {
    public String title;
    public String address;
    public String tel;
    public String detail;
    public String id;
    public ImageBean imageBean;
    public POICategoryBean poiCategoryBean;
    public UserInfoBean userInfoBean;
    public Date createdAt;
    public TYPE type;
    public CityBean cityBean;

    public enum TYPE {
        BAIDU,
        LEANCLOUD
    }

    public POIResultBean setImageBean(ImageBean imageBean) {
        this.imageBean = imageBean;
        return this;
    }

    public POIResultBean setTitle(String title) {
        this.title = title;
        return this;
    }

    public POIResultBean setAddress(String address) {
        this.address = address;
        return this;
    }

    public POIResultBean setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public POIResultBean setDetail(String detail) {
        this.detail = detail;
        return this;
    }

    public POIResultBean setId(String id) {
        this.id = id;
        return this;
    }

    public POIResultBean setPoiCategoryBean(POICategoryBean poiCategoryBean) {
        this.poiCategoryBean = poiCategoryBean;
        return this;
    }

    public POIResultBean setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
        return this;
    }

    public POIResultBean setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public POIResultBean setType(TYPE type) {
        this.type = type;
        return this;
    }

    public POIResultBean setCityBean(CityBean cityBean) {
        this.cityBean = cityBean;
        return this;
    }
}
