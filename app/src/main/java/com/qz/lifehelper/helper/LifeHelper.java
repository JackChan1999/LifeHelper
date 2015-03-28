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
 * LifeFragment的助手
 *
 * 帮助LifeFragment实现一部分业务逻辑
 */

@EBean
public class LifeHelper {

	@Bean
	LocationBusiness locationBusiness;

	@RootContext
	Context context;

	/**
	 * 前往POI搜索结果页
	 */
	public void toPOIResult(String catrgory) {
		Intent intent = POIResultActivity.generateIntent(context, locationBusiness.getCurrentCity().cityName, catrgory);
		context.startActivity(intent);
	}

	/**
	 * 前往航班信息页
	 */
	public void toPlane() {
		Toast.makeText(context, "前往飞机票", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 前往火车信息页
	 */
	public void toTrain() {
		Toast.makeText(context, "前往火车票", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 前往长途汽车页
	 */
	public void toBus() {
		Toast.makeText(context, "前往大巴票", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 前往交易市场搜索结果页
	 */
	public void toMarketResult(String category) {
		Toast.makeText(context, "前往" + category + "交易市场", Toast.LENGTH_SHORT).show();

	}

	/**
	 * 前往交易市场类别目录页
	 */
	public void toMarketCategory() {
		Toast.makeText(context, "前往更多交易市场", Toast.LENGTH_SHORT).show();
	}
}
