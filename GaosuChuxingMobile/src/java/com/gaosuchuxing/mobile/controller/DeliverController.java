/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.controller;

import com.alibaba.fastjson.JSONObject;
import com.gaosuchuxing.mobile.common.Constant;
import com.gaosuchuxing.mobile.common.WxConfig;
import com.gaosuchuxing.mobile.common.WxRequest;
import com.gaosuchuxing.mobile.delegate.WebDelegate;
import com.gaosuchuxing.mobile.domain.ActivityNoticeVO;
import com.gaosuchuxing.mobile.domain.DeliverVO;
import com.gaosuchuxing.mobile.domain.NotificationVO;
import com.gaosuchuxing.mobile.domain.OrderCouponVO;
import com.gaosuchuxing.mobile.domain.OrderDetailVO;
import com.gaosuchuxing.mobile.domain.OrderVO;
import com.gaosuchuxing.mobile.domain.ShopVO;
import com.gaosuchuxing.mobile.domain.StationVO;
import com.gaosuchuxing.mobile.form.LoginForm;
import com.gaosuchuxing.mobile.util.CommonUtil;
import com.gaosuchuxing.mobile.util.NumberUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
@RequestMapping("/deliver")
public class DeliverController extends MultiActionController {
    
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
    
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, LoginForm loginForm) {
        ModelAndView mav = new ModelAndView();
        
        if (request.getMethod().equals("GET")) {
            mav.setViewName("mobileDeliverLoginLayout");
            mav.addObject("loginForm", loginForm);
        } else {
            String userId = loginForm.getUserId();
            HttpSession session = request.getSession(true);
            if (session != null) {
                session.removeAttribute(Constant.SESSION_INFO.LOGIN_DELIVER);
            } else {
                mav.setViewName("mobileDeliverLoginLayout");
                mav.addObject("loginForm", new LoginForm());
                return mav;
            }
            
            if (userId == null || userId.isEmpty()) {
                request.setAttribute(Constant.ATTRIBUTE.ERROR_MESSAGE, "账号不能为空");
                mav.setViewName("mobileDeliverLoginLayout");
                mav.addObject("loginForm", loginForm);
                return mav;
            }

            try {
                DeliverVO loginDeliver = webDelegate.getDeliver(loginForm.getUserId());
                
                if (loginDeliver != null && loginDeliver.getPassword().equals(loginForm.getDigest())) {                    
                    session.setAttribute(Constant.SESSION_INFO.LOGIN_DELIVER, loginDeliver);
                    return new ModelAndView("redirect:/deliver/home");
                    
                } else {
                    if (loginDeliver == null)
                        request.setAttribute(Constant.ATTRIBUTE.ERROR_MESSAGE, "该账号不存在");
                    else
                        request.setAttribute(Constant.ATTRIBUTE.ERROR_MESSAGE, "密码不正确");
                    
                    mav.setViewName("mobileDeliverLoginLayout");
                    mav.addObject("loginForm", loginForm);
                    return mav;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute(Constant.ATTRIBUTE.ERROR_MESSAGE, "账号或密码有错误");
                mav.setViewName("mobileDeliverLoginLayout");
                mav.addObject("loginForm", loginForm);
                return mav;
            }
        }
        
        return mav;
    }
    
    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        request.getSession().removeAttribute(Constant.SESSION_INFO.LOGIN_DELIVER);
        mav.setViewName("mobileDeliverLoginLayout");
        mav.addObject("loginForm", new LoginForm());
        return mav;
    }
    
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginDeliver(request)) {
            return new ModelAndView("redirect:/deliver/logout");
        }
        
        ModelAndView mav = new ModelAndView("mobileDeliverHomeLayout");    
        
        DeliverVO loginDeliver = (DeliverVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_DELIVER);        
        int deliverId = loginDeliver.getId();
        
        List<OrderVO> allOrders = webDelegate.getOrderListByDeliver(deliverId, null);
        List<OrderVO> waitingOrders = webDelegate.getOrderListByDeliver(deliverId, Constant.ORDER_STATUS.WAITING);
        List<OrderVO> shippingOrders = webDelegate.getOrderListByDeliver(deliverId, Constant.ORDER_STATUS.SHIPPING);
        List<OrderVO> completedOrders = webDelegate.getOrderListByDeliver(deliverId, Constant.ORDER_STATUS.COMPLETED);
        
        mav.addObject("allOrders", allOrders);
        mav.addObject("waitingOrders", waitingOrders);
        mav.addObject("shippingOrders", shippingOrders);
        mav.addObject("completedOrders", completedOrders);
        
        return mav;
    }
    
    public ModelAndView getOrderList(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginDeliver(request)) {
            return new ModelAndView("redirect:/deliver/logout");
        }
        
        ModelAndView mav = new ModelAndView("orderListLayout");    
        
        String tabInfo = "tab_1_1";
        
        if (!CommonUtil.isEmptyStr(request.getParameter("tabInfo")))
            tabInfo = request.getParameter("tabInfo");
        
        DeliverVO loginDeliver = (DeliverVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_DELIVER);        
        int deliverId = loginDeliver.getId();
        
        List<OrderVO> allOrders = webDelegate.getOrderListByDeliver(deliverId, null);
        List<OrderVO> waitingOrders = webDelegate.getOrderListByDeliver(deliverId, Constant.ORDER_STATUS.WAITING);
        List<OrderVO> shippingOrders = webDelegate.getOrderListByDeliver(deliverId, Constant.ORDER_STATUS.SHIPPING);
        List<OrderVO> completedOrders = webDelegate.getOrderListByDeliver(deliverId, Constant.ORDER_STATUS.COMPLETED);
        
        mav.addObject("allOrders", allOrders);
        mav.addObject("waitingOrders", waitingOrders);
        mav.addObject("shippingOrders", shippingOrders);
        mav.addObject("completedOrders", completedOrders);
        mav.addObject("tabInfo", tabInfo);
        
        return mav;
    }
    
    @RequestMapping("/setOrderStatus")
    public String setOrderStatus(HttpServletRequest request, HttpServletResponse response) {
        int orderId = NumberUtil.strToInt(request.getParameter("orderId"));
        String status = request.getParameter("status");
        
        webDelegate.setOrderStatus(orderId, status);
        
        CommonUtil.sendSuccessMessageResponse(response);
                
        return null;
    }
    
    public ModelAndView order(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginDeliver(request)) {
            return new ModelAndView("redirect:/deliver/logout");
        }
        
        int orderId= NumberUtil.strToInt(request.getParameter("orderId"));
        
        ModelAndView mav = new ModelAndView("mobileDeliverOrderLayout");   
        mav.addObject("orderId", orderId);
        
        return mav;
    }
    
    public ModelAndView getOrderContent(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginDeliver(request)) {
            return new ModelAndView("redirect:/deliver/logout");
        }
        
        ModelAndView mav = new ModelAndView("orderContentLayout");  
        
        int orderId= NumberUtil.strToInt(request.getParameter("orderId"));
        
        OrderVO order = webDelegate.getOrder(orderId);
        ShopVO shop = webDelegate.getShop(order.getShopId());
        List<OrderDetailVO> orderDetails = webDelegate.getOrderDetailListByGoods(orderId);
        List<OrderCouponVO> orderCoupons = webDelegate.getOrderCouponList(orderId);
        double couponAmount = 0;
        
        if (orderCoupons != null) {
            for (OrderCouponVO coupon: orderCoupons) {
                couponAmount += coupon.getAmount();
            }
        }
        
        mav.addObject("shop", shop);
        mav.addObject("order", order);
        mav.addObject("goodsList", orderDetails);
        mav.addObject("couponAmount", couponAmount);
        
        return mav;
    }
    
    public ModelAndView getOrderFooter(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginDeliver(request)) {
            return new ModelAndView("redirect:/deliver/logout");
        }
        
        ModelAndView mav = new ModelAndView("orderFooterLayout");  
        
        int orderId= NumberUtil.strToInt(request.getParameter("orderId"));
        
        OrderVO order = webDelegate.getOrder(orderId);
        
        mav.addObject("order", order);
        
        return mav;
    }
    
    public ModelAndView notification(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginDeliver(request)) {
            return new ModelAndView("redirect:/deliver/logout");
        }
        
        ModelAndView mav = new ModelAndView("mobileDeliverNotificationLayout");    
        
        DeliverVO loginDeliver = (DeliverVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_DELIVER);        
        int deliverId = loginDeliver.getId();
        
        List<NotificationVO> notifications = webDelegate.getNotificationList(-1, deliverId, -1, -1, null, null);
        
        mav.addObject("notifications", notifications);
        
        return mav;
    }
    
    @RequestMapping("/setNotificationStatus")
    public String setNotificationStatus(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginDeliver(request)) {
            return "redirect:/deliver/logout";
        }
        
        DeliverVO loginDeliver = (DeliverVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_DELIVER);
        int deliverId = loginDeliver.getId();
        
        int notificationId = NumberUtil.strToInt(request.getParameter("notificationId"));
        
        webDelegate.setNotificationStatus(notificationId, -1, loginDeliver.getId());       
        
        loginDeliver = webDelegate.getDeliverById(deliverId);        
        request.getSession().setAttribute(Constant.SESSION_INFO.LOGIN_DELIVER, loginDeliver);
        
        
        CommonUtil.sendSuccessMessageResponse(response);
                
        return null;
    }
    
    public ModelAndView activityNotice(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginDeliver(request)) {
            return new ModelAndView("redirect:/deliver/logout");
        }
        
        int activityNoticeId= NumberUtil.strToInt(request.getParameter("activityNoticeId"));
        
        ActivityNoticeVO activityNotice = webDelegate.getActivityNotice(activityNoticeId);
        
        ModelAndView mav = new ModelAndView("mobileDeliverActivityNoticeLayout");   
        mav.addObject("activityNotice", activityNotice);
        
        return mav;
    }
    
    public ModelAndView profile(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginDeliver(request)) {
            return new ModelAndView("redirect:/deliver/logout");
        }
        
        if (CommonUtil.isEmptyStr(request.getParameter("code")) && !Constant.DEV_MODE) {
            String redirectUrl = new String(request.getRequestURL());
            
            String wxAuthCodeUrl = WxConfig.AUTH_CODE_URL;
            wxAuthCodeUrl += "?appid=" + WxConfig.APP_ID;
            wxAuthCodeUrl += "&redirect_uri=" + redirectUrl;
            wxAuthCodeUrl += "&response_type=code";
//            wxAuthCodeUrl += "&scope=snsapi_base";
            wxAuthCodeUrl += "&scope=snsapi_userinfo";
            wxAuthCodeUrl += "&state=STATE#wechat_redirect";
            
            String url = response.encodeRedirectURL(wxAuthCodeUrl);
            
            return new ModelAndView("redirect:" + url);
            
        } else {
            String code = request.getParameter("code");
                        
            String tokenBody = WxRequest.getAccessToken(code);
            
            String imgUrl = null;
            
            if (tokenBody != null) {                
                try {
                    JSONObject tokenJson = JSONObject.parseObject(tokenBody);                    
                    String accessToken = tokenJson.getString("access_token");
                    String openId = tokenJson.getString("openid");
                    
//                    String openId = "omFwR00yfzLJj67aiUAN6aoRtz_k";
                    
//                    if (CommonUtil.isEmptyStr(accessToken) || CommonUtil.isEmptyStr(openId)) {
//                        CommonUtil.sendMessageResponse(response, "invaild token: " + tokenBody);
//                        return null;
//                    }
                    
//                    CommonUtil.sendMessageResponse(response, "access_token: " + accessToken);
//                    CommonUtil.sendMessageResponse(response, "openid: " + openId);
                    
                    String userInfoBody = WxRequest.getUserInfo(accessToken, openId);
                    
                    if (userInfoBody != null) {
                        System.out.println("userInfo: " + userInfoBody);
                        
                        JSONObject userInfoJson = JSONObject.parseObject(userInfoBody);
                        
                        imgUrl = userInfoJson.getString("headimgurl");
                        
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
            
            DeliverVO deliver = (DeliverVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_DELIVER);        
        
            StationVO station = webDelegate.getStationByDeliver(deliver.getId());

            int deliveryQty = webDelegate.getOrderCountByDeliver(deliver.getId());

            ModelAndView mav = new ModelAndView("mobileDeliverProfileLayout");           

            mav.addObject("deliver", deliver);
            mav.addObject("deliveryQty", deliveryQty);
            mav.addObject("imgUrl", imgUrl);

            if (station != null)
                mav.addObject("stationName", station.getName());

            return mav;
        }        
        
    }
    
    public ModelAndView password(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginDeliver(request)) {
            return new ModelAndView("redirect:/deliver/logout");
        }
        
        ModelAndView mav = new ModelAndView("mobileDeliverPasswordLayout");                   
        return mav;
    }
    
    @RequestMapping(value="/setPassword", method = RequestMethod.POST)
    public String setPassword(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginDeliver(request)) {
            return "redirect:/deliver/logout";
        }
        
        DeliverVO deliver = (DeliverVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_DELIVER);
        
        String currentPwd = request.getParameter("currentPwd");
        String newPwd = request.getParameter("newPwd");
        
        if (CommonUtil.isEmptyStr(currentPwd) || CommonUtil.isEmptyStr(newPwd)) {
            CommonUtil.sendMessageResponse(response, "旧密码和新密码不正确");
        } else if (!deliver.getPassword().equals(currentPwd)) {
            CommonUtil.sendMessageResponse(response, "旧密码不正确");
        } else if (currentPwd.equals(newPwd)){
            CommonUtil.sendMessageResponse(response, "新密码和旧密码不能一致");
        } else {
            webDelegate.setDeliverPassword(deliver.getId(), newPwd);
            deliver.setPassword(newPwd);
            request.getSession().setAttribute(Constant.SESSION_INFO.LOGIN_DELIVER, deliver);
            CommonUtil.sendSuccessMessageResponse(response);
        }
        
        return null;
    }
    
    public ModelAndView feedback(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginDeliver(request)) {
            return new ModelAndView("redirect:/deliver/logout");
        }
        
        ModelAndView mav = new ModelAndView("mobileDeliverFeedbackLayout");                   
        return mav;
    }
    
    @RequestMapping(value="/addFeedback", method = RequestMethod.POST)
    public String addFeedback(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginDeliver(request)) {
            return "redirect:/deliver/logout";
        }
        
        DeliverVO deliver = (DeliverVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_DELIVER);
        
        String feedback = request.getParameter("feedback");
        
        webDelegate.addFeedback(-1, deliver.getId(), feedback);
        
        CommonUtil.sendSuccessMessageResponse(response);
        
        return null;
    }
    
    public ModelAndView about(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginDeliver(request)) {
            return new ModelAndView("redirect:/deliver/logout");
        }
        
        ModelAndView mav = new ModelAndView("mobileDeliverAboutLayout");                   
        return mav;
    }
}