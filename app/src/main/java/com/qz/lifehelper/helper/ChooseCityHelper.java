package com.qz.lifehelper.helper;

import android.content.Context;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.entity.ChooseCityListItemData;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.CityItemBean;
import com.qz.lifehelper.entity.FindLoactionItemBean;
import com.qz.lifehelper.entity.SectionItemBean;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ChooseBusCityFragment的助手
 * <p/>
 * 帮助ChooseBusCityFragment实现一部分业务逻辑
 */

@EBean
public class ChooseCityHelper {

    @RootContext
    Context context;

    @Bean
    LocationBusiness locationBusiness;

    private CityBean currentLocationCityBean = null;

    /**
     * 为选择城市列表提供数据
     */
    public List<ChooseCityListItemData> getChooseCityListData() {
        List<ChooseCityListItemData> chooseCityListData = new ArrayList<>();
        chooseCityListData.addAll(getCurrentLocationCity());
        chooseCityListData.addAll(getHotCity());
        chooseCityListData.addAll(getAllCityGroupByFirstChar());

        return chooseCityListData;
    }

    /**
     * 提供当前定位城市列表
     */
    private List<ChooseCityListItemData> getCurrentLocationCity() {

        List<ChooseCityListItemData> currentLoactionCity = new ArrayList<>();
        currentLoactionCity.add(SectionItemBean.generateSection("当前定位城市"));
        if (currentLocationCityBean == null) {
            currentLoactionCity.add(FindLoactionItemBean.generateCity(context.getResources().getString(
                    R.string.find_location_ing)));
        } else {
            currentLoactionCity.add(FindLoactionItemBean.generateCity(currentLocationCityBean.cityName));
        }
        return currentLoactionCity;
    }

    /**
     * 提供全国热门城市的列表
     */
    private List<ChooseCityListItemData> getHotCity() {
        List<ChooseCityListItemData> hotCities = new ArrayList<>();
        hotCities.add(SectionItemBean.generateSection("热门城市"));
        hotCities.add(CityItemBean.generateCity("杭州"));
        hotCities.add(CityItemBean.generateCity("北京"));
        hotCities.add(CityItemBean.generateCity("上海"));
        hotCities.add(CityItemBean.generateCity("深圳"));
        hotCities.add(CityItemBean.generateCity("天津"));
        return hotCities;
    }

    /**
     * 提供全国所有城市的数据给城市列表，并以首字母分组排序
     */
    private List<ChooseCityListItemData> getAllCityGroupByFirstChar() {
        List<ChooseCityListItemData> cities = new ArrayList<>();

        Map<String, List<CityBean>> allCity = locationBusiness.getAllCity();
        for (String section : allCity.keySet()) {
            cities.add(SectionItemBean.generateSection(section));
            for (CityBean cityBean : allCity.get(section)) {
                cities.add(CityItemBean.generateCity(cityBean.cityName));
            }
        }
        return cities;
    }

    /**
     * 设置当前定位到的城市
     */
    public void setCurrentLocationCity(CityBean currentLocationCityBean) {
        this.currentLocationCityBean = currentLocationCityBean;
    }
}
