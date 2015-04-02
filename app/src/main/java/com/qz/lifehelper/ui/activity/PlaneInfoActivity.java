package com.qz.lifehelper.ui.activity;

import android.support.v7.app.ActionBarActivity;

import com.qz.lifehelper.R;
import com.qz.lifehelper.helper.PlaneInfoHelper;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

/**
 * 机票信息查询页
 * <p/>
 * 他只是一个wrapper，具体逻辑在各个相关的Fragment中
 */
@EActivity(R.layout.activity_plane_info)
public class PlaneInfoActivity extends ActionBarActivity {

    @Bean
    PlaneInfoHelper planeInfoHelper;

    /**
     * 配置PlaneInfoHelper
     * <p/>
     * 需要在这里配置FragmentManager
     */
    @AfterInject()
    void setSearchPlaneHelper() {
        planeInfoHelper.setFragmentManager(this.getSupportFragmentManager());
    }

    /**
     * 一开始先跳转到SearchPlaneInfoFragment
     *
     * 该页面用于配置要搜索的信息参数
     */
    @AfterViews()
    void showSearchPlaneInfoFragmnet() {
        planeInfoHelper.setPlaneInfoSearchArgument();
    }

}
