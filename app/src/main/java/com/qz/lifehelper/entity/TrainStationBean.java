package com.qz.lifehelper.entity;

/**
 * 该类封装类火车站的信息
 */
public class TrainStationBean {
    public String stationName;
    public String spell;
    public String stationCode;

    public static TrainStationBean generateStation(String stationName, String spell, String stationCode) {
        TrainStationBean trainStationBean = new TrainStationBean();
        trainStationBean.stationName = stationName;
        trainStationBean.spell = spell;
        trainStationBean.stationCode = stationCode;
        return trainStationBean;
    }

    @Override
    public String toString() {
        return stationName;
    }
}
