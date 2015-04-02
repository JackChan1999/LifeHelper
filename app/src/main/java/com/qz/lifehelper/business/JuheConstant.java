package com.qz.lifehelper.business;

/**
 * Created by kohoh on 15/4/2.
 */
public class JuheConstant {

    static public final String DTYPE = "&dtype=json";
    static public final String PLANE_key = "06753a2070829c649753320a490c5b19";
    static public final String BASE_URL = "http://apis.juhe.cn";
    static public final String PLANE = "/plan";
    static public final String AIPORT = PLANE + "/city?key=" + PLANE_key + DTYPE;
    public static final String PLANE_INFO = PLANE + "/s2s?key=" + PLANE_key + DTYPE;
    public static final String QUERY_DATE_FORMAT_PATTERN = "yyyy'-'MM'-'dd";
    public static final String RESPONSE_DATE_FORMAT_PATTERN = "yyyy'-'MM'-'dd hh:mm";
}
