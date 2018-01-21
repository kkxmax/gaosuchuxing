/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.common;

public class WxConfig {
    public static final String APP_ID = "wx043d494f2a4940b5";
    public static final String APP_SECRET = "79efbd6b8cc151585eeb0e0abe3a12f0";
    public static final String PARTNER = "1485014222";
    public static final String PARTNER_KEY = "qwertyuiopasdfghjklzxcvbnm123456";
    
    public static final String AUTH_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String ACCESS_TOKEN_URL_CGI = "https://api.weixin.qq.com/cgi-bin/token";
    public static final String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=";
    public static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";
//    public static final String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info";
    
    public static final String ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static String NOTIFY_URL = "http://www.gaodkj.com/GaosuChuxingMobile/wxpayNotify";
}
