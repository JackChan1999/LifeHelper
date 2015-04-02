package com.qz.lifehelper.event;

import java.util.Calendar;

/**
 * 当在DatePickerFragment中选择好日期后，会发送该event
 */
public class GetDateEvent {

    public Calendar calendar;

    public static GetDateEvent generateEvent(Calendar calendar) {
        GetDateEvent event = new GetDateEvent();
        event.calendar = calendar;
        return event;
    }
}
