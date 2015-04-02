package com.qz.lifehelper.helper;

import android.content.Context;
import android.widget.Toast;

import com.qz.lifehelper.business.AuthenticationBusiness;
import com.qz.lifehelper.entity.UserInfoBean;
import com.qz.lifehelper.event.GetAuthEvent;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import bolts.Task;

/**
 * PersonalFragment的助手
 * <p/>
 * 帮助PersonalFragment实现一部分业务逻辑
 */

@EBean
public class PersonalHelper {

    @RootContext
    Context context;

    @Bean
    AuthenticationBusiness authenticationBusiness;

    private Task<UserInfoBean>.TaskCompletionSource loginTaskSource;

    /**
     * 前往登录和注册页面
     */
    public Task<UserInfoBean> login() {
        loginTaskSource = Task.create();
        Toast.makeText(context, "前往登录和注册页面", Toast.LENGTH_SHORT).show();
        return loginTaskSource.getTask();
    }

    @AfterInject
    public void registerEventBus() {
        authenticationBusiness.getEventBus().register(this);
    }

    /**
     * 当接收到登录成功当event厚，设置登录task当结果
     */
    public void onEvent(GetAuthEvent event) {
        if (loginTaskSource != null && event.authState == GetAuthEvent.AUTH_STATE.LOGIN) {
            loginTaskSource.setResult(event.userInfoBean);
        }
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
