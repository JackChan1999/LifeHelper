package com.qz.lifehelper.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.qz.lifehelper.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by kohoh on 15/3/14.
 */
@EActivity(R.layout.activity_choose_city)
public class ChooseCityActivity extends ActionBarActivity {

    @AfterViews
    public void setActionBar() {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        this.getSupportActionBar().setCustomView(R.layout.actionbar_choose_city);
    }


}
