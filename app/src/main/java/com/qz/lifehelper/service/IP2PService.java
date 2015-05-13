package com.qz.lifehelper.service;

import com.qz.lifehelper.entity.P2PCategoryBean;
import com.qz.lifehelper.entity.P2PItemBean;
import com.qz.lifehelper.entity.UserInfoBean;

import java.util.Date;
import java.util.List;

import bolts.Task;

/**
 * 在线交易市场服务器接口
 */
public interface IP2PService {

    /**
     * 获取P2P分类信息数据
     */
    public Task<List<P2PCategoryBean>> getP2PCategory();

    /**
     * 获取所有用户的P2P结果信息
     *
     * @param catergoryBean P2P类别
     * @param count         获取个数
     * @param after         用于分页，获取在个时间戳之后的数据。如果为null，则查询第一页信息。
     */
    public Task<List<P2PItemBean>> getP2PItem(final P2PCategoryBean catergoryBean, int count, Date after);

    /**
     * 添加p2p信息
     */
    public Task<P2PItemBean> addP2PItem(final P2PItemBean p2pItemBean);

    /**
     * 删除p2p信息
     */
    public Task<P2PItemBean> deleteP2PItem(final P2PItemBean p2pItemBean);

    /**
     * 修改p2p信息
     */
    public Task<P2PItemBean> alterP2PItem(final P2PItemBean p2pItemBean);

    /**
     * 获取p2p信息
     *
     * @param catergoryBean P2P类别
     * @param count         获取个数
     * @param after         用于分页，获取在个时间戳之后的数据
     * @param userInfoBean  要查询的用户
     */
    Task<List<P2PItemBean>> getP2PItem(P2PCategoryBean catergoryBean, int count, Date after, UserInfoBean userInfoBean);
}
