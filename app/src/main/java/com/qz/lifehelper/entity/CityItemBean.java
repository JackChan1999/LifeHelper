package com.qz.lifehelper.entity;

/**
 * 这是ChooseCity页面中，item为城市时对应的数据类型
 */
public class CityItemBean implements ChooseCityListItemData {

    public String cityName;
    public ChooseCityListItemType type;

    static public CityItemBean generateCity(String cityName) {
        CityItemBean city = new CityItemBean();
        city.cityName = cityName;
        city.type = ChooseCityListItemType.CITY;
        return city;
    }

    @Override
    public ChooseCityListItemType getItemType() {
        return type;
    }
}
