package com.qz.lifehelper.presentation;

/**
 * Created by kohoh on 15/3/16.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.entity.ChooseCityListItemData;
import com.qz.lifehelper.entity.ChooseCityListItemSection;
import com.qz.lifehelper.entity.ChooseCityListItemType;
import com.qz.lifehelper.entity.City;
import com.qz.lifehelper.ui.activity.ChooseCityActivity;

import de.greenrobot.event.EventBus;

/**
 * 这是ChooseCityActivity的Presentation
 */

@EBean
public class ChooseCityActivityPresentation {

	@RootContext
	Context context;

	@Bean
	LocationBusiness locationBusiness;

	private List<ChooseCityListItemData> chooseCityListData;

	/**
	 * 为选择城市列表提供数据
	 */
	public List<ChooseCityListItemData> getChooseCityListData() {
		List<ChooseCityListItemData> cities = new ArrayList<>();
		cities.addAll(getCurrentCity());
		cities.addAll(getCurrentLocationCity());
		cities.addAll(getHistoryCity());
		cities.addAll(getHotCity());
		cities.addAll(getAllCityGroupByFirstChar());
		return cities;
	}

	/**
	 * 提供当前城市列表
	 */
	public List<ChooseCityListItemData> getCurrentCity() {
		List<ChooseCityListItemData> historyCities = new ArrayList<>();
		historyCities.add(ChooseCityListItemSection.generateSection("当前选择城市"));
		// TODO 暂时没有实现
		historyCities.add(City.generateCity("杭州", ChooseCityListItemType.CITY));
		return historyCities;
	}


    /**
     * 提供当前定位城市列表
     */
    public List<ChooseCityListItemData> getCurrentLocationCity() {

        List<ChooseCityListItemData> historyCities = new ArrayList<>();
        historyCities.add(ChooseCityListItemSection.generateSection("当前定位城市"));
        City currentLocationCity = locationBusiness.getCurrentLocationCity();
        if (currentLocationCity == null) {
            historyCities.add(City.generateCity(context.getResources().getString(R.string.find_location_ing), ChooseCityListItemType.FIND_LOCATION));
            locationBusiness.findCurrentLocationCity();
        } else {
            historyCities.add(City.generateCity(currentLocationCity.cityName, ChooseCityListItemType.FIND_LOCATION));
        }
        return historyCities;
    }

	/**
	 * 提供热门城市列表
	 */
	public List<ChooseCityListItemData> getHistoryCity() {
		List<ChooseCityListItemData> historyCities = new ArrayList<>();
		historyCities.add(ChooseCityListItemSection.generateSection("最近游览城市"));
		// TODO 暂时没有实现
		historyCities.add(City.generateCity("杭州",ChooseCityListItemType.CITY));
		historyCities.add(City.generateCity("北京",ChooseCityListItemType.CITY));
		return historyCities;
	}

	/**
	 * 提供全国热门城市的列表
	 */
	public List<ChooseCityListItemData> getHotCity() {
		List<ChooseCityListItemData> hotCities = new ArrayList<>();
		hotCities.add(ChooseCityListItemSection.generateSection("热门城市"));
		hotCities.add(City.generateCity("杭州",ChooseCityListItemType.CITY));
		hotCities.add(City.generateCity("北京",ChooseCityListItemType.CITY));
		hotCities.add(City.generateCity("上海",ChooseCityListItemType.CITY));
		hotCities.add(City.generateCity("深圳",ChooseCityListItemType.CITY));
		return hotCities;
	}

	/**
	 * 提供全国所有城市的数据给城市列表，并以首字母分组排序
	 */
	public List<ChooseCityListItemData> getAllCityGroupByFirstChar() {
		List<ChooseCityListItemData> cities = new ArrayList<>();

		Map<String, List<City>> allCity = locationBusiness.getAllCity();
		for (String section : allCity.keySet()) {
			cities.add(ChooseCityListItemSection.generateSection(section));
			cities.addAll(allCity.get(section));
		}
		return cities;
	}

    @AfterInject
    public void registerEventBus() {
        EventBus.getDefault().register(this);
    }


    /**
     * 获取到当前位置后会被调用
     */
    public void onEvent(LocationBusiness.GetLocationEvent event) {
        EventBus.getDefault().post(new ChooseCityActivity.RefreshCityListEvent());
    }
}
