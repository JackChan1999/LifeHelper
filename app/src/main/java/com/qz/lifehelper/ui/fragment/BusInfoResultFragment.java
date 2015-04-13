package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.ListFragment;

import com.qz.lifehelper.business.BusBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.json.BusInfoBean;
import com.qz.lifehelper.ui.adapter.BusInfoAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * 长途大巴搜索结果页
 */
@EFragment
public class BusInfoResultFragment extends ListFragment {

    /**
     * 出发城市
     */
    private CityBean startCity;
    /**
     * 目的城市
     */
    private CityBean endCity;


    /**
     * 生成BusInfoResultFragment
     *
     * @param startCity 出发城市
     * @param endCity   目的城市
     */
    public static BusInfoResultFragment generateFragment(CityBean startCity, CityBean endCity) {

        BusInfoResultFragment fragment = new BusInfoResultFragment_();
        fragment.startCity = startCity;
        fragment.endCity = endCity;
        return fragment;
    }

    @Bean
    BusBusiness busBusiness;

    @Bean
    BusInfoAdapter adapter;

    List<BusInfoBean> data = new ArrayList<>();

    /**
     * 配置长途大巴信息搜索结果列表
     */
    @AfterViews
    public void setListView() {
        setListAdapter(adapter);

        busBusiness.getBusInfo(startCity, endCity).onSuccess(new Continuation<List<BusInfoBean>, Void>() {
            @Override
            public Void then(Task<List<BusInfoBean>> task) throws Exception {
                data.clear();
                data.addAll(task.getResult());
                adapter.setData(data);
                adapter.notifyDataSetChanged();
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }
}
