package com.qz.lifehelper.ui.activity;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.presentation.IPOIResultView;
import com.qz.lifehelper.presentation.POIResultActivitPresentation;

/**
 * Created by kohoh on 15/3/19.
 */
@EActivity(R.layout.activity_poi_result)
public class POIResultActivity extends ActionBarActivity implements IPOIResultView {

	@ViewById(R.id.poi_result_lv)
	public ListView poiResultLv;

	public static final String LOCATION = "LOCATION";
	public static final String CATEGORY = "CATEGORY";

	@Extra(LOCATION)
	String currentLocation;

	@Extra(CATEGORY)
	String category;

    /**
     * 生成跳转到POIResultActivity的Intent
     * @param location 查询信息的位置
     * @param category 查询信息的类别
     */
	static public Intent generateIntent(Context context, String location, String category) {
		Intent intent = new Intent(context, POIResultActivity_.class);
		intent.putExtra(LOCATION, location);
		intent.putExtra(CATEGORY, category);
		return intent;
	}

    /**
     * 设置ViewModel
     */
	@AfterExtras
	public void setPresentation() {
		presentation.setCurrentLocation(currentLocation);
		presentation.setCategory(category);
	}

	@Bean
	public POIResultActivitPresentation presentation;

	@Override
	protected void onStart() {
		super.onResume();
        //开始绑定数据
		presentation.bind(this);
	}

	@Override
	protected void onStop() {
		super.onPause();
        //停止绑定数据
		presentation.unBind();
	}

    /**
     * 设置POI结果列表
     */
    @AfterViews
	public void setPoiResltListSp() {
		poiResultLv.setAdapter(presentation.getPOIResultListAdapter());
	}

    /**
     * 当开始加载数据时被调用
     */
	@Override
	public void starLoadPOIData() {

	}

    /**
     * 当成功加载数据时被调用
     */
	@Override
	public void loadPOIDataSuccess() {

	}

    /**
     * 当加载数据失败时被调用
     */
	@Override
	public void loadPOIDataFial() {

	}
}
