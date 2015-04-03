package com.qz.lifehelper.helper;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.TrainStationBean;
import com.qz.lifehelper.event.GetStationEvent;
import com.qz.lifehelper.ui.fragment.ChooseTrainStationFragment;
import com.qz.lifehelper.ui.fragment.ChooseTrainStationFragment_;
import com.qz.lifehelper.ui.fragment.TrainInfoRequestFragment;
import com.qz.lifehelper.ui.fragment.TrainInfoRequestFragment_;
import com.qz.lifehelper.ui.fragment.TrainInfoResultFragment;
import com.qz.lifehelper.utils.DateUtil;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;

import bolts.Task;
import de.greenrobot.event.EventBus;

/**
 * 该类帮助TrainInfoActivity,TrainInfoRequestFragment,TrainInfoResultFragment,
 * ChooseTrainStationFragment实现业务逻辑
 */
@EBean(scope = EBean.Scope.Singleton)
public class TrainInfoHelper {
    private FragmentManager fragmentManager;

    /**
     * 设置FragmentManger
     * <p/>
     * 因为TrainInfoHelper是singleton的，所以fragmentManager可以被正常使用
     */
    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    /**
     * 跳转到TrainInfoRequestFragment
     * <p/>
     * 到该页面设置查询火车票的搜索信息
     */
    public void setTrainInfoSearchArgument() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        TrainInfoRequestFragment fragment = new TrainInfoRequestFragment_();
        transaction.replace(R.id.fragmrnt_container, fragment);
        transaction.commit();
    }

    /**
     * 获取默认的出发火车站
     */
    public TrainStationBean getDefaultStartStation() {
        return TrainStationBean.generateStation("北京", "beijing", "BJP");
    }

    /**
     * 获取默认的到达火车站
     */
    public TrainStationBean getDefaultEndStation() {
        return TrainStationBean.generateStation("上海", "shanghai", "SHH");
    }

    @AfterInject
    void reigisterEventBus() {
        EventBus.getDefault().register(this);
    }

    private Task<TrainStationBean>.TaskCompletionSource chooseTrainStationTask;

    /**
     * 选择火车站
     * <p/>
     * 会跳转到ChooseTrainStation
     */
    public Task<TrainStationBean> chooseTrainStation() {
        chooseTrainStationTask = Task.create();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack("");
        ChooseTrainStationFragment fragment = new ChooseTrainStationFragment_();
        transaction.replace(R.id.fragmrnt_container, fragment);
        transaction.commit();

        return chooseTrainStationTask.getTask();
    }

    /**
     * 当接受到GetStationEvent后，设置选择火车站成功
     * <p/>
     * ChooseStationFragment会发出该Event，通知选择火车站成功
     */
    public void onEvent(GetStationEvent event) {
        if (chooseTrainStationTask != null) {
            chooseTrainStationTask.setResult(event.trainStationBean);
            chooseTrainStationTask = null;
        }

    }

    /**
     * 这是选择出发日期的日期格式
     */
    public static final String dateFormatPattern = "yyyy'-'MM'-'dd EE";


    /**
     * 跳转到TrainInfoResultFragment，更具参数搜索相应到火车信息
     *
     * @param startStation 出发火车站
     * @param endStation   目的地火车站
     * @param date         出发日期
     */
    public void searchTrainInfo(TrainStationBean startStation, TrainStationBean endStation, String date) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack("");
        TrainInfoResultFragment fragment = TrainInfoResultFragment.generateFragment(startStation,
                endStation, DateUtil.parseDate(dateFormatPattern, date));
        transaction.replace(R.id.fragmrnt_container, fragment);
        transaction.commit();
    }

    /**
     * 设置选择的火车站
     * <p/>
     * ChosoeSTraintationFragment会使用该方法，设置用户选择的火车站
     */
    public void setStation(TrainStationBean trainStationBean) {
        fragmentManager.popBackStack();
        EventBus.getDefault().post(GetStationEvent.generateEvent(trainStationBean));
    }
}
