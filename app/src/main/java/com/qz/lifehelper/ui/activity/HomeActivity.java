package com.qz.lifehelper.ui.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import bolts.Continuation;
import bolts.Task;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.helper.HomeHelper;

/**
 * 主页面
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

		final TextView currentCityTv = (TextView) actionbar.findViewById(R.id.current_city_tv);
		currentCityTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				homeHelper.chooseCity().onSuccess(new Continuation<String, Void>() {
					@Override
					public Void then(Task<String> task) throws Exception {
						currentCityTv.setText(task.getResult());
						return null;
					}
				});
			}
		});
		homeHelper.getCurrentCity().onSuccess(new Continuation<String, Void>() {
			@Override
			public Void then(Task<String> task) throws Exception {
				currentCityTv.setText(task.getResult());
				return null;
			}
		});
	}

	@Bean
	LocationBusiness locationBusiness;

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
