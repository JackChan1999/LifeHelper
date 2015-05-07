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
 * 用户注册界面
 */

@EFragment(R.layout.fragment_signin)
public class SigninFragment extends BaseFragment {


    static public class Builder {
        SigninFragment fragment = new SigninFragment_.FragmentBuilder_().build();

        public Builder setCallback(Callback callback) {
            fragment.callback = callback;
            return this;
        }

        public SigninFragment create() {

            if (fragment.callback == null) {
                throw new IllegalStateException("没有设置Callbcak");
            }

            return fragment;
        }
    }

    /**
     * 当注册出现结果时，会回调该接口
     */
    public interface Callback {
        /**
         * 当成功注册后会回调该接口
         */
        public void onSiginSuccess(UserInfoBean userInfoBean);
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

    @Click(R.id.signin_bn)
    void sigin() {
        String userName = userNameEt.getText().toString();
        String passowrd = passwordEt.getText().toString();

        dialogBusiness.showDialog(getFragmentManager(), new DialogBusiness.ProgressDialogBuilder().create(), "sigin");
        authenticationBusiness.signin(userName, passowrd).onSuccess(new Continuation<UserInfoBean, Void>() {
            @Override
            public Void then(Task<UserInfoBean> task) throws Exception {
                dialogBusiness.hideDialog("sigin");
                callback.onSiginSuccess(task.getResult());
                return null;
            }
        }).continueWith(new Continuation<Void, Object>() {
            @Override
            public Object then(Task<Void> task) throws Exception {
                if (task.isFaulted()) {
                    dialogBusiness.hideDialog("sigin");
                    Toast.makeText(getActivity(), "该用户已经存在", Toast.LENGTH_LONG).show();
                }
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

}
