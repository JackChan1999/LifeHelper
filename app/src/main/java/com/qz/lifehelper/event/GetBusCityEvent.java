package com.qz.lifehelper.event;

import com.qz.lifehelper.entity.CityBean;

/**
 * Created by kohoh on 15/4/13.
 */
public class GetBusCityEvent {
    public CityBean cityBean;

    static final public GetBusCityEvent generateEvent(CityBean cityBean) {
        GetBusCityEvent event = new GetBusCityEvent();
        event.cityBean = cityBean;
        return event;
    }
}
