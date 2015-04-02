package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.AirPortBean;
import com.qz.lifehelper.helper.SearchPlaneHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import bolts.Continuation;
import bolts.Task;

/**
 * 机票时时信息搜索
 */
@EFragment(R.layout.fragment_search_plane)
public class SearchPlaneFragment extends Fragment {

    @Bean
    SearchPlaneHelper searchPlaneHelper;

    private AirPortBean startAirport = AirPortBean.generateAirport("北京", "PEK");
    private AirPortBean endAirport = AirPortBean.generateAirport("上海", "SHA");

    @AfterViews
    void setStartAirport() {
        startAirportTv.setText(startAirport.airpory);
    }

    @AfterViews
    void setEndAirport() {
        endAirportTv.setText(endAirport.airpory);
    }

    /**
     * 选择出发机场
     */
    @Click(R.id.strat_loaction)
    void chooseStartLoaction() {
        searchPlaneHelper.chooseAirport().onSuccess(new Continuation<AirPortBean, Void>() {
            @Override
            public Void then(Task<AirPortBean> task) throws Exception {
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
        searchPlaneHelper.chooseAirport().onSuccess(new Continuation<AirPortBean, Void>() {
            @Override
            public Void then(Task<AirPortBean> task) throws Exception {
                endAirport = task.getResult();
                setEndAirport();
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    /**
     * 选择出发日期
     */
    @Click(R.id.choose_date)
    void chooseDate() {
        String currentDate = dateTv.getText().toString();
        searchPlaneHelper.chooseDate(currentDate).onSuccess(new Continuation<String, Void>() {
            @Override
            public Void then(Task<String> task) throws Exception {
                dateTv.setText(task.getResult());
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    /**
     * 搜索航班日期
     */
    @Click(R.id.search)
    void searchPlaneInfo() {
        String date = dateTv.getText().toString();
        searchPlaneHelper.searchPlaneInfo(startAirport, endAirport, date);
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
        dateTv.setText(searchPlaneHelper.getCurrentDate());
    }
}
