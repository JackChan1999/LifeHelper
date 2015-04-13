package com.qz.lifehelper.entity.json;

/**
 * 封装来长途巴士的信息
 */
public class BusInfoBean {

    public String startTinme;
    public String startStation;
    public String endStation;
    public String ticketPrice;

    public BusInfoBean setStartTime(String startTinme) {
        this.startTinme = startTinme;
        return this;
    }

    public BusInfoBean setStartStation(String startStation) {
        this.startStation = startStation;
        return this;
    }

    public BusInfoBean setEndStation(String endStation) {
        this.endStation = endStation;
        return this;
    }

    public BusInfoBean setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
        return this;
    }
}
