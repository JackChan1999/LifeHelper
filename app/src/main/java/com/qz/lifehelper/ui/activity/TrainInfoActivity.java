package com.qz.lifehelper.ui.activity;

import android.support.v7.app.ActionBarActivity;

import com.qz.lifehelper.R;
import com.qz.lifehelper.helper.TrainInfoHelper;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

/**
 * 火车票信息Activity
 * <p/>
 * 它只是一个wrapper，具体的业务逻辑是由Fragment实现的
 */
@EActivity(R.layout.activity_train_info)
public class TrainInfoActivity extends ActionBarActivity {

    @Bean
    TrainInfoHelper trainInfoHelper;

    /**
     * 配置TrainInfoHelper
     * <p/>
     * 需要在这里配置FragmentManager
     */
    @AfterInject
    void setTrainInfoHelper() {
        trainInfoHelper.setFragmentManager(this.getSupportFragmentManager());
    }

    /**
     * 跳转到TrainInfoRequestFragment
     * <p/>
     * 该页面用于设置要搜索的火车票信息的参数
     */
    @AfterViews
    void setTrainInfoSearchArgument() {
        trainInfoHelper.setTrainInfoSearchArgument();
    }

}
