package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.DateBusiness;
import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.business.TrainBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.utils.DateUtil;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

import bolts.Continuation;
import bolts.Task;

/**
 * 配置要搜索的火车票信息
 */
@EFragment(R.layout.fragment_tarin_info_request)
public class TrainInfoRequestFragment extends Fragment {

    @Bean
    TrainBusiness trainBusiness;

    /**
     * 当前选择的出发火车站
     */
    private CityBean startCity;
    /**
     * 当前选择的目的地火车站
     */
    private CityBean endCity;

    @AfterInject
    void setDefaultStartCity() {
        startCity = trainBusiness.getDefaultStartCity();
    }

    @AfterInject
    void setDefaulteEndCity() {
        endCity = trainBusiness.getDefaultEndCity();
    }

    /**
     * 设置出发火车站信息
     */
    @AfterViews
    void setStartCity() {
        startStationTv.setText(startCity.cityName);
    }

    /**
     * 设置目的地火车站信息
     */
    @AfterViews
    void setEndCity() {
        endStationTv.setText(endCity.cityName);
    }

    /**
     * 选择出发城市
     */
    @Click(R.id.strat_loaction)
    void chooseStartCity() {
        locationBusiness.chooseCity(this.getFragmentManager()).onSuccess(new Continuation<CityBean, Void>() {
            @Override
            public Void then(Task<CityBean> task) throws Exception {
                startCity = task.getResult();
                setStartCity();
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

    }

    @Bean
    LocationBusiness locationBusiness;

    /**
     * 选择目的火车站
     */
    @Click(R.id.end_loaction)
    void chooseEndCity() {
        locationBusiness.chooseCity(this.getFragmentManager()).onSuccess(new Continuation<CityBean, Void>() {
            @Override
            public Void then(Task<CityBean> task) throws Exception {
                endCity = task.getResult();
                setEndCity();
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    @Bean
    DateBusiness dateBusiness;

    /**
     * 选择出发日期
     */
    @Click(R.id.choose_date)
    void chooseDate() {
        String currentDate = dateTv.getText().toString();
        dateBusiness.chooseDate(DateUtil.parseDate(TrainBusiness.dateFormatPattern, currentDate),
                getFragmentManager()).onSuccess(new Continuation<Date, Void>() {
            @Override
            public Void then(Task<Date> task) throws Exception {
                Date chooseDate = task.getResult();
                dateTv.setText(DateUtil.formatDate(TrainBusiness.dateFormatPattern, chooseDate));
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    /**
     * 搜索火车票的信息
     */
    @Click(R.id.search)
    void searchTarinInfo() {
        String date = dateTv.getText().toString();
        trainBusiness.toTrainInfoResultFragment(getFragmentManager(), startCity, endCity, date);
    }

    @ViewById(R.id.strat_loaction_tv)
    TextView startStationTv;

    @ViewById(R.id.end_loaction_tv)
    TextView endStationTv;

    @ViewById(R.id.date_tv)
    TextView dateTv;

    /**
     * 设置默认当出发日期
     * <p/>
     * 也就是设置当前当日期
     */
    @AfterViews
    void setDefaultDate() {
        dateTv.setText(DateUtil.getCurrentDate(TrainBusiness.dateFormatPattern));
    }

    @Click(R.id.exchange_iv)
    void exchangeCity() {
        CityBean tempCity = startCity;
        startCity = endCity;
        endCity = tempCity;
        setEndCity();
        setStartCity();
    }

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @AfterViews
    void setToolbar() {
        TextView titleTv = (TextView) toolbar.findViewById(R.id.title_tv);
        titleTv.setText("搜索火车票");
    }
}
