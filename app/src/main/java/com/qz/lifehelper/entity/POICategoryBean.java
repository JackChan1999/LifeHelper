package com.qz.lifehelper.entity;

/**
 * 改类封装的是POI的分类信息
 */
public class POICategoryBean {

    public String categotyName;

    public static POICategoryBean generate(String categotyName) {
        POICategoryBean categoryBean = new POICategoryBean();
        categoryBean.categotyName = categotyName;
        return categoryBean;
    }
}
