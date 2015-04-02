package com.qz.lifehelper.ui.activity;

import android.support.v7.app.ActionBarActivity;

import com.qz.lifehelper.R;
import com.qz.lifehelper.helper.SearchPlaneHelper;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

/**
 * 机票时时信息搜索
 * <p/>
 * 他只是一个wrapper，具体逻辑是在SearchPlaneFragment中
 */
@EActivity(R.layout.activity_search_plane)
public class SearchPlaneActivity extends ActionBarActivity {

    @Bean
    SearchPlaneHelper searchPlaneHelper;

    @AfterInject()
    void setSearchPlaneHelper() {
        searchPlaneHelper.setFragmentManager(this.getSupportFragmentManager());
    }

    @AfterViews()
    void showSearchPlaneFragmnet() {
        searchPlaneHelper.searchPlane();
    }

}
