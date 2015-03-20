package com.qz.lifehelper.event;


import com.qz.lifehelper.entity.POIResult;

import java.util.List;

/**
 * Created by kohoh on 15/3/20.
 */
public class GetPOIResultEvent {

    public List<POIResult> poiResults;

    static public GetPOIResultEvent generateEvnet(List<POIResult> poiResults) {
        GetPOIResultEvent event = new GetPOIResultEvent();
        event.poiResults = poiResults;
        return event;
    }

}
