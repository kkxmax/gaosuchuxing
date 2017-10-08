/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /*
     * datetime format
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String CHINA_DATETIME_FORMAT = "yyyy年 M月 d日 HH:mm:ss";
    public static final String CHINA_DATE_FORMAT = "yyyy年 MM月 dd日";

    private static Calendar calendar = Calendar.getInstance();

    public static Date parseDate(String str, String datePattern) {
        Date date = new Date();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;
    }

    public static Date parseDate(String str) {
        return parseDate(str, DEFAULT_DATE_FORMAT);
    }

    public static Date parseDateTime(String str, String dateTimePattern) {
        return parseDate(str, dateTimePattern);
    }

    public static Date parseDateTime(String str) {
        return parseDateTime(str, DEFAULT_DATETIME_FORMAT);
    }

    public static String formatDate(Date date, String datePattern) {
        if (date == null)
            return "";
        
        return new SimpleDateFormat(datePattern).format(date);
    }

    public static String formatDate(Date date) {
        return formatDate(date, DEFAULT_DATE_FORMAT);
    }

    public static String formatDateTime(Date date, String dateTimePattern) {
        return formatDate(date, dateTimePattern);
    }

    public static String formatDateTime(Date date) {
        return formatDate(date, DEFAULT_DATETIME_FORMAT);
    }
    
    public static Date getOnlyDate(Date date) {
        return parseDate(formatDate(date));
    }
    
    public static Date timestampToDate(Timestamp timestamp) {
        if (timestamp == null)
            return null;
        else
            return new Date(timestamp.getTime());
    }
    
    /*
     * add date
     */
    public static Date addDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }
}
