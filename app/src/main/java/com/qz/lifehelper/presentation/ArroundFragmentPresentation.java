package com.qz.lifehelper.presentation;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.ui.activity.POIResultActivity;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by kohoh on 15/3/23.
 */

@EBean
public class ArroundFragmentPresentation {

	@RootContext
	Context context;

	@Bean
	LocationBusiness locationBusiness;

	private void toPOIResult(String category) {
		Intent intent = POIResultActivity.generateIntent(context, locationBusiness.getCurrentCity().cityName, category);
        context.startActivity(intent);
    }

	public void toFood() {
        toPOIResult("餐厅");
	}

	public void toHotel() {
        toPOIResult("酒店");
	}

	public void toHospital() {
        toPOIResult("医院");
	}

	public void toToilet() {
        toPOIResult("厕所");
	}

	public void toSuperMarket() {
        toPOIResult("超市");
	}

	public void toBBQ() {
        toPOIResult("烧烤");
	}

	public void toChafingdish() {
        toPOIResult("火锅");
	}

	public void toMore() {
        Toast.makeText(context, "前往POI类别列表", Toast.LENGTH_SHORT).show();
	}

	public void toTop10Restaurant() {
        Toast.makeText(context, "前往十佳餐厅", Toast.LENGTH_SHORT).show();
    }

	public void toSale() {
        Toast.makeText(context, "前往特惠", Toast.LENGTH_SHORT).show();
    }

	public void toTopic() {
        Toast.makeText(context, "前往最热", Toast.LENGTH_SHORT).show();
    }

}
