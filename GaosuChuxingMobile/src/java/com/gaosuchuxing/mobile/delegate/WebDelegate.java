/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.delegate;

import com.gaosuchuxing.mobile.domain.ActivityVO;
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
import com.gaosuchuxing.mobile.domain.StationVO;
import com.gaosuchuxing.mobile.domain.UserCouponVO;
import com.gaosuchuxing.mobile.domain.UserVO;
import com.gaosuchuxing.mobile.service.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WebDelegate {
    
    private WebService webService;

    /**
     * Get the value of webService
     *
     * @return the value of webService
     */
    public WebService getWebService() {
        return webService;
    }

    /**
     * Set the value of webService
     *
     * @param webService new value of webService
     */
    public void setWebService(WebService webService) {
        this.webService = webService;
    }
    
    public List<StationVO> getStationListByNear(ArrayList<String> adcodes) {
        return webService.getStationListByNear(adcodes);
    }
    
    public List<StationVO> getStationList(String keyword, int districtId, String adcode, String status, int offset, int size, String sortColumn, String sort) {
        return webService.getStationList(keyword, districtId, adcode, status, offset, size, sortColumn, sort);
    }

    public int countAllStation(String keyword, int districtId, String adcode, String status) {
        return webService.countAllStation(keyword, districtId, adcode, status);
    }

    public StationVO getStation(int id) {
        return webService.getStation(id);
    }

    public StationVO getStationByName(String stationName) {
        return webService.getStationByName(stationName);
    }
    
    
    public List<GoodsKindVO> getGoodsKindList(int shopKindId, String name, int offset, int size, String sortColumn, String sort) {
        return webService.getGoodsKindList(shopKindId, name, offset, size, sortColumn, sort);
    }

    
    public GoodsKindVO getGoodsKind(int id) {
        return webService.getGoodsKind(id);
    }
    
    public List<ShopKindVO> getShopKindList(String keyword, int offset, int size, String sortColumn, String sort) {
        return webService.getShopKindList(keyword, offset, size, sortColumn, sort);
    }
    
    public ShopKindVO getShopKind(int shopKindId) {
        return webService.getShopKind(shopKindId);
    }
    
    public ShopKindVO getShopKindByName(String shopKindName) {
        return webService.getShopKindByName(shopKindName);
    }
    
    public List<ShopVO> getShopList(String type, String keyword, int stationId, int shopKindId, int districtId, int offset, int size, String sortColumn, String sort) {
        return webService.getShopList(type, keyword, stationId, shopKindId, districtId, offset, size, sortColumn, sort);
    }
    
    public int countAllShop(String type, String keyword, int stationId, int shopKindId, int districtId) {
        return webService.countAllShop(type, keyword, stationId, shopKindId, districtId);
    }
    
    public ShopVO getShop(int shopId) {
        return webService.getShop(shopId);
    }
    
    public ShopVO getShopByName(String shopName) {
        return webService.getShopByName(shopName);
    }
    
    public List<OrderVO> getOrderList(int state, int deliverId, String orderNum, String userName, String deliverName, String orderStatus, String from, String to, int offset, int size, String sortColumn, String sort) {
        return webService.getOrderList(state, deliverId, orderNum, userName, deliverName, orderStatus, from, to, offset, size, sortColumn, sort);
    }
    
    public List<OrderVO> getOrderListByDeliver(int deliverId, String orderStatus) {
        return webService.getOrderListByDeliver(deliverId, orderStatus);
    }
    
    public List<OrderVO> getOrderListByUser(boolean state, int userId, String orderStatus, int stationId) {
        return webService.getOrderListByUser(state, userId, orderStatus, stationId);
    }
    
    public int countAllOrder(int state, int deliverId, String orderNum, String userName, String deliverName, String orderStatus, String from, String to) {
        return webService.countAllOrder(state, deliverId, orderNum, userName, deliverName, orderStatus, from, to);
    }
    
    public OrderVO getOrder(int orderId) {
        return webService.getOrder(orderId);
    }

    public void setOrderStatus(int orderId, String status) {
        webService.setOrderStatus(orderId, status);
    }        
    
    public List<OrderDetailVO> getOrderDetailList(int orderId) {
        return webService.getOrderDetailList(orderId);
    }

    public List<OrderDetailVO> getOrderDetailListByGoods(int orderId, boolean isPay) {
        return webService.getOrderDetailListByGoods(orderId, isPay);
    }

    public List<OrderCouponVO> getOrderCouponList(int orderId, int status) {
        return webService.getOrderCouponList(orderId, status);
    }
    
    public List<GoodsVO> getGoodsListByShop(String keyword, int shopKindId, int shopId, int goodsKindId, int userId, int orderId, int offset, int size, String sortColumn, String sort) {
        return webService.getGoodsListByShop(keyword, shopKindId, shopId, goodsKindId, userId, orderId, offset, size, sortColumn, sort);
    }
    
    public List<GoodsVO> getGoodsListByOrder(String keyword, int goodsKindId, int userId, int orderId, int offset, int size, String sortColumn, String sort) {
        return webService.getGoodsListByOrder(keyword, goodsKindId, userId, orderId, offset, size, sortColumn, sort);
    }
    
    public int countAllGoodsByOrder(String keyword, int shopKindId, int shopId, int goodsKindId) {
        return webService.countAllGoodsByOrder(keyword, shopKindId, shopId, goodsKindId);
    }
    
    public GoodsVO getGoods(int id) {
        return webService.getGoods(id);
    }
    
    public GoodsVO getGoodsByName(String name) {
        return webService.getGoodsByName(name);
    }
    
    public List<GoodsKindVO> getGoodsKindListByShopId(int shopId, int userId) {
        return webService.getGoodsKindListByShopId(shopId, userId);
    }
    
    public DeliverVO getDeliver(String deliverId) {
        return webService.getDeliver(deliverId);
    }
    
    public DeliverVO getDeliverById(int id) {
        return webService.getDeliverById(id);
    }
    
    public List<DeliverVO> getDeliverList(String keyword, int offset, int size, String sortColumn, String sort) {
        return webService.getDeliverList(keyword, offset, size, sortColumn, sort);
    }
    
    public int countAllDeliver(String keyword) {
        return webService.countAllDeliver(keyword);
    }
    
    public void setDeliverPassword(int deliverId, String password) {
        webService.setDeliverPassword(deliverId, password);
    }
    
    public List<NotificationVO> getNotificationList(int userId, int deliverId, int offset, int size, String sortColumn, String sort) {
        return webService.getNotificationList(userId, deliverId, offset, size, sortColumn, sort);
    }

    public void addNewNotification(NotificationVO notification) {
        webService.addNewNotification(notification);
    }

    public void setNotificationStatus(int notificationId, int userId, int deliverId) {
        webService.setNotificationStatus(notificationId, userId, deliverId);
    }
    
    public ActivityNoticeVO getActivityNotice(int id) {
        return webService.getActivityNotice(id);
    }
    
    public List<StationVO> getStationByDeliver(int deliverId) {
        return webService.getStationByDeliver(deliverId);
    }

    public void addFeedback(int userId, int deliverId, String feedback) {
        webService.addFeedback(userId, deliverId, feedback);
    }
    
    public int getOrderCountByDeliver(int deliverId) {
        return webService.getOrderCountByDeliver(deliverId);
    }
     
    public GoodsVO getGoodsByOrder(int goodsId, int shopId, int userId) {
        return webService.getGoodsByOrder(goodsId, shopId, userId);
    }

    public UserVO getUserByWxOpenId(String wxOpenId) {
        return webService.getUserByWxOpenId(wxOpenId);
    }

    public void addUser(String wxOpenId, String name, String imgUrl) {
        webService.addUser(wxOpenId, name, imgUrl);
    }

    public void activeUser(String wxOpenId, String telNo) {
        webService.activeUser(wxOpenId, telNo);
    } 
    
    public void updateUserImgUrl(String wxOpenId, String nick, String imgUrl) {
        webService.updateUserImgUrl(wxOpenId, nick, imgUrl);
    }
    
    public void disableUserIsNew(String wxOpenId) {
        webService.disableUserIsNew(wxOpenId);
    }
    
    public UserVO getUser(int userId) {
        return webService.getUser(userId);
    }
        
    public int hasOrder(int userId, int shopId) {
        return webService.hasOrder(userId, shopId);
    }

    public String addNewOrder(int userId, int shopId, double shippingFee, int deliverId) {
        return webService.addNewOrder(userId, shopId, shippingFee, deliverId);
    }

    public OrderVO getOrderInfo(int orderId, String orderNum, String searchKey) {
        return webService.getOrderInfo(orderId, orderNum, searchKey);
    }

    public String addOrderDetail(int orderId, int goodsId, double price, int qty) {
        return webService.addOrderDetail(orderId, goodsId, price, qty);
    }

    public void addNewOrderCoupon(int orderId, int userCouponId, double amount, int qty) {
        webService.addNewOrderCoupon(orderId, userCouponId, amount, qty);
    }

    public void settleOrder(int orderId, String description, Date predictTime) {
        webService.settleOrder(orderId, description, predictTime);
    }
    
    public void makeNewOrderDetail(int orderId) {
        webService.makeNewOrderDetail(orderId);
    }

    public void deleteOrderDetail(int orderId, String searchKey) {
        webService.deleteOrderDetail(orderId, searchKey);
    }
    
    public List<CouponVO> getBaseLoginCouponList() {
        return webService.getBaseLoginCouponList();
    }
    
    public List<CouponVO> getBaseShareCouponList() {
        return webService.getBaseShareCouponList();
    }

    public CouponVO getCoupon(int id) {
        return webService.getCoupon(id);
    }

    public List<UserCouponVO> getUserCouponList(int userId) {
        return webService.getUserCouponList(userId);
    }
    
    public void addNewUserCoupon(int userId, int couponId, int qty, int noticeId) {
        webService.addNewUserCoupon(userId, couponId, qty, noticeId);
    }
    
    public int getConsumeUserCouponIdByUser(int userId, int couponId) {
        return webService.getConsumeUserCouponIdByUser(userId, couponId);
    }

    public boolean consumeCoupon(int userId, int couponId, int orderId) {
        return webService.consumeCoupon(userId, couponId, orderId);
    }
    
    public List<OrderDetailVO> getOrderDetailListByPay(int orderId) {
        return webService.getOrderDetailListByPay(orderId);
    }
    
    public void addWelcomeCoupon(ArrayList<UserCouponVO> welcomes) {
        webService.addWelcomeCoupon(welcomes);
    }
    
    public UserCouponVO getUserCoupon(int id) {
        return webService.getUserCoupon(id);
    }
    
    public UserCouponVO getUnusedUserCoupon(int userId, int couponId) {
        return webService.getUnusedUserCoupon(userId, couponId);
    }
        
    public void addNewShareCoupon(ArrayList<ShareCouponVO> shares, int orderId) {
        webService.addNewShareCoupon(shares, orderId);
    }
    
    public List<ShareCouponVO> getShareCouponList(String num) {
        return webService.getShareCouponList(num);
    }
    
    public ShareCouponVO getShareCoupon(String id) {
        return webService.getShareCoupon(id);
    }
    
    public void setShareCoupon(long id, int userId) {
        webService.setShareCoupon(id, userId);
    }
    
    public void addNewCoupon(CouponVO coupon) {
        webService.addNewCoupon(coupon);
    }
    
    public List<CouponVO> getBaseFullTypeCouponList() {
        return webService.getBaseFullTypeCouponList();
    }
    
    public List<CouponVO> getBaseUnlimitedCouponList() {
        return webService.getBaseUnlimitedCouponList();
    }
    
    public void deleteShareCoupon(String num) {
        webService.deleteShareCoupon(num);
    }
    
    
    public boolean isLimitedShareCoupon(String num, int size) {
        return webService.isLimitedShareCoupon(num, size);
    }
    
    public void addNotification(int userId, int deliverId, int activityNoticeId, int orderId, String title, String description) {
        webService.addNotification(userId, deliverId, activityNoticeId, orderId, title, description);
    }
    
    public UserVO getUserById(String userId) {
        return webService.getUserById(userId);
    }
    
    public ActivityVO getActivity(int id) {
        return webService.getActivity(id);
    }
    
    public void deleteOrderCoupon(int orderId) {
        webService.deleteOrderCoupon(orderId);
    }
    
    public void updateDeliverAccessToken(int deliverId, String accessToken) {
        webService.updateDeliverAccessToken(deliverId, accessToken);
    }
}
