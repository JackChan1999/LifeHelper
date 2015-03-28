package com.qz.lifehelper.helper;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.collections4.OrderedMap;
import org.apache.commons.collections4.map.ListOrderedMap;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.entity.City;
import com.qz.lifehelper.event.GetCurrentCityEvent;
import com.qz.lifehelper.ui.activity.ChooseCityActivity_;
import com.qz.lifehelper.ui.fragment.ArroundFragmnet_;
import com.qz.lifehelper.ui.fragment.LifeFragment_;
import com.qz.lifehelper.ui.fragment.PersonalFragment_;

import de.greenrobot.event.EventBus;

/**
 * Created by kohoh on 15/3/18.
 */
@EBean
public class HomeHelper {

	@Bean
	LocationBusiness locationBusiness;

	@RootContext
	Context context;

	/**
	 * 前往选择城市页
	 */
	public void chooseCity() {
		Intent intent = new Intent(context, ChooseCityActivity_.class);
		context.startActivity(intent);
	}

	/**
	 * 前往关键字搜索页
	 */
	public void search() {
		Toast.makeText(context, "前往搜索页面", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 获取当前选择的城市
	 */
	public void getCurrentCity() {
		City currentCity = locationBusiness.getCurrentCity();
		if (currentCity == null) {
			chooseCity();
		} else {
			EventBus.getDefault().post(GetCurrentCityEvent.generateEvent(currentCity));
		}
	}

	/**
	 * 获取Home页面的三个子页面对应的Fragment及其对应当Tab标题
	 */
	public OrderedMap<String, Fragment> getFragments() {
		OrderedMap<String,Fragment> fragments = new ListOrderedMap<String,Fragment>();
		fragments.put("周边", new ArroundFragmnet_());
		fragments.put("生活", new LifeFragment_());
		fragments.put("个人", new PersonalFragment_());
		return fragments;
	}

}
