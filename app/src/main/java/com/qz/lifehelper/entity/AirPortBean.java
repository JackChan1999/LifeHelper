package com.qz.lifehelper.entity;

/**
 * Created by kohoh on 15/4/2.
 */
public class AirPortBean {

    public String airpory;
    public String spell;

    public static final AirPortBean generateAirport(String airporyName, String spell) {
        AirPortBean airPortBean = new AirPortBean();
        airPortBean.airpory = airporyName;
        airPortBean.spell = spell;
        return airPortBean;
    }

    @Override
    public String toString() {
        return airpory;
    }
}
