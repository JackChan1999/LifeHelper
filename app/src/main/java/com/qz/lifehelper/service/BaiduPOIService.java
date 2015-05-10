package com.qz.lifehelper.service;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.POICategoryBean;
import com.qz.lifehelper.entity.POIResultBean;
import com.qz.lifehelper.persist.POIPersist;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import bolts.Task;

/**
 * 百度的POI服务
 */
@EBean
public class BaiduPOIService {

    @Bean
    POIPersist poiPersist;

    /**
     * 获取POI数据
     *
     * @param cityBean     要获取的数据的城市
     * @param categoryBean 要获取的数据的分类
     * @param count        每页的个数
     * @param page         地几页
     */
    public Task<List<POIResultBean>> getPoiItems(CityBean cityBean, final POICategoryBean categoryBean, int count, int page) {

        final Task<List<POIResultBean>>.TaskCompletionSource taskCompletionSource = Task.create();

        final PoiSearch poiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                poiSearch.destroy();
                List<PoiInfo> poiInfos = poiResult.getAllPoi();
                List<POIResultBean> poiResultBeans = new ArrayList<>();
                if (poiInfos != null) {
                    for (PoiInfo poiInfo : poiInfos) {
                        POIResultBean mPOIResultBean = convertToPOIItemBean(poiInfo, categoryBean);
                        poiResultBeans.add(mPOIResultBean);
                    }
                }
                taskCompletionSource.setResult(poiResultBeans);
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        };
        poiSearch.setOnGetPoiSearchResultListener(listener);
        poiSearch.searchInCity(new PoiCitySearchOption()
                .city(cityBean.cityName)
                .keyword(categoryBean.categotyName)
                .pageNum(page)
                .pageCapacity(count));
        return taskCompletionSource.getTask();
    }

    /**
     * 将PoiInfo转换为POIItemBean
     */
    private POIResultBean convertToPOIItemBean(PoiInfo poiInfo, POICategoryBean categoryBean) {
        POIResultBean mPOIResultBean = new POIResultBean()
                .setAddress(poiInfo.address)
                .setTel(poiInfo.phoneNum)
                .setTitle(poiInfo.name)
                .setId(poiInfo.uid)
                .setImageBean(ImageBean.generateImage(
                        poiPersist.getDefaultPOIImage(categoryBean),
                        ImageBean.ImageType.OUTLINE))
                .setPoiCategoryBean(categoryBean)
                .setDetail(poiPersist.getDefaultPOIDetail(categoryBean));
        return mPOIResultBean;
    }

}
