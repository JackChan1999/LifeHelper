package com.qz.lifehelper.business;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qz.lifehelper.entity.AirportBean;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.PlaneInfoBean;
import com.qz.lifehelper.entity.json.AirportJsonBean;
import com.qz.lifehelper.entity.json.PlaneInfoJsonBean;
import com.qz.lifehelper.persist.TrafficPersist;
import com.qz.lifehelper.service.JuheConstant;
import com.qz.lifehelper.service.PlaneService;
import com.qz.lifehelper.utils.DateUtil;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Task;
import retrofit.RestAdapter;

/**
 * 处理航班信息相关的业务逻辑
 */
@EBean
public class PlaneBusiness {

    @Bean
    TrafficPersist trafficPersist;

    private PlaneService planeService;

    /**
     * 设置PlaneService，PlaneService负责与服务器的链接
     */
    @AfterInject
    void setPlaneService() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(JuheConstant.BASE_URL).build();
        planeService = restAdapter.create(PlaneService.class);
    }

    /**
     * 获取机场列表
     */
    public Task<List<AirportBean>> getAirports() {
        return Task.callInBackground(new Callable<List<AirportBean>>() {
            @Override
            public List<AirportBean> call() throws Exception {
                String airportJson = trafficPersist.getAirport();
                Gson gson = new Gson();
                List<AirportJsonBean> airportJsonBeans = gson.fromJson(airportJson, new TypeToken<List<AirportJsonBean>>() {
                }.getType());

                List<AirportBean> airportBeans = new ArrayList<AirportBean>();
                for (AirportJsonBean airportJsonBean : airportJsonBeans) {
                    airportBeans.add(AirportBean.generateAirport(airportJsonBean.getCity(), airportJsonBean.getSpell()));
                }
                return airportBeans;
            }
        });
    }

    /**
     * 获取航班信息
     *
     * @param statrCity 出发机场
     * @param endCity   目的地机场
     * @param dateFly      出发日期
     */
    public Task<List<PlaneInfoBean>> getPlaneInfo(final CityBean statrCity, final CityBean endCity, final Date dateFly) {
        return Task.callInBackground(new Callable<List<PlaneInfoBean>>() {
            @Override
            public List<PlaneInfoBean> call() throws Exception {
                String date = DateUtil.formatDate(JuheConstant.QUERY_DATE_FORMAT_PATTERN, dateFly);
                String start = statrCity.cityName;
                String end = endCity.cityName;
                List<PlaneInfoJsonBean> planeInfoJsonBeans = planeService.getPlaneInfo(start, end, date).getResult();
                List<PlaneInfoBean> planeInfoBeans = new ArrayList<PlaneInfoBean>();
                for (PlaneInfoJsonBean planeInfoJsonBean : planeInfoJsonBeans) {
                    planeInfoBeans.add(convert2PlaneInfoBena(planeInfoJsonBean));
                }

                return planeInfoBeans;
            }
        });
    }

    /**
     * JsonBena与Bean之间的转换
     */
    private PlaneInfoBean convert2PlaneInfoBena(PlaneInfoJsonBean planeInfoJsonBean) throws ParseException {
        String planeInfo = planeInfoJsonBean.getAirline() + " " + planeInfoJsonBean.getFlightNum();
        Date dactualDate = DateUtil.parseDate(JuheConstant.RESPONSE_DATE_FORMAT_PATTERN, planeInfoJsonBean.getDepTime());
        Date aactualDate = DateUtil.parseDate(JuheConstant.RESPONSE_DATE_FORMAT_PATTERN, planeInfoJsonBean.getArrTime());
        String startTime = DateUtil.formatDate("hh:mm", dactualDate);
        String endTime = DateUtil.formatDate("hh:mm", aactualDate);
        String startAirport = planeInfoJsonBean.getDepCity() + " " + planeInfoJsonBean.getDepTerminal();
        String endAirport = planeInfoJsonBean.getArrCity() + " " + planeInfoJsonBean.getArrTerminal();
        String onTimeRate = planeInfoJsonBean.getOnTimeRate();
        PlaneInfoBean planeInfoBean = new PlaneInfoBean()
                .setPlaneInfo(planeInfo)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setStartAirport(startAirport)
                .setEndAirport(endAirport)
                .setOnTimeRate(onTimeRate);
        return planeInfoBean;
    }
}
