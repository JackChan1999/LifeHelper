package com.qz.lifehelper.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qz.lifehelper.entity.json.JuheResponseJsonBean;
import com.qz.lifehelper.entity.json.TrainInfoJsonBean;
import com.qz.lifehelper.entity.json.TrainStationJsonBean;
import com.qz.lifehelper.persist.TrafficPersist;
import com.qz.lifehelper.utils.OutlineServiceUtil;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import retrofit.http.Query;

/**
 * 火车信息的离线服务器
 */
@EBean
public class TrainOutlineService implements TrainService {

    @RootContext
    Context context;

    @Bean
    TrafficPersist trafficPersist;

    @Override
    public JuheResponseJsonBean<TrainStationJsonBean> getStation() {
        return null;
    }

    @Override
    public JuheResponseJsonBean<TrainInfoJsonBean> getTrainInfo(@Query("from") String startStation, @Query("to") String endStation, @Query("date") String startDate) {
        JuheResponseJsonBean<TrainInfoJsonBean> results = null;

        try {
            OutlineServiceUtil.analogLoding();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String trainIfnoStr = trafficPersist.getTrainInfo(startStation, endStation);
        Gson gson = new Gson();
        results = gson.fromJson(trainIfnoStr, new TypeToken<JuheResponseJsonBean<TrainInfoJsonBean>>() {
        }.getType());

        return results;
    }
}
