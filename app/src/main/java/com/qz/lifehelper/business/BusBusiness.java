package com.qz.lifehelper.business;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.json.BusInfoBean;
import com.qz.lifehelper.entity.json.BusInfoJsonBean;
import com.qz.lifehelper.entity.json.JuheResponseJsonBean2;
import com.qz.lifehelper.service.BusOutlineService_;
import com.qz.lifehelper.service.BusService;
import com.qz.lifehelper.service.JuheConstant;
import com.qz.lifehelper.ui.fragment.BusInfoRequestFragment;
import com.qz.lifehelper.ui.fragment.BusInfoRequestFragment_;
import com.qz.lifehelper.ui.fragment.BusInfoResultFragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

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

    @RootContext
    Context context;

    @Bean
    AppBusiness appBusiness;

    /**
     * 配置BusService
     * <p/>
     * busService负责与服务器沟通，获取数据
     */
    @AfterInject
    void setBusService() {
        if (appBusiness.getDateSourceType().equals(AppBusiness.DATE_SOURCE.OUTLINE)) {
            busService = BusOutlineService_.getInstance_(context);
        } else {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(JuheConstant.BASE_URL2)
                    .build();
            busService = restAdapter.create(BusService.class);
        }
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
     * 将BusInfoJsonBean转换为BusInfoBean
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

    /**
     * 跳转到BusInfoRequestFragment
     * <p/>
     * 到该页面设置查询长途大巴票的搜索信息
     */
    public void toBusInfoRequestFragment(FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        BusInfoRequestFragment fragment = new BusInfoRequestFragment_();
        transaction.replace(R.id.fragmrnt_container, fragment);
        transaction.commit();
    }

    /**
     * 获取默认的出发城市
     */
    public CityBean getDefaultStartCity() {
        return CityBean.generateCity("杭州");
    }

    /**
     * 获取默认的到达城市
     */
    public CityBean getDefaultEndCity() {
        return CityBean.generateCity("上海");
    }

    /**
     * 跳转到BusInfoResultFragment，查询长途大巴的信息
     *
     * @param startCity 出发城市
     * @param endCity   目的城市
     */
    public void toBusInfoResultFragment(FragmentManager fragmentManager, CityBean startCity, CityBean endCity) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack("");
        BusInfoResultFragment fragment = BusInfoResultFragment.generateFragment(startCity,
                endCity);
        transaction.replace(R.id.fragmrnt_container, fragment);
        transaction.commit();
    }
}
