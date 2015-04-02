package com.qz.lifehelper.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.helper.HomeHelper;
import com.qz.lifehelper.ui.adapter.FragmentPagerViewAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Map;

import bolts.Continuation;
import bolts.Task;

/**
 * 主页面
 */

@EActivity(R.layout.activity_home)
public class HomeActivity extends ActionBarActivity {

    @Bean
    HomeHelper homeHelper;

    /**
     * 放置子页面的PagerView
     */
    @ViewById(R.id.container)
    ViewPager containerPv;

    /**
     * 设置放置子页面的PagerView
     */
    @AfterViews
    public void setContainerPv() {
        FragmentPagerViewAdapter adapter = new FragmentPagerViewAdapter(this.getSupportFragmentManager());
        containerPv.setAdapter(adapter);
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

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
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
        for (Map.Entry<String, Fragment> entry : homeHelper.getFragments().entrySet()) {
            actionBar.addTab(actionBar.newTab().setText(entry.getKey()).setTabListener(tabListener));
            adapter.addfragment(entry.getValue());
        }
        adapter.notifyDataSetChanged();
    }

    @AfterViews
    public void setActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_home);
        View actionbar = getSupportActionBar().getCustomView();
        actionbar.findViewById(R.id.search_bn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeHelper.search();
            }
        });

        final TextView currentCityTv = (TextView) actionbar.findViewById(R.id.current_city_tv);
        currentCityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeHelper.chooseCity().onSuccess(new Continuation<String, Void>() {
                    @Override
                    public Void then(Task<String> task) throws Exception {
                        currentCityTv.setText(task.getResult());
                        return null;
                    }
                });
            }
        });
        homeHelper.getCurrentCity().onSuccess(new Continuation<String, Void>() {
            @Override
            public Void then(Task<String> task) throws Exception {
                currentCityTv.setText(task.getResult());
                return null;
            }
        });
    }

    @Bean
    LocationBusiness locationBusiness;

}
