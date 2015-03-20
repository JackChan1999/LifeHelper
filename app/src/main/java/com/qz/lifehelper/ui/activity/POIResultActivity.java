package com.qz.lifehelper.ui.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.City;
import com.qz.lifehelper.presentation.IPOIResultView;
import com.qz.lifehelper.presentation.POIResultActivitPresentation;

/**
 * Created by kohoh on 15/3/19.
 */
@EActivity(R.layout.activity_poi_result)
public class POIResultActivity extends ActionBarActivity implements IPOIResultView {

	@ViewById(R.id.category_sp)
	public Spinner categortSp;

	@ViewById(R.id.distance_sp)
	public Spinner distanceSp;

	@ViewById(R.id.grade_sp)
	public Spinner gradeSp;

	@ViewById(R.id.poi_result_lv)
	public ListView poiResultLv;

	@ViewById(R.id.current_loaction_tv)
	public TextView currentLocationTv;

	@Bean
	public POIResultActivitPresentation presentation;

	@Override
	protected void onStart() {
		super.onResume();
		presentation.bind(this);
	}

	@Override
	protected void onStop() {
		super.onPause();
		presentation.unBind();
	}

	@AfterViews
	public void setCategortSp() {
		categortSp.setAdapter(presentation.getCategorySpinnerAdapter());
		final String[] categorys = this.getResources().getStringArray(R.array.category);
		categortSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				presentation.setCategory(categorys[position]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	@AfterViews
	public void setDistanceSp() {
		distanceSp.setAdapter(presentation.getDistanceSpinnerAdapter());
		final String[] distances = this.getResources().getStringArray(R.array.distance);
		distanceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				presentation.setDistance(distances[position]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	@AfterViews
	public void setGradeSp() {
		gradeSp.setAdapter(presentation.getGradeSpinnerAdapter());
		final String[] grades = this.getResources().getStringArray(R.array.grade);
		gradeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				presentation.setGrade(grades[position]);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	@AfterViews
	public void setPoiResltListSp() {
		poiResultLv.setAdapter(presentation.getPOIResultListAdapter());
	}

	@Override
	public void setCurrentLocation(City currentLoaction) {
		String currentLocaion = "我在：" + currentLoaction.cityName;
		currentLocationTv.setText(currentLocaion);
	}

	@Override
	public void starLoadPOIData() {

	}

	@Override
	public void loadPOIDataSuccess() {

	}

	@Override
	public void loadPOIDataFial() {

	}
}
