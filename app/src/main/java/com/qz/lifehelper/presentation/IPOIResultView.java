package com.qz.lifehelper.presentation;

import com.qz.lifehelper.entity.City;

/**
 * Created by kohoh on 15/3/20.
 */
public interface IPOIResultView {

    /**
     * 当开始加载数据时，该方法被调用
     */
    public void starLoadPOIData();

    /**
     * 当成功加载到数据时，该方法被调用
     */
    public void loadPOIDataSuccess();


    /**
     *  当加载数据失败时，该方法被调用
     */
    public void loadPOIDataFial();
}
