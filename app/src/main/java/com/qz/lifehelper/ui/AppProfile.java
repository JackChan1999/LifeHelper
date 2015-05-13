package com.qz.lifehelper.ui;

/**
 * 该类用于控制整个APP的行为
 */
public class AppProfile {

    /**
     * 数据来源
     */
    public enum DATE_SOURCE {
        //离线数据，在线数据
        OUTLINE, ONLINE,
    }

    /**
     * 数据来源
     * <p/>
     * 用于控制是采用离线数据还是在线数据
     */
    static public DATE_SOURCE dateSource = DATE_SOURCE.ONLINE;

}
