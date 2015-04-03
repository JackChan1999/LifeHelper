package com.qz.lifehelper.ui.fragment;


import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.qz.lifehelper.business.TrainBusiness;
import com.qz.lifehelper.entity.TrainStationBean;
import com.qz.lifehelper.helper.TrainInfoHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * 选择火车站页面
 */
@EFragment
public class ChooseTrainStationFragment extends ListFragment {

    @Bean
    TrainInfoHelper trainInfoHelper;

    @Bean
    TrainBusiness trainBusiness;

    private List<TrainStationBean> trainStationBeans = new ArrayList<>();

    /**
     * 配置火车站
     */
    @AfterViews
    void setListView() {
        final ArrayAdapter<TrainStationBean> adapter = new ArrayAdapter<TrainStationBean>(getActivity(), android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        trainBusiness.getStations().onSuccess(new Continuation<List<TrainStationBean>, Void>() {
            @Override
            public Void then(Task<List<TrainStationBean>> task) throws Exception {
                trainStationBeans.addAll(task.getResult());
                adapter.addAll(trainStationBeans);
                adapter.notifyDataSetChanged();
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        trainInfoHelper.setStation(trainStationBeans.get(position));
    }
}
