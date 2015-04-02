package com.qz.lifehelper.service;

import com.qz.lifehelper.entity.json.AirportJsonBean;
import com.qz.lifehelper.entity.json.JuheResponseJsonBean;
import com.qz.lifehelper.entity.json.PlaneInfoJsonBean;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * 该接口负责从服务端获取航班相关的信息
 */
public interface PlaneService {

    /**
     * 获取机场列表
     */
    @GET(JuheConstant.AIPORT)
    public JuheResponseJsonBean<AirportJsonBean> getAirport();

    /**
     * 获取航线信息
     *
     * @param statrAirport 出发机场
     * @param endAirport   目的地机场
     * @param dateFly      出发日期
     */
    @GET(JuheConstant.PLANE_INFO)
    public JuheResponseJsonBean<PlaneInfoJsonBean> getPlaneInfo(@Query("start") String statrAirport, @Query("end") String endAirport, @Query("date") String dateFly);

}
