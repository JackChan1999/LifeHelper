package com.qz.lifehelper.presentation;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.business.POIBusiness;
import com.qz.lifehelper.entity.City;
import com.qz.lifehelper.entity.POIResult;
import com.qz.lifehelper.event.GetCurrentCityEvent;
import com.qz.lifehelper.event.GetPOIResultEvent;
import com.qz.lifehelper.ui.activity.ChooseCityActivity_;

import de.greenrobot.event.EventBus;

/**
 * Created by kohoh on 15/3/20.
 */
@EBean
public class POIResultActivitPresentation {

	@Bean
	public LocationBusiness locationBusiness;
	@Bean
	public POIBusiness poiBusiness;

	@RootContext
	public Context context;

	private IPOIResultView poiResultView;

	private City currentCity;
	private String distance = "0.5公里";
	private String category = "美食";
	private String grade = "4星";
	private List<POIResult> poiResults;

	private POIResultListAdpater poiResultListAdpater;

	public void registerEventBus() {
		poiBusiness.getEventBus().register(this);
		EventBus.getDefault().register(this);
	}

	public void unregisterEventBus() {
		poiBusiness.getEventBus().unregister(this);
		EventBus.getDefault().unregister(this);
	}

	public void bind(IPOIResultView POIResultView) {
		this.poiResultView = POIResultView;
		registerEventBus();

		currentCity = locationBusiness.getCurrentCity();
		if (currentCity == null) {
			Intent intent = new Intent(context, ChooseCityActivity_.class);
			context.startActivity(intent);
		} else {
			POIResultView.setCurrentLocation(currentCity);
		}
		reload(currentCity, category, distance);
	}

	public void unBind() {
		unregisterEventBus();
	}

	@AfterInject
	public void setPOIResultListAdapter() {
		poiResultListAdpater = new POIResultListAdpater(context);
	}

	public ListAdapter getPOIResultListAdapter() {
		return poiResultListAdpater;
	}

	public SpinnerAdapter getDistanceSpinnerAdapter() {
		ArrayAdapter<String> distanceSpinnerAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, context.getResources().getStringArray(R.array.distance));
		return distanceSpinnerAdapter;
	}

	public SpinnerAdapter getCategorySpinnerAdapter() {
		ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, context.getResources().getStringArray(R.array.category));
		return categorySpinnerAdapter;
	}

	public SpinnerAdapter getGradeSpinnerAdapter() {
		ArrayAdapter<String> gradeSpinnerAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, context.getResources().getStringArray(R.array.grade));
		return gradeSpinnerAdapter;
	}

	public void setDistance(String distance) {
		this.distance = distance;
		reload(currentCity, category, grade);
	}

	public void setCategory(String category) {
		this.category = category;
		reload(currentCity, category, grade);
	}

	public void setGrade(String grade) {
		this.grade = grade;
		reload(currentCity, category, grade);
	}

	public void reload(City city, String category, String distance) {
		if (city == null || city.cityName == null || city.cityName.equals("") || category == null
				|| category.equals("")) {
			return;
		}
		poiResultView.starLoadPOIData();
		poiBusiness.loadPOIData(currentCity, category, distance);
	}

	public void onEventMainThread(GetPOIResultEvent event) {
		List<POIResult> poiResults = event.poiResults;
		if (poiResults == null || poiResults.size() == 0) {
			poiResultView.loadPOIDataFial();
		} else {
			this.poiResults = poiResults;
			poiResultListAdpater.setData(poiResults);
			poiResultListAdpater.notifyDataSetChanged();
			poiResultView.loadPOIDataSuccess();
		}
	}

	public void onEvent(GetCurrentCityEvent event) {
		currentCity = event.currentCity;
		reload(currentCity, category, distance);
	}
}

class POIResultListAdpater extends BaseAdapter {

	List<POIResult> data = new ArrayList<>();

	private Context context;

	POIResultListAdpater(Context context) {
		this.context = context;
	}

	public void setData(List<POIResult> data) {
		if (data != null) {
			this.data.clear();
			this.data.addAll(data);
		}
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ItemPOIREsultChilds {
		ImageView poiIv;
		TextView titleTv;
		TextView addressTv;
		ProgressBar gradePb;
		TextView categoryTv;
		TextView distanceTv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_poi_result, parent, false);
			ItemPOIREsultChilds childs = new ItemPOIREsultChilds();
			childs.poiIv = (ImageView) convertView.findViewById(R.id.poi_iv);
			childs.titleTv = (TextView) convertView.findViewById(R.id.title_tv);
			childs.addressTv = (TextView) convertView.findViewById(R.id.address_tv);
			childs.gradePb = (ProgressBar) convertView.findViewById(R.id.grade_pb);
			childs.categoryTv = (TextView) convertView.findViewById(R.id.category_tv);
			childs.distanceTv = (TextView) convertView.findViewById(R.id.distance_tv);
			convertView.setTag(childs);
		}

		ItemPOIREsultChilds childs = (ItemPOIREsultChilds) convertView.getTag();
		POIResult poiResult = data.get(position);

		childs.poiIv.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_light));
		childs.titleTv.setText(poiResult.title);
		childs.addressTv.setText(poiResult.address);
		childs.gradePb.setProgress(poiResult.grade);
		childs.categoryTv.setText(poiResult.category);
		childs.distanceTv.setText(poiResult.distance);
		return convertView;
	}
}
