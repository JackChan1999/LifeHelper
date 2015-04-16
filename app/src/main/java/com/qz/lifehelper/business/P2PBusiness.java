package com.qz.lifehelper.business;

import android.support.v4.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.P2PCategoryBean;
import com.qz.lifehelper.entity.P2PItemBean;
import com.qz.lifehelper.entity.P2PRequestBean;
import com.qz.lifehelper.entity.json.P2PCategoryJsonBean;
import com.qz.lifehelper.entity.json.P2PItemJsonBean;
import com.qz.lifehelper.persist.OutlinePersist;
import com.qz.lifehelper.persist.P2PPersist;
import com.qz.lifehelper.ui.fragment.P2PListFragment;
import com.qz.lifehelper.ui.fragment.P2pCategoryFragment;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

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
                toP2PDetailFragment();
                break;
        }
    }

    /**
     * 前往P2P详情页
     */
    public void toP2PDetailFragment() {

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

    /**
     * 获取P2P结果信息
     *
     * @param catergoryBean P2类别
     */
    public Task<List<P2PItemBean>> getP2PList(P2PCategoryBean catergoryBean) {
        return Task.callInBackground(new Callable<String>() {
            @Override
            public String call() throws Exception {
                //模拟网络加载
                Thread.sleep(1000);
                return outlinePersist.getP2PItems();
            }
        }).continueWithTask(new Continuation<String, Task<List<P2PItemBean>>>() {
            @Override
            public Task<List<P2PItemBean>> then(Task<String> task) throws Exception {
                return parseP2PItems(task.getResult());
            }
        });
    }

    /**
     * 解析P2P items的json数据
     */
    public Task<List<P2PItemBean>> parseP2PItems(final String p2pItemsJson) {
        return Task.callInBackground(new Callable<List<P2PItemBean>>() {
            @Override
            public List<P2PItemBean> call() throws Exception {
                Gson gson = new Gson();
                List<P2PItemJsonBean> p2pItemJsonBeans = gson.fromJson(p2pItemsJson, new TypeToken<List<P2PItemJsonBean>>() {
                }.getType());
                List<P2PItemBean> p2pItemBeans = new ArrayList<P2PItemBean>();
                for (P2PItemJsonBean p2pItemJsonBean : p2pItemJsonBeans) {
                    p2pItemBeans.add(new P2PItemBean()
                            .setTitle(p2pItemJsonBean.getTitle())
                            .setDetail(p2pItemJsonBean.getDetail())
                            .setPrice(p2pItemJsonBean.getPrice())
                            .setAddress(p2pItemJsonBean.getAddress())
                            .setTel(p2pItemJsonBean.getTel())
                            .setId(p2pItemJsonBean.getId())
                            .setImageBean(ImageBean.generateImage(p2pItemJsonBean.getImage(), ImageBean.ImageType.OUTLINE)));
                }
                return p2pItemBeans;
            }
        });
    }

    @Bean
    P2PPersist p2pPersist;

    /**
     * 获取P2P分类信息数据
     */
    public Task<List<P2PCategoryBean>> getP2PCategory() {
        return Task.callInBackground(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return p2pPersist.getP2PCategory();
            }
        }).continueWithTask(new Continuation<String, Task<List<P2PCategoryBean>>>() {
            @Override
            public Task<List<P2PCategoryBean>> then(Task<String> task) throws Exception {
                return parseP2PCategory(task.getResult());
            }
        });
    }

    /**
     * 解析p2p分类信息json数据
     */
    private Task<List<P2PCategoryBean>> parseP2PCategory(final String p2pCategoryJson) {
        return Task.callInBackground(new Callable<List<P2PCategoryBean>>() {
            @Override
            public List<P2PCategoryBean> call() throws Exception {
                Gson gson = new Gson();
                List<P2PCategoryJsonBean> p2pCategoryJsonBeans = gson.fromJson(p2pCategoryJson, new TypeToken<List<P2PCategoryJsonBean>>() {
                }.getType());
                List<P2PCategoryBean> p2pCategoryBeans = new ArrayList<P2PCategoryBean>();
                for (P2PCategoryJsonBean p2pCategoryJsonBean : p2pCategoryJsonBeans) {
                    p2pCategoryBeans.add(new P2PCategoryBean()
                            .setTitle(p2pCategoryJsonBean.getTitle())
                            .setContent(p2pCategoryJsonBean.getContent())
                            .setImageBean(ImageBean.generateImage(p2pCategoryJsonBean.getImage(), ImageBean.ImageType.OUTLINE)));
                }
                return p2pCategoryBeans;
            }
        });
    }


}
