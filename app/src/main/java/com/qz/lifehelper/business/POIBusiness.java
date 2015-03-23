package com.qz.lifehelper.business;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.qz.lifehelper.entity.City;
import com.qz.lifehelper.entity.POIResult;
import com.qz.lifehelper.event.GetPOIResultEvent;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by kohoh on 15/3/20.
 */
@EBean
public class POIBusiness {
	boolean isLoding = false;

    /**
     * 开始加载制定城市的相关类别的POI数据。当加载成功会发出GetPOIResultEvent。
     * */
	public void loadPOIData(City city, final String category) {
		if (isLoding) {
			return;
		}

		final PoiSearch poiSearch = PoiSearch.newInstance();
		OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
			@Override
			public void onGetPoiResult(PoiResult poiResult) {
				isLoding = false;
				poiSearch.destroy();
				List<PoiInfo> poiInfos = poiResult.getAllPoi();
				List<POIResult> poiResults = new ArrayList<>();
				for (PoiInfo poiInfo : poiInfos) {
					POIResult mPOIResult = new POIResult();
					mPOIResult.address = poiInfo.address;
					mPOIResult.poiIv = null;
					mPOIResult.tel = poiInfo.phoneNum;
					mPOIResult.title = poiInfo.name;
					poiResults.add(mPOIResult);
				}
				eventBus.post(GetPOIResultEvent.generateEvnet(poiResults));
			}

			@Override
			public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

			}
		};
		poiSearch.setOnGetPoiSearchResultListener(listener);
		poiSearch.searchInCity(new PoiCitySearchOption().city(city.cityName).keyword(category).pageNum(10));
		isLoding = true;
	}

	EventBus eventBus;

	public POIBusiness() {
		eventBus = EventBus.builder().build();
	}

	public EventBus getEventBus() {
		return eventBus;
	}
}
