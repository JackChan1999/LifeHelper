package com.qz.lifehelper.service;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.qz.lifehelper.business.AuthenticationBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.POICategoryBean;
import com.qz.lifehelper.entity.POIResultBean;
import com.qz.lifehelper.persist.POIPersist;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

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
    public Task<List<POIResultBean>> getPoiItems(final CityBean cityBean, final POICategoryBean categoryBean, final int count, final int page) {

        final Task<List<POIResultBean>>.TaskCompletionSource taskCompletionSource = Task.create();
        Task.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                final PoiSearch poiSearch = PoiSearch.newInstance();
                OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
                    @Override
                    public void onGetPoiResult(PoiResult poiResult) {
                        poiSearch.destroy();
                        List<PoiInfo> poiInfos = poiResult.getAllPoi();
                        List<POIResultBean> poiResultBeans = new ArrayList<>();
                        if (poiInfos != null) {
                            for (PoiInfo poiInfo : poiInfos) {
                                POIResultBean mPOIResultBean = convertToPOIItemBean(poiInfo, categoryBean, cityBean);
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
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
        return taskCompletionSource.getTask();
    }

    /**
     * 将PoiInfo转换为POIItemBean
     */
    private POIResultBean convertToPOIItemBean(PoiInfo poiInfo, POICategoryBean categoryBean, CityBean cityBean) {

        POIResultBean mPOIResultBean = new POIResultBean()
                .setAddress(poiInfo.address)
                .setTel(poiInfo.phoneNum)
                .setTitle(poiInfo.name)
                .setId(poiInfo.uid)
                .setImageBean(ImageBean.generateImage(
                        poiPersist.getDefaultPOIImage(categoryBean),
                        ImageBean.ImageType.OUTLINE))
                .setPoiCategoryBean(categoryBean)
                .setDetail(poiPersist.getDefaultPOIDetail(categoryBean))
                .setType(POIResultBean.TYPE.BAIDU)
                .setUserInfoBean(AuthenticationBusiness.getBaiduUser())
                .setCreatedAt(new Date())
                .setCityBean(cityBean);

        return mPOIResultBean;
    }

}
