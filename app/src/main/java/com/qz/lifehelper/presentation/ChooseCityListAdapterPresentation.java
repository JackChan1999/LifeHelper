package com.qz.lifehelper.presentation;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.entity.City;
import com.qz.lifehelper.ui.activity.ChooseCityActivity;

import de.greenrobot.event.EventBus;

/**
 * Created by kohoh on 15/3/17.
 */
@EBean
public class ChooseCityListAdapterPresentation {

    @Bean
    LocationBusiness locationBusiness;

    /**
     * 重新定位，获取当前的位置
     */
    public void findCurrentLocationCity() {
        locationBusiness.clearCurrentLocationCity();
        EventBus.getDefault().post(new ChooseCityActivity.RefreshCityListEvent());
        locationBusiness.findCurrentLocationCity();
    }

    /**
     * 设置当前城市
     */
    public void setCurrentCity(City city) {
        locationBusiness.setCurrentCity(city);
    }
}
