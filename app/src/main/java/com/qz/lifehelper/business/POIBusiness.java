package com.qz.lifehelper.business;

import android.content.Context;
import android.widget.Toast;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.POICategoryBean;
import com.qz.lifehelper.entity.POIResultBean;
import com.qz.lifehelper.entity.json.POIResultJsonBean;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

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

    @RootContext
    Context context;

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
                        POICategoryBean categoryBean = POICategoryBean.generate(category);
                        POIResultBean mPOIResultBean = new POIResultBean()
                                .setAddress(poiInfo.address)
                                .setTel(poiInfo.phoneNum)
                                .setTitle(poiInfo.name)
                                .setId(poiInfo.uid)
                                .setImageBean(ImageBean.generateImage(
                                        getDefaultPOIImage(categoryBean),
                                        ImageBean.ImageType.OUTLINE))
                                .setPoiCategoryBean(categoryBean)
                                .setDetail(getDefaultPOIDetail(categoryBean));
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


    /**
     * 解析POIResult的json数据
     */
    public Task<List<POIResultBean>> parsePOIResult(final String tenTopSpotsJson) {
        return Task.callInBackground(new Callable<List<POIResultBean>>() {
            @Override
            public List<POIResultBean> call() throws Exception {
                Gson gson = new Gson();
                List<POIResultJsonBean> poiResultJsonBeans = gson.fromJson(tenTopSpotsJson, new TypeToken<List<POIResultJsonBean>>() {
                }.getType());

                List<POIResultBean> poiResultBeans = new ArrayList<POIResultBean>();
                for (POIResultJsonBean poiResultJsonBean : poiResultJsonBeans) {
                    poiResultBeans.add(new POIResultBean()
                            .setTitle(poiResultJsonBean.getTitle())
                            .setDetail(poiResultJsonBean.getContent())
                            .setImageBean(ImageBean.generateImage(poiResultJsonBean.getImage(), ImageBean.ImageType.OUTLINE)));
                }
                return poiResultBeans;
            }
        });
    }

    /**
     * 前往我发布的信息页面
     */
    public void toMyPublish() {
        Toast.makeText(context, "前往我发布的信息页面", Toast.LENGTH_SHORT).show();
    }

    static Map<String, String> poiCategories = new HashMap<>();

    static {
        poiCategories.put("餐厅", "restruant");
    }

    /**
     * 获取默认的POI图片
     *
     * @param categoryBean poi类别
     */
    private String getDefaultPOIImage(POICategoryBean categoryBean) {
        String category = poiCategories.get(categoryBean.categotyName);
        if (category == null) {
            return "file:///android_asset/ten_top_spots_image/ten_top_spots_1.jpg";
        }
        String image = "file:///android_asset/poi/" + category + "/" + String.valueOf(new Random().nextInt(11) % 10) + ".png";
        return image;
    }

    /**
     * 获取默认的POI细节
     *
     * @param categoryBean poi类别
     */
    private String getDefaultPOIDetail(POICategoryBean categoryBean) {
        String category = poiCategories.get(categoryBean.categotyName);
        String detail = null;
        InputStream detailIS = null;
        try {
            detailIS = context.getAssets().open("poi/" + category + "/" + String.valueOf(new Random().nextInt(11) % 10) + ".txt");
            detail = IOUtils.toString(detailIS);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return detail;
        }
    }
}
