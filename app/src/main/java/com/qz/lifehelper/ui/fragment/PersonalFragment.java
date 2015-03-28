package com.qz.lifehelper.ui.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.AppBusiness;
import com.qz.lifehelper.business.AuthenticationBusiness;
import com.qz.lifehelper.event.GetAuthEvent;
import com.qz.lifehelper.presentation.PersonalHelper;

/**
 * Created by kohoh on 15/3/23.
 */

@EFragment(R.layout.fragment_personal)
public class PersonalFragment extends Fragment {

	@Bean
	PersonalHelper personalHelper;

	@Bean
	AuthenticationBusiness authenticationBusiness;

	@Bean
	AppBusiness appBusiness;

	@Click(R.id.login_bn)
	void onLoginBnClick() {
		// 前往登录注册页面
		authenticationBusiness.login();
	}

	@Click(R.id.my_sales)
	void onMySalesClick() {
		personalHelper.toMySale();
	}

	@Click(R.id.my_publish)
	void onMyPublishClick() {
		personalHelper.toMyPublish();
	}

	@Click(R.id.public_info)
	void onPublicInfoClick() {
		personalHelper.toPublicInf();
	}

	@Click(R.id.logout_bn)
	void onLogoutBnClick() {
		authenticationBusiness.logout();
	}

	/**
	 * 设置当前的用户状态
	 */
	@AfterViews
	void setAuthState() {
		if (authenticationBusiness.isLogin()) {
			// TODO 设置状态为登录
		} else {
			// TODO 设置状态为未登录
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		registerEventBus();
	}

	@Override
	public void onStop() {
		super.onStop();
		unregisterEventBus();
	}

	private void registerEventBus() {
		authenticationBusiness.getEventBus().register(this);
	}

	private void unregisterEventBus() {
		authenticationBusiness.getEventBus().unregister(this);
	}

	/**
	 * 收到登录或者登出消息后，刷新页面的用户状态
	 */
	public void onEventMainThread(GetAuthEvent event) {
		switch (event.authState) {
		case LOGIN:
			// TODO 设置状态为登录
			break;
		case LOGOUT:
			// TODO 设置状态为未登录
			break;
		}
	}

	@ViewById(R.id.login_bn)
	View loginBn;

	@ViewById(R.id.user_icon_iv)
	ImageView userIconIv;

	@ViewById(R.id.user_icon_background_iv)
	ImageView userIconBagIv;

	@ViewById(R.id.user_name)
	TextView userNameTv;

	/**
	 * 修改为登录状态
	 */
	void login(String userName, Bitmap userIcon, Bitmap userIconBag) {
		userNameTv.setVisibility(View.VISIBLE);
		loginBn.setVisibility(View.GONE);
		userIconBagIv.setImageBitmap(userIconBag);
		userIconIv.setImageBitmap(userIcon);
		userNameTv.setText(userName);
	}

	/**
	 * 修改为登出状态
	 */
	void logout(Bitmap userIcon, Bitmap userIconBag) {
		loginBn.setVisibility(View.VISIBLE);
		userNameTv.setVisibility(View.GONE);
		userIconBagIv.setImageBitmap(userIconBag);
		userIconIv.setImageBitmap(userIcon);
		userNameTv.setText(null);
	}

	@ViewById(R.id.app_version_tv)
	TextView appVersionTv;

	/**
	 * 设置当前版本号
	 */
	@AfterViews
	void setAppVersion() {
		appVersionTv.setText(appBusiness.getVersionNumber());
	}
}
