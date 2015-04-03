package com.qz.lifehelper.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 和日期时间相关的工具
 */
public class DateUtil {
    /**
     * 获取制定格式的当前的日期
     *
     * @param dateFormatPattern 日期格式
     */
    static public String getCurrentDate(String dateFormatPattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatPattern);
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    /**
     * 将Date日期转换为对应的格式。
     *
     * @param dateFormatPattern 日期格式
     */
    static public String formatDate(String dateFormatPattern, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatPattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 将一段字符形势的日期转换为Date对象
     * <p/>
     * 如果转换过程中出现了错误，则会返回当前时间的Date
     *
     * @param dateFormatPattern 日期格式
     */
    static public Date parseDate(String dateFormatPattern, String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatPattern);
        Date dateParsed = new Date();
        try {
            dateParsed = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return dateParsed;
        }
    }
}
