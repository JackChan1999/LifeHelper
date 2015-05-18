package com.qz.lifehelper.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qz.lifehelper.entity.json.AirportJsonBean;
import com.qz.lifehelper.entity.json.JuheResponseJsonBean;
import com.qz.lifehelper.entity.json.PlaneInfoJsonBean;
import com.qz.lifehelper.utils.OutlineServiceUtil;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;

import retrofit.http.Query;

/**
 * 航班信息的离线服务器
 */
@EBean
public class PlaneOutlineService implements PlaneService {

    @RootContext
    Context context;

    @Override
    public JuheResponseJsonBean<AirportJsonBean> getAirport() {
        return null;
    }

    @Override
    public JuheResponseJsonBean<PlaneInfoJsonBean> getPlaneInfo(@Query("start") String statrAirport, @Query("end") String endAirport, @Query("date") String dateFly) {

        JuheResponseJsonBean<PlaneInfoJsonBean> results = null;
        try {
            OutlineServiceUtil.analogLoding();
            InputStream planeInfoIs = context.getAssets().open("traffic/plane_info");
            String planeIfnoStr = IOUtils.toString(planeInfoIs);
            Gson gson = new Gson();
            results = gson.fromJson(planeIfnoStr, new TypeToken<JuheResponseJsonBean<PlaneInfoJsonBean>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        return results;
    }
}
