package com.qz.lifehelper.business;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.qz.lifehelper.entity.P2PCategoryBean;
import com.qz.lifehelper.entity.P2PItemBean;
import com.qz.lifehelper.service.IP2PService;
import com.qz.lifehelper.service.P2POutlineService_;
import com.qz.lifehelper.ui.AppProfile;
import com.qz.lifehelper.ui.fragment.P2PAddFragment;
import com.qz.lifehelper.ui.fragment.P2PAlterFragment;
import com.qz.lifehelper.ui.fragment.P2PDetailFragment;
import com.qz.lifehelper.ui.fragment.P2PListFragment;
import com.qz.lifehelper.ui.fragment.P2pCategoryFragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Date;
import java.util.List;

import bolts.Task;

/**
 * 负责处理P2P的业务逻辑
 */
@EBean
public class P2PBusiness implements IP2PService {


    @RootContext
    Context context;

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

    /**
     * 前往我发布到商品页面
     */
    public void toMySale() {
        Toast.makeText(context, "前往我发布的商品页面", Toast.LENGTH_SHORT).show();
    }

    private IP2PService p2pService;

    @AfterInject
    void setService() {
        if (AppProfile.dateSource.equals(AppProfile.DATE_SOURCE.ONLINE)) {
            p2pService = P2POutlineService_.getInstance_(context);
        } else {
            p2pService = P2POutlineService_.getInstance_(context);
        }
    }

    @Override
    public Task<List<P2PCategoryBean>> getP2PCategory() {
        return p2pService.getP2PCategory();
    }

    @Override
    public Task<List<P2PItemBean>> getP2PItem(P2PCategoryBean catergoryBean, int count, Date after) {
        return p2pService.getP2PItem(catergoryBean, count, after);
    }

    @Override
    public Task<P2PItemBean> addP2PItem(P2PItemBean p2pItemBean) {
        return p2pService.addP2PItem(p2pItemBean);
    }

    @Override
    public Task<P2PItemBean> deleteP2PItem(P2PItemBean p2pItemBean) {
        return p2pService.deleteP2PItem(p2pItemBean);
    }

    @Override
    public Task<P2PItemBean> alterP2PItem(P2PItemBean p2pItemBean) {
        return p2pService.alterP2PItem(p2pItemBean);
    }

}
