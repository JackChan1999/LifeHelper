package com.qz.lifehelper.persist;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 该类用于维护与位置相关信息的数据
 */
@EBean
public class LocationPersist {

    @RootContext
    Context context;

    static final public String TAG = LocationPersist.class.getSimpleName() + "TAG";

    /**
     * 获取本地存储的全国城市列表的json数据
     */
    public String getAllCitiesGroupByFirstChar() {
        String citiesJson = null;
        try {
            InputStream citiesInputStream = context.getAssets().open("cities");
            citiesJson = IOUtils.toString(citiesInputStream);
        } catch (IOException e) {
            Log.e(TAG, "getAllCitiesGroupByFirstChar fail", e);
            e.printStackTrace();
        }

        return citiesJson;
    }

    static final private String CURRENT_CITY = "CURRENT_CITY";
    static final private String LOCATION_SHARED_PREFERENCES = "LOCATION_SHARED_PREFERENCES";

    private SharedPreferences getLocationSharedPrefences() {
        return context.getSharedPreferences(LOCATION_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    /**
     * 设置当前应用选择的城市
     */
    public void setCurrentCity(String cityName) {
        getLocationSharedPrefences().edit().putString(CURRENT_CITY, cityName).commit();
    }

    /**
     * 获取当前应用选择的城市
     */
    public String getCurrentCity() {
        return getLocationSharedPrefences().getString(CURRENT_CITY, null);
    }
}
