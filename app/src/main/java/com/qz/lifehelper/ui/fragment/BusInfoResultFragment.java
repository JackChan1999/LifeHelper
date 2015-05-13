package com.qz.lifehelper.ui.fragment;

import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.BusBusiness;
import com.qz.lifehelper.business.DialogBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.json.BusInfoBean;
import com.qz.lifehelper.ui.adapter.BusInfoAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * 长途大巴搜索结果页
 */
@EFragment(R.layout.layout_listview)
public class BusInfoResultFragment extends BaseFragment {

    /**
     * 出发城市
     */
    private CityBean startCity;
    /**
     * 目的城市
     */
    private CityBean endCity;


    /**
     * 生成BusInfoResultFragment
     *
     * @param startCity 出发城市
     * @param endCity   目的城市
     */
    public static BusInfoResultFragment generateFragment(CityBean startCity, CityBean endCity) {

        BusInfoResultFragment fragment = new BusInfoResultFragment_();
        fragment.startCity = startCity;
        fragment.endCity = endCity;
        return fragment;
    }

    @Bean
    BusBusiness busBusiness;

    @Bean
    BusInfoAdapter adapter;

    List<BusInfoBean> data = new ArrayList<>();

    @Bean
    DialogBusiness dialogBusiness;

    @ViewById(R.id.listview)
    ListView listView;

    /**
     * 配置长途大巴信息搜索结果列表
     */
    @AfterViews
    public void setListView() {
        listView.setAdapter(adapter);

        dialogBusiness.showDialog(getFragmentManager(), new DialogBusiness.ProgressDialogBuilder().create(), "bus_info");
        busBusiness.getBusInfo(startCity, endCity).onSuccess(new Continuation<List<BusInfoBean>, Void>() {
            @Override
            public Void then(Task<List<BusInfoBean>> task) throws Exception {
                dialogBusiness.hideDialog("bus_info");
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
