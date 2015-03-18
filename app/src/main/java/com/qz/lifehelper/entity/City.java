package com.qz.lifehelper.entity;

/**
 * 城市
 */
public class City{
	public String cityName;

    static public City generateCity(String cityName) {
        City city = new City();
        city.cityName = cityName;
        return city;
    }
}
