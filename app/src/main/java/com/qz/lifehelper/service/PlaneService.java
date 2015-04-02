package com.qz.lifehelper.service;

import com.qz.lifehelper.business.JuheConstant;
import com.qz.lifehelper.entity.PlaneInfoJsonBean;
import com.qz.lifehelper.entity.json.AirportJsonBean;
import com.qz.lifehelper.entity.json.JuheResponseJsonBean;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * 该类负责从服务端获取航班相关的信息
 */
public interface PlaneService {

    static public final String KEY = "06753a2070829c649753320a490c5b19";

    @GET(JuheConstant.AIPORT)
    public JuheResponseJsonBean<AirportJsonBean> getAirport();

    @GET(JuheConstant.PLANE_INFO)
    public JuheResponseJsonBean<PlaneInfoJsonBean> getPlaneInfo(@Query("start") String statrAirport, @Query("end") String endAirport, @Query("date") String dateFly);

}
