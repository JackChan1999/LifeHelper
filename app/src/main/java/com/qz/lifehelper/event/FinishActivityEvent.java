package com.qz.lifehelper.event;

import android.app.Activity;

/**
 * Created by kohoh on 15/3/18.
 */
public class FinishActivityEvent {

    public Class<? extends Activity> aClass;

    static public FinishActivityEvent generateEvent(Class<? extends Activity> aClass) {
        FinishActivityEvent event = new FinishActivityEvent();
        event.aClass = aClass;
        return event;
    }

}
