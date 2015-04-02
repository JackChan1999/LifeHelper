package com.qz.lifehelper.business;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.POIResultBean;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bolts.Task;

/**
 * 该类主要负责与POI先相关的业务逻辑
 * <p/>
 * 所有加载到的POI信息都会被缓存，方便POIResultDetail的调用
 */
@EBean(scope = EBean.Scope.Singleton)
public class POIBusiness {
    // 是否正在加载POI数据
    boolean isLoding = false;

    // 缓存所有加载过的POI数据
    Map<String, POIResultBean> poiResults = new LinkedHashMap<>();

    /**
     * 开始加载制定城市的相关类别的POI数据。
     */
    public Task<List<POIResultBean>> loadPOIData(CityBean cityBean, final String category) {
        final Task<List<POIResultBean>>.TaskCompletionSource taskCompletionSource = Task.create();

        if (isLoding) {
            return Task.forError(new Exception("已经开始加载，请不要重复加载"));
        }

        final PoiSearch poiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                isLoding = false;
                poiSearch.destroy();
                List<PoiInfo> poiInfos = poiResult.getAllPoi();
                List<POIResultBean> poiResultBeans = new ArrayList<>();
                if (poiInfos != null) {
                    for (PoiInfo poiInfo : poiInfos) {
                        POIResultBean mPOIResultBean = new POIResultBean();
                        mPOIResultBean.address = poiInfo.address;
                        mPOIResultBean.poiIv = null;
                        mPOIResultBean.tel = poiInfo.phoneNum;
                        mPOIResultBean.title = poiInfo.name;
                        mPOIResultBean.id = poiInfo.uid;
                        poiResultBeans.add(mPOIResultBean);
                        addPOIResult(mPOIResultBean);
                    }
                }
                taskCompletionSource.setResult(poiResultBeans);
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        };
        poiSearch.setOnGetPoiSearchResultListener(listener);
        poiSearch.searchInCity(new PoiCitySearchOption().city(cityBean.cityName).keyword(category).pageNum(10));
        isLoding = true;
        return taskCompletionSource.getTask();
    }

    /**
     * 增加POI缓存数据
     */
    private void addPOIResult(POIResultBean poiResultBean) {
        if (!poiResults.containsKey(poiResultBean.id)) {
            poiResults.put(poiResultBean.id, poiResultBean);
        }
    }

    /**
     * 根据POI数据的id获取缓存的POI数据
     */
    public POIResultBean getPOIResult(String poiResultId) {
        return poiResults.get(poiResultId);
    }
}
