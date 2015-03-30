package com.qz.lifehelper.ui.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import bolts.Continuation;
import bolts.Task;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.event.GetCurrentCityEvent;
import com.qz.lifehelper.helper.ChooseCityHelper;
import com.qz.lifehelper.ui.adapter.ChooseCityListAdapter;

/**
 * 选择城市页面
 */
@EActivity(R.layout.activity_choose_city)
public class ChooseCityActivity extends ActionBarActivity {

	public static final String TAG = ChooseCityActivity.class.getSimpleName() + "TAG";

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
		locationBusiness.findCurrentLocationCity().onSuccess(new Continuation<CityBean, Void>() {
			@Override
			public Void then(Task<CityBean> task) throws Exception {
				onGetCurrentLocationCity(task.getResult());
				return null;
			}
		});
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
		locationBusiness.getEventBus().register(this);
	}

	public void unregisterEventBus() {
		locationBusiness.getEventBus().unregister(this);
	}

	/**
	 * 当成功定位到当前设备的位置时，会触发该方法
	 **/
	private void onGetCurrentLocationCity(CityBean currentLoactionCity) {
		chooseCityHelper.setCurrentLocationCity(currentLoactionCity);
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
