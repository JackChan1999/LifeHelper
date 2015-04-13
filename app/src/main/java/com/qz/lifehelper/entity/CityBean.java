package com.qz.lifehelper.entity;

/**
 * 该类分装了城市的信息
 */
public class CityBean {
    public String cityName;

    static public CityBean generateCity(String cityName) {
        CityBean cityBean = new CityBean();
        cityBean.cityName = cityName;
        return cityBean;
    }

    @Override
    public String toString() {
        return cityName;
    }
}
