package com.qz.lifehelper.presentation;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.widget.Toast;

import com.qz.lifehelper.business.LocationBusiness;

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
		Toast.makeText(context, "前往选择城市页面", Toast.LENGTH_SHORT).show();
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
}
