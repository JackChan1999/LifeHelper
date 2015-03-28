package com.qz.lifehelper.event;

/**
 * 用户身份识别事件
 *
 * 当成登录或登出时，会发出该事件
 */
public class GetAuthEvent {

    //用户登录状态 LOGIN为登录 LOGOUT为登出
    public AUTH_STATE authState;

    public enum AUTH_STATE{LOGIN,LOGOUT}
    
	static public GetAuthEvent generateEvent(AUTH_STATE authState) {
        GetAuthEvent event = new GetAuthEvent();
        event.authState = authState;
        return event;
    }

}
