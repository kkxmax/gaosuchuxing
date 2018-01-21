/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.service;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface WebService {
    public List<StationVO> getStationListByNear(ArrayList<String> adcodes);
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
    public List<OrderVO> getOrderListByUser(boolean state, int userId, String orderStatus, int stationId);
    
    public List<OrderDetailVO> getOrderDetailList(int orderId);
    public List<OrderDetailVO> getOrderDetailListByGoods(int orderId, boolean isPay);
    public List<OrderCouponVO> getOrderCouponList(int orderId, int status);
    
    public List<GoodsVO> getGoodsListByShop(String keyword, int shopKindId, int shopId, int goodsKindId, int userId, int orderId, int offset, int size, String sortColumn, String sort);
    public List<GoodsVO> getGoodsListByOrder(String keyword, int goodsKindId, int userId, int orderId, int offset, int size, String sortColumn, String sort);
    public int countAllGoodsByOrder(String keyword, int shopKindId, int shopId, int goodsKindId);
    public GoodsVO getGoods(int id);
    public GoodsVO getGoodsByName(String name);
    public List<GoodsKindVO> getGoodsKindListByShopId(int shopId, int userId);
    
    public DeliverVO getDeliver(String deliverId);
    public DeliverVO getDeliverById(int id);
    public List<DeliverVO> getDeliverList(String keyword, int offset, int size, String sortColumn, String sort);
    public int countAllDeliver(String keyword);
    public void setDeliverPassword(int deliverId, String password);
    public void updateDeliverAccessToken(int deliverId, String accessToken);
    
    public List<NotificationVO> getNotificationList(int userId, int deliverId, int offset, int size, String sortColumn, String sort);
    public void addNewNotification(NotificationVO notification);
    public void setNotificationStatus(int notificationId, int userId, int deliverId);
    
    public ActivityNoticeVO getActivityNotice(int id);
    
    public List<StationVO> getStationByDeliver(int deliverId);
    public void addFeedback(int userId, int deliverId, String feedback);
    public int getOrderCountByDeliver(int deliverId);
    
    public GoodsVO getGoodsByOrder(int goodsId, int shopId, int userId);
    
    public UserVO getUserByWxOpenId(String wxOpenId);
    public void addUser(String wxOpenId, String name, String imgUrl);
    public void activeUser(String wxOpenId, String telNo);
    public void updateUserImgUrl(String wxOpenId, String nick, String imgUrl);
    public void disableUserIsNew(String wxOpenId);
    public UserVO getUser(int userId);
    public UserVO getUserById(String userId);
    
    public int hasOrder(int userId, int shopId);
    public String addNewOrder(int userId, int shopId, double shippingFee, int deliverId);
    public OrderVO getOrderInfo(int orderId, String orderNum, String searchKey);
    public String addOrderDetail(int orderId, int goodsId, double price, int qty);
    public void addNewOrderCoupon(int orderId, int userCouponId, double amount, int qty);
    public void settleOrder(int orderId, String description, Date predictTime);
    
    public void makeNewOrderDetail(int orderId);
    public void deleteOrderDetail(int orderId, String searchKey);
    
    public List<CouponVO> getBaseLoginCouponList();
    public List<CouponVO> getBaseShareCouponList();
    public CouponVO getCoupon(int id);
    
    public List<UserCouponVO> getUserCouponList(int userId);
    public void addWelcomeCoupon(ArrayList<UserCouponVO> welcomes);
    public void addNewUserCoupon(int userId, int couponId, int qty, int noticeId);
    public int getConsumeUserCouponIdByUser(int userId, int couponId);
    public boolean consumeCoupon(int userId, int couponId, int orderId);
    public UserCouponVO getUserCoupon(int id);
    public UserCouponVO getUnusedUserCoupon(int userId, int couponId);
    
    public List<OrderDetailVO> getOrderDetailListByPay(int orderId);
    
    public void addNewShareCoupon(ArrayList<ShareCouponVO> shares, int orderId);
    public List<ShareCouponVO> getShareCouponList(String num);
    public ShareCouponVO getShareCoupon(String id);
    public void setShareCoupon(long id, int userId);
    
    public void addNewCoupon(CouponVO coupon);
    public List<CouponVO> getBaseFullTypeCouponList();
    public List<CouponVO> getBaseUnlimitedCouponList();
    
    public void deleteShareCoupon(String num);
    public boolean isLimitedShareCoupon(String num, int size);
    
    public void addNotification(int userId, int deliverId, int activityNoticeId, int orderId, String title, String description);
    public ActivityVO getActivity(int id);
    
    public void deleteOrderCoupon(int orderId);
    
    public int getBadge(int userId, int deliverId);
}
