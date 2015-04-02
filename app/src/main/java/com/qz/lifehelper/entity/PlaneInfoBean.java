package com.qz.lifehelper.entity;

/**
 * 封装航班信息数据
 */
public class PlaneInfoBean {
    public String planeInfo;
    public String startTime;
    public String endTime;
    public String startAirport;
    public String endAirport;
    public String planeState;

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

    public PlaneInfoBean setPlaneState(String planeState) {
        this.planeState = planeState;
        return this;
    }
}
