package com.qz.lifehelper.ui.fragment;


import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.PlaneBusiness;
import com.qz.lifehelper.entity.AirportBean;
import com.qz.lifehelper.helper.PlaneInfoHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * 选择机场页
 */
@EFragment(R.layout.layout_listview)
public class ChooseAirportFragment extends ListFragment {

    @Bean
    PlaneInfoHelper planeInfoHelper;

    @Bean
    PlaneBusiness planeBusiness;

    /**
     * 配置机场列表页面
     */
    @AfterViews
    void setListView() {
        final ListView listView = getListView();
        final ArrayAdapter<AirportBean> adapter = new ArrayAdapter<AirportBean>(getActivity(), android.R.layout.simple_list_item_1);
        this.setListAdapter(adapter);
        planeBusiness.getAirports().onSuccess(new Continuation<List<AirportBean>, Object>() {
            @Override
            public Object then(Task<List<AirportBean>> task) throws Exception {
                final List<AirportBean> airports = task.getResult();
                adapter.addAll(airports);
                adapter.notifyDataSetChanged();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        planeInfoHelper.setAirPort(airports.get(position));
                    }
                });
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }


}
