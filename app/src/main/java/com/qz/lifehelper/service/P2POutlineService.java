package com.qz.lifehelper.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qz.lifehelper.dao.DaoMaster;
import com.qz.lifehelper.dao.DaoSession;
import com.qz.lifehelper.dao.P2P;
import com.qz.lifehelper.dao.P2PDao;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.P2PCategoryBean;
import com.qz.lifehelper.entity.P2PItemBean;
import com.qz.lifehelper.entity.UserInfoBean;
import com.qz.lifehelper.entity.json.P2PCategoryJsonBean;
import com.qz.lifehelper.entity.json.P2PItemJsonBean;
import com.qz.lifehelper.persist.P2PPersist;
import com.qz.lifehelper.utils.OutlineServiceUtil;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * 这是一个P2P的虚拟服务器
 */
@EBean
public class P2POutlineService implements IP2PService {

    private P2PDao p2pDao;

    @RootContext
    Context context;

    @AfterInject
    void setDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, OutlineServiceConstant.BD_NAME, null);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        p2pDao = daoSession.getP2PDao();
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

    /**
     * 获取P2P结果信息
     *
     * @param catergoryBean P2P类别
     * @param count
     * @param after
     */
    public Task<List<P2PItemBean>> getP2PItem(final P2PCategoryBean catergoryBean, int count, Date after) {
        return getP2PItem(catergoryBean, count, after, null);
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
                OutlineServiceUtil.analogLoding();
                P2P p2p = convertToP2P(p2pItemBean);
                p2p.setCreatedAt(new Date());
                Long id = p2pDao.insert(p2p);
                p2pItemBean.setId(String.valueOf(id));
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
                OutlineServiceUtil.analogLoding();
                P2P p2p = convertToP2P(p2pItemBean);
                p2pDao.delete(p2p);
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
                OutlineServiceUtil.analogLoding();
                P2P p2p = convertToP2P(p2pItemBean);
                p2pDao.update(p2p);
                return p2pItemBean;
            }
        });
    }

    @Override
    public Task<List<P2PItemBean>> getP2PItem(final P2PCategoryBean catergoryBean, final int count, final Date after, final UserInfoBean userInfoBean) {
        return Task.callInBackground(new Callable<List<P2PItemBean>>() {
            @Override
            public List<P2PItemBean> call() throws Exception {
                //模拟网络
                OutlineServiceUtil.analogLoding();
                QueryBuilder<P2P> queryBuilder = p2pDao.queryBuilder();
                if (catergoryBean != null) {
                    queryBuilder.where(P2PDao.Properties.Category.eq(catergoryBean.title));
                }
                if (userInfoBean != null) {
                    queryBuilder.where(P2PDao.Properties.UserId.eq(userInfoBean.id));
                }
                queryBuilder.limit(count);
                if (after != null) {
                    queryBuilder.where(P2PDao.Properties.CreatedAt.lt(after));
                }
                queryBuilder.orderDesc(P2PDao.Properties.CreatedAt);
                List<P2P> p2ps = queryBuilder.list();
                List<P2PItemBean> p2pItemBeans = new ArrayList<P2PItemBean>();
                for (P2P p2p : p2ps) {
                    P2PItemBean p2pItemBean = convertToP2PItemBean(p2p);
                    p2pItemBeans.add(p2pItemBean);
                }

                return p2pItemBeans;
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
     *
     * @param p2pItemJsonBean
     * @return
     */
    private P2PItemBean convertToP2PItemBean(P2PItemJsonBean p2pItemJsonBean) {
        //TODO 设置用户
        return new P2PItemBean()
                .setTitle(p2pItemJsonBean.getTitle())
                .setDetail(p2pItemJsonBean.getDetail())
                .setPrice(p2pItemJsonBean.getPrice() + "元")
                .setAddress("地址：" + p2pItemJsonBean.getAddress())
                .setTel("电话：" + p2pItemJsonBean.getTel())
                .setId(p2pItemJsonBean.getId())
                .setCategoryBean(new P2PCategoryBean().setTitle(p2pItemJsonBean.getCategory()))
                .setImageBean(ImageBean.generateImage(p2pItemJsonBean.getImage(), ImageBean.ImageType.OUTLINE))
                .setUserInfoBean(null);
    }

    /**
     * 将P2PItemBean转换为P2P
     */
    private P2P convertToP2P(P2PItemBean p2pItemBean) {
        P2P p2p = new P2P();
        p2p.setTitle(p2pItemBean.title);
        p2p.setDetail(p2pItemBean.detail);
        p2p.setTel(p2pItemBean.tel);
        p2p.setAdd(p2pItemBean.address);
        p2p.setCategory(p2pItemBean.categoryBean.title);
        p2p.setCreatedAt(p2pItemBean.createdAt);
        p2p.setPrice(p2pItemBean.price);
        p2p.setUserId(p2pItemBean.userInfoBean.id != null ? Long.valueOf(p2pItemBean.userInfoBean.id) : null);
        p2p.setImageId(p2pItemBean.imageBean.id != null ? Long.valueOf(p2pItemBean.imageBean.id) : null);
        p2p.setId(p2pItemBean.id != null ? Long.valueOf(p2pItemBean.id) : null);
        return p2p;
    }

    /**
     * 将P2P转换为P2PItemBean
     */
    private P2PItemBean convertToP2PItemBean(P2P p2p) {
        return new P2PItemBean()
                .setTitle(p2p.getTitle())
                .setPrice(p2p.getPrice())
                .setDetail(p2p.getDetail())
                .setAddress(p2p.getAdd())
                .setTel(p2p.getTel())
                .setCategoryBean(new P2PCategoryBean().setTitle(p2p.getCategory()))
                .setCreatedAt(p2p.getCreatedAt())
                .setImageBean(ImageBean.generateImage(
                        p2p.getImage() != null ? p2p.getImage().getImageSrc() : null
                        , ImageBean.ImageType.OUTLINE
                        , p2p.getImageId() != null ? String.valueOf(p2p.getImageId()) : null))
                .setUserInfoBean(UserInfoBean.generateBean(
                        p2p.getUser() != null ? p2p.getUser().getName() : null
                        , null
                        , p2p.getUserId() != null ? String.valueOf(p2p.getUserId()) : null))
                .setId(p2p.getId() != null ? String.valueOf(p2p.getId()) : null);
    }

}
