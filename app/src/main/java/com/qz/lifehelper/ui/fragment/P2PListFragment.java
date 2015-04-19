package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.ArrayList;
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
    P2PBusiness p2pBusiness;

    @Bean
    DialogBusiness dialogBusiness;

    private List<P2PItemBean> data = new ArrayList<>();

    @AfterViews
    void setListView() {
        listView.setAdapter(adapter);
        dialogBusiness.showDialog(getFragmentManager()
                , new DialogBusiness.ProgressDialogBuilder().create()
                , "p2pList");
        p2pBusiness.getP2PList(catergoryBean).onSuccess(new Continuation<List<P2PItemBean>, Void>() {
            @Override
            public Void then(Task<List<P2PItemBean>> task) throws Exception {
                data.clear();
                data.addAll(task.getResult());
                adapter.setData(data);
                dialogBusiness.hideDialog("p2pList");
                adapter.notifyDataSetChanged();
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                p2pBusiness.toP2PDetailFragment(transaction, data.get(position));
            }
        });

        registerForContextMenu(listView);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add("修改");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        p2pBusiness.toP2PAlterFragment(transaction, data.get(info.position), new P2PAlterFragment.Callback() {
            @Override
            public void onAlterSuccess(P2PItemBean p2pItemBean) {
                //刷新数据
                getFragmentManager().popBackStack();
            }

            @Override
            public void onDeleteSuccess(P2PItemBean p2pItemBean) {
                //刷新数据
                getFragmentManager().popBackStack();
            }
        });
        return true;
    }

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @AfterViews
    void setToolbar() {
        toolbar.setTitle(catergoryBean.title);
        toolbar.inflateMenu(R.menu.menu_p2p_list);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.p2p_add_menu_bn:
                        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.addToBackStack(null);
                        P2PAddFragment.Callback callback = new P2PAddFragment.Callback() {
                            @Override
                            public void onAddP2PItemSuccess(P2PItemBean p2pItemBean) {
                                //TODO 刷新数据
                            }
                        };
                        p2pBusiness.toP2PAddFragment(transaction, callback);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}
