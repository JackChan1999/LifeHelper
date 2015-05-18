package com.qz.lifehelper.ui.fragment;

import android.view.View;
import android.widget.Toast;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.AuthenticationBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.POICategoryBean;
import com.qz.lifehelper.entity.POIResultBean;
import com.qz.lifehelper.entity.UserInfoBean;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * 当前用户发布的POI信息
 */
@EFragment(R.layout.fragment_poi_list)
public class PersonalPOIListFragment extends POIListFragment {
    static public class Builder {
        PersonalPOIListFragment fragment = new PersonalPOIListFragment_.FragmentBuilder_().build();

        public PersonalPOIListFragment create() {
            return fragment;
        }
    }

    protected String getTitleName() {
        return "我发布的周边信息";
    }

    @Bean
    AuthenticationBusiness authenticationBusiness;

    @Override
    protected Task<List<POIResultBean>> getPOIItems(final CityBean cityBean, final POICategoryBean categoryBean, final int count, final POIResultBean lastestItem) {
        return authenticationBusiness.getCurrentUser(false)
                .continueWithTask(new Continuation<UserInfoBean, Task<List<POIResultBean>>>() {
                    @Override
                    public Task<List<POIResultBean>> then(Task<UserInfoBean> task) throws Exception {
                        if (task.isFaulted()) {
                            Toast.makeText(PersonalPOIListFragment.this.getActivity(), "请先登录", Toast.LENGTH_LONG).show();
                            return null;
                        } else {
                            return poiBusiness.getPersonalPOIItems(count, lastestItem, task.getResult());
                        }
                    }
                }, Task.UI_THREAD_EXECUTOR);
    }

    @ViewById(R.id.add_poi_bn)
    View addBn;

    @AfterViews
    void setAddBn() {
        addBn.setVisibility(View.GONE);
    }

}
