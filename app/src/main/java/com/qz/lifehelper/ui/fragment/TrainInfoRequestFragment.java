package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.DateBusiness;
import com.qz.lifehelper.entity.TrainStationBean;
import com.qz.lifehelper.helper.TrainInfoHelper;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import bolts.Continuation;
import bolts.Task;

/**
 * 配置要搜索的火车票信息
 */
@EFragment(R.layout.fragment_tarin_info_request)
public class TrainInfoRequestFragment extends Fragment {

    @Bean
    TrainInfoHelper trainInfoHelper;

    /**
     * 当前选择的出发火车站
     */
    private TrainStationBean startStation;
    /**
     * 当前选择的目的地火车站
     */
    private TrainStationBean endStation;

    @AfterInject
    void setDefaultStartStation() {
        startStation = trainInfoHelper.getDefaultStartStation();
    }

    @AfterInject
    void setDefaulteEndStation() {
        endStation = trainInfoHelper.getDefaultEndStation();
    }

    /**
     * 设置出发火车站信息
     */
    @AfterViews
    void setStartStation() {
        startStationTv.setText(startStation.stationName);
    }

    /**
     * 设置目的地火车站信息
     */
    @AfterViews
    void setEndStation() {
        endStationTv.setText(endStation.stationName);
    }

    /**
     * 选择出火车站
     */
    @Click(R.id.strat_loaction)
    void chooseStartLoaction() {
        trainInfoHelper.chooseTrainStation().onSuccess(new Continuation<TrainStationBean, Void>() {
            @Override
            public Void then(Task<TrainStationBean> task) throws Exception {
                startStation = task.getResult();
                setStartStation();
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

    }

    /**
     * 选择目的火车站
     */
    @Click(R.id.end_loaction)
    void chooseEndLoaction() {
        trainInfoHelper.chooseTrainStation().onSuccess(new Continuation<TrainStationBean, Void>() {
            @Override
            public Void then(Task<TrainStationBean> task) throws Exception {
                endStation = task.getResult();
                setEndStation();
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
        trainInfoHelper.chooseDate(currentDate).onSuccess(new Continuation<String, Void>() {
            @Override
            public Void then(Task<String> task) throws Exception {
                dateTv.setText(task.getResult());
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    /**
     * 搜索火车票的信息
     */
    @Click(R.id.search)
    void searchPlaneInfo() {
        String date = dateTv.getText().toString();
        trainInfoHelper.searchTrainInfo(startStation, endStation, date);
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
        dateTv.setText(DateBusiness.getCurrentDate(TrainInfoHelper.dateFormatPattern));
    }
}
