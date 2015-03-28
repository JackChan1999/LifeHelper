package com.qz.lifehelper.helper;

/**
 * Created by kohoh on 15/3/16.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

/**
 * 这是ChooseCityActivity的Presentation
 */

@EBean(scope = EBean.Scope.Singleton)
public class ChooseCityHelper {

	@RootContext
	Context context;

	@Bean
	LocationBusiness locationBusiness;

	private City currentLocationCity = null;


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
		currentLoactionCity.add(ChooseCityListItemSection.generateSection("当前定位城市"));
		if (currentLocationCity == null) {
			currentLoactionCity.add(ChooseCityListItemCity.generateCity(
					context.getResources().getString(R.string.find_location_ing), ChooseCityListItemType.FIND_LOCATION));
			locationBusiness.findCurrentLocationCity();
		} else {
			currentLoactionCity.add(ChooseCityListItemCity.generateCity(currentLocationCity.cityName,
					ChooseCityListItemType.FIND_LOCATION));
		}
		return currentLoactionCity;
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

    /**
     * 设置当前定位到的城市
     */
    public void setCurrentLocationCity(City currentLocationCity) {
        this.currentLocationCity = currentLocationCity;
    }
}
