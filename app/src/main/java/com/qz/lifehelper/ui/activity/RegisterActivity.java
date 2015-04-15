package com.qz.lifehelper.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SignUpCallback;
import com.qz.lifehelper.R;
import com.qz.lifehelper.service.AVOSService;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 用户注册页面
 */
@EActivity(R.layout.activity_register)
public class RegisterActivity extends Activity {
    private Context mContext;
    private ProgressDialog mRegisterDialog;


    //用户账号
    @ViewById(R.id.et_account)
    EditText mUaerAccount;

    //用户密码
    @ViewById(R.id.et_password)
    EditText mUaerPassword;

    //用户邮箱
    @ViewById(R.id.et_email)
    EditText mUaerEmail;

    //确认密码
    @ViewById(R.id.et_password_confirm)
    EditText mUaerPasswordConfirm;

    //注册按钮
    @ViewById(R.id.btn_register)
    Button mBtnRegister;

    @ViewById(R.id.iv_back)
    ImageView mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext=RegisterActivity.this;
    }

    @Click(R.id.iv_back)
    void back()
    {
        finish();
    }

    @Click(R.id.btn_register)
    void register()
    {
        if (mUaerPassword.getText().toString()
                .equals(mUaerPasswordConfirm.getText().toString())) {
            if (!mUaerAccount.getText().toString().isEmpty()) {
                if (!mUaerPassword.getText().toString().isEmpty()) {
                    if (!mUaerEmail.getText().toString().isEmpty()) {
                        progressDialogShow();
                        userRegister();
                    } else {
                        Toast.makeText(mContext,mContext
                                .getString(R.string.email_address_null),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext,mContext
                            .getString(R.string.password_null),Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext,mContext
                        .getString(R.string.user_name_null),Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext,mContext
                    .getString(R.string.password_not_equals),Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 注册
     */
    public void userRegister() {
        SignUpCallback signUpCallback = new SignUpCallback() {
            public void done(AVException e) {
                progressDialogDismiss();
                if (e == null) {
                    Toast.makeText(mContext,mContext
                            .getString(R.string.register_success),Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(mContext, LoginActivity_.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    switch (e.getCode()) {
                        case 202:
                            Toast.makeText(mContext,mContext
                                    .getString(R.string.user_name_repeat),Toast.LENGTH_SHORT).show();
                            break;
                        case 203:
                            Toast.makeText(mContext,mContext
                                    .getString(R.string.email_repeat),Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(mContext,mContext
                                    .getString(R.string.network_error),Toast.LENGTH_SHORT).show();
                            Log.e("errorInfo:",e.getMessage().toString());
                            break;
                    }
                }
            }
        };
        String username = mUaerAccount.getText().toString();
        String password = mUaerPassword.getText().toString();
        String email = mUaerEmail.getText().toString();

        AVOSService.signUp(username, password, email, signUpCallback);
    }
    /**
     * 隐藏登录中提示框
     */
    private void progressDialogDismiss() {
        if (mRegisterDialog != null)
            mRegisterDialog.dismiss();
    }

    /**
     * 显示登录中提示框
     */
    private void progressDialogShow() {
        mRegisterDialog = ProgressDialog
                .show(mContext,
                        mContext.getResources().getText(
                                R.string.tip),
                        mContext.getResources().getText(
                                R.string.registering), true, false);
    }
}
