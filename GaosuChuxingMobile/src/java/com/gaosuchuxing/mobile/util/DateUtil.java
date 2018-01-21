/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.util;

import com.gaosuchuxing.mobile.common.Constant;
import com.gaosuchuxing.mobile.domain.OrderVO;
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

//    private static Calendar calendar = Calendar.getInstance();

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
    
    /*
     *  get delivery time
     */
    public static String getDeliveryTime(OrderVO order) {
        if (order == null || !order.getState() || order.getPayDate() == null)
            return "";
        
        if (order.getOrderStatus().equals(Constant.ORDER_STATUS.COMPLETED)) {
            return formatDate(order.getDeliveryDate(), "HH:mm");
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(order.getPayDate());
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            return formatDate(calendar.getTime(), "HH:mm");
        }
    }
    
    public static String getDeliveryTime() {
        Calendar calendar = Calendar.getInstance();
        
//        int yy = calendar.get(Calendar.YEAR);
//        int mm = calendar.get(Calendar.MONTH);
//        int dd = calendar.get(Calendar.DAY_OF_MONTH);
//        
//        calendar.set(yy, mm, dd, 11, 0);
//        
//        Date date = new Date();
//        
//        if (date.after(calendar.getTime())) {
//            return "下午06:00";
//        } else {
//            return "中午12:00";
//        }
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        
        String prefix = (calendar.get(Calendar.HOUR_OF_DAY) < 12) ? "上午" : "下午";        
        
        return prefix + " " + DateUtil.formatDate(calendar.getTime(), "hh:mm");
        
    }
    
    public static String getPredictTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        
        return DateUtil.formatDate(calendar.getTime(), "HH:mm") + "前";
        
    }
    
    public static Date getPredictDateTime(String time) {
        Calendar calendar = Calendar.getInstance();
        String[] times = time.split(":");
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = NumberUtil.strToInt(times[0], 0);
        int min = NumberUtil.strToInt(times[1], 0);
        calendar.set(yy, mm, dd, hour, min);
        return calendar.getTime();
    }
    
    public static Date getValidToDate(Date fromDate) {
        Calendar calendar = Calendar.getInstance();
        if (fromDate != null)
            calendar.setTime(fromDate);
        calendar.add(Calendar.MONTH, 6);
        return calendar.getTime();
    }
    
    public static boolean isOrderTime() {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        
        int yy = from.get(Calendar.YEAR);
        int mm = from.get(Calendar.MONTH);
        int dd = from.get(Calendar.DAY_OF_MONTH);
        
        from.set(yy, mm, dd, 8, 59);
        to.set(yy, mm, dd, 19, 1);
        
        Calendar now = Calendar.getInstance();
        
        return (from.before(now) && to.after(now)) || Constant.NO_ORDER_TIME;
//        return from.before(now) && to.after(now);
    }
    
}
