package com.qz.lifehelper.presentation;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.widget.Toast;

import com.qz.lifehelper.business.AppBusiness;
import com.qz.lifehelper.business.AuthenticationBusiness;

/**
 * Created by kohoh on 15/3/23.
 */

@EBean
public class PersonalFragmentPresentation {

	private IPersonalView personalView;

	@Bean
	AuthenticationBusiness authenticationBusiness;

	@Bean
	AppBusiness appBusiness;


	@RootContext
	Context context;

    //这里的登录分为辆步
    //1. 已经登录
    //2. 登录后跳转

	public void bind(IPersonalView personalView) {
		this.personalView = personalView;

		if (authenticationBusiness.isLogin()) {
//			personalView.login();
		} else {
//			personalView.logout();
		}

		personalView.setAppVersion(getAppVersion());
	}

	public void unbind() {
	}


	public void toLogin() {
		authenticationBusiness.login();
	}

	public void toMySale() {
		Toast.makeText(context, "前往我发布的商品页面", Toast.LENGTH_SHORT).show();
	}

	public void toMyPublish() {
		Toast.makeText(context, "前往我发布的信息页面", Toast.LENGTH_SHORT).show();
	}

	public void toPublicInf() {
		Toast.makeText(context, "前往公共信息页面", Toast.LENGTH_SHORT).show();
	}

	public void logout() {
		authenticationBusiness.logout();
	}

	private String getAppVersion() {
		return appBusiness.getVersionNumber();
	}
}
