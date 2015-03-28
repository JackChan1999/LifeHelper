package com.qz.lifehelper.ui.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.event.GetCurrentCityEvent;
import com.qz.lifehelper.event.GetCurrentLocationCityEvent;
import com.qz.lifehelper.presentation.ChooseCityHelper;
import com.qz.lifehelper.presentation.IChooseCityView;
import com.qz.lifehelper.presentation.adapter.ChooseCityListAdapter;

import de.greenrobot.event.EventBus;

/**
 * Created by kohoh on 15/3/14.
 */
@EActivity(R.layout.activity_choose_city)
public class ChooseCityActivity extends ActionBarActivity implements IChooseCityView {

	public static final String TAG = ChooseCityActivity.class.getSimpleName() + "TAG";

	// 配置ActionBar
	@AfterViews
	public void setActionBar() {
		this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		this.getSupportActionBar().setCustomView(R.layout.actionbar_choose_city);
		this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@ViewById(R.id.choose_city_lv)
	public ListView cityLv;

	@Bean
	ChooseCityHelper chooseCityHelper;

	@Bean
	LocationBusiness locationBusiness;

	@Bean
	ChooseCityListAdapter cityListAdapter;

	/**
	 * 配置选择城市列表
	 */
	@AfterViews
	public void setCityListView() {
		locationBusiness.findCurrentLocationCity();
		refreshCityList();
		cityLv.setAdapter(cityListAdapter);
	}

	@Override
	protected void onStart() {
		super.onStart();
		regisetEventBus();
	}

	@Override
	protected void onStop() {
		super.onStop();
		unregisterEventBus();
	}

	public void regisetEventBus() {
		EventBus.getDefault().register(this);
	}

	public void unregisterEventBus() {
		EventBus.getDefault().unregister(this);
	}

	/**
	 * 当收到成功定位的消息，设置当前定位到的城市，并刷新城市列表
	 */
	public void onEventMainThread(GetCurrentLocationCityEvent event) {
		chooseCityHelper.setCurrentLocationCity(event.currentLocationCity);
		refreshCityList();
	}

	/**
	 * 当接收到设置当前位置的消息，关闭该activity
	 */
	public void onEventMainThread(GetCurrentCityEvent event) {
		finish();
	}

	// 刷新城市列表
	void refreshCityList() {
		cityListAdapter.setItemDatas(chooseCityHelper.getChooseCityListData());
		cityListAdapter.notifyDataSetChanged();
	}
}
