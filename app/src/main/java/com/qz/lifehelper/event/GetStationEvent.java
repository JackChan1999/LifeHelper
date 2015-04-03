package com.qz.lifehelper.event;

import com.qz.lifehelper.entity.TrainStationBean;

/**
 * 当设置类火车站时，会发送该事件
 * <p/>
 * 该事件由ChooseTrainStationFragment发出
 */
public class GetStationEvent {
    public TrainStationBean trainStationBean;

    static public GetStationEvent generateEvent(TrainStationBean trainStationBean) {
        GetStationEvent event = new GetStationEvent();
        event.trainStationBean = trainStationBean;
        return event;
    }
}
