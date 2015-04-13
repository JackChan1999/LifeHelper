package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.ListFragment;

import com.qz.lifehelper.business.PlaneBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.PlaneInfoBean;
import com.qz.lifehelper.ui.adapter.PlaneInfoAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * 航班信息结果页
 */
@EFragment
public class PlaneInfoResultFragment extends ListFragment {

    /**
     * 出发机场
     */
    private CityBean statrCity;
    /**
     * 目的地机场
     */
    private CityBean endCity;
    /**
     * 出发日期
     */
    private Date dateFly;


    /**
     * 生成PlaneInfoResultFragment
     *
     * @param startCity 出发机场
     * @param endCity   目的地机场
     * @param dateFly      出发日期
     */
    public static PlaneInfoResultFragment generateFragment(CityBean startCity, CityBean endCity, Date dateFly) {

        PlaneInfoResultFragment fragment = new PlaneInfoResultFragment_();
        fragment.statrCity = startCity;
        fragment.endCity = endCity;
        fragment.dateFly = dateFly;
        return fragment;
    }

    @Bean
    PlaneBusiness planeBusiness;

    @Bean
    PlaneInfoAdapter adapter;

    List<PlaneInfoBean> data = new ArrayList<>();

    /**
     * 配置航班信息搜索结果列表
     */
    @AfterViews
    public void setListView() {
        setListAdapter(adapter);

        planeBusiness.getPlaneInfo(statrCity, endCity, dateFly).onSuccess(new Continuation<List<PlaneInfoBean>, Object>() {
            @Override
            public Object then(Task<List<PlaneInfoBean>> task) throws Exception {
                data.clear();
                data.addAll(task.getResult());
                adapter.setData(data);
                adapter.notifyDataSetChanged();
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }
}
