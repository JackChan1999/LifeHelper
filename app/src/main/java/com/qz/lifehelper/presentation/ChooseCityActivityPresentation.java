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
import com.qz.lifehelper.entity.ChooseCityListItemCity;
import com.qz.lifehelper.entity.ChooseCityListItemData;
import com.qz.lifehelper.entity.ChooseCityListItemSection;
import com.qz.lifehelper.entity.ChooseCityListItemType;
import com.qz.lifehelper.entity.City;
import com.qz.lifehelper.event.GetCurrentLocationCityEvent;
import com.qz.lifehelper.event.InvalidCityListEvent;
import com.qz.lifehelper.event.NewCityListEvent;

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
	private City currentLocationCity = null;

	/**
	 * 为选择城市列表提供数据
	 */
	public void getChooseCityListData() {
		if (chooseCityListData == null) {
			chooseCityListData = new ArrayList<>();
			chooseCityListData.addAll(getCurrentCity());
			chooseCityListData.addAll(getCurrentLocationCity());
			chooseCityListData.addAll(getHistoryCity());
			chooseCityListData.addAll(getHotCity());
			chooseCityListData.addAll(getAllCityGroupByFirstChar());
		}
		EventBus.getDefault().post(NewCityListEvent.generateEvent(chooseCityListData));
	}

	/**
	 * 提供当前城市列表
	 */
	private List<ChooseCityListItemData> getCurrentCity() {
		List<ChooseCityListItemData> historyCities = new ArrayList<>();
		historyCities.add(ChooseCityListItemSection.generateSection("当前选择城市"));
		// TODO 暂时没有实现
		historyCities.add(ChooseCityListItemCity.generateCity("杭州", ChooseCityListItemType.CITY));
		return historyCities;
	}

	/**
	 * 提供当前定位城市列表
	 */
	private List<ChooseCityListItemData> getCurrentLocationCity() {

		List<ChooseCityListItemData> historyCities = new ArrayList<>();
		historyCities.add(ChooseCityListItemSection.generateSection("当前定位城市"));
		if (currentLocationCity == null) {
			historyCities.add(ChooseCityListItemCity.generateCity(
					context.getResources().getString(R.string.find_location_ing), ChooseCityListItemType.FIND_LOCATION));
			locationBusiness.findCurrentLocationCity();
		} else {
			historyCities.add(ChooseCityListItemCity.generateCity(currentLocationCity.cityName,
					ChooseCityListItemType.FIND_LOCATION));
		}
		return historyCities;
	}

	/**
	 * 提供热门城市列表
	 */
	private List<ChooseCityListItemData> getHistoryCity() {
		List<ChooseCityListItemData> historyCities = new ArrayList<>();
		historyCities.add(ChooseCityListItemSection.generateSection("最近游览城市"));
		// TODO 暂时没有实现
		historyCities.add(ChooseCityListItemCity.generateCity("杭州", ChooseCityListItemType.CITY));
		historyCities.add(ChooseCityListItemCity.generateCity("北京", ChooseCityListItemType.CITY));
		return historyCities;
	}

	/**
	 * 提供全国热门城市的列表
	 */
	private List<ChooseCityListItemData> getHotCity() {
		List<ChooseCityListItemData> hotCities = new ArrayList<>();
		hotCities.add(ChooseCityListItemSection.generateSection("热门城市"));
		hotCities.add(ChooseCityListItemCity.generateCity("杭州", ChooseCityListItemType.CITY));
		hotCities.add(ChooseCityListItemCity.generateCity("北京", ChooseCityListItemType.CITY));
		hotCities.add(ChooseCityListItemCity.generateCity("上海", ChooseCityListItemType.CITY));
		hotCities.add(ChooseCityListItemCity.generateCity("深圳", ChooseCityListItemType.CITY));
		return hotCities;
	}

	/**
	 * 提供全国所有城市的数据给城市列表，并以首字母分组排序
	 */
	private List<ChooseCityListItemData> getAllCityGroupByFirstChar() {
		List<ChooseCityListItemData> cities = new ArrayList<>();

		Map<String, List<City>> allCity = locationBusiness.getAllCity();
		for (String section : allCity.keySet()) {
			cities.add(ChooseCityListItemSection.generateSection(section));
			for (City city : allCity.get(section)) {
				cities.add(ChooseCityListItemCity.generateCity(city.cityName, ChooseCityListItemType.CITY));
			}
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
	public void onEvent(GetCurrentLocationCityEvent event) {
		invalidCityList();
		currentLocationCity = event.currentLocationCity;
		getChooseCityListData();
	}

	public void invalidCityList() {
		chooseCityListData.clear();
		chooseCityListData = null;
		currentLocationCity = null;
	}

	public void onEvent(InvalidCityListEvent event) {
		invalidCityList();
		getChooseCityListData();

	}
}
