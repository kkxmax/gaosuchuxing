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
    
    public static final class SHOP_KIND {
        public static final String COMPANY = "company";
        public static final String SERVICE = "service";
    }
    
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
    
    public static final class GOODS_STATUS {
        public static final String SET_ON = "已上架";
        public static final String SET_OFF = "未上架";
    }
    
    public static final class ORDER_STATUS {
        public static final String WAITING = "待配送"; 
        public static final String SHIPPING = "配送中"; 
        public static final String COMPLETED = "已完成"; 
    }
    
    public static final class ACTIVITY_STATUS {
        public static final String READY = "未开始"; 
        public static final String IN_PROGRESS = "进行中"; 
        public static final String FINISHED = "已结束"; 
    }
    
    public static final class ACTIVITY_TYPE {
        public static final String UNLIMITED = "unlimited"; 
        public static final String FULL = "full";
        public static final String ALL = "-1";
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
    
    public static final String[] COMPANY_SHOP_COLUMNS = new String[] {
        "shops.name", "shop_kinds.name", "stations.name", "districts.name", "shops.start_fee", "shop.shipping_fee", "stations.reg_date"
    };
    
    public static final String[] SERVICE_SHOP_COLUMNS = new String[] {
        "shops.name", "shop_kinds.name", "stations.name", "districts.name", "stations.reg_date"
    };
    
    public static final String[] SHOP_KIND_COLUMNS = new String[] {
        "shop_kinds.name"
    };
    
    public static final String[] GOODS_KIND_COLUMNS = new String[] {
        "goods_kinds.name", "shop_kinds.name" 
    };
    
    public static final String[] ORDER_COLUMNS = new String[] {
        "orders.order_num", "users.name", "users.user_id", "orders.amount", "orders.status", "delivers.name", "orders.pay_date", "orders.shipping_date", "orders.delivery_date"
    };
    
    public static final String[] STAT_DELIVER_COLUMNS = new String[] {
        "delivers.deliver_id", "delivers.name", "SUM(order_details.qty)"
    };
    
    public static final String[] ACTIVITY_COLUMNS = new String[] {
        "num", "name", "activity_amount", "coupon_qty", "start_date", "status", "reg_date"
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
    
    public static final String[] GOODS_COLUMNS = new String[] {
        "goods.id", "goods.num", "goods.name", "shops.name", "shop_kinds.name", "goods_kinds.name", "goods.price", "goods.status", "goods.reg_date"
    };
    
    public static final String[] ACTIVITY_NOTICE_COLUMNS = new String[] {
        "activity_notices.id", "activity_notices.name", "activity_notices.status", "activity_notices.reg_date"
    };
    
    public static final class ACTIVITY_NOTICE_STATUS {
        public static final String PUBLISH_ON = "发布";
        public static final String PUBLISH_OFF = "未发布";
    };

    public static final String[] USER_COUPON_COLUMNS = new String[] {
        "user_coupons.coupon_id", "user_coupons.qty", "users.user_id", "user_coupons.status", "user_coupons.used_date", "user_coupons.reg_date"
    };
    
    public static final class USER_COUPON_STATUS {
        public static final String NOT_USED = "未使用";
        public static final String USED = "已使用";
        public static final String EXPIRED = "已过期";
    };
}
