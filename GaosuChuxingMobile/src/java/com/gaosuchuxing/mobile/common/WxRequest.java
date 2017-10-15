/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.common;

import com.gaosuchuxing.mobile.util.CommonUtil;
import com.gaosuchuxing.mobile.util.DateUtil;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.shopxx.plugin.weixin.utils.GetWxOrderno;
import static net.shopxx.plugin.weixin.utils.GetWxOrderno.doXMLParse;
import net.shopxx.plugin.weixin.utils.RequestHandler;
import net.shopxx.plugin.weixin.utils.Sha1Util;
import net.shopxx.plugin.weixin.utils.TenpayUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.simple.JSONObject;

public class WxRequest {
    
    public static String getAuthCodeUrl(String redirectUrl) {
        String wxAuthCodeUrl = WxConfig.AUTH_CODE_URL;
        wxAuthCodeUrl += "?appid=" + WxConfig.APP_ID;
        wxAuthCodeUrl += "&redirect_uri=" + redirectUrl;
        wxAuthCodeUrl += "&response_type=code";
//            wxAuthCodeUrl += "&scope=snsapi_base";
        wxAuthCodeUrl += "&scope=snsapi_userinfo";
        wxAuthCodeUrl += "&state=STATE#wechat_redirect";
        
        return wxAuthCodeUrl;
    }    
    
    public static String getAccessToken(String authCode) {
        try {
            HttpClient client = new HttpClient();
            
            if (Constant.DEV_MODE)
                client.getHostConfiguration().setProxy("10.70.250.242", 8080);
            
            PostMethod post = new PostMethod(WxConfig.ACCESS_TOKEN_URL);
            post.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            
            NameValuePair appid = new NameValuePair("appid", WxConfig.APP_ID);
            NameValuePair secret = new NameValuePair("secret", WxConfig.APP_SECRET);
            NameValuePair code = new NameValuePair("code", authCode);
            NameValuePair grant_type = new NameValuePair("grant_type", "authorization_code");
            
            post.setRequestBody(new NameValuePair[] {appid, secret, code, grant_type});
            
            int responseCode = client.executeMethod(post);
            String responseBody = post.getResponseBodyAsString();
            
//            String url = WxConfig.ACCESS_TOKEN_URL + "?grant_type=client_credential";
//            url += "&appid=" + WxConfig.APP_ID;
//            url += "&secret=" + WxConfig.APP_SECRET;
//
//            GetMethod get = new GetMethod(url);
//            
//            int responseCode = client.executeMethod(get);
//            String responseBody = get.getResponseBodyAsString();
            
            if(responseCode == 200)
                return responseBody;
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public static String getUserInfo(String accessToken, String openId) {
        try {
            HttpClient client = new HttpClient();
            
            if (Constant.DEV_MODE)
                client.getHostConfiguration().setProxy("10.70.250.242", 8080);
            
            PostMethod post = new PostMethod(WxConfig.USER_INFO_URL);
            
            post.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            
            NameValuePair access_token = new NameValuePair("access_token", accessToken);
            NameValuePair openid = new NameValuePair("openid", openId);
            
            post.setRequestBody(new NameValuePair[] {access_token, openid});
            
            int responseCode = client.executeMethod(post);
//            String responseBody = post.getResponseBodyAsString();
//            String responseBody = new String(post.getResponseBody(), "UTF-8");
            String responseBody;
//            System.out.println("chartset: " + post.getResponseCharSet());
            if (post.getResponseCharSet().equalsIgnoreCase(Constant.ISO_8859_1)) {
                responseBody = CommonUtil.charsetEncoding(post.getResponseBodyAsString());
            } else {
                responseBody = post.getResponseBodyAsString();
            }

//            String url = WxConfig.USER_INFO_URL + "?access_token=" + accessToken;
//            url += "&openid=" + openId;
//
//            GetMethod get = new GetMethod(url);
//            
//            int responseCode = client.executeMethod(get);
//            String responseBody;
//            if (get.getResponseCharSet().equalsIgnoreCase(Constant.ISO_8859_1)) {
//                responseBody = CommonUtil.charsetEncoding(get.getResponseBodyAsString());
//            } else {
//                responseBody = get.getResponseBodyAsString();
//            }
            
//            String responseBody = new String(post.getResponseBody(), "UTF-8");
            
            if(responseCode == 200)
                return responseBody;
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public static JSONObject getWxPrepayInfo(HttpServletRequest request, HttpServletResponse response, String goodsName, int amount) {
        String currTime = TenpayUtil.getCurrTime(); 
        //8位日期  
        String strTime = currTime.substring(8, currTime.length());  
        //四位随机数  
        String strRandom = TenpayUtil.buildRandom(4) + "";  
        //10位序列号,可以自行调整。  
        String strReq = strTime + strRandom; 
        
        //商户号  
        String mch_id = WxConfig.PARTNER; 
        
        //设备号   非必输  
        String device_info="";  
        
        //随机数   
        String nonce_str = strReq;  
        
        String body = goodsName;
        
        String total_fee = String.valueOf(amount);
        
        String spbill_create_ip;
        if (!Constant.DEV_MODE)
            spbill_create_ip = request.getRemoteAddr();
        else
            spbill_create_ip = "60.21.159.210";
        
        String notify_url = WxConfig.NOTIFY_URL;//微信异步通知地址           
//        String trade_type = "APP";//app支付必须填写为APP  
        String trade_type = "JSAPI";//app支付必须填写为APP  
        
        //商户订单号  
        String out_trade_no = DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS");//订单编号加时间戳  
        
        //对以下字段进行签名  
        SortedMap<String, String> packageParams = new TreeMap<String, String>();  
        
        packageParams.put("appid", WxConfig.APP_ID);    
        packageParams.put("attach", body);   
        packageParams.put("body", body);    
        packageParams.put("mch_id", mch_id);      
        packageParams.put("nonce_str", nonce_str);    
        packageParams.put("notify_url", notify_url);    
        packageParams.put("openid", Constant.wxOpenId);
        packageParams.put("out_trade_no", out_trade_no);      
        packageParams.put("spbill_create_ip", spbill_create_ip);   
        packageParams.put("total_fee", total_fee);  
        packageParams.put("trade_type", trade_type);
        
        RequestHandler reqHandler = new RequestHandler(request, response);  
        reqHandler.init(WxConfig.APP_ID, WxConfig.APP_SECRET, WxConfig.PARTNER_KEY);        
        String sign = reqHandler.createSign(packageParams);//获取签名 
        
        String xml="<xml>"+  
                    "<appid>"+WxConfig.APP_ID+"</appid>"+  
                    "<attach>"+body+"</attach>"+  
                    "<body><![CDATA["+body+"]]></body>"+  
                    "<mch_id>"+mch_id+"</mch_id>"+  
                    "<nonce_str>"+nonce_str+"</nonce_str>"+  
                    "<notify_url>"+notify_url+"</notify_url>"+  
                    "<openid>"+Constant.wxOpenId+"</openid>"+  
                    "<out_trade_no>"+out_trade_no+"</out_trade_no>"+  
                    "<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+  
                    "<total_fee>"+total_fee+"</total_fee>"+  
                    "<trade_type>"+trade_type+"</trade_type>"+  
                    "<sign>"+sign+"</sign>"+  
                    "</xml>";  
        
         String allParameters = "";  
        try {  
            allParameters =  reqHandler.genPackage(packageParams);  
         } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        String createOrderURL = WxConfig.ORDER_URL;
        String prepay_id="";
        
        try {  
//            prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);  

            System.out.println("xml: " + xml);

            HttpClient client = new HttpClient();
            
            if (Constant.DEV_MODE)
                client.getHostConfiguration().setProxy("10.70.250.242", 8080);
            
            PostMethod post = new PostMethod(createOrderURL);
            
            post.setRequestHeader("Content-Type", "text/xml;charset=utf-8");
            
            post.setRequestEntity(new StringRequestEntity(xml, "text/xml", "utf-8"));
            
            int responseCode = client.executeMethod(post);
            
            String responseBody = "";
            
            if (responseCode == 200) {                
                if (post.getResponseCharSet().equalsIgnoreCase(Constant.ISO_8859_1)) {
                    responseBody = CommonUtil.charsetEncoding(post.getResponseBodyAsString());
                } else {
                    responseBody = post.getResponseBodyAsString();
                }
            }
            
            System.out.println("prepay msg: " + responseBody);
            
            if (responseBody.indexOf("FAIL") == -1) {
                Map map = doXMLParse(responseBody);
                String return_code  = (String) map.get("return_code");
                prepay_id  = (String) map.get("prepay_id");
                
                System.out.println("prepayid: " + prepay_id);
            }
            
            if(!CommonUtil.isEmptyStr(prepay_id)) {  
                SortedMap<String, String> finalpackage = new TreeMap<String, String>();  
                String timestamp = Sha1Util.getTimeStamp();  
//                String packageValue = "Sign=WXPay";
//                finalpackage.put("appid", WxConfig.APP_ID);    
//                finalpackage.put("timestamp", timestamp);    
//                finalpackage.put("noncestr", nonce_str);    
//                finalpackage.put("partnerid", WxConfig.PARTNER);   
//                finalpackage.put("package", packageValue);                
//                finalpackage.put("package", prepay_id);                
//                finalpackage.put("prepayid", prepay_id);    
//                String finalsign = reqHandler.createSign(finalpackage); 
                
                finalpackage.put("appId", WxConfig.APP_ID);    
                finalpackage.put("timeStamp", timestamp);    
                finalpackage.put("nonceStr", nonce_str);  
                finalpackage.put("package", "prepay_id=" + prepay_id);   
                finalpackage.put("signType", "MD5");   
                String finalsign = reqHandler.createSign(finalpackage); 
                
                JSONObject json = new JSONObject();
                json.put("appid", WxConfig.APP_ID);
                json.put("timestamp", timestamp);
                json.put("noncestr", nonce_str);
                json.put("partnerid", WxConfig.PARTNER);
                json.put("prepayid", prepay_id);
//                json.put("package", packageValue);
                json.put("sign", finalsign);
                
                System.out.println("json: " + json.toJSONString());
                
                return json;
            }  
        } catch (Exception e1) {  
            // TODO Auto-generated catch block  
            e1.printStackTrace();  
        }
        
        return null;
    }  
}
