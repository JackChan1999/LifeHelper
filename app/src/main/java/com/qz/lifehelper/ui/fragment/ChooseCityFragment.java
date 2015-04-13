package com.qz.lifehelper.ui.fragment;


import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.helper.ChooseBusCityHelper;
import com.qz.lifehelper.ui.adapter.BusChooseCityAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * 选择城市页面
 */
@EFragment(R.layout.fragment_choose_city)
public class ChooseCityFragment extends Fragment {

    public interface CallBcak {
        void onCityChoosed(CityBean cityBean);
    }

    static public class Builder {
        public Builder() {
            fragment = new ChooseCityFragment_();
        }


        public Builder setCallback(CallBcak callback) {
            fragment.callBcak = callback;
            return this;
        }


        ChooseCityFragment fragment;

        public ChooseCityFragment create() {
            return fragment;
        }
    }


    @Bean
    LocationBusiness locationBusiness;

    @Bean
    ChooseBusCityHelper chooseBusCityHelper;

    private CallBcak callBcak;

    private List<CityBean> cityBeans = new ArrayList<>();

    BusChooseCityAdapter adapter;

    @ViewById(R.id.choose_city_lv)
    ListView listView;

    /**
     * 配置城市
     */
    @AfterViews
    void setListView() {
        adapter = new BusChooseCityAdapter.Builder()
                .setContext(this.getActivity())
                .setCallBack(new BusChooseCityAdapter.Callback() {
                    @Override
                    public void onChooseCity(CityBean cityBean) {
                        callBcak.onCityChoosed(cityBean);
                    }

                    @Override
                    public void onFindCurrentLoactionCity() {
                        chooseBusCityHelper.setCurrentLocationCity(CityBean.generateCity(
                                ChooseCityFragment.this.getActivity().getString(R.string.find_location_ing)));
                        refreshList();
                        locationBusiness.findCurrentLocationCity().onSuccess(new Continuation<CityBean, Void>() {
                            @Override
                            public Void then(Task<CityBean> task) throws Exception {
                                chooseBusCityHelper.setCurrentLocationCity(task.getResult());
                                refreshList();
                                return null;
                            }
                        });
                    }
                })
                .create();

        listView.setAdapter(adapter);
        refreshList();
        locationBusiness.findCurrentLocationCity().onSuccess(new Continuation<CityBean, Void>() {
            @Override
            public Void then(Task<CityBean> task) throws Exception {
                chooseBusCityHelper.setCurrentLocationCity(task.getResult());
                refreshList();
                return null;
            }
        });
    }

    private void refreshList() {
        adapter.setData(chooseBusCityHelper.getChooseCityListData());
        adapter.notifyDataSetChanged();
    }
}
