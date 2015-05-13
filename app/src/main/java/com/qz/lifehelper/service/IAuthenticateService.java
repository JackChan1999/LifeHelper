package com.qz.lifehelper.service;

import com.qz.lifehelper.entity.UserInfoBean;

import bolts.Task;

/**
 * 用户验证服务器接口
 */
public interface IAuthenticateService {

    /**
     * 登录
     */
    public Task<UserInfoBean> login(final String userName, final String password);

    /**
     * 注册
     */
    public Task<UserInfoBean> signin(final String userName, final String password);

}
