package com.qz.lifehelper.helper;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.AirPortBean;
import com.qz.lifehelper.event.GetAirportEvent;
import com.qz.lifehelper.event.GetDateEvent;
import com.qz.lifehelper.ui.fragment.ChooseAirPortFragment_;
import com.qz.lifehelper.ui.fragment.DatePickerFragment;
import com.qz.lifehelper.ui.fragment.PlaneInfoFragment;
import com.qz.lifehelper.ui.fragment.SearchPlaneFragment_;

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
 * 该类帮助SearchPlane实现相关当业务逻辑
 */
@EBean(scope = EBean.Scope.Singleton)
public class SearchPlaneHelper {
    private final static String TAG = SearchPlaneHelper.class.getSimpleName() + "TAG";

    private static final String dateFormatPattern = "yyyy'-'MM'-'dd EE";

    private Task<String>.TaskCompletionSource chooseDateTask;

    private static final String SEARCH_PLANE_FRAGMENT = "SEARCH_PLANE_FRAGMENT";
    private static final String CHOOSE_AITPORT_FRAGMNET = "CHOOSE_AITPORT_FRAGMNET";

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

    public void onEvent(GetDateEvent event) {
        if (chooseDateTask != null) {
            String date = DateFormatUtils.format(event.calendar, dateFormatPattern);
            chooseDateTask.setResult(date);
            chooseDateTask = null;
        }
    }


    private Task<AirPortBean>.TaskCompletionSource chooseAirportTask;

    /**
     * 选择机场
     */
    public Task<AirPortBean> chooseAirport() {
        chooseAirportTask = Task.create();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack("");
        transaction.replace(R.id.fragmrnt_container, new ChooseAirPortFragment_(), CHOOSE_AITPORT_FRAGMNET);
        transaction.commit();


        return chooseAirportTask.getTask();
    }

    public void onEvent(GetAirportEvent event) {
        if (chooseAirportTask != null) {
            chooseAirportTask.setResult(event.airPortBean);
            chooseAirportTask = null;
        }
    }

    /**
     * 搜索相关当航班信息
     *
     * @param startLoaction 出发机场
     * @param endLocation   目的地机场
     * @param date          出发时间
     */
    public void searchPlaneInfo(AirPortBean startLoaction, AirPortBean endLocation, String date) {
        try {
            Date dateFly = new SimpleDateFormat(dateFormatPattern).parse(date);
            PlaneInfoFragment planeInfoFragment = PlaneInfoFragment.generateFragment(startLoaction, endLocation, dateFly);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack("");
            transaction.replace(R.id.fragmrnt_container, planeInfoFragment);
            transaction.commit();
        } catch (ParseException e) {
            Log.e(TAG, "searchPlaneInfo fail", e);
            e.printStackTrace();
        }
    }

    /**
     * 获取当前当日期
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
    public void setAirPort(AirPortBean airport) {
        fragmentManager.popBackStack();
        EventBus.getDefault().post(GetAirportEvent.generateEvent(airport));
    }

    /**
     * 跳转到搜索航班信息页
     */
    public void searchPlane() {
        fragmentManager.beginTransaction().replace(R.id.fragmrnt_container, new SearchPlaneFragment_(), SEARCH_PLANE_FRAGMENT).commit();
    }
}
