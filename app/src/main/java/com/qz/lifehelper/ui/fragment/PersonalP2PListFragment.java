package com.qz.lifehelper.ui.fragment;

import android.view.View;
import android.widget.Toast;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.AuthenticationBusiness;
import com.qz.lifehelper.entity.P2PCategoryBean;
import com.qz.lifehelper.entity.P2PItemBean;
import com.qz.lifehelper.entity.UserInfoBean;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Date;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * 当前用户发布的商品
 */
@EFragment(R.layout.fragment_p2p_list)
public class PersonalP2PListFragment extends P2PListFragment {
    static public class Builder {
        PersonalP2PListFragment fragment = new PersonalP2PListFragment_.FragmentBuilder_().build();

        public PersonalP2PListFragment create() {
            return fragment;
        }
    }

    protected String getTitleName() {
        return "我的商品";
    }

    @Bean
    AuthenticationBusiness authenticationBusiness;

    protected Task<List<P2PItemBean>> getP2PItem(final P2PCategoryBean catergoryBean, final int count, final Date after) {
        return authenticationBusiness.getCurrentUser(false).continueWithTask(new Continuation<UserInfoBean, Task<List<P2PItemBean>>>() {
            @Override
            public Task<List<P2PItemBean>> then(Task<UserInfoBean> task) throws Exception {
                if (task.isFaulted()) {
                    Toast.makeText(PersonalP2PListFragment.this.getActivity(), "请先登录", Toast.LENGTH_LONG).show();
                    return null;
                } else {
                    return p2pBusiness.getP2PItem(catergoryBean, count, after, task.getResult());
                }
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    @ViewById(R.id.add_p2p_bn)
    View addBn;

    @AfterViews
    void setAddBn() {
        addBn.setVisibility(View.GONE);
    }

}
