package com.qz.lifehelper.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.POIResultBean;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 十大旅游景点的Adapter
 */
@EBean
public class TopAdapter extends BaseAdapter {

    @RootContext
    Context context;

    List<POIResultBean> data = new ArrayList<>();

    public void setData(List<POIResultBean> data) {
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

        class ChildViews {
            TextView titleTv;
            TextView contentTv;
            ImageView imageIv;
        }

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ten_top_spots, parent, false);
            ChildViews childViews = new ChildViews();
            childViews.titleTv = (TextView) convertView.findViewById(R.id.poi_title_tv);
            childViews.contentTv = (TextView) convertView.findViewById(R.id.poi_context_tv);
            childViews.imageIv = (ImageView) convertView.findViewById(R.id.poi_image_tv);
            convertView.setTag(childViews);
        }

        ChildViews childViews = (ChildViews) convertView.getTag();
        POIResultBean poiResultBean = this.data.get(position);

        childViews.titleTv.setText(poiResultBean.title);
        childViews.contentTv.setText(poiResultBean.detail);
        Picasso.with(context)
                .load(poiResultBean.imageBean.imageSrc)
                .fit()
                .into(childViews.imageIv);

        return convertView;
    }
}
