package com.qz.lifehelper.event;

import com.qz.lifehelper.entity.CityBean;

/**
 * 当前选中城市发生改变事件
 * <p/>
 * 当应用的当前选中城市发生改变时，会发送该事件
 */
public class GetCurrentCityEvent {

    static public GetCurrentCityEvent generateEvent(CityBean currentCityBean) {
        GetCurrentCityEvent event = new GetCurrentCityEvent();
        event.currentCityBean = currentCityBean;
        return event;
    }

    public CityBean currentCityBean;
}
