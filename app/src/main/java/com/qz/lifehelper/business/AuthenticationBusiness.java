package com.qz.lifehelper.business;

import android.content.Context;
import android.widget.Toast;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import de.greenrobot.event.EventBus;

/**
 * 该类主要负责处理用户验证相关的业务逻辑
 */

@EBean(scope = EBean.Scope.Singleton)
public class AuthenticationBusiness {

    @RootContext
    Context context;

    EventBus eventBus = EventBus.builder().build();

    /**
     * 判断是否已经登录
     */
    public boolean isLogin() {
        //TODO 还没有实现
        return false;
    }

    /**
     * 登录
     * 登录成功之后，会发送GetAuthEvent，通知相关组件登录成功
     */
    public void login(String userName,String password) {
        Toast.makeText(context, "前往登录和注册页面", Toast.LENGTH_SHORT).show();
    }

    /**
     * 登出
     * 登出成功之后，会发送GetAuthEvent，通知相关组件已经成功登出
     */
    public void logout() {
        Toast.makeText(context, "退出登入", Toast.LENGTH_SHORT).show();
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
