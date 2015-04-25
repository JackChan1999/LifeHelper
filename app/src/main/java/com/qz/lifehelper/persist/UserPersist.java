package com.qz.lifehelper.persist;

import android.content.Context;
import android.content.SharedPreferences;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * 存储和用户信息相相关当内容
 */
@EBean
public class UserPersist {

    @RootContext
    Context context;

    static private final String USER_SHARED_PREFERSNCES = "USER_SHARED_PREFERSNCES";
    static private final String USER_NAME = "USER_NAME";
    static private final String USER_ICON = "USER_ICON";

    private SharedPreferences getUserSharedPreferences() {
        return context.getSharedPreferences(USER_SHARED_PREFERSNCES, Context.MODE_PRIVATE);
    }

    public String getUserName() {
        return getUserSharedPreferences().getString(USER_NAME, null);
    }

    public String getUserIcon() {
        return getUserSharedPreferences().getString(USER_ICON, null);
    }

    public void setUserName(String userName) {
        getUserSharedPreferences().edit().putString(USER_NAME, userName).commit();
    }

    public void setUserIcon(String userIcon) {
        getUserSharedPreferences().edit().putString(USER_ICON, userIcon).commit();
    }

}
