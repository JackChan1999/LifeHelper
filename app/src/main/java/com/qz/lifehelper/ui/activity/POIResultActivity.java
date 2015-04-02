package com.qz.lifehelper.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.POIBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.POIResultBean;
import com.qz.lifehelper.ui.adapter.POIResultListAdpater;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * POI搜索结果页
 */
@EActivity(R.layout.activity_poi_result)
public class POIResultActivity extends ActionBarActivity {

    @ViewById(R.id.poi_result_lv)
    public ListView poiResultLv;

    public static final String LOCATION = "LOCATION";
    public static final String CATEGORY = "CATEGORY";

    @Extra(LOCATION)
    String currentLocation;

    @Extra(CATEGORY)
    String category;

    /**
     * 生成跳转到POIResultActivity的Intent
     *
     * @param location 查询信息的位置
     * @param category 查询信息的类别
     */
    static public Intent generateIntent(Context context, String location, String category) {
        Intent intent = new Intent(context, POIResultActivity_.class);
        intent.putExtra(LOCATION, location);
        intent.putExtra(CATEGORY, category);
        return intent;
    }

    @Bean
    POIBusiness poiBusiness;

    @Bean
    POIResultListAdpater adpater;

    /**
     * 设置POI结果列表
     */
    @AfterViews
    public void setPoiResltLv() {
        poiResultLv.setAdapter(adpater);
        poiBusiness.loadPOIData(CityBean.generateCity(currentLocation), category).onSuccess(
                new Continuation<List<POIResultBean>, Void>() {
                    @Override
                    public Void then(Task<List<POIResultBean>> task) throws Exception {
                        List<POIResultBean> poiResultBeans = task.getResult();
                        if (poiResultBeans == null || poiResultBeans.size() == 0) {
                            onLoadPOIDataFial();
                        } else {
                            adpater.setData(poiResultBeans);
                            adpater.notifyDataSetChanged();
                            onLoadPOIDataSuccess();
                        }
                        return null;
                    }
                }, Task.UI_THREAD_EXECUTOR);
        onStarLoadPOIData();
    }

    /**
     * 当开始加载数据时被调用
     */
    void onStarLoadPOIData() {

    }

    /**
     * 当成功加载数据时被调用
     */
    void onLoadPOIDataSuccess() {

    }

    /**
     * 当加载数据失败时被调用
     */
    void onLoadPOIDataFial() {

    }

}
