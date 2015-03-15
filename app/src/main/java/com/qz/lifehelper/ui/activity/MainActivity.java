package com.qz.lifehelper.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.qz.lifehelper.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kohoh on 15/3/14.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

	@ViewById(R.id.activities_lv)
	ListView listView;

	@AfterViews
	void setListView() {

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        ArrayList<String> activities = new ArrayList<String>();
        activities.add("ChooseCities");
        adapter.addAll(activities);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, ChooseCityActivity_.class);
                        MainActivity.this.startActivity(intent);
                        break;
                }
            }
        });
    }

}
