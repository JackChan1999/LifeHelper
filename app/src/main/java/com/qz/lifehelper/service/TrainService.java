package com.qz.lifehelper.service;

import com.qz.lifehelper.entity.json.JuheResponseJsonBean;
import com.qz.lifehelper.entity.json.TrainInfoJsonBean;
import com.qz.lifehelper.entity.json.TrainStationJsonBean;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * 该接口负责从服务端获取火车相关的信息
 */
public interface TrainService {

    /**
     * 获取火车站列表
     */
    @GET(JuheConstant.TRAIN_STATION)
    public JuheResponseJsonBean<TrainStationJsonBean> getStation();

    /**
     * 获取火车信息
     *
     * @param startStation 出发火车站的名字
     * @param endStation   到达火车站的名字
     * @param startDate    出发日期 格式：yyyy-MM-dd
     * @return
     */
    @GET(JuheConstant.TRAIN_INFO)
    public JuheResponseJsonBean<TrainInfoJsonBean> getTrainInfo(
            @Query("from") String startStation,
            @Query("to") String endStation,
            @Query("date") String startDate);

}
