package com.qz.lifehelper.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.qz.lifehelper.business.POIBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.POICategoryBean;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

/**
 * POI的Activity
 * <p/>
 * 他只是一个wrapper，真正的业务逻辑由fragment实现
 */
@EActivity
public class POIActivity extends BaseActivity {

    private static final String FRAGMENT_TYPE = "FRAGMENT_TYPE";
    private static final String CATEGROY = "CATEGROY";
    private static final String CITY = "CITY";

    public enum FRAGMENT_TYPE_ENUM {
        POILIST, PERSONALLIST
    }

    static public Intent generatePOIListIntent(Context context, String category, String city) {
        Intent intent = new Intent(context, POIActivity_.class);
        intent.putExtra(CATEGROY, category);
        intent.putExtra(CITY, city);
        intent.putExtra(FRAGMENT_TYPE, FRAGMENT_TYPE_ENUM.POILIST);
        return intent;
    }

    static public Intent generatePersonalPOIIntent(Context context) {
        Intent intent = new Intent(context, POIActivity_.class);
        intent.putExtra(FRAGMENT_TYPE, FRAGMENT_TYPE_ENUM.PERSONALLIST);
        return intent;
    }

    @Extra(CATEGROY)
    String category;

    @Extra(CITY)
    String city;

    @Extra(FRAGMENT_TYPE)
    FRAGMENT_TYPE_ENUM fragment_type;

    @Bean
    POIBusiness poiBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (fragment_type) {
            case POILIST:
                POICategoryBean categoryBean = POICategoryBean.generate(category);
                CityBean cityBean = CityBean.generateCity(city);
                poiBusiness.toPOIListFragment(transaction, categoryBean, cityBean);
                break;
            case PERSONALLIST:
                poiBusiness.toMyPOI(transaction);
                break;
            default:
                break;
        }
    }
}
