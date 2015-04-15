package com.qz.lifehelper.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qz.lifehelper.entity.HomeFragmentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 存放子页面当PagerView的adapter
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    List<HomeFragmentBean> data = new ArrayList<>();

    public void setData(List<HomeFragmentBean> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    public HomePagerAdapter(FragmentManager fm) {
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
