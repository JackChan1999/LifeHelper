package com.qz.lifehelper.ui.fragment;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.DialogBusiness;
import com.qz.lifehelper.business.TopInfoBusiness;
import com.qz.lifehelper.entity.POIResultBean;
import com.qz.lifehelper.ui.adapter.TenTopSpotsAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * 十大景点
 */
@EFragment(R.layout.fragment_ten_top_spots)
public class TenTopSpotsFragment extends BaseFragment {

    @ViewById(R.id.listview)
    ListView listView;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @Bean
    TenTopSpotsAdapter adapter;

    @Bean
    TopInfoBusiness topInfoBusiness;

    @Bean
    DialogBusiness dialogBusiness;

    @AfterViews
    void setListView() {
        listView.setAdapter(adapter);
        dialogBusiness.showDialog(
                getFragmentManager()
                , new DialogBusiness.ProgressDialogBuilder().create()
                , "TenTopSpots");
        topInfoBusiness.getTenTopSpots().onSuccess(new Continuation<List<POIResultBean>, Void>() {
            @Override
            public Void then(Task<List<POIResultBean>> task) throws Exception {
                dialogBusiness.hideDialog("TenTopSpots");
                adapter.setData(task.getResult());
                adapter.notifyDataSetChanged();
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    @AfterViews
    void setToolbar() {
        ((ActionBarActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("十大旅游景点");
    }
}
