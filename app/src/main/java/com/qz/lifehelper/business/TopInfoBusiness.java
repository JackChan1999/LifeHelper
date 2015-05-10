package com.qz.lifehelper.business;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.qz.lifehelper.entity.P2PCategoryBean;
import com.qz.lifehelper.entity.P2PItemBean;
import com.qz.lifehelper.entity.POIResultBean;
import com.qz.lifehelper.persist.OutlinePersist;
import com.qz.lifehelper.service.P2POutlineService;
import com.qz.lifehelper.ui.fragment.SaleFragment;
import com.qz.lifehelper.ui.fragment.SaleFragment_;
import com.qz.lifehelper.ui.fragment.TenTopRestruantFragment;
import com.qz.lifehelper.ui.fragment.TenTopRestruantFragment_;
import com.qz.lifehelper.ui.fragment.TenTopSpotsFragment;
import com.qz.lifehelper.ui.fragment.TenTopSpotsFragment_;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

/**
 * 负责实现TopInfo的业务逻辑
 * <p/>
 * TopInfo指的是一堆吸引用户去看的软文
 */
@EBean
public class TopInfoBusiness {

    @Bean
    OutlinePersist outlinePersist;

    @Bean
    POIBusiness poiBusiness;

    /**
     * 获取十大景点数据
     */
    public Task<List<POIResultBean>> getTenTopSpots() {
        return Task.callInBackground(new Callable<String>() {
            @Override
            public String call() throws Exception {
                //模拟网络加载
                Thread.sleep(1000);
                return outlinePersist.getTenTopSpots();
            }
        }).continueWithTask(new Continuation<String, Task<List<POIResultBean>>>() {
            @Override
            public Task<List<POIResultBean>> then(Task<String> task) throws Exception {
                return poiBusiness.parsePOIResult(task.getResult());
            }
        });
    }

    /**
     * 前往十大景点
     */
    public void toTenTopSpotsFragment(FragmentManager fragmentManager) {
        TenTopSpotsFragment fragment = new TenTopSpotsFragment_.FragmentBuilder_().build();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }

    /**
     * 前往特卖
     */
    public void toSaleFragment(FragmentTransaction transaction) {
        SaleFragment fragment = new SaleFragment_.FragmentBuilder_().build();
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }

    @Bean
    P2POutlineService p2POutlineService;

    /**
     * 获取特卖商品
     */
    public Task<List<P2PItemBean>> getSales() {
        return Task.callInBackground(new Callable<String>() {
            @Override
            public String call() throws Exception {
                //模拟网络加载
                Thread.sleep(1000);
                return outlinePersist.getTenTopSpots();
            }
        }).continueWithTask(new Continuation<String, Task<List<P2PItemBean>>>() {
            @Override
            public Task<List<P2PItemBean>> then(Task<String> task) throws Exception {
                P2PCategoryBean categoryBean = new P2PCategoryBean().setTitle("电子数码");
                return p2POutlineService.getP2PItem(categoryBean, 1000, null);
            }
        });
    }

    /**
     * 前往十大景点
     */
    public void toTenTopRestruantFragment(FragmentTransaction transaction) {
        TenTopRestruantFragment fragment = new TenTopRestruantFragment_.FragmentBuilder_().build();
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }


    /**
     * 获取十大餐厅信息
     */
    public Task<List<POIResultBean>> getTenTopRestruant() {
        return Task.callInBackground(new Callable<String>() {
            @Override
            public String call() throws Exception {
                //模拟网络加载
                Thread.sleep(1000);
                return outlinePersist.getTenTopRestruant();
            }
        }).continueWithTask(new Continuation<String, Task<List<POIResultBean>>>() {
            @Override
            public Task<List<POIResultBean>> then(Task<String> task) throws Exception {
                return poiBusiness.parsePOIResult(task.getResult());
            }
        });
    }
}
