package com.qz.lifehelper.persist;

import android.content.Context;
import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 该类负责交通出行的数据持续化
 */
@EBean
public class TrafficPersist {

    private static final String TAG = TrafficPersist.class.getSimpleName() + "TAG";

    @RootContext
    Context context;

    /**
     * 获取机场列表的json数据
     */
    public String getAirport() {
        String airportJson = null;
        try {
            InputStream airportsIs = context.getAssets().open("airports");
            airportJson = IOUtils.toString(airportsIs);
        } catch (IOException e) {
            Log.e(TAG, "getAirport fail", e);
            e.printStackTrace();
        }
        return airportJson;
    }

    /**
     * 获取火车站列表的json数据
     */
    public String getTrainStation() {
        String stationJson = null;
        try {
            InputStream stationIs = context.getAssets().open("train_station");
            stationJson = IOUtils.toString(stationIs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stationJson;
    }

    static Map<String, String> cityMap = new HashMap<>();

    static {
        cityMap.put("上海", "sh");
        cityMap.put("天津", "tj");
        cityMap.put("杭州", "hz");
        cityMap.put("北京", "bj");
        cityMap.put("深圳", "sz");
    }


    /**
     * 获取离线的长途大巴信息
     */
    public String getBusInfo(String starCity, String endCity) {
        String busInfoStr = null;
        try {
            InputStream busInfoIs = context.getAssets().open("traffic/bus/" + cityMap.get(starCity) + cityMap.get(endCity));
            busInfoStr = IOUtils.toString(busInfoIs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return busInfoStr;
    }

    /**
     * 获取离线的火车信息
     */
    public String getTrainInfo(String starCity, String endCity) {
        String trainInfoStr = null;
        try {
            InputStream trainInfoIs = context.getAssets().open("traffic/train/" + cityMap.get(starCity) + cityMap.get(endCity));
            trainInfoStr = IOUtils.toString(trainInfoIs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trainInfoStr;
    }

    /**
     * 获取离线的飞机信息
     */
    public String getPlaneInfo(String starCity, String endCity) {
        String planeInfoStr = null;
        try {
            InputStream planeInfoIs = context.getAssets().open("traffic/plane/" + cityMap.get(starCity) + cityMap.get(endCity));
            planeInfoStr = IOUtils.toString(planeInfoIs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return planeInfoStr;
    }
}
