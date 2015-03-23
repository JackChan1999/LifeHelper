package com.qz.lifehelper.business;

import android.content.Context;
import android.widget.Toast;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by kohoh on 15/3/23.
 */

@EBean
public class AuthenticationBusiness {

    @RootContext
    Context context;

    public boolean isLogin() {
        return false;
    }

    public void login() {
        Toast.makeText(context, "前往登录和注册页面", Toast.LENGTH_SHORT).show();
    }

    public void logout() {
        Toast.makeText(context, "退出登入", Toast.LENGTH_SHORT).show();
    }

}
