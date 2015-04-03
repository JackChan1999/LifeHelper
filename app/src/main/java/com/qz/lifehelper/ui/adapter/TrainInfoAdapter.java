package com.qz.lifehelper.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.TrainInfoBean;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类是TrainInfoResultFragment使用的适配器，适配火车票的信息
 */
@EBean
public class TrainInfoAdapter extends BaseAdapter {

    @RootContext
    Context context;

    private List<TrainInfoBean> data = new ArrayList<>();

    public void setData(List<TrainInfoBean> data) {
        this.data.clear();
        this.data.addAll(data);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        class TrainInfoItemChildViews {
            TextView trainInfotv;
            TextView startTimeTv;
            TextView endTimeTv;
            TextView startStationTv;
            TextView endStationTv;
            TextView durationTv;
            TextView surplusTicketCountTv;
        }

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_train_info, parent, false);
            TrainInfoItemChildViews childViews = new TrainInfoItemChildViews();
            childViews.trainInfotv = (TextView) convertView.findViewById(R.id.train_info_tv);
            childViews.startTimeTv = (TextView) convertView.findViewById(R.id.start_time_tv);
            childViews.endTimeTv = (TextView) convertView.findViewById(R.id.end_time_tv);
            childViews.startStationTv = (TextView) convertView.findViewById(R.id.start_station_tv);
            childViews.endStationTv = (TextView) convertView.findViewById(R.id.end_station_tv);
            childViews.durationTv = (TextView) convertView.findViewById(R.id.duration_tv);
            childViews.surplusTicketCountTv = (TextView) convertView.findViewById(R.id.surplus_ticket_count_tv);
            convertView.setTag(childViews);
        }

        TrainInfoItemChildViews childViews = (TrainInfoItemChildViews) convertView.getTag();
        TrainInfoBean trainInfoBean = data.get(position);

        childViews.trainInfotv.setText(trainInfoBean.trainInfo);
        childViews.startTimeTv.setText(trainInfoBean.startTime);
        childViews.endTimeTv.setText(trainInfoBean.endTime);
        childViews.startStationTv.setText(trainInfoBean.startStation);
        childViews.endStationTv.setText(trainInfoBean.endStation);
        childViews.durationTv.setText(trainInfoBean.duration);
        childViews.surplusTicketCountTv.setText(trainInfoBean.surplusTicketCount);
        return convertView;
    }

}
