package com.qz.lifehelper.helper;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.ui.fragment.ChooseCityFragment;
import com.qz.lifehelper.ui.fragment.PlaneInfoRequestFragment_;
import com.qz.lifehelper.ui.fragment.PlaneInfoResultFragment;
import com.qz.lifehelper.utils.DateUtil;

import org.androidannotations.annotations.EBean;

import java.util.Date;

import bolts.Task;

/**
 * 该类帮助PlaneInfoActivity,SearchPlaneInfoFragment,PlaneInfoResultFragment,ChooseAirportFragment实现相关当业务逻辑
 */
@EBean(scope = EBean.Scope.Singleton)
public class PlaneInfoHelper {
    private final static String TAG = PlaneInfoHelper.class.getSimpleName() + "TAG";

    /**
     * 这是选择出发日期的日期格式
     */
    public static final String dateFormatPattern = "yyyy'-'MM'-'dd EE";


    /**
     * 选择机场，跳转到ChooseAirportFragment
     */
    public Task<CityBean> chooseCity() {
        final Task<CityBean>.TaskCompletionSource taskCompletionSource = Task.create();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack("");
        ChooseCityFragment fragment = new ChooseCityFragment.Builder()
                .setCallback(new ChooseCityFragment.CallBcak() {
                    @Override
                    public void onCityChoosed(CityBean cityBean) {
                        fragmentManager.popBackStack();
                        taskCompletionSource.setResult(cityBean);
                    }
                })
                .create();
        transaction.replace(R.id.fragmrnt_container, fragment);
        transaction.commit();

        return taskCompletionSource.getTask();
    }


    /**
     * 搜索相关当航班信息，跳转到PlaneInfoResultFragment
     *
     * @param startCity 出发机场
     * @param endCity   目的地机场
     * @param date      出发时间 该时间格式是 #dateFormatPattern
     */
    public void searchPlaneInfo(CityBean startCity, CityBean endCity, String date) {
        Date dateFly = DateUtil.parseDate(dateFormatPattern, date);
        PlaneInfoResultFragment planeInfoResultFragment = PlaneInfoResultFragment.generateFragment(startCity, endCity, dateFly);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack("");
        transaction.replace(R.id.fragmrnt_container, planeInfoResultFragment);
        transaction.commit();
    }

    FragmentManager fragmentManager;

    /**
     * 设FragmentManager
     * <p/>
     * 该方法会在Activity中被调用。由于Bean是singleton的，所以可以实现传递
     */
    public void setFragmentManager(FragmentManager supportFragmentManager) {
        this.fragmentManager = supportFragmentManager;
    }


    /**
     * 配置航班搜索的参数，跳转到PlaneInfoRequestFragment
     * <p/>
     * 在该页面设置搜索的信息
     */
    public void setPlaneInfoSearchArgument() {
        fragmentManager.beginTransaction().replace(R.id.fragmrnt_container, new PlaneInfoRequestFragment_()).commit();
    }

    /**
     * 获取默认的出发机场
     */
    public CityBean getDefaultStartCity() {
        return CityBean.generateCity("北京");
    }

    /**
     * 获取默认的目的地机场
     */
    public CityBean getDefaultEndCity() {
        return CityBean.generateCity("上海");
    }
}
