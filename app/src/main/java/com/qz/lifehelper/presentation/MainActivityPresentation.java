package com.qz.lifehelper.presentation;


import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.RootContext;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.widget.adapterview.ItemClickEvent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.qz.lifehelper.ui.activity.Activity1_;
import com.qz.lifehelper.ui.activity.BaiduMapDempActivity_;
import com.qz.lifehelper.ui.activity.ChooseCityActivity_;

/**
 * Created by kohoh on 15/3/14.
 */
@PresentationModel
public class MainActivityPresentation{

    private List<Class<? extends Activity>> activities;

    private Context context;

    public MainActivityPresentation(Context context) {
        activities = new ArrayList<Class<? extends Activity>>();
        activities.addAll(getActivityClasses());
        this.context = context;
    }

    private List<Class<? extends Activity>> getActivityClasses() {
        List<Class<? extends Activity>> activities = new ArrayList<Class<? extends Activity>>();

        activities.add(ChooseCityActivity_.class);
        activities.add(Activity1_.class);
        activities.add(BaiduMapDempActivity_.class);

        return activities;
    }

    @ItemPresentationModel(value = ActivityItemPresentationModel.class)
    public List<Class<? extends Activity>> getActivities() {
        return activities;
    }

    public void toTargetActivity(ItemClickEvent event) {
        Intent intent = new Intent(context, activities.get(event.getPosition()));
        context.startActivity(intent);
    }
}
