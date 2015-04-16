package com.qz.lifehelper.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.P2PCategoryBean;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * P2PCategoryFragment使用的adapter
 */
@EBean
public class P2PCategoryAdapter extends BaseAdapter {

    private List<P2PCategoryBean> data = new ArrayList<>();

    public void setData(List<P2PCategoryBean> data) {
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

    @RootContext
    Context context;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        class ChildViews {
            ImageView imageIv;
            TextView titleTv;
            TextView contentTv;
        }

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_p2p_category, parent, false);
            ChildViews childViews = new ChildViews();
            childViews.imageIv = (ImageView) convertView.findViewById(R.id.image_tv);
            childViews.titleTv = (TextView) convertView.findViewById(R.id.title_tv);
            childViews.contentTv = (TextView) convertView.findViewById(R.id.content_tv);
            convertView.setTag(childViews);
        }

        ChildViews childViews = (ChildViews) convertView.getTag();
        P2PCategoryBean p2pCategoryBean = data.get(position);

        childViews.titleTv.setText(p2pCategoryBean.title);
        childViews.contentTv.setText(p2pCategoryBean.content);
        //TODO 设置图片

        return convertView;
    }
}
