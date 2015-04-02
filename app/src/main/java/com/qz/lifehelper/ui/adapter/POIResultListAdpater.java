package com.qz.lifehelper.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.POIResultBean;
import com.qz.lifehelper.ui.activity.POIResultDetailActivity;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 该adapter用于POI搜索结果列表
 */
@EBean
public class POIResultListAdpater extends BaseAdapter {

    List<POIResultBean> data = new ArrayList<>();

    @RootContext
    Context context;

    public void setData(List<POIResultBean> data) {
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
        final POIResultBean poiResultBean = data.get(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = POIResultDetailActivity.generateIntent(context, poiResultBean.id);
                context.startActivity(intent);
            }
        });

        childs.poiIv.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_light));
        childs.titleTv.setText(poiResultBean.title);
        childs.addressTv.setText(poiResultBean.address);
        childs.telTv.setText(poiResultBean.tel);
        return convertView;
    }
}
