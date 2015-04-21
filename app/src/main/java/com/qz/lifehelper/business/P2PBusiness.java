package com.qz.lifehelper.business;

import android.support.v4.app.FragmentTransaction;

import com.qz.lifehelper.entity.P2PCategoryBean;
import com.qz.lifehelper.entity.P2PItemBean;
import com.qz.lifehelper.ui.fragment.P2PAddFragment;
import com.qz.lifehelper.ui.fragment.P2PAlterFragment;
import com.qz.lifehelper.ui.fragment.P2PDetailFragment;
import com.qz.lifehelper.ui.fragment.P2PListFragment;
import com.qz.lifehelper.ui.fragment.P2pCategoryFragment;

import org.androidannotations.annotations.EBean;

/**
 * 负责处理P2P的业务逻辑
 */
@EBean
public class P2PBusiness {


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
    public void toP2PAddFragment(FragmentTransaction transaction, P2PCategoryBean categoryBean, P2PAddFragment.Callback callback) {
        P2PAddFragment fragment = new P2PAddFragment.Builder()
                .setCallback(callback)
                .setCategory(categoryBean)
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
    public void toP2PCategoryFragment(FragmentTransaction transaction, P2pCategoryFragment.Callback callback) {
        P2pCategoryFragment fragment = new P2pCategoryFragment.Builder()
                .setCallback(callback)
                .create();
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }

}
