package com.qz.lifehelper.presentation;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.entity.City;
import com.qz.lifehelper.event.GetCurrentCityEvent;
import com.qz.lifehelper.ui.activity.ChooseCityActivity_;

import de.greenrobot.event.EventBus;

/**
 * Created by kohoh on 15/3/18.
 */
@EBean
public class HomeActivityPresentation {

	@Bean
	LocationBusiness locationBusiness;

	@RootContext
	Context context;

	private void toSearchResult() {
		Toast.makeText(context, "前往分类搜索页面", Toast.LENGTH_SHORT).show();
	}

	private void toMoreCategory() {
		Toast.makeText(context, "前往更多分类页面", Toast.LENGTH_SHORT).show();
	}

	private void toChooseCity() {
        Intent intent = new Intent(context, ChooseCityActivity_.class);
        context.startActivity(intent);
    }

	private void toSearch() {
		Toast.makeText(context, "前往搜索页面", Toast.LENGTH_SHORT).show();
	}

	public void findFood() {
		toSearchResult();
	}

	public void findScenic() {
		toSearchResult();
	}

	public void findMovie() {
		toSearchResult();
	}

	public void findHotel() {
		toSearchResult();
	}

	public void callTakeOut() {
		toSearchResult();
	}

	public void findSpecialLocalProduct() {
		toSearchResult();
	}

	public void useCar() {
		toSearchResult();
	}

	public void findMore() {
		toMoreCategory();
	}

	public void chooseCity() {
		toChooseCity();
	}

	public void search() {
		toSearch();
	}

    public void getCurrentCity() {
        City currentCity = locationBusiness.getCurrentCity();
        if (currentCity == null) {
            toChooseCity();
        } else {
            EventBus.getDefault().post(GetCurrentCityEvent.generateEvent(currentCity));
        }
    }
}
