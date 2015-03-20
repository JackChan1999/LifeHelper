package com.qz.lifehelper.event;

import com.qz.lifehelper.entity.City;

/**
* Created by kohoh on 15/3/18.
*/
public class GetCurrentCityEvent {

    static public GetCurrentCityEvent generateEvent(City currentCity) {
        GetCurrentCityEvent event = new GetCurrentCityEvent();
        event.currentCity = currentCity;
        return event;
    }

    public City currentCity;
}
