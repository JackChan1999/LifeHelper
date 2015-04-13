package com.qz.lifehelper.helper;

import android.app.Activity;
import android.os.Bundle;

import com.qz.lifehelper.ui.activity.BusInfoActivity_;
import com.qz.lifehelper.ui.activity.ChooseCityActivity_;
import com.qz.lifehelper.ui.activity.HomeActivity_;
import com.qz.lifehelper.ui.activity.POIResultActivity;
import com.qz.lifehelper.ui.activity.POIResultActivity_;
import com.qz.lifehelper.ui.activity.PlaneInfoActivity_;
import com.qz.lifehelper.ui.activity.TrainInfoActivity_;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity的助手
 * <p/>
 * 帮助MainActivity实现一部分业务逻辑
 */
@EBean
public class MainHelper {

    private List<ActivityClass> activityClasses;

    /**
     * 获取Activity目录信息
     */
    public List<ActivityClass> getActivityClasses() {

        if (activityClasses == null) {
            activityClasses = new ArrayList<ActivityClass>();
            activityClasses.add(new ActivityClass("ChooseCityActivity", ChooseCityActivity_.class));
            activityClasses.add(new ActivityClass("HomeActivity", HomeActivity_.class));

            Bundle extra = new Bundle();
            extra.putString(POIResultActivity.CATEGORY, "酒店");
            extra.putString(POIResultActivity.LOCATION, "上海");
            activityClasses.add(new ActivityClass("POIResultActivity", POIResultActivity_.class, extra));

            activityClasses.add(new ActivityClass("SearchPlaen", PlaneInfoActivity_.class));
            activityClasses.add(new ActivityClass("SearchTrain", TrainInfoActivity_.class));

            activityClasses.add(new ActivityClass("SearchBus", BusInfoActivity_.class));
        }

        return activityClasses;
    }

    /**
     * 该类封装类Activity到信息，包括名字和类
     */
    public class ActivityClass {
        private String name;
        private Class<? extends Activity> activityClass;
        private Bundle extra;

        public Bundle getExtra() {
            return extra;
        }

        public String getName() {
            return name;
        }

        public Class<? extends Activity> getActivityClass() {
            return activityClass;
        }

        ActivityClass(String name, Class<? extends Activity> activityClass) {
            this.name = name;
            this.activityClass = activityClass;
        }

        public ActivityClass(String name, Class<? extends Activity> activityClass, Bundle extra) {
            this.name = name;
            this.activityClass = activityClass;
            this.extra = extra;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
