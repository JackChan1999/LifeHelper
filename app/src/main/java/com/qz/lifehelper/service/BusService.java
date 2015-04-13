package com.qz.lifehelper.service;

import com.qz.lifehelper.entity.json.BusInfoJsonBean;
import com.qz.lifehelper.entity.json.JuheResponseJsonBean2;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by kohoh on 15/4/13.
 */
public interface BusService {

    @GET(JuheConstant.BUS_INFO)
    public JuheResponseJsonBean2<BusInfoJsonBean> getBusInfo(
            @Query("from") String startCity,
            @Query("to") String endCity);
}
