package com.qz.lifehelper.service;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.P2PCategoryBean;
import com.qz.lifehelper.entity.P2PItemBean;
import com.qz.lifehelper.entity.UserInfoBean;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Task;

/**
 * 交易市场的在线服务器
 * <p/>
 * 使用的是leancloud
 */
@EBean
public class P2POnlineService implements IP2PService {


    @Bean
    P2POutlineService p2pOutlineService;

    @Override
    public Task<List<P2PCategoryBean>> getP2PCategory() {
        return p2pOutlineService.getP2PCategory();
    }

    @Override
    public Task<List<P2PItemBean>> getP2PItem(final P2PCategoryBean catergoryBean, final int count, final Date after, final UserInfoBean userInfoBean) {
        return Task.callInBackground(new Callable<List<P2PItemBean>>() {
            @Override
            public List<P2PItemBean> call() throws Exception {
                AVQuery<AVObject> query = new AVQuery<AVObject>(LeancloudConstant.P2P_TABLE);
                query.limit(count);
                if (catergoryBean != null) {
                    query.whereEqualTo(LeancloudConstant.CATEGORY_COLUME, catergoryBean.title);
                }
                query.orderByDescending(LeancloudConstant.CREATED_AT_COLUME);
                if (after != null) {
                    query.whereLessThan(LeancloudConstant.CREATED_AT_COLUME, after);
                }
                if (userInfoBean != null && userInfoBean.id != null) {
                    AVUser currentUserObject = AVUser.getQuery().whereEqualTo(LeancloudConstant.ID_COLUME, userInfoBean.id).getFirst();
                    query.whereEqualTo(LeancloudConstant.USER_COLUME, currentUserObject);
                }
                List<AVObject> p2pObjects = query.find();
                List<P2PItemBean> p2pItemBeans = new ArrayList<P2PItemBean>();
                for (AVObject p2pObject : p2pObjects) {
                    P2PItemBean p2pItemBean = convertToP2PItemBean(p2pObject);
                    p2pItemBeans.add(p2pItemBean);
                }

                return p2pItemBeans;
            }
        });
    }

    @Override
    public Task<List<P2PItemBean>> getP2PItem(final P2PCategoryBean catergoryBean, final int count, final Date after) {
        return getP2PItem(catergoryBean, count, after, null);
    }

    @Override
    public Task<P2PItemBean> addP2PItem(final P2PItemBean p2pItemBean) {
        return Task.callInBackground(new Callable<P2PItemBean>() {
            @Override
            public P2PItemBean call() throws Exception {
                AVObject p2pObject = convertToP2PObject(p2pItemBean);
                p2pObject.save();
                return convertToP2PItemBean(p2pObject);
            }
        });
    }

    @Override
    public Task<P2PItemBean> deleteP2PItem(final P2PItemBean p2pItemBean) {
        return Task.callInBackground(new Callable<P2PItemBean>() {
            @Override
            public P2PItemBean call() throws Exception {
                AVObject p2pObject = convertToP2PObject(p2pItemBean);
                p2pObject.delete();
                return p2pItemBean;
            }
        });
    }

    @Override
    public Task<P2PItemBean> alterP2PItem(final P2PItemBean p2pItemBean) {
        return Task.callInBackground(new Callable<P2PItemBean>() {
            @Override
            public P2PItemBean call() throws Exception {
                AVObject p2pObject = convertToP2PObject(p2pItemBean);
                p2pObject.save();
                p2pObject.fetchIfNeeded();
                return convertToP2PItemBean(p2pObject);
            }
        });
    }

    /**
     * 将p2pItemBean转换为Avobject
     * <p/>
     * 注意该Avobject的数据并没有保持与服务器一致
     */
    private AVObject convertToP2PObject(P2PItemBean p2pItemBean) {
        AVObject p2pObject = new AVObject(LeancloudConstant.P2P_TABLE);
        p2pObject.put(LeancloudConstant.TITLE_COLUME, p2pItemBean.title);
        p2pObject.put(LeancloudConstant.PRICE_COLUME, p2pItemBean.price);
        p2pObject.put(LeancloudConstant.ADD_COLUME, p2pItemBean.address);
        p2pObject.put(LeancloudConstant.TEL_COLUME, p2pItemBean.tel);
        p2pObject.put(LeancloudConstant.DETAIL_COLUME, p2pItemBean.detail);
        p2pObject.put(LeancloudConstant.CATEGORY_COLUME, p2pItemBean.categoryBean.title);
        p2pObject.setObjectId(p2pItemBean.id);

        AVObject userObject = AVObject.createWithoutData(LeancloudConstant.USER_TABLE, p2pItemBean.userInfoBean.id);
        p2pObject.put(LeancloudConstant.USER_COLUME, userObject);

        AVObject imageObject = AVObject.createWithoutData(LeancloudConstant.IMAGE_TABLE, p2pItemBean.imageBean.id);
        p2pObject.put(LeancloudConstant.IMAGE_COLUME, imageObject);
        return p2pObject;
    }

    /**
     * 将Avobject转换为p2pItemBean
     */
    private P2PItemBean convertToP2PItemBean(AVObject p2pObject) throws AVException {
        p2pObject.getAVObject(LeancloudConstant.USER_COLUME).fetchIfNeeded();
        p2pObject.getAVObject(LeancloudConstant.IMAGE_COLUME).fetchIfNeeded();

        AVObject userObject = p2pObject.getAVObject(LeancloudConstant.USER_COLUME);
        UserInfoBean userInfoBean = UserInfoBean.generateBean(
                userObject.getString(LeancloudConstant.USER_COLUME)
                , null
                , userObject.getObjectId()
        );

        AVObject imageObject = p2pObject.getAVObject(LeancloudConstant.IMAGE_COLUME);
        ImageBean imageBean = ImageBean.generateImage(
                imageObject.getString(LeancloudConstant.IMAGE_COLUME)
                , ImageBean.ImageType.QINIUYUN
                , imageObject.getObjectId());

        return new P2PItemBean()
                .setTitle(p2pObject.getString(LeancloudConstant.TITLE_COLUME))
                .setPrice(p2pObject.getString(LeancloudConstant.PRICE_COLUME))
                .setAddress(p2pObject.getString(LeancloudConstant.ADD_COLUME))
                .setTel(p2pObject.getString(LeancloudConstant.TEL_COLUME))
                .setDetail(p2pObject.getString(LeancloudConstant.DETAIL_COLUME))
                .setUserInfoBean(userInfoBean)
                .setImageBean(imageBean)
                .setId(p2pObject.getObjectId())
                .setCategoryBean(new P2PCategoryBean().setTitle(p2pObject.getString(LeancloudConstant.CATEGORY_COLUME)))
                .setCreatedAt(p2pObject.getCreatedAt());
    }
}
