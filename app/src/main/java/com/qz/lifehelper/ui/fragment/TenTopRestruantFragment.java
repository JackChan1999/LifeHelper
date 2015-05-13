package com.qz.lifehelper.ui.fragment;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.TopInfoBusiness;
import com.qz.lifehelper.entity.POIResultBean;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.List;

import bolts.Task;

/**
 * 十大餐厅
 */
@EFragment(R.layout.fragment_top)
public class TenTopRestruantFragment extends TopFragment {
    @Override
    protected String getTitleName() {
        return "十大餐厅";
    }

    @Bean
    TopInfoBusiness topInfoBusiness;

    @Override
    protected Task<List<POIResultBean>> getData() {
        return topInfoBusiness.getTenTopRestruant();
    }
}
