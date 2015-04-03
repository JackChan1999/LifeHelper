package com.qz.lifehelper.entity;

/**
 * 该类封装了火车信息
 */
public class TrainInfoBean {
    /**
     * 火车车号
     * 例如：G30
     */
    public String trainInfo;
    /**
     * 发车时间
     */
    public String startTime;
    /**
     * 抵达时间
     */
    public String endTime;
    /**
     * 出发火车站
     */
    public String startStation;
    /**
     * 到达火车站
     */
    public String endStation;
    /**
     * 历时
     */
    public String duration;
    /**
     * 余票
     */
    public String surplusTicketCount;

    public TrainInfoBean setTrainInfo(String trainInfo) {
        this.trainInfo = trainInfo;
        return this;
    }

    public TrainInfoBean setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public TrainInfoBean setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public TrainInfoBean setStartStation(String startStation) {
        this.startStation = startStation;
        return this;
    }

    public TrainInfoBean setEndStation(String endStation) {
        this.endStation = endStation;
        return this;
    }

    public TrainInfoBean setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public TrainInfoBean setSurplusTicketCount(String surplusTicketCount) {
        this.surplusTicketCount = surplusTicketCount;
        return this;
    }
}
