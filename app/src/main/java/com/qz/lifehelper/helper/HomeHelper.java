package com.qz.lifehelper.helper;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.FragmentBean;
import com.qz.lifehelper.ui.fragment.ArroundFragmnet_;
import com.qz.lifehelper.ui.fragment.LifeFragment_;
import com.qz.lifehelper.ui.fragment.PersonalFragment_;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 前往关键字搜索页
     */
    public void search() {
        Toast.makeText(context, "前往搜索页面", Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取当前选择的城市
     */
    public Task<String> getCurrentCity(FragmentManager fragmentManager) {
        final Task<String>.TaskCompletionSource taskCompletionSource = Task.create();
        CityBean currentCityBean = locationBusiness.getCurrentCity();
        //如果美欧设置当前的城市，则跳转到ChooseCityFragment去选择一个城市
        if (currentCityBean == null) {
            locationBusiness.chooseCity(fragmentManager).onSuccess(new Continuation<CityBean, Void>() {
                @Override
                public Void then(Task<CityBean> task) throws Exception {
                    taskCompletionSource.setResult(task.getResult().cityName);
                    return null;
                }
            });
        } else {
            taskCompletionSource.setResult(currentCityBean.cityName);
        }
        return taskCompletionSource.getTask();
    }

    /**
     * 获取Home页面的三个子页面对应的Fragment及其对应当Tab标题
     */
    public List<FragmentBean> getFragments() {
        List<FragmentBean> fragments = new ArrayList<>();
        fragments.add(FragmentBean.generateHomeFragment(new ArroundFragmnet_(), "周边"));
        fragments.add(FragmentBean.generateHomeFragment(new LifeFragment_(), "生活"));
        fragments.add(FragmentBean.generateHomeFragment(new PersonalFragment_(), "个人"));
        return fragments;
    }

}
