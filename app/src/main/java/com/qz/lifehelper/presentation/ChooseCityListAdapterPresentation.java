package com.qz.lifehelper.presentation;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.entity.City;
import com.qz.lifehelper.event.FinishActivityEvent;
import com.qz.lifehelper.event.InvalidCityListEvent;
import com.qz.lifehelper.event.NewCityListEvent;
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
        EventBus.getDefault().post(InvalidCityListEvent.generateEvent());
        locationBusiness.findCurrentLocationCity();
    }

    /**
     * 设置当前城市
     */
    public void setCurrentCity(City city) {
        locationBusiness.setCurrentCity(city);
        EventBus.getDefault().post(FinishActivityEvent.generateEvent(ChooseCityActivity.class));
    }
}
