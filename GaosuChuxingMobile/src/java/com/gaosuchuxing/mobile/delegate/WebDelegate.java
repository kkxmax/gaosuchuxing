/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.delegate;

import com.gaosuchuxing.mobile.domain.ActivityNoticeVO;
import com.gaosuchuxing.mobile.domain.DeliverVO;
import com.gaosuchuxing.mobile.domain.GoodsKindVO;
import com.gaosuchuxing.mobile.domain.GoodsVO;
import com.gaosuchuxing.mobile.domain.NotificationVO;
import com.gaosuchuxing.mobile.domain.OrderCouponVO;
import com.gaosuchuxing.mobile.domain.OrderDetailVO;
import com.gaosuchuxing.mobile.domain.OrderVO;
import com.gaosuchuxing.mobile.domain.ShopKindVO;
import com.gaosuchuxing.mobile.domain.ShopVO;
import com.gaosuchuxing.mobile.domain.StationVO;
import com.gaosuchuxing.mobile.domain.UserVO;
import com.gaosuchuxing.mobile.service.WebService;
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

    public List<OrderDetailVO> getOrderDetailListByGoods(int orderId) {
        return webService.getOrderDetailListByGoods(orderId);
    }

    public List<OrderCouponVO> getOrderCouponList(int orderId) {
        return webService.getOrderCouponList(orderId);
    }
    
    public List<GoodsVO> getGoodsList(String keyword, int shopKindId, int shopId, int goodsKindId, int offset, int size, String sortColumn, String sort) {
        return webService.getGoodsList(keyword, shopKindId, shopId, goodsKindId, offset, size, sortColumn, sort);
    }
    
    public int countAllGoods(String keyword, int shopKindId, int shopId, int goodsKindId) {
        return webService.countAllGoods(keyword, shopKindId, shopId, goodsKindId);
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

    public void setNotificationStatus(int notificationId) {
        webService.setNotificationStatus(notificationId);
    }
    
    public ActivityNoticeVO getActivityNotice(int id) {
        return webService.getActivityNotice(id);
    }
    
    public StationVO getStationByDeliver(int deliverId) {
        return webService.getStationByDeliver(deliverId);
    }

    public void addFeedback(int deliverId, String feedback) {
        webService.addFeedback(deliverId, feedback);
    }
    
    public int getOrderCountByDeliver(int deliverId) {
        return webService.getOrderCountByDeliver(deliverId);
    }
     
    public GoodsVO getGoodsByOrder(int goodsId, int userId) {
        return webService.getGoodsByOrder(goodsId, userId);
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
    
    public void updateUserImgUrl(String wxOpenId, String imgUrl) {
        webService.updateUserImgUrl(wxOpenId, imgUrl);
    }
    
    public void disableUserIsNew(String wxOpenId) {
        webService.disableUserIsNew(wxOpenId);
    }
}
