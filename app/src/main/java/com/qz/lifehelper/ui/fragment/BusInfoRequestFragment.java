package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.helper.BusInfoHelper;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import bolts.Continuation;
import bolts.Task;

/**
 * 配置要搜索的汽车票的信息
 */
@EFragment(R.layout.fragment_bus_info_request)
public class BusInfoRequestFragment extends Fragment {

    @Bean
    BusInfoHelper busInfoHelper;

    /**
     * 当前选择的出发城市
     */
    private CityBean startCity;
    /**
     * 当前选择的目的城市
     */
    private CityBean endCity;

    @AfterInject
    void setDefaultStartCity() {
        startCity = busInfoHelper.getDefaultStartCity();
    }

    @AfterInject
    void setDefaulteEndCity() {
        endCity = busInfoHelper.getDefaultEndCity();
    }

    /**
     * 设置出发城市信息
     */
    @AfterViews
    void setStartCity() {
        startCityTv.setText(startCity.cityName);
    }

    /**
     * 设置目的城市信息
     */
    @AfterViews
    void setEndCity() {
        endCityTv.setText(endCity.cityName);
    }

    /**
     * 选择出发城市
     */
    @Click(R.id.strat_loaction)
    void chooseStartCity() {
        busInfoHelper.chooseCity().onSuccess(new Continuation<CityBean, Void>() {
            @Override
            public Void then(Task<CityBean> task) throws Exception {
                startCity = task.getResult();
                setStartCity();
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

    }

    /**
     * 选择目的城市
     */
    @Click(R.id.end_loaction)
    void chooseEndCity() {
        busInfoHelper.chooseCity().onSuccess(new Continuation<CityBean, Void>() {
            @Override
            public Void then(Task<CityBean> task) throws Exception {
                endCity = task.getResult();
                setEndCity();
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    /**
     * 搜索大巴的信息
     */
    @Click(R.id.search)
    void searchBusInfo() {
        busInfoHelper.searchBusInfo(startCity, endCity);
    }

    @ViewById(R.id.strat_loaction_tv)
    TextView startCityTv;

    @ViewById(R.id.end_loaction_tv)
    TextView endCityTv;
}
