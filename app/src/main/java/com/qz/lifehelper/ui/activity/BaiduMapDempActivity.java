package com.qz.lifehelper.ui.activity;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.support.v7.app.ActionBarActivity;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.qz.lifehelper.R;

/**
 * Created by kohoh on 15/3/13.
 */
@EActivity(R.layout.activitiy_baidu_map_demo)
public class BaiduMapDempActivity extends ActionBarActivity {

    @ViewById(R.id.baidu_map)
    MapView mMapView;

    @AfterInject
    public void initBaiduMap() {
        SDKInitializer.initialize(getApplicationContext());
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
