package com.qz.lifehelper.entity;

import java.io.Serializable;

/**
 * 封装了前往P2Activity的请求参数
 */
public class P2PRequestBean implements Serializable {

    /**
     * P2PListFragment需要这个参数来加载制定分类的数据
     */
    public P2PCategoryBean category;

    /**
     * P2PDetailFragment需要该参数来加载p2p详细信息
     */
    public P2PItemBean p2PItemBean;

    /**
     * P2P的fragment类型
     */
    public enum FragmentType implements Serializable {
        //类别目录
        P2P_CATEGORY
        //结果列表
        , P2P_LIST
        //结果详情
        , P2P_DETAIL
        //新增P2P
        , P2P_ADD
        //修改P2P
        , P2P_ALTER
        //个人商品页面
        , PERSONAL_P2P_LIST
    }

    /**
     * 通过该参数确定要打开的fragment
     */
    public FragmentType fragmentType;

    public P2PRequestBean setCategory(P2PCategoryBean category) {
        this.category = category;
        return this;
    }

    public P2PRequestBean setFragmentType(FragmentType fragmentType) {
        this.fragmentType = fragmentType;
        return this;
    }

    public P2PRequestBean setP2PItemBean(P2PItemBean p2PItemBean) {
        this.p2PItemBean = p2PItemBean;
        return this;
    }
}
