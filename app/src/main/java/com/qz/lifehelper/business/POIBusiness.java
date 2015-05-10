package com.qz.lifehelper.business;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.POICategoryBean;
import com.qz.lifehelper.entity.POIResultBean;
import com.qz.lifehelper.entity.json.POIResultJsonBean;
import com.qz.lifehelper.service.BaiduPOIService;
import com.qz.lifehelper.ui.fragment.POIDetailFragment;
import com.qz.lifehelper.ui.fragment.POIListFragment;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Task;

/**
 * 该类主要负责与POI先相关的业务逻辑
 * <p/>
 * 所有加载到的POI信息都会被缓存，方便POIResultDetail的调用
 */
@EBean
public class POIBusiness {
    // 是否正在加载POI数据
    boolean isLoding = false;

    @RootContext
    Context context;

    @Bean
    BaiduPOIService baiduPOIService;

    private int baiduPoiCurrentPagerNumber = -1;

    /**
     * 获取POI数据
     *
     * @param cityBean     要查询数据的城市
     * @param categoryBean 要查询数据的分类
     * @param count        每页的个数
     * @param lastestItem  当前最后一个数据。用于分页，如果为null，则会加载第一页
     */
    public Task<List<POIResultBean>> getPOIItems(CityBean cityBean, POICategoryBean categoryBean, int count, POIResultBean lastestItem) {
        if (baiduPoiCurrentPagerNumber == -1) {
            baiduPoiCurrentPagerNumber++;
        } else {
            baiduPoiCurrentPagerNumber++;
        }
        return baiduPOIService.getPoiItems(cityBean, categoryBean, count, baiduPoiCurrentPagerNumber);
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

    /**
     * 前往POIListFragment
     */
    public void toPOIListFragment(FragmentTransaction transaction, POICategoryBean categoryBean, CityBean cityBean) {

        POIListFragment fragment = new POIListFragment.Builder()
                .setCategory(categoryBean)
                .setCity(cityBean)
                .create();

        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }

    /**
     * 前往POIDetailFragment
     */
    public void toPOIDetailFragment(FragmentTransaction transaction, POIResultBean poiItemBean) {
        POIDetailFragment fragment = new POIDetailFragment.Builder()
                .setPOItemBean(poiItemBean)
                .create();
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }
}
