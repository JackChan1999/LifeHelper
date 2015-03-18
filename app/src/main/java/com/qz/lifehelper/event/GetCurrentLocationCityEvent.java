package com.qz.lifehelper.event;

import com.qz.lifehelper.entity.City;

/**
 * 获取定位信息事件
 */
public class GetCurrentLocationCityEvent {

    public City currentLocationCity;

    static public GetCurrentLocationCityEvent generateEvent(City currentLocationCity) {
        GetCurrentLocationCityEvent event = new GetCurrentLocationCityEvent();
        event.currentLocationCity = currentLocationCity;
        return event;
    }

}
