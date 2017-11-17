/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.dao;
import com.gaosuchuxing.domain.ActivityDetailVO;
import com.gaosuchuxing.domain.ActivityVO;
import com.gaosuchuxing.domain.ActivityNoticeVO;
import com.gaosuchuxing.domain.CouponVO;
import com.gaosuchuxing.domain.DeliverVO;
import com.gaosuchuxing.domain.DistrictVO;
import com.gaosuchuxing.domain.FeedbackVO;
import com.gaosuchuxing.domain.GoodsKindVO;
import com.gaosuchuxing.domain.GoodsVO;
import com.gaosuchuxing.domain.ManagerVO;
import com.gaosuchuxing.domain.GroupVO;
import com.gaosuchuxing.domain.OrderCouponVO;
import com.gaosuchuxing.domain.OrderDetailVO;
import com.gaosuchuxing.domain.OrderVO;
import com.gaosuchuxing.domain.PermissionVO;
import com.gaosuchuxing.domain.RoleVO;
import com.gaosuchuxing.domain.ShareCouponVO;
import com.gaosuchuxing.domain.ShopKindVO;
import com.gaosuchuxing.domain.ShopVO;
import com.gaosuchuxing.domain.StationVO;
import com.gaosuchuxing.domain.UserCouponVO;
import com.gaosuchuxing.domain.UserVO;
import java.util.ArrayList;
import java.util.List;

public interface WebDAO {
    /*********************************
     * hok start
     *********************************/
    public ManagerVO getMangerLogin(String adminId);    
    public ManagerVO getMangerByName(String name);    
    public List<ManagerVO> getManagerList(String keyword, int groupId, int offset, int size, String sortColumn, String sort);
    public int countAllManager(String keyword, int groupId);
    public ManagerVO getManager(int id);
    public void addNewManager(ManagerVO newAdmin);
    public void updateManager(ManagerVO admin);    
    public void deleteManager(int id);
    public void setPassword(int id, String password);
    
    public List<GroupVO> getGroupList(String keyword, String sortColumn, String sort);
    public boolean addNewGroup(GroupVO newGroup);
    public boolean updateGroup(GroupVO group);
    public boolean deleteGroup(int groupId);
    public void setRole(int groupId, List<RoleVO> roles);
    public List<RoleVO> getRoleList(int groupId);
            
    public boolean checkPermission(int adminId, String permissionName);
    
    public List<PermissionVO> getPermissionList();
    
    public List<StationVO> getStationList(String keyword, int districtId, String adcode, String status, int offset, int size, String sortColumn, String sort);
    public int countAllStation(String keyword, int districtId, String adcode, String status);
    public StationVO getStation(int id);
    public StationVO getStationByName(String stationName);
    public void addNewStation(StationVO newStation);
    public void updateStation(StationVO station);    
    public boolean deleteStation(int id);
    
    public List<DistrictVO> getDistrictList(int parentId, boolean isParent);
    
    public List<GoodsKindVO> getGoodsKindList(int shopKindId, String name, int offset, int size, String sortColumn, String sort);
    public GoodsKindVO getGoodsKind(int id);
    public boolean addNewGoodsKind(String name, int shopKindId);
    public boolean updateGoodsKind(String name, int shopKindId, int id);
    public boolean deleteGoodsKind(int id);
    
    public List<ShopKindVO> getShopKindList(String keyword, int offset, int size, String sortColumn, String sort);
    public ShopKindVO getShopKind(int shopKindId);
    public boolean addNewShopKind(ShopKindVO newShopKind);
    public boolean updateShopKind(ShopKindVO shopKind);
    public boolean deleteShopKind(int shopKindId);
    
    public List<ShopVO> getShopList(String type, String keyword, int stationId, int shopKindId, int districtId, int offset, int size, String sortColumn, String sort);
    public int countAllShop(String type, String keyword, int stationId, int shopKindId, int districtId);
    public ShopVO getShop(int shopId);
    public ShopVO getShopByName(String shopName);
    public void addNewShop(ShopVO newShop);
    public void updateShop(ShopVO shop);
    public boolean deleteShop(int shopId);
    
    public int getGoodsIdForCheckGoodsNameOfShop(int shopId, String goodsName);
    
    public List<OrderVO> getOrderList(int state, String orderNum, String userName, String deliverName, String orderStatus, String from, String to, int offset, int size, String sortColumn, String sort);
    public int countAllOrder(int state, String orderNum, String userName, String deliverName, String orderStatus, String from, String to);
    public OrderVO getOrder(int orderId);
    public double getOrderAmount(int state, String orderNum, String userName, String deliverName, String orderStatus, String from, String to);
    
    public List<OrderDetailVO> getOrderDetailList(int orderId);
    public List<OrderCouponVO> getOrderCouponList(int orderId);
    
    public List<OrderVO> getDeliverSum(String deliverName, String from, String to, int offset, int size, String sortColumn, String sort);
    public int countAllDeliverSum(String deliverName, String from, String to);
    
    public List<ActivityVO> getActivityList(String acitivityName, String status, int offset, int size, String sortColumn, String sort);
    public int countAllActivity(String acitivityName, String status);
    public ActivityVO getActivity(int id);
    public ActivityVO getActivityByName(String name);
    public int addNewActivity(ActivityVO activity);
    public void updateActivity(ActivityVO activity);
    public void updateActivityAmountAndQty(int activityId, double amount, int qty);
    public boolean deleteActivity(int activityId);
    public int getMaxActivityId();
        
    public void addTmpActivityDetail(long tmpUId, ActivityDetailVO tmp);
    public void updateTmpActivityDetail(long id, double rate);
    public void updateTmpActivityDetailRate(List<ActivityDetailVO> details);
    public void deleteTmpActivityDetail(long id, long tmpUId);
    public void deleteAllTmpActivityDetail(long tmpUId);
    public void makeTmpActivityDetail(long tmpUId, int activityId);
    public List<ActivityDetailVO> getTmpActivityDetailList(long tmpUId, String type);
            
    public List<ActivityDetailVO> getActivityDetail(int activityId, String type);
    public void addNewActivityDetail(int activityId, List<ActivityDetailVO> activityDetails);
    public void updateActivityDetail(int activityId, String type, List<ActivityDetailVO> activityDetails);
    public void deleteActivityDetail(int activityId, String type);
    
    public void addNotification(int userId, int deliverId, int activityNoticeId, int orderId, String title, String description, String num);
    
    public List<CouponVO> getActivityCouponList(int activityNoticeId);
    
    public void addNewShareCoupon(ArrayList<ShareCouponVO> shares);
    public List<ShareCouponVO> getShareCouponList(String num);
    public ShareCouponVO getShareCoupon(String id);
    public void setShareCoupon(long id);    
    public void deleteShareCoupon(String num);
    public boolean isLimitedShareCoupon(String num, int size);
    
    public DeliverVO getDeliverByName(String name);
    public boolean isDeliverOfStation(int deliverId);    
    
    /*********************************
     * jiw start
     *********************************/
    public List<FeedbackVO> getFeedbackList(String keyword, String status, int offset, int size, String sortColumn, String sort);
    public int countAllFeedback(String keyword, String status);
    public void setFeedbackStatus(int feedbackId);
    
    public DeliverVO getDeliver(String deliverId);
    public DeliverVO getDeliverById(int id);
    public List<DeliverVO> getDeliverList(String keyword, int offset, int size, String sortColumn, String sort);
    public int countAllDeliver(String keyword);
    public void addNewDeliver(DeliverVO newDeliver);
    public void updateDeliver(DeliverVO deliver);
    public boolean deleteDeliver(int deliverId);
    public void setDeliverPassword(int deliverId, String password);
    
    public List<UserVO> getUserList(String keyword, String from, String to, int offset, int size, String sortColumn, String sort);
    public int countAllUser(String keyword, String from, String to);
    
    public List<GoodsVO> getGoodsList(String keyword, int shopKindId, int shopId, int offset, int size, String sortColumn, String sort);
    public int countAllGoods(String keyword, int shopKindId, int shopId);
    public void addNewGoods(GoodsVO newGoods);
    public void updateGoods(GoodsVO goods);
    public boolean deleteGoods(int id);
    public GoodsVO getGoods(int id);
    public boolean checkGoodsByName(String name);
    public void changeGoodsStatus(int id, String status);
    
    public List<ActivityNoticeVO> getActivityNoticeList(int offset, int size, String sortColumn, String sort);
    public ActivityNoticeVO getActivityNotice(int id);
    public int countAllActivityNotice();
    public void addNewActivityNotice(ActivityNoticeVO newNotice);
    public void updateActivityNotice(ActivityNoticeVO notice);
    public boolean deleteActivityNotice(int id);
    public void setActivityNoticeStatus(int id);
    
    public List<ActivityVO> getActivityListByStatus();
    
    public List<UserCouponVO> getUserCouponList(String couponName, String userTelNo, String couponStatus, int offset, int size, String sortColumn, String sort);
    public int countAllUserCoupon(String couponName, String userTelNo, String couponStatus);
    
    public boolean checkFeedback();
    
    public List<PermissionVO> getManagerPermission(int managerId, int parentId);
}
