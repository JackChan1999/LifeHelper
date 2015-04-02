package com.qz.lifehelper.event;

import com.qz.lifehelper.entity.AirportBean;

/**
 * 选中机场时发出该Event
 * <p/>
 * ChooseAirportFragmnet会发送该Event
 */
public class GetAirportEvent {
    /**
     * 选中的机场
     */
    public AirportBean airportBean;

    static public GetAirportEvent generateEvent(AirportBean airportBean) {
        GetAirportEvent event = new GetAirportEvent();
        event.airportBean = airportBean;
        return event;
    }
}
