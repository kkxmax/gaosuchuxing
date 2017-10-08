/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.delegate;

import com.gaosuchuxing.common.Constant;
import com.gaosuchuxing.domain.ActivityDetailVO;
import com.gaosuchuxing.domain.ActivityNoticeVO;
import com.gaosuchuxing.domain.ActivityVO;
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
import com.gaosuchuxing.domain.ShopKindVO;
import com.gaosuchuxing.domain.ShopVO;
import com.gaosuchuxing.domain.StationVO;
import com.gaosuchuxing.domain.UserCouponVO;
import com.gaosuchuxing.domain.UserVO;
import com.gaosuchuxing.service.WebService;
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
    
    /*********************************
     * hok start
     *********************************/
    
    public ManagerVO getManagerLogin(String adminId) {
        return webService.getAdminLogin(adminId);
    }
    
    public List<ManagerVO> getManagerList(String keyword, int groupId, int offset, int size, String sortColumn, String sort) {
        return webService.getManagerList(keyword, groupId, offset, size, sortColumn, sort);
    }
    
    public int countAllManager(String keyword, int groupId) {
        return webService.countAllManager(keyword, groupId);
    }
    
    public ManagerVO getManager(int id) {
        return webService.getManager(id);
    }
    
    public void addNewManager(ManagerVO newAdmin) {
        webService.addNewManager(newAdmin);
    }
    
    public void updateManager(ManagerVO admin) {
        webService.updateManager(admin);
    }
    
    public void deleteManager(int id) {
        webService.deleteManager(id);
    }
    
    public void setPassword(int id, String password) {
        webService.setPassword(id, password);
    }
    
    public List<GroupVO> getGroupList(String keyword, String sortColumn, String sort) {
        return webService.getGroupList(keyword, sortColumn, sort);
    }
    
    public boolean addNewGroup(GroupVO newGroup) {
        return webService.addNewGroup(newGroup);
    }
    
    public boolean updateGroup(GroupVO group) {
        return webService.updateGroup(group);
    }
    
    public boolean deleteGroup(int groupId) {
        return webService.deleteGroup(groupId);
    }
    
    public void setRole(int groupId, List<RoleVO> roles) {
        webService.setRole(groupId, roles);
    }
    
    public List<RoleVO> getRoleList(int groupId) {
        return webService.getRoleList(groupId);
    }
    
    public boolean checkPermission(int adminId, String permissionName) {
        return webService.checkPermission(adminId, permissionName);
    }
    
    public List<PermissionVO> getPermissionList() {
        return webService.getPermissionList();
    }
  
    public List<StationVO> getStationList(String keyword, int districtId, String adcode, String status, int offset, int size, String sortColumn, String sort) {
        return webService.getStationList(keyword, districtId, adcode, status, offset, size, sortColumn, sort);
    }

    public int countAllStation(String keyword, int districtId, String adcode, String status) {
        return webService.countAllStation(keyword, districtId, adcode, status);
    }
    
    public List<StationVO> getStationList(String keyword, int districtId, String status, int offset, int size, String sortColumn, String sort) {
        return webService.getStationList(keyword, districtId, null, status, offset, size, sortColumn, sort);
    }

    public int countAllStation(String keyword, int districtId, String status) {
        return webService.countAllStation(keyword, districtId, null, status);
    }

    public StationVO getStation(int id) {
        return webService.getStation(id);
    }
    
    public StationVO getStationByName(String stationName) {
        return webService.getStationByName(stationName);
    }

    public void addNewStation(StationVO newStation) {
        webService.addNewStation(newStation);
    }

    public void updateStation(StationVO station) {
        webService.updateStation(station);
    }

    public boolean deleteStation(int id) {
        return webService.deleteStation(id);
    }

    public List<DistrictVO> getDistrictList(int parentId, boolean isParent) {
        return webService.getDistrictList(parentId, isParent);
    }
    
    public List<GoodsKindVO> getGoodsKindList(int shopKindId, String name, int offset, int size, String sortColumn, String sort) {
        return webService.getGoodsKindList(shopKindId, name, offset, size, sortColumn, sort);
    }
    
    public List<GoodsKindVO> getGoodsKindList(int shopKindId) {
        return webService.getGoodsKindList(shopKindId, null, -1, -1, null, null);
    }
    
    public List<GoodsKindVO> getGoodsKindList() {
        return webService.getGoodsKindList(-1, null, -1, -1, null, null);
    }
    
    public GoodsKindVO getGoodsKind(int id) {
        return webService.getGoodsKind(id);
    }

    public boolean addNewGoodsKind(String name, int shopKindId) {
        return webService.addNewGoodsKind(name, shopKindId);
    }

    public boolean updateGoodsKind(String name, int shopKindId, int id) {
        return webService.updateGoodsKind(name, shopKindId, id);
    }
    
    public boolean deleteGoodsKind(int id) {
        return webService.deleteGoodsKind(id);
    }

    public List<ShopKindVO> getShopKindList(String keyword, int offset, int size, String sortColumn, String sort) {
        return webService.getShopKindList(keyword, offset, size, sortColumn, sort);
    }    
    
    public List<ShopKindVO> getShopKindList() {
        return webService.getShopKindList(null, -1, -1, null, null);
    }
    
    public ShopKindVO getShopKind(int shopKindId) {
        return webService.getShopKind(shopKindId);
    }
    
    public boolean addNewShopKind(ShopKindVO newShopKind) {
        return webService.addNewShopKind(newShopKind);
    }

    public boolean updateShopKind(ShopKindVO shopKind) {
        return webService.updateShopKind(shopKind);
    }

    public boolean deleteShopKind(int shopKindId) {
        return webService.deleteShopKind(shopKindId);
    }

    public List<ShopVO> getShopList(String type, String keyword, int stationId, int shopKindId, int districtId, int offset, int size, String sortColumn, String sort) {
        return webService.getShopList(type, keyword, stationId, shopKindId, districtId, offset, size, sortColumn, sort);
    }
    
    public List<ShopVO> getShopList(int shopKindId) {
        return webService.getShopList(null, null, -1, shopKindId, -1, -1, -1, null, null);
    }

    public int countAllShop(String type, String keyword, int stationId, int shopKindId, int districtId) {
        return webService.countAllShop(type, keyword, stationId, shopKindId, districtId);
    }

    public void addNewShop(ShopVO newShop) {
        webService.addNewShop(newShop);
    }

    public void updateShop(ShopVO shop) {
        webService.updateShop(shop);
    }

    public boolean deleteShop(int shopId) {
        return webService.deleteShop(shopId);
    }
    
    public ShopVO getShop(int shopId) {
        return webService.getShop(shopId);
    }
    
    public ShopVO getShopByName(String shopName) {
        return webService.getShopByName(shopName);
    }   
    
    public List<OrderVO> getOrderList(int state, String orderNum, String userName, String deliverName, String orderStatus, String from, String to, int offset, int size, String sortColumn, String sort) {
        return webService.getOrderList(state, orderNum, userName, deliverName, orderStatus, from, to, offset, size, sortColumn, sort);
    }

    public int countAllOrder(int state, String orderNum, String userName, String deliverName, String orderStatus, String from, String to) {
        return webService.countAllOrder(state, orderNum, userName, deliverName, orderStatus, from, to);
    }

    public OrderVO getOrder(int orderId) {
        return webService.getOrder(orderId);
    }

    public List<OrderDetailVO> getOrderDetailList(int orderId) {
        return webService.getOrderDetailList(orderId);
    }

    public List<OrderCouponVO> getOrderCouponList(int orderId) {
        return webService.getOrderCouponList(orderId);
    }
    
    public List<OrderVO> getDeliverSum(String deliverName, String from, String to, int offset, int size, String sortColumn, String sort) {
        return webService.getDeliverSum(deliverName, from, to, offset, size, sortColumn, sort);
    }

    public int countAllDeliverSum(String deliverName, String from, String to) {
        return webService.countAllDeliverSum(deliverName, from, to);
    }
    
    public List<ActivityVO> getActivityList(String acitivityName, String status, int offset, int size, String sortColumn, String sort) {
        return webService.getActivityList(acitivityName, status, offset, size, sortColumn, sort);
    }

    public int countAllActivity(String acitivityName, String status) {
        return webService.countAllActivity(acitivityName, status);
    }
    
    public ActivityVO getActivity(int id) {
        return webService.getActivity(id);
    }
    
    public ActivityVO getActivityByName(String name) {
        return webService.getActivityByName(name);
    }

    public int addNewActivity(ActivityVO activity) {
        return webService.addNewActivity(activity);
    }

    public void updateActivity(ActivityVO activity) {
       webService.updateActivity(activity);
    }
    
    public void updateActivityAmountAndQty(int activityId, double amount, int qty) {
        webService.updateActivityAmountAndQty(activityId, amount, qty);
    }

    public boolean deleteActivity(int activityId) {
        return webService.deleteActivity(activityId);
    }
    
    public int getMaxActivityId() {
        return webService.getMaxActivityId();
    }

    public void addTmpActivityDetail(long tmpUId, ActivityDetailVO tmp) {
        webService.addTmpActivityDetail(tmpUId, tmp);
    }
    
    public void updateTmpActivityDetail(long id, double rate) {
        webService.updateTmpActivityDetail(id, rate);
    }
    
    public void updateTmpActivityDetailRate(List<ActivityDetailVO> details) {
        webService.updateTmpActivityDetailRate(details);
    }

    public void deleteTmpActivityDetail(long id, long tmpUId) {
        webService.deleteTmpActivityDetail(id, tmpUId);
    }

    public void deleteAllTmpActivityDetail(long tmpUId) {
        webService.deleteAllTmpActivityDetail(tmpUId);
    }

    public void makeTmpActivityDetail(long tmpUId, int activityId) {
        webService.makeTmpActivityDetail(tmpUId, activityId);
    }

    public List<ActivityDetailVO> getTmpActivityDetailList(long tmpUId, String type) {
        return webService.getTmpActivityDetailList(tmpUId, type);
    }

    public List<ActivityDetailVO> getActivityDetail(int activityId, String type) {
        return webService.getActivityDetail(activityId, type);
    }

    public void addNewActivityDetail(int activityId, List<ActivityDetailVO> activityDetails) {
        webService.addNewActivityDetail(activityId, activityDetails);
    }

    public void updateActivityDetail(int activityId, String type, List<ActivityDetailVO> activityDetails) {
        webService.updateActivityDetail(activityId, type, activityDetails);
    }

    public void deleteActivityDetail(int activityId, String type) {
        webService.deleteActivityDetail(activityId, type);
    }
    
    /*********************************
     * jiw start
     *********************************/
    public List<FeedbackVO> getFeedbackList(String keyword, String status, int offset, int size, String sortColumn, String sort) {
        return webService.getFeedbackList(keyword, status, offset, size, sortColumn, sort);
    }

    public int countAllFeedback(String keyword, String status) {
        return webService.countAllFeedback(keyword, status);
    }
    
    public void setFeedbackStatus(int feedbackId) {
        webService.setFeedbackStatus(feedbackId);
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
    
    public void addNewDeliver(DeliverVO newDeliver) {
        webService.addNewDeliver(newDeliver);
    }
    
    public void updateDeliver(DeliverVO deliver) {
        webService.updateDeliver(deliver);
    }
    
    public boolean deleteDeliver(int deliverId) {
        return webService.deleteDeliver(deliverId);
    }
    
    public void setDeliverPassword(int deliverId, String password) {
        webService.setDeliverPassword(deliverId, password);
    }
    
    public List<UserVO> getUserList(String keyword, String from, String to, int offset, int size, String sortColumn, String sort) {
        return webService.getUserList(keyword, from, to, offset, size, sortColumn, sort);
    }
    
    public int countAllUser(String keyword, String from, String to) {
        return webService.countAllUser(keyword, from, to);
    }
    
    public List<GoodsVO> getGoodsList(String keyword, int shopKindId, int shopId, int offset, int size, String sortColumn, String sort) {
        return webService.getGoodsList(keyword, shopKindId, shopId, offset, size, sortColumn, sort);
    }
    
    public int countAllGoods(String keyword, int shopKindId, int shopId) {
        return webService.countAllGoods(keyword, shopKindId, shopId);
    }
    
    public void addNewGoods(GoodsVO newGood) {
        webService.addNewGoods(newGood);
    }
    
    public void updateGoods(GoodsVO good) {
        webService.updateGoods(good);
    }
    
    public boolean deleteGoods(int id) {
        return webService.deleteGoods(id);
    }
    
    public GoodsVO getGoods(int id) {
        return webService.getGoods(id);
    }
    
    public GoodsVO getGoodsByName(String name) {
        return webService.getGoodsByName(name);
    }
    
    public void changeGoodsStatus(int id, String status) {
        webService.changeGoodsStatus(id, status);
    }
    
    public List<ActivityNoticeVO> getActivityNoticeList(int offset, int size, String sortColumn, String sort) {
        return webService.getActivityNoticeList(offset, size, sortColumn, sort);
    }

    public ActivityNoticeVO getActivityNotice(int id) {
        return webService.getActivityNotice(id);
    }
    
    public int countAllActivityNotice() {
        return webService.countAllActivityNotice();
    }

    public void addNewActivityNotice(ActivityNoticeVO newNotice) {
        webService.addNewActivityNotice(newNotice);
    }

    public void updateActivityNotice(ActivityNoticeVO notice) {
        webService.updateActivityNotice(notice);
    }

    public boolean deleteActivityNotice(int id) {
        return webService.deleteActivityNotice(id);
    }

    public void setActivityNoticeStatus(int id) {
        webService.setActivityNoticeStatus(id);
    }
    
    public List<ActivityVO> getActivityListByStatus() {
        return webService.getActivityListByStatus();
    }
    
    public List<UserCouponVO> getUserCouponList(String couponName, String userTelNo, String couponStatus, int offset, int size, String sortColumn, String sort) {
        return webService.getUserCouponList(couponName, userTelNo, couponStatus, offset, size, sortColumn, sort);
    }

    public int countAllUserCoupon(String couponName, String userTelNo, String couponStatus) {
        return webService.countAllUserCoupon(couponName, userTelNo, couponStatus);
    }
}
