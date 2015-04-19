package com.qz.lifehelper.business;

import android.support.v4.app.FragmentManager;

import com.qz.lifehelper.ui.fragment.DatePickerFragment;

import org.androidannotations.annotations.EBean;

import java.util.Calendar;
import java.util.Date;

import bolts.Task;

/**
 * 该类帮助处理日期时间相关的用户逻辑
 */
@EBean
public class DateBusiness {
    /**
     * 选择日期
     * <p/>
     * 会跳转到DatePickerFragment让用户进行日期的选择
     *
     * @param baseDate 基础日期，会以此日期为中心，选择日期
     */
    public Task<Date> chooseDate(Date baseDate, FragmentManager fragmentManager) {
        final Task<Date>.TaskCompletionSource taskCompletionSource = Task.create();
        Calendar baseCalendar = Calendar.getInstance();
        baseCalendar.setTime(baseDate);
        DatePickerFragment datePickerFragment = new DatePickerFragment.Builder()
                .setBaseDate(baseDate)
                .setCallback(new DatePickerFragment.Callback() {
                    @Override
                    public void onDateChoosed(Date date) {
                        taskCompletionSource.trySetResult(date);
                    }
                })
                .create();
        datePickerFragment.show(fragmentManager, "date_picker_fragmnet");
        return taskCompletionSource.getTask();
    }
}
