package com.qz.lifehelper.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.event.GetCurrentCityEvent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import bolts.Continuation;
import bolts.Task;

/**
 * 选择城市页面
 *
 * 这只是一个wrapper，真正的逻辑是由ChooseCityFragment实现的
 */
@EActivity(R.layout.layout_fragment_container)
public class ChooseCityActivity extends ActionBarActivity {

    @AfterViews
    public void setActionBar() {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        this.getSupportActionBar().setCustomView(R.layout.actionbar_choose_city);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Bean
    LocationBusiness locationBusiness;

    @AfterViews
    public void toChooseCity() {
        locationBusiness.chooseCity(this.getSupportFragmentManager()).onSuccess(new Continuation<CityBean, Void>() {
            @Override
            public Void then(Task<CityBean> task) throws Exception {
                locationBusiness.getEventBus().post(GetCurrentCityEvent.generateEvent(task.getResult()));
                locationBusiness.setCurrentCity(task.getResult());
                finish();
                return null;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
