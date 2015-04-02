package com.qz.lifehelper.persist;

import android.content.Context;
import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

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

}
