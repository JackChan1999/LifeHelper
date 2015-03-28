package com.qz.lifehelper.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.event.GetCurrentCityEvent;
import com.qz.lifehelper.helper.HomeHelper;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by kohoh on 15/3/18.
 */

@EActivity(R.layout.activity_home)
public class HomeActivity extends ActionBarActivity {

	@Bean
	HomeHelper homeHelper;

	/**
	 * 放置子页面的PagerView
	 */
	@ViewById(R.id.container)
	ViewPager containerPv;

	/**
	 * 设置放置子页面的PagerView
	 */
	@AfterViews
	public void setContainerPv() {
		ContainerAdapter adapter = new ContainerAdapter(this.getSupportFragmentManager());
		containerPv.setAdapter(adapter);
		final ActionBar actionBar = getSupportActionBar();

		containerPv.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			@Override
			public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
				containerPv.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

			}

			@Override
			public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

			}
		};

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		for (Map.Entry<String, Fragment> entry : homeHelper.getFragments().entrySet()) {
			actionBar.addTab(actionBar.newTab().setText(entry.getKey()).setTabListener(tabListener));
			adapter.addfragment(entry.getValue());
		}
		adapter.notifyDataSetChanged();
	}

	TextView currentCityTv;

	@AfterViews
	public void setActionBar() {
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.actionbar_home);
		View actionbar = getSupportActionBar().getCustomView();
		actionbar.findViewById(R.id.search_bn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				homeHelper.search();
			}
		});

		currentCityTv = (TextView) actionbar.findViewById(R.id.current_city_tv);
		currentCityTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				homeHelper.chooseCity();
			}
		});
		homeHelper.getCurrentCity();
	}

	@AfterInject
	public void registerEventBus() {
		EventBus.getDefault().register(this);
	}

	/**
	 * 当当前选择城市被修改时，更新Home页面当当前城市
	 */
	public void onEventMainThread(GetCurrentCityEvent event) {
		currentCityTv.setText(event.currentCity.cityName);
	}

	/**
     * 存放子页面当PagerView的adapter
     */
	class ContainerAdapter extends FragmentPagerAdapter {

		List<Fragment> fragments = new ArrayList<>();

        /**
         * 添加子页面对应的fragment
         */
		public void addfragment(Fragment fragment) {
			fragments.add(fragment);
		}

		public ContainerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}
	}
}
