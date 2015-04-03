package com.qz.lifehelper.entity;

/**
 * Created by kohoh on 15/4/3.
 */
public class TrainInfoBean {
    public String trainInfo;
    public String startTime;
    public String endTime;
    public String startStation;
    public String endStation;
    public String duration;
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
