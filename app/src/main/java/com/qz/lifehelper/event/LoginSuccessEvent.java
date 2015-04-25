package com.qz.lifehelper.event;

import com.qz.lifehelper.entity.UserInfoBean;

/**
 * 当登陆成功后AuthenticateActivity会发出该事件
 */
public class LoginSuccessEvent {

    public UserInfoBean userInfoBean;

    static final public LoginSuccessEvent generateEvent(UserInfoBean userInfoBean) {
        LoginSuccessEvent event = new LoginSuccessEvent();
        event.userInfoBean = userInfoBean;
        return event;
    }
}
