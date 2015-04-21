package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.Fragment;

import com.qz.lifehelper.R;
import com.qz.lifehelper.helper.LifeHelper;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * 生活信息页
 */

@EFragment(R.layout.fragmnet_life)
public class LifeFragment extends Fragment {

    @Bean
    LifeHelper lifeHelper;

    @Click(R.id.plane)
    public void onPlaneClick() {
        lifeHelper.toPlane();
    }

    @Click(R.id.bus)
    public void onBusClick() {
        lifeHelper.toBus();
    }

    @Click(R.id.train)
    public void onTrainClick() {
        lifeHelper.toTrain();
    }

    @Click(R.id.move)
    public void onMoveClick() {
        lifeHelper.toPOIResult("物流");

    }

    @Click(R.id.maternitymatron)
    public void onMaternityMatronClick() {
        lifeHelper.toPOIResult("月嫂");
    }

    @Click(R.id.cleaner)
    public void onCleanerClick() {
        lifeHelper.toPOIResult("保洁");
    }

    @Click(R.id.buy_fruit)
    public void onBuyFruitClick() {
        lifeHelper.toMarketResult("蔬菜家禽");
    }

    @Click(R.id.buy_electric_appliance)
    public void onBuyElectricAppliance() {
        lifeHelper.toMarketResult("电子数码");
    }

    @Click(R.id.buy_clothes)
    public void onBuyClothes() {
        lifeHelper.toMarketResult("衣服鞋包");
    }

    @Click(R.id.buy_more)
    public void onBuyMore() {
        lifeHelper.toMarketCategory();
    }
}
