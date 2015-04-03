package com.qz.lifehelper.service;

/**
 * 该类存储的是聚合网的相关常量
 */
public class JuheConstant {

    static private final String DTYPE = "&dtype=json";
    static private final String PLANE_key = "06753a2070829c649753320a490c5b19";
    static public final String BASE_URL = "http://apis.juhe.cn";
    static private final String PLANE = "/plan";
    static public final String AIPORT = PLANE + "/city?key=" + PLANE_key + DTYPE;
    public static final String PLANE_INFO = PLANE + "/s2s?key=" + PLANE_key + DTYPE;
    public static final String QUERY_DATE_FORMAT_PATTERN = "yyyy'-'MM'-'dd";
    public static final String RESPONSE_DATE_FORMAT_PATTERN = "yyyy'-'MM'-'dd hh:mm";


    private static final String TRAIN_KEY = "781db56ed179991a4e62449a87dba8cd";
    private static final String TRAIN = "/train";
    public static final String TRAIN_STATION = TRAIN + "/station.list.php?key=" + TRAIN_KEY + DTYPE;
    public static final String TRAIN_INFO = TRAIN + "/yp?key=" + TRAIN_KEY + DTYPE;
}
