package com.qz.lifehelper.helper;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.ui.activity.BusInfoActivity_;
import com.qz.lifehelper.ui.activity.POIResultActivity;
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
        Intent intent = POIResultActivity.generateIntent(context, locationBusiness.getCurrentCity().cityName, catrgory);
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
        Toast.makeText(context, "前往" + category + "交易市场", Toast.LENGTH_SHORT).show();

    }

    /**
     * 前往交易市场类别目录页
     */
    public void toMarketCategory() {
        Toast.makeText(context, "前往更多交易市场", Toast.LENGTH_SHORT).show();
    }
}
