package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.Fragment;

import com.qz.lifehelper.R;
import com.qz.lifehelper.helper.ArroundHelper;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * 周边信息页
 */
@EFragment(R.layout.fragment_arround)
public class ArroundFragmnet extends Fragment {

    @Bean
    ArroundHelper arroundHelper;

    @Click(R.id.topic)
    public void onTopicClick() {
        arroundHelper.toTopic();
    }

    @Click(R.id.food)
    public void onFoodClick() {
        arroundHelper.toPOIResult("餐厅");
    }

    @Click(R.id.hotel)
    public void onHotelClick() {
        arroundHelper.toPOIResult("酒店");
    }

    @Click(R.id.hospital)
    public void onHostelClick() {
        arroundHelper.toPOIResult("医院");

    }

    @Click(R.id.toliet)
    public void onTolietClick() {
        arroundHelper.toPOIResult("厕所");

    }

    @Click(R.id.supermarket)
    public void onSupermarketClick() {
        arroundHelper.toPOIResult("超市");

    }

    @Click(R.id.bbq)
    public void onBBQClick() {
        arroundHelper.toPOIResult("烧烤");

    }

    @Click(R.id.charfingdish)
    public void onCharfingdishClick() {
        arroundHelper.toPOIResult("火锅");

    }

    @Click(R.id.more)
    public void onMoreClick() {
        arroundHelper.toPOICategory();
    }

    @Click(R.id.top10restaurant)
    public void onTop10RestaurantClick() {
        arroundHelper.toTop10Restaurant();
    }

    @Click(R.id.sale)
    public void onSaleClick() {
        arroundHelper.toSale();
    }
}
