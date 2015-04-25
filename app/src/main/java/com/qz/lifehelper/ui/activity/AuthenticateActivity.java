package com.qz.lifehelper.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.qz.lifehelper.entity.UserInfoBean;
import com.qz.lifehelper.event.LoginSuccessEvent;
import com.qz.lifehelper.event.SigninSuccessEvent;
import com.qz.lifehelper.ui.fragment.AuthenticateFragment;

import org.androidannotations.annotations.EActivity;

import de.greenrobot.event.EventBus;

/**
 * 用户验证Activity
 * <p/>
 * 它只是一个wrapper，真正当业务逻辑由LiginFragmnet，SinginFragment，AuthenticateFragment实现
 */
@EActivity
public class AuthenticateActivity extends BaseActivity {

    static final public Intent generateIntent(Context context) {
        return new Intent(context, AuthenticateActivity_.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthenticateFragment authenticateFragment = new AuthenticateFragment.Builder()
                .setCallback(new AuthenticateFragment.Callback() {
                    @Override
                    public void onLoginSuccess(UserInfoBean userInfoBean) {
                        EventBus.getDefault().post(LoginSuccessEvent.generateEvent(userInfoBean));
                        finish();
                    }

                    @Override
                    public void onSigninSuccess(UserInfoBean userInfoBean) {
                        EventBus.getDefault().post(SigninSuccessEvent.generateEvent(userInfoBean));
                        finish();
                    }
                })
                .create();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(android.R.id.content, authenticateFragment);
        transaction.commit();
    }
}
