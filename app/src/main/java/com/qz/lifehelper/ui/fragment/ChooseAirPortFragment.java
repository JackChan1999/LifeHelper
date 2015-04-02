package com.qz.lifehelper.ui.fragment;


import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.PlaneBusiness;
import com.qz.lifehelper.entity.AirPortBean;
import com.qz.lifehelper.helper.SearchPlaneHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * 选择机场
 */
@EFragment(R.layout.layout_listview)
public class ChooseAirPortFragment extends ListFragment {

    @Bean
    SearchPlaneHelper searchPlaneHelper;

    @Bean
    PlaneBusiness planeBusiness;

    @AfterViews
    void setListView() {
        final ListView listView = getListView();
        final ArrayAdapter<AirPortBean> adapter = new ArrayAdapter<AirPortBean>(getActivity(), android.R.layout.simple_list_item_1);
        this.setListAdapter(adapter);
        planeBusiness.getAirports().onSuccess(new Continuation<List<AirPortBean>, Object>() {
            @Override
            public Object then(Task<List<AirPortBean>> task) throws Exception {
                final List<AirPortBean> airports = task.getResult();
                adapter.addAll(airports);
                adapter.notifyDataSetChanged();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        searchPlaneHelper.setAirPort(airports.get(position));
                    }
                });
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }


}
