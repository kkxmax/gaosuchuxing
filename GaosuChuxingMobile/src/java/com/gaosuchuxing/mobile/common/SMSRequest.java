/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.common;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.gaosuchuxing.mobile.util.CommonUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class SMSRequest {
    public static final String ACCOUNT_SID = "8aaf07085e4aee52015e4bd30cd40028";
    public static final String AUTH_TOKEN = "d394f006091f43f3892fecfe02d9a4bf";
    public static final String APP_ID = "8aaf07085e4aee52015e4bd30d18002c";
    public static final String APP_TOKEN = "675e1881aeb9785871d22ef65142b0a6";
    public static final String SIGNUP_TEMPLATE_ID = "202824";    
    public static final String SHIPPING_TEMPLATE_ID = "215369";
    
    private static final int CODE_LENGTH = 6;
    
    public static String getVerifyCode() {
        Date currentTime = new Date();
        Random RandomData = new Random();
        
        String verifyCode = Long.toString(Math.abs(RandomData.nextLong() + currentTime.getTime()));
        verifyCode = verifyCode.substring(verifyCode.length() - CODE_LENGTH);
        return verifyCode;
    }
    
    public static String sendSMS(String phoneNumber, String templateId, String code, String expire) {
        HashMap<String, Object> result = null;

        //初始化SDK
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();

        //******************************注释*********************************************
        //*初始化服务器地址和端口                                                       *
        //*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
        //*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
        //*******************************************************************************
        restAPI.init("app.cloopen.com", "8883");

        //******************************注释*********************************************
        //*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
        //*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
        //*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
        //*******************************************************************************
        restAPI.setAccount(ACCOUNT_SID, AUTH_TOKEN) ;


        //******************************注释*********************************************
        //*初始化应用ID                                                                 *
        //*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
        //*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
        //*******************************************************************************
        restAPI.setAppId(APP_ID);


        //******************************注释****************************************************************
        //*调用发送模板短信的接口发送短信                                                                  *
        //*参数顺序说明：                                                                                  *
        //*第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号                          *
        //*第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。    *
        //*系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
        //*第三个参数是要替换的内容数组。																														       *
        //**************************************************************************************************

        //**************************************举例说明***********************************************************************
        //*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
        //*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});																		  *
        //*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
        //*********************************************************************************************************************
        
        if (templateId.equals(SIGNUP_TEMPLATE_ID))
            result = restAPI.sendTemplateSMS(phoneNumber, SIGNUP_TEMPLATE_ID, new String[]{code, expire + "分钟"});
        else
            result = restAPI.sendTemplateSMS(phoneNumber, SHIPPING_TEMPLATE_ID, null);

        System.out.println("SDKTestGetSubAccounts result=" + result);
        
        if ("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet) {
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }

//            return "success";
        } else {
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
        }
        
        return result.toString();
    }
}
