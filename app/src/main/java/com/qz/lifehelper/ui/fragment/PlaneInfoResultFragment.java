package com.qz.lifehelper.ui.fragment;

import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.DialogBusiness;
import com.qz.lifehelper.business.PlaneBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.PlaneInfoBean;
import com.qz.lifehelper.ui.adapter.PlaneInfoAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * 航班信息结果页
 */
@EFragment(R.layout.layout_listview)
public class PlaneInfoResultFragment extends BaseFragment {

    /**
     * 出发城市
     */
    private CityBean startCity;
    /**
     * 目的城市
     */
    private CityBean endCity;
    /**
     * 出发日期
     */
    private Date dateFly;


    /**
     * 生成PlaneInfoResultFragment
     *
     * @param startCity 出发城市
     * @param endCity   目的城市
     * @param dateFly   出发日期
     */
    public static PlaneInfoResultFragment generateFragment(CityBean startCity, CityBean endCity, Date dateFly) {

        PlaneInfoResultFragment fragment = new PlaneInfoResultFragment_();
        fragment.startCity = startCity;
        fragment.endCity = endCity;
        fragment.dateFly = dateFly;
        return fragment;
    }

    @Bean
    PlaneBusiness planeBusiness;

    @Bean
    PlaneInfoAdapter adapter;

    List<PlaneInfoBean> data = new ArrayList<>();

    @ViewById(R.id.listview)
    ListView listView;

    @Bean
    DialogBusiness dialogBusiness;

    /**
     * 配置航班信息搜索结果列表
     */
    @AfterViews
    public void setListView() {
        listView.setAdapter(adapter);

        dialogBusiness.showDialog(getFragmentManager(), new DialogBusiness.ProgressDialogBuilder().create(), "plane_info");
        planeBusiness.getPlaneInfo(startCity, endCity, dateFly).onSuccess(new Continuation<List<PlaneInfoBean>, Object>() {
            @Override
            public Object then(Task<List<PlaneInfoBean>> task) throws Exception {
                dialogBusiness.hideDialog("plane_info");
                data.clear();
                data.addAll(task.getResult());
                adapter.setData(data);
                adapter.notifyDataSetChanged();
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @AfterViews
    void setToolbar() {
        TextView titleTv = (TextView) toolbar.findViewById(R.id.title_tv);
        String title = startCity.cityName + "－" + endCity.cityName;
        titleTv.setText(title);
    }
}
