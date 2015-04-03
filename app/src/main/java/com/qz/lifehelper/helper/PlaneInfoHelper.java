package com.qz.lifehelper.helper;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.AirportBean;
import com.qz.lifehelper.event.GetAirportEvent;
import com.qz.lifehelper.event.GetDateEvent;
import com.qz.lifehelper.ui.fragment.ChooseAirportFragment_;
import com.qz.lifehelper.ui.fragment.DatePickerFragment;
import com.qz.lifehelper.ui.fragment.PlaneInfoRequestFragment_;
import com.qz.lifehelper.ui.fragment.PlaneInfoResultFragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import bolts.Task;
import de.greenrobot.event.EventBus;

/**
 * 该类帮助PlaneInfoActivity,SearchPlaneInfoFragment,PlaneInfoResultFragment,ChooseAirportFragment实现相关当业务逻辑
 */
@EBean(scope = EBean.Scope.Singleton)
public class PlaneInfoHelper {
    private final static String TAG = PlaneInfoHelper.class.getSimpleName() + "TAG";

    /**
     * 这是选择出发日期的日期格式
     */
    private static final String dateFormatPattern = "yyyy'-'MM'-'dd EE";

    /**
     * 选择日期的task
     */
    private Task<String>.TaskCompletionSource chooseDateTask;

    /**
     * 设置搜索航班信息结果的frgamnet的TAG
     */
    private static final String SEARCH_PLANE_INFO_FRAGMENT = "SEARCH_PLANE_INFO_FRAGMENT";
    /**
     * 选择机场的Fragmnet的TAG
     */
    private static final String CHOOSE_AITPORT_FRAGMNET = "CHOOSE_AITPORT_FRAGMNET";

    //TODO 使用DateBusiness实现

    /**
     * 选择日期
     *
     * @param baseDate 基础日期，会以此日期为中心，选择日期
     */
    public Task<String> chooseDate(String baseDate) {
        chooseDateTask = Task.create();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatPattern);
        try {
            Date date = simpleDateFormat.parse(baseDate);
            Calendar baseCalendar = Calendar.getInstance();
            baseCalendar.setTime(date);
            DatePickerFragment datePickerFragment = DatePickerFragment.generateFragment(baseCalendar);
            datePickerFragment.show(fragmentManager, "date_picker_fragmnet");
        } catch (ParseException e) {
            e.printStackTrace();
            chooseDateTask.setError(e);
        } finally {
            return chooseDateTask.getTask();

        }
    }

    @AfterInject
    void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    /**
     * 当成功选择了日期后会发送GetDateEvent，因此在这里设置选择日期的结果
     */
    public void onEvent(GetDateEvent event) {
        if (chooseDateTask != null) {
            String date = DateFormatUtils.format(event.calendar, dateFormatPattern);
            chooseDateTask.setResult(date);
            chooseDateTask = null;
        }
    }


    /**
     * 选择机场的task
     */
    private Task<AirportBean>.TaskCompletionSource chooseAirportTask;

    /**
     * 选择机场，跳转到ChooseAirportFragment
     */
    public Task<AirportBean> chooseAirport() {
        chooseAirportTask = Task.create();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack("");
        transaction.replace(R.id.fragmrnt_container, new ChooseAirportFragment_(), CHOOSE_AITPORT_FRAGMNET);
        transaction.commit();

        return chooseAirportTask.getTask();
    }

    /**
     * 当成功的选择了机场后，会发出GetAirportEvent，因此在这里设置选择机场的结果
     */
    public void onEvent(GetAirportEvent event) {
        if (chooseAirportTask != null) {
            chooseAirportTask.setResult(event.airportBean);
            chooseAirportTask = null;
        }
    }

    /**
     * 搜索相关当航班信息，跳转到PlaneInfoResultFragment
     *
     * @param startLoaction 出发机场
     * @param endLocation   目的地机场
     * @param date          出发时间 该时间格式是 #dateFormatPattern
     */
    public void searchPlaneInfo(AirportBean startLoaction, AirportBean endLocation, String date) {
        try {
            Date dateFly = new SimpleDateFormat(dateFormatPattern).parse(date);
            PlaneInfoResultFragment planeInfoResultFragment = PlaneInfoResultFragment.generateFragment(startLoaction, endLocation, dateFly);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack("");
            transaction.replace(R.id.fragmrnt_container, planeInfoResultFragment);
            transaction.commit();
        } catch (ParseException e) {
            Log.e(TAG, "searchPlaneInfo fail", e);
            e.printStackTrace();
        }
    }

    /**
     * 获取当前当日期
     * <p/>
     * 第一次打开设置航班信息搜索结果页的时候，显示的都是当前的日期
     */
    public String getCurrentDate() {
        Date date = new Date();
        return DateFormatUtils.format(date, dateFormatPattern);
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
     * 设置选择的机场
     * <p/>
     * 该方法会被ChooseAiroortFragment调用
     */
    public void setAirPort(AirportBean airport) {
        fragmentManager.popBackStack();
        EventBus.getDefault().post(GetAirportEvent.generateEvent(airport));
    }

    /**
     * 配置航班搜索的参数，跳转到PlaneInfoRequestFragment
     * <p/>
     * 在该页面设置搜索的信息
     */
    public void setPlaneInfoSearchArgument() {
        fragmentManager.beginTransaction().replace(R.id.fragmrnt_container, new PlaneInfoRequestFragment_(), SEARCH_PLANE_INFO_FRAGMENT).commit();
    }

    /**
     * 获取默认的出发机场
     */
    public AirportBean getDefaultStartAirport() {
        return AirportBean.generateAirport("北京", "PEK");
    }

    /**
     * 获取默认的目的地机场
     */
    public AirportBean getDefaultEndAirport() {
        return AirportBean.generateAirport("上海", "SHA");
    }
}
