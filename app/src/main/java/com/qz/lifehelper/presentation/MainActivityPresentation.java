package com.qz.lifehelper.presentation;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.EBean;

import android.app.Activity;

import com.qz.lifehelper.ui.activity.BaiduMapDempActivity_;
import com.qz.lifehelper.ui.activity.ChooseCityActivity_;
import com.qz.lifehelper.ui.activity.HomeActivity_;
import com.qz.lifehelper.ui.activity.POIResultActivity;
import com.qz.lifehelper.ui.activity.POIResultActivity_;

/**
 * Created by kohoh on 15/3/14.
 */

/**
 * 这是MainActivity对应的Presentation
 */
@EBean
public class MainActivityPresentation {

	private List<ActivityClass> activityClasses;

	/**
	 * 获取Activity目录信息
	 */
	public List<ActivityClass> getActivityClasses() {

		if (activityClasses == null) {
			activityClasses = new ArrayList<ActivityClass>();
			activityClasses.add(new ActivityClass("ChooseCityActivity", ChooseCityActivity_.class));
			activityClasses.add(new ActivityClass("BaiduMaoDemoActivity", BaiduMapDempActivity_.class));
            activityClasses.add(new ActivityClass("HomeActivity", HomeActivity_.class));
            activityClasses.add(new ActivityClass("POIResultActivity", POIResultActivity_.class));
        }

		return activityClasses;
	}

	/**
	 * 该类封装类Activity到信息，包括名字和类
	 */
	public class ActivityClass {
		private String name;
		private Class<? extends Activity> activityClass;

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

		@Override
		public String toString() {
			return name;
		}
	}

}
