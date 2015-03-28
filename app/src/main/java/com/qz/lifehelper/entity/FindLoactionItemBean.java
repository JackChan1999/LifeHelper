package com.qz.lifehelper.entity;

/**
 * 这是ChooseCity页面中，item为定位当前城市时对应的数据类型
 */
public class FindLoactionItemBean implements ChooseCityListItemData {

    public String cityName;
    public ChooseCityListItemType type;

    static public FindLoactionItemBean generateCity(String cityName) {
        FindLoactionItemBean city = new FindLoactionItemBean();
        city.cityName = cityName;
        city.type = ChooseCityListItemType.FIND_LOCATION;
        return city;
    }

    @Override
    public ChooseCityListItemType getItemType() {
        return type;
    }
}
