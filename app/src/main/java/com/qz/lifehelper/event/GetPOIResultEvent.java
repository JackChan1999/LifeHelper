package com.qz.lifehelper.event;


import com.qz.lifehelper.entity.POIResultBean;

import java.util.List;

/**
 * 获取POI搜索结果事件
 *
 * 当成功获取到POI搜索结果，会发送该事件
 */
public class GetPOIResultEvent {

    public List<POIResultBean> poiResultBeans;

    static public GetPOIResultEvent generateEvnet(List<POIResultBean> poiResultBeans) {
        GetPOIResultEvent event = new GetPOIResultEvent();
        event.poiResultBeans = poiResultBeans;
        return event;
    }

}
