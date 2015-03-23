package com.qz.lifehelper.presentation;

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
public class LifeFragmentPresentation {

	@Bean
	LocationBusiness locationBusiness;

	@RootContext
	Context context;

	private void toPOIResult(String catrgory) {
		Intent intent = POIResultActivity.generateIntent(context, locationBusiness.getCurrentCity().cityName, catrgory);
		context.startActivity(intent);
	}

	public void toPlane() {
		Toast.makeText(context, "前往飞机票", Toast.LENGTH_SHORT).show();
	}

	public void toTrain() {
		Toast.makeText(context, "前往火车票", Toast.LENGTH_SHORT).show();
	}

	public void toBus() {
		Toast.makeText(context, "前往大巴票", Toast.LENGTH_SHORT).show();
	}

	public void toMove() {
		toPOIResult("物流");
	}

	public void toMaternityMatron() {
		toPOIResult("月嫂");
	}

	public void toCleaner() {
		toPOIResult("保洁");
	}

	public void toBuyFruit() {
		Toast.makeText(context, "前往蔬菜水果交易市场", Toast.LENGTH_SHORT).show();
	}

	public void toBuyElectricAppliance() {
		Toast.makeText(context, "前往电子商品交易市场", Toast.LENGTH_SHORT).show();
	}

	public void toBuyClothes() {
		Toast.makeText(context, "前往衣服鞋包交易市场", Toast.LENGTH_SHORT).show();
	}

	public void toBuyMore() {
		Toast.makeText(context, "前往更多交易市场", Toast.LENGTH_SHORT).show();
	}
}
