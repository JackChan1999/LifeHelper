package com.qz.lifehelper.ui.fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.qz.lifehelper.R;
import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.helper.HomeHelper;
import com.qz.lifehelper.ui.adapter.PagerAndTabAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import bolts.Continuation;
import bolts.Task;

/**
 * 显示Home页，实现Home页的真正的业务逻辑。
 * <p/>
 * 它内部由三个frgamnet组成：ArroundFragmnet、LifeFragment、PersonalFragment
 */
@EFragment(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {

    static public class Builder {
        private HomeFragment fragment = new HomeFragment_.FragmentBuilder_().build();

        public HomeFragment create() {
            return fragment;
        }
    }

    @Bean
    HomeHelper homeHelper;

    /**
     * 放置子页面的PagerView
     */
    @ViewById(R.id.pager)
    ViewPager pager;

    /**
     * tab标签
     */
    @ViewById(R.id.tab)
    PagerSlidingTabStrip tab;

    /**
     * ActionBar的替代物
     */
    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    /**
     * 设置放置子页面的PagerView和Tab标签
     */
    @AfterViews
    public void setPagerAndTab() {
        PagerAndTabAdapter adapter = new PagerAndTabAdapter(this.getFragmentManager());
        pager.setAdapter(adapter);
        adapter.setData(homeHelper.getFragments());
        adapter.notifyDataSetChanged();
        tab.setShouldExpand(true);
        tab.setViewPager(pager);
        tab.setTextColorResource(R.color.app_text_color_light);
        tab.setIndicatorColorResource(R.color.tab_indicator);
        tab.setDividerColorResource(R.color.tab_divider);
    }

    @AfterViews
    public void setToolBar() {
//        ((ActionBarActivity) getActivity()).setSupportActionBar(toolbar);
//        toolbar.findViewById(R.id.search_bn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                homeHelper.search();
//            }
//        });

//        toolbar.setTitle("LifeHelper");

        final TextView currentCityTv = (TextView) toolbar.findViewById(R.id.current_city_tv);
        currentCityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationBusiness.chooseCity(getFragmentManager()).onSuccess(new Continuation<CityBean, Void>() {
                    @Override
                    public Void then(Task<CityBean> task) throws Exception {
                        currentCityTv.setText(task.getResult().cityName);
                        locationBusiness.setCurrentCity(task.getResult());
                        return null;
                    }
                }, Task.UI_THREAD_EXECUTOR);
            }
        });
        homeHelper.getCurrentCity(getFragmentManager()).onSuccess(new Continuation<String, Void>() {
            @Override
            public Void then(Task<String> task) throws Exception {
                currentCityTv.setText(task.getResult());
                locationBusiness.setCurrentCity(CityBean.generateCity(task.getResult()));
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    @Bean
    LocationBusiness locationBusiness;
}
