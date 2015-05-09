package com.qz.lifehelper.service;

import com.avos.avoscloud.AVUser;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.UserInfoBean;

import org.androidannotations.annotations.EBean;

import java.util.concurrent.Callable;

import bolts.Task;

/**
 * 用于进行身份验证的Service
 * <p/>
 * 该服务端使用来Leancloud
 */
@EBean
public class AuthenticateOnlineService implements IAuthenticateService {

    /**
     * 登录
     */
    public Task<UserInfoBean> login(final String userName, final String password) {
        return Task.callInBackground(new Callable<UserInfoBean>() {
            @Override
            public UserInfoBean call() throws Exception {
                AVUser avUser = AVUser.logIn(userName, password);
                String id = avUser.getObjectId();
                UserInfoBean userInfoBean = UserInfoBean.generateBean(
                        avUser.getUsername()
                        , getDefaultUserIcon()
                        , id);
                return userInfoBean;
            }
        });
    }


    /**
     * 注册
     */
    public Task<UserInfoBean> signin(final String userName, final String password) {
        return Task.callInBackground(new Callable<UserInfoBean>() {
            @Override
            public UserInfoBean call() throws Exception {
                AVUser avUser = new AVUser();
                avUser.setUsername(userName);
                avUser.setPassword(password);
                avUser.signUp();

                String id = avUser.getObjectId();

                UserInfoBean userInfoBean = UserInfoBean.generateBean(
                        userName
                        , getDefaultUserIcon()
                        , id);
                return userInfoBean;
            }
        });
    }

    /**
     * 获取默认当头像
     */
    private ImageBean getDefaultUserIcon() {
        return ImageBean.generateImage("file:///android_asset/user_icon_1.png", ImageBean.ImageType.OUTLINE);
    }
}
