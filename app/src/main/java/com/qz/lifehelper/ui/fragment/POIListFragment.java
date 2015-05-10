package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.DialogBusiness;
import com.qz.lifehelper.business.POIBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.POICategoryBean;
import com.qz.lifehelper.entity.POIResultBean;
import com.qz.lifehelper.ui.activity.POIAddFragment;
import com.qz.lifehelper.ui.adapter.POIListAdapter;
import com.qz.lifehelper.ui.view.XListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

/**
 * POI列表页
 */
@EFragment(R.layout.fragment_poi_list)
public class POIListFragment extends BaseFragment {

    private static final int ITEM_COUNT = 10;

    private static final String TAG = POIListFragment.class.getSimpleName() + "_TAG";


    static public class Builder {
        private POIListFragment fragment = new POIListFragment_.FragmentBuilder_().build();

        public Builder setCategory(POICategoryBean category) {
            fragment.categoryBean = category;
            return this;
        }

        public Builder setCity(CityBean city) {
            fragment.cityBean = city;
            return this;
        }

        public POIListFragment create() {

            if (fragment.categoryBean == null) {
                throw new IllegalStateException("请设置分类");
            }

            if (fragment.cityBean == null) {
                throw new IllegalStateException("请设置城市");
            }

            return fragment;
        }
    }

    private POICategoryBean categoryBean;
    private CityBean cityBean;

    @ViewById(R.id.listview)
    public XListView listView;

    @Bean
    POIBusiness poiBusiness;

    @Bean
    POIListAdapter adpater;

    private List<POIResultBean> poiResultBeans = new ArrayList<>();

    /**
     * 设置POI结果列表
     */
    @AfterViews
    public void setListView() {
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(false);
        listView.setAdapter(adpater);

        dialogBusiness.showDialog(getFragmentManager(), new DialogBusiness.ProgressDialogBuilder().create(), "poi_list");
        getPOIItems(cityBean
                , categoryBean
                , ITEM_COUNT
                , null)
                .continueWith(
                        new Continuation<List<POIResultBean>, Void>() {
                            @Override
                            public Void then(Task<List<POIResultBean>> task) throws Exception {
                                dialogBusiness.hideDialog("poi_list");
                                if (task.isFaulted()) {
                                    Log.e(TAG, "load poi items fial", task.getError());
                                    Toast.makeText(POIListFragment.this.getActivity(), "load poi items fail", Toast.LENGTH_LONG).show();
                                } else {
                                    poiResultBeans.clear();
                                    poiResultBeans.addAll(task.getResult());
                                    adpater.setData(poiResultBeans);
                                    adpater.notifyDataSetChanged();
                                    listView.setPullLoadEnable(true);
                                }
                                return null;
                            }
                        }, Task.UI_THREAD_EXECUTOR);

        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                getPOIItems(cityBean
                        , categoryBean
                        , ITEM_COUNT
                        , poiResultBeans.size() != 0 ? poiResultBeans.get(poiResultBeans.size() - 1) : null)
                        .onSuccess(new Continuation<List<POIResultBean>, Void>() {
                            @Override
                            public Void then(Task<List<POIResultBean>> task) throws Exception {
                                listView.stopLoadMore();
                                poiResultBeans.addAll(task.getResult());
                                adpater.setData(poiResultBeans);
                                adpater.notifyDataSetChanged();
                                return null;
                            }
                        }, Task.UI_THREAD_EXECUTOR);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                poiBusiness.toPOIDetailFragment(transaction, poiResultBeans.get(position));
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
        poiBusiness.toPOIAlterFragment(transaction, poiResultBeans.get(info.position - 1), new POIAlterFragment.Callback() {
            @Override
            public void onAlterSuccess(final POIResultBean poiItemBean) {
                Task.call(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        for (POIResultBean targerItem : poiResultBeans) {
                            if (targerItem.id.equals(poiItemBean.id)) {
                                int indexOf = poiResultBeans.indexOf(targerItem);
                                poiResultBeans.remove(targerItem);
                                poiResultBeans.add(indexOf, poiItemBean);
                                break;
                            }
                        }
                        adpater.setData(poiResultBeans);
                        adpater.notifyDataSetChanged();
                        getFragmentManager().popBackStack();
                        return null;
                    }
                });
            }

            @Override
            public void onDeleteSuccess(final POIResultBean poiItemBean) {
                Task.call(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        for (POIResultBean targerItem : poiResultBeans) {
                            if (targerItem.id.equals(poiItemBean.id)) {
                                poiResultBeans.remove(targerItem);
                                break;
                            }
                        }
                        adpater.setData(poiResultBeans);
                        adpater.notifyDataSetChanged();
                        getFragmentManager().popBackStack();
                        return null;
                    }
                });
            }
        });
        return true;
    }

    @Bean
    DialogBusiness dialogBusiness;

    @Click(R.id.add_poi_bn)
    void OnAddPOIBnClick() {
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        POIAddFragment.Callback callback = new POIAddFragment.Callback() {
            @Override
            public void onAddPOIItemSuccess(POIResultBean poiItemBean) {
                poiResultBeans.add(0, poiItemBean);
                Task.call(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        adpater.setData(poiResultBeans);
                        adpater.notifyDataSetChanged();
                        getFragmentManager().popBackStack();
                        return null;
                    }
                }, Task.UI_THREAD_EXECUTOR);
            }
        };
        poiBusiness.toPOIAddFragment(transaction, callback, categoryBean);
    }


    @ViewById(R.id.title_tv)
    TextView toolbarTitleTv;

    @AfterViews
    void setToolbar() {
        toolbarTitleTv.setText(getTitleName());
    }

    protected String getTitleName() {
        return categoryBean.categotyName;
    }

    protected Task<List<POIResultBean>> getPOIItems(final CityBean cityBean, final POICategoryBean categoryBean, final int count, POIResultBean lastestItem) {
        return poiBusiness.getPOIItems(cityBean, categoryBean, count, lastestItem);
    }

}
