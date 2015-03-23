package com.qz.lifehelper.ui.fragment;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.presentation.IPersonalView;
import com.qz.lifehelper.presentation.PersonalFragmentPresentation;

/**
 * Created by kohoh on 15/3/23.
 */

@EFragment(R.layout.fragment_personal)
public class PersonalFragment extends Fragment implements IPersonalView{

	@Bean
	PersonalFragmentPresentation presentation;

	@Click(R.id.login_bn)
	public void onLoginBnClick() {
		presentation.toLogin();
	}

	@Click(R.id.my_sales)
	public void onMySalesClick() {
		presentation.toMySale();
	}

	@Click(R.id.my_publish)
	public void onMyPublishClick() {
		presentation.toMyPublish();
	}

	@Click(R.id.public_info)
	public void onPublicInfoClick() {
		presentation.toPublicInf();
	}

	@Click(R.id.logout_bn)
	public void onLogoutBnClick() {
		presentation.logout();
	}

    @Override
    public void onStart() {
        presentation.bind(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        presentation.unbind();
    }

    @ViewById(R.id.login_bn)
    View loginBn;

    @ViewById(R.id.user_icon_iv)
    ImageView userIconIv;

    @ViewById(R.id.user_icon_background_iv)
    ImageView userIconBagIv;

    @Override
    public void login(Bitmap userIcon, Bitmap userIconBag) {
        loginBn.setVisibility(View.GONE);
        userIconBagIv.setImageBitmap(userIconBag);
        userIconIv.setImageBitmap(userIcon);
    }

    @Override
    public void logout(Bitmap userIcon,Bitmap userIconBag) {
        loginBn.setVisibility(View.VISIBLE);
        userIconBagIv.setImageBitmap(userIconBag);
        userIconIv.setImageBitmap(userIcon);
    }

    @ViewById(R.id.app_version_tv)
    TextView appVersionTv;

    @Override
    public void setAppVersion(String version) {
        appVersionTv.setText(version);
    }
}
