package com.qz.lifehelper.service;

/**
 * 该类存储的是聚合网的相关常量
 */
public class JuheConstant {

    static public final String BASE_URL = "http://apis.juhe.cn";
    static public final String BASE_URL2 = "http://op.juhe.cn/onebox";
    static private final String DTYPE = "&dtype=json";

    public static final String QUERY_DATE_FORMAT_PATTERN = "yyyy'-'MM'-'dd";
    public static final String RESPONSE_DATE_FORMAT_PATTERN = "yyyy'-'MM'-'dd hh:mm";

    static private final String PLANE_key = "16ea998d117e8396db8ec655bd75d38f";
    static private final String PLANE = "/plan";
    static public final String AIPORT = PLANE + "/city?key=" + PLANE_key + DTYPE;
    public static final String PLANE_INFO = PLANE + "/bc?key=" + PLANE_key + DTYPE;

    private static final String TRAIN_KEY = "48477366563bf2e6539ba7d3078914c7";
    private static final String TRAIN = "/train";
    public static final String TRAIN_STATION = TRAIN + "/station.list.php?key=" + TRAIN_KEY + DTYPE;
    public static final String TRAIN_INFO = TRAIN + "/yp?key=" + TRAIN_KEY + DTYPE;

    private static final String BUS_KEY = "2f9d0d99e9a2624ec351118b237ac023";
    private static final String BUS = "/bus";
    public static final String BUS_INFO = BUS + "/query_ab?key=" + BUS_KEY + DTYPE;
}
