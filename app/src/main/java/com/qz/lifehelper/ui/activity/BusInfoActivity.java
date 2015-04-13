package com.qz.lifehelper.ui.activity;

import android.support.v7.app.ActionBarActivity;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.BusBusiness;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

/**
 * 长途大巴票信息Activity
 * <p/>
 * 它只是一个wrapper，具体的业务逻辑是由Fragment实现的
 */
@EActivity(R.layout.layout_fragment_container)
public class BusInfoActivity extends ActionBarActivity {

    @Bean
    BusBusiness busBusiness;

    /**
     * 跳转到TrainInfoRequestFragment
     * <p/>
     * 该页面用于设置要搜索的火车票信息的参数
     */
    @AfterViews
    void setTrainInfoSearchArgument() {
        busBusiness.toBusInfoRequestFragment(this.getSupportFragmentManager());
    }

}
