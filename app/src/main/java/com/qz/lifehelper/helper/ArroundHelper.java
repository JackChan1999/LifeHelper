package com.qz.lifehelper.helper;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.ui.activity.POIResultActivity;

/**
 * Created by kohoh on 15/3/23.
 */

@EBean
public class ArroundHelper {

	@RootContext
	Context context;

	@Bean
	LocationBusiness locationBusiness;

	/**
	 * 跳转到POI搜索结果页面
	 */
	public void toPOIResult(String category) {
		Intent intent = POIResultActivity.generateIntent(context, locationBusiness.getCurrentCity().cityName, category);
		context.startActivity(intent);
	}

	/**
	 * 跳转到POI类别目录页
	 */
	public void toPOICategory() {
		Toast.makeText(context, "前往POI类别列表", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 跳转到十佳餐厅页面
	 */
	public void toTop10Restaurant() {
		Toast.makeText(context, "前往十佳餐厅", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 跳转到特惠页面
	 */
	public void toSale() {
		Toast.makeText(context, "前往特惠", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 跳转到最热页面
	 */
	public void toTopic() {
		Toast.makeText(context, "前往最热", Toast.LENGTH_SHORT).show();
	}

}
