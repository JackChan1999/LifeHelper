package com.qz.lifehelper.entity.json;

/**
 * 这里封装了聚合网的长途汽车信息
 */
public class BusInfoJsonBean {

    private String start;
    private String arrive;
    private String date;
    private String price;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
