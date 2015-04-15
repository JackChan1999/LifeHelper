package com.qz.lifehelper.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

/**
 * 一个基础Activity
 */
abstract public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(android.R.id.content, getFragment());
        transaction.commit();
    }

    /**
     * 提供改Acitivity打开后要显示的Fragment
     */
    abstract protected Fragment getFragment();

}
