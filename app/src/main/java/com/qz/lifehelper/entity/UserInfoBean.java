package com.qz.lifehelper.entity;

/**
 * Created by kohoh on 15/3/30.
 */
public class UserInfoBean {
    public String userName;
    public ImageBean userIcon;

    static public final UserInfoBean generateBean(String userName, ImageBean userIcon) {
        UserInfoBean userInfoBean = new UserInfoBean();

        userInfoBean.userIcon = userIcon;
        userInfoBean.userName = userName;

        return userInfoBean;
    }
}
