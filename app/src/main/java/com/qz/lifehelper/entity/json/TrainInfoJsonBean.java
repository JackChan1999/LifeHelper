package com.qz.lifehelper.entity.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 这里封装的是聚合网的火车信息
 */
public class TrainInfoJsonBean {

    @SerializedName("train_no")
    @Expose
    private String trainNo;
    @SerializedName("start_station_name")
    @Expose
    private String startStationName;
    @SerializedName("end_station_name")
    @Expose
    private String endStationName;
    @SerializedName("from_station_name")
    @Expose
    private String fromStationName;
    @SerializedName("to_station_name")
    @Expose
    private String toStationName;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("arrive_time")
    @Expose
    private String arriveTime;
    @SerializedName("train_class_name")
    @Expose
    private String trainClassName;
    @SerializedName("day_difference")
    @Expose
    private String dayDifference;
    @Expose
    private String lishi;
    @SerializedName("gr_num")
    @Expose
    private String grNum;
    @SerializedName("qt_num")
    @Expose
    private String qtNum;
    @SerializedName("rw_num")
    @Expose
    private String rwNum;
    @SerializedName("rz_num")
    @Expose
    private String rzNum;
    @SerializedName("tz_num")
    @Expose
    private String tzNum;
    @SerializedName("wz_num")
    @Expose
    private String wzNum;
    @SerializedName("yw_num")
    @Expose
    private String ywNum;
    @SerializedName("yz_num")
    @Expose
    private String yzNum;
    @SerializedName("ze_num")
    @Expose
    private String zeNum;
    @SerializedName("zy_num")
    @Expose
    private String zyNum;
    @SerializedName("swz_num")
    @Expose
    private String swzNum;

    /**
     * @return The trainNo
     */
    public String getTrainNo() {
        return trainNo;
    }

    /**
     * @param trainNo The train_no
     */
    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    /**
     * @return The startStationName
     */
    public String getStartStationName() {
        return startStationName;
    }

    /**
     * @param startStationName The start_station_name
     */
    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }

    /**
     * @return The endStationName
     */
    public String getEndStationName() {
        return endStationName;
    }

    /**
     * @param endStationName The end_station_name
     */
    public void setEndStationName(String endStationName) {
        this.endStationName = endStationName;
    }

    /**
     * @return The fromStationName
     */
    public String getFromStationName() {
        return fromStationName;
    }

    /**
     * @param fromStationName The from_station_name
     */
    public void setFromStationName(String fromStationName) {
        this.fromStationName = fromStationName;
    }

    /**
     * @return The toStationName
     */
    public String getToStationName() {
        return toStationName;
    }

    /**
     * @param toStationName The to_station_name
     */
    public void setToStationName(String toStationName) {
        this.toStationName = toStationName;
    }

    /**
     * @return The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime The start_time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return The arriveTime
     */
    public String getArriveTime() {
        return arriveTime;
    }

    /**
     * @param arriveTime The arrive_time
     */
    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    /**
     * @return The trainClassName
     */
    public String getTrainClassName() {
        return trainClassName;
    }

    /**
     * @param trainClassName The train_class_name
     */
    public void setTrainClassName(String trainClassName) {
        this.trainClassName = trainClassName;
    }

    /**
     * @return The dayDifference
     */
    public String getDayDifference() {
        return dayDifference;
    }

    /**
     * @param dayDifference The day_difference
     */
    public void setDayDifference(String dayDifference) {
        this.dayDifference = dayDifference;
    }

    /**
     * @return The lishi
     */
    public String getLishi() {
        return lishi;
    }

    /**
     * @param lishi The lishi
     */
    public void setLishi(String lishi) {
        this.lishi = lishi;
    }

    /**
     * @return The grNum
     */
    public String getGrNum() {
        return grNum;
    }

    /**
     * @param grNum The gr_num
     */
    public void setGrNum(String grNum) {
        this.grNum = grNum;
    }

    /**
     * @return The qtNum
     */
    public String getQtNum() {
        return qtNum;
    }

    /**
     * @param qtNum The qt_num
     */
    public void setQtNum(String qtNum) {
        this.qtNum = qtNum;
    }

    /**
     * @return The rwNum
     */
    public String getRwNum() {
        return rwNum;
    }

    /**
     * @param rwNum The rw_num
     */
    public void setRwNum(String rwNum) {
        this.rwNum = rwNum;
    }

    /**
     * @return The rzNum
     */
    public String getRzNum() {
        return rzNum;
    }

    /**
     * @param rzNum The rz_num
     */
    public void setRzNum(String rzNum) {
        this.rzNum = rzNum;
    }

    /**
     * @return The tzNum
     */
    public String getTzNum() {
        return tzNum;
    }

    /**
     * @param tzNum The tz_num
     */
    public void setTzNum(String tzNum) {
        this.tzNum = tzNum;
    }

    /**
     * @return The wzNum
     */
    public String getWzNum() {
        return wzNum;
    }

    /**
     * @param wzNum The wz_num
     */
    public void setWzNum(String wzNum) {
        this.wzNum = wzNum;
    }

    /**
     * @return The ywNum
     */
    public String getYwNum() {
        return ywNum;
    }

    /**
     * @param ywNum The yw_num
     */
    public void setYwNum(String ywNum) {
        this.ywNum = ywNum;
    }

    /**
     * @return The yzNum
     */
    public String getYzNum() {
        return yzNum;
    }

    /**
     * @param yzNum The yz_num
     */
    public void setYzNum(String yzNum) {
        this.yzNum = yzNum;
    }

    /**
     * @return The zeNum
     */
    public String getZeNum() {
        return zeNum;
    }

    /**
     * @param zeNum The ze_num
     */
    public void setZeNum(String zeNum) {
        this.zeNum = zeNum;
    }

    /**
     * @return The zyNum
     */
    public String getZyNum() {
        return zyNum;
    }

    /**
     * @param zyNum The zy_num
     */
    public void setZyNum(String zyNum) {
        this.zyNum = zyNum;
    }

    /**
     * @return The swzNum
     */
    public String getSwzNum() {
        return swzNum;
    }

    /**
     * @param swzNum The swz_num
     */
    public void setSwzNum(String swzNum) {
        this.swzNum = swzNum;
    }

}