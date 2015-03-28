package com.qz.lifehelper.helper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by kohoh on 15/3/23.
 */

@EBean
public class PersonalHelper {

	@RootContext
	Context context;

	/**
	 * 前往登录和注册页面
	 */
	public void toLogin() {
		Toast.makeText(context, "前往登录和注册页面", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 前往我发布到商品页面
	 */
	public void toMySale() {
		Toast.makeText(context, "前往我发布的商品页面", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 前往我发布的信息页面
	 */
	public void toMyPublish() {
		Toast.makeText(context, "前往我发布的信息页面", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 前往公共信息页面
	 */
	public void toPublicInf() {
		Toast.makeText(context, "前往公共信息页面", Toast.LENGTH_SHORT).show();
	}

}
