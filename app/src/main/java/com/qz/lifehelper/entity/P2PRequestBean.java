package com.qz.lifehelper.entity;

import java.io.Serializable;

/**
 * 封装了前往P2Activity的请求参数
 */
public class P2PRequestBean implements Serializable {

    public P2PCatergoryBean category;


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
    }

    public FragmentType fragmentType;

    public P2PRequestBean setCategory(P2PCatergoryBean category) {
        this.category = category;
        return this;
    }

    public P2PRequestBean setFragmentType(FragmentType fragmentType) {
        this.fragmentType = fragmentType;
        return this;
    }
}
