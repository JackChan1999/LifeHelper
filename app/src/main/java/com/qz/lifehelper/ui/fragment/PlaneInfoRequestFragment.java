package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.DateBusiness;
import com.qz.lifehelper.entity.AirportBean;
import com.qz.lifehelper.helper.PlaneInfoHelper;
import com.qz.lifehelper.helper.TrainInfoHelper;
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
    PlaneInfoHelper planeInfoHelper;

    /**
     * 当前选择的出发机场
     */
    private AirportBean startAirport;
    /**
     * 当前选择的目的地机场
     */
    private AirportBean endAirport;

    @AfterInject
    void setDefaultStartAirport() {
        startAirport = planeInfoHelper.getDefaultStartAirport();
    }

    @AfterInject
    void setDefaulteEndAirport() {
        endAirport = planeInfoHelper.getDefaultEndAirport();
    }

    /**
     * 设置出发机场信息
     */
    @AfterViews
    void setStartAirport() {
        startAirportTv.setText(startAirport.airpory);
    }

    /**
     * 设置目的地机场信息
     */
    @AfterViews
    void setEndAirport() {
        endAirportTv.setText(endAirport.airpory);
    }

    /**
     * 选择出发机场
     */
    @Click(R.id.strat_loaction)
    void chooseStartLoaction() {
        planeInfoHelper.chooseAirport().onSuccess(new Continuation<AirportBean, Void>() {
            @Override
            public Void then(Task<AirportBean> task) throws Exception {
                startAirport = task.getResult();
                setStartAirport();
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

    }

    /**
     * 选择目的机场
     */
    @Click(R.id.end_loaction)
    void chooseEndLoaction() {
        planeInfoHelper.chooseAirport().onSuccess(new Continuation<AirportBean, Void>() {
            @Override
            public Void then(Task<AirportBean> task) throws Exception {
                endAirport = task.getResult();
                setEndAirport();
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
        dateBusiness.chooseDate(DateUtil.parseDate(PlaneInfoHelper.dateFormatPattern, currentDate),
                getFragmentManager()).onSuccess(new Continuation<Date, Void>() {
            @Override
            public Void then(Task<Date> task) throws Exception {
                Date chooseDate = task.getResult();
                dateTv.setText(DateUtil.formatDate(TrainInfoHelper.dateFormatPattern, chooseDate));
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
        planeInfoHelper.searchPlaneInfo(startAirport, endAirport, date);
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
        dateTv.setText(DateUtil.getCurrentDate(PlaneInfoHelper.dateFormatPattern));
    }
}
