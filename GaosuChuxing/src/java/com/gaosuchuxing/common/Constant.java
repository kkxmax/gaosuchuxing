/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.common;

public class Constant {
    public static final class SESSION_INFO {
        public static final String CAPTCHA_KEY = "CAPTCHA_KEY";        
        public static final String LOGIN_USER = "LOGIN_USER";
    }
    
    public static final class ATTRIBUTE {
        public static final String ERROR_MESSAGE = "ERROR_MESSAGE";
        public static final String WARNING_MESSAGE = "WARNING_MESSAGE";
    }
    
    public static String GOODS_IMAGE_DIR = "goods_images";
    public static String AVATAR_IMAGE_DIR = "avatar_images";
    public static String STATION_IMAGE_DIR = "station_images";
    public static String SHOP_IMAGE_DIR = "shop_images";
    public static String ACTIVITY_IMAGE_DIR = "activity_images";
    public static String IMAGE_DIR = "gaosuchuxing_images";
    
    public static final class PERMISSION {
        public static final String USER = "用户管理";
        public static final String DELIVER = "配送员管理";
        public static final String AREA = "服务区管理";
        public static final String STATION = "餐饮店管理";
        public static final String GOODS = "商品管理";
        public static final String ORDER = "订单管理";
        public static final String COUPON = "优惠券管理";
        public static final String FEEDBACK = "意见反馈";
        public static final String NOTIFICATION = "系统消息";
        public static final String SYSTEM = "系统管理";
        public static final String ADMIN = "运营人员管理";
        public static final String GROUP = "角色管理";
        public static final String PASSWORD = "修改密码";
    }
    
    public static final String[] MANAGER_COLUMNS = new String[] {
        "managers.admin_id", "managers.name", "groups.name", "managers.reg_date"
    };
    
    public static final String[] GROUP_COLUMNS = new String[] {
        "groups.name"
    };
    
    public static final String[] STATION_COLUMNS = new String[] {
        "stations.name", "districts.name", "delivers.name", "stations.status", "stations.reg_date"
    };
    
    public static final String[] FEEDBACK_COLUMNS = new String[] {
        "feedbacks.id", "feedbacks.comment", "feedbacks.reg_date", "feedbacks.status"
    };
    
    public static final String[] DELIVER_COLUMNS = new String[] {
        "delivers.deliver_id", "delivers.name", "delivers.tel_no", "delivers.reg_date"
    };
    
    public static final String[] USER_COLUMNS = new String[] {
        "users.user_id", "users.name", "users.reg_date", "users.order_count", "users.order_amount"
    };
}
