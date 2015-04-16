package com.qz.lifehelper.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        HomeFragment fragment = new HomeFragment.Builder()
                .create();
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }
}
