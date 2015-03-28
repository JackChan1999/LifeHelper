package com.qz.lifehelper.event;


import com.qz.lifehelper.entity.POIResultBean;

import java.util.List;

/**
 * Created by kohoh on 15/3/20.
 */
public class GetPOIResultEvent {

    public List<POIResultBean> poiResultBeans;

    static public GetPOIResultEvent generateEvnet(List<POIResultBean> poiResultBeans) {
        GetPOIResultEvent event = new GetPOIResultEvent();
        event.poiResultBeans = poiResultBeans;
        return event;
    }

}
