package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.DateBusiness;
import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.business.PlaneBusiness;
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
 * 航班信息搜索参数填写页面
 * 在该页面设置要搜索的相关信息，然后根据这些信息搜索航班的信息
 */
@EFragment(R.layout.fragment_plane_info_request)
public class PlaneInfoRequestFragment extends Fragment {

    @Bean
    PlaneBusiness planeBusiness;

    /**
     * 当前选择的出发机场
     */
    private CityBean startCity;
    /**
     * 当前选择的目的地机场
     */
    private CityBean endCity;

    @AfterInject
    void setDefaultStartCity() {
        startCity = planeBusiness.getDefaultStartCity();
    }

    @AfterInject
    void setDefaulteEndCity() {
        endCity = planeBusiness.getDefaultEndCity();
    }

    /**
     * 设置出发城市
     */
    @AfterViews
    void setStartCity() {
        startAirportTv.setText(startCity.cityName);
    }

    /**
     * 设置目的城市
     */
    @AfterViews
    void setEndCity() {
        endAirportTv.setText(endCity.cityName);
    }

    @Bean
    LocationBusiness locationBusiness;

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

    /**
     * 选择目的城市
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
        dateBusiness.chooseDate(DateUtil.parseDate(PlaneBusiness.dateFormatPattern, currentDate),
                getFragmentManager()).onSuccess(new Continuation<Date, Void>() {
            @Override
            public Void then(Task<Date> task) throws Exception {
                Date chooseDate = task.getResult();
                dateTv.setText(DateUtil.formatDate(PlaneBusiness.dateFormatPattern, chooseDate));
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    /**
     * 搜索航班的信息
     */
    @Click(R.id.search)
    void searchPlaneInfo() {
        String date = dateTv.getText().toString();
        planeBusiness.toPlaneInfoResultFragment(getFragmentManager(), startCity, endCity, date);
    }

    @ViewById(R.id.strat_loaction_tv)
    TextView startAirportTv;

    @ViewById(R.id.end_loaction_tv)
    TextView endAirportTv;

    @ViewById(R.id.date_tv)
    TextView dateTv;

    /**
     * 设置默认当出发日期
     * <p/>
     * 也就是设置当前当日期
     */
    @AfterViews
    void setDefaultDate() {
        dateTv.setText(DateUtil.getCurrentDate(PlaneBusiness.dateFormatPattern));
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
        titleTv.setText("搜索飞机票");
    }
}
