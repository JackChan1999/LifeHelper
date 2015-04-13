package com.qz.lifehelper.entity;

/**
 * 封装航班信息数据
 */
public class PlaneInfoBean {
    /**
     * 飞机的信息
     */
    public String planeInfo;
    /**
     * 出发时间
     */
    public String startTime;
    /**
     * 抵达时间
     */
    public String endTime;
    /**
     * 出发机场
     */
    public String startAirport;
    /**
     * 目的地机场
     */
    public String endAirport;
    /**
     * 准点率
     */
    public String onTimeRate;

    public PlaneInfoBean setPlaneInfo(String planeInfo) {
        this.planeInfo = planeInfo;
        return this;
    }

    public PlaneInfoBean setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public PlaneInfoBean setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public PlaneInfoBean setStartAirport(String startAirport) {
        this.startAirport = startAirport;
        return this;
    }

    public PlaneInfoBean setEndAirport(String endAirport) {
        this.endAirport = endAirport;
        return this;
    }

    public PlaneInfoBean setOnTimeRate(String onTimeRate) {
        this.onTimeRate = onTimeRate;
        return this;
    }
}
