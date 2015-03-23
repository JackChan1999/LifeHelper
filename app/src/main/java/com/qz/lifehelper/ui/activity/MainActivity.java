package com.qz.lifehelper.ui.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.presentation.MainActivityPresentation;

/**
 * Created by kohoh on 15/3/14.
 */

/**
 * 该Activity存在的目的是：
 * 1. 在开发工程中，方便的打开某个Activity
 * 2. 作为项目架构的一个演示Demo
 *
 * 因此，在后期，这个Activity不需要存在
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

	@ViewById(R.id.activities_lv)
	ListView activitiesLv;

    //MainActiity对应到presentation
	@Bean
	MainActivityPresentation presentation;

    //设置Activity目录列表
	@AfterViews
	void setActivitiesLv() {
		ArrayAdapter<MainActivityPresentation.ActivityClass> activitiesAdapter = new ArrayAdapter<MainActivityPresentation.ActivityClass>(
				this, android.R.layout.simple_list_item_1);
		activitiesAdapter.addAll(presentation.getActivityClasses());

		activitiesLv.setAdapter(activitiesAdapter);
	}

    //导航到制定到Activity
    @ItemClick(R.id.activities_lv)
    void totargetActivity(int position) {
        Intent intent = new Intent(MainActivity.this,
                presentation.getActivityClasses().get(position).getActivityClass());
        Bundle bundle = presentation.getActivityClasses().get(position).getExtra();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        MainActivity.this.startActivity(intent);
    }

}
