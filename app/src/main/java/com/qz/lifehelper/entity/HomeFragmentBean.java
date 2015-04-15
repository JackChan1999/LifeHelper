package com.qz.lifehelper.entity;

import android.support.v4.app.Fragment;

/**
 * 封装类Home页面中，Pager使用的数据
 */
public class HomeFragmentBean {

    /**
     * pager中的fragment
     */
    public Fragment fragment;
    /**
     * tab的标题
     */
    public String tabTitle;

    static public HomeFragmentBean generateHomeFragment(Fragment fragment, String tabTitle) {
        HomeFragmentBean homeFragmentBean = new HomeFragmentBean();
        homeFragmentBean.fragment = fragment;
        homeFragmentBean.tabTitle = tabTitle;
        return homeFragmentBean;
    }

}
