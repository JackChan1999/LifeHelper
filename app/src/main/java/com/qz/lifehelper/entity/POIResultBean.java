package com.qz.lifehelper.entity;

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
}
