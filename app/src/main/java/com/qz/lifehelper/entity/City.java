package com.qz.lifehelper.entity;

/**
 * 城市
 */
public class City implements ChooseCityListItemData {
	public String cityName;
    public ChooseCityListItemType type;

    static public City generateCity(String cityName, ChooseCityListItemType type) {
        City city = new City();
        city.cityName = cityName;
        city.type = type;
        return city;
    }

	@Override
	public ChooseCityListItemType getItemType() {
		return type;
	}
}
