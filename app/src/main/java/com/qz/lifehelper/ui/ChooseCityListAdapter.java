package com.qz.lifehelper.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.ChooseCityListItemData;
import com.qz.lifehelper.entity.ChooseCityListItemSection;
import com.qz.lifehelper.entity.City;

/**
 * 选择城市列表对应的适配器
 */
public class ChooseCityListAdapter extends BaseAdapter {
	private List<ChooseCityListItemData> itemDatas = new ArrayList<>();
	private Context context;

	public ChooseCityListAdapter(Context context) {
		this.context = context;
	}

	public void setItemDatas(List<ChooseCityListItemData> itemDatas) {
		this.itemDatas.clear();
		this.itemDatas.addAll(itemDatas);
	}

	@Override
	public int getCount() {
		return itemDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return itemDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		/**
		 * item_choose_city的子view
		 */
		class ItemChildView {
			public View chooseCitySection;
			public View chooseCityItem;
		}

		/**
		 * item_choose_city_section的子view
		 */
		class ChooseCitySectionChildView {
			public TextView titleTv;
		}

		/**
		 * item_choose_city_item的子view
		 */
		class ChooseCityItemChildView {
			public TextView cityNameTv;
		}

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			ItemChildView itemChildView = new ItemChildView();
			convertView = inflater.inflate(R.layout.item_choose_city, parent, false);
			itemChildView.chooseCityItem = convertView.findViewById(R.id.item_choose_city_item);
			itemChildView.chooseCitySection = convertView.findViewById(R.id.item_choose_city_section);

			ChooseCitySectionChildView chooseCitySectionChildView = new ChooseCitySectionChildView();
			chooseCitySectionChildView.titleTv = (TextView) itemChildView.chooseCitySection.findViewById(R.id.title_tv);
			itemChildView.chooseCitySection.setTag(chooseCitySectionChildView);

			ChooseCityItemChildView chooseCityItemChildView = new ChooseCityItemChildView();
			chooseCityItemChildView.cityNameTv = (TextView) itemChildView.chooseCityItem.findViewById(R.id.city_name_tv);
			itemChildView.chooseCityItem.setTag(chooseCityItemChildView);

			convertView.setTag(itemChildView);
		}

		ChooseCityListItemData itemData = itemDatas.get(position);
		ItemChildView itemChildView = (ItemChildView) convertView.getTag();
		ChooseCityItemChildView chooseCityItemChildView = (ChooseCityItemChildView) itemChildView.chooseCityItem.getTag();
		ChooseCitySectionChildView chooseCitySectionChildView = (ChooseCitySectionChildView) itemChildView.chooseCitySection.getTag();
		switch (itemData.getItemType()) {
		case SECTION:
			itemChildView.chooseCityItem.setVisibility(View.GONE);
			itemChildView.chooseCitySection.setVisibility(View.VISIBLE);
			chooseCitySectionChildView.titleTv.setText(((ChooseCityListItemSection) itemData).title);
			break;
		case CITY:
			itemChildView.chooseCityItem.setVisibility(View.VISIBLE);
			itemChildView.chooseCitySection.setVisibility(View.GONE);
			chooseCityItemChildView.cityNameTv.setText(((City) itemData).cityName);
			break;
		}

		return convertView;
	}
}
