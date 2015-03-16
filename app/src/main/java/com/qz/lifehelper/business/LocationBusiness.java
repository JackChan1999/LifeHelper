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
import com.qz.lifehelper.entity.City;
import com.qz.lifehelper.entity.json.CitiesGroupByFirstCharJson;
import com.qz.lifehelper.entity.json.CityJson;
import com.qz.lifehelper.persist.CityPersist;

/**
 * Created by kohoh on 15-3-14.
 */
@EBean
public class LocationBusiness {

	public static final String TAG = LocationBusiness.class.getSimpleName() + "TAG";

	@RootContext
	Context context;

	@Bean
	CityPersist cityPersist;

	/**
	 * 获取当前位置
	 *
	 * @param locationListener
	 *            回调接口，当获取到位置信息后，将会被毁掉
	 */
	// TODO 不应该使用BDLocationListener
	// TODO 是否应该使用毁掉到接口方式
	public void getCurrentLocation(final BDLocationListener locationListener) {
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

					Log.d("TAG", "get current location");
					locationClient.stop();
					locationListener.onReceiveLocation(bdLocation);
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

		String citiesJson = cityPersist.getAllCitiesGroupByFirstChar();
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
