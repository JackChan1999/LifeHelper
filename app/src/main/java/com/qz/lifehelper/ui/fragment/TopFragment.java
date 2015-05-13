package com.qz.lifehelper.ui.fragment;

import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.DialogBusiness;
import com.qz.lifehelper.entity.POIResultBean;
import com.qz.lifehelper.ui.adapter.TopAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * 公告信息
 */
@EFragment(R.layout.fragment_top)
abstract public class TopFragment extends BaseFragment {

    @ViewById(R.id.listview)
    ListView listView;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @Bean
    TopAdapter adapter;

    @Bean
    DialogBusiness dialogBusiness;

    @AfterViews
    void setListView() {
        listView.setAdapter(adapter);
        dialogBusiness.showDialog(
                getFragmentManager()
                , new DialogBusiness.ProgressDialogBuilder().create()
                , "top");
        getData().onSuccess(new Continuation<List<POIResultBean>, Void>() {
            @Override
            public Void then(Task<List<POIResultBean>> task) throws Exception {
                dialogBusiness.hideDialog("top");
                adapter.setData(task.getResult());
                adapter.notifyDataSetChanged();
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    @ViewById(R.id.title_tv)
    TextView titleTv;

    @AfterViews
    void setToolbar() {
        titleTv.setText(getTitleName());
    }

    abstract protected String getTitleName();

    abstract protected Task<List<POIResultBean>> getData();
}
