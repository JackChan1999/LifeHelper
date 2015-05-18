package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.DialogBusiness;
import com.qz.lifehelper.business.P2PBusiness;
import com.qz.lifehelper.entity.P2PCategoryBean;
import com.qz.lifehelper.entity.P2PItemBean;
import com.qz.lifehelper.ui.adapter.P2PListAdapter;
import com.qz.lifehelper.ui.view.XListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

/**
 * P2结果列表页面
 */
@EFragment(R.layout.fragment_p2p_list)
public class P2PListFragment extends BaseFragment {

    private static final String TAG = P2PListFragment.class.getSimpleName() + "_TAG";

    private static final int ITEM_COUNT = 10;

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
    XListView listView;

    @Bean
    P2PListAdapter adapter;

    @Bean
    P2PBusiness p2pBusiness;

    @Bean
    DialogBusiness dialogBusiness;

    private List<P2PItemBean> data = new LinkedList<>();

    @AfterViews
    void setListView() {
        listView.setAdapter(adapter);
        dialogBusiness.showDialog(getFragmentManager()
                , new DialogBusiness.ProgressDialogBuilder().create()
                , "p2pList");

        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                getP2PItem(
                        catergoryBean
                        , ITEM_COUNT
                        , data.size() != 0 ? data.get(data.size() - 1).createdAt : null)
                        .continueWith(new Continuation<List<P2PItemBean>, Object>() {
                            @Override
                            public Object then(Task<List<P2PItemBean>> task) throws Exception {
                                listView.stopLoadMore();
                                if (task.isFaulted()) {
                                    Log.e(TAG, "load p2p items fail", task.getError());
                                } else {
                                    Log.d(TAG, "load p2p items success", task.getError());
                                    List<P2PItemBean> newData = task.getResult();
                                    if (newData != null && newData.size() > 0) {
                                        data.addAll(task.getResult());
                                        refreshListView(data);
                                    } else {
                                        listView.setPullLoadEnable(false);
                                    }
                                }
                                return null;
                            }
                        }, Task.UI_THREAD_EXECUTOR);
            }
        });

        getP2PItem(catergoryBean, ITEM_COUNT, null).continueWith(new Continuation<List<P2PItemBean>, Void>() {
            @Override
            public Void then(Task<List<P2PItemBean>> task) throws Exception {
                dialogBusiness.hideDialog("p2pList");
                if (task.isFaulted()) {
                    Log.e(TAG, "load p2p items fail", task.getError());
                } else {
                    Log.d(TAG, "load p2p items success");
                    data.clear();
                    data.addAll(task.getResult());
                    refreshListView(data);
                }
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

    private void refreshListView(List<P2PItemBean> data) {
        adapter.setData(data);
        adapter.notifyDataSetChanged();
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
        p2pBusiness.toP2PAlterFragment(transaction, data.get(info.position - 1), new P2PAlterFragment.Callback() {
            @Override
            public void onAlterSuccess(final P2PItemBean p2pItemBean) {
                Task.call(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        for (P2PItemBean targerItem : data) {
                            if (targerItem.id.equals(p2pItemBean.id)) {
                                int indexOf = data.indexOf(targerItem);
                                data.remove(targerItem);
                                data.add(indexOf, p2pItemBean);
                                break;
                            }
                        }
                        refreshListView(data);
                        getFragmentManager().popBackStack();
                        return null;
                    }
                });
            }

            @Override
            public void onDeleteSuccess(final P2PItemBean p2pItemBean) {
                Task.call(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        for (P2PItemBean targerItem : data) {
                            if (targerItem.id.equals(p2pItemBean.id)) {
                                data.remove(targerItem);
                                break;
                            }
                        }
                        refreshListView(data);
                        getFragmentManager().popBackStack();
                        return null;
                    }
                });
            }
        });
        return true;
    }

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.title_tv)
    TextView toolbarTitleTv;

    @Click(R.id.add_p2p_bn)
    void onAddP2PBnClicked() {
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        P2PAddFragment.Callback callback = new P2PAddFragment.Callback() {
            @Override
            public void onAddP2PItemSuccess(P2PItemBean p2pItemBean) {
                data.add(0, p2pItemBean);
                Task.call(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        refreshListView(data);
                        getFragmentManager().popBackStack();
                        return null;
                    }
                }, Task.UI_THREAD_EXECUTOR);
            }
        };
        p2pBusiness.toP2PAddFragment(transaction, catergoryBean, callback);
    }

    @AfterViews
    void setToolbar() {
        toolbarTitleTv.setText(getTitleName());
    }

    protected String getTitleName() {
        return catergoryBean.title;
    }

    protected Task<List<P2PItemBean>> getP2PItem(P2PCategoryBean catergoryBean, int count, Date after) {
        return p2pBusiness.getP2PItem(catergoryBean, count, after);
    }
}
