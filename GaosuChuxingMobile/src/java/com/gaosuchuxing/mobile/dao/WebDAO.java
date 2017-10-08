/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.dao;

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
import java.util.List;

public interface WebDAO {
    public List<StationVO> getStationList(String keyword, int districtId, String adcode, String status, int offset, int size, String sortColumn, String sort);
    public int countAllStation(String keyword, int districtId, String adcode, String status);
    public StationVO getStation(int id);
    public StationVO getStationByName(String stationName);
    
    public List<GoodsKindVO> getGoodsKindList(int shopKindId, String name, int offset, int size, String sortColumn, String sort);
    public GoodsKindVO getGoodsKind(int id);
    
    public List<ShopKindVO> getShopKindList(String keyword, int offset, int size, String sortColumn, String sort);
    public ShopKindVO getShopKind(int shopKindId);
    public ShopKindVO getShopKindByName(String shopKindName);
    
    public List<ShopVO> getShopList(String type, String keyword, int stationId, int shopKindId, int districtId, int offset, int size, String sortColumn, String sort);
    public int countAllShop(String type, String keyword, int stationId, int shopKindId, int districtId);
    public ShopVO getShop(int shopId);
    public ShopVO getShopByName(String shopName);
    
    public List<OrderVO> getOrderList(int state, int deliverId, String orderNum, String userName, String deliverName, String orderStatus, String from, String to, int offset, int size, String sortColumn, String sort);
    public int countAllOrder(int state, int deliverId, String orderNum, String userName, String deliverName, String orderStatus, String from, String to);
    public OrderVO getOrder(int orderId);
    public List<OrderVO> getOrderListByDeliver(int deliverId, String orderStatus);    
    public void setOrderStatus(int orderId, String status);
    
    public List<OrderDetailVO> getOrderDetailList(int orderId);
    public List<OrderDetailVO> getOrderDetailListByGoods(int orderId);
    public List<OrderCouponVO> getOrderCouponList(int orderId);
    
    public List<GoodsVO> getGoodsList(String keyword, int shopKindId, int shopId, int goodsKindId, int offset, int size, String sortColumn, String sort);
    public int countAllGoods(String keyword, int shopKindId, int shopId, int goodsKindId);
    public GoodsVO getGoods(int id);
    public GoodsVO getGoodsByName(String name);
    public List<GoodsKindVO> getGoodsKindListByShopId(int shopId, int userId);
    
    public DeliverVO getDeliver(String deliverId);
    public DeliverVO getDeliverById(int id);
    public List<DeliverVO> getDeliverList(String keyword, int offset, int size, String sortColumn, String sort);
    public int countAllDeliver(String keyword);
    public void setDeliverPassword(int deliverId, String password);
    
    public List<NotificationVO> getNotificationList(int userId, int deliverId, int offset, int size, String sortColumn, String sort);
    public void addNewNotification(NotificationVO notification);
    public void setNotificationStatus(int notificationId);
    
    public ActivityNoticeVO getActivityNotice(int id);
    
    public StationVO getStationByDeliver(int deliverId);
    public void addFeedback(int deliverId, String feedback);
    public int getOrderCountByDeliver(int deliverId);
    
    public GoodsVO getGoodsByOrder(int goodsId, int userId);
    
    public UserVO getUserByWxOpenId(String wxOpenId);
    public void addUser(String wxOpenId, String name, String imgUrl);
    public void activeUser(String wxOpenId, String telNo);
    public void updateUserImgUrl(String wxOpenId, String imgUrl);
    public void disableUserIsNew(String wxOpenId);
}
