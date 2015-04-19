package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.P2PBusiness;
import com.qz.lifehelper.entity.P2PCategoryBean;
import com.qz.lifehelper.service.P2PService;
import com.qz.lifehelper.ui.adapter.P2PCategoryAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * P2P的分类目录页
 */
@EFragment(R.layout.fragment_p2p_category)
public class P2pCategoryFragment extends BaseFragment {
    public static class Builder {

        private P2pCategoryFragment fragment = new P2pCategoryFragment_.FragmentBuilder_().build();

        public P2pCategoryFragment create() {
            return fragment;
        }
    }

    @ViewById(R.id.listview)
    ListView listView;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @Bean
    P2PCategoryAdapter adapter;

    @Bean
    P2PBusiness p2pBusiness;

    @Bean
    P2PService p2pService;

    List<P2PCategoryBean> data = new ArrayList<>();

    @AfterViews
    void setListView() {
        listView.setAdapter(adapter);
        p2pService.getP2PCategory().onSuccess(new Continuation<List<P2PCategoryBean>, Void>() {
            @Override
            public Void then(Task<List<P2PCategoryBean>> task) throws Exception {
                data.clear();
                data.addAll(task.getResult());
                adapter.setData(data);
                adapter.notifyDataSetChanged();
                return null;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = P2pCategoryFragment.this.getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                p2pBusiness.toP2PListFragment(transaction, data.get(position));
            }
        });
    }

    @AfterViews
    void setToolbar() {
        toolbar.setTitle("商品分类");
        ((ActionBarActivity) getActivity()).setSupportActionBar(toolbar);
    }
}
