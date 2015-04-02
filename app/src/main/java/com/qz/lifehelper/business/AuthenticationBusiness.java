package com.qz.lifehelper.business;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.qz.lifehelper.entity.UserInfoBean;
import com.qz.lifehelper.persist.UserPersist;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import bolts.Task;
import de.greenrobot.event.EventBus;

/**
 * 该类主要负责处理用户验证相关的业务逻辑
 */

@EBean(scope = EBean.Scope.Singleton)
public class AuthenticationBusiness {

    @RootContext
    Context context;

    @Bean
    UserPersist userPersist;

    EventBus eventBus = EventBus.builder().build();

    /**
     * 判断是否已经登录
     */
    public boolean isLogin() {
        // TODO 还没有实现
        return false;
    }

    /**
     * 登录 登录成功之后，会发送GetAuthEvent，通知相关组件登录成功
     */
    public Task<UserInfoBean> login(String userName, String password) {
        Task<UserInfoBean>.TaskCompletionSource taskCompletionSource = Task.create();

        // TODO 登录业务逻辑

        return taskCompletionSource.getTask();
    }

    /**
     * 注册 注册成功之后，会发送GetAuthEvent，通知相关组建登录成功。注意，注册成功也就相当于登录成功了
     */
    public Task<UserInfoBean> signin(String userName, String password) {
        Task<UserInfoBean>.TaskCompletionSource taskCompletionSource = Task.create();

        // TODO 注册业务逻辑

        return taskCompletionSource.getTask();
    }

    /**
     * 获取当前用户的信息
     * <p/>
     * 只有当isLogin返回true时，才会返回有效数据
     */
    public UserInfoBean getUserInfo() throws Exception {
        if (!isLogin()) {
            throw new Exception("还没有登录");
        }
        return UserInfoBean.generateBean(userPersist.getUserName(), userPersist.getUserIcon());
    }

    /**
     * 获取用户当默认头像
     * <p/>
     * 当没有登录当时候，或者登录后用户没有设置头像，则使用默认头像
     */
    public Bitmap getDefaultUserIcon() {
        // TODO 没有实现
        return null;
    }

    /**
     * 设置当前登录用户当信息
     */
    private void setUserInfo(UserInfoBean userInfo) {
        userPersist.setUserName(userInfo.userName);
        userPersist.setUserIcon(userInfo.userIcon);
    }

    /**
     * 登出 登出成功之后，会发送GetAuthEvent，通知相关组件已经成功登出
     */
    public void logout() {
        Toast.makeText(context, "退出登入", Toast.LENGTH_SHORT).show();
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
