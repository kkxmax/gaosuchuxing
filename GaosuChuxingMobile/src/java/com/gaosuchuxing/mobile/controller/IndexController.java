/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.gaosuchuxing.mobile.common.Constant;
import com.gaosuchuxing.mobile.common.SMSRequest;
import com.gaosuchuxing.mobile.common.WxConfig;
import com.gaosuchuxing.mobile.common.WxRequest;
import com.gaosuchuxing.mobile.delegate.WebDelegate;
import com.gaosuchuxing.mobile.domain.SmsVO;
import com.gaosuchuxing.mobile.domain.UserVO;
import com.gaosuchuxing.mobile.util.CommonUtil;
import com.gaosuchuxing.mobile.util.DateUtil;
import com.gaosuchuxing.mobile.util.NumberUtil;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.shopxx.plugin.weixin.utils.GetWxOrderno;
import net.shopxx.plugin.weixin.utils.RequestHandler;
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
        return new ModelAndView("redirect:/user/wxAuthorize");
    }
    
    public ModelAndView deliver(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("redirect:/deliver/home");
    }
    
    public ModelAndView advertise(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("mobileAdvLayout");
        
        HttpSession session = request.getSession(true);
        
        String num = request.getParameter("num");
        
        if (!CommonUtil.isEmptyStr(num)) {
            session.removeAttribute(Constant.SESSION_INFO.SHARE_COUPON_NUM);
            session.setAttribute(Constant.SESSION_INFO.SHARE_COUPON_NUM, num);
        }
        
        if (Constant.DEV_MODE) {
            session.removeAttribute(Constant.SESSION_INFO.LOGIN_USER);
            
            UserVO user = webDelegate.getUserByWxOpenId(Constant.wxOpenId);
                        
            if (user != null) {
                webDelegate.updateUserImgUrl(Constant.wxOpenId, Constant.nickName, Constant.imgUrl);
                user.setAvatarPath(Constant.imgUrl);
                user.setName(Constant.nickName);
            } else {
                webDelegate.addUser(Constant.wxOpenId, Constant.nickName, Constant.imgUrl);
                user = webDelegate.getUserByWxOpenId(Constant.wxOpenId);
            }

            session.setAttribute(Constant.SESSION_INFO.LOGIN_USER, user);
            
            String shareNum = (String) session.getAttribute(Constant.SESSION_INFO.SHARE_COUPON_NUM);
            mav.addObject("shareNum", shareNum);

            return mav;
        }
        
        if (CommonUtil.isEmptyStr(request.getParameter("code"))) {
            String redirectUrl = new String(request.getRequestURL());
            
            String wxAuthCodeUrl = WxRequest.getAuthCodeUrl(redirectUrl);
            
            String url = response.encodeRedirectURL(wxAuthCodeUrl);
            
            return new ModelAndView("redirect:" + url);
            
        } else {
            String code = request.getParameter("code");
                        
            String tokenBody = WxRequest.getAccessToken(code);
            
            if (tokenBody != null) {                
                try {
                    com.alibaba.fastjson.JSONObject tokenJson = com.alibaba.fastjson.JSONObject.parseObject(tokenBody);                    
                    String accessToken = tokenJson.getString("access_token");
                    String openId = tokenJson.getString("openid");
                    
                    String userInfoBody = WxRequest.getUserInfo(accessToken, openId);
                    
                    if (userInfoBody != null) {
                        System.out.println("userInfo: " + userInfoBody);
                        
                        com.alibaba.fastjson.JSONObject userInfoJson = com.alibaba.fastjson.JSONObject.parseObject(userInfoBody);
                        
                        String wxOpenId = userInfoJson.getString("openid");
                        String nickName = userInfoJson.getString("nickname");
                        String imgUrl = userInfoJson.getString("headimgurl");
                        
                        session.removeAttribute(Constant.SESSION_INFO.LOGIN_USER);
                        
                        UserVO user = webDelegate.getUserByWxOpenId(wxOpenId);
                        
                        if (user != null) {
                            webDelegate.updateUserImgUrl(wxOpenId, nickName, imgUrl);
                            user.setAvatarPath(imgUrl);
                            user.setName(nickName);
                        } else {
                            webDelegate.addUser(wxOpenId, nickName, imgUrl);
                            user = webDelegate.getUserByWxOpenId(wxOpenId);
                        }
                        
                        session.setAttribute(Constant.SESSION_INFO.LOGIN_USER, user);
                        
                        String shareNum = (String) session.getAttribute(Constant.SESSION_INFO.SHARE_COUPON_NUM);
                        mav.addObject("shareNum", shareNum);
                        return mav;
                        
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
            
            return null;
        }
    }
    
//    public String weixinpay(HttpServletRequest request, HttpServletResponse response) {
//        String goodsName = request.getParameter("goodsName");        
//        int amount = NumberUtil.strToInt(request.getParameter("amount"));
//        
//        if (CommonUtil.isEmptyStr(goodsName) || amount == -1) {
//            goodsName = DateUtil.formatDate(new java.util.Date(), "yyyyMMddHHmmssSSS");
//            amount = 1;
//        }   
//        
//        org.json.simple.JSONObject json = WxRequest.getWxPrepayInfo(request, response, goodsName, amount);
//        
//        if (json != null)
//            CommonUtil.sendJSONResponse(response, json);
//        
//        return null;
//    }
    
    
//    @RequestMapping(value="/weixinpay")
    public String wxpayNotify(HttpServletRequest request, HttpServletResponse response) { 
        try {
            request.setCharacterEncoding("UTF-8");  
            response.setCharacterEncoding("UTF-8");  
            response.setContentType("text/html;charset=UTF-8");  
            response.setHeader("Access-Control-Allow-Origin", "*");   
            InputStream in=request.getInputStream();  
            ByteArrayOutputStream out=new ByteArrayOutputStream();  
            byte[] buffer =new byte[1024];  
            int len=0;  
            while((len=in.read(buffer))!=-1){  
                out.write(buffer, 0, len);  
            }  
            out.close();  
            in.close();  
            String msgxml=new String(out.toByteArray(),"utf-8");//xml数据  
            System.out.println(msgxml);     
            Map map =  new GetWxOrderno().doXMLParse(msgxml);  
            String result_code=(String) map.get("result_code");  
            String out_trade_no = (String) map.get("out_trade_no");  
            String total_fee  = (String) map.get("total_fee");  
            String sign  = (String) map.get("sign");  
            Double amount=new Double(total_fee)/100;//获取订单金额  
            String attach= (String) map.get("attach");  
            String sn=out_trade_no;//获取订单编号  
//            Long memberid=Long.valueOf(attach);  
//            Member member=memberService.find(memberid);  
//            Order order=orderService.findBySn(sn);  

            if(result_code.equals("SUCCESS")/*&&member!=null&&order!=null*/) {  
//                //验证签名  
//                float sessionmoney = Float.parseFloat(order.getAmount().toString());  
//                String finalmoney = String.format("%.2f", sessionmoney);  
//                finalmoney = finalmoney.replace(".", "");  
//                int intMoney = Integer.parseInt(finalmoney);              

                //总金额以分为单位，不带小数点  
                String order_total_fee = String.valueOf(amount);  
                String fee_type  = (String) map.get("fee_type");  
                String bank_type  = (String) map.get("bank_type");  
                String cash_fee  = (String) map.get("cash_fee");  
                String is_subscribe  = (String) map.get("is_subscribe");  
                String nonce_str  = (String) map.get("nonce_str");  
                String openid  = (String) map.get("openid");  
                String return_code  = (String) map.get("return_code");  
                String sub_mch_id  = (String) map.get("sub_mch_id");  
                String time_end  = (String) map.get("time_end");  
                String trade_type  = (String) map.get("trade_type");  
                String transaction_id  = (String) map.get("transaction_id");  
                
                //需要对以下字段进行签名  
                SortedMap<String, String> packageParams = new TreeMap<String, String>();  
                packageParams.put("appid", WxConfig.APP_ID);    
                packageParams.put("attach", attach); //用自己系统的数据：会员id  
                packageParams.put("bank_type", bank_type);    
                packageParams.put("cash_fee", cash_fee);    
                packageParams.put("fee_type", fee_type);      
                packageParams.put("is_subscribe", is_subscribe);    
                packageParams.put("mch_id", WxConfig.PARTNER);    
                packageParams.put("nonce_str", nonce_str);        
                packageParams.put("openid", openid);   
                packageParams.put("out_trade_no", out_trade_no);  
                packageParams.put("result_code", result_code);    
                packageParams.put("return_code", return_code);        
                packageParams.put("sub_mch_id", sub_mch_id);   
                packageParams.put("time_end", time_end);  
                packageParams.put("total_fee", order_total_fee);    //用自己系统的数据：订单金额  
                packageParams.put("trade_type", trade_type);   
                packageParams.put("transaction_id", transaction_id);  
                RequestHandler reqHandler = new RequestHandler(request, response);  
                reqHandler.init(WxConfig.APP_ID, WxConfig.APP_SECRET, WxConfig.PARTNER_KEY);        
                String endsign = reqHandler.createSign(packageParams);  
                if(sign.equals(endsign)){//验证签名是否正确  官方签名工具地址：https://pay.weixin.qq.com/wiki/tools/signverify/   
//                    if("订单没有支付"){  
//                        try{  
//                        //处理自己的业务逻辑  
                        response.getWriter().write(CommonUtil.setXml("SUCCESS", "OK"));    //告诉微信已经收到通知了  
//                        }catch(Exception e){  
//                            System.out.println("微信支付异步通知异常");  
//                        }  
//                    }else if("订单支付了"){  
//                        response.getWriter().write(setXml("SUCCESS", "OK"));    //告诉微信已经收到通知了  
//                    }  

                }  
            }  
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
}
