package com.qz.lifehelper.event;

import com.qz.lifehelper.entity.CityBean;

/**
 * 获取定位信息事件
 * <p/>
 * 当成功定位到设备当前所在位置时，会发送该事件
 */
public class GetCurrentLocationCityEvent {

    public CityBean currentLocationCityBean;

    static public GetCurrentLocationCityEvent generateEvent(CityBean currentLocationCityBean) {
        GetCurrentLocationCityEvent event = new GetCurrentLocationCityEvent();
        event.currentLocationCityBean = currentLocationCityBean;
        return event;
    }

}
