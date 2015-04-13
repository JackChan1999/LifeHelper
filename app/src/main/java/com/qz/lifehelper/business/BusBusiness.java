package com.qz.lifehelper.business;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.json.BusInfoBean;
import com.qz.lifehelper.entity.json.BusInfoJsonBean;
import com.qz.lifehelper.entity.json.JuheResponseJsonBean2;
import com.qz.lifehelper.entity.json.TrainStationJsonBean;
import com.qz.lifehelper.persist.TrafficPersist;
import com.qz.lifehelper.service.BusService;
import com.qz.lifehelper.service.JuheConstant;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Task;
import retrofit.RestAdapter;

/**
 * 该类负责处理长途大巴相关的业务逻辑
 */
@EBean
public class BusBusiness {
    private BusService busService;

    /**
     * 配置BusService
     * <p/>
     * busService负责与服务器沟通，获取数据
     */
    @AfterInject
    void setBusService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(JuheConstant.BASE_URL2)
                .build();
        busService = restAdapter.create(BusService.class);
    }


    /**
     * 获取相应的大巴信息
     *
     * @param startCity 始发城市
     * @param endCity   目的城市
     */
    public Task<List<BusInfoBean>> getBusInfo(final CityBean startCity, final CityBean endCity) {
        return Task.callInBackground(new Callable<List<BusInfoBean>>() {
            @Override
            public List<BusInfoBean> call() throws Exception {
                JuheResponseJsonBean2<BusInfoJsonBean> responseJsonBean = busService.getBusInfo(
                        startCity.cityName,
                        endCity.cityName);

                List<BusInfoJsonBean> busInfoJsonBeans = responseJsonBean.getResult().getList();
                List<BusInfoBean> busInfoBeans = new ArrayList<>();
                for (BusInfoJsonBean busInfoJsonBean : busInfoJsonBeans) {
                    busInfoBeans.add(convertToBusInfoBean(busInfoJsonBean));
                }

                return busInfoBeans;
            }
        });
    }

    /**
     * 将TrainInfoJsonBean转换为TrainInfoBean
     */
    private BusInfoBean convertToBusInfoBean(BusInfoJsonBean busInfoJsonBean) {

        String startStation = busInfoJsonBean.getStart();
        String endStation = busInfoJsonBean.getArrive();
        String startTime = busInfoJsonBean.getDate();
        String ticketPrice = busInfoJsonBean.getPrice();

        return new BusInfoBean()
                .setStartStation(startStation)
                .setEndStation(endStation)
                .setStartTime(startTime)
                .setTicketPrice(ticketPrice);
    }

    @Bean
    TrafficPersist trafficPersist;

    /**
     * 获取城市列表
     */
    public Task<List<CityBean>> getBusCity() {
        return Task.callInBackground(new Callable<List<CityBean>>() {
            @Override
            public List<CityBean> call() throws Exception {
                String stationJson = trafficPersist.getTrainStation();
                Gson gson = new Gson();
                List<TrainStationJsonBean> trainStationJsonBeans = gson.fromJson(stationJson, new TypeToken<List<TrainStationJsonBean>>() {
                }.getType());
                List<CityBean> cityBeans = new ArrayList<CityBean>();

                for (TrainStationJsonBean trainStationJsonBean : trainStationJsonBeans) {
                    cityBeans.add(CityBean.generateCity(trainStationJsonBean.getSta_name()));
                }
                return cityBeans;
            }

        });
    }
}
