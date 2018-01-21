/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.service.impl;

import com.gaosuchuxing.mobile.domain.ActivityVO;
import com.gaosuchuxing.mobile.dao.WebDAO;
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

public class ServiceImpl implements WebService {
    
    private WebDAO webDAO;

    /**
     * Get the value of webDAO
     *
     * @return the value of webDAO
     */
    public WebDAO getWebDAO() {
        return webDAO;
    }

    /**
     * Set the value of webDAO
     *
     * @param webDAO new value of webDAO
     */
    public void setWebDAO(WebDAO webDAO) {
        this.webDAO = webDAO;
    }

    @Override
    public List<StationVO> getStationListByNear(ArrayList<String> adcodes) {
        return webDAO.getStationListByNear(adcodes);
    }

    @Override
    public List<StationVO> getStationList(String keyword, int districtId, String adcode, String status, int offset, int size, String sortColumn, String sort) {
        return webDAO.getStationList(keyword, districtId, adcode, status, offset, size, sortColumn, sort);
    }

    @Override
    public int countAllStation(String keyword, int districtId, String adcode, String status) {
        return webDAO.countAllStation(keyword, districtId, adcode, status);
    }

    @Override
    public StationVO getStation(int id) {
        return webDAO.getStation(id);
    }

    @Override
    public StationVO getStationByName(String stationName) {
        return webDAO.getStationByName(stationName);
    }

    @Override
    public List<GoodsKindVO> getGoodsKindList(int shopKindId, String name, int offset, int size, String sortColumn, String sort) {
        return webDAO.getGoodsKindList(shopKindId, name, offset, size, sortColumn, sort);
    }

    @Override
    public GoodsKindVO getGoodsKind(int id) {
        return webDAO.getGoodsKind(id);
    }

    @Override
    public List<ShopKindVO> getShopKindList(String keyword, int offset, int size, String sortColumn, String sort) {
        return webDAO.getShopKindList(keyword, offset, size, sortColumn, sort);
    }

    @Override
    public ShopKindVO getShopKind(int shopKindId) {
        return webDAO.getShopKind(shopKindId);
    }

    @Override
    public ShopKindVO getShopKindByName(String shopKindName) {
        return webDAO.getShopKindByName(shopKindName);
    }

    @Override
    public List<ShopVO> getShopList(String type, String keyword, int stationId, int shopKindId, int districtId, int offset, int size, String sortColumn, String sort) {
        return webDAO.getShopList(type, keyword, stationId, shopKindId, districtId, offset, size, sortColumn, sort);
    }

    @Override
    public int countAllShop(String type, String keyword, int stationId, int shopKindId, int districtId) {
        return webDAO.countAllShop(type, keyword, stationId, shopKindId, districtId);
    }

    @Override
    public ShopVO getShop(int shopId) {
        return webDAO.getShop(shopId);
    }

    @Override
    public ShopVO getShopByName(String shopName) {
        return webDAO.getShopByName(shopName);
    }

    @Override
    public List<OrderVO> getOrderList(int state, int deliverId, String orderNum, String userName, String deliverName, String orderStatus, String from, String to, int offset, int size, String sortColumn, String sort) {
        return webDAO.getOrderList(state, deliverId, orderNum, userName, deliverName, orderStatus, from, to, offset, size, sortColumn, sort);
    }

    @Override
    public List<OrderVO> getOrderListByDeliver(int deliverId, String orderStatus) {
        return webDAO.getOrderListByDeliver(deliverId, orderStatus);
    }
    
    @Override
    public List<OrderVO> getOrderListByUser(boolean state, int userId, String orderStatus, int stationId) {
        return webDAO.getOrderListByUser(state, userId, orderStatus, stationId);
    }

    @Override
    public int countAllOrder(int state, int deliverId, String orderNum, String userName, String deliverName, String orderStatus, String from, String to) {
        return webDAO.countAllOrder(state, deliverId, orderNum, userName, deliverName, orderStatus, from, to);
    }

    @Override
    public OrderVO getOrder(int orderId) {
        return webDAO.getOrder(orderId);
    }

    @Override
    public void setOrderStatus(int orderId, String status) {
        webDAO.setOrderStatus(orderId, status);
    }    

    @Override
    public List<OrderDetailVO> getOrderDetailList(int orderId) {
        return webDAO.getOrderDetailList(orderId);
    }

    @Override
    public List<OrderDetailVO> getOrderDetailListByGoods(int orderId, boolean isPay) {
        return webDAO.getOrderDetailListByGoods(orderId, isPay);
    }

    @Override
    public List<OrderCouponVO> getOrderCouponList(int orderId, int status) {
        return webDAO.getOrderCouponList(orderId, status);
    }
    
    
    @Override
    public List<GoodsVO> getGoodsListByShop(String keyword, int shopKindId, int shopId, int goodsKindId, int userId, int orderId, int offset, int size, String sortColumn, String sort) {
        return webDAO.getGoodsListByShop(keyword, shopKindId, shopId, goodsKindId, userId, orderId, offset, size, sortColumn, sort);
    }

    @Override
    public List<GoodsVO> getGoodsListByOrder(String keyword, int goodsKindId, int userId, int orderId, int offset, int size, String sortColumn, String sort) {
        return webDAO.getGoodsListByOrder(keyword, goodsKindId, userId, orderId, offset, size, sortColumn, sort);
    }

    @Override
    public int countAllGoodsByOrder(String keyword, int shopKindId, int shopId, int goodsKindId) {
        return webDAO.countAllGoodsByShop(keyword, shopKindId, shopId, goodsKindId);
    }

    @Override
    public GoodsVO getGoods(int id) {
        return webDAO.getGoods(id);
    }

    @Override
    public GoodsVO getGoodsByName(String name) {
        return webDAO.getGoodsByName(name);
    }

    @Override
    public List<GoodsKindVO> getGoodsKindListByShopId(int shopId, int userId) {
        return webDAO.getGoodsKindListByShopId(shopId, userId);
    }
    
    @Override
    public DeliverVO getDeliver(String deliverId) {
        return webDAO.getDeliver(deliverId);
    }
    
    @Override
    public DeliverVO getDeliverById(int id) {
        return webDAO.getDeliverById(id);
    }
    
    @Override
    public List<DeliverVO> getDeliverList(String keyword, int offset, int size, String sortColumn, String sort) {
        return webDAO.getDeliverList(keyword, offset, size, sortColumn, sort);
    }
    
    @Override
    public int countAllDeliver(String keyword) {
        return webDAO.countAllDeliver(keyword);
    }
    
    @Override
    public void setDeliverPassword(int deliverId, String password) {
        webDAO.setDeliverPassword(deliverId, password);
    }

    @Override
    public List<NotificationVO> getNotificationList(int userId, int deliverId, int offset, int size, String sortColumn, String sort) {
        return webDAO.getNotificationList(userId, deliverId, offset, size, sortColumn, sort);
    }

    @Override
    public void addNewNotification(NotificationVO notification) {
        webDAO.addNewNotification(notification);
    }

    @Override
    public void setNotificationStatus(int notificationId, int userId, int deliverId) {
        webDAO.setNotificationStatus(notificationId, userId, deliverId);
    }

    @Override
    public ActivityNoticeVO getActivityNotice(int id) {
        return webDAO.getActivityNotice(id);
    }

    @Override
    public List<StationVO> getStationByDeliver(int deliverId) {
        return webDAO.getStationByDeliver(deliverId);
    }

    @Override
    public void addFeedback(int userId, int deliverId, String feedback) {
        webDAO.addFeedback(userId, deliverId, feedback);
    }

    @Override
    public int getOrderCountByDeliver(int deliverId) {
        return webDAO.getOrderCountByDeliver(deliverId);
    }

    @Override
    public GoodsVO getGoodsByOrder(int goodsId, int shopId, int userId) {
        return webDAO.getGoodsByOrder(goodsId, shopId, userId);
    }

    @Override
    public UserVO getUserByWxOpenId(String wxOpenId) {
        return webDAO.getUserByWxOpenId(wxOpenId);
    }

    @Override
    public void addUser(String wxOpenId, String name, String imgUrl) {
        webDAO.addUser(wxOpenId, name, imgUrl);
    }

    @Override
    public void activeUser(String wxOpenId, String telNo) {
        webDAO.activeUser(wxOpenId, telNo);
    }

    @Override
    public void updateUserImgUrl(String wxOpenId, String nick, String imgUrl) {
        webDAO.updateUserImgUrl(wxOpenId, nick, imgUrl);
    }

    @Override
    public void disableUserIsNew(String wxOpenId) {
        webDAO.disableUserIsNew(wxOpenId);
    }

    @Override
    public int hasOrder(int userId, int shopId) {
        return webDAO.hasOrder(userId, shopId);
    }

    @Override
    public String addNewOrder(int userId, int shopId, double shippingFee, int deliverId) {
        return webDAO.addNewOrder(userId, shopId, shippingFee, deliverId);
    }

    @Override
    public OrderVO getOrderInfo(int orderId, String orderNum, String searchKey) {
        return webDAO.getOrderInfo(orderId, orderNum, searchKey);
    }

    @Override
    public String addOrderDetail(int orderId, int goodsId, double price, int qty) {
        return webDAO.addOrderDetail(orderId, goodsId, price, qty);
    }

    @Override
    public void addNewOrderCoupon(int orderId, int userCouponId, double amount, int qty) {
        webDAO.addNewOrderCoupon(orderId, userCouponId, amount, qty);
    }

    @Override
    public void settleOrder(int orderId, String description, Date predictTime) {
        webDAO.settleOrder(orderId, description, predictTime);
    }

    @Override
    public void makeNewOrderDetail(int orderId) {
        webDAO.makeNewOrderDetail(orderId);
    }

    @Override
    public void deleteOrderDetail(int orderId, String searchKey) {
        webDAO.deleteOrderDetail(orderId, searchKey);
    }

    @Override
    public List<CouponVO> getBaseLoginCouponList() {
        return webDAO.getBaseLoginCouponList();
    }
    
    @Override
    public List<CouponVO> getBaseShareCouponList() {
        return webDAO.getBaseShareCouponList();
    }

    @Override
    public CouponVO getCoupon(int id) {
        return webDAO.getCoupon(id);
    }

    @Override
    public List<UserCouponVO> getUserCouponList(int userId) {
        return webDAO.getUserCouponList(userId);
    }

    @Override
    public void addNewUserCoupon(int userId, int couponId, int qty, int noticeId) {
        webDAO.addNewUserCoupon(userId, couponId, qty, noticeId);
    }

    @Override
    public int getConsumeUserCouponIdByUser(int userId, int couponId) {
        return webDAO.getConsumeUserCouponIdByUser(userId, couponId);
    }

    @Override
    public boolean consumeCoupon(int userId, int couponId, int orderId) {
        return webDAO.consumeCoupon(userId, couponId, orderId);
    }

    @Override
    public List<OrderDetailVO> getOrderDetailListByPay(int orderId) {
        return webDAO.getOrderDetailListByPay(orderId);
    }

    @Override
    public void addWelcomeCoupon(ArrayList<UserCouponVO> welcomes) {
        webDAO.addWelcomeCoupon(welcomes);
    }

    @Override
    public UserVO getUser(int userId) {
        return webDAO.getUser(userId);
    }

    @Override
    public UserCouponVO getUserCoupon(int id) {
        return webDAO.getUserCoupon(id);
    }

    @Override
    public UserCouponVO getUnusedUserCoupon(int userId, int couponId) {
        return webDAO.getUnusedUserCoupon(userId, couponId);
    }

    @Override
    public void addNewShareCoupon(ArrayList<ShareCouponVO> shares, int orderId) {
        webDAO.addNewShareCoupon(shares, orderId);
    }

    @Override
    public List<ShareCouponVO> getShareCouponList(String num) {
        return webDAO.getShareCouponList(num);
    }

    @Override
    public ShareCouponVO getShareCoupon(String id) {
        return webDAO.getShareCoupon(id);
    }

    @Override
    public void setShareCoupon(long id, int userId) {
        webDAO.setShareCoupon(id, userId);
    }

    @Override
    public void addNewCoupon(CouponVO coupon) {
        webDAO.addNewCoupon(coupon);
    }

    @Override
    public List<CouponVO> getBaseFullTypeCouponList() {
        return webDAO.getBaseFullTypeCouponList();
    }

    @Override
    public List<CouponVO> getBaseUnlimitedCouponList() {
        return webDAO.getBaseUnlimitedCouponList();
    }

    @Override
    public void deleteShareCoupon(String num) {
        webDAO.deleteShareCoupon(num);
    }

    @Override
    public boolean isLimitedShareCoupon(String num, int size) {
        return webDAO.isLimitedShareCoupon(num, size);
    }

    @Override
    public void addNotification(int userId, int deliverId, int activityNoticeId, int orderId, String title, String description) {
        webDAO.addNotification(userId, deliverId, activityNoticeId, orderId, title, description);
    }

    @Override
    public UserVO getUserById(String userId) {
        return webDAO.getUserById(userId);
    }

    @Override
    public ActivityVO getActivity(int id) {
        return webDAO.getActivity(id);
    }

    @Override
    public void deleteOrderCoupon(int orderId) {
        webDAO.deleteOrderCoupon(orderId);
    }

    @Override
    public void updateDeliverAccessToken(int deliverId, String deliverIp) {
        webDAO.updateDeliverAccessToken(deliverId, deliverIp);
    }

    @Override
    public int getBadge(int userId, int deliverId) {
        return webDAO.getBadge(userId, deliverId);
    }
    
    
}
