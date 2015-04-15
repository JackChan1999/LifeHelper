package com.qz.lifehelper.business;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.qz.lifehelper.entity.POIResultBean;
import com.qz.lifehelper.persist.OutlinePersist;
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
}
