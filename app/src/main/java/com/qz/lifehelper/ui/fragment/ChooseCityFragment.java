package com.qz.lifehelper.ui.fragment;


import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.LocationBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.helper.ChooseCityHelper;
import com.qz.lifehelper.ui.adapter.ChooseCityAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import bolts.Continuation;
import bolts.Task;

/**
 * 选择城市页面
 */
@EFragment(R.layout.fragment_choose_city)
public class ChooseCityFragment extends BaseFragment {

    private static final String TAG = ChooseCityFragment.class.getSimpleName() + "TAG";

    /**
     * ChooseCityFragment的回调接口
     */
    public interface CallBcak {
        /**
         * 当选中一个城市之后，将会回调该方法
         */
        void onCityChoosed(CityBean cityBean);
    }

    /**
     * ChooseCityFragment的构造器
     */
    static public class Builder {
        public Builder() {
            fragment = new ChooseCityFragment_();
        }


        /**
         * 设置回调接口
         */
        public Builder setCallback(CallBcak callback) {
            fragment.callBcak = callback;
            return this;
        }


        ChooseCityFragment fragment;

        /**
         * 构建ChooseCityFragment
         */
        public ChooseCityFragment create() {
            if (fragment.callBcak == null) {
                throw new RuntimeException("没有设置回调接口");
            }
            return fragment;
        }
    }


    @Bean
    LocationBusiness locationBusiness;

    @Bean
    ChooseCityHelper chooseCityHelper;

    private CallBcak callBcak;

    ChooseCityAdapter adapter;

    @ViewById(R.id.choose_city_lv)
    ListView listView;

    /**
     * 配置城市
     */
    @AfterViews
    void setListView() {
        adapter = new ChooseCityAdapter.Builder()
                .setContext(this.getActivity())
                .setCallBack(new ChooseCityAdapter.Callback() {
                    @Override
                    public void onChooseCity(CityBean cityBean) {
                        callBcak.onCityChoosed(cityBean);
                    }

                    @Override
                    public void onFindCurrentLoactionCity() {
                        chooseCityHelper.setCurrentLocationCity(CityBean.generateCity(
                                ChooseCityFragment.this.getActivity().getString(R.string.find_location_ing)));
                        findCurrentLocationCity();
                    }
                })
                .create();

        listView.setAdapter(adapter);
        findCurrentLocationCity();
    }

    /**
     * 定位当前位置
     */
    private void findCurrentLocationCity() {
        refreshList();
        locationBusiness.findCurrentLocationCity().onSuccess(new Continuation<CityBean, Void>() {
            @Override
            public Void then(Task<CityBean> task) throws Exception {
                chooseCityHelper.setCurrentLocationCity(task.getResult());
                refreshList();
                return null;
            }
        });
    }

    /**
     * 刷新城市列表
     */
    private void refreshList() {
        adapter.setData(chooseCityHelper.getChooseCityListData());
        adapter.notifyDataSetChanged();
    }

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @AfterViews
    void setToolBar() {
        TextView titleTv = (TextView) toolbar.findViewById(R.id.title_tv);
        String title = "选择城市";
        titleTv.setText(title);
    }
}
