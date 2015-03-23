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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.POIBusiness;
import com.qz.lifehelper.entity.City;
import com.qz.lifehelper.entity.POIResult;
import com.qz.lifehelper.event.GetCurrentCityEvent;
import com.qz.lifehelper.event.GetPOIResultEvent;
import com.qz.lifehelper.ui.activity.POIResultDetailActivity;

import de.greenrobot.event.EventBus;

/**
 * Created by kohoh on 15/3/20.
 */
@EBean
public class POIResultActivitPresentation {
	@Bean
	public POIBusiness poiBusiness;

	@RootContext
	public Context context;

	private IPOIResultView poiResultView;

	private City currentCity;
	private String category;
	private List<POIResult> poiResults;

	private POIResultListAdpater poiResultListAdpater;

    /**
     * 注册EventBus
     */
	public void registerEventBus() {
		poiBusiness.getEventBus().register(this);
		EventBus.getDefault().register(this);
	}

    /**
     * 取消注册EventBus
     */
	public void unregisterEventBus() {
		poiBusiness.getEventBus().unregister(this);
		EventBus.getDefault().unregister(this);
	}

    /**
     * 设置当前当位置
     */
	public void setCurrentLocation(String currentLocation) {
		currentCity = City.generateCity(currentLocation);
	}

    /**
     * 开始绑定数据
     */
	public void bind(IPOIResultView POIResultView) {
		this.poiResultView = POIResultView;
		registerEventBus();
		reload(currentCity, category);
	}

    /**
     * 停止绑定数据
     */
	public void unBind() {
		unregisterEventBus();
	}

    /**
     * 设置POI结果列表适配器
     */
	@AfterInject
	public void setPOIResultListAdapter() {
		poiResultListAdpater = new POIResultListAdpater(context);
	}

    /**
     * 获取POI结果列表适配器
     */
	public ListAdapter getPOIResultListAdapter() {
		return poiResultListAdpater;
	}

    /**
     * 设置POI搜索分类
     */
	public void setCategory(String category) {
		this.category = category;
	}

    /**
     * 重新加载数据
     */
	public void reload(City city, String category) {
		if (city == null || city.cityName == null || city.cityName.equals("") || category == null
				|| category.equals("")) {
			return;
		}
		poiResultView.starLoadPOIData();
		poiBusiness.loadPOIData(currentCity, category);
	}

    /**
     * 当成功加载到了POI结果数据，将会调用该方法
     */
    //TODO 后面考虑使用bolts代替
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

    /**
     * 当当前城市发生改变时，被调用
     */
	public void onEvent(GetCurrentCityEvent event) {
		currentCity = event.currentCity;
		reload(currentCity, category);
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
		TextView telTv;
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
			childs.telTv = (TextView) convertView.findViewById(R.id.tel_tv);
			convertView.setTag(childs);
		}

		ItemPOIREsultChilds childs = (ItemPOIREsultChilds) convertView.getTag();
		final POIResult poiResult = data.get(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = POIResultDetailActivity.generateIntent(context, poiResult.id);
                context.startActivity(intent);
            }
        });

		childs.poiIv.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_light));
		childs.titleTv.setText(poiResult.title);
		childs.addressTv.setText(poiResult.address);
		childs.telTv.setText(poiResult.tel);
		return convertView;
	}
}
