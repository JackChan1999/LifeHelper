package com.qz.lifehelper.ui.activity;

import android.support.v7.app.ActionBarActivity;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.PlaneBusiness;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

/**
 * 机票信息查询页
 * <p/>
 * 他只是一个wrapper，具体逻辑在各个相关的Fragment中
 */
@EActivity(R.layout.layout_fragment_container)
public class PlaneInfoActivity extends ActionBarActivity {

    @Bean
    PlaneBusiness planeBusiness;

    /**
     * 一开始先跳转到PlaneInfoRequestFragment
     * <p/>
     * 该页面用于配置要搜索的信息参数
     */
    @AfterViews()
    void setPlaneInfoSearchArgument() {
        planeBusiness.toPlaneInfoRequestFragment(getSupportFragmentManager());
    }

}
