package com.qz.lifehelper.helper;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.ui.fragment.BusInfoRequestFragment;
import com.qz.lifehelper.ui.fragment.BusInfoRequestFragment_;
import com.qz.lifehelper.ui.fragment.BusInfoResultFragment;
import com.qz.lifehelper.ui.fragment.ChooseCityFragment;

import org.androidannotations.annotations.EBean;

import bolts.Task;

/**
 * 该类帮助BusInfoActivity,BusInfoRequestFragment,BusInfoResultFragment
 */
@EBean(scope = EBean.Scope.Singleton)
public class BusInfoHelper {
    private FragmentManager fragmentManager;

    /**
     * 设置FragmentManger
     * <p/>
     * 因为BusInfoHelper是singleton的，所以fragmentManager可以被正常使用
     */
    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    /**
     * 跳转到BusInfoRequestFragment
     * <p/>
     * 到该页面设置查询长途大巴票的搜索信息
     */
    public void setBusInfoSearchArgument() {
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

    private Task<CityBean>.TaskCompletionSource chooseCityTask;

    /**
     * 选择城市
     * <p/>
     * 会跳转到ChooseBusCity
     */
    public Task<CityBean> chooseCity() {
        chooseCityTask = Task.create();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack("");
        ChooseCityFragment fragment = new ChooseCityFragment.Builder()
                .setCallback(new ChooseCityFragment.CallBcak() {
                    @Override
                    public void onCityChoosed(CityBean cityBean) {
                        fragmentManager.popBackStack();
                        if (chooseCityTask != null) {
                            chooseCityTask.setResult(cityBean);
                            chooseCityTask = null;
                        }
                    }
                })
                .create();
        transaction.replace(R.id.fragmrnt_container, fragment);
        transaction.commit();

        return chooseCityTask.getTask();
    }

    /**
     * 跳转到BusInfoResultFragment，更具参数搜索相应到火车信息
     *
     * @param startCity 出发城市
     * @param endCity   目的城市
     */
    public void searchBusInfo(CityBean startCity, CityBean endCity) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack("");
        BusInfoResultFragment fragment = BusInfoResultFragment.generateFragment(startCity,
                endCity);
        transaction.replace(R.id.fragmrnt_container, fragment);
        transaction.commit();
    }
}
