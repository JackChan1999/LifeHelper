package com.qz.lifehelper.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.PlaneInfoBean;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 航班信息结果页的适配器
 */
@EBean
public class PlaneInfoAdapter extends BaseAdapter {

    @RootContext
    Context context;

    private List<PlaneInfoBean> planeInfoBeans = new ArrayList<>();

    @Override
    public int getCount() {
        return planeInfoBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return planeInfoBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        class PlaneInfoItemChildViews {
            TextView planeInfotv;
            TextView startTimeTv;
            TextView endTimeTv;
            TextView startAirportTv;
            TextView endAirportTv;
            TextView onTineRateTv;
        }

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_plane_info, parent, false);
            PlaneInfoItemChildViews childViews = new PlaneInfoItemChildViews();
            childViews.planeInfotv = (TextView) convertView.findViewById(R.id.plane_info_tv);
            childViews.startTimeTv = (TextView) convertView.findViewById(R.id.start_time_tv);
            childViews.endTimeTv = (TextView) convertView.findViewById(R.id.end_time_tv);
            childViews.startAirportTv = (TextView) convertView.findViewById(R.id.start_airport_tv);
            childViews.endAirportTv = (TextView) convertView.findViewById(R.id.end_airport_tv);
            childViews.onTineRateTv = (TextView) convertView.findViewById(R.id.on_time_rate_tv);
            convertView.setTag(childViews);
        }

        PlaneInfoItemChildViews childViews = (PlaneInfoItemChildViews) convertView.getTag();
        PlaneInfoBean planeInfoBean = planeInfoBeans.get(position);

        childViews.planeInfotv.setText(planeInfoBean.planeInfo);
        childViews.startTimeTv.setText(planeInfoBean.startTime);
        childViews.endTimeTv.setText(planeInfoBean.endTime);
        childViews.startAirportTv.setText(planeInfoBean.startAirport);
        childViews.endAirportTv.setText(planeInfoBean.endAirport);
        childViews.onTineRateTv.setText(planeInfoBean.onTimeRate);
        return convertView;
    }

    public void setData(List<PlaneInfoBean> data) {
        planeInfoBeans.clear();
        planeInfoBeans.addAll(data);
    }
}
