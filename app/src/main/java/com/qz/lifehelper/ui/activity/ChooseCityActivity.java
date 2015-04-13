package com.qz.lifehelper.ui.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.event.GetCurrentCityEvent;
import com.qz.lifehelper.ui.fragment.ChooseCityFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

/**
 * 选择城市页面
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ChooseCityFragment fragment = new ChooseCityFragment.Builder()
                .setCallback(new ChooseCityFragment.CallBcak() {
                    @Override
                    public void onCityChoosed(CityBean cityBean) {
                        locationBusiness.getEventBus().post(GetCurrentCityEvent.generateEvent(cityBean));
                        finish();
                    }
                })
                .create();
        transaction.replace(R.id.fragmrnt_container, fragment);
        transaction.commit();

    }
}
