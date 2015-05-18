package com.qz.lifehelper.business;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.TrainInfoBean;
import com.qz.lifehelper.entity.TrainStationBean;
import com.qz.lifehelper.entity.json.JuheResponseJsonBean;
import com.qz.lifehelper.entity.json.TrainInfoJsonBean;
import com.qz.lifehelper.entity.json.TrainStationJsonBean;
import com.qz.lifehelper.persist.TrafficPersist;
import com.qz.lifehelper.service.JuheConstant;
import com.qz.lifehelper.service.TrainOutlineService_;
import com.qz.lifehelper.service.TrainService;
import com.qz.lifehelper.ui.fragment.TrainInfoRequestFragment;
import com.qz.lifehelper.ui.fragment.TrainInfoRequestFragment_;
import com.qz.lifehelper.ui.fragment.TrainInfoResultFragment;
import com.qz.lifehelper.utils.DateUtil;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Task;
import retrofit.RestAdapter;

/**
 * 该类负责处理火车相关的业务逻辑
 */
@EBean
public class TrainBusiness {
    private TrainService trainService;

    @Bean
    TrafficPersist trafficPersist;

    @RootContext
    Context context;

    @Bean
    AppBusiness appBusiness;

    /**
     * 配置TrainService
     * <p/>
     * trainService负责与服务器沟通，获取数据
     */
    @AfterInject
    void setTrainService() {

        if (appBusiness.getDateSourceType().equals(AppBusiness.DATE_SOURCE.OUTLINE)) {
            trainService = TrainOutlineService_.getInstance_(context);
        } else {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(JuheConstant.BASE_URL)
                    .build();
            trainService = restAdapter.create(TrainService.class);
        }
    }

    /**
     * 获取火车站
     * <p/>
     * 这里使用的是离线的json数据
     */
    public Task<List<TrainStationBean>> getStations() {
        return Task.callInBackground(new Callable<List<TrainStationBean>>() {
            @Override
            public List<TrainStationBean> call() throws Exception {
                String stationJson = trafficPersist.getTrainStation();
                Gson gson = new Gson();
                List<TrainStationJsonBean> trainStationJsonBeans = gson.fromJson(stationJson, new TypeToken<List<TrainStationJsonBean>>() {
                }.getType());
                List<TrainStationBean> trainStationBeans = new ArrayList<TrainStationBean>();

                for (TrainStationJsonBean trainStationJsonBean : trainStationJsonBeans) {
                    trainStationBeans.add(TrainStationBean.generateStation(trainStationJsonBean.getSta_name(),
                            trainStationJsonBean.getSta_ename(), trainStationJsonBean.getSta_code()));
                }
                return trainStationBeans;
            }

        });
    }

    /**
     * 获取相应的火车信息
     *
     * @param startCity 始发城市
     * @param endCity   目的城市
     * @param dateStart 出发日期
     */
    public Task<List<TrainInfoBean>> getTrainInfo(final CityBean startCity, final CityBean endCity, final Date dateStart) {
        return Task.callInBackground(new Callable<List<TrainInfoBean>>() {
            @Override
            public List<TrainInfoBean> call() throws Exception {
                JuheResponseJsonBean<TrainInfoJsonBean> responseJsonBean = trainService.getTrainInfo(
                        startCity.cityName,
                        endCity.cityName,
                        DateUtil.formatDate(JuheConstant.QUERY_DATE_FORMAT_PATTERN, dateStart));

                List<TrainInfoJsonBean> trainStationJsonBeans = responseJsonBean.getResult();
                List<TrainInfoBean> trainInfoBeans = new ArrayList<>();
                for (TrainInfoJsonBean trainInfoJsonBean : trainStationJsonBeans) {
                    trainInfoBeans.add(convertToTrainInfoBean(trainInfoJsonBean));
                }

                return trainInfoBeans;
            }
        });
    }

    /**
     * 将TrainInfoJsonBean转换为TrainInfoBean
     */
    private TrainInfoBean convertToTrainInfoBean(TrainInfoJsonBean trainInfoJsonBean) {
        String duration = DateUtil.formatDate("hh时mm分", DateUtil.parseDate("hh:mm", trainInfoJsonBean.getLishi()));
        String surplusTicketCount = getsurplusTicketCount(trainInfoJsonBean) + "张";
        String startStation = trainInfoJsonBean.getFromStationName();
        String endStation = trainInfoJsonBean.getToStationName();
        String startTime = trainInfoJsonBean.getStartTime();
        String endTime = trainInfoJsonBean.getArriveTime();
        String trainInfo = trainInfoJsonBean.getTrainNo() + " " + trainInfoJsonBean.getTrainClassName();

        return new TrainInfoBean()
                .setDuration(duration)
                .setSurplusTicketCount(surplusTicketCount)
                .setStartStation(startStation)
                .setEndStation(endStation)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setTrainInfo(trainInfo);
    }

    /**
     * 计算出余票信息
     * <p/>
     * 由于返回的数据中没有总的余票数据，因此该数据需要在这里计算
     */
    private Integer getsurplusTicketCount(TrainInfoJsonBean trainInfoJsonBean) {
        Integer count = 0;

        try {
            count += Integer.valueOf(trainInfoJsonBean.getQtNum());
        } catch (Exception e) {

        }

        try {
            count += Integer.valueOf(trainInfoJsonBean.getGrNum());
        } catch (Exception e) {

        }

        try {
            count += Integer.valueOf(trainInfoJsonBean.getRwNum());
        } catch (Exception e) {

        }

        try {
            count += Integer.valueOf(trainInfoJsonBean.getRzNum());
        } catch (Exception e) {

        }

        try {
            count += Integer.valueOf(trainInfoJsonBean.getTzNum());
        } catch (Exception e) {

        }

        try {
            count += Integer.valueOf(trainInfoJsonBean.getWzNum());
        } catch (Exception e) {

        }

        try {
            count += Integer.valueOf(trainInfoJsonBean.getYwNum());
        } catch (Exception e) {

        }

        try {
            count += Integer.valueOf(trainInfoJsonBean.getYzNum());
        } catch (Exception e) {

        }

        try {
            count += Integer.valueOf(trainInfoJsonBean.getZeNum());
        } catch (Exception e) {

        }

        try {
            count += Integer.valueOf(trainInfoJsonBean.getZyNum());
        } catch (Exception e) {

        }

        try {
            count += Integer.valueOf(trainInfoJsonBean.getSwzNum());
        } catch (Exception e) {

        }


        return count;
    }

    /**
     * 跳转到TrainInfoRequestFragment
     * <p/>
     * 到该页面设置查询火车票的搜索信息
     */
    public void toTrainInfoRequestFragment(FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        TrainInfoRequestFragment fragment = new TrainInfoRequestFragment_();
        transaction.replace(R.id.fragmrnt_container, fragment);
        transaction.commit();
    }

    /**
     * 获取默认的出发城市
     */
    public CityBean getDefaultStartCity() {
        return CityBean.generateCity("北京");
    }

    /**
     * 获取默认的到达城市
     */
    public CityBean getDefaultEndCity() {
        return CityBean.generateCity("上海");
    }

    /**
     * 这是选择出发日期的日期格式
     */
    public static final String dateFormatPattern = "yyyy'-'MM'-'dd EE";


    /**
     * 跳转到TrainInfoResultFragment，更具参数搜索相应到火车信息
     *
     * @param fragmentManager
     * @param startCity       出发城市
     * @param endCity         目的城市
     * @param date            出发日期
     */
    public void toTrainInfoResultFragment(FragmentManager fragmentManager, CityBean startCity, CityBean endCity, String date) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack("");
        TrainInfoResultFragment fragment = TrainInfoResultFragment.generateFragment(startCity,
                endCity, DateUtil.parseDate(dateFormatPattern, date));
        transaction.replace(R.id.fragmrnt_container, fragment);
        transaction.commit();
    }
}
