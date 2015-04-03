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

    @GET(JuheConstant.TRAIN_STATION)
    public JuheResponseJsonBean<TrainStationJsonBean> getStation();

    @GET(JuheConstant.TRAIN_INFO)
    public JuheResponseJsonBean<TrainInfoJsonBean> getTrainInfo(
            @Query("from") String startStation,
            @Query("to") String endStation,
            @Query("date") String startDate);

}
