package com.qz.lifehelper.business;

import android.support.v4.app.FragmentManager;

import com.qz.lifehelper.event.GetDateEvent;
import com.qz.lifehelper.ui.fragment.DatePickerFragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;

import java.util.Calendar;
import java.util.Date;

import bolts.Task;
import de.greenrobot.event.EventBus;

/**
 * 该类帮助处理日期时间相关的用户逻辑
 */
@EBean
public class DateBusiness {

    private Task<Date>.TaskCompletionSource chooseDateTask;

    /**
     * 选择日期
     * <p/>
     * 会跳转到DatePickerFragment让用户进行日期的选择
     *
     * @param baseDate 基础日期，会以此日期为中心，选择日期
     */
    public Task<Date> chooseDate(Date baseDate, FragmentManager fragmentManager) {
        chooseDateTask = Task.create();
        Calendar baseCalendar = Calendar.getInstance();
        baseCalendar.setTime(baseDate);
        DatePickerFragment datePickerFragment = DatePickerFragment.generateFragment(baseCalendar);
        datePickerFragment.show(fragmentManager, "date_picker_fragmnet");
        return chooseDateTask.getTask();

    }

    @AfterInject
    void registerEventBus() {
        EventBus.getDefault().register(this);
    }


    /**
     * 当成功选择了日期后会发送GetDateEvent，因此在这里设置选择日期的结果
     * <p/>
     * DatePickerFragmnet会发出该event，通知选择日期成功
     */
    public void onEvent(GetDateEvent event) {
        if (chooseDateTask != null) {
            chooseDateTask.setResult(event.calendar.getTime());
            chooseDateTask = null;
        }
    }

}
