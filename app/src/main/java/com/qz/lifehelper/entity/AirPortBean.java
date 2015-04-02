package com.qz.lifehelper.entity;

/**
 * 该类封装了机场数据
 */
public class AirportBean {

    /**
     * 机场名字
     */
    public String airpory;
    /**
     * 拼音
     */
    public String spell;

    public static final AirportBean generateAirport(String airporyName, String spell) {
        AirportBean airportBean = new AirportBean();
        airportBean.airpory = airporyName;
        airportBean.spell = spell;
        return airportBean;
    }

    @Override
    public String toString() {
        return airpory;
    }
}
