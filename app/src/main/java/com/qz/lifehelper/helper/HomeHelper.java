package com.qz.lifehelper.helper;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.event.GetCurrentCityEvent;
import com.qz.lifehelper.ui.activity.ChooseCityActivity_;
import com.qz.lifehelper.ui.fragment.ArroundFragmnet_;
import com.qz.lifehelper.ui.fragment.LifeFragment_;
import com.qz.lifehelper.ui.fragment.PersonalFragment_;


import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.collections4.OrderedMap;
import org.apache.commons.collections4.map.ListOrderedMap;

import bolts.Continuation;
import bolts.Task;

/**
 * HomeActivity的助手
 * <p/>
 * 帮助HomeActivity实现一部分业务逻辑
 */
@EBean
public class HomeHelper {

    @Bean
    LocationBusiness locationBusiness;

    @RootContext
    Context context;

    private Task<String>.TaskCompletionSource chooseCityTaskSource;

    /**
     * 前往选择城市页
     */
    public Task<String> chooseCity() {
        chooseCityTaskSource = Task.create();
        Intent intent = new Intent(context, ChooseCityActivity_.class);
        context.startActivity(intent);
        return chooseCityTaskSource.getTask();
    }

    /**
     * 前往关键字搜索页
     */
    public void search() {
        Toast.makeText(context, "前往搜索页面", Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取当前选择的城市
     */
    public Task<String> getCurrentCity() {
        final Task<String>.TaskCompletionSource taskCompletionSource = Task.create();
        CityBean currentCityBean = locationBusiness.getCurrentCity();
        if (currentCityBean == null) {
            chooseCity().onSuccess(new Continuation<String, Void>() {
                @Override
                public Void then(Task<String> task) throws Exception {
                    taskCompletionSource.setResult(task.getResult());
                    return null;
                }
            });
        } else {
            taskCompletionSource.setResult(currentCityBean.cityName);
        }
        return taskCompletionSource.getTask();
    }

    @AfterInject
    public void reigsterEventBus() {
        locationBusiness.getEventBus().register(this);
    }

    /**
     * 当接收到选择城市的event，则设置选择城市task的结果
     */
    public void onEvent(GetCurrentCityEvent event) {
        if (chooseCityTaskSource != null) {
            chooseCityTaskSource.setResult(event.currentCityBean.cityName);
        }
    }

    /**
     * 获取Home页面的三个子页面对应的Fragment及其对应当Tab标题
     */
    public OrderedMap<String, Fragment> getFragments() {
        OrderedMap<String, Fragment> fragments = new ListOrderedMap<String, Fragment>();
        fragments.put("周边", new ArroundFragmnet_());
        fragments.put("生活", new LifeFragment_());
        fragments.put("个人", new PersonalFragment_());
        return fragments;
    }

}
