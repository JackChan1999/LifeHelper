package com.qz.lifehelper.ui.fragment;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.DialogBusiness;
import com.qz.lifehelper.business.P2PBusiness;
import com.qz.lifehelper.entity.P2PCategoryBean;
import com.qz.lifehelper.entity.P2PItemBean;
import com.qz.lifehelper.ui.adapter.P2PListAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * P2结果列表页面
 */
@EFragment(R.layout.fragment_p2p_list)
public class P2PListFragment extends BaseFragment {

    public static class Builder {

        private P2PListFragment fragment = new P2PListFragment_.FragmentBuilder_().build();

        public Builder setCategory(P2PCategoryBean p2PCategoryBean) {
            fragment.catergoryBean = p2PCategoryBean;
            return this;
        }

        public P2PListFragment create() {
            if (fragment.catergoryBean == null) {
                throw new RuntimeException("没有设置category");
            }
            return fragment;
        }
    }

    private P2PCategoryBean catergoryBean;

    @ViewById(R.id.listview)
    ListView listView;

    @Bean
    P2PListAdapter adapter;

    @Bean
    P2PBusiness p2PBusiness;

    @Bean
    DialogBusiness dialogBusiness;

    @AfterViews
    void setListView() {
        listView.setAdapter(adapter);
        dialogBusiness.showDialog(getFragmentManager()
                , new DialogBusiness.ProgressDialogBuilder().create()
                , "p2pList");
        p2PBusiness.getP2PList(catergoryBean).onSuccess(new Continuation<List<P2PItemBean>, Void>() {
            @Override
            public Void then(Task<List<P2PItemBean>> task) throws Exception {
                adapter.setData(task.getResult());
                dialogBusiness.hideDialog("p2pList");
                adapter.notifyDataSetChanged();
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @AfterViews
    void setToolbar() {
        toolbar.setTitle(catergoryBean.title);
        ((ActionBarActivity) getActivity()).setSupportActionBar(toolbar);
    }

}
