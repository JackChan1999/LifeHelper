package com.qz.lifehelper.ui.fragment;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.DialogBusiness;
import com.qz.lifehelper.business.POIBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.POICategoryBean;
import com.qz.lifehelper.entity.POIResultBean;
import com.qz.lifehelper.ui.adapter.POIListAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * POI列表页
 */
@EFragment(R.layout.fragment_poi_list)
public class POIListFragment extends BaseFragment {

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

    @ViewById(R.id.poi_result_lv)
    public ListView listView;

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
        listView.setAdapter(adpater);
        poiBusiness.loadPOIData(cityBean, categoryBean).onSuccess(
                new Continuation<List<POIResultBean>, Void>() {
                    @Override
                    public Void then(Task<List<POIResultBean>> task) throws Exception {
                        poiResultBeans.clear();
                        poiResultBeans.addAll(task.getResult());
                        if (poiResultBeans == null || poiResultBeans.size() == 0) {
                            onLoadPOIDataFial();
                        } else {
                            adpater.setData(poiResultBeans);
                            adpater.notifyDataSetChanged();
                            onLoadPOIDataSuccess();
                        }
                        return null;
                    }
                }, Task.UI_THREAD_EXECUTOR);
        onStarLoadPOIData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                poiBusiness.toPOIDetailFragment(transaction, poiResultBeans.get(position));
            }
        });
    }

    @Bean
    DialogBusiness dialogBusiness;

    /**
     * 当开始加载数据时被调用
     */
    void onStarLoadPOIData() {
        dialogBusiness.showDialog(getFragmentManager(), new DialogBusiness.ProgressDialogBuilder().create(), "poi_list");
    }

    /**
     * 当成功加载数据时被调用
     */
    void onLoadPOIDataSuccess() {
        dialogBusiness.hideDialog("poi_list");
    }

    /**
     * 当加载数据失败时被调用
     */
    void onLoadPOIDataFial() {

    }

    @ViewById(R.id.title_tv)
    TextView toolbarTitleTv;

    @AfterViews
    void setToolbar() {
        toolbarTitleTv.setText(categoryBean.categotyName);
    }

}
