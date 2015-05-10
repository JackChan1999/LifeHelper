package com.qz.lifehelper.helper;

import android.content.Context;
import android.content.Intent;

import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.entity.P2PCategoryBean;
import com.qz.lifehelper.entity.P2PRequestBean;
import com.qz.lifehelper.ui.activity.BusInfoActivity_;
import com.qz.lifehelper.ui.activity.P2PActivity;
import com.qz.lifehelper.ui.activity.POIActivity;
import com.qz.lifehelper.ui.activity.PlaneInfoActivity_;
import com.qz.lifehelper.ui.activity.TrainInfoActivity_;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;


/**
 * LifeFragment的助手
 * <p/>
 * 帮助LifeFragment实现一部分业务逻辑
 */

@EBean
public class LifeHelper {

    @Bean
    LocationBusiness locationBusiness;

    @RootContext
    Context context;

    /**
     * 前往POI搜索结果页
     */
    public void toPOIResult(String catrgory) {
        Intent intent = POIActivity.generatePOIListIntent(context, catrgory, locationBusiness.getCurrentCity().cityName);
        context.startActivity(intent);
    }

    /**
     * 前往航班信息页
     */
    public void toPlane() {
        Intent intent = new Intent(context, PlaneInfoActivity_.class);
        context.startActivity(intent);
    }

    /**
     * 前往火车信息页
     */
    public void toTrain() {
        Intent intent = new Intent(context, TrainInfoActivity_.class);
        context.startActivity(intent);
    }

    /**
     * 前往长途汽车页
     */
    public void toBus() {
        Intent intent = new Intent(context, BusInfoActivity_.class);
        context.startActivity(intent);
    }

    /**
     * 前往交易市场搜索结果页
     */
    public void toMarketResult(String category) {
        Intent intent = P2PActivity.generateIntent(context
                , new P2PRequestBean()
                .setFragmentType(P2PRequestBean.FragmentType.P2P_LIST)
                .setCategory(new P2PCategoryBean().setTitle(category)));

        context.startActivity(intent);
    }

    /**
     * 前往交易市场类别目录页
     */
    public void toMarketCategory() {
        Intent intent = P2PActivity.generateIntent(context
                , new P2PRequestBean()
                .setFragmentType(P2PRequestBean.FragmentType.P2P_CATEGORY));

        context.startActivity(intent);
    }
}
