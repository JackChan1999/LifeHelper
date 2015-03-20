package com.qz.lifehelper.presentation;

import com.qz.lifehelper.entity.City;

/**
 * Created by kohoh on 15/3/20.
 */
public interface IPOIResultView {

    public void setCurrentLocation(City currentLoaction);

    public void starLoadPOIData();

    public void loadPOIDataSuccess();

    public void loadPOIDataFial();
}
