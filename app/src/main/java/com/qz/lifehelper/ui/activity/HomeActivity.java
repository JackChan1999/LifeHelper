package com.qz.lifehelper.ui.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;

/**
 * Created by kohoh on 15/3/18.
 */

@EActivity(R.layout.activity_home)
public class HomeActivity extends ActionBarActivity {

    @Bean
    HomeActivityPresentation presentation;

    @ViewById(R.id.container)
    ViewPager containerPv;

    @AfterViews
    public void setContainerPv() {
        containerPv.setAdapter(presentation.getContainerAdapter(this.getSupportFragmentManager()));
        final ActionBar actionBar = getSupportActionBar();

        containerPv.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ActionBar.TabListener tabListener=new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                containerPv.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        };

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("周边").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("生活").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("个人").setTabListener(tabListener));
    }

    TextView currentCityTv;

    @AfterViews
    public void setActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_home);
        View actionbar = getSupportActionBar().getCustomView();
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
