/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.controller;

import com.gaosuchuxing.mobile.common.Constant;
import com.gaosuchuxing.mobile.common.WxConfig;
import com.gaosuchuxing.mobile.common.WxRequest;
import com.gaosuchuxing.mobile.delegate.WebDelegate;
import com.gaosuchuxing.mobile.domain.DeliverVO;
import com.gaosuchuxing.mobile.domain.GoodsKindVO;
import com.gaosuchuxing.mobile.domain.GoodsVO;
import com.gaosuchuxing.mobile.domain.OrderCouponVO;
import com.gaosuchuxing.mobile.domain.OrderDetailVO;
import com.gaosuchuxing.mobile.domain.OrderVO;
import com.gaosuchuxing.mobile.domain.ShopKindVO;
import com.gaosuchuxing.mobile.domain.ShopVO;
import com.gaosuchuxing.mobile.domain.StepVO;
import com.gaosuchuxing.mobile.domain.StationVO;
import com.gaosuchuxing.mobile.domain.UserVO;
import com.gaosuchuxing.mobile.util.CommonUtil;
import com.gaosuchuxing.mobile.util.MathUtil;
import com.gaosuchuxing.mobile.util.NumberUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
        if (CommonUtil.isEmptyStr(request.getParameter("code"))) {
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
                    com.alibaba.fastjson.JSONObject tokenJson = com.alibaba.fastjson.JSONObject.parseObject(tokenBody);                    
                    String accessToken = tokenJson.getString("access_token");
                    String openId = tokenJson.getString("openid");
                    
                    String userInfoBody = WxRequest.requestUserInfo(accessToken, openId);
                    
                    if (userInfoBody != null) {
                        System.out.println("userInfo: " + userInfoBody);
                        
                        com.alibaba.fastjson.JSONObject userInfoJson = com.alibaba.fastjson.JSONObject.parseObject(userInfoBody);
                        
                        String wxOpenId = userInfoJson.getString("openid");
                        String nickName = userInfoJson.getString("nickname");
                        String imgUrl = userInfoJson.getString("headimgurl");
                        
                        HttpSession session = request.getSession(true);
                        session.removeAttribute(Constant.SESSION_INFO.LOGIN_USER);
                        
                        UserVO user = webDelegate.getUserByWxOpenId(wxOpenId);
                        
                        if (user != null) {
                            webDelegate.updateUserImgUrl(wxOpenId, imgUrl);
                            user.setAvatarPath(imgUrl);
                        } else {
                            webDelegate.addUser(wxOpenId, nickName, imgUrl);
                            user = webDelegate.getUserByWxOpenId(wxOpenId);
                        }
                        
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
    
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        
        mav.setViewName("mobileUserHomeLayout");
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        if (loginUser != null && loginUser.getIsNew()) {
            mav.addObject("welcome", true);
            webDelegate.disableUserIsNew(loginUser.getWxOpenId());
        }
        
//        request.setAttribute(Constant.ATTRIBUTE.TITLE, "高速出行");
//        request.setAttribute(Constant.ATTRIBUTE.CLOSE_URL, request.getContextPath() + "/user/home");
        
        return mav;
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
        request.setAttribute(Constant.ATTRIBUTE.CLOSE_URL, "javascript:history.go(-1);");
        
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
        ModelAndView mav = new ModelAndView();
        int stationId = NumberUtil.strToInt(request.getParameter("stationId"));
        
        StationVO station = webDelegate.getStation(stationId);
        
        if (station != null)
            request.setAttribute(Constant.ATTRIBUTE.TITLE, station.getName());
        
        request.setAttribute(Constant.ATTRIBUTE.CLOSE_URL, "javascript:history.go(-1);");                
        
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
    
    public ModelAndView shop(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        
        mav.setViewName("mobileUserShopLayout");
                
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));        
        ShopVO shop = webDelegate.getShop(shopId);
        
        List<GoodsKindVO> goodsKinds = webDelegate.getGoodsKindListByShopId(shopId, -1);                 
        
        mav.addObject("shop", shop);
        mav.addObject("goodsKinds", goodsKinds);
        
        if (goodsKinds != null && !goodsKinds.isEmpty()) {
            List<GoodsVO> goodsList = webDelegate.getGoodsList(null, -1, shopId, goodsKinds.get(0).getId(), -1, -1, null, null);
            mav.addObject("goodsList", goodsList);
            mav.addObject("goodsKindName", goodsKinds.get(0).getName());
        }
                
        return mav;
    }
    
    public ModelAndView getGoodsList(HttpServletRequest request, HttpServletResponse response) {
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));
        int goodsKindId = NumberUtil.strToInt(request.getParameter("goodsKindId"));
        
        GoodsKindVO goodsKind = webDelegate.getGoodsKind(goodsKindId);
        
        ModelAndView mav = new ModelAndView();
        
        mav.setViewName("goodsListLayout");
                
        List<GoodsVO> goodsList = webDelegate.getGoodsList(null, -1, shopId, goodsKindId, -1, -1, null, null);
        mav.addObject("goodsList", goodsList);
        mav.addObject("goodsKindName", goodsKind.getName());
                
        return mav;
    }
    
    @RequestMapping("/getGoodsInfo")
    public String getGoodsInfo(HttpServletRequest request, HttpServletResponse response) {
        int goodsId = NumberUtil.strToInt(request.getParameter("goodsId"));
        int userId = 0;
        
        UserVO loginUser = (UserVO) request.getSession().getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        if (loginUser != null) {
            userId = loginUser.getId();
        }
        
        GoodsVO goods = webDelegate.getGoodsByOrder(goodsId, userId);
        
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
    
    public ModelAndView submit(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        
        mav.setViewName("mobileUserSubmitLayout");
        
        int orderId = NumberUtil.strToInt(request.getParameter("orderId"));
        
        OrderVO order = webDelegate.getOrder(orderId);
        ShopVO shop = null;
        
        if (order != null)
            shop = webDelegate.getShop(order.getShopId());
        
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
}
