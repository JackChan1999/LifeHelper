package com.qz.lifehelper.entity;

import android.support.v4.app.Fragment;

/**
 * 封装ViewPager和PagerSlidingTabStrip的fragment数据
 */
public class FragmentBean {

    /**
     * pager中的fragment
     */
    public Fragment fragment;
    /**
     * tab的标题
     */
    public String tabTitle;

    static public FragmentBean generateHomeFragment(Fragment fragment, String tabTitle) {
        FragmentBean fragmentBean = new FragmentBean();
        fragmentBean.fragment = fragment;
        fragmentBean.tabTitle = tabTitle;
        return fragmentBean;
    }

}
