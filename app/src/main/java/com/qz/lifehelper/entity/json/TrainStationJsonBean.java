package com.qz.lifehelper.entity.json;

/**
 * 这里封装的是聚合网提供给的火车站信息
 */
public class TrainStationJsonBean {
    private String sta_name;
    private String sta_ename;
    private String sta_code;


    public String getSta_name() {
        return sta_name;
    }

    public void setSta_name(String sta_name) {
        this.sta_name = sta_name;
    }

    public String getSta_ename() {
        return sta_ename;
    }

    public void setSta_ename(String sta_ename) {
        this.sta_ename = sta_ename;
    }

    public String getSta_code() {
        return sta_code;
    }

    public void setSta_code(String sta_code) {
        this.sta_code = sta_code;
    }
}
