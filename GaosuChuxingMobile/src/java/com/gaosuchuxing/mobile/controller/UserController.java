/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.controller;

import com.gaosuchuxing.mobile.common.AMapUtil;
import com.gaosuchuxing.mobile.domain.ActivityVO;
import com.gaosuchuxing.mobile.common.Constant;
import com.gaosuchuxing.mobile.common.SMSRequest;
import com.gaosuchuxing.mobile.common.WxConfig;
import com.gaosuchuxing.mobile.common.WxRequest;
import com.gaosuchuxing.mobile.delegate.WebDelegate;
import com.gaosuchuxing.mobile.domain.ActivityNoticeVO;
import com.gaosuchuxing.mobile.domain.CouponVO;
import com.gaosuchuxing.mobile.domain.DeliverVO;
import com.gaosuchuxing.mobile.domain.GoodsKindVO;
import com.gaosuchuxing.mobile.domain.GoodsVO;
import com.gaosuchuxing.mobile.domain.NotificationVO;
import com.gaosuchuxing.mobile.domain.OrderCouponVO;
import com.gaosuchuxing.mobile.domain.OrderDetailVO;
import com.gaosuchuxing.mobile.domain.OrderVO;
import com.gaosuchuxing.mobile.domain.PointVO;
import com.gaosuchuxing.mobile.domain.ShareCouponVO;
import com.gaosuchuxing.mobile.domain.ShopKindVO;
import com.gaosuchuxing.mobile.domain.ShopVO;
import com.gaosuchuxing.mobile.domain.SmsVO;
import com.gaosuchuxing.mobile.domain.StepVO;
import com.gaosuchuxing.mobile.domain.StationVO;
import com.gaosuchuxing.mobile.domain.UserCouponVO;
import com.gaosuchuxing.mobile.domain.UserVO;
import com.gaosuchuxing.mobile.util.CommonUtil;
import com.gaosuchuxing.mobile.util.DateUtil;
import com.gaosuchuxing.mobile.util.Gps;
import com.gaosuchuxing.mobile.util.GpsUtil;
import com.gaosuchuxing.mobile.util.MapUtil;
import com.gaosuchuxing.mobile.util.Md5Util;
import com.gaosuchuxing.mobile.util.NumberUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
@RequestMapping("/user")
public class UserController extends MultiActionController {
    
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
        
//    @RequestMapping(value="/forward")
//    public String redirectHome(HttpServletRequest request, HttpServletResponse response) {
////        if (CommonUtil.isEmptyStr(previousPage)) {
////            return "redirect:/user/home";
////        } else {
////            response.setHeader(METHOD_GET, METHOD_GET);
////            return null;
////        }
//        return "redirect:/user/home";
//    }
    
    public String wxAuthorize(HttpServletRequest request, HttpServletResponse response) {
        if (Constant.DEV_MODE) {
            HttpSession session = request.getSession(true);
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
            
            user.setToken(Md5Util.getMd5(user.getWxOpenId()));

            session.setAttribute(Constant.SESSION_INFO.LOGIN_USER, user);

            return "redirect:/user/home";
        }
            
        
        if (CommonUtil.isEmptyStr(request.getParameter("code"))) {
            String redirectUrl = new String(request.getRequestURL());
            
            String wxAuthCodeUrl = WxRequest.getAuthCodeUrl(redirectUrl);
            
            String url = response.encodeRedirectURL(wxAuthCodeUrl);
            
            return "redirect:" + url;
            
        } else {
            String code = request.getParameter("code");
                        
            String tokenBody = WxRequest.getAccessTokenByCode(code);
            
            if (tokenBody != null) {                
                try {
                    com.alibaba.fastjson.JSONObject tokenJson = com.alibaba.fastjson.JSONObject.parseObject(tokenBody);                    
                    String accessToken = tokenJson.getString("access_token");
                    String openId = tokenJson.getString("openid");
                    
                    String userInfoBody = WxRequest.getUserInfo(accessToken, openId);
                    
                    if (userInfoBody != null) {
//                        System.out.println("userInfo: " + userInfoBody);
                        
                        com.alibaba.fastjson.JSONObject userInfoJson = com.alibaba.fastjson.JSONObject.parseObject(userInfoBody);
                        
                        String wxOpenId = userInfoJson.getString("openid");
                        String nickName = userInfoJson.getString("nickname");
                        String imgUrl = userInfoJson.getString("headimgurl");
                        
                        HttpSession session = request.getSession(true);
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
                        
                        user.setToken(Md5Util.getMd5(user.getWxOpenId()));
                        
                        session.setAttribute(Constant.SESSION_INFO.LOGIN_USER, user);
                        
                        return "redirect:/user/home";
                        
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
    
    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public String signup(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            CommonUtil.sendMessageResponse(response, "信息有错误");
            return null;
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        String telNo = request.getParameter("telno");
        String verifyCode = request.getParameter("code");
        
        if (CommonUtil.isEmptyStr(telNo) || CommonUtil.isEmptyStr(verifyCode)) {
            CommonUtil.sendMessageResponse(response, "手机号或验证吗不能为空");
        } else {
            HttpSession session = request.getSession();
            
            if (session == null) {
                CommonUtil.sendMessageResponse(response, "信息有错误");
                return null;
            }
            SmsVO sms = (SmsVO) session.getAttribute(Constant.SESSION_INFO.SMS_VERIFY_INFO);
            
            if (sms == null) {
                CommonUtil.sendMessageResponse(response, "验证码不正确");
                return null;
            }
            
            UserVO exist = webDelegate.getUserById(telNo);
            
            if (exist != null) {
                CommonUtil.sendMessageResponse(response, "手机号重复");
                return null;
            }
            
            if (sms.getTelNo().equals(telNo) && sms.getVerifyCode().equals(verifyCode)) {
                webDelegate.activeUser(loginUser.getWxOpenId(), telNo);
                loginUser = webDelegate.getUserByWxOpenId(loginUser.getWxOpenId());
                session.removeAttribute(Constant.SESSION_INFO.LOGIN_USER);
                session.setAttribute(Constant.SESSION_INFO.LOGIN_USER, loginUser);
                session.removeAttribute(Constant.SESSION_INFO.SMS_VERIFY_INFO);
                
                JSONObject json = new JSONObject();
                json.put("name", loginUser.getName());
                json.put("telno", loginUser.getUserId());
                CommonUtil.sendJSONResponse(response, json);
            } else {
                CommonUtil.sendMessageResponse(response, "验证码不正确");
            }
        }
        
        return null;
    }
    
    @RequestMapping("/verify")
    public String verifyTelephone(HttpServletRequest request, HttpServletResponse response) {        
        String telNo = request.getParameter("telno");              
        
        if (CommonUtil.isEmptyStr(telNo)) {
            CommonUtil.sendMessageResponse(response, "请输入手机号");
            return null;
        }    
        
        String verifyCode = SMSRequest.getVerifyCode();
        
        SMSRequest.sendSMS(telNo, SMSRequest.SIGNUP_TEMPLATE_ID, verifyCode, "5");
        
        SmsVO sms = new SmsVO();
        sms.setTelNo(telNo);
        sms.setVerifyCode(verifyCode);
        
        request.getSession(true).setAttribute(Constant.SESSION_INFO.SMS_VERIFY_INFO, sms);
        
        CommonUtil.sendMessageResponse(response, "success");
        
        return null;
    }
    
    @RequestMapping("/takeAdv")
    public String takeAdv(HttpServletRequest request, HttpServletResponse response) {        
        HttpSession session = request.getSession();       
        
        if (session == null) {
            CommonUtil.sendMessageResponse(response, "红包有错误");
            return null;
        }    
                
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        if (loginUser == null/* || !loginUser.getStatus()*/) {
            CommonUtil.sendMessageResponse(response, "抱歉。 您必须重新加载页面");
            return null;
        } 
        
        String shareNum = request.getParameter("shareNum");
        
        int id = NumberUtil.strToInt(request.getParameter("id"));
        int qty = -1;
        
        if (id == -1) {
            qty = 10;
        } else {
            ActivityVO activity = webDelegate.getActivity(id);
            if (activity != null)
                qty = activity.getCouponQty();
        }
        
        if (qty == -1) {
            CommonUtil.sendMessageResponse(response, "红包有错误");
            return null;
        }
                
        List<ShareCouponVO> shares = webDelegate.getShareCouponList(shareNum);
        
        if (shares == null) {
            CommonUtil.sendMessageResponse(response, "红包已被抢光");
            return null;
        }
        
        boolean has = false;
        for (ShareCouponVO share: shares) {
            if (share.getUserId() == loginUser.getId()) {
                has = true;
                break;
            }
        }
        
        if (has) {
            CommonUtil.sendMessageResponse(response, "你已经有一个优惠券");
            return null;
        }
        
        int count = 0;
        
        for (ShareCouponVO share: shares) {
            if (share.getStatus())
                count++;
        }
        
        if (count > qty) {
            if (qty != -1)
                webDelegate.deleteShareCoupon(shareNum);
            CommonUtil.sendMessageResponse(response, "因为优惠券数量已满，您不能有");            
            return null;
        }
        
        ArrayList<ShareCouponVO> couponList = new ArrayList<ShareCouponVO>();
        
        for (ShareCouponVO share: shares) {
            if (!share.getStatus())
                couponList.add(share);
        }
        
        int len = couponList.size();
        
        for (int i = 0; i < len; i++) {
            ShareCouponVO c = couponList.get(i);
            c.setIndex(i+1);
        }
        
        int r = NumberUtil.getRandomByRange(1, len);
        
        ShareCouponVO take = null;
        
        for (ShareCouponVO c: couponList) {
            if (c.getIndex() == r) {
                take = c;
                webDelegate.setShareCoupon(c.getId(), loginUser.getId());
                System.out.println("take coupon:" + c.getId());
                break;
            }    
        }
        
        if (take != null) {
            CouponVO coupon = webDelegate.getCoupon(take.getCouponId());
            
            if (coupon != null) {
                JSONObject json = new JSONObject();
                json.put("name", coupon.getName());
                json.put("num", coupon.getNum());
                json.put("kind", coupon.getKind());
                json.put("price", coupon.getPrice());
                json.put("type", coupon.getCouponType());
                json.put("userId", loginUser.getUserId());
                
                webDelegate.addNewUserCoupon(loginUser.getId(), coupon.getId(), 1, id);
                
                CommonUtil.sendJSONResponse(response, json);
                
                if (webDelegate.isLimitedShareCoupon(shareNum, qty))
                    webDelegate.deleteShareCoupon(shareNum);
                
                return null;
            }
            
            
        } 
            
        CommonUtil.sendMessageResponse(response, "红包已被抢光");
        
        return null;
    }    
    
    @RequestMapping("/takeActivityAdv")
    public String takeActivtyAdv(HttpServletRequest request, HttpServletResponse response) {      
        HttpSession session = request.getSession();       
        
        if (session == null) {
            CommonUtil.sendMessageResponse(response, "红包有错误");
            return null;
        }    
                
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        if (loginUser == null/* || !loginUser.getStatus()*/) {
            CommonUtil.sendMessageResponse(response, "您必须重新加载页面");
            return null;
        } 
        
        String shareNum = request.getParameter("shareNum");
        
        int id = NumberUtil.strToInt(request.getParameter("id"));
        int qty = -1;
        
        if (id == -1) {
            qty = 10;
        } else {
            ActivityVO activity = webDelegate.getActivity(id);
            if (activity != null)
                qty = activity.getCouponQty();
        }
        
        if (qty == -1) {
            CommonUtil.sendMessageResponse(response, "红包有错误");
            return null;
        }
                
        List<ShareCouponVO> shares = webDelegate.getShareCouponList(shareNum);
        
        if (shares == null) {
            CommonUtil.sendMessageResponse(response, "红包已被抢光");
            return null;
        }
        
        boolean has = false;
        for (ShareCouponVO share: shares) {
            if (share.getUserId() == loginUser.getId()) {
                has = true;
                break;
            }
        }
        
        if (has) {
            CommonUtil.sendMessageResponse(response, "你已经有一个优惠券");
            return null;
        }
        
        int count = 0;
        
        for (ShareCouponVO share: shares) {
            if (share.getStatus())
                count++;
        }
        
        if (count > qty) {
            if (qty != -1)
                webDelegate.deleteShareCoupon(shareNum);
            CommonUtil.sendMessageResponse(response, "因为优惠券数量已满，您不能有");            
            return null;
        }
        
        ArrayList<ShareCouponVO> couponList = new ArrayList<ShareCouponVO>();
        
        for (ShareCouponVO share: shares) {
            if (!share.getStatus())
                couponList.add(share);
        }
        
        int len = couponList.size();
        
        for (int i = 0; i < len; i++) {
            ShareCouponVO c = couponList.get(i);
            c.setIndex(i+1);
        }
        
        int r = NumberUtil.getRandomByRange(1, len);
        
        ShareCouponVO take = null;
        
        for (ShareCouponVO c: couponList) {
            if (c.getIndex() == r) {
                take = c;
                webDelegate.setShareCoupon(c.getId(), loginUser.getId());
                break;
            }    
        }
        
        if (take != null) {
            CouponVO coupon = webDelegate.getCoupon(take.getCouponId());
            
            if (coupon != null) {
                JSONObject json = new JSONObject();
                json.put("name", coupon.getName());
                json.put("num", coupon.getNum());
                json.put("kind", coupon.getKind());
                json.put("price", coupon.getPrice());
                json.put("type", coupon.getCouponType());
                json.put("userId", loginUser.getUserId());
                
                webDelegate.addNewUserCoupon(loginUser.getId(), coupon.getId(), 1, id);
                
                CommonUtil.sendJSONResponse(response, json);
                
                if (webDelegate.isLimitedShareCoupon(shareNum, qty))
                    webDelegate.deleteShareCoupon(shareNum);
                
                return null;
            }
            
            
        } 
            
        CommonUtil.sendMessageResponse(response, "红包已被抢光");
        
        return null;
    }    
    
    @RequestMapping("/validUser")
    public String validUser(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            CommonUtil.sendMessageResponse(response, "noValid");
            return null;
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        if (loginUser.getStatus())
            CommonUtil.sendSuccessMessageResponse(response);
        else
            CommonUtil.sendMessageResponse(response, "noValid");
        
        return null;
    }
    
    
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        ModelAndView mav = new ModelAndView();
        
        mav.setViewName("mobileUserHomeLayout");
        mav.addObject("loginUserId", loginUser.getId());
        
        
//        Gps gps = GpsUtil.gcj02_To_Bd09(124.30751, 40.14847);
//        System.out.println(gps.toString());
        
//        request.setAttribute(Constant.ATTRIBUTE.TITLE, "高速出行");
//        request.setAttribute(Constant.ATTRIBUTE.CLOSE_URL, request.getContextPath() + "/user/home");
        
        return mav;
    }
    
    @RequestMapping(value = "/setLocation")
    public String setLocation(HttpServletRequest request, HttpServletResponse response) {
        if (CommonUtil.isLoginUser(request)) {
            double lng = NumberUtil.strToDouble(request.getParameter("lng"));
            double lat = NumberUtil.strToDouble(request.getParameter("lat"));
            
            Gps gps = new Gps(lat, lng);
            
            request.getSession().setAttribute(Constant.SESSION_INFO.CURRENT_LOCATION, gps);
        }
        
        return null;
    }
    
    @RequestMapping(value = "/welcomeUser")
    public String welcomeUser(HttpServletRequest request, HttpServletResponse response) {
        int userId = NumberUtil.strToInt(request.getParameter("id"));
        UserVO user = webDelegate.getUser(userId);
        
//        if (user != null && user.getIsNew()) {
//            List<CouponVO> coupons = webDelegate.getBaseLoginCouponList();
//            if (coupons != null) {
//                ArrayList<UserCouponVO> welcomes = new ArrayList<UserCouponVO>();
//                
//                for (CouponVO coupon: coupons) {
//                    UserCouponVO welcome = new UserCouponVO();
//                    welcome.setCouponId(coupon.getId());
//                    welcome.setUserId(user.getId());
//                    welcome.setQty(1);
//                    welcome.setValidFromDate(new Date());
//                    welcome.setValidToDate(DateUtil.getValidToDate(new Date()));
//                    welcome.setStatus(false);
//                    welcome.setRegDate(new Date());
//                    welcomes.add(welcome);
//                }
//                
//                webDelegate.addWelcomeCoupon(welcomes);
//                webDelegate.disableUserIsNew(user.getWxOpenId());
//            }
//            
//            CommonUtil.sendMessageResponse(response, "welcome");
//        } else {
            CommonUtil.sendMessageResponse(response, "not");
//        }
        
        return null;
    }
    
    public ModelAndView searchRoute(HttpServletRequest request, HttpServletResponse response) {        
        ModelAndView mav = new ModelAndView();
        
//        request.setAttribute(Constant.ATTRIBUTE.TITLE, "路线选择");
//        request.setAttribute(Constant.ATTRIBUTE.CLOSE_URL, request.getContextPath() + "/user/home");
        
        double startLat = NumberUtil.strToDouble(request.getParameter("startLat"));
        double startLng = NumberUtil.strToDouble(request.getParameter("startLng"));
        double endLat = NumberUtil.strToDouble(request.getParameter("endLat"));
        double endLng = NumberUtil.strToDouble(request.getParameter("endLng"));
                
        String startAddress = request.getParameter("startAddress");
        String endAddress = request.getParameter("endAddress");
                
        mav.setViewName("mobileUserSearchRouteLayout");
        mav.addObject("startLat", startLat);
        mav.addObject("startLng", startLng);
        mav.addObject("endLat", endLat);
        mav.addObject("endLng", endLng);
        
        mav.addObject("startAddress", startAddress);
        mav.addObject("endAddress", endAddress);
        
        int historyLength = NumberUtil.strToInt(request.getParameter("historyLength"), 0);
        mav.addObject("historyLength", historyLength);
        
        return mav;
    }
    
    
    @RequestMapping(value = "/getStationList") 
    public String getStationList(HttpServletRequest request, HttpServletResponse response) {
//        double lng = NumberUtil.strToDouble(request.getParameter("lng"));
//        double lat = NumberUtil.strToDouble(request.getParameter("lat"));
        String adcode = request.getParameter("adcode");
        boolean searchBeijing = NumberUtil.strToInt(request.getParameter("beijing")) > 0;
        
        if (searchBeijing)
            adcode = Constant.BEIJING_ADCODE;
        
        List<StationVO> stations = webDelegate.getStationList(null, -1, adcode, null, -1, -1, null, null);
        
        if (stations != null) {
            JSONObject jsonResult = new JSONObject();
            JSONArray items = new JSONArray();
            
            for (StationVO station: stations) {
                JSONObject item = new JSONObject();
                item.put("stationId", station.getId());
                item.put("stationName", station.getName());
                item.put("longitude", station.getLongitude());
                item.put("latitude", station.getLatitude());
                item.put("status", station.getStatus().equals(Constant.STATION_STATUS.OPEN)? true: false);
                items.add(item);
            }
            
            jsonResult.put("stations", items);
            
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            
            try {
                jsonResult.writeJSONString(response.getWriter());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                response.getOutputStream().write(null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        return null;
    }
    
    @RequestMapping(value = "/getStationListByNear") 
    public String getStationListByNear(HttpServletRequest request, HttpServletResponse response) {        
        List<StationVO> stations = webDelegate.getStationList(null, -1, null, null, -1, -1, null, null);
        
        ArrayList<StepVO> steps = new ArrayList<>();
        
        Map<String,String[]> map = request.getParameterMap();
        
        int len = map.size() / 4;
//        int len = map.size() / 5;
        
        for (int i = 0; i < len; i++) {
            String startLng = "startLng_" + i;
            String startLat = "startLat_" + i;
            String endLng = "endLng_" + i;
            String endLat = "endLat_" + i;
//            String distance = "distance_" + i;
            
            StepVO step = new StepVO();
            
            if (map.containsKey(startLng))                
                step.setSLng(NumberUtil.strToDouble(request.getParameter(startLng)));
            if (map.containsKey(startLat))
                step.setSLat(NumberUtil.strToDouble(request.getParameter(startLat)));
            if (map.containsKey(endLng))
                step.setELng(NumberUtil.strToDouble(request.getParameter(endLng)));
            if (map.containsKey(endLat))
                step.setELat(NumberUtil.strToDouble(request.getParameter(endLat)));
//            if (map.containsKey(distance))
//                step.setDistance(NumberUtil.strToDouble(request.getParameter(distance)));
            
            steps.add(step);
        }
        
        ArrayList<StationVO> founds = new ArrayList<>();
        
        if (steps != null && stations != null) {
//            for (StepVO step: steps) {
//                for (StationVO station: stations) {
//                    if (MapUtil.checkStation(station, step) && !founds.contains(station))
//                        founds.add(station);
//                }
//            }  

            for (int i = 0; i < steps.size(); i++) {
                StepVO step = steps.get(i);
                for (int j = 0; j < stations.size(); j++) {
                    StationVO station = stations.get(j);
                    
                    if (MapUtil.checkStation(station, step, i, j) && !founds.contains(station)) {
                        founds.add(station);
                        System.out.println("found: i=" + i + ", station=" + station.getId());
                    }   
                }
            }
            
        }
        
        
//        StationVO step0 = new StationVO();
//        step0.setName("start step 8");
//        step0.setId(8);
//        step0.setLongitude(steps.get(8).getSLng());
//        step0.setLatitude(steps.get(8).getSLat());
//        founds.add(step0);
//        
//        StationVO step1 = new StationVO();
//        step1.setName("end step 8");
//        step1.setId(8);
//        step1.setLongitude(steps.get(8).getELng());
//        step1.setLatitude(steps.get(8).getELat());
//        founds.add(step1);
//        
//        for (StationVO s: stations)  {
//            switch (s.getId()) {
//                case 23: founds.add(s); break;
//                case 28: founds.add(s); break;
//                case 42: founds.add(s); break;
//            }
//                
//        }
            
        
        JSONObject jsonResult = new JSONObject();
        JSONArray items = new JSONArray();
        
        for (StationVO found: founds) {
            JSONObject item = new JSONObject();
            item.put("stationId", found.getId());
            item.put("stationName", found.getName());
            item.put("longitude", found.getLongitude());
            item.put("latitude", found.getLatitude());
            item.put("status", found.getStatus().equals(Constant.STATION_STATUS.OPEN)? true: false);
//            item.put("status", true);
            items.add(item);
        }

        jsonResult.put("stations", items);
        CommonUtil.sendJSONResponse(response, jsonResult);
        
        return null;
    }
    
    @RequestMapping(value = "/getStationListByNear2") 
    public String getStationListByNear2(HttpServletRequest request, HttpServletResponse response) {        
        List<StationVO> stations = webDelegate.getStationList(null, -1, null, null, -1, -1, null, null);
        ArrayList<StationVO> founds = new ArrayList<>();
        
        ArrayList<PointVO> points = new ArrayList<>();
        
        Map<String,String[]> map = request.getParameterMap();
        
        int len = map.size() / 2;
        
        for (int i = 0; i < len; i++) {
            String keyLat = "pointLat_" + i;
            String keyLng = "pointLng_" + i;
            
            double lat = 0, lng = 0;
            
            if (map.containsKey(keyLat))                
                lat = NumberUtil.strToDouble(request.getParameter(keyLat));
            if (map.containsKey(keyLng))                
                lng = NumberUtil.strToDouble(request.getParameter(keyLng));
            
            PointVO point = new PointVO(lng, lat);
            
            points.add(point);
        }
        
        ArrayList<StepVO> steps = new ArrayList<>();
        
        len = points.size()/2;
        
        
        double slat = 0, slng = 0, elat = 0, elng = 0;
        for (int i = 0; i < points.size(); i++) {
            PointVO point = points.get(i);
            if (i == 0) {
                slat = point.getLat();
                slng = point.getLng();
            } else if (i == 1) {
                elat = point.getLat();
                elng = point.getLng();
                StepVO step = new StepVO(slng, slat, elng, elat);
                steps.add(step);                
            } else {
                slat = points.get(i-1).getLat();
                slng = points.get(i-1).getLng();
                elat = point.getLat();
                elng = point.getLng();
                StepVO step = new StepVO(slng, slat, elng, elat);
                steps.add(step);                
            }
                        
        }
        
        
        
        if (steps != null && stations != null) {
//            for (StepVO step: steps) {
//                for (StationVO station: stations) {
//                    if (MapUtil.checkStation(station, step) && !founds.contains(station))
//                        founds.add(station);
//                }
//            }  

            for (int i = 0; i < steps.size(); i++) {
                StepVO step = steps.get(i);
                
                for (int j = 0; j < stations.size(); j++) {
                    StationVO station = stations.get(j);
                                        
                    if (MapUtil.checkStation(station, step, i, j) && !founds.contains(station)) {
                        founds.add(station);
//                        System.out.println("found: i=" + i + ", station=" + station.getId());
                    }   
                }
            }
            
        }            
        
        JSONObject jsonResult = new JSONObject();
        JSONArray items = new JSONArray();
        
        for (StationVO found: founds) {
            JSONObject item = new JSONObject();
            item.put("stationId", found.getId());
            item.put("stationName", found.getName());
            item.put("longitude", found.getLongitude());
            item.put("latitude", found.getLatitude());
            item.put("status", found.getStatus().equals(Constant.STATION_STATUS.OPEN)? true: false);
            items.add(item);
        }

        jsonResult.put("stations", items);
        CommonUtil.sendJSONResponse(response, jsonResult);
        
        return null;
    }
    
    @RequestMapping(value = "/getStationListByNear3") 
    public String getStationListByNear3(HttpServletRequest request, HttpServletResponse response) {     
        double startLng = NumberUtil.strToDouble(request.getParameter("startLng"));
        double startLat = NumberUtil.strToDouble(request.getParameter("startLat"));
        double endLng = NumberUtil.strToDouble(request.getParameter("endLng"));
        double endLat = NumberUtil.strToDouble(request.getParameter("endLat"));
        
        Vector vector = AMapUtil.getPointInfoByDriving(startLng, startLat, endLng, endLat);
        
        ArrayList<PointVO> points = (ArrayList<PointVO>) vector.get(0);
        ArrayList<String> adcodes = (ArrayList<String>) vector.get(1);
        
        if (points == null) {
            CommonUtil.sendMessageResponse(response, "error");
            return null;
        }
        
        List<StationVO> stations = webDelegate.getStationListByNear(adcodes);
        ArrayList<StationVO> founds = new ArrayList<>();
        
        ArrayList<StepVO> steps = new ArrayList<>();        
        
        double slat = 0, slng = 0, elat = 0, elng = 0;
        for (int i = 0; i < points.size(); i++) {
            PointVO point = points.get(i);
            if (i == 0) {
                slat = point.getLat();
                slng = point.getLng();
            } else if (i == 1) {
                elat = point.getLat();
                elng = point.getLng();
                StepVO step = new StepVO(slng, slat, elng, elat);
                steps.add(step);                
            } else {
                slat = points.get(i-1).getLat();
                slng = points.get(i-1).getLng();
                elat = point.getLat();
                elng = point.getLng();
                StepVO step = new StepVO(slng, slat, elng, elat);
                steps.add(step);                
            }
                        
        }
        
        
        
        if (steps != null && stations != null) {
//            for (StepVO step: steps) {
//                for (StationVO station: stations) {
//                    if (MapUtil.checkStation(station, step) && !founds.contains(station))
//                        founds.add(station);
//                }
//            }  

            for (int i = 0; i < steps.size(); i++) {
                StepVO step = steps.get(i);
                
                for (int j = 0; j < stations.size(); j++) {
                    StationVO station = stations.get(j);
                                        
                    if (MapUtil.checkStation(station, step, i, j) && !founds.contains(station)) {
                        founds.add(station);
//                        System.out.println("found: i=" + i + ", station=" + station.getId());
                    }   
                }
            }
        }
        
        JSONObject jsonResult = new JSONObject();
        JSONArray items = new JSONArray();
        
        for (StationVO found: founds) {
            com.alibaba.fastjson.JSONObject driveInfo = AMapUtil.getDrivingInfo(startLng, startLat, found.getLongitude(), found.getLatitude());
            
            JSONObject item = new JSONObject();
            item.put("stationId", found.getId());
            item.put("stationName", found.getName());
            item.put("longitude", found.getLongitude());
            item.put("latitude", found.getLatitude());
            item.put("status", found.getStatus().equals(Constant.STATION_STATUS.OPEN)? true: false);
            item.put("time", driveInfo.get("duration"));
//            item.put("time", "");
            item.put("distance", driveInfo.get("distance"));
//            item.put("distance", "");
            items.add(item);
            
        }

        jsonResult.put("stations", items);
        CommonUtil.sendJSONResponse(response, jsonResult);
        
        return null;
    }
    
    public ModelAndView stationInfo(HttpServletRequest request, HttpServletResponse response) {        
        ModelAndView mav = new ModelAndView();
        
//        request.setAttribute(Constant.ATTRIBUTE.TITLE, "服务区详情");
//        request.setAttribute(Constant.ATTRIBUTE.CLOSE_URL, request.getContextPath() + "/user/home");
//        request.setAttribute(Constant.ATTRIBUTE.CLOSE_URL, "javascript:history.go(-1);");
        
        int stationId = NumberUtil.strToInt(request.getParameter("stationId"));
        
        StationVO station = webDelegate.getStation(stationId);
        List<ShopVO> shops = null;
        
        if (station != null) {
            shops = webDelegate.getShopList(Constant.SHOP_KIND.SERVICE, null, stationId, -1, -1, -1, -1, null, null);
        }
                        
        mav.setViewName("mobileUserStationInfoLayout");
        
        mav.addObject("station", station);
        mav.addObject("shops", shops);
        
        String page = request.getParameter("page");
        double startLat = NumberUtil.strToDouble(request.getParameter("startLat"));
        double startLng = NumberUtil.strToDouble(request.getParameter("startLng"));
        double endLat = NumberUtil.strToDouble(request.getParameter("endLat"));
        double endLng = NumberUtil.strToDouble(request.getParameter("endLng"));
        
        mav.addObject("page", page);
        mav.addObject("startLat", startLat);
        mav.addObject("startLng", startLng);
        mav.addObject("endLat", endLat);
        mav.addObject("endLng", endLng);        
        mav.addObject("stationId", stationId);
        
        return mav;
    }
    
    public ModelAndView station(HttpServletRequest request, HttpServletResponse response) {
//        if (!CommonUtil.isLoginUser(request)) {
//            return new ModelAndView("redirect:/user/wxAuthorize");
//        }
        
        ModelAndView mav = new ModelAndView();
        int stationId = NumberUtil.strToInt(request.getParameter("stationId"));
                
        StationVO station = webDelegate.getStation(stationId);
        
        HttpSession session = request.getSession();
        
        if (station != null) {
            request.setAttribute(Constant.ATTRIBUTE.TITLE, station.getName());
            
            if (session != null)
                session.setAttribute(Constant.SESSION_INFO.STATION_INFO, station);
        }
//        request.setAttribute(Constant.ATTRIBUTE.CLOSE_URL, "javascript:history.go(-1);");                
        
        List<ShopVO> shops = null;
        
        ShopKindVO takeout = webDelegate.getShopKindByName(Constant.SHOP_KIND_NAME.TAKE_OUT);
        
        if (station != null) {
            shops = webDelegate.getShopList(Constant.SHOP_KIND.COMPANY, null, stationId, takeout.getId(), -1, -1, -1, null, null);
        }
                        
        mav.setViewName("mobileUserStationLayout");
        
        mav.addObject("station", station);
        mav.addObject("shops", shops);
        
        boolean isOrderTime = DateUtil.isOrderTime();
        
        if (isOrderTime)
            mav.addObject("orderTime", isOrderTime);
        
        String page = request.getParameter("page");
        double startLat = NumberUtil.strToDouble(request.getParameter("startLat"));
        double startLng = NumberUtil.strToDouble(request.getParameter("startLng"));
        double endLat = NumberUtil.strToDouble(request.getParameter("endLat"));
        double endLng = NumberUtil.strToDouble(request.getParameter("endLng"));
        
        mav.addObject("page", page);
        mav.addObject("startLat", startLat);
        mav.addObject("startLng", startLng);
        mav.addObject("endLat", endLat);
        mav.addObject("endLng", endLng);        
        mav.addObject("stationId", stationId);        
        
        int historyLength = NumberUtil.strToInt(request.getParameter("historyLength"), 0);
        mav.addObject("historyLength", historyLength);
        
        return mav;
    }
    
    public ModelAndView takeout(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        int stationId = NumberUtil.strToInt(request.getParameter("stationId"));
        
        ShopKindVO takeout = webDelegate.getShopKindByName(Constant.SHOP_KIND_NAME.TAKE_OUT);
        
        List<ShopVO> shops = webDelegate.getShopList(Constant.SHOP_KIND.COMPANY, null, stationId, takeout.getId(), -1, -1, -1, null, null);
                        
        mav.setViewName("mobileUserTakeoutLayout");
        mav.addObject("shops", shops);
        
        String page = request.getParameter("page");
        String stationPage = request.getParameter("stationPage");
        double startLat = NumberUtil.strToDouble(request.getParameter("startLat"));
        double startLng = NumberUtil.strToDouble(request.getParameter("startLng"));
        double endLat = NumberUtil.strToDouble(request.getParameter("endLat"));
        double endLng = NumberUtil.strToDouble(request.getParameter("endLng"));
        
        mav.addObject("page", page);
        mav.addObject("stationPage", stationPage);
        mav.addObject("startLat", startLat);
        mav.addObject("startLng", startLng);
        mav.addObject("endLat", endLat);
        mav.addObject("endLng", endLng);        
        mav.addObject("stationId", stationId);    
        
        int historyLength = NumberUtil.strToInt(request.getParameter("historyLength"), 0);
        mav.addObject("historyLength", historyLength);
        
        return mav;
    }
    
    public ModelAndView pharmacy(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        int stationId = NumberUtil.strToInt(request.getParameter("stationId"));
        
        ShopKindVO pharmacy = webDelegate.getShopKindByName(Constant.SHOP_KIND_NAME.PHARMACY);
        
        List<ShopVO> shops = webDelegate.getShopList(Constant.SHOP_KIND.COMPANY, null, stationId, pharmacy.getId(), -1, -1, -1, null, null);
                        
        mav.setViewName("mobileUserPharmacyLayout");
        mav.addObject("shops", shops);
        
        String page = request.getParameter("page");
        String stationPage = request.getParameter("stationPage");
        double startLat = NumberUtil.strToDouble(request.getParameter("startLat"));
        double startLng = NumberUtil.strToDouble(request.getParameter("startLng"));
        double endLat = NumberUtil.strToDouble(request.getParameter("endLat"));
        double endLng = NumberUtil.strToDouble(request.getParameter("endLng"));
        
        mav.addObject("page", page);
        mav.addObject("stationPage", stationPage);
        mav.addObject("startLat", startLat);
        mav.addObject("startLng", startLng);
        mav.addObject("endLat", endLat);
        mav.addObject("endLng", endLng);        
        mav.addObject("stationId", stationId);     
        
        int historyLength = NumberUtil.strToInt(request.getParameter("historyLength"), 0);
        mav.addObject("historyLength", historyLength);
        
        return mav;
    }
    
    public ModelAndView shop(HttpServletRequest request, HttpServletResponse response) {
//        if (!CommonUtil.isLoginUser(request)) {
//            return new ModelAndView("redirect:/user/wxAuthorize");
//        }
//        
//        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);

        request.setAttribute(Constant.ATTRIBUTE.TITLE, "点餐");
        
        ModelAndView mav = new ModelAndView();
        
        mav.setViewName("mobileUserShopLayout");
                
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));        
        ShopVO shop = webDelegate.getShop(shopId);
        
//        int orderId = webDelegate.hasOrder(loginUser.getId(), shopId);
//        OrderVO order; 
//        
//        if (orderId > 0) {
//            order = webDelegate.getOrderInfo(orderId, "", null);
//        } else {
//            order = new OrderVO();
//        }
//        
//        List<GoodsKindVO> goodsKinds = webDelegate.getGoodsKindListByShopId(shopId, loginUser.getId());                 
        
        mav.addObject("shop", shop);
//        mav.addObject("order", order);
//        mav.addObject("goodsKinds", goodsKinds);
        if (shop.getShopKindName().equals(Constant.SHOP_KIND_NAME.PHARMACY)) {
            mav.addObject("pharmacy", "pharmacy");
            request.setAttribute(Constant.ATTRIBUTE.TITLE, "医药");
        }
//        if (goodsKinds != null && !goodsKinds.isEmpty()) {
//            List<GoodsVO> goodsList = webDelegate.getGoodsListByShop(null, -1, shopId, goodsKinds.get(0).getId(), loginUser.getId(), -1, -1, -1, null, null);
//            mav.addObject("goodsList", goodsList);
//            mav.addObject("goodsKindName", goodsKinds.get(0).getName());
//        }
//        
//        boolean isOrderTime = DateUtil.isOrderTime();
//        
//        if (isOrderTime)
//            mav.addObject("orderTime", isOrderTime);
        
        String page = request.getParameter("page");
        String stationPage = request.getParameter("stationPage");
        double startLat = NumberUtil.strToDouble(request.getParameter("startLat"));
        double startLng = NumberUtil.strToDouble(request.getParameter("startLng"));
        double endLat = NumberUtil.strToDouble(request.getParameter("endLat"));
        double endLng = NumberUtil.strToDouble(request.getParameter("endLng"));
        int stationId = NumberUtil.strToInt(request.getParameter("stationId"));
        int backShopId = NumberUtil.strToInt(request.getParameter("backShopId"));
        
        mav.addObject("page", page);
        mav.addObject("stationPage", stationPage);
        mav.addObject("startLat", startLat);
        mav.addObject("startLng", startLng);
        mav.addObject("endLat", endLat);
        mav.addObject("endLng", endLng);        
        mav.addObject("stationId", stationId);    
        mav.addObject("backShopId", backShopId);    
        
        int historyLength = NumberUtil.strToInt(request.getParameter("historyLength"), 0);
        mav.addObject("historyLength", historyLength);
                
        return mav;
    }
    
    public ModelAndView getGoodsKindList(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));
        
        ModelAndView mav = new ModelAndView();
        
        mav.setViewName("goodsKindListLayout");
        
        List<GoodsKindVO> goodsKinds = webDelegate.getGoodsKindListByShopId(shopId, loginUser.getId()); 
        
        mav.addObject("goodsKinds", goodsKinds);
        
        if (goodsKinds != null && !goodsKinds.isEmpty())
            mav.addObject("activeGoodsKind", goodsKinds.get(0).getId());
        
        return mav;
    }
    
    public ModelAndView shopFooter(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        ModelAndView mav = new ModelAndView();
        
        mav.setViewName("shopFooterLayout");
        
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));        
        ShopVO shop = webDelegate.getShop(shopId);
        
        int orderId = webDelegate.hasOrder(loginUser.getId(), shopId);
        OrderVO order; 
        
        if (orderId > 0) {
            order = webDelegate.getOrderInfo(orderId, "", null);
        } else {
            order = new OrderVO();
        }
        
        mav.addObject("shop", shop);
        mav.addObject("order", order);
        
        boolean isOrderTime = DateUtil.isOrderTime();
        
        if (isOrderTime)
            mav.addObject("orderTime", isOrderTime);
        
        return mav;
    }
    
    public ModelAndView getGoodsList(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));
        int goodsKindId = NumberUtil.strToInt(request.getParameter("goodsKindId"));
                
        GoodsKindVO goodsKind = webDelegate.getGoodsKind(goodsKindId);
        
        ModelAndView mav = new ModelAndView();
        
        mav.setViewName("goodsListLayout");
                
        List<GoodsVO> goodsList = webDelegate.getGoodsListByShop(null, -1, shopId, goodsKindId, loginUser.getId(), -1, -1, -1, null, null);
        mav.addObject("goodsList", goodsList);
        if (goodsKind != null)
            mav.addObject("goodsKindName", goodsKind.getName());
        
        boolean isOrderTime = DateUtil.isOrderTime();
        
        if (isOrderTime)
            mav.addObject("orderTime", isOrderTime);
                
        return mav;
    }
    
    public ModelAndView searchPharmacy(HttpServletRequest request, HttpServletResponse response) {
//        if (!CommonUtil.isLoginUser(request)) {
//            return new ModelAndView("redirect:/user/wxAuthorize");
//        }
        
//        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));
        ShopVO shop = webDelegate.getShop(shopId);
        String searchKey = CommonUtil.charsetEncoding(request.getParameter("searchKey"));
//        ShopKindVO pharmacy = webDelegate.getShopKindByName(Constant.SHOP_KIND_NAME.PHARMACY);
//        List<GoodsVO> goodsList = webDelegate.getGoodsListByShop(searchKey, -1, shopId, -1, loginUser.getId(), -1, -1, -1, null, null);
        
//        int orderId = webDelegate.hasOrder(loginUser.getId(), shopId);
//        OrderVO order; 
//        
//        if (orderId > 0) {
//            order = webDelegate.getOrderInfo(orderId, "", searchKey);
//        } else {
//            order = new OrderVO();
//        }
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("mobileUserSearchPharmacyLayout");
//        mav.addObject("goodsList", goodsList);
        mav.addObject("searchKey", searchKey);
//        mav.addObject("order", order);
        mav.addObject("shop", shop);
        
//        request.setAttribute(Constant.ATTRIBUTE.TITLE, shop.getName());
        
        boolean isOrderTime = DateUtil.isOrderTime();
        
        if (isOrderTime)
            mav.addObject("orderTime", isOrderTime);
        
        String page = request.getParameter("page");
        String stationPage = request.getParameter("stationPage");
        double startLat = NumberUtil.strToDouble(request.getParameter("startLat"));
        double startLng = NumberUtil.strToDouble(request.getParameter("startLng"));
        double endLat = NumberUtil.strToDouble(request.getParameter("endLat"));
        double endLng = NumberUtil.strToDouble(request.getParameter("endLng"));
        int stationId = NumberUtil.strToInt(request.getParameter("stationId"));
        int backShopId = NumberUtil.strToInt(request.getParameter("backShopId"));
        
        mav.addObject("page", page);
        mav.addObject("stationPage", stationPage);
        mav.addObject("startLat", startLat);
        mav.addObject("startLng", startLng);
        mav.addObject("endLat", endLat);
        mav.addObject("endLng", endLng);        
        mav.addObject("stationId", stationId);    
        mav.addObject("backShopId", backShopId);  
        
        int historyLength = NumberUtil.strToInt(request.getParameter("historyLength"), 0);
        mav.addObject("historyLength", historyLength);
        
        return mav;
    }
    
    public ModelAndView searchPharmacyFooter(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));
        ShopVO shop = webDelegate.getShop(shopId);
        String searchKey = request.getParameter("searchKey");
        
        int orderId = webDelegate.hasOrder(loginUser.getId(), shopId);
        OrderVO order; 
        
        if (orderId > 0) {
            order = webDelegate.getOrderInfo(orderId, "", searchKey);
        } else {
            order = new OrderVO();
        }
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("shopFooterLayout");
        mav.addObject("order", order);
        mav.addObject("shop", shop);
        
        boolean isOrderTime = DateUtil.isOrderTime();
        
        if (isOrderTime)
            mav.addObject("orderTime", isOrderTime);
        
        return mav;
    }
    
    public ModelAndView getSearchList(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        String searchKey = request.getParameter("searchKey");
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));
//        ShopKindVO pharmacy = webDelegate.getShopKindByName(Constant.SHOP_KIND_NAME.PHARMACY);
        List<GoodsVO> goodsList = webDelegate.getGoodsListByShop(searchKey, -1, shopId, -1, loginUser.getId(), -1, -1, -1, null, null);
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("searchListLayout");
                
        mav.addObject("goodsList", goodsList);
        
        boolean isOrderTime = DateUtil.isOrderTime();
        
        if (isOrderTime)
            mav.addObject("orderTime", isOrderTime);
                
        return mav;
    }
    
    
    
    @RequestMapping("/getGoodsInfo")
    public String getGoodsInfo(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return "redirect:/user/wxAuthorize";
        }
        
        int goodsId = NumberUtil.strToInt(request.getParameter("goodsId"));
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        GoodsVO goods = webDelegate.getGoodsByOrder(goodsId, shopId, loginUser.getId());
        
        if (goods != null) {
            JSONObject json = new JSONObject();
            json.put("id", goods.getId());
            json.put("name", goods.getName());
            json.put("price", NumberUtil.format(goods.getPrice()));
            json.put("description", goods.getDescription());
            json.put("qty", goods.getQty());
            json.put("imagePath", goods.getImagePath());
            json.put("isOrdertime", DateUtil.isOrderTime());
            
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");

            try {
                json.writeJSONString(response.getWriter());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
        }
    
        return null;
    }
    
    @RequestMapping("/getGoodsListInfo")
    public String getGoodsListInfo(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return "redirect:/user/wxAuthorize";
        }
        
        int orderId = NumberUtil.strToInt(request.getParameter("orderId"));
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));
        String searchKey = request.getParameter("searchKey");
                
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        List<GoodsVO> goodsList = null;
        if (orderId != -1)
            goodsList = webDelegate.getGoodsListByOrder(searchKey, -1, loginUser.getId(), orderId, -1, -1, null, null);
        if (shopId != -1)
            goodsList = webDelegate.getGoodsListByShop(searchKey, -1, shopId, -1, loginUser.getId(), -1, -1, -1, null, null);
        
        if (goodsList != null) {
            JSONObject jsonObj = new JSONObject();
            JSONArray rows = new JSONArray();
            for (GoodsVO goods: goodsList) {
                JSONObject item = new JSONObject();
                item.put("id", goods.getId());
                item.put("name", goods.getName());
                item.put("price", NumberUtil.format(goods.getPrice()));
                item.put("qty", goods.getQty());
                item.put("amount", NumberUtil.format(goods.getQty() * goods.getPrice()));
                rows.add(item);
            }
            
            jsonObj.put("goodsList", rows);
            
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");

            try {
                jsonObj.writeJSONString(response.getWriter());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
        }
    
        return null;
    }
    
    @RequestMapping("/setOrder")
    public String setOrder(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            CommonUtil.sendMessageResponse(response, "redirect");
            return null;
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        int goodsId = NumberUtil.strToInt(request.getParameter("goodsId"));
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));
        String searchKey = request.getParameter("searchKey");
//        double price = NumberUtil.strToDouble(request.getParameter("price"));
        int qty = NumberUtil.strToInt(request.getParameter("qty"));
        
        int orderId = webDelegate.hasOrder(loginUser.getId(), shopId);
        
        ShopVO shop = webDelegate.getShop(shopId);
        GoodsVO goods = webDelegate.getGoods(goodsId);
        
        if (orderId == 0) {
            String orderNum = webDelegate.addNewOrder(loginUser.getId(), shopId, shop.getShippingFee(), shop.getDeliverId());
            orderId = webDelegate.getOrderInfo(-1, orderNum, searchKey).getId();
        }
        
        if (qty != 0)
            webDelegate.addOrderDetail(orderId, goodsId, goods.getPrice(), qty);
        
        OrderVO order = webDelegate.getOrderInfo(orderId, "", searchKey);
        
        if (order != null) {
            JSONObject json = new JSONObject();
            json.put("id", order.getId());
            json.put("qty", order.getOrderQty());
            json.put("amount", NumberUtil.format(order.getOrderAmount(), "#0.##"));
            json.put("startFee", shop.getStartFee());
            json.put("shippingFee", shop.getShippingFee());
            
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");

            try {
                json.writeJSONString(response.getWriter());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
        }
    
        return null;
    }
    
    @RequestMapping("/getOrderInfo")
    public String getOrderInfo(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return "redirect:/user/wxAuthorize";
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));
        String searchKey = request.getParameter("searchKey");
        
        int orderId = webDelegate.hasOrder(loginUser.getId(), shopId);
        
        ShopVO shop = webDelegate.getShop(shopId);
                
        OrderVO order = webDelegate.getOrderInfo(orderId, "", searchKey);
        
        if (order != null) {
            JSONObject json = new JSONObject();
            json.put("id", order.getId());
            json.put("qty", order.getOrderQty());
            json.put("amount", order.getOrderAmount());
            json.put("startFee", shop.getStartFee());
            json.put("shippingFee", order.getShippingFee());
            
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");

            try {
                json.writeJSONString(response.getWriter());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
        } else {
            CommonUtil.sendMessageResponse(response, "empty");
        }
    
        return null;
    }
    
    @RequestMapping("/deleteAllOrder")
    public String deleteAllOrder(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return "redirect:/user/wxAuthorize";
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));
        String searchKey = request.getParameter("searchKey");
        
        ShopVO shop = webDelegate.getShop(shopId);
        int orderId = webDelegate.hasOrder(loginUser.getId(), shopId);
        
        if (orderId > 0)
            webDelegate.deleteOrderDetail(orderId, searchKey);
        
        CommonUtil.sendMessageResponse(response, shop.getStartFee()+"");
    
        return null;
    }
    
    @RequestMapping("/getGoodsKindInfo")
    public String getGoodsKindInfo(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return "redirect:/user/wxAuthorize";
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);                
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));
        
        List<GoodsKindVO> goodsKindList = webDelegate.getGoodsKindListByShopId(shopId, loginUser.getId());
        
        if (goodsKindList != null) {
            JSONObject json = new JSONObject();
            JSONArray rows = new JSONArray();
            for (GoodsKindVO goodsKind: goodsKindList) {
                JSONObject item = new JSONObject();
                item.put("id", goodsKind.getId());
                item.put("qty", goodsKind.getQty());
                rows.add(item);
            }
            
            json.put("goodsKind", rows);
            
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");

            try {
                json.writeJSONString(response.getWriter());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        return null;
    }    
    
    public ModelAndView orderSubmit(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        ModelAndView mav = new ModelAndView();
        
        mav.setViewName("mobileUserSubmitLayout");
        
        int orderId = NumberUtil.strToInt(request.getParameter("orderId"));
        String opinion = request.getParameter("opinion");
        String predictTime = request.getParameter("predictTime");
                
        if (orderId <= 0)
            return new ModelAndView("redirect:/user/wxAuthorize");
        
//        String token = request.getParameter("token");
//        
//        if (CommonUtil.isEmptyStr(token) || !token.equals(loginUser.getToken())) 
//            return new ModelAndView("redirect:/user/wxAuthorize");
                
        OrderVO order = webDelegate.getOrderInfo(orderId, "", null);
        
        if (order == null || order.getState() || order.getUserId() != loginUser.getId())
            return new ModelAndView("redirect:/user/wxAuthorize");
        
        ShopVO shop = null;
        
        if (order != null)
            shop = webDelegate.getShop(order.getShopId());
        
        List<OrderDetailVO> orderDetails = webDelegate.getOrderDetailListByGoods(orderId, true);
        
        int fromCoupon = NumberUtil.strToInt(request.getParameter("fromCoupon"));
                
        double couponAmount = 0;
        
        if (fromCoupon == 1) {
            List<OrderCouponVO> orderCoupons = webDelegate.getOrderCouponList(orderId, 0);

            if (orderCoupons != null) {
                for (OrderCouponVO coupon: orderCoupons) {
                    couponAmount += coupon.getAmount();
                }
            }
        } else {
            webDelegate.deleteOrderCoupon(orderId);
        }
        
//        boolean useCoupon = (orderCoupons != null && !orderCoupons.isEmpty());
        
        mav.addObject("shop", shop);
        mav.addObject("order", order);
        mav.addObject("goodsList", orderDetails);
        mav.addObject("couponAmount", couponAmount);   
        mav.addObject("useCoupon", (couponAmount > 0)? true: false);
        mav.addObject("user", loginUser);
        if (predictTime != null) {
            mav.addObject("predictTime", predictTime);
            mav.addObject("selectTime", predictTime.replaceAll("前", ""));
        } else {    
            mav.addObject("predictTime", DateUtil.getPredictTime());
            mav.addObject("selectTime", DateUtil.getPredictTime().replaceAll("前", ""));
        }
        mav.addObject("currentTime", DateUtil.formatDate(new Date(), "HH:mm"));
        mav.addObject("token", loginUser.getToken());
        mav.addObject("opinion", opinion);
        
        String page = request.getParameter("page");
        String stationPage = request.getParameter("stationPage");
        double startLat = NumberUtil.strToDouble(request.getParameter("startLat"));
        double startLng = NumberUtil.strToDouble(request.getParameter("startLng"));
        double endLat = NumberUtil.strToDouble(request.getParameter("endLat"));
        double endLng = NumberUtil.strToDouble(request.getParameter("endLng"));
        int stationId = NumberUtil.strToInt(request.getParameter("stationId"));
        int backShopId = NumberUtil.strToInt(request.getParameter("backShopId"));
        String submitPage = request.getParameter("submitPage");
        String backSearchKey = request.getParameter("backSearchKey");
        
        mav.addObject("page", page);
        mav.addObject("stationPage", stationPage);
        mav.addObject("startLat", startLat);
        mav.addObject("startLng", startLng);
        mav.addObject("endLat", endLat);
        mav.addObject("endLng", endLng);        
        mav.addObject("stationId", stationId);    
        mav.addObject("backShopId", backShopId);  
        mav.addObject("submitPage", submitPage);  
        mav.addObject("backSearchKey", backSearchKey);  
        
        int historyLength = NumberUtil.strToInt(request.getParameter("historyLength"), 0);
        mav.addObject("historyLength", historyLength);
                
        return mav;
    }
    
    public ModelAndView submitContent(HttpServletRequest request, HttpServletResponse response) {
        int orderId = NumberUtil.strToInt(request.getParameter("orderId"));
        OrderVO order = webDelegate.getOrderInfo(orderId, "", null);
        
        ShopVO shop = null;
        
        if (order != null)
            shop = webDelegate.getShop(order.getShopId());
        
        List<OrderDetailVO> orderDetails = webDelegate.getOrderDetailListByGoods(orderId, true);
        
        double couponAmount = 0;
        List<OrderCouponVO> orderCoupons = webDelegate.getOrderCouponList(orderId, 0);

        if (orderCoupons != null) {
            for (OrderCouponVO coupon: orderCoupons) {
                couponAmount += coupon.getAmount();
            }
        }
        
        boolean useCoupon = (orderCoupons != null && !orderCoupons.isEmpty());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("submitContentLayout");
        
        mav.addObject("shop", shop);
        mav.addObject("order", order);
        mav.addObject("goodsList", orderDetails);
        mav.addObject("couponAmount", couponAmount);   
        mav.addObject("useCoupon", useCoupon);
        mav.addObject("deliveryTime", DateUtil.getDeliveryTime());
        
        return mav;
    }
    
//    @RequestMapping(value="/useCoupon", method = RequestMethod.POST)
    public ModelAndView useCoupon(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
//        String token = request.getParameter("token");
        
        int orderId = NumberUtil.strToInt(request.getParameter("orderId"));
        String opinion = request.getParameter("opinion");
        String predictTime = request.getParameter("predictTime");
        
        OrderVO order = webDelegate.getOrderInfo(orderId, "", null);
        
        if (order == null || order.getState() || order.getUserId() != loginUser.getId())
            return new ModelAndView("redirect:/user/wxAuthorize");
        
//        if (CommonUtil.isEmptyStr(token) || !loginUser.getToken().equals(token)) {
//            return new ModelAndView("redirect:/user/wxAuthorize");
//        }
        
        ModelAndView mav = new ModelAndView("mobileUserCouponLayout");     
        
        List<UserCouponVO> coupons = webDelegate.getUserCouponList(loginUser.getId());
        
        if (coupons != null && coupons.isEmpty())
            coupons = null;
        
        mav.addObject("coupons", coupons);
        mav.addObject("useCoupon", true);
        mav.addObject("orderId", orderId);
        mav.addObject("opinion", opinion);
        mav.addObject("predictTime", predictTime);
        
        String page = request.getParameter("page");
        String stationPage = request.getParameter("stationPage");
        double startLat = NumberUtil.strToDouble(request.getParameter("startLat"));
        double startLng = NumberUtil.strToDouble(request.getParameter("startLng"));
        double endLat = NumberUtil.strToDouble(request.getParameter("endLat"));
        double endLng = NumberUtil.strToDouble(request.getParameter("endLng"));
        int stationId = NumberUtil.strToInt(request.getParameter("stationId"));
        int backShopId = NumberUtil.strToInt(request.getParameter("backShopId"));
        String submitPage = request.getParameter("submitPage");
                
        mav.addObject("page", page);
        mav.addObject("stationPage", stationPage);
        mav.addObject("startLat", startLat);
        mav.addObject("startLng", startLng);
        mav.addObject("endLat", endLat);
        mav.addObject("endLng", endLng);        
        mav.addObject("stationId", stationId);    
        mav.addObject("backShopId", backShopId);  
        mav.addObject("submitPage", submitPage);  
        
        int historyLength = NumberUtil.strToInt(request.getParameter("historyLength"), 0);
        mav.addObject("historyLength", historyLength);
        
        return mav;
    }
    
    @RequestMapping("/validCoupon")
    public String validCoupon(HttpServletRequest request, HttpServletResponse response) {
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        if (loginUser == null) {
            CommonUtil.sendMessageResponse(response, "error");
            return null;
        }
        
        int couponId = NumberUtil.strToInt(request.getParameter("couponId"));
        int orderId = NumberUtil.strToInt(request.getParameter("orderId"));
                
        CouponVO Coupon = webDelegate.getCoupon(couponId);
        UserCouponVO userCoupon = webDelegate.getUnusedUserCoupon(loginUser.getId(), couponId);
        OrderVO order = webDelegate.getOrderInfo(orderId, null, null);
        
        if (order == null || Coupon == null || userCoupon == null) 
            CommonUtil.sendMessageResponse(response, "error");
        else if (order.getOrderAmount() < Coupon.getFullValue())
            CommonUtil.sendMessageResponse(response, "error");
        else
            CommonUtil.sendMessageResponse(response, userCoupon.getId()+"");
            
        return null;
    }
    
    @RequestMapping("/consumeCoupon")
    public String consumeCoupon(HttpServletRequest request, HttpServletResponse response) {
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        if (loginUser == null) {
            CommonUtil.sendMessageResponse(response, "error");
            return null;
        }
        
        int couponId = NumberUtil.strToInt(request.getParameter("id"));
        int orderId = NumberUtil.strToInt(request.getParameter("orderId"));
        
//        UserCouponVO userCoupon = webDelegate.getUserCoupon(couponId);
        UserCouponVO userCoupon = webDelegate.getUnusedUserCoupon(loginUser.getId(), couponId);
        OrderVO order = webDelegate.getOrderInfo(orderId, null, null);
        
        if (order == null || userCoupon == null) {
            CommonUtil.sendMessageResponse(response, "error");
        } else {
            webDelegate.addNewOrderCoupon(orderId, userCoupon.getId(), userCoupon.getPrice(), 1);
            CommonUtil.sendSuccessMessageResponse(response);
        }
        
        return null;
            
    }
    
    @RequestMapping("/validOrder")
    public String validOrder(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            CommonUtil.sendMessageResponse(response, "redirect");
            return null;
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        int orderId = NumberUtil.strToInt(request.getParameter("orderId"));
        OrderVO order = webDelegate.getOrderInfo(orderId, "", null);
        
        if (order == null || order.getState() || order.getUserId() != loginUser.getId()) {
            CommonUtil.sendMessageResponse(response, "redirect");
            return null;
        }
        
        CommonUtil.sendSuccessMessageResponse(response);
        
        return null;
    }    
    
    @RequestMapping("/wxPrepay")
    public String wxPrepay(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            CommonUtil.sendMessageResponse(response, "redirect");
            return null;
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        int orderId = NumberUtil.strToInt(request.getParameter("orderId"));
        
        OrderVO order = webDelegate.getOrderInfo(orderId, "", null);
        
        if (order == null || order.getState() || order.getUserId() != loginUser.getId()) {
            CommonUtil.sendMessageResponse(response, "error");
            
        } else {
            ShopVO shop = webDelegate.getShop(order.getShopId()); 
            List<OrderDetailVO> details = webDelegate.getOrderDetailListByPay(orderId);
            
            if (shop == null || details == null) {
                CommonUtil.sendMessageResponse(response, "error");
                return null;
            }
            
            double orderAmount = 0;
            
            for (OrderDetailVO detail: details)
                orderAmount += detail.getQty() * detail.getPrice();
            
            double couponAmount = 0;
            List<OrderCouponVO> orderCoupons = webDelegate.getOrderCouponList(orderId, 0);

            if (orderCoupons != null) {
                for (OrderCouponVO coupon: orderCoupons) {
                    couponAmount += coupon.getAmount();
                }
            }
            
            double shippingFee = shop.getShippingFee();
            
            double amount = orderAmount + shippingFee - couponAmount;
            
            if (amount <= 0) {
                CommonUtil.sendMessageResponse(response, "noPay");
            } else {
                int payAmount = (int)(amount * 100);
    //            int payAmount = 1;

                JSONObject json = WxRequest.getWxPrepayInfo(request, response, loginUser.getWxOpenId(), order.getOrderNum(), payAmount);

                if (json != null)
                    CommonUtil.sendJSONResponse(response, json);
                else
                    CommonUtil.sendMessageResponse(response, "error");
            }
            
        }
        
        return null;
    }
    
    @RequestMapping("/wxConfigInfo")
    public String wxConfigInfo(HttpServletRequest request, HttpServletResponse response) {
//        String url = new String(request.getRequestURL());
        String url = request.getParameter("url");
        
        JSONObject json = WxRequest.getSignatureInfo(url);
            
        if (json != null)
            CommonUtil.sendJSONResponse(response, json);
        else
            CommonUtil.sendMessageResponse(response, "error");
        
        return null;
    }
    
//    @RequestMapping(value="/settleOrder", method = RequestMethod.POST)
    public ModelAndView settleOrder(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        String url = new String(request.getRequestURL());
                
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
//        String token = request.getParameter("token");
        
        String description = request.getParameter("desc");
        String predictTime = request.getParameter("predictTime");
        if (predictTime.lastIndexOf("前") != -1)
            predictTime = predictTime.replaceAll("前", "");
        
//        if (CommonUtil.isEmptyStr(token) || !loginUser.getToken().equals(token)) {
//            return new ModelAndView("redirect:/user/wxAuthorize");
//        }
        
        int orderId = NumberUtil.strToInt(request.getParameter("orderId"));
        
//        if (orderId <= 0)
//            return new ModelAndView("redirect:/user/wxAuthorize");
        
        OrderVO order = webDelegate.getOrder(orderId);
        
//        if (order == null && order.getState() || order.getUserId() != loginUser.getId())
//            return new ModelAndView("redirect:/user/wxAuthorize");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("mobileUserSettleOrderLayout");
        mav.addObject("url", url);
        mav.addObject("orderId", orderId);

        if (order != null && !order.getState() && loginUser != null && order.getUserId() == loginUser.getId()) {
            List<OrderDetailVO> orderDetails = webDelegate.getOrderDetailList(orderId);
        
            if (orderDetails != null) {
                webDelegate.settleOrder(orderId, description, DateUtil.getPredictDateTime(predictTime));
                webDelegate.makeNewOrderDetail(orderId);
            }
            
            String num = DateUtil.formatDate(new Date(), "YYMMddHHmmssSSS");

            List<CouponVO> coupons = webDelegate.getBaseShareCouponList();

            ArrayList<ShareCouponVO> shares = new ArrayList<ShareCouponVO>();

            for (CouponVO coupon: coupons) {
                if (coupon.getNum().equals(Constant.SHARE_COUPON.FULL_30)) {
                    for (int i = 0; i < 10; i++) {
                        ShareCouponVO share = new ShareCouponVO();
                        share.setNum(num);
                        share.setCouponId(coupon.getId());
                        shares.add(share);
                    }
                } else if (coupon.getNum().equals(Constant.SHARE_COUPON.FULL_50)) {
                    for (int i = 0; i < 15; i++) {
                        ShareCouponVO share = new ShareCouponVO();
                        share.setNum(num);
                        share.setCouponId(coupon.getId());
                        shares.add(share);
                    }
                } else if (coupon.getNum().equals(Constant.SHARE_COUPON.FULL_100)) {
                    for (int i = 0; i < 15; i++) {
                        ShareCouponVO share = new ShareCouponVO();
                        share.setNum(num);
                        share.setCouponId(coupon.getId());
                        shares.add(share);
                    }
                } else if (coupon.getNum().equals(Constant.SHARE_COUPON.FULL_150)) {
                    for (int i = 0; i < 15; i++) {
                        ShareCouponVO share = new ShareCouponVO();
                        share.setNum(num);
                        share.setCouponId(coupon.getId());
                        shares.add(share);
                    }
                } else if (coupon.getNum().equals(Constant.SHARE_COUPON.FULL_200)) {
                    for (int i = 0; i < 15; i++) {
                        ShareCouponVO share = new ShareCouponVO();
                        share.setNum(num);
                        share.setCouponId(coupon.getId());
                        shares.add(share);
                    }
                } else if (coupon.getNum().equals(Constant.SHARE_COUPON.UNLIMITED_5)) {
                    for (int i = 0; i < 10; i++) {
                        ShareCouponVO share = new ShareCouponVO();
                        share.setNum(num);
                        share.setCouponId(coupon.getId());
                        shares.add(share);
                    }
                } else if (coupon.getNum().equals(Constant.SHARE_COUPON.UNLIMITED_8)) {
                    for (int i = 0; i < 10; i++) {
                        ShareCouponVO share = new ShareCouponVO();
                        share.setNum(num);
                        share.setCouponId(coupon.getId());
                        shares.add(share);
                    }
                } else if (coupon.getNum().equals(Constant.SHARE_COUPON.UNLIMITED_10)) {
                    for (int i = 0; i < 5; i++) {
                        ShareCouponVO share = new ShareCouponVO();
                        share.setNum(num);
                        share.setCouponId(coupon.getId());
                        shares.add(share);
                    }
                } else if (coupon.getNum().equals(Constant.SHARE_COUPON.UNLIMITED_15)) {
                    for (int i = 0; i < 5; i++) {
                        ShareCouponVO share = new ShareCouponVO();
                        share.setNum(num);
                        share.setCouponId(coupon.getId());
                        shares.add(share);
                    }
                }      
            }

            webDelegate.addNewShareCoupon(shares, orderId);

            String shareUrl;
            if (Constant.DEV_MODE)
                shareUrl = request.getScheme() + "://" + request.getServerName() + ((request.getServerPort() != 80)? ":" + request.getServerPort(): "") + request.getContextPath() + "/advertise?num=" + num;
            else    
                shareUrl = "http://www.gaodkj.com" + request.getContextPath() + "/advertise?num=" + num;
            
            mav.addObject("shareUrl", shareUrl);
            
        } else if (order != null) {
            String shareUrl;
            if (Constant.DEV_MODE)
                shareUrl = request.getScheme() + "://" + request.getServerName() + ((request.getServerPort() != 80)? ":" + request.getServerPort(): "") + request.getContextPath() + "/advertise?num=" + order.getShareNum();
            else    
                shareUrl = "http://www.gaodkj.com" + request.getContextPath() + "/advertise?num=" + order.getShareNum();
            
            mav.addObject("shareUrl", shareUrl);
        }
        
        String page = request.getParameter("page");
        String stationPage = request.getParameter("stationPage");
        double startLat = NumberUtil.strToDouble(request.getParameter("startLat"));
        double startLng = NumberUtil.strToDouble(request.getParameter("startLng"));
        double endLat = NumberUtil.strToDouble(request.getParameter("endLat"));
        double endLng = NumberUtil.strToDouble(request.getParameter("endLng"));
        int stationId = NumberUtil.strToInt(request.getParameter("stationId"));
        int backShopId = NumberUtil.strToInt(request.getParameter("backShopId"));
        String submitPage = request.getParameter("submitPage");
        String backSearchKey = request.getParameter("backSearchKey");
        
        mav.addObject("page", page);
        mav.addObject("stationPage", stationPage);
        mav.addObject("startLat", startLat);
        mav.addObject("startLng", startLng);
        mav.addObject("endLat", endLat);
        mav.addObject("endLng", endLng);        
        mav.addObject("stationId", stationId);    
        mav.addObject("backShopId", backShopId);  
        mav.addObject("submitPage", submitPage);  
        mav.addObject("backSearchKey", backSearchKey);  
        
        int historyLength = NumberUtil.strToInt(request.getParameter("historyLength"), 0);
        
//        Object gaode = request.getSession().getAttribute(Constant.SESSION_INFO.GO_GAODE_MAP);
//        Object baidu = request.getSession().getAttribute(Constant.SESSION_INFO.GO_BAIDU_MAP);
//        
//        if (gaode != null)
//            historyLength += NumberUtil.strToInt(gaode.toString(), 0);        
//        if (baidu != null)
//            historyLength += NumberUtil.strToInt(baidu.toString(), 0);
        
        mav.addObject("historyLength", historyLength);
        
//        request.getSession().removeAttribute(Constant.SESSION_INFO.GO_GAODE_MAP);
//        request.getSession().removeAttribute(Constant.SESSION_INFO.GO_BAIDU_MAP);
        
        StationVO station = (StationVO) request.getSession().getAttribute(Constant.SESSION_INFO.STATION_INFO);
        Gps gps = (Gps) request.getSession().getAttribute(Constant.SESSION_INFO.CURRENT_LOCATION);
        
        if (station != null && gps != null) {
            mav.addObject("stationLat", station.getLatitude());
            mav.addObject("stationLng", station.getLongitude());
            mav.addObject("stationName", station.getName());            
            mav.addObject("region", station.getDistrictParentName());
            mav.addObject("myLng", gps.getWgLon());
            mav.addObject("myLat", gps.getWgLat());
        }    
                
        return mav;
    }
    
    public ModelAndView orders(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        ModelAndView mav = new ModelAndView();
        
        mav.setViewName("mobileUserOrdersLayout");
        
        List<OrderVO> allOrders = webDelegate.getOrderListByUser(true, loginUser.getId(), null, -1);
        List<OrderVO> waitingOrders = webDelegate.getOrderListByUser(true, loginUser.getId(), Constant.ORDER_STATUS.WAITING, -1);
        List<OrderVO> shippingOrders = webDelegate.getOrderListByUser(true, loginUser.getId(), Constant.ORDER_STATUS.SHIPPING, -1);
        List<OrderVO> completedOrders = webDelegate.getOrderListByUser(true, loginUser.getId(), Constant.ORDER_STATUS.COMPLETED, -1);
        
        if (allOrders.isEmpty())
            allOrders = null;
        
        mav.addObject("allOrders", allOrders);
        mav.addObject("waitingOrders", waitingOrders);
        mav.addObject("shippingOrders", shippingOrders);
        mav.addObject("completedOrders", completedOrders);
        
        return mav;
    }
    
    public ModelAndView getOrderList(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        ModelAndView mav = new ModelAndView("userOrderListLayout");    
        
        String tabInfo = "tab_1_1";
        
        if (!CommonUtil.isEmptyStr(request.getParameter("tabInfo")))
            tabInfo = request.getParameter("tabInfo");
        
        List<OrderVO> allOrders = webDelegate.getOrderListByUser(true, loginUser.getId(), null, -1);
        List<OrderVO> waitingOrders = webDelegate.getOrderListByUser(true, loginUser.getId(), Constant.ORDER_STATUS.WAITING, -1);
        List<OrderVO> shippingOrders = webDelegate.getOrderListByUser(true, loginUser.getId(), Constant.ORDER_STATUS.SHIPPING, -1);
        List<OrderVO> completedOrders = webDelegate.getOrderListByUser(true, loginUser.getId(), Constant.ORDER_STATUS.COMPLETED, -1);
        
        if (allOrders.isEmpty())
            allOrders = null;
        
        mav.addObject("allOrders", allOrders);
        mav.addObject("waitingOrders", waitingOrders);
        mav.addObject("shippingOrders", shippingOrders);
        mav.addObject("completedOrders", completedOrders);
        mav.addObject("tabInfo", tabInfo);
        
        return mav;
    }
    
    public ModelAndView order(HttpServletRequest request, HttpServletResponse response) {
        int orderId= NumberUtil.strToInt(request.getParameter("orderId"));
        String orderPage = request.getParameter("orderPage");
        if (orderId <= 0)
            return new ModelAndView("redirect:/user/orders");
        
        ModelAndView mav = new ModelAndView("mobileUserOrderLayout");   
        mav.addObject("orderId", orderId);
        mav.addObject("orderPage", orderPage);
        return mav;
    }
    
    public ModelAndView getOrderContent(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("userOrderContentLayout");  
        
        int orderId= NumberUtil.strToInt(request.getParameter("orderId"));
        
        OrderVO order = webDelegate.getOrder(orderId);
        ShopVO shop = webDelegate.getShop(order.getShopId());
        List<OrderDetailVO> orderDetails = webDelegate.getOrderDetailListByGoods(orderId, false);
        List<OrderCouponVO> orderCoupons = webDelegate.getOrderCouponList(orderId, 1);
        double couponAmount = 0;
        
        DeliverVO deliver = webDelegate.getDeliverById(shop.getDeliverId());
        
        if (orderCoupons != null) {
            for (OrderCouponVO coupon: orderCoupons) {
                couponAmount += coupon.getAmount();
            }
        }
        
//        String deliveryTime = DateUtil.getDeliveryTime(order);
        
        mav.addObject("shop", shop);
        mav.addObject("order", order);
        mav.addObject("goodsList", orderDetails);
        mav.addObject("couponAmount", couponAmount);
        mav.addObject("deliver", deliver);
//        mav.addObject("deliveryTime", deliveryTime);
        mav.addObject("predictTime", DateUtil.formatDateTime(order.getPredictDate(), "HH:mm") + "前");
        
        return mav;
    }
    
    public ModelAndView notification(HttpServletRequest request, HttpServletResponse response) {
//        if (!CommonUtil.isLoginUser(request)) {
//            return new ModelAndView("redirect:/user/wxAuthorize");
//        }
        
        ModelAndView mav = new ModelAndView("mobileUserNotificationLayout");    
        
//        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
//        
//        List<NotificationVO> notifications = webDelegate.getNotificationList(loginUser.getId(), -1, -1, -1, null, null);
//        
//        mav.addObject("notifications", notifications);

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        
        return mav;
    }
    
    public ModelAndView getNotificationList(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        List<NotificationVO> notifications = webDelegate.getNotificationList(loginUser.getId(), -1, -1, -1, null, null);
        
        ModelAndView mav = new ModelAndView("notificationListLayout");    
        mav.addObject("notifications", notifications);
        
        return mav;
    }
    
    @RequestMapping("/setNotificationStatus")
    public String setNotificationStatus(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return "redirect:/user/wxAuthorize";
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        String wxOpenId = loginUser.getWxOpenId();
        
        int notificationId = NumberUtil.strToInt(request.getParameter("notificationId"));
        
        webDelegate.setNotificationStatus(notificationId, loginUser.getId(), -1);
        
        loginUser = webDelegate.getUserByWxOpenId(wxOpenId);
        request.getSession().setAttribute(Constant.SESSION_INFO.LOGIN_USER, loginUser);
        
        CommonUtil.sendSuccessMessageResponse(response);
                
        return null;
    }
    
    public ModelAndView activityNotice(HttpServletRequest request, HttpServletResponse response) {
//        if (!CommonUtil.isLoginUser(request)) {
//            return new ModelAndView("redirect:/user/wxAuthorize");
//        }
        
        int activityNoticeId= NumberUtil.strToInt(request.getParameter("activityNoticeId"));
        
//        if (!CommonUtil.isLoginUser(request)) {
//            if (activityNoticeId != -1) {
//                HttpSession session = request.getSession();
//                session.setAttribute(Constant.SESSION_INFO.ACTIVITY_NOTICE_ID, activityNoticeId);
//            }
//
//            if (Constant.DEV_MODE) {            
//                UserVO user = webDelegate.getUserByWxOpenId(Constant.wxOpenId);
//                HttpSession session = request.getSession();
//                session.removeAttribute(Constant.SESSION_INFO.LOGIN_USER);
//
//                if (user != null) {
//                    webDelegate.updateUserImgUrl(Constant.wxOpenId, Constant.nickName, Constant.imgUrl);
//                    user.setAvatarPath(Constant.imgUrl);
//                    user.setName(Constant.nickName);
//                } else {
//                    webDelegate.addUser(Constant.wxOpenId, Constant.nickName, Constant.imgUrl);
//                    user = webDelegate.getUserByWxOpenId(Constant.wxOpenId);
//                }
//
//                session.setAttribute(Constant.SESSION_INFO.LOGIN_USER, user);
//
//            } else if (CommonUtil.isEmptyStr(request.getParameter("code"))) {
//                String redirectUrl = new String(request.getRequestURL());
//
//                String wxAuthCodeUrl = WxRequest.getAuthCodeUrl(redirectUrl);
//
//                String url = response.encodeRedirectURL(wxAuthCodeUrl);
//
//                return new ModelAndView("redirect:" + url);
//
//            } else {
//                String code = request.getParameter("code");
//
//                String tokenBody = WxRequest.getAccessTokenByCode(code);
//
//                if (tokenBody != null) {                
//                    try {
//                        com.alibaba.fastjson.JSONObject tokenJson = com.alibaba.fastjson.JSONObject.parseObject(tokenBody);                    
//                        String accessToken = tokenJson.getString("access_token");
//                        String openId = tokenJson.getString("openid");
//
//                        String userInfoBody = WxRequest.getUserInfo(accessToken, openId);
//
//                        if (userInfoBody != null) {                        
//                            com.alibaba.fastjson.JSONObject userInfoJson = com.alibaba.fastjson.JSONObject.parseObject(userInfoBody);
//
//                            String wxOpenId = userInfoJson.getString("openid");
//                            String nickName = userInfoJson.getString("nickname");
//                            String imgUrl = userInfoJson.getString("headimgurl");
//
//                            UserVO user = webDelegate.getUserByWxOpenId(wxOpenId);
//
//                            if (user != null) {
//                                webDelegate.updateUserImgUrl(wxOpenId, nickName, imgUrl);
//                                user.setAvatarPath(imgUrl);
//                                user.setName(nickName);
//                            } else {
//                                webDelegate.addUser(wxOpenId, nickName, imgUrl);
//                                user = webDelegate.getUserByWxOpenId(wxOpenId);
//                            }
//
//                            user.setToken(Md5Util.getMd5(user.getWxOpenId()));
//
//                            HttpSession session = request.getSession();
//                            session.setAttribute(Constant.SESSION_INFO.LOGIN_USER, user);
//
//                        } else {
//                            CommonUtil.sendMessageResponse(response, "user info failure");
//                        }
//
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                        CommonUtil.sendMessageResponse(response, "unknow error: " + ex.getMessage());
//                    }         
//                }
//            }
//
//            Object obj = request.getSession().getAttribute(Constant.SESSION_INFO.ACTIVITY_NOTICE_ID);
//
//            if (obj != null)
//                activityNoticeId = NumberUtil.strToInt(obj.toString());
//        }
        
//        int share = NumberUtil.strToInt(request.getParameter("share"));
        
        ActivityNoticeVO activityNotice = webDelegate.getActivityNotice(activityNoticeId);
        
        ModelAndView mav = new ModelAndView("mobileUserActivityNoticeLayout");   
        mav.addObject("activityNotice", activityNotice);
        
//        Date endDate = DateUtil.addDate(activityNotice.getEndDate(), 1);
//        Date now = new Date();
//        
//        if (share > 0 && activityNotice.getStartDate().before(now) && endDate.after(now))
//            mav.addObject("shared", true);


        String shareUrl = "";
        if (Constant.DEV_MODE)
            shareUrl = request.getScheme() + "://" + request.getServerName() + ((request.getServerPort() != 80)? ":" + request.getServerPort(): "") + request.getContextPath() + "/user/activityNotice?activityNoticeId=" + activityNoticeId;
        else    
            shareUrl = "http://www.gaodkj.com" + request.getContextPath() + "/user/activityNotice?activityNoticeId=" + activityNoticeId;
            
        mav.addObject("shareUrl", shareUrl);
        
        return mav;
    }
    
    public ModelAndView activity(HttpServletRequest request, HttpServletResponse response) {
//        if (!CommonUtil.isLoginUser(request)) {
//            return new ModelAndView("redirect:/user/wxAuthorize");
//        }
        
        int activityId= NumberUtil.strToInt(request.getParameter("activityId"));
        
        if (!CommonUtil.isLoginUser(request)) {
            if (activityId != -1) {
                HttpSession session = request.getSession();
                session.setAttribute(Constant.SESSION_INFO.ACTIVITY_ID, activityId);
            }

            if (Constant.DEV_MODE) {            
                UserVO user = webDelegate.getUserByWxOpenId(Constant.wxOpenId);
                HttpSession session = request.getSession();
                session.removeAttribute(Constant.SESSION_INFO.LOGIN_USER);

                if (user != null) {
                    webDelegate.updateUserImgUrl(Constant.wxOpenId, Constant.nickName, Constant.imgUrl);
                    user.setAvatarPath(Constant.imgUrl);
                    user.setName(Constant.nickName);
                } else {
                    webDelegate.addUser(Constant.wxOpenId, Constant.nickName, Constant.imgUrl);
                    user = webDelegate.getUserByWxOpenId(Constant.wxOpenId);
                }

                session.setAttribute(Constant.SESSION_INFO.LOGIN_USER, user);

            } else if (CommonUtil.isEmptyStr(request.getParameter("code"))) {
                String redirectUrl = new String(request.getRequestURL());

                String wxAuthCodeUrl = WxRequest.getAuthCodeUrl(redirectUrl);

                String url = response.encodeRedirectURL(wxAuthCodeUrl);

                return new ModelAndView("redirect:" + url);

            } else {
                String code = request.getParameter("code");

                String tokenBody = WxRequest.getAccessTokenByCode(code);

                if (tokenBody != null) {                
                    try {
                        com.alibaba.fastjson.JSONObject tokenJson = com.alibaba.fastjson.JSONObject.parseObject(tokenBody);                    
                        String accessToken = tokenJson.getString("access_token");
                        String openId = tokenJson.getString("openid");

                        String userInfoBody = WxRequest.getUserInfo(accessToken, openId);

                        if (userInfoBody != null) {                        
                            com.alibaba.fastjson.JSONObject userInfoJson = com.alibaba.fastjson.JSONObject.parseObject(userInfoBody);

                            String wxOpenId = userInfoJson.getString("openid");
                            String nickName = userInfoJson.getString("nickname");
                            String imgUrl = userInfoJson.getString("headimgurl");

                            UserVO user = webDelegate.getUserByWxOpenId(wxOpenId);

                            if (user != null) {
                                webDelegate.updateUserImgUrl(wxOpenId, nickName, imgUrl);
                                user.setAvatarPath(imgUrl);
                                user.setName(nickName);
                            } else {
                                webDelegate.addUser(wxOpenId, nickName, imgUrl);
                                user = webDelegate.getUserByWxOpenId(wxOpenId);
                            }

                            user.setToken(Md5Util.getMd5(user.getWxOpenId()));

                            HttpSession session = request.getSession();
                            session.setAttribute(Constant.SESSION_INFO.LOGIN_USER, user);

                        } else {
                            CommonUtil.sendMessageResponse(response, "user info failure");
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        CommonUtil.sendMessageResponse(response, "unknow error: " + ex.getMessage());
                    }         
                }
            }

            Object obj = request.getSession().getAttribute(Constant.SESSION_INFO.ACTIVITY_ID);

            if (obj != null)
                activityId = NumberUtil.strToInt(obj.toString());
        }        
        
        ActivityVO activity = webDelegate.getActivity(activityId);
        
        ModelAndView mav = new ModelAndView("mobileUserActivityLayout");   
        mav.addObject("activity", activity);
        
        Date endDate = DateUtil.addDate(activity.getEndDate(), 1);
        Date now = new Date();
        
        if (activity.getStartDate().before(now) && endDate.after(now))
            mav.addObject("shared", true);
        
        String shareUrl = "";
        if (Constant.DEV_MODE)
            shareUrl = request.getScheme() + "://" + request.getServerName() + ((request.getServerPort() != 80)? ":" + request.getServerPort(): "") + request.getContextPath() + "/user/activity?activityId=" + activityId;
        else    
            shareUrl = "http://www.gaodkj.com" + request.getContextPath() + "/user/activity?activityId=" + activityId;
            
        mav.addObject("shareUrl", shareUrl);
        
        return mav;
    }
    
    public ModelAndView profile(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        ModelAndView mav = new ModelAndView("mobileUserProfileLayout");           
        
        return mav;
    }
    
    public ModelAndView userCoupon(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        ModelAndView mav = new ModelAndView("mobileUserCouponLayout");     
        String page = request.getParameter("page");
        
        List<UserCouponVO> coupons = webDelegate.getUserCouponList(loginUser.getId());
        
        if (coupons != null && coupons.isEmpty())
            coupons = null;
        
        mav.addObject("coupons", coupons);
        mav.addObject("page", page);
        return mav;
    }
    
    public ModelAndView feedback(HttpServletRequest request, HttpServletResponse response) {
       if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        ModelAndView mav = new ModelAndView("mobileUserFeedbackLayout");                   
        return mav;
    }
    
//    @RequestMapping(value="/addFeedback", method = RequestMethod.POST)
    public String addFeedback(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return "redirect:/user/wxAuthorize";
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        String feedback = request.getParameter("feedback");
        
        webDelegate.addFeedback(loginUser.getId(), -1, feedback);
        
//        return "redirect:/user/feedbackSuccess";
        return null;
    }
    
    public ModelAndView feedbackSuccess(HttpServletRequest request, HttpServletResponse response) {        
        ModelAndView mav = new ModelAndView("mobileUserFeedbackSuccessLayout");                   
        return mav;
    }
    
    public ModelAndView about(HttpServletRequest request, HttpServletResponse response) {
        
        ModelAndView mav = new ModelAndView("mobileUserAboutLayout");                   
        return mav;
    }
    
    public ModelAndView cart(HttpServletRequest request, HttpServletResponse response) {
//        if (!CommonUtil.isLoginUser(request)) {
//            return new ModelAndView("redirect:/user/wxAuthorize");
//        }
//        
//        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
//        
        int stationId = NumberUtil.strToInt(request.getParameter("stationId"));
        
        ModelAndView mav = new ModelAndView();
        
        mav.setViewName("mobileUserCartLayout");
        
//        List<OrderVO> orders = webDelegate.getOrderListByUser(false, loginUser.getId(), Constant.ORDER_STATUS.WAITING, stationId);
                
//        mav.addObject("orders", orders);
        mav.addObject("stationId", stationId);
        
        String page = request.getParameter("page");
        String stationPage = request.getParameter("stationPage");
        double startLat = NumberUtil.strToDouble(request.getParameter("startLat"));
        double startLng = NumberUtil.strToDouble(request.getParameter("startLng"));
        double endLat = NumberUtil.strToDouble(request.getParameter("endLat"));
        double endLng = NumberUtil.strToDouble(request.getParameter("endLng"));
        
        mav.addObject("page", page);
        mav.addObject("stationPage", stationPage);
        mav.addObject("startLat", startLat);
        mav.addObject("startLng", startLng);
        mav.addObject("endLat", endLat);
        mav.addObject("endLng", endLng);        
        
        response.setHeader("Cache-Control", "private, no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("max-age", "0");
        response.setDateHeader("Expires", 0);
        
        int historyLength = NumberUtil.strToInt(request.getParameter("historyLength"), 0);
        mav.addObject("historyLength", historyLength);
        
        return mav;
    }
    
    public ModelAndView getCartList(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        int stationId = NumberUtil.strToInt(request.getParameter("stationId"));
        
        ModelAndView mav = new ModelAndView();
        
        mav.setViewName("cartListLayout");
        
        List<OrderVO> orders = webDelegate.getOrderListByUser(false, loginUser.getId(), Constant.ORDER_STATUS.WAITING, stationId);
                
        mav.addObject("orders", orders);
        
        response.setHeader("Cache-Control", "private, no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("max-age", "0");
        response.setDateHeader("Expires", 0);
        
        return mav;
    }
    
    @RequestMapping("/couponShare")
    public String couponShare(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            CommonUtil.sendMessageResponse(response, Constant.REDIRECT);
            return null;
        }
        
        int orderId = NumberUtil.strToInt(request.getParameter("orderId"));
        
        OrderVO order = webDelegate.getOrder(orderId);
        
        if (order == null || !order.getState()) {
            CommonUtil.sendMessageResponse(response, "error");
            return null;
        }
        
        if (!CommonUtil.isEmptyStr(order.getShareNum())) {
            CommonUtil.sendMessageResponse(response, "fail");
            return null;
        }
                
        String num = DateUtil.formatDate(new Date(), "YYMMddHHmmssSSS");
        
        List<CouponVO> coupons = webDelegate.getBaseShareCouponList();
        
        ArrayList<ShareCouponVO> shares = new ArrayList<ShareCouponVO>();
        
        for (CouponVO coupon: coupons) {
            if (coupon.getNum().equals(Constant.SHARE_COUPON.FULL_30)) {
                for (int i = 0; i < 10; i++) {
                    ShareCouponVO share = new ShareCouponVO();
                    share.setNum(num);
                    share.setCouponId(coupon.getId());
                    shares.add(share);
                }
            } else if (coupon.getNum().equals(Constant.SHARE_COUPON.FULL_50)) {
                for (int i = 0; i < 15; i++) {
                    ShareCouponVO share = new ShareCouponVO();
                    share.setNum(num);
                    share.setCouponId(coupon.getId());
                    shares.add(share);
                }
            } else if (coupon.getNum().equals(Constant.SHARE_COUPON.FULL_100)) {
                for (int i = 0; i < 15; i++) {
                    ShareCouponVO share = new ShareCouponVO();
                    share.setNum(num);
                    share.setCouponId(coupon.getId());
                    shares.add(share);
                }
            } else if (coupon.getNum().equals(Constant.SHARE_COUPON.FULL_150)) {
                for (int i = 0; i < 15; i++) {
                    ShareCouponVO share = new ShareCouponVO();
                    share.setNum(num);
                    share.setCouponId(coupon.getId());
                    shares.add(share);
                }
            } else if (coupon.getNum().equals(Constant.SHARE_COUPON.FULL_200)) {
                for (int i = 0; i < 15; i++) {
                    ShareCouponVO share = new ShareCouponVO();
                    share.setNum(num);
                    share.setCouponId(coupon.getId());
                    shares.add(share);
                }
            } else if (coupon.getNum().equals(Constant.SHARE_COUPON.UNLIMITED_5)) {
                for (int i = 0; i < 10; i++) {
                    ShareCouponVO share = new ShareCouponVO();
                    share.setNum(num);
                    share.setCouponId(coupon.getId());
                    shares.add(share);
                }
            } else if (coupon.getNum().equals(Constant.SHARE_COUPON.UNLIMITED_8)) {
                for (int i = 0; i < 10; i++) {
                    ShareCouponVO share = new ShareCouponVO();
                    share.setNum(num);
                    share.setCouponId(coupon.getId());
                    shares.add(share);
                }
            } else if (coupon.getNum().equals(Constant.SHARE_COUPON.UNLIMITED_10)) {
                for (int i = 0; i < 5; i++) {
                    ShareCouponVO share = new ShareCouponVO();
                    share.setNum(num);
                    share.setCouponId(coupon.getId());
                    shares.add(share);
                }
            } else if (coupon.getNum().equals(Constant.SHARE_COUPON.UNLIMITED_15)) {
                for (int i = 0; i < 5; i++) {
                    ShareCouponVO share = new ShareCouponVO();
                    share.setNum(num);
                    share.setCouponId(coupon.getId());
                    shares.add(share);
                }
            }      
        }
        
        webDelegate.addNewShareCoupon(shares, orderId);
        
        String shareUrl;
        if (Constant.DEV_MODE)
            shareUrl = request.getScheme() + "://" + request.getServerName() + ((request.getServerPort() != 80)? ":" + request.getServerPort(): "") + request.getContextPath() + "/advertise?num=" + num;
        else    
            shareUrl = "http://www.gaodkj.com" + request.getContextPath() + "/advertise?num=" + num;
        
        JSONObject json = new JSONObject();
        json.put("shareUrl", shareUrl);
        
//        CommonUtil.sendMessageResponse(response, shareUrl);
        CommonUtil.sendJSONResponse(response, json);
        
        return null;
        
    }
    
    @RequestMapping("/getBadge")
    public String getBadge(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getParameter("url");
        
        if (!CommonUtil.isEmptyStr(url) && (url.lastIndexOf("activity") != -1 || url.lastIndexOf("activityNotice") != -1)) {
            CommonUtil.sendMessageResponse(response, "no redirect");
            return null;  
        }
        
        if (!CommonUtil.isLoginUser(request)) {
            CommonUtil.sendMessageResponse(response, Constant.REDIRECT);
            return null;
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        UserVO user = webDelegate.getUser(loginUser.getId());
        
        if (user != null) {
//            int badge = user.getBadge();
            int badge = webDelegate.getBadge(user.getId(), 0);
            
            loginUser.setBadge(badge);
            
            request.getSession().setAttribute(Constant.SESSION_INFO.LOGIN_USER, loginUser);
            
//            if (badge > 0)
            CommonUtil.sendMessageResponse(response, badge + "");
        }
        
        return null;
    }
    
    @RequestMapping("/goGaodeMap")
    public String goGaodeMap(HttpServletRequest request, HttpServletResponse response) {        
        Object obj = request.getSession().getAttribute(Constant.SESSION_INFO.GO_GAODE_MAP);
        
        if (obj == null) {
            request.getSession().setAttribute(Constant.SESSION_INFO.GO_GAODE_MAP, 1);
        } else {
            int cnt = NumberUtil.strToInt(obj.toString());
            request.getSession().setAttribute(Constant.SESSION_INFO.GO_GAODE_MAP, cnt+1);
        }
        
        return null;
    }
    
    @RequestMapping("/goBaiduMap")
    public String goBaiduMap(HttpServletRequest request, HttpServletResponse response) {
        Object obj = request.getSession().getAttribute(Constant.SESSION_INFO.GO_BAIDU_MAP);
        
        if (obj == null) {
            request.getSession().setAttribute(Constant.SESSION_INFO.GO_BAIDU_MAP, 1);
        } else {
            int cnt = NumberUtil.strToInt(obj.toString());
            request.getSession().setAttribute(Constant.SESSION_INFO.GO_BAIDU_MAP, cnt+1);
        }
        return null;
    }
}
