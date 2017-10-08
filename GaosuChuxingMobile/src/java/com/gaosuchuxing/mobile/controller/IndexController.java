/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.controller;

import com.alibaba.fastjson.JSONObject;
import com.gaosuchuxing.mobile.common.WxConfig;
import com.gaosuchuxing.mobile.common.WxRequest;
import com.gaosuchuxing.mobile.delegate.WebDelegate;
import com.gaosuchuxing.mobile.util.CommonUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class IndexController extends MultiActionController {
    
    private WebDelegate webDelegate;

    /**
     * Get the value of webDelegate
     *
     * @return the value of webDelegate
     */
    public WebDelegate getWebDelegate() {
        return webDelegate;
    }

    /**
     * Set the value of webDelegate
     *
     * @param webDelegate new value of webDelegate
     */
    public void setWebDelegate(WebDelegate webDelegate) {
        this.webDelegate = webDelegate;
    }
    
    
    public ModelAndView user(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("redirect:/user/home");
    }
    
    public ModelAndView deliver(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("redirect:/deliver/home");
    }
    
    public String wxAuth(HttpServletRequest request, HttpServletResponse response) {        
        if (CommonUtil.isEmptyStr(request.getParameter("code"))) {
//            String redirectUrl = new String(request.getRequestURL()) + request.getQueryString();       
            String redirectUrl = new String(request.getRequestURL());
            
            String wxAuthCodeUrl = WxConfig.AUTH_CODE_URL;
            wxAuthCodeUrl += "?appid=" + WxConfig.APP_ID;
            wxAuthCodeUrl += "&redirect_uri=" + redirectUrl;
            wxAuthCodeUrl += "&response_type=code";
//            wxAuthCodeUrl += "&scope=snsapi_base";
            wxAuthCodeUrl += "&scope=snsapi_userinfo";
            wxAuthCodeUrl += "&state=STATE#wechat_redirect";
            
            String url = response.encodeRedirectURL(wxAuthCodeUrl);
            
            return "redirect:" + url;
            
        } else {
            String code = request.getParameter("code");
                        
            String tokenBody = WxRequest.requestAccessToken(code);
            
            if (tokenBody != null) {                
                try {
                    JSONObject tokenJson = JSONObject.parseObject(tokenBody);                    
                    String accessToken = tokenJson.getString("access_token");
                    String openId = tokenJson.getString("openid");
                    
                    if (CommonUtil.isEmptyStr(accessToken) || CommonUtil.isEmptyStr(openId)) {
                        CommonUtil.sendMessageResponse(response, "invaild token: " + tokenBody);
                        return null;
                    }
                    
                    CommonUtil.sendMessageResponse(response, "access_token: " + accessToken);
                    CommonUtil.sendMessageResponse(response, "openid: " + openId);
                    
                    String userInfoBody = WxRequest.requestUserInfo(accessToken, openId);
                    
                    if (userInfoBody != null) {
                        System.out.println("userInfo: " + userInfoBody);
                        
                        JSONObject userInfoJson = JSONObject.parseObject(userInfoBody);                    
                        
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("text/html");

                        try {
                            response.getOutputStream().write(userInfoBody.getBytes());
                        } catch (Exception ex ) {
                            ex.printStackTrace();
                        }
                        
                    } else {
                        CommonUtil.sendMessageResponse(response, "user info failure");
                    }
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                    
                    CommonUtil.sendMessageResponse(response, "unknow error: " + ex.getMessage());
                }                
                
            } else {
                CommonUtil.sendMessageResponse(response, "access token failure");
            }
            
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("text/html");
//
//            try {
//                response.getOutputStream().write(tokenBody.getBytes());
//            } catch (Exception ex ) {
//                ex.printStackTrace();
//            }
        } 
        
        return null;
    }

}
