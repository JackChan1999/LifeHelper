package com.qz.lifehelper.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.P2PCategoryBean;
import com.qz.lifehelper.entity.P2PItemBean;
import com.qz.lifehelper.entity.json.P2PCategoryJsonBean;
import com.qz.lifehelper.entity.json.P2PItemJsonBean;
import com.qz.lifehelper.persist.P2PPersist;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

/**
 * 这是一个P2P的虚拟服务器
 */
@EBean
public class P2PService {

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

    /**
     * 获取P2P结果信息
     *
     * @param catergoryBean P2P类别
     */
    public Task<List<P2PItemBean>> getP2PItem(final P2PCategoryBean catergoryBean) {
        return Task.callInBackground(new Callable<String>() {
            @Override
            public String call() throws Exception {
                //模拟网络加载
                Thread.sleep(1000);
                return p2pPersist.getP2PItem(catergoryBean);
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
    private Task<List<P2PItemBean>> parseP2PItems(final String p2pItemsJson) {
        return Task.callInBackground(new Callable<List<P2PItemBean>>() {
            @Override
            public List<P2PItemBean> call() throws Exception {
                Gson gson = new Gson();
                List<P2PItemJsonBean> p2pItemJsonBeans = gson.fromJson(p2pItemsJson, new TypeToken<List<P2PItemJsonBean>>() {
                }.getType());
                List<P2PItemBean> p2pItemBeans = new ArrayList<P2PItemBean>();
                for (P2PItemJsonBean p2pItemJsonBean : p2pItemJsonBeans) {
                    p2pItemBeans.add(convertToP2PItemBean(p2pItemJsonBean));
                }
                return p2pItemBeans;
            }
        });
    }

    /**
     * 添加p2p信息
     */
    public Task<P2PItemBean> addP2PItem(final P2PItemBean p2pItemBean) {
        return Task.callInBackground(new Callable<P2PItemBean>() {
            @Override
            public P2PItemBean call() throws Exception {
                //模拟网络
                Thread.sleep(2000);

                List<P2PItemJsonBean> p2pItemJsonBeans = getP2PItemJsonBean(p2pItemBean.categoryBean);
                p2pItemBean.setId(generateId());
                p2pItemJsonBeans.add(0, convertToP2pItemJsonBean(p2pItemBean));
                setP2PItemJsonBean(p2pItemJsonBeans, p2pItemBean.categoryBean);
                return p2pItemBean;
            }
        });
    }

    /**
     * 删除p2p信息
     */
    public Task<P2PItemBean> deleteP2PItem(final P2PItemBean p2pItemBean) {
        return Task.callInBackground(new Callable<P2PItemBean>() {
            @Override
            public P2PItemBean call() throws Exception {
                //模拟网络
                Thread.sleep(1000);

                List<P2PItemJsonBean> p2pItemJsonBeans = getP2PItemJsonBean(p2pItemBean.categoryBean);
                for (P2PItemJsonBean p2pItemJsonBean : p2pItemJsonBeans) {
                    if (p2pItemJsonBean.getId().equals(p2pItemBean.id)) {
                        p2pItemJsonBeans.remove(p2pItemJsonBean);
                        break;
                    }
                }
                setP2PItemJsonBean(p2pItemJsonBeans, p2pItemBean.categoryBean);
                return p2pItemBean;
            }
        });
    }

    /**
     * 修改p2p信息
     */
    public Task<P2PItemBean> alterP2PItem(final P2PItemBean p2pItemBean) {
        return Task.callInBackground(new Callable<P2PItemBean>() {
            @Override
            public P2PItemBean call() throws Exception {
                //模拟网络
                Thread.sleep(1000);

                List<P2PItemJsonBean> p2PItemJsonBeans = getP2PItemJsonBean(p2pItemBean.categoryBean);
                for (P2PItemJsonBean p2pItemJsonBean : p2PItemJsonBeans) {
                    if (p2pItemJsonBean.getId().equals(p2pItemBean.id)) {
                        int indexOf = p2PItemJsonBeans.indexOf(p2pItemJsonBean);
                        p2PItemJsonBeans.remove(p2pItemJsonBean);
                        p2PItemJsonBeans.add(indexOf, convertToP2pItemJsonBean(p2pItemBean));
                        break;
                    }
                }
                setP2PItemJsonBean(p2PItemJsonBeans, p2pItemBean.categoryBean);
                return p2pItemBean;
            }

        });
    }

    /**
     * 获取对应分类的P2PItemJsonBean数据
     */
    private List<P2PItemJsonBean> getP2PItemJsonBean(P2PCategoryBean categoryBean) throws IOException {
        Gson gson = new Gson();
        String p2pJson = p2pPersist.getP2PItem(categoryBean);
        return gson.fromJson(p2pJson, new TypeToken<List<P2PItemJsonBean>>() {
        }.getType());
    }

    /**
     * 设置对应分类的P2PItemJsonBean数据
     */
    private void setP2PItemJsonBean(List<P2PItemJsonBean> p2pItemJsonBeans, P2PCategoryBean categoryBean) throws IOException {
        Gson gson = new Gson();
        String p2pJson = gson.toJson(p2pItemJsonBeans);
        p2pPersist.setP2PItem(p2pJson, categoryBean);
    }

    /**
     * 生成id
     */
    private String generateId() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(Calendar.DATE));
    }

    /**
     * 将p2pItemBean转换为p2pItemJsonBean
     */
    private P2PItemJsonBean convertToP2pItemJsonBean(P2PItemBean p2pItemBean) {
        P2PItemJsonBean p2pItemJsonBean = new P2PItemJsonBean();
        p2pItemJsonBean.setId(p2pItemBean.id);
        p2pItemJsonBean.setTitle(p2pItemBean.title);
        p2pItemJsonBean.setDetail(p2pItemBean.detail);
        p2pItemJsonBean.setAddress(p2pItemBean.address);
        p2pItemJsonBean.setTel(p2pItemBean.tel);
        p2pItemJsonBean.setPrice(p2pItemBean.price);
        p2pItemJsonBean.setCategory(p2pItemBean.categoryBean.title);
        p2pItemJsonBean.setImage(p2pItemBean.imageBean.imageSrc);
        return p2pItemJsonBean;
    }

    /**
     * 将p2pItemJsonBean转换为p2pItemBean
     * @param p2pItemJsonBean
     * @return
     */
    private P2PItemBean convertToP2PItemBean(P2PItemJsonBean p2pItemJsonBean) {
        return new P2PItemBean()
                .setTitle(p2pItemJsonBean.getTitle())
                .setDetail(p2pItemJsonBean.getDetail())
                .setPrice(p2pItemJsonBean.getPrice())
                .setAddress(p2pItemJsonBean.getAddress())
                .setTel(p2pItemJsonBean.getTel())
                .setId(p2pItemJsonBean.getId())
                .setCategoryBean(new P2PCategoryBean().setTitle(p2pItemJsonBean.getCategory()))
                .setImageBean(ImageBean.generateImage(p2pItemJsonBean.getImage(), ImageBean.ImageType.OUTLINE));
    }
}
