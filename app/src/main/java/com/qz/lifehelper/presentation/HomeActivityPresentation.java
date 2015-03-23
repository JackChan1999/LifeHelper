package com.qz.lifehelper.presentation;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.Toast;

import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.entity.City;
import com.qz.lifehelper.event.GetCurrentCityEvent;
import com.qz.lifehelper.ui.activity.ChooseCityActivity_;
import com.qz.lifehelper.ui.fragment.ArroundFragmnet;
import com.qz.lifehelper.ui.fragment.ArroundFragmnet_;
import com.qz.lifehelper.ui.fragment.LifeFragment;
import com.qz.lifehelper.ui.fragment.LifeFragment_;
import com.qz.lifehelper.ui.fragment.PersonalFragment;
import com.qz.lifehelper.ui.fragment.PersonalFragment_;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by kohoh on 15/3/18.
 */
@EBean
public class HomeActivityPresentation {

	@Bean
	LocationBusiness locationBusiness;

	@RootContext
	Context context;

	private void toChooseCity() {
        Intent intent = new Intent(context, ChooseCityActivity_.class);
        context.startActivity(intent);
    }

	private void toSearch() {
		Toast.makeText(context, "前往搜索页面", Toast.LENGTH_SHORT).show();
	}

	public void chooseCity() {
		toChooseCity();
	}

	public void search() {
		toSearch();
	}

    public void getCurrentCity() {
        City currentCity = locationBusiness.getCurrentCity();
        if (currentCity == null) {
            toChooseCity();
        } else {
            EventBus.getDefault().post(GetCurrentCityEvent.generateEvent(currentCity));
        }
    }

    public PagerAdapter getContainerAdapter(FragmentManager fragmentManager) {
        return new ContainerAdapter(fragmentManager);
    }

    class ContainerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragments = new ArrayList<>();


        public ContainerAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new ArroundFragmnet_());
            fragments.add(new LifeFragment_());
            fragments.add(new PersonalFragment_());
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
