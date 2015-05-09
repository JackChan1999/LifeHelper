package com.qz.lifehelper.entity;

/**
 * Created by kohoh on 15/3/30.
 */
public class UserInfoBean {
    public String userName;
    public ImageBean userIcon;
    public String id;

    static public final UserInfoBean generateBean(String userName, ImageBean userIcon, String id) {
        UserInfoBean userInfoBean = new UserInfoBean();

        userInfoBean.userIcon = userIcon;
        userInfoBean.userName = userName;
        userInfoBean.id = id;

        return userInfoBean;
    }
}
