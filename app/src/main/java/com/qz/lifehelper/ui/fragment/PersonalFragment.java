package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.AppBusiness;
import com.qz.lifehelper.business.AuthenticationBusiness;
import com.qz.lifehelper.business.NoticeInfoBusiness;
import com.qz.lifehelper.business.P2PBusiness;
import com.qz.lifehelper.business.POIBusiness;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.UserInfoBean;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import bolts.Continuation;
import bolts.Task;

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

    @Click(R.id.my_sales)
    void onMySalesClick() {
        p2pBusiness.toMySale();
    }

    @Click(R.id.my_publish)
    void onMyPublishClick() {
        poiBusiness.toMyPublish();
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
}
