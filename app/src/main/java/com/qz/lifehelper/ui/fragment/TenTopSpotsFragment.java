package com.qz.lifehelper.ui.fragment;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.TopInfoBusiness;
import com.qz.lifehelper.entity.POIResultBean;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.List;

import bolts.Task;

/**
 * 十大景点
 */
@EFragment(R.layout.fragment_top)
public class TenTopSpotsFragment extends TopFragment {

    @Bean
    TopInfoBusiness topInfoBusiness;

    protected String getTitleName() {
        return "十大旅游景点";
    }

    protected Task<List<POIResultBean>> getData() {
        return topInfoBusiness.getTenTopSpots();
    }
}
