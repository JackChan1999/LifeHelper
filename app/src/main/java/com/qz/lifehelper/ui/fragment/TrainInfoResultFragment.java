package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.ListFragment;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.TrainBusiness;
import com.qz.lifehelper.entity.TrainInfoBean;
import com.qz.lifehelper.entity.TrainStationBean;
import com.qz.lifehelper.ui.adapter.TrainInfoAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * 火车票信息搜索结果页
 */
@EFragment(R.layout.layout_listview)
public class TrainInfoResultFragment extends ListFragment {

    /**
     * 出发火车站
     */
    private TrainStationBean startStation;
    /**
     * 目的地火车站
     */
    private TrainStationBean endStation;
    /**
     * 出发日期
     */
    private Date dateStart;


    /**
     * 生成TrainInfoResultFragment
     *
     * @param startStation 出发火车站
     * @param endStation   目的地火车站
     * @param dateStart    出发日期
     */
    public static TrainInfoResultFragment generateFragment(TrainStationBean startStation, TrainStationBean endStation, Date dateStart) {

        TrainInfoResultFragment fragment = new TrainInfoResultFragment_();
        fragment.startStation = startStation;
        fragment.endStation = endStation;
        fragment.dateStart = dateStart;
        return fragment;
    }

    @Bean
    TrainBusiness trainBusiness;

    @Bean
    TrainInfoAdapter adapter;

    List<TrainInfoBean> data = new ArrayList<>();

    /**
     * 配置火车票信息搜索结果列表
     */
    @AfterViews
    public void setListView() {
        setListAdapter(adapter);

        trainBusiness.getTrainInfo(startStation, endStation, dateStart).onSuccess(new Continuation<List<TrainInfoBean>, Void>() {
            @Override
            public Void then(Task<List<TrainInfoBean>> task) throws Exception {
                data.clear();
                data.addAll(task.getResult());
                adapter.setData(data);
                adapter.notifyDataSetChanged();
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }
}
