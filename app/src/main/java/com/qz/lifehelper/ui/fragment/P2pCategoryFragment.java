package com.qz.lifehelper.ui.fragment;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.P2PBusiness;
import com.qz.lifehelper.entity.P2PCategoryBean;
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

        public Builder setCallback(Callback callback) {
            fragment.callback = callback;
            return this;
        }

        public P2pCategoryFragment create() {
            if (fragment.callback == null) {
                throw new IllegalStateException("没有设置Callback");
            }

            return fragment;
        }
    }

    /**
     * 当选择来分类后回调该接口
     */
    public interface Callback {
        /**
         * 当成功选择来分类后会回调该方法
         */
        public void onCategorySelected(P2PCategoryBean categoryBean);
    }

    private Callback callback;

    @ViewById(R.id.listview)
    ListView listView;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @Bean
    P2PCategoryAdapter adapter;

    @Bean
    P2PBusiness p2pBusiness;

    List<P2PCategoryBean> data = new ArrayList<>();

    @AfterViews
    void setListView() {
        listView.setAdapter(adapter);
        p2pBusiness.getP2PCategory().onSuccess(new Continuation<List<P2PCategoryBean>, Void>() {
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
                callback.onCategorySelected(data.get(position));
            }
        });
    }

    @ViewById(R.id.title_tv)
    TextView titleTv;

    @AfterViews
    void setToolbar() {
        titleTv.setText("商品目录");
    }
}
