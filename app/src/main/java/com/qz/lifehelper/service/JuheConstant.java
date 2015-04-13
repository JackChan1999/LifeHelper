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

    static private final String PLANE_key = "06753a2070829c649753320a490c5b19";
    static private final String PLANE = "/plan";
    static public final String AIPORT = PLANE + "/city?key=" + PLANE_key + DTYPE;
    public static final String PLANE_INFO = PLANE + "/s2s?key=" + PLANE_key + DTYPE;

    private static final String TRAIN_KEY = "781db56ed179991a4e62449a87dba8cd";
    private static final String TRAIN = "/train";
    public static final String TRAIN_STATION = TRAIN + "/station.list.php?key=" + TRAIN_KEY + DTYPE;
    public static final String TRAIN_INFO = TRAIN + "/yp?key=" + TRAIN_KEY + DTYPE;

    private static final String BUS_KEY = "8c12674c63e8804a8b71b748c9392687";
    private static final String BUS = "/bus";
    public static final String BUS_INFO = BUS + "/query_ab?key=" + BUS_KEY + DTYPE;
}
