package com.qz.lifehelper.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.qz.lifehelper.business.P2PBusiness;
import com.qz.lifehelper.entity.P2PCategoryBean;
import com.qz.lifehelper.entity.P2PRequestBean;
import com.qz.lifehelper.ui.fragment.P2pCategoryFragment;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

/**
 * 这是p2p的主Activity，它只是一个wrapper
 * <p/>
 * 具体的业务逻辑是由P2CategoryFragment,P2PListFragment,P2PDetailFragment实现的
 */
@EActivity
public class P2PActivity extends BaseActivity {

    static public Intent generateIntent(Context context, P2PRequestBean p2pRequestBean) {
        Intent intent = new Intent(context, P2PActivity_.class);
        intent.putExtra(P2P_REQUEST_BEAN, p2pRequestBean);
        return intent;
    }

    private static final String P2P_REQUEST_BEAN = "P2P_REQUEST_BEAN";
    @Bean
    P2PBusiness p2pBusiness;

    @Extra(P2P_REQUEST_BEAN)
    P2PRequestBean p2pRequestBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (p2pRequestBean.fragmentType) {
            case P2P_CATEGORY:
                p2pBusiness.toP2PCategoryFragment(transaction, new P2pCategoryFragment.Callback() {
                    @Override
                    public void onCategorySelected(P2PCategoryBean categoryBean) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.addToBackStack(null);
                        p2pBusiness.toP2PListFragment(transaction, categoryBean);
                    }
                });
                break;
            case P2P_LIST:
                p2pBusiness.toP2PListFragment(transaction, p2pRequestBean.category);
                break;
            case PERSONAL_P2P_LIST:
                p2pBusiness.toMySale(transaction);
                break;
        }
    }
}
