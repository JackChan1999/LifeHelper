package com.qz.lifehelper.event;

import com.qz.lifehelper.entity.AirPortBean;

/**
 * Created by kohoh on 15/4/1.
 */
public class GetAirportEvent {
    public AirPortBean airPortBean;

    static public GetAirportEvent generateEvent(AirPortBean airPortBean) {
        GetAirportEvent event = new GetAirportEvent();
        event.airPortBean = airPortBean;
        return event;
    }
}
