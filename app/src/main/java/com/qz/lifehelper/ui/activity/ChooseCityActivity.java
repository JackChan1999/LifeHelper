package com.qz.lifehelper.ui.activity;

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

/**
 * Created by kohoh on 15/3/14.
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
	ChooseCityActivityPresentation presentation;

	@AfterViews
	/**
	 * 配置选择城市列表
	 */
	public void setCityListView() {
		ChooseCityListAdapter chooseCityListAdapter = new ChooseCityListAdapter(this);
		chooseCityListAdapter.setItemDatas(presentation.getChooseCityListData());
		cityLv.setAdapter(chooseCityListAdapter);
	}
}
