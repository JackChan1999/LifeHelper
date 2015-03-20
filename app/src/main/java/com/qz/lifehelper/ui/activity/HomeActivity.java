package com.qz.lifehelper.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.event.GetCurrentCityEvent;
import com.qz.lifehelper.presentation.HomeActivityPresentation;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import de.greenrobot.event.EventBus;

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

    TextView currentCityTv;

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

        currentCityTv = (TextView) actionbar.findViewById(R.id.current_city_tv);
        currentCityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentation.chooseCity();
            }
        });
        presentation.getCurrentCity();
    }

    @AfterInject
    public void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    public void onEventMainThread(GetCurrentCityEvent event) {
        currentCityTv.setText(event.currentCity.cityName);
    }
}
