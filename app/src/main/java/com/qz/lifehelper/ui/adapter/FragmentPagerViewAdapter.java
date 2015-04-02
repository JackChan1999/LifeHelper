package com.qz.lifehelper.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 存放子页面当PagerView的adapter
 */
public class FragmentPagerViewAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments = new ArrayList<>();

    /**
     * 添加子页面对应的fragment
     */
    public void addfragment(Fragment fragment) {
        fragments.add(fragment);
    }

    public FragmentPagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
