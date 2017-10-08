/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.service.impl;

import com.gaosuchuxing.mobile.dao.WebDAO;
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
    public List<OrderDetailVO> getOrderDetailListByGoods(int orderId) {
        return webDAO.getOrderDetailListByGoods(orderId);
    }

    @Override
    public List<OrderCouponVO> getOrderCouponList(int orderId) {
        return webDAO.getOrderCouponList(orderId);
    }
    
    
    @Override
    public List<GoodsVO> getGoodsList(String keyword, int shopKindId, int shopId, int goodsKindId, int offset, int size, String sortColumn, String sort) {
        return webDAO.getGoodsList(keyword, shopKindId, shopId, goodsKindId, offset, size, sortColumn, sort);
    }

    @Override
    public int countAllGoods(String keyword, int shopKindId, int shopId, int goodsKindId) {
        return webDAO.countAllGoods(keyword, shopKindId, shopId, goodsKindId);
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
    public void setNotificationStatus(int notificationId) {
        webDAO.setNotificationStatus(notificationId);
    }

    @Override
    public ActivityNoticeVO getActivityNotice(int id) {
        return webDAO.getActivityNotice(id);
    }

    @Override
    public StationVO getStationByDeliver(int deliverId) {
        return webDAO.getStationByDeliver(deliverId);
    }

    @Override
    public void addFeedback(int deliverId, String feedback) {
        webDAO.addFeedback(deliverId, feedback);
    }

    @Override
    public int getOrderCountByDeliver(int deliverId) {
        return webDAO.getOrderCountByDeliver(deliverId);
    }

    @Override
    public GoodsVO getGoodsByOrder(int goodsId, int userId) {
        return webDAO.getGoodsByOrder(goodsId, userId);
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
    public void updateUserImgUrl(String wxOpenId, String imgUrl) {
        webDAO.updateUserImgUrl(wxOpenId, imgUrl);
    }

    @Override
    public void disableUserIsNew(String wxOpenId) {
        webDAO.disableUserIsNew(wxOpenId);
    }
    
    
}
