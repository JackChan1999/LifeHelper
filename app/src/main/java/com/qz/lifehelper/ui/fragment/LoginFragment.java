package com.qz.lifehelper.ui.fragment;

import android.widget.EditText;
import android.widget.Toast;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.AuthenticationBusiness;
import com.qz.lifehelper.business.DialogBusiness;
import com.qz.lifehelper.entity.UserInfoBean;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import bolts.Continuation;
import bolts.Task;

/**
 * 登陆洁面
 */
@EFragment(R.layout.fragment_login)
public class LoginFragment extends BaseFragment {

    static public class Builder {
        LoginFragment fragment = new LoginFragment_.FragmentBuilder_().build();

        public Builder setCallback(Callback callback) {
            fragment.callback = callback;
            return this;
        }

        public LoginFragment create() {

            if (fragment.callback == null) {
                throw new IllegalStateException("没有设置Callbcak");
            }

            return fragment;
        }
    }

    /**
     * 当登陆出现结果时，会回调该接口
     */
    public interface Callback {
        /**
         * 当成功登入后会回调该接口
         */
        public void onLoginSuccess(UserInfoBean userInfoBean);
    }

    private Callback callback;

    @ViewById(R.id.user_name_et)
    EditText userNameEt;

    @ViewById(R.id.password_et)
    EditText passwordEt;

    @Bean
    AuthenticationBusiness authenticationBusiness;

    @Bean
    DialogBusiness dialogBusiness;

    @Click(R.id.login_bn)
    void login() {
        String userName = userNameEt.getText().toString();
        String passowrd = passwordEt.getText().toString();

        dialogBusiness.showDialog(getFragmentManager(), new DialogBusiness.ProgressDialogBuilder().create(), "login");
        authenticationBusiness.login(userName, passowrd).onSuccess(new Continuation<UserInfoBean, Void>() {
            @Override
            public Void then(Task<UserInfoBean> task) throws Exception {
                dialogBusiness.hideDialog("login");
                callback.onLoginSuccess(task.getResult());
                return null;
            }
        }).continueWith(new Continuation<Void, Void>() {
            @Override
            public Void then(Task<Void> task) throws Exception {
                if (task.isFaulted()) {
                    dialogBusiness.hideDialog("login");
                    Toast.makeText(getActivity(), "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }
}
