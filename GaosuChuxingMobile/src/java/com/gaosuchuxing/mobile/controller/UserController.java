/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.controller;

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
import com.gaosuchuxing.mobile.util.MathUtil;
import com.gaosuchuxing.mobile.util.Md5Util;
import com.gaosuchuxing.mobile.util.NumberUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
                        
            String tokenBody = WxRequest.getAccessToken(code);
            
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
            return "redirect:/user/wxAuthorize";
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
        
        SMSRequest.sendSMS(telNo, verifyCode, "5");
        
        SmsVO sms = new SmsVO();
        sms.setTelNo(telNo);
        sms.setVerifyCode(verifyCode);
        
        request.getSession(true).setAttribute(Constant.SESSION_INFO.SMS_VERIFY_INFO, sms);
        
        CommonUtil.sendMessageResponse(response, "发送成功!");
        
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
        
        if (loginUser == null || !loginUser.getStatus()) {
            CommonUtil.sendMessageResponse(response, "需要用户验证");
            return null;
        } 
        
        String shareNum = request.getParameter("shareNum");
        
        List<ShareCouponVO> shares = webDelegate.getShareCouponList(shareNum);
        
        if (shares == null) {
            CommonUtil.sendMessageResponse(response, "优惠券不存在");
            return null;
        }
        
        int count = 0;
        
        for (ShareCouponVO share: shares) {
            if (share.getStatus())
                count++;
        }
        
        if (count > 10) {
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
                webDelegate.setShareCoupon(c.getId());
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
                
                webDelegate.addNewUserCoupon(loginUser.getId(), coupon.getId(), 1);
                
                CommonUtil.sendJSONResponse(response, json);
                
                return null;
            }
            
            
        } 
            
        CommonUtil.sendMessageResponse(response, "优惠券有错误");
        
        return null;
    }    
    
    @RequestMapping("/validUser")
    public String validUser(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return "redirect:/user/wxAuthorize";
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
        
//        request.setAttribute(Constant.ATTRIBUTE.TITLE, "高速出行");
//        request.setAttribute(Constant.ATTRIBUTE.CLOSE_URL, request.getContextPath() + "/user/home");
        
        return mav;
    }
    
    public String welcomeUser(HttpServletRequest request, HttpServletResponse response) {
        int userId = NumberUtil.strToInt(request.getParameter("id"));
        UserVO user = webDelegate.getUser(userId);
        
        if (user != null && user.getIsNew()) {
            List<CouponVO> coupons = webDelegate.getBaseLoginCouponList();
            if (coupons != null) {
                ArrayList<UserCouponVO> welcomes = new ArrayList<UserCouponVO>();
                
                for (CouponVO coupon: coupons) {
                    UserCouponVO welcome = new UserCouponVO();
                    welcome.setCouponId(coupon.getId());
                    welcome.setUserId(user.getId());
                    welcome.setQty(1);
                    welcome.setValidFromDate(new Date());
                    welcome.setValidToDate(DateUtil.getValidToDate(new Date()));
                    welcome.setStatus(false);
                    welcome.setRegDate(new Date());
                    welcomes.add(welcome);
                }
                
                webDelegate.addWelcomeCoupon(welcomes);
                webDelegate.disableUserIsNew(user.getWxOpenId());
            }
            
            CommonUtil.sendMessageResponse(response, "welcome");
        } else {
            CommonUtil.sendMessageResponse(response, "not");
        }
        
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
        
        String startAddress = CommonUtil.charsetEncoding(request.getParameter("startAddress"));
        String endAddress = CommonUtil.charsetEncoding(request.getParameter("endAddress"));
                
        mav.setViewName("mobileUserSearchRouteLayout");
        mav.addObject("startLat", startLat);
        mav.addObject("startLng", startLng);
        mav.addObject("endLat", endLat);
        mav.addObject("endLng", endLng);
        
        mav.addObject("startAddress", startAddress);
        mav.addObject("endAddress", endAddress);
        
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
        
        for (int i = 0; i < map.size(); i++) {
            String startLng = "startLng_" + i;
            String startLat = "startLat_" + i;
            String endLng = "endLng_" + i;
            String endLat = "endLat_" + i;
            
            StepVO step = new StepVO();
            
            if (map.containsKey(startLng))                
                step.setSlng(NumberUtil.strToDouble(request.getParameter(startLng)));
            if (map.containsKey(startLat))
                step.setSlat(NumberUtil.strToDouble(request.getParameter(startLat)));
            if (map.containsKey(endLng))
                step.setElng(NumberUtil.strToDouble(request.getParameter(endLng)));
            if (map.containsKey(endLat))
                step.setElat(NumberUtil.strToDouble(request.getParameter(endLat)));
            
            steps.add(step);
        }
        
        ArrayList<StationVO> founds = new ArrayList<>();
        
        if (steps != null && stations != null) {
            for (StepVO step: steps) {
                for (StationVO station: stations) {
                    if (MathUtil.checkStation(station, step) && !founds.contains(station))
                        founds.add(station);
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

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            jsonResult.writeJSONString(response.getWriter());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        
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
        
        return mav;
    }
    
    public ModelAndView station(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        ModelAndView mav = new ModelAndView();
        int stationId = NumberUtil.strToInt(request.getParameter("stationId"));
        
        StationVO station = webDelegate.getStation(stationId);
        
        if (station != null)
            request.setAttribute(Constant.ATTRIBUTE.TITLE, station.getName());
        
//        request.setAttribute(Constant.ATTRIBUTE.CLOSE_URL, "javascript:history.go(-1);");                
        
        List<ShopVO> shops = null;
        
        ShopKindVO takeout = webDelegate.getShopKindByName(Constant.SHOP_KIND_NAME.TAKE_OUT);
        
        if (station != null) {
            shops = webDelegate.getShopList(Constant.SHOP_KIND.COMPANY, null, stationId, takeout.getId(), -1, -1, -1, null, null);
        }
                        
        mav.setViewName("mobileUserStationLayout");
        
        mav.addObject("station", station);
        mav.addObject("shops", shops);
        
        return mav;
    }
    
    public ModelAndView takeout(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        int stationId = NumberUtil.strToInt(request.getParameter("stationId"));
        
        ShopKindVO takeout = webDelegate.getShopKindByName(Constant.SHOP_KIND_NAME.TAKE_OUT);
        
        List<ShopVO> shops = webDelegate.getShopList(Constant.SHOP_KIND.COMPANY, null, stationId, takeout.getId(), -1, -1, -1, null, null);
                        
        mav.setViewName("mobileUserTakeoutLayout");
        mav.addObject("shops", shops);
        
        return mav;
    }
    
    public ModelAndView pharmacy(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        int stationId = NumberUtil.strToInt(request.getParameter("stationId"));
        
        ShopKindVO pharmacy = webDelegate.getShopKindByName(Constant.SHOP_KIND_NAME.PHARMACY);
        
        List<ShopVO> shops = webDelegate.getShopList(Constant.SHOP_KIND.COMPANY, null, stationId, pharmacy.getId(), -1, -1, -1, null, null);
                        
        mav.setViewName("mobileUserPharmacyLayout");
        mav.addObject("shops", shops);
        
        return mav;
    }
    
    public ModelAndView shop(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        ModelAndView mav = new ModelAndView();
        
        mav.setViewName("mobileUserShopLayout");
                
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));        
        ShopVO shop = webDelegate.getShop(shopId);
        
        int orderId = webDelegate.hasOrder(loginUser.getId(), shopId);
        OrderVO order; 
        
        if (orderId > 0) {
            order = webDelegate.getOrderInfo(orderId, "", null);
        } else {
            order = new OrderVO();
        }
        
        List<GoodsKindVO> goodsKinds = webDelegate.getGoodsKindListByShopId(shopId, loginUser.getId());                 
        
        mav.addObject("shop", shop);
        mav.addObject("order", order);
        mav.addObject("goodsKinds", goodsKinds);
        if (shop.getShopKindName().equals(Constant.SHOP_KIND_NAME.PHARMACY))
            mav.addObject("pharmacy", "pharmacy");
        
        if (goodsKinds != null && !goodsKinds.isEmpty()) {
            List<GoodsVO> goodsList = webDelegate.getGoodsListByShop(null, -1, shopId, goodsKinds.get(0).getId(), loginUser.getId(), -1, -1, -1, null, null);
            mav.addObject("goodsList", goodsList);
            mav.addObject("goodsKindName", goodsKinds.get(0).getName());
        }
                
        return mav;
    }
    
    public ModelAndView searchPharmacy(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));
        ShopVO shop = webDelegate.getShop(shopId);
        String searchKey = CommonUtil.charsetEncoding(request.getParameter("searchKey"));
//        ShopKindVO pharmacy = webDelegate.getShopKindByName(Constant.SHOP_KIND_NAME.PHARMACY);
        List<GoodsVO> goodsList = webDelegate.getGoodsListByShop(searchKey, -1, shopId, -1, loginUser.getId(), -1, -1, -1, null, null);
        
        int orderId = webDelegate.hasOrder(loginUser.getId(), shopId);
        OrderVO order; 
        
        if (orderId > 0) {
            order = webDelegate.getOrderInfo(orderId, "", searchKey);
        } else {
            order = new OrderVO();
        }
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("mobileUserSearchPharmacyLayout");
        mav.addObject("goodsList", goodsList);
        mav.addObject("searchKey", searchKey);
        mav.addObject("order", order);
        mav.addObject("shop", shop);
        
        request.setAttribute(Constant.ATTRIBUTE.TITLE, shop.getName());
        
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
            return "redirect:/user/wxAuthorize";
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
        
        if (orderId <= 0)
            return new ModelAndView("redirect:/user/wxAuthorize");
        
//        String token = request.getParameter("token");
//        
//        if (CommonUtil.isEmptyStr(token) || !token.equals(loginUser.getToken())) 
//            return new ModelAndView("redirect:/user/wxAuthorize");
                
        OrderVO order = webDelegate.getOrderInfo(orderId, "", null);
        
        if (order == null)
            return new ModelAndView("redirect:/user/wxAuthorize");
        
        ShopVO shop = null;
        
        if (order != null)
            shop = webDelegate.getShop(order.getShopId());
        
        List<OrderDetailVO> orderDetails = webDelegate.getOrderDetailListByGoods(orderId);
        
        double couponAmount = 0;
        List<OrderCouponVO> orderCoupons = webDelegate.getOrderCouponList(orderId);

        if (orderCoupons != null) {
            for (OrderCouponVO coupon: orderCoupons) {
                couponAmount += coupon.getAmount();
            }
        }
        
        boolean useCoupon = (orderCoupons != null && !orderCoupons.isEmpty());
        
        mav.addObject("shop", shop);
        mav.addObject("order", order);
        mav.addObject("goodsList", orderDetails);
        mav.addObject("couponAmount", couponAmount);   
        mav.addObject("useCoupon", useCoupon);
        mav.addObject("user", loginUser);
        mav.addObject("deliveryTime", DateUtil.getDeliveryTime());
        mav.addObject("token", loginUser.getToken());
                
        return mav;
    }
    
    @RequestMapping(value="/useCoupon", method = RequestMethod.POST)
    public ModelAndView useCoupon(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        String token = request.getParameter("token");
        
        int orderId = NumberUtil.strToInt(request.getParameter("orderId"));
        
        if (CommonUtil.isEmptyStr(token) || !loginUser.getToken().equals(token)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        ModelAndView mav = new ModelAndView("mobileUserCouponLayout");     
        
        List<UserCouponVO> coupons = webDelegate.getUserCouponList(loginUser.getId());
        
        if (coupons != null && coupons.isEmpty())
            coupons = null;
        
        mav.addObject("coupons", coupons);
        mav.addObject("useCoupon", true);
        mav.addObject("orderId", orderId);
        
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
        int userCouponId = NumberUtil.strToInt(request.getParameter("id"));
        int orderId = NumberUtil.strToInt(request.getParameter("orderId"));
        
        UserCouponVO userCoupon = webDelegate.getUserCoupon(userCouponId);
        OrderVO order = webDelegate.getOrderInfo(orderId, null, null);
        
        if (order == null || userCoupon == null) {
            CommonUtil.sendMessageResponse(response, "error");
        } else {
            webDelegate.addNewOrderCoupon(orderId, userCouponId, userCoupon.getPrice(), 1);
            CommonUtil.sendSuccessMessageResponse(response);
        }
        
        return null;
            
    }
    
    @RequestMapping("/wxPrepay")
    public String wxPrepay(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return "redirect:/user/wxAuthorize";
        }
        
        int orderId = NumberUtil.strToInt(request.getParameter("orderId"));
        
        OrderVO order = webDelegate.getOrderInfo(orderId, "", null);
        
        if (order == null || order.getState()) {
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
            
            double shippingFee = shop.getShippingFee();
            
            double amount = orderAmount + shippingFee;
            
            int payAmount = (int)(amount * 100);
            
            JSONObject json = WxRequest.getWxPrepayInfo(request, response, order.getOrderNum(), payAmount);
            
            if (json != null)
                CommonUtil.sendJSONResponse(response, json);
            else
                CommonUtil.sendMessageResponse(response, "error");
        }
        
        return null;
    }
    
//    @RequestMapping(value="/settleOrder", method = RequestMethod.POST)
    public ModelAndView settleOrder(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
//        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
//        
//        String token = request.getParameter("token");
//        
//        String description = request.getParameter("desc");
//        
//        if (CommonUtil.isEmptyStr(token) || !loginUser.getToken().equals(token)) {
//            return new ModelAndView("redirect:/user/wxAuthorize");
//        }
//        
//        int orderId = NumberUtil.strToInt(request.getParameter("orderId"));
//        
//        if (orderId <= 0)
//            return new ModelAndView("redirect:/user/wxAuthorize");
//        
//        OrderVO order = webDelegate.getOrder(orderId);
//        
//        if (order == null && order.getState())
//            return new ModelAndView("redirect:/user/wxAuthorize");
//        
//        List<OrderDetailVO> orderDetails = webDelegate.getOrderDetailList(orderId);
//        
//        if (orderDetails == null)
//            return new ModelAndView("redirect:/user/wxAuthorize");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("mobileUserSettleOrderLayout");
                
//        webDelegate.settleOrder(orderId, description);        
        
//        webDelegate.makeNewOrderDetail(orderId);
                
        return mav;
        
    }
    
    public ModelAndView orders(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        ModelAndView mav = new ModelAndView();
        
        mav.setViewName("mobileUserOrdersLayout");
        
        List<OrderVO> allOrders = webDelegate.getOrderListByUser(loginUser.getId(), null);
        List<OrderVO> waitingOrders = webDelegate.getOrderListByUser(loginUser.getId(), Constant.ORDER_STATUS.WAITING);
        List<OrderVO> shippingOrders = webDelegate.getOrderListByUser(loginUser.getId(), Constant.ORDER_STATUS.SHIPPING);
        List<OrderVO> completedOrders = webDelegate.getOrderListByUser(loginUser.getId(), Constant.ORDER_STATUS.COMPLETED);
        
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
        
        List<OrderVO> allOrders = webDelegate.getOrderListByUser(loginUser.getId(), null);
        List<OrderVO> waitingOrders = webDelegate.getOrderListByUser(loginUser.getId(), Constant.ORDER_STATUS.WAITING);
        List<OrderVO> shippingOrders = webDelegate.getOrderListByUser(loginUser.getId(), Constant.ORDER_STATUS.SHIPPING);
        List<OrderVO> completedOrders = webDelegate.getOrderListByUser(loginUser.getId(), Constant.ORDER_STATUS.COMPLETED);
        
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
        
        if (orderId <= 0)
            return new ModelAndView("redirect:/user/orders");
        
        ModelAndView mav = new ModelAndView("mobileUserOrderLayout");   
        mav.addObject("orderId", orderId);
        
        return mav;
    }
    
    public ModelAndView getOrderContent(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("userOrderContentLayout");  
        
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
    
    public ModelAndView notification(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        ModelAndView mav = new ModelAndView("mobileUserNotificationLayout");    
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        List<NotificationVO> notifications = webDelegate.getNotificationList(-1, loginUser.getId(), -1, -1, null, null);
        
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
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        int activityNoticeId= NumberUtil.strToInt(request.getParameter("activityNoticeId"));
        
        ActivityNoticeVO activityNotice = webDelegate.getActivityNotice(activityNoticeId);
        
        ModelAndView mav = new ModelAndView("mobileUserActivityNoticeLayout");   
        mav.addObject("activityNotice", activityNotice);
        
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
        
        List<UserCouponVO> coupons = webDelegate.getUserCouponList(loginUser.getId());
        
        if (coupons != null && coupons.isEmpty())
            coupons = null;
        
        mav.addObject("coupons", coupons);
        
        return mav;
    }
    
    public ModelAndView feedback(HttpServletRequest request, HttpServletResponse response) {
       if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        ModelAndView mav = new ModelAndView("mobileUserFeedbackLayout");                   
        return mav;
    }
    
    @RequestMapping(value="/addFeedback", method = RequestMethod.POST)
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
        if (!CommonUtil.isLoginUser(request)) {
            return new ModelAndView("redirect:/user/wxAuthorize");
        }
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        ModelAndView mav = new ModelAndView();
        
        mav.setViewName("mobileUserCartLayout");
        
        List<OrderVO> orders = webDelegate.getOrderListByUser(loginUser.getId(), Constant.ORDER_STATUS.WAITING);
                
        mav.addObject("orders", orders);
        
        return mav;
    }
    
    @RequestMapping("/couponShare")
    public String couponShare(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLoginUser(request)) {
            return "redirect:/user/wxAuthorize";
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
            } else if (coupon.getNum().equals(Constant.SHARE_COUPON.UNLIMITED_5)) {
                for (int i = 0; i < 5; i++) {
                    ShareCouponVO share = new ShareCouponVO();
                    share.setNum(num);
                    share.setCouponId(coupon.getId());
                    shares.add(share);
                }
            }      
        }
        
        webDelegate.addNewShareCoupon(shares);
        
        String shareUrl;
        if (Constant.DEV_MODE)
            shareUrl = request.getScheme() + "://" + request.getServerName() + ((request.getServerPort() != 80)? ":" + request.getServerPort(): "") + request.getContextPath() + "/advertise?num=" + num;
        else    
            shareUrl = "http://www.gaodkj.com" + request.getContextPath() + "/advertise?num=" + num;
        
        CommonUtil.sendMessageResponse(response, shareUrl);
        
        return null;
        
    }
    
    @RequestMapping("/validTake")
    public String validTake(HttpServletRequest request, HttpServletResponse response) {
        
        
        return null;
    }
}
