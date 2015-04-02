package com.qz.lifehelper.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.qz.lifehelper.event.GetDateEvent;

import org.androidannotations.annotations.EFragment;

import java.util.Calendar;

import de.greenrobot.event.EventBus;

/**
 * 选择日期
 */
@EFragment
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static final String BASE_DATE = "BASE_DATE";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar baseCalendar = (Calendar) getArguments().getSerializable(BASE_DATE);
        int year = baseCalendar.get(Calendar.YEAR);
        int month = baseCalendar.get(Calendar.MONTH);
        int day = baseCalendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        EventBus.getDefault().post(GetDateEvent.generateEvent(calendar));
    }

    public static DatePickerFragment generateFragment(Calendar baseCalendar) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BASE_DATE, baseCalendar);
        datePickerFragment.setArguments(bundle);
        return datePickerFragment;
    }
}
