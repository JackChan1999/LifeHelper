package com.qz.lifehelper.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qz.lifehelper.entity.json.BusInfoJsonBean;
import com.qz.lifehelper.entity.json.JuheResponseJsonBean2;
import com.qz.lifehelper.utils.OutlineServiceUtil;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;

import retrofit.http.Query;

/**
 * 长途汽车的离线服务器
 */
@EBean
public class BusOutlineService implements BusService {

    @RootContext
    Context context;

    @Override
    public JuheResponseJsonBean2<BusInfoJsonBean> getBusInfo(@Query("from") String startCity, @Query("to") String endCity) {
        JuheResponseJsonBean2<BusInfoJsonBean> results = null;
        try {
            OutlineServiceUtil.analogLoding();
            InputStream busInfoIs = context.getAssets().open("traffic/bus_info");
            String busIfnoStr = IOUtils.toString(busInfoIs);
            Gson gson = new Gson();
            results = gson.fromJson(busIfnoStr, new TypeToken<JuheResponseJsonBean2<BusInfoJsonBean>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        return results;
    }
}
