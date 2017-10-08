/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.common;

import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class WxRequest {
    
    public static String requestAccessToken(String authCode) {
        try {
            HttpClient client = new HttpClient();
//            client.getHostConfiguration().setProxy("10.70.250.242", 8080);
            
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
    
    public static String requestUserInfo(String accessToken, String openId) {
        try {
            HttpClient client = new HttpClient();
            
            PostMethod post = new PostMethod(WxConfig.USER_INFO_URL);
            
            post.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            
            NameValuePair access_token = new NameValuePair("access_token", accessToken);
            NameValuePair openid = new NameValuePair("openid", openId);
            
            post.setRequestBody(new NameValuePair[] {access_token, openid});
            
            int responseCode = client.executeMethod(post);
            String responseBody = post.getResponseBodyAsString();
            
            if(responseCode == 200)
                return responseBody;
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
}
