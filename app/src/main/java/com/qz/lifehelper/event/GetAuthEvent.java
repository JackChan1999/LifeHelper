package com.qz.lifehelper.event;

/**
 * Created by kohoh on 15/3/24.
 */
public class GetAuthEvent {

    public AUTH_STATE authState;

    public enum AUTH_STATE{LOGIN,LOGOUT}
    
	static public GetAuthEvent generateEvent(AUTH_STATE authState) {
        GetAuthEvent event = new GetAuthEvent();
        event.authState = authState;
        return event;
    }

}
