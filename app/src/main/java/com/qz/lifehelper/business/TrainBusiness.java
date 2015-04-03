package com.qz.lifehelper.business;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qz.lifehelper.entity.TrainInfoBean;
import com.qz.lifehelper.entity.TrainStationBean;
import com.qz.lifehelper.entity.json.JuheResponseJsonBean;
import com.qz.lifehelper.entity.json.TrainInfoJsonBean;
import com.qz.lifehelper.entity.json.TrainStationJsonBean;
import com.qz.lifehelper.persist.TrafficPersist;
import com.qz.lifehelper.service.JuheConstant;
import com.qz.lifehelper.service.TrainService;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

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

    @AfterInject
    void setTrainService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(JuheConstant.BASE_URL)
                .build();
        trainService = restAdapter.create(TrainService.class);
    }

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

    public Task<List<TrainInfoBean>> getTrainInfo(final TrainStationBean startStation, final TrainStationBean endStation, final Date dateStart) {
        return Task.callInBackground(new Callable<List<TrainInfoBean>>() {
            @Override
            public List<TrainInfoBean> call() throws Exception {
                JuheResponseJsonBean<TrainInfoJsonBean> responseJsonBean = trainService.getTrainInfo(
                        startStation.stationName,
                        endStation.stationName,
                        DateBusiness.formatDate(JuheConstant.QUERY_DATE_FORMAT_PATTERN, dateStart));

                List<TrainInfoJsonBean> trainStationJsonBeans = responseJsonBean.getResult();
                List<TrainInfoBean> trainInfoBeans = new ArrayList<>();
                for (TrainInfoJsonBean trainInfoJsonBean : trainStationJsonBeans) {
                    trainInfoBeans.add(convertToTrainInfoBean(trainInfoJsonBean));
                }

                return trainInfoBeans;
            }
        });
    }

    private TrainInfoBean convertToTrainInfoBean(TrainInfoJsonBean trainInfoJsonBean) {
        String duration = DateBusiness.formatDate("hh时mm分", DateBusiness.parseDate("hh:mm", trainInfoJsonBean.getLishi()));
        String surplusTicketCount = getsurplusTicketCount(trainInfoJsonBean) + "张";
        String startStation = trainInfoJsonBean.getStartStationName();
        String endStation = trainInfoJsonBean.getEndStationName();
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
}
