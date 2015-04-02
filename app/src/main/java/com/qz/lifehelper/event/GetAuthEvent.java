package com.qz.lifehelper.event;

import com.qz.lifehelper.entity.UserInfoBean;

/**
 * 用户身份识别事件
 * <p/>
 * 当成登录或登出时，会发出该事件
 */
public class GetAuthEvent {

    // 用户登录状态 LOGIN为登录 LOGOUT为登出
    public AUTH_STATE authState;

    public enum AUTH_STATE {
        LOGIN,
        LOGOUT
    }

    public UserInfoBean userInfoBean;

    static public GetAuthEvent generateEvent(AUTH_STATE authState) {
        GetAuthEvent event = new GetAuthEvent();
        event.authState = authState;
        return event;
    }

    static public GetAuthEvent generateEvent(UserInfoBean userInfoBean) {
        GetAuthEvent event = new GetAuthEvent();
        event.authState = AUTH_STATE.LOGIN;
        event.userInfoBean = userInfoBean;
        return event;
    }

}
