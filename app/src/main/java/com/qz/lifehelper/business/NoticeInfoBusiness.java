package com.qz.lifehelper.business;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.qz.lifehelper.persist.OutlinePersist;
import com.qz.lifehelper.ui.fragment.NoticeInfoFragment;
import com.qz.lifehelper.ui.fragment.NoticeInfoFragment_;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.concurrent.Callable;

import bolts.Task;

/**
 * 处理公告信息的业务逻辑
 */
@EBean
public class NoticeInfoBusiness {

    @Bean
    OutlinePersist outlinePersist;

    /**
     * 获取公告信息
     */
    public Task<String> getNoticeInfo() {
        return Task.callInBackground(new Callable<String>() {
            @Override
            public String call() throws Exception {
                //模拟从网络加载数据
                Thread.sleep(1000);
                return outlinePersist.getNoticeInfo();
            }
        });
    }

    /**
     * 前往公告信息页
     */
    public void toNotcieInfoFragment(FragmentManager fragmentManager) {
        NoticeInfoFragment fragment = new NoticeInfoFragment_.FragmentBuilder_().build();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack("");
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }
}
