package com.qz.lifehelper.event;

import com.qz.lifehelper.entity.ChooseCityListItemData;

import java.util.List;

/**
 * 刷新ChooseCity列表的事件
 */
public class NewCityListEvent {

    public List<ChooseCityListItemData> cityListItemDatas;

    static public NewCityListEvent generateEvent(List<ChooseCityListItemData> cityListItemDatas) {
        NewCityListEvent event = new NewCityListEvent();
        event.cityListItemDatas = cityListItemDatas;
        return event;
    }

}
