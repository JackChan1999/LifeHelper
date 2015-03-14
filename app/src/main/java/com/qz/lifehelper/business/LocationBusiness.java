package com.qz.lifehelper.business;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by kohoh on 15-3-14.
 */
public class LocationBusiness {

    public static final String TAG = LocationBusiness.class.getSimpleName() + "TAG";

    private Context context;

    public LocationBusiness(Context context) {
        this.context = context;
    }

    /**
     * 获取当前位置
     *
     * @param locationListener 回调接口，当获取到位置信息后，将会被毁掉
     */
    //TODO 不应该使用BDLocationListener
    //TODO 是否应该使用毁掉到接口方式
    public void getCurrentLocation(final BDLocationListener locationListener) {
        final LocationClient locationClient = new LocationClient(context);

        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setIsNeedAddress(true);
        locationClientOption.setScanSpan(10001);
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        locationClient.setLocOption(locationClientOption);

        BDLocationListener wrapperListener = new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (bdLocation != null && bdLocation.getCity() != null && bdLocation.getCityCode() != null
                        && !bdLocation.getCity().equals("") && !bdLocation.getCity().equals("null") &&
                        !bdLocation.getCityCode().equals("") && !bdLocation.getCityCode().equals("null")) {

                    Log.d("TAG", "get current location");
                    locationClient.stop();
                    locationListener.onReceiveLocation(bdLocation);
                }
            }
        };
        locationClient.registerLocationListener(wrapperListener);

        locationClient.start();
    }
}
