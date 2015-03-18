package com.qz.lifehelper.ui.activity;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.presentation.ChooseCityActivityPresentation;
import com.qz.lifehelper.ui.ChooseCityListAdapter;

import de.greenrobot.event.EventBus;

/**
 * Created by kohoh on 15/3/14.
 */
@EActivity(R.layout.activity_choose_city)
public class ChooseCityActivity extends ActionBarActivity {

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
	ChooseCityActivityPresentation presentation;

	@Bean
	ChooseCityListAdapter chooseCityListAdapter;

	/**
	 * 配置选择城市列表
	 */
	@AfterViews
	public void setCityListView() {
		cityLv.setAdapter(chooseCityListAdapter);
		EventBus.getDefault().post(new RefreshCityListEvent());
	}

    //注册Eventbus
	@AfterInject
	public void reginsterEventBus() {
		EventBus.getDefault().register(this);
	}

    /**
     * 刷新ChooseCity列表的事件
     */
	public static class RefreshCityListEvent {
	}

	/**
	 * 刷新ChooseCity列表的数据
	 */
	public void onEventMainThread(RefreshCityListEvent event) {
		chooseCityListAdapter.setItemDatas(presentation.getChooseCityListData());
		chooseCityListAdapter.notifyDataSetChanged();
	}
}
