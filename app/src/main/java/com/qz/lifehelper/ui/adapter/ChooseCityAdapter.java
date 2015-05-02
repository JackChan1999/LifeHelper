package com.qz.lifehelper.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.ChooseCityListItemData;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.CityItemBean;
import com.qz.lifehelper.entity.FindLoactionItemBean;
import com.qz.lifehelper.entity.SectionItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择城市列表对应的适配器
 */
public class ChooseCityAdapter extends BaseAdapter {
    private List<ChooseCityListItemData> itemDatas = new ArrayList<>();

    /**
     * ChooseCityAdapter的回调接口
     */
    public interface Callback {
        /**
         * 当选中一个城市后，会回调该方法
         */
        void onChooseCity(CityBean cityBean);

        /**
         * 当开始定位当前位置时，会回调该方法
         */
        void onFindCurrentLoactionCity();
    }

    /**
     * ChooseCityAdapter的构造器
     */
    static public class Builder {

        private ChooseCityAdapter adapter;

        public Builder() {
            adapter = new ChooseCityAdapter();
        }

        /**
         * 设置Context
         */
        public Builder setContext(Context context) {
            adapter.context = context;
            return this;
        }

        /**
         * 设置回调接口
         */
        public Builder setCallBack(Callback callBack) {
            adapter.callback = callBack;
            return this;
        }

        public ChooseCityAdapter create() {
            if (adapter.context == null) {
                throw new RuntimeException("没有设置Context");
            }

            if (adapter.callback == null) {
                throw new RuntimeException("没有设置回调接口");
            }

            return adapter;
        }
    }

    private Context context;
    private Callback callback;

    public void setData(List<ChooseCityListItemData> itemDatas) {
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

    /**
     * 一共分为了三种View，分别是SECTION,CITY,FIND_LOACTION
     * <p/>
     * 由于该list需要三种item，因此实现的时候，将三种item对应的view放在了一个主view中。
     * 当具体需要某种item的时候，将另外两个item影藏
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /**
         * 用于包含所有的子item
         */
        class ItemChildViews {
            public View sectionItem;
            public View cityItem;
            public View findLocationItem;
        }

        /**
         * 标题item的子view
         */
        class SectionItemChildViews {
            public TextView titleTv;
        }


        /**
         * 城市item的子view
         */
        class CityItemChildViews {
            public TextView cityNameTv;
        }

        /**
         * 定位item的子view
         */
        class FindLoactionItemChildlViews {
            public TextView cityNameTv;
            public Button findLocationBn;
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            ItemChildViews itemChildViews = new ItemChildViews();
            convertView = inflater.inflate(R.layout.item_choose_city, parent, false);
            itemChildViews.cityItem = convertView.findViewById(R.id.city_item);
            itemChildViews.sectionItem = convertView.findViewById(R.id.section_item);
            itemChildViews.findLocationItem = convertView.findViewById(R.id.find_loaction_item);
            convertView.setTag(itemChildViews);

            SectionItemChildViews sectionItemChildViews = new SectionItemChildViews();
            sectionItemChildViews.titleTv = (TextView) itemChildViews.sectionItem.findViewById(R.id.title_tv);
            itemChildViews.sectionItem.setTag(sectionItemChildViews);

            CityItemChildViews cityItemChildViews = new CityItemChildViews();
            cityItemChildViews.cityNameTv = (TextView) itemChildViews.cityItem.findViewById(R.id.city_name_tv);
            itemChildViews.cityItem.setTag(cityItemChildViews);

            FindLoactionItemChildlViews findLoactionItemChildlViews = new FindLoactionItemChildlViews();
            findLoactionItemChildlViews.cityNameTv = (TextView) itemChildViews.findLocationItem.findViewById(R.id.city_name_tv);
            findLoactionItemChildlViews.findLocationBn = (Button) itemChildViews.findLocationItem.findViewById(R.id.find_location_bn);
            itemChildViews.findLocationItem.setTag(findLoactionItemChildlViews);
        }

        final ChooseCityListItemData itemData = itemDatas.get(position);
        final ItemChildViews itemChildViews = (ItemChildViews) convertView.getTag();
        final CityItemChildViews cityItemChildViews = (CityItemChildViews) itemChildViews.cityItem.getTag();
        final SectionItemChildViews sectionItemChildViews = (SectionItemChildViews) itemChildViews.sectionItem.getTag();
        final FindLoactionItemChildlViews findLoactionItemChildlViews = (FindLoactionItemChildlViews) itemChildViews.findLocationItem.getTag();
        switch (itemData.getItemType()) {
            case SECTION:
                itemChildViews.cityItem.setVisibility(View.GONE);
                itemChildViews.sectionItem.setVisibility(View.VISIBLE);
                itemChildViews.findLocationItem.setVisibility(View.GONE);
                sectionItemChildViews.titleTv.setText(((SectionItemBean) itemData).title);
                break;
            case CITY:
                itemChildViews.cityItem.setVisibility(View.VISIBLE);
                itemChildViews.sectionItem.setVisibility(View.GONE);
                itemChildViews.findLocationItem.setVisibility(View.GONE);
                cityItemChildViews.cityNameTv.setText(((CityItemBean) itemData).cityName);
                break;
            case FIND_LOCATION:
                itemChildViews.cityItem.setVisibility(View.GONE);
                itemChildViews.sectionItem.setVisibility(View.GONE);
                itemChildViews.findLocationItem.setVisibility(View.VISIBLE);
                findLoactionItemChildlViews.cityNameTv.setText(((FindLoactionItemBean) itemData).cityName);
                if (((FindLoactionItemBean) itemData).cityName.equals(context.getString(R.string.find_location_ing))) {
                    findLoactionItemChildlViews.findLocationBn.setBackground(context.getResources().getDrawable(R.color.orange_dark));
                } else {
                    findLoactionItemChildlViews.findLocationBn.setBackground(context.getResources().getDrawable(R.color.green_dark));
                }
                findLoactionItemChildlViews.findLocationBn.setVisibility(View.VISIBLE);
                findLoactionItemChildlViews.findLocationBn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onFindCurrentLoactionCity();
                    }
                });
                break;
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = "";
                switch (itemData.getItemType()) {
                    case CITY:
                        cityName = ((CityItemBean) itemData).cityName;
                        break;
                    case FIND_LOCATION:
                        cityName = ((FindLoactionItemBean) itemData).cityName;
                        break;
                }
                if (!cityName.equals(context.getString(R.string.find_location_ing))) {
                    callback.onChooseCity(CityBean.generateCity(cityName));
                }
            }
        });

        return convertView;
    }


}
