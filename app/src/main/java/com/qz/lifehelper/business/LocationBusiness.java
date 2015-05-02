package com.qz.lifehelper.business;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.json.CitiesGroupByFirstCharJsonBean;
import com.qz.lifehelper.entity.json.CityJsonBean;
import com.qz.lifehelper.event.GetCurrentCityEvent;
import com.qz.lifehelper.event.GetCurrentLocationCityEvent;
import com.qz.lifehelper.persist.LocationPersist;
import com.qz.lifehelper.ui.fragment.ChooseCityFragment;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.collections4.map.ListOrderedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bolts.Task;
import de.greenrobot.event.EventBus;

/**
 * 该类主要负责与位置相关的业务逻辑
 * <p/>
 * currentCity指的是当前应用选择的城市
 * currentLoactionCity 指的是当前设备所在的城市
 */
@EBean(scope = EBean.Scope.Singleton)
public class LocationBusiness {

    public static final String TAG = LocationBusiness.class.getSimpleName() + "TAG";

    @RootContext
    Context context;

    @Bean
    LocationPersist locationPersist;

    /**
     * 选择城市
     * <p/>
     * 会跳转到ChooseBusCity
     */
    public Task<CityBean> chooseCity(final FragmentManager fragmentManager) {
        final Task<CityBean>.TaskCompletionSource taskCompletionSource = Task.create();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack("");
        ChooseCityFragment fragment = new ChooseCityFragment.Builder()
                .setCallback(new ChooseCityFragment.CallBcak() {
                    @Override
                    public void onCityChoosed(CityBean cityBean) {
                        fragmentManager.popBackStack();
                        taskCompletionSource.setResult(cityBean);
                    }
                })
                .create();
        transaction.add(android.R.id.content, fragment);
        transaction.commit();

        return taskCompletionSource.getTask();
    }

    /**
     * 设置应用选择的当前城市
     */
    public void setCurrentCity(CityBean cityBean) {
        locationPersist.setCurrentCity(cityBean.cityName);
        getEventBus().post(GetCurrentCityEvent.generateEvent(cityBean));
    }

    /**
     * 获取应用选择的当前城市
     */
    public CityBean getCurrentCity() {
        String currentCityName = locationPersist.getCurrentCity();
        if (currentCityName == null) {
            return null;
        } else {
            return CityBean.generateCity(currentCityName);
        }
    }

    /**
     * 通过定位获取当前设备所在位置。
     * <p/>
     * 在获取到当前设备所在位置后，会发送GetLocationEvent，以通知先关组件
     */
    public Task<CityBean> findCurrentLocationCity() {
        final Task<CityBean>.TaskCompletionSource taskCompletionSource = Task.create();

        final LocationClient locationClient = new LocationClient(context);

        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setIsNeedAddress(true);
        locationClientOption.setScanSpan(10001);
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        locationClient.setLocOption(locationClientOption);

        BDLocationListener wrapperListener = new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (bdLocation != null && bdLocation.getCity() != null && bdLocation.getCityCode() != null
                        && !bdLocation.getCity().equals("") && !bdLocation.getCity().equals("null")
                        && !bdLocation.getCityCode().equals("") && !bdLocation.getCityCode().equals("null")) {

                    Log.d(TAG, "get current location");
                    locationClient.stop();
                    String cityName = bdLocation.getCity();
                    cityName = cityName.replace("市", "");
                    CityBean currentLocationCity = CityBean.generateCity(cityName);
                    taskCompletionSource.setResult(currentLocationCity);
                    getEventBus().post(GetCurrentLocationCityEvent.generateEvent(currentLocationCity));
                }
            }
        };
        locationClient.registerLocationListener(wrapperListener);

        locationClient.start();
        return taskCompletionSource.getTask();
    }

    /**
     * 获取以首字母分组排序的全部城市
     * <p/>
     * 该方法会为ChooseCity页面提供城市列表
     * 返回的map，key是城市的首字母，value是该首字母对应的城市的list集合
     */
    public Map<String, List<CityBean>> getAllCity() {
        Map<String, List<CityBean>> cities = new ListOrderedMap<>();

        String citiesJson = locationPersist.getAllCitiesGroupByFirstChar();
        Gson gson = new Gson();
        List<CitiesGroupByFirstCharJsonBean> citiesGroupByFirstCharJsonBeans = gson.fromJson(citiesJson,
                new TypeToken<List<CitiesGroupByFirstCharJsonBean>>() {
                }.getType());
        for (CitiesGroupByFirstCharJsonBean citiesGroupByFirstCharJsonBean : citiesGroupByFirstCharJsonBeans) {
            List<CityBean> realCities = new ArrayList<>();
            for (CityJsonBean cityJsonBean : citiesGroupByFirstCharJsonBean.getCities()) {
                realCities.add(CityBean.generateCity(cityJsonBean.getName()));
            }
            if (realCities.size() > 0) {
                cities.put(citiesGroupByFirstCharJsonBean.getSection(), realCities);
            }
        }
        return cities;
    }

    private EventBus eventBus = EventBus.builder().build();

    public EventBus getEventBus() {
        return eventBus;
    }
}
