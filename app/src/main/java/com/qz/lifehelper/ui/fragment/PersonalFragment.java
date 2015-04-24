package com.qz.lifehelper.ui.fragment;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.AppBusiness;
import com.qz.lifehelper.business.AuthenticationBusiness;
import com.qz.lifehelper.entity.UserInfoBean;
import com.qz.lifehelper.helper.PersonalHelper;

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
    PersonalHelper personalHelper;

    @Bean
    AuthenticationBusiness authenticationBusiness;

    @Bean
    AppBusiness appBusiness;

    @Click(R.id.user_icon_iv)
    void onLoginBnClick() {
        personalHelper.login().onSuccess(new Continuation<UserInfoBean, Void>() {
            @Override
            public Void then(Task<UserInfoBean> task) throws Exception {
                // TODO 设置登录成功当状态
                return null;
            }
        });
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
        personalHelper.toPublicInf(getFragmentManager());
    }

    @Click(R.id.logout_bn)
    void onLogoutBnClick() {
        authenticationBusiness.logout();
        // TODO 设置登出当状态
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

    @ViewById(R.id.user_icon_iv)
    ImageView userIconIv;

    @ViewById(R.id.user_name)
    TextView userNameTv;

    /**
     * 修改为登录状态
     */
    void login(String userName, Bitmap userIcon, Bitmap userIconBag) {
        userIconIv.setImageBitmap(userIcon);
        userNameTv.setText(userName);
    }

    /**
     * 修改为登出状态
     */
    void logout(Bitmap userIcon, Bitmap userIconBag) {
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
