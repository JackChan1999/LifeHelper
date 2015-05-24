package com.qz.lifehelper.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qz.lifehelper.entity.json.AirportJsonBean;
import com.qz.lifehelper.entity.json.JuheResponseJsonBean;
import com.qz.lifehelper.entity.json.PlaneInfoJsonBean;
import com.qz.lifehelper.persist.TrafficPersist;
import com.qz.lifehelper.utils.OutlineServiceUtil;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import retrofit.http.Query;

/**
 * 航班信息的离线服务器
 */
@EBean
public class PlaneOutlineService implements PlaneService {

    @RootContext
    Context context;

    @Bean
    TrafficPersist trafficPersist;

    @Override
    public JuheResponseJsonBean<AirportJsonBean> getAirport() {
        return null;
    }

    @Override
    public JuheResponseJsonBean<PlaneInfoJsonBean> getPlaneInfo(@Query("start") String statrAirport, @Query("end") String endAirport, @Query("date") String dateFly) {

        try {
            OutlineServiceUtil.analogLoding();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JuheResponseJsonBean<PlaneInfoJsonBean> results = null;

        String planeIfnoStr = trafficPersist.getPlaneInfo(statrAirport, endAirport);
        Gson gson = new Gson();
        results = gson.fromJson(planeIfnoStr, new TypeToken<JuheResponseJsonBean<PlaneInfoJsonBean>>() {
        }.getType());

        return results;
    }
}
