package com.qz.lifehelper.ui.activity;

import android.support.v4.app.Fragment;

import com.qz.lifehelper.ui.fragment.HomeFragment;

import org.androidannotations.annotations.EActivity;

/**
 * 主页面
 *
 * 其只是一个Fragment容器，其真正的逻辑是由具体的Fragment负责
 */

@EActivity
public class HomeActivity extends BaseActivity {


    @Override
    protected Fragment getFragment() {
        return new HomeFragment.Builder()
                .create();
    }
}
