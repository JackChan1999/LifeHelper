package com.qz.lifehelper.ui.fragment;

import android.view.View;

import com.qz.lifehelper.R;
import com.qz.lifehelper.business.AuthenticationBusiness;
import com.qz.lifehelper.business.TopInfoBusiness;
import com.qz.lifehelper.entity.P2PCategoryBean;
import com.qz.lifehelper.entity.P2PItemBean;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Date;
import java.util.List;

import bolts.Task;

/**
 * 特卖页面
 */
@EFragment(R.layout.fragment_p2p_list)
public class SaleFragment extends P2PListFragment {

    static public class Builder {
        SaleFragment fragment = new SaleFragment_.FragmentBuilder_().build();

        public SaleFragment create() {
            return fragment;
        }
    }

    protected String getTitleName() {
        return "特卖商品";
    }

    @Bean
    TopInfoBusiness topInfoBusiness;

    @Bean
    AuthenticationBusiness authenticationBusiness;

    protected Task<List<P2PItemBean>> getP2PItem(final P2PCategoryBean catergoryBean, final int count, final Date after) {
        return topInfoBusiness.getSales();
    }

    @ViewById(R.id.add_p2p_bn)
    View addBn;

    @AfterViews
    void setAddBn() {
        addBn.setVisibility(View.GONE);
    }

    @AfterViews
    void cancelSetListview() {
        listView.setPullLoadEnable(false);
        unregisterForContextMenu(listView);
    }

}
