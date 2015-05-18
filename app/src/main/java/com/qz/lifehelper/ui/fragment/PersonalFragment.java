package com.qz.lifehelper.ui.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.AppBusiness;
import com.qz.lifehelper.business.AuthenticationBusiness;
import com.qz.lifehelper.business.NoticeInfoBusiness;
import com.qz.lifehelper.business.P2PBusiness;
import com.qz.lifehelper.business.POIBusiness;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.P2PRequestBean;
import com.qz.lifehelper.entity.UserInfoBean;
import com.qz.lifehelper.event.LoginSuccessEvent;
import com.qz.lifehelper.event.LogoutEvent;
import com.qz.lifehelper.event.SigninSuccessEvent;
import com.qz.lifehelper.ui.activity.P2PActivity;
import com.qz.lifehelper.ui.activity.POIActivity;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

import bolts.Continuation;
import bolts.Task;
import de.greenrobot.event.EventBus;

/**
 * 个人信息页
 */

@EFragment(R.layout.fragment_personal)
public class PersonalFragment extends Fragment {


    @Bean
    AuthenticationBusiness authenticationBusiness;

    @Bean
    AppBusiness appBusiness;

    @Bean
    P2PBusiness p2pBusiness;

    @Bean
    POIBusiness poiBusiness;

    @Bean
    NoticeInfoBusiness noticeInfoBusiness;

    @Click(R.id.user_icon_iv)
    void onLoginBnClick() {
        if (!authenticationBusiness.isLogin()) {
            authenticationBusiness.toAuthenticateActivity().onSuccess(new Continuation<UserInfoBean, Void>() {
                @Override
                public Void then(Task<UserInfoBean> task) throws Exception {
                    UserInfoBean userInfoBean = task.getResult();
                    login(userInfoBean.userName, userInfoBean.userIcon);
                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(LoginSuccessEvent event) {
        login(event.userInfoBean.userName, event.userInfoBean.userIcon);
    }

    public void onEvent(SigninSuccessEvent event) {
        login(event.userInfoBean.userName, event.userInfoBean.userIcon);
    }

    public void onEvent(LogoutEvent event) {
        logout();
    }

    @Click(R.id.my_sales)
    void onMySalesClick() {
        Intent intent = P2PActivity.generateIntent(
                this.getActivity()
                , new P2PRequestBean().setFragmentType(P2PRequestBean.FragmentType.PERSONAL_P2P_LIST));
        this.getActivity().startActivity(intent);
    }

    @Click(R.id.my_publish)
    void onMyPublishClick() {
        Intent intent = POIActivity.generatePersonalPOIIntent(this.getActivity());
        this.getActivity().startActivity(intent);
    }

    @Click(R.id.public_info)
    void onPublicInfoClick() {
        noticeInfoBusiness.toNotcieInfoFragment(getFragmentManager());
    }

    @Click(R.id.logout_bn)
    void onLogoutBnClick() {
        authenticationBusiness.logout();
        logout();
    }

    /**
     * 设置当前的用户状态
     */
    @AfterViews
    void setAuthState() {
        if (authenticationBusiness.isLogin()) {
            authenticationBusiness.getCurrentUser(false).onSuccess(new Continuation<UserInfoBean, Void>() {
                @Override
                public Void then(Task<UserInfoBean> task) throws Exception {
                    UserInfoBean userInfoBean = task.getResult();
                    login(userInfoBean.userName, userInfoBean.userIcon);
                    return null;
                }
            });
        } else {
            logout();
        }
    }

    @ViewById(R.id.user_icon_iv)
    ImageView userIconIv;

    @ViewById(R.id.user_name)
    TextView userNameTv;

    /**
     * 修改为登录状态
     */
    void login(String userName, ImageBean imageBean) {
        Picasso.with(this.getActivity())
                .load(imageBean.imageSrc)
                .into(userIconIv);
        userNameTv.setText(userName);
    }

    /**
     * 修改为登出状态
     */
    void logout() {
        Picasso.with(this.getActivity())
                .load(authenticationBusiness.getDefaultUserIcon().imageSrc)
                .into(userIconIv);
        userNameTv.setText("点击头像登入");
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

    private int appVersionTvCount = 0;
    private Date appVersionTvLastClickTime;

    @Click(R.id.app_version_tv)
    void setAppDateSourceType() {
        Date currentTime = new Date();
        if (appVersionTvLastClickTime != null && currentTime.getTime() - appVersionTvLastClickTime.getTime() > 1000) {
            appVersionTvCount = 0;
            appVersionTvLastClickTime = null;
            return;
        }

        if (appVersionTvCount == 4) {
            if (appBusiness.getDateSourceType().equals(AppBusiness.DATE_SOURCE.OUTLINE)) {
                appBusiness.setDateSourceType(AppBusiness.DATE_SOURCE.ONLINE);
                authenticationBusiness.logout();
                Toast.makeText(this.getActivity(), "转化为在线数据", Toast.LENGTH_LONG).show();
            } else {
                appBusiness.setDateSourceType(AppBusiness.DATE_SOURCE.OUTLINE);
                authenticationBusiness.logout();
                Toast.makeText(this.getActivity(), "转化为离线数据", Toast.LENGTH_LONG).show();
            }
            appVersionTvCount = 0;
        } else {
            appVersionTvLastClickTime = new Date();
            appVersionTvCount++;
        }
    }
}
