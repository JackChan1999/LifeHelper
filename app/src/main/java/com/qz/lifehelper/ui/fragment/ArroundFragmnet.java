package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.qz.lifehelper.R;
import com.qz.lifehelper.presentation.ArroundFragmentPresentation;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by kohoh on 15/3/23.
 */
@EFragment(R.layout.fragment_arround)
public class ArroundFragmnet extends Fragment {

    @Bean
    ArroundFragmentPresentation presentation;

    @ViewById(R.id.topic)
    View topicView;

    @ViewById(R.id.food)
    View foodView;

    @ViewById(R.id.hotel)
    View hotelView;

    @ViewById(R.id.hospital)
    View hospitalView;

    @ViewById(R.id.toliet)
    View toiletView;

    @ViewById(R.id.supermarket)
    View supermarketView;

    @ViewById(R.id.bbq)
    View bbqView;

    @ViewById(R.id.charfingdish)
    View charfingdishView;

    @ViewById(R.id.more)
    View moreView;

    @ViewById(R.id.top10restaurant)
    View top10restaurantView;

    @ViewById(R.id.sale)
    View saleView;


    @Click(R.id.topic)
    public void onTopicClick() {
        presentation.toTopic();
    }

    @Click(R.id.food)
    public void onFoodClick() {
        presentation.toFood();
    }

    @Click(R.id.hotel)
    public void onHotelClick() {
        presentation.toHotel();
    }

    @Click(R.id.hospital)
    public void onHostelClick() {
        presentation.toHospital();
    }

    @Click(R.id.toliet)
    public void onTolietClick() {
        presentation.toToilet();
    }

    @Click(R.id.supermarket)
    public void onSupermarketClick() {
        presentation.toSuperMarket();
    }

    @Click(R.id.bbq)
    public void onBBQClick() {
        presentation.toBBQ();
    }

    @Click(R.id.charfingdish)
    public void onCharfingdishClick() {
        presentation.toChafingdish();
    }

    @Click(R.id.more)
    public void onMoreClick() {
        presentation.toMore();
    }

    @Click(R.id.top10restaurant)
    public void onTop10RestaurantClick() {
        presentation.toTop10Restaurant();
    }

    @Click(R.id.sale)
    public void onSaleClick() {
        presentation.toSale();
    }
}
