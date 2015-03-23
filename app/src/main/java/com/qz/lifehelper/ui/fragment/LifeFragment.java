package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.Fragment;

import com.qz.lifehelper.R;
import com.qz.lifehelper.presentation.LifeFragmentPresentation;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * Created by kohoh on 15/3/23.
 */

@EFragment(R.layout.fragmnet_life)
public class LifeFragment extends Fragment {

    @Bean
    LifeFragmentPresentation presentation;

    @Click(R.id.plane)
    public void onPlaneClick() {
        presentation.toPlane();
    }

    @Click(R.id.bus)
    public void onBusClick() {
        presentation.toBus();
    }

    @Click(R.id.train)
    public void onTrainClick() {
        presentation.toTrain();
    }

    @Click(R.id.move)
    public void onMoveClick() {
        presentation.toMove();
    }

    @Click(R.id.maternitymatron)
    public void onMaternityMatronClick() {
        presentation.toMaternityMatron();
    }

    @Click(R.id.cleaner)
    public void onCleanerClick() {
        presentation.toCleaner();
    }

    @Click(R.id.buy_fruit)
    public void onBuyFruitClick() {
        presentation.toBuyFruit();
    }

    @Click(R.id.buy_electric_appliance)
    public void onBuyElectricAppliance() {
        presentation.toBuyElectricAppliance();
    }

    @Click(R.id.buy_clothes)
    public void onBuyClothes() {
        presentation.toBuyClothes();
    }

    @Click(R.id.buy_more)
    public void onBuyMore() {
        presentation.toBuyMore();
    }
}
