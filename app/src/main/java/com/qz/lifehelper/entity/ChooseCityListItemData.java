package com.qz.lifehelper.entity;

/**
 * ChooseCity列表中的Item数据需要实现该接口
 */
public interface ChooseCityListItemData {

    /**
     * 获取Item数据的类型 1. SECTION 分组标题 2. CITY 城市数据 3.FInD_LOCATION 定位
     */
    public ChooseCityListItemType getItemType();
}
