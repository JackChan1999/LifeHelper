package com.qz.lifehelper.ui.activity;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.qz.lifehelper.R;
import com.qz.lifehelper.presentation.HomeActivityPresentation;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by kohoh on 15/3/18.
 */

@EActivity(R.layout.activity_home)
public class HomeActivity extends ActionBarActivity {

    @Bean
    HomeActivityPresentation presentation;

    @Click(R.id.find_food_lv)
    public void finFood() {
        presentation.findFood();
    }

    @Click(R.id.find_scenic_lv)
    public void findScenic() {
        presentation.findScenic();
    }

    @Click(R.id.find_movie_iv)
    public void findMovie() {
        presentation.findMovie();
    }

    @Click(R.id.find_hotel_iv)
    public void findHotel() {
        presentation.findHotel();
    }

    @Click(R.id.call_take_out_iv)
    public void callTakeOut() {
        presentation.callTakeOut();
    }

    @Click(R.id.special_local_product_iv)
    public void findSpecialLocalProduct() {
        presentation.findSpecialLocalProduct();
    }

    @Click(R.id.use_car_iv)
    public void useCar() {
        presentation.useCar();
    }

    @Click(R.id.more_iv)
    public void findMore() {
        presentation.findMore();
    }

    @AfterViews
    public void setActionBar() {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_home);
        View actionbar=getSupportActionBar().getCustomView();
        actionbar.findViewById(R.id.search_bn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentation.search();
            }
        });

        actionbar.findViewById(R.id.choose_city_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentation.chooseCity();
            }
        });
    }
}
