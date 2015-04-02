package com.qz.lifehelper.business;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qz.lifehelper.entity.AirPortBean;
import com.qz.lifehelper.entity.PlaneInfoBean;
import com.qz.lifehelper.entity.PlaneInfoJsonBean;
import com.qz.lifehelper.entity.json.AirportJsonBean;
import com.qz.lifehelper.persist.TrafficPersist;
import com.qz.lifehelper.service.PlaneService;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @AfterInject
    void setPlaneService() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(JuheConstant.BASE_URL).build();
        planeService = restAdapter.create(PlaneService.class);
    }

    public Task<List<AirPortBean>> getAirports() {
        return Task.callInBackground(new Callable<List<AirPortBean>>() {
            @Override
            public List<AirPortBean> call() throws Exception {
                String airportJson = trafficPersist.getAirport();
                Gson gson = new Gson();
                List<AirportJsonBean> airportJsonBeans = gson.fromJson(airportJson, new TypeToken<List<AirportJsonBean>>() {
                }.getType());

                List<AirPortBean> airPortBeans = new ArrayList<AirPortBean>();
                for (AirportJsonBean airportJsonBean : airportJsonBeans) {
                    airPortBeans.add(AirPortBean.generateAirport(airportJsonBean.getCity(), airportJsonBean.getSpell()));
                }
                return airPortBeans;
            }
        });
    }

    public Task<List<PlaneInfoBean>> getPlaneInfo(final AirPortBean statrAirport, final AirPortBean endAirport, final Date dateFly) {
        return Task.callInBackground(new Callable<List<PlaneInfoBean>>() {
            @Override
            public List<PlaneInfoBean> call() throws Exception {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(JuheConstant.QUERY_DATE_FORMAT_PATTERN);
                String date = simpleDateFormat.format(dateFly);
                String start = statrAirport.airpory;
                String end = endAirport.airpory;
                List<PlaneInfoJsonBean> planeInfoJsonBeans = planeService.getPlaneInfo(start, end, date).getResult();
                List<PlaneInfoBean> planeInfoBeans = new ArrayList<PlaneInfoBean>();
                for (PlaneInfoJsonBean planeInfoJsonBean : planeInfoJsonBeans) {
                    planeInfoBeans.add(convert2PlaneInfoBena(planeInfoJsonBean));
                }

                return planeInfoBeans;
            }
        });
    }

    private PlaneInfoBean convert2PlaneInfoBena(PlaneInfoJsonBean planeInfoJsonBean) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        String planeInfo = planeInfoJsonBean.getComplany() + " " + planeInfoJsonBean.getAirModel();
        simpleDateFormat.applyPattern(JuheConstant.RESPONSE_DATE_FORMAT_PATTERN);
        Date dactualDate = simpleDateFormat.parse(planeInfoJsonBean.getDepTime());
        Date aactualDate = simpleDateFormat.parse(planeInfoJsonBean.getArrTime());
        simpleDateFormat.applyPattern("hh:mm");
        String startTime = simpleDateFormat.format(dactualDate);
        String endTime = simpleDateFormat.format(aactualDate);
        String startAirport = planeInfoJsonBean.getDepAirport();
        if (startAirport.equals("")) {
            startAirport = planeInfoJsonBean.getStart();
        }
        String endAirport = planeInfoJsonBean.getArrAirport();
        if (endAirport.equals("")) {
            endAirport = planeInfoJsonBean.getEnd();
        }
        String planeState = planeInfoJsonBean.getStatus();
        PlaneInfoBean planeInfoBean = new PlaneInfoBean()
                .setPlaneInfo(planeInfo)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setStartAirport(startAirport)
                .setEndAirport(endAirport)
                .setPlaneState(planeState);
        return planeInfoBean;
    }
}
