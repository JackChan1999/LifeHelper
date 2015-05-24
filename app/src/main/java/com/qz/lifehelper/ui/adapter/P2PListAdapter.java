package com.qz.lifehelper.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.P2PItemBean;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * P2ListFragment使用的adapter
 */
@EBean
public class P2PListAdapter extends BaseAdapter {
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
            ImageView p2pImageIv;
            TextView p2pTitleTv;
            TextView p2pPriceTv;
            TextView p2pAddressTv;
            TextView p2pTelTv;
        }

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_p2p_item, parent, false);
            ChildViews childViews = new ChildViews();
            childViews.p2pImageIv = (ImageView) convertView.findViewById(R.id.p2p_image_iv);
            childViews.p2pTitleTv = (TextView) convertView.findViewById(R.id.p2p_title_tv);
            childViews.p2pPriceTv = (TextView) convertView.findViewById(R.id.p2p_price_tv);
            childViews.p2pAddressTv = (TextView) convertView.findViewById(R.id.p2p_address_tv);
            childViews.p2pTelTv = (TextView) convertView.findViewById(R.id.p2p_tel_tv);
            convertView.setTag(childViews);
        }

        ChildViews childViews = (ChildViews) convertView.getTag();
        P2PItemBean p2PItemBean = data.get(position);

        childViews.p2pTitleTv.setText(p2PItemBean.title);
        childViews.p2pPriceTv.setText(p2PItemBean.price + "元");
        childViews.p2pAddressTv.setText("add:" + p2PItemBean.address);
        childViews.p2pTelTv.setText("tel:" + p2PItemBean.tel);

        String imageSrc = p2PItemBean.imageBean.imageSrc;
        if (p2PItemBean.imageBean.imageType.equals(ImageBean.ImageType.QINIUYUN)) {
            imageSrc = imageSrc + "?imageView2/0/w/120";
            Picasso.with(context)
                    .load(imageSrc)
                    .into(childViews.p2pImageIv);
        } else {
            Picasso.with(context)
                    .load(imageSrc)
                    .resize(150, 0)
                    .into(childViews.p2pImageIv);
        }

        return convertView;
    }

    private List<P2PItemBean> data = new ArrayList<>();

    public void setData(List<P2PItemBean> data) {
        this.data.clear();
        this.data.addAll(data);
    }
}
