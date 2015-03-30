package com.qz.lifehelper.entity;

/**
 * Created by kohoh on 15/3/30.
 */
public class UserInfoBean {
	public String userName;
	public String userIcon;

	static public final UserInfoBean generateBean(String userName, String userIcon) {
		UserInfoBean userInfoBean = new UserInfoBean();

		userInfoBean.userIcon = userIcon;
		userInfoBean.userName = userName;

		return userInfoBean;
	}
}
