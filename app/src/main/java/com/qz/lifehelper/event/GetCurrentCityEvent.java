package com.qz.lifehelper.event;

import com.qz.lifehelper.entity.CityBean;

/**
* Created by kohoh on 15/3/18.
*/
public class GetCurrentCityEvent {

    static public GetCurrentCityEvent generateEvent(CityBean currentCityBean) {
        GetCurrentCityEvent event = new GetCurrentCityEvent();
        event.currentCityBean = currentCityBean;
        return event;
    }

    public CityBean currentCityBean;
}
