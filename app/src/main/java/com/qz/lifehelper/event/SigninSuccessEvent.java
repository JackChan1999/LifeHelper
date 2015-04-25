package com.qz.lifehelper.event;

import com.qz.lifehelper.entity.UserInfoBean;

/**
 * 当注册成功后AuthenticateActivity会发出该事件
 */
public class SigninSuccessEvent {

    public UserInfoBean userInfoBean;

    static final public SigninSuccessEvent generateEvent(UserInfoBean userInfoBean) {
        SigninSuccessEvent event = new SigninSuccessEvent();
        event.userInfoBean = userInfoBean;
        return event;
    }
}
