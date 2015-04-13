package com.qz.lifehelper.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.json.BusInfoBean;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kohoh on 15/4/13.
 */
@EBean
public class BusInfoAdapter extends BaseAdapter {

    @RootContext
    Context context;

    private List<BusInfoBean> data = new ArrayList<>();

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        class BusInfoChildViews {
            TextView startTimeTv;
            TextView startStationTv;
            TextView endStationTv;
            TextView ticketPriceTv;
        }

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_bus_info, parent, false);
            BusInfoChildViews childViews = new BusInfoChildViews();
            childViews.startTimeTv = (TextView) convertView.findViewById(R.id.start_time_tv);
            childViews.startStationTv = (TextView) convertView.findViewById(R.id.start_station_tv);
            childViews.endStationTv = (TextView) convertView.findViewById(R.id.end_station_tv);
            childViews.ticketPriceTv = (TextView) convertView.findViewById(R.id.ticket_price_tv);
            convertView.setTag(childViews);
        }

        BusInfoChildViews childViews = (BusInfoChildViews) convertView.getTag();
        BusInfoBean busInfoBean = data.get(position);

        childViews.startTimeTv.setText(busInfoBean.startTinme);
        childViews.startStationTv.setText(busInfoBean.startStation);
        childViews.endStationTv.setText(busInfoBean.endStation);
        childViews.ticketPriceTv.setText(busInfoBean.ticketPrice);

        return convertView;
    }

    public void setData(List<BusInfoBean> data) {
        this.data.clear();
        this.data.addAll(data);
    }
}
