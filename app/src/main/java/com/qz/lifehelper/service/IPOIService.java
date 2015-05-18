package com.qz.lifehelper.service;

import com.qz.lifehelper.entity.CityBean;
import com.qz.lifehelper.entity.POICategoryBean;
import com.qz.lifehelper.entity.POIResultBean;
import com.qz.lifehelper.entity.UserInfoBean;

import java.util.Date;
import java.util.List;

import bolts.Task;

/**
 * 周边信息服务器接口
 */
public interface IPOIService {
    /**
     * 获取POI数据
     *
     * @param cityBean     获取数据的城市。如果为null，则获取全部数据
     * @param categoryBean 获取数据的分类。如果为null，则获取全部数据
     * @param count        获取数据每页的个数
     * @param after        最后一个数据的创建时间。用于分页，如果为null，则加载第一页
     * @param userInfoBean 获取数据属于的用户。如果为null，则获取全部数据
     */
    public Task<List<POIResultBean>> getPOIItems(final CityBean cityBean, final POICategoryBean categoryBean, final int count, final Date after, final UserInfoBean userInfoBean);

    /**
     * 添加POI信息
     */
    public Task<POIResultBean> addPOIItem(final POIResultBean poiItemBean);

    /**
     * 修改POI信息
     */
    public Task<POIResultBean> alterPOIItem(final POIResultBean poiItemBean);

    /**
     * 删除POI信息
     */
    public Task<POIResultBean> deletePOIItem(final POIResultBean poiItemBean);
}
