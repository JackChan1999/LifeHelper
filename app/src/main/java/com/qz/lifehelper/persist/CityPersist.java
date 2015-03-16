package com.qz.lifehelper.persist;

import java.io.IOException;
import java.io.InputStream;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.io.IOUtils;

import android.content.Context;
import android.util.Log;

/**
 * Created by kohoh on 15/3/16.
 */
@EBean
public class CityPersist {

	@RootContext
	Context context;

	static final public String TAG = CityPersist.class.getSimpleName() + "TAG";

	/**
	 * 获取本地存储的全国城市列表
	 */
	public String getAllCitiesGroupByFirstChar() {
		String citiesJson = null;
		try {
			InputStream citiesInputStream = context.getAssets().open("cities");
			citiesJson = IOUtils.toString(citiesInputStream);
		} catch (IOException e) {
			Log.e(TAG, "getAllCitiesGroupByFirstChar fail", e);
			e.printStackTrace();
		}

		return citiesJson;
	}

}
