package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.ListFragment;

import com.qz.lifehelper.business.PlaneBusiness;
import com.qz.lifehelper.entity.AirPortBean;
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
public class PlaneInfoFragment extends ListFragment {

    private AirPortBean statrAirport;
    private AirPortBean endAirport;
    private Date dateFly;


    public static PlaneInfoFragment generateFragment(AirPortBean startAirport, AirPortBean endAirport, Date dateFly) {

        PlaneInfoFragment fragment = new PlaneInfoFragment_();
        fragment.statrAirport = startAirport;
        fragment.endAirport = endAirport;
        fragment.dateFly = dateFly;
        return fragment;
    }

    @Bean
    PlaneBusiness planeBusiness;

    @Bean
    PlaneInfoAdapter adapter;

    List<PlaneInfoBean> data = new ArrayList<>();

    @AfterViews
    public void setListView() {
        setListAdapter(adapter);

        planeBusiness.getPlaneInfo(statrAirport, endAirport, dateFly).onSuccess(new Continuation<List<PlaneInfoBean>, Object>() {
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
