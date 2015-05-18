package com.qz.lifehelper.service;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.qz.lifehelper.business.AuthenticationBusiness;
import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.ImageBean;
import com.qz.lifehelper.entity.POICategoryBean;
import com.qz.lifehelper.entity.POIResultBean;
import com.qz.lifehelper.entity.UserInfoBean;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Task;

/**
 * POIde线上服务器
 * <p/>
 * 使用的是Leancloud
 */
@EBean
public class POIOnlineService implements IPOIService {

    @Bean
    AuthenticationBusiness authenticationBusiness;

    /**
     * 获取POI数据
     *
     * @param cityBean     获取数据的城市。如果为null，则获取全部数据
     * @param categoryBean 获取数据的分类。如果为null，则获取全部数据
     * @param count        获取数据每页的个数
     * @param after        最后一个数据的创建时间。用于分页，如果为null，则加载第一页
     * @param userInfoBean 获取数据属于的用户。如果为null，则获取全部数据
     */
    public Task<List<POIResultBean>> getPOIItems(final CityBean cityBean, final POICategoryBean categoryBean, final int count, final Date after, final UserInfoBean userInfoBean) {

        return Task.callInBackground(new Callable<List<POIResultBean>>() {
            @Override
            public List<POIResultBean> call() throws Exception {
                AVQuery<AVObject> query = new AVQuery<AVObject>(LeancloudConstant.POI_TABLE);

                if (cityBean != null) {
                    query.whereEqualTo(LeancloudConstant.CITY_COLUME, cityBean.cityName);
                }
                if (categoryBean != null) {
                    query.whereEqualTo(LeancloudConstant.CATEGORY_COLUME, categoryBean.categotyName);
                }
                query.limit(count);
                if (after != null) {
                    query.whereLessThan(LeancloudConstant.CREATED_AT_COLUME, after);
                }
                if (!authenticationBusiness.isSuperUser(userInfoBean) && userInfoBean != null) {
                    AVObject avUser = AVObject.createWithoutData(LeancloudConstant.USER_TABLE, userInfoBean.id);
                    query.whereEqualTo(LeancloudConstant.USER_COLUME, avUser);
                }
                query.orderByDescending(LeancloudConstant.CREATED_AT_COLUME);

                List<AVObject> poiObjects = query.find();
                List<POIResultBean> poiItemBeans = new ArrayList<POIResultBean>();
                for (AVObject poiObject : poiObjects) {
                    POIResultBean poiItemBean = convertToPOIItemBean(poiObject);
                    poiItemBeans.add(poiItemBean);
                }

                return poiItemBeans;
            }
        });
    }

    /**
     * 添加POI信息
     */
    public Task<POIResultBean> addPOIItem(final POIResultBean poiItemBean) {
        return Task.callInBackground(new Callable<POIResultBean>() {
            @Override
            public POIResultBean call() throws Exception {
                AVObject poiObject = convetToPOIObject(poiItemBean);
                poiObject.save();
                return convertToPOIItemBean(poiObject);
            }
        });
    }

    private AVObject convetToPOIObject(POIResultBean poiItemBean) {
        AVObject poiObject = new AVObject(LeancloudConstant.POI_TABLE);
        poiObject.put(LeancloudConstant.TITLE_COLUME, poiItemBean.title);
        poiObject.put(LeancloudConstant.TEL_COLUME, poiItemBean.tel);
        poiObject.put(LeancloudConstant.ADD_COLUME, poiItemBean.address);
        poiObject.put(LeancloudConstant.DETAIL_COLUME, poiItemBean.detail);
        AVObject imageObject = AVObject.createWithoutData(LeancloudConstant.IMAGE_TABLE, poiItemBean.imageBean.id);
        poiObject.put(LeancloudConstant.IMAGE_COLUME, imageObject);
        AVObject userObject = AVObject.createWithoutData(LeancloudConstant.USER_TABLE, poiItemBean.userInfoBean.id);
        poiObject.put(LeancloudConstant.USER_COLUME, userObject);
        poiObject.put(LeancloudConstant.CATEGORY_COLUME, poiItemBean.poiCategoryBean.categotyName);
        poiObject.put(LeancloudConstant.CITY_COLUME, poiItemBean.cityBean.cityName);

        poiObject.setObjectId(poiItemBean.id);

        return poiObject;
    }

    private POIResultBean convertToPOIItemBean(AVObject poiObject) throws AVException {
        AVObject imageObject = poiObject.getAVObject(LeancloudConstant.IMAGE_COLUME).fetchIfNeeded();
        AVObject userObject = poiObject.getAVObject(LeancloudConstant.USER_COLUME).fetchIfNeeded();

        ImageBean imageBean = ImageBean.generateImage(
                imageObject.getString(LeancloudConstant.IMAGE_COLUME)
                , ImageBean.ImageType.QINIUYUN
                , imageObject.getObjectId());

        UserInfoBean userInfoBean = UserInfoBean.generateBean(
                userObject.getString(LeancloudConstant.USERNAME_COLUME)
                , null
                , userObject.getObjectId());

        POIResultBean poiItemBean = new POIResultBean()
                .setId(poiObject.getObjectId())
                .setTitle(poiObject.getString(LeancloudConstant.TITLE_COLUME))
                .setTel(poiObject.getString(LeancloudConstant.TEL_COLUME))
                .setAddress(poiObject.getString(LeancloudConstant.ADD_COLUME))
                .setDetail(poiObject.getString(LeancloudConstant.DETAIL_COLUME))
                .setImageBean(imageBean)
                .setPoiCategoryBean(POICategoryBean.generate(poiObject.getString(LeancloudConstant.CATEGORY_COLUME)))
                .setUserInfoBean(userInfoBean)
                .setCreatedAt(poiObject.getCreatedAt())
                .setType(POIResultBean.TYPE.LEANCLOUD)
                .setCityBean(CityBean.generateCity(poiObject.getString(LeancloudConstant.CITY_COLUME)));

        return poiItemBean;
    }

    /**
     * 修改POI信息
     */
    public Task<POIResultBean> alterPOIItem(final POIResultBean poiItemBean) {
        return Task.callInBackground(new Callable<POIResultBean>() {
            @Override
            public POIResultBean call() throws Exception {
                AVObject poiObject = convetToPOIObject(poiItemBean);
                poiObject.save();
                poiObject.fetchIfNeeded();
                return convertToPOIItemBean(poiObject);
            }
        });
    }

    /**
     * 删除POI信息
     */
    public Task<POIResultBean> deletePOIItem(final POIResultBean poiItemBean) {
        return Task.callInBackground(new Callable<POIResultBean>() {
            @Override
            public POIResultBean call() throws Exception {
                AVObject poiObject = convetToPOIObject(poiItemBean);
                poiObject.delete();
                return poiItemBean;
            }
        });
    }
}
