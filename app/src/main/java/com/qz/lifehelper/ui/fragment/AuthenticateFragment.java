package com.qz.lifehelper.ui.fragment;

import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.qz.lifehelper.R;
import com.qz.lifehelper.entity.FragmentBean;
import com.qz.lifehelper.entity.UserInfoBean;
import com.qz.lifehelper.ui.adapter.PagerAndTabAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 身份验证页面
 * <p/>
 * 它只是一个wrapper，真正的逻辑由LoginFragment和LogoutFragment实现
 */
@EFragment(R.layout.fragment_authenticate)
public class AuthenticateFragment extends BaseFragment {

    static public class Builder {
        AuthenticateFragment fragment = new AuthenticateFragment_.FragmentBuilder_().build();

        public Builder setCallback(Callback callback) {
            fragment.callback = callback;
            return this;
        }

        public AuthenticateFragment create() {

            if (fragment.callback == null) {
                throw new IllegalStateException("没有设置 Callback");
            }

            return fragment;
        }
    }

    /**
     * 当用户身份验证出现结果时，回调该接口
     */
    public interface Callback {

        /**
         * 当登入成功后回调该接口
         */
        public void onLoginSuccess(UserInfoBean userInfoBean);

        /**
         * 当注册成功时回调该接口
         */
        public void onSigninSuccess(UserInfoBean userInfoBean);
    }

    private Callback callback;

    @ViewById(R.id.tab)
    PagerSlidingTabStrip tab;

    @ViewById(R.id.pager)
    ViewPager pager;

    @AfterViews
    void setToolbar() {

    }

    @AfterViews
    void setTabAndPager() {
        PagerAndTabAdapter adapter = new PagerAndTabAdapter(getFragmentManager());
        pager.setAdapter(adapter);
        adapter.setData(getFragments());
        adapter.notifyDataSetChanged();
        tab.setShouldExpand(true);
        tab.setViewPager(pager);
        tab.setTextColorResource(R.color.app_text_color_light);
        tab.setIndicatorColorResource(R.color.tab_indicator);
        tab.setDividerColorResource(R.color.tab_divider);
    }

    private List<FragmentBean> getFragments() {
        List<FragmentBean> fragmentBeans = new ArrayList<>();
        LoginFragment loginFragment = new LoginFragment.Builder()
                .setCallback(new LoginFragment.Callback() {
                    @Override
                    public void onLoginSuccess(UserInfoBean userInfoBean) {
                        callback.onLoginSuccess(userInfoBean);
                    }
                })
                .create();

        SigninFragment signinFragment = new SigninFragment.Builder()
                .setCallback(new SigninFragment.Callback() {
                    @Override
                    public void onSiginSuccess(UserInfoBean userInfoBean) {
                        callback.onSigninSuccess(userInfoBean);
                    }
                })
                .create();

        fragmentBeans.add(FragmentBean.generateHomeFragment(loginFragment, "登入"));
        fragmentBeans.add(FragmentBean.generateHomeFragment(signinFragment, "注册"));

        return fragmentBeans;
    }
}

