package com.qz.lifehelper.business;

import android.content.Context;
import android.content.Intent;

import com.avos.avoscloud.AVUser;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.UserInfoBean;
import com.qz.lifehelper.event.LoginSuccessEvent;
import com.qz.lifehelper.event.SigninSuccessEvent;
import com.qz.lifehelper.persist.UserPersist;
import com.qz.lifehelper.service.AuthenticateOnlineService_;
import com.qz.lifehelper.service.AuthenticateOutlineService_;
import com.qz.lifehelper.service.IAuthenticateService;
import com.qz.lifehelper.service.LeancloudConstant;
import com.qz.lifehelper.ui.AppProfile;
import com.qz.lifehelper.ui.activity.AuthenticateActivity;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import bolts.Continuation;
import bolts.Task;
import de.greenrobot.event.EventBus;

/**
 * 该类主要负责处理用户验证相关的业务逻辑
 */

@EBean
public class AuthenticationBusiness {

    @Bean
    UserPersist userPersist;

    @RootContext
    Context context;


    IAuthenticateService authenticateService;

    @AfterInject
    void setService() {
        if (AppProfile.dateSource.equals(AppProfile.DATE_SOURCE.ONLINE)) {
            authenticateService = AuthenticateOnlineService_.getInstance_(context);
        } else {
            authenticateService = AuthenticateOutlineService_.getInstance_(context);
        }
    }

    private Task<UserInfoBean>.TaskCompletionSource authenticateTaskCS;

    /**
     * 前往AuthenticateActivity，进行身份验证
     */
    public Task<UserInfoBean> toAuthenticateActivity() {
        authenticateTaskCS = Task.create();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        Intent intent = AuthenticateActivity.generateIntent(context);
        context.startActivity(intent);
        return authenticateTaskCS.getTask();
    }

    public void onEvent(LoginSuccessEvent event) {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (authenticateTaskCS != null) {
            authenticateTaskCS.trySetResult(event.userInfoBean);
        }
    }

    public void onEvent(SigninSuccessEvent event) {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (authenticateTaskCS != null) {
            authenticateTaskCS.trySetResult(event.userInfoBean);
        }
    }

    /**
     * 判断是否已经登录
     */
    public boolean isLogin() {
        if (userPersist.getUserName() == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 登录
     */
    public Task<UserInfoBean> login(final String userName, final String password) {
        return authenticateService.login(userName, password).onSuccess(new Continuation<UserInfoBean, UserInfoBean>() {
            @Override
            public UserInfoBean then(Task<UserInfoBean> task) throws Exception {
                UserInfoBean userInfoBean = task.getResult();
                saveUseInfo(userInfoBean);
                return userInfoBean;
            }
        });
    }

    /**
     * 注册
     */
    public Task<UserInfoBean> signin(final String userName, String password) {
        return authenticateService.signin(userName, password).onSuccess(new Continuation<UserInfoBean, UserInfoBean>() {
            @Override
            public UserInfoBean then(Task<UserInfoBean> task) throws Exception {
                UserInfoBean userInfoBean = task.getResult();
                saveUseInfo(userInfoBean);
                return userInfoBean;
            }
        });
    }

    /**
     * 获取当前用户的信息
     * <p/>
     * 只有当isLogin返回true时，才会返回有效数据
     */
    private UserInfoBean getCurrentUser() {
        if (!isLogin()) {
            throw new IllegalStateException("还没有登陆");
        }

        String id = AVUser.getCurrentUser().getObjectId();

        return UserInfoBean.generateBean(
                userPersist.getUserName()
                , ImageBean.generateImage(
                        userPersist.getUserIcon()
                        , ImageBean.ImageType.OUTLINE)
                , id);
    }

    /**
     * 获取当前登录的用户信息
     *
     * @param tryToLogin true，如果当前没有登录则引导用户登录
     */
    public Task<UserInfoBean> getCurrentUser(boolean tryToLogin) {
        if (isLogin()) {
            return Task.forResult(getCurrentUser());
        } else if (tryToLogin) {
            return toAuthenticateActivity();
        } else {
            return Task.forError(new Exception("当前没有登录"));
        }
    }


    /**
     * 设置当前登录用户当信息
     */
    private void saveUseInfo(UserInfoBean userInfo) {
        userPersist.setUserName(userInfo.userName);
        userPersist.setUserIcon(userInfo.userIcon.imageSrc);
    }

    /**
     * 登出 登出成功之后，会发送GetAuthEvent，通知相关组件已经成功登出
     */
    public void logout() {
        userPersist.setUserName(null);
        userPersist.setUserIcon(null);
    }

    /**
     * 获取默认当头像
     */
    public ImageBean getDefaultUserIcon() {
        return ImageBean.generateImage("file:///android_asset/user_icon_1.png", ImageBean.ImageType.OUTLINE);
    }

    /**
     * 判断是不是超级用户
     * <p/>
     * 超级用户拥有无敌权限
     */
    public boolean isSuperUser(UserInfoBean userInfoBean) {
        if (!isLogin()) {
            return false;
        }
        UserInfoBean cuerrentUser = getCurrentUser();

        if (LeancloudConstant.SUPER_USER_ID.equals(cuerrentUser.id)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取超级用户
     */
    static public UserInfoBean getSuperUser() {
        return UserInfoBean.generateBean("root", null, LeancloudConstant.SUPER_USER_ID);
    }

    static public UserInfoBean getBaiduUser() {
        return UserInfoBean.generateBean("badidu_isuhfiudshfidshfiuhdsifhdsi", null, "webfefuehwfhewifhewiufhewifh");
    }
}
