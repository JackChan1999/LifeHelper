package com.qz.lifehelper.event;

import com.qz.lifehelper.entity.City;

/**
* Created by kohoh on 15/3/18.
*/
public class CetCurrentCityEvent {

    static public CetCurrentCityEvent generateEvent(City currentCity) {
        CetCurrentCityEvent event = new CetCurrentCityEvent();
        event.currentCity = currentCity;
        return event;
    }

    public City currentCity;
}
