package com.qz.lifehelper.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.qz.lifehelper.R;
import com.qz.lifehelper.service.AVOSService;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

/**
 * 用户登陆注册页
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {
    private Context mContext;
    //返回按钮
    @ViewById(R.id.iv_back)
    ImageView mIvBack;

    //用户账号
    @ViewById(R.id.et_account)
    EditText mEditTextUaerAccount;

    //用户密码
    @ViewById(R.id.et_password)
    EditText mEditTextUaerPassword;

    //登陆
    @ViewById(R.id.btn_login)
    Button mBtnLogin;

    //注册
    @ViewById(R.id.tv_register)
    TextView mTvRegister;

    //忘记密码
    @ViewById(R.id.tv_forget_password)
    TextView mTvForgetPassword;

    private ProgressDialog mLoginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = LoginActivity.this;
    }

    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
    }

    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);
    }

    @Click(R.id.tv_forget_password)
    void findPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getResources().getString(R.string.find_password));
        builder.setPositiveButton(mContext.getResources().getString(R.string.find_password), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = null;
                if (email != null) {
                    RequestPasswordResetCallback callback = new RequestPasswordResetCallback() {
                        public void done(AVException e) {
                            if (e == null) {
                                Toast.makeText(mContext,
                                        R.string.email_sended,
                                        Toast.LENGTH_LONG).show();
                                Intent LoginIntent = new Intent(mContext,
                                        LoginActivity.class);
                                startActivity(LoginIntent);
                                finish();
                            } else {
                                Toast.makeText(mContext, mContext
                                        .getString(R.string.email_send_error), Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    AVOSService.requestPasswordReset(email, callback);
                } else {
                    Toast.makeText(mContext, mContext
                            .getString(R.string.email_address_null), Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.show();
    }

    //注册用户
    @Click(R.id.tv_register)
    void register() {
        Intent intent = new Intent(mContext, RegisterActivity_.class);
        mContext.startActivity(intent);
    }

    @Click(R.id.iv_back)
    void back() {
        finish();
    }

    //用户登陆
    @Click(R.id.btn_login)
    void login() {
        {
            String userAccount = mEditTextUaerAccount.getText().toString();
            if (userAccount.isEmpty()) {
                Toast.makeText(mContext, mContext.getResources().getString(R.string.account_input), Toast.LENGTH_SHORT).show();
                return;
            }
            String userPassword = mEditTextUaerPassword.getText().toString();
            if (userPassword.isEmpty()) {
                Toast.makeText(mContext, mContext.getResources().getString(R.string.password_input), Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialogShow();
            AVUser.logInInBackground(userAccount,
                    userPassword,
                    new LogInCallback() {
                        public void done(AVUser user, AVException e) {
                            if (user != null) {
                                progressDialogDismiss();
                                Intent mainIntent = new Intent(mContext,
                                        HomeActivity_.class);
                                startActivity(mainIntent);
                                finish();
                            } else {
                                progressDialogDismiss();
                                showLoginError();
                            }
                        }
                    });
        }
    }

    /**
     * 隐藏登录中提示框
     */
    private void progressDialogDismiss() {
        if (mLoginDialog != null)
            mLoginDialog.dismiss();
    }

    /**
     * 显示登录中提示框
     */
    private void progressDialogShow() {
        mLoginDialog = ProgressDialog
                .show(mContext,
                        mContext.getResources().getText(
                                R.string.tip),
                        mContext.getResources().getText(
                                R.string.logining), true, false);
    }

    /**
     * 登陆失败错误提示
     */
    private void showLoginError() {
        new AlertDialog.Builder(mContext)
                .setTitle(
                        mContext.getResources().getString(
                                R.string.tip))
                .setMessage(
                        mContext.getResources().getString(
                                R.string.login_error))
                .setNegativeButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }
}
