package com.qz.lifehelper.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qz.lifehelper.entity.FragmentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager和PagerSlidingTabStrip的adapter
 */
public class PagerAndTabAdapter extends FragmentPagerAdapter {

    List<FragmentBean> data = new ArrayList<>();

    public void setData(List<FragmentBean> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    public PagerAndTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position).fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position).tabTitle;
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
