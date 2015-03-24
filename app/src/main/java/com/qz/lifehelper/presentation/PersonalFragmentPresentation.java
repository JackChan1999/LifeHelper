package com.qz.lifehelper.presentation;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.AppBusiness;
import com.qz.lifehelper.business.AuthenticationBusiness;
import com.qz.lifehelper.event.GetAuthEvent;

import static com.qz.lifehelper.event.GetAuthEvent.AUTH_STATE.*;

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

//    @Bean
//    ImageUtils imageUtils;


	@RootContext
	Context context;

    //这里的登录分为辆步
    //1. 已经登录
    //2. 登录后跳转

	public void bind(IPersonalView personalView) {
        registerEventBus();
		this.personalView = personalView;

		if (authenticationBusiness.isLogin()) {
            onLogin();
		} else {
            onLogout();
		}

		personalView.setAppVersion(getAppVersion());
	}

	public void unbind() {
        unregisterEventBus();
	}

    public void onEventMainThread(GetAuthEvent event){
        switch (event.authState){
            case LOGIN:
                onLogin();
                break;
            case LOGOUT:
                onLogout();
                break;
        }
    }

    private void registerEventBus() {
        authenticationBusiness.getEventBus().register(this);
    }

    private void unregisterEventBus() {
        authenticationBusiness.getEventBus().unregister(this);
    }

    private void onLogin() {
//        User user=authenticationBusiness.getUser();
//        Bitmap defaultUserIcon = imageUtils.load(R.drawable.default_user_icon);
//        Bitmap defaultUserIconBg = imageUtils.loadBg(R.drawable.default_user_icon);
//        personalView.login(user.name,defaultUserIcon,defaultUserIconBg);
//
//        Bitmap userIcon;
//        Bitmap userIconBg;
//        personalView.setUserName(user.name);
//        imageUtils.load(user.icon,new Callback{
//            void onSuccess(Bitmap icon){
//                userIcon=icon;
//                imageUtils.loadBg(user.icon,newCallback{
//                    void onSuccss(Bitmap iconBg){
//                        userIconBg=iconBg;
//                        personalView.login(user.name,userIcon,userIconBg);
//                    }
//                })
//            }
//        });
    }

    private void onLogout(){
//        Bitmap defaultUserIcon = imageUtils.load(R.drawable.default_user_icon);
//        Bitmap defaultUserIconBg = imageUtils.loadBg(R.drawable.default_user_icon);
//        personalView.logout(defaultUserIcon,defaultUserIconBg);
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
