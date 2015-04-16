package com.qz.lifehelper.entity;

import java.io.Serializable;

/**
 * 分装的是P2P的分类信息
 */
public class P2PCatergoryBean implements Serializable {
    public String category;

    public P2PCatergoryBean setCategory(String category) {
        this.category = category;
        return this;
    }
}
