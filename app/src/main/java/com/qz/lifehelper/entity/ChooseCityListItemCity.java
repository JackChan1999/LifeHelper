package com.qz.lifehelper.entity;

/**
 * Created by kohoh on 15/3/18.
 */
public class ChooseCityListItemCity implements ChooseCityListItemData {
    public String cityName;
    public ChooseCityListItemType type;

    static public ChooseCityListItemCity generateCity(String cityName, ChooseCityListItemType type) {
        ChooseCityListItemCity city = new ChooseCityListItemCity();
        city.cityName = cityName;
        city.type = type;
        return city;
    }

    @Override
    public ChooseCityListItemType getItemType() {
        return type;
    }
}
