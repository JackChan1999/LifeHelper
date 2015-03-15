package com.qz.lifehelper.presentation;

import android.app.Activity;

import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.ItemPresentationModel;

/**
* Created by kohoh on 15/3/15.
*/
class ActivityItemPresentationModel implements ItemPresentationModel<Class<? extends Activity>> {

    private Class<? extends Activity> activityClass;

    public String getTitle() {
        return activityClass.getSimpleName();
    }

    @Override
    public void updateData(Class<? extends Activity> bean, ItemContext itemContext) {
        this.activityClass = bean;
    }
}
