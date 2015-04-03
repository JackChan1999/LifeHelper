package com.qz.lifehelper.event;

import com.qz.lifehelper.entity.TrainStationBean;

/**
 * Created by kohoh on 15/4/3.
 */
public class GetStationEvent {
    public TrainStationBean trainStationBean;

    static public GetStationEvent generateEvent(TrainStationBean trainStationBean) {
        GetStationEvent event = new GetStationEvent();
        event.trainStationBean = trainStationBean;
        return event;
    }
}
