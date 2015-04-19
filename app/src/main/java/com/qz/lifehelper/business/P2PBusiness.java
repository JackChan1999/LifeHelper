package com.qz.lifehelper.business;

import android.support.v4.app.FragmentTransaction;

import com.qz.lifehelper.entity.P2PCategoryBean;
import com.qz.lifehelper.entity.P2PItemBean;
import com.qz.lifehelper.entity.P2PRequestBean;
import com.qz.lifehelper.persist.OutlinePersist;
import com.qz.lifehelper.persist.P2PPersist;
import com.qz.lifehelper.ui.fragment.P2PAddFragment;
import com.qz.lifehelper.ui.fragment.P2PAlterFragment;
import com.qz.lifehelper.ui.fragment.P2PDetailFragment;
import com.qz.lifehelper.ui.fragment.P2PListFragment;
import com.qz.lifehelper.ui.fragment.P2pCategoryFragment;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * 负责处理P2P的业务逻辑
 */
@EBean
public class P2PBusiness {

    /**
     * 根据请求参数，前往制定的fragment
     */
    public void toTargetFragment(FragmentTransaction transaction, P2PRequestBean p2pRequestBean) {
        switch (p2pRequestBean.fragmentType) {
            case P2P_CATEGORY:
                toP2PCategoryFragment(transaction);
                break;
            case P2P_LIST:
                toP2PListFragment(transaction, p2pRequestBean.category);
                break;
            case P2P_DETAIL:
                toP2PDetailFragment(transaction, p2pRequestBean.p2PItemBean);
                break;
            case P2P_ADD:
                //TODO 这里实际上是没有实现的
                toP2PAddFragment(transaction, p2pRequestBean.addCallBack);
                break;
            case P2P_ALTER:
                //TODO 这里实际上是没有实现的
                toP2PAlterFragment(transaction, null, null);
                break;
        }
    }

    /**
     * 前往P2P修改页面
     */
    public void toP2PAlterFragment(FragmentTransaction transaction, P2PItemBean p2PItemBean, P2PAlterFragment.Callback callback) {
        P2PAlterFragment fragment = new P2PAlterFragment.Builder()
                .setCallback(callback)
                .setP2PItemBean(p2PItemBean)
                .create();
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }

    /**
     * 前往P2P新增页面
     */
    public void toP2PAddFragment(FragmentTransaction transaction, P2PAddFragment.Callback callback) {
        P2PAddFragment fragment = new P2PAddFragment.Builder()
                .setCallback(callback)
                .create();
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }


    /**
     * 前往P2P详情页
     */
    public void toP2PDetailFragment(FragmentTransaction transaction, P2PItemBean p2PItemBean) {
        P2PDetailFragment fragment = new P2PDetailFragment.Builder()
                .setP2PItemBean(p2PItemBean)
                .create();
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }

    /**
     * 前往P2P结果列表页
     */
    public void toP2PListFragment(FragmentTransaction transaction, P2PCategoryBean p2PCategoryBean) {
        P2PListFragment fragment = new P2PListFragment.Builder()
                .setCategory(p2PCategoryBean)
                .create();
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }

    /**
     * 前往P2P分类目录页
     */
    public void toP2PCategoryFragment(FragmentTransaction transaction) {
        P2pCategoryFragment fragment = new P2pCategoryFragment.Builder().create();
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }

    @Bean
    OutlinePersist outlinePersist;

    @Bean
    P2PPersist p2pPersist;

}
