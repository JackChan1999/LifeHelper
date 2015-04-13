package com.qz.lifehelper.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import org.androidannotations.annotations.EFragment;

import java.util.Calendar;
import java.util.Date;

/**
 * 选择日期页面
 * <p/>
 * 该页面会弹出一个DatePickerDialog
 */
@EFragment
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Date baseDate;
    private Callback callback;

    public interface Callback {
        void onDateChoosed(Date date);
    }

    static public class Builder {
        private DatePickerFragment fragment = new DatePickerFragment_();

        public Builder setBaseDate(Date baseDate) {
            fragment.baseDate = baseDate;
            return this;
        }

        public Builder setCallback(Callback callback) {
            fragment.callback = callback;
            return this;
        }

        public DatePickerFragment create() {

            if (fragment.baseDate == null) {
                throw new RuntimeException("没有设置基准日期");
            }

            if (fragment.callback == null) {
                throw new RuntimeException("没有设置回调接口");
            }

            return fragment;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar baseCalendar = Calendar.getInstance();
        baseCalendar.setTime(baseDate);
        int year = baseCalendar.get(Calendar.YEAR);
        int month = baseCalendar.get(Calendar.MONTH);
        int day = baseCalendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        Date date = calendar.getTime();
        callback.onDateChoosed(date);
    }
}
