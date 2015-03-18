package com.qz.lifehelper.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.collections4.map.ListOrderedMap;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qz.lifehelper.entity.ChooseCityListItemType;
import com.qz.lifehelper.entity.City;
import com.qz.lifehelper.entity.json.CitiesGroupByFirstCharJson;
import com.qz.lifehelper.entity.json.CityJson;
import com.qz.lifehelper.event.CetCurrentCityEvent;
import com.qz.lifehelper.event.GetCurrentLocationCityEvent;
import com.qz.lifehelper.persist.LocationPersist;

import de.greenrobot.event.EventBus;

/**
 * Created by kohoh on 15-3-14.
 */
@EBean
public class LocationBusiness {

	public static final String TAG = LocationBusiness.class.getSimpleName() + "TAG";

	@RootContext
	Context context;

	@Bean
	LocationPersist locationPersist;

	/**
	 * 设置当前城市
	 */
	public void setCurrentCity(City city) {
		locationPersist.setCurrentCity(city.cityName);
		EventBus.getDefault().post(CetCurrentCityEvent.generateEvent(city));
	}

	/**
	 * 获取当前城市
	 */
	public City getCurrentCity() {
		String currentCityName = locationPersist.getCurrentCity();
		if (currentCityName == null) {
			return null;
		} else {
			return City.generateCity(currentCityName);
		}
	}

	/**
	 * 通过定位获取当前位置。在获取到当前位置后，会发送GetLocationEvent
	 */
	public void findCurrentLocationCity() {
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
					EventBus.getDefault().post(
							GetCurrentLocationCityEvent.generateEvent(City.generateCity(bdLocation.getCity())));
				}
			}
		};
		locationClient.registerLocationListener(wrapperListener);

		locationClient.start();
	}

	/**
	 * 获取以首字母分组排序的全部城市
	 */
	public Map<String, List<City>> getAllCity() {
		Map<String, List<City>> cities = new ListOrderedMap<>();

		String citiesJson = locationPersist.getAllCitiesGroupByFirstChar();
		Gson gson = new Gson();
		List<CitiesGroupByFirstCharJson> citiesGroupByFirstCharJsons = gson.fromJson(citiesJson,
				new TypeToken<List<CitiesGroupByFirstCharJson>>() {
				}.getType());
		for (CitiesGroupByFirstCharJson citiesGroupByFirstCharJson : citiesGroupByFirstCharJsons) {
			List<City> realCities = new ArrayList<>();
			for (CityJson cityJson : citiesGroupByFirstCharJson.getCities()) {
				realCities.add(City.generateCity(cityJson.getName()));
			}
			if (realCities.size() > 0) {
				cities.put(citiesGroupByFirstCharJson.getSection(), realCities);
			}
		}
		return cities;
	}
}
