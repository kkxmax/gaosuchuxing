/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.service.impl;

import com.gaosuchuxing.dao.WebDAO;
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

    /*********************************
     * hok start
     *********************************/
    @Override
    public ManagerVO getAdminLogin(String adminId) {
        return webDAO.getMangerLogin(adminId);
    }

    @Override
    public List<ManagerVO> getManagerList(String keyword, int groupId, int offset, int size, String sortColumn, String sort) {
        return webDAO.getManagerList(keyword, groupId, offset, size, sortColumn, sort);
    }

    @Override
    public int countAllManager(String keyword, int groupId) {
        return webDAO.countAllManager(keyword, groupId);
    }

    @Override
    public ManagerVO getManager(int id) {
        return webDAO.getManager(id);
    }

    @Override
    public void addNewManager(ManagerVO newManager) {
        webDAO.addNewManager(newManager);
    }

    @Override
    public void updateManager(ManagerVO admin) {
        webDAO.updateManager(admin);
    }

    @Override
    public void deleteManager(int id) {
        webDAO.deleteManager(id);
    }

    @Override
    public void setPassword(int id, String password) {
        webDAO.setPassword(id, password);
    }

    @Override
    public List<GroupVO> getGroupList(String keyword, String sortColumn, String sort) {
        return webDAO.getGroupList(keyword, sortColumn, sort);
    }

    @Override
    public boolean addNewGroup(GroupVO newGroup) {
        return webDAO.addNewGroup(newGroup);
    }

    @Override
    public boolean updateGroup(GroupVO group) {
        return webDAO.updateGroup(group);
    }

    @Override
    public boolean deleteGroup(int groupId) {
        return webDAO.deleteGroup(groupId);
    }

    @Override
    public void setRole(int groupId, List<RoleVO> roles) {
        webDAO.setRole(groupId, roles);
    }

    @Override
    public List<RoleVO> getRoleList(int groupId) {
        return webDAO.getRoleList(groupId);
    }

    @Override
    public boolean checkPermission(int adminId, String permissionName) {
        return webDAO.checkPermission(adminId, permissionName);
    }

    @Override
    public List<PermissionVO> getPermissionList() {
        return webDAO.getPermissionList();
    }   

    @Override
    public List<StationVO> getStationList(String keyword, int districtId, String status, int offset, int size, String sortColumn, String sort) {
        return webDAO.getStationList(keyword, districtId, status, offset, size, sortColumn, sort);
    }

    @Override
    public int countAllStation(String keyword, int districtId, String status) {
        return webDAO.countAllStation(keyword, districtId, status);
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
    public void addNewStation(StationVO newStation) {
        webDAO.addNewStation(newStation);
    }

    @Override
    public void updateStation(StationVO station) {
        webDAO.updateStation(station);
    }

    @Override
    public boolean deleteStation(int id) {
        return webDAO.deleteStation(id);
    }

    @Override
    public List<DistrictVO> getDistrictList(int parentId, boolean isParent) {
        return webDAO.getDistrictList(parentId, isParent);
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
    public boolean addNewGoodsKind(String name, int shopKindId) {
        return webDAO.addNewGoodsKind(name, shopKindId);
    }

    @Override
    public boolean updateGoodsKind(String name, int shopKindId, int id) {
        return webDAO.updateGoodsKind(name, shopKindId, id);
    }

    @Override
    public boolean deleteGoodsKind(int id) {
        return webDAO.deleteGoodsKind(id);
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
    public boolean addNewShopKind(ShopKindVO newShopKind) {
        return webDAO.addNewShopKind(newShopKind);
    }

    @Override
    public boolean updateShopKind(ShopKindVO shopKind) {
        return webDAO.updateShopKind(shopKind);
    }

    @Override
    public boolean deleteShopKind(int shopKindId) {
        return webDAO.deleteShopKind(shopKindId);
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
    public void addNewShop(ShopVO newShop) {
        webDAO.addNewShop(newShop);
    }

    @Override
    public void updateShop(ShopVO shop) {
        webDAO.updateShop(shop);
    }

    @Override
    public boolean deleteShop(int shopId) {
        return webDAO.deleteShop(shopId);
    }

    @Override
    public List<OrderVO> getOrderList(int state, String orderNum, String userName, String deliverName, String orderStatus, String from, String to, int offset, int size, String sortColumn, String sort) {
        return webDAO.getOrderList(state, orderNum, userName, deliverName, orderStatus, from, to, offset, size, sortColumn, sort);
    }

    @Override
    public int countAllOrder(int state, String orderNum, String userName, String deliverName, String orderStatus, String from, String to) {
        return webDAO.countAllOrder(state, orderNum, userName, deliverName, orderStatus, from, to);
    }

    @Override
    public OrderVO getOrder(int orderId) {
        return webDAO.getOrder(orderId);
    }

    @Override
    public List<OrderDetailVO> getOrderDetailList(int orderId) {
        return webDAO.getOrderDetailList(orderId);
    }

    @Override
    public List<OrderCouponVO> getOrderCouponList(int orderId) {
        return webDAO.getOrderCouponList(orderId);
    }

    @Override
    public List<OrderVO> getDeliverSum(String deliverName, String from, String to, int offset, int size, String sortColumn, String sort) {
        return webDAO.getDeliverSum(deliverName, from, to, offset, size, sortColumn, sort);
    }

    @Override
    public int countAllDeliverSum(String deliverName, String from, String to) {
        return webDAO.countAllDeliverSum(deliverName, from, to);
    }

    @Override
    public List<ActivityVO> getActivityList(String acitivityName, String status, int offset, int size, String sortColumn, String sort) {
        return webDAO.getActivityList(acitivityName, status, offset, size, sortColumn, sort);
    }

    @Override
    public int countAllActivity(String acitivityName, String status) {
        return webDAO.countAllActivity(acitivityName, status);
    }

    @Override
    public ActivityVO getActivity(int id) {
        return webDAO.getActivity(id);
    }

    @Override
    public ActivityVO getActivityByName(String name) {
        return webDAO.getActivityByName(name);
    }

    @Override
    public int addNewActivity(ActivityVO activity) {
        return webDAO.addNewActivity(activity);
    }

    @Override
    public void updateActivity(ActivityVO activity) {
       webDAO.updateActivity(activity);
    }

    @Override
    public void updateActivityAmountAndQty(int activityId, double amount, int qty) {
        webDAO.updateActivityAmountAndQty(activityId, amount, qty);
    }

    @Override
    public boolean deleteActivity(int activityId) {
        return webDAO.deleteActivity(activityId);
    }

    @Override
    public int getMaxActivityId() {
        return webDAO.getMaxActivityId();
    }

    @Override
    public void addTmpActivityDetail(long tmpUId, ActivityDetailVO tmp) {
        webDAO.addTmpActivityDetail(tmpUId, tmp);
    }

    @Override
    public void updateTmpActivityDetail(long id, double rate) {
        webDAO.updateTmpActivityDetail(id, rate);
    }

    @Override
    public void updateTmpActivityDetailRate(List<ActivityDetailVO> details) {
        webDAO.updateTmpActivityDetailRate(details);
    }

    @Override
    public void deleteTmpActivityDetail(long id, long tmpUId) {
        webDAO.deleteTmpActivityDetail(id, tmpUId);
    }

    @Override
    public void deleteAllTmpActivityDetail(long tmpUId) {
        webDAO.deleteAllTmpActivityDetail(tmpUId);
    }

    @Override
    public void makeTmpActivityDetail(long tmpUId, int activityId) {
        webDAO.makeTmpActivityDetail(tmpUId, activityId);
    }

    @Override
    public List<ActivityDetailVO> getTmpActivityDetailList(long tmpUId, String type) {
        return webDAO.getTmpActivityDetailList(tmpUId, type);
    }

    @Override
    public List<ActivityDetailVO> getActivityDetail(int activityId, String type) {
        return webDAO.getActivityDetail(activityId, type);
    }

    @Override
    public void addNewActivityDetail(int activityId, List<ActivityDetailVO> activityDetails) {
        webDAO.addNewActivityDetail(activityId, activityDetails);
    }

    @Override
    public void updateActivityDetail(int activityId, String type, List<ActivityDetailVO> activityDetails) {
        webDAO.updateActivityDetail(activityId, type, activityDetails);
    }

    @Override
    public void deleteActivityDetail(int activityId, String type) {
        webDAO.deleteActivityDetail(activityId, type);
    }
    
    
    
    
    /*********************************
     * jiw start
     *********************************/
    @Override
    public List<FeedbackVO> getFeedbackList(String keyword, String status, int offset, int size, String sortColumn, String sort) {
        return webDAO.getFeedbackList(keyword, status, offset, size, sortColumn, sort);
    }

    @Override
    public int countAllFeedback(String keyword, String status) {
        return webDAO.countAllFeedback(keyword, status);
    }
    
    @Override
    public void setFeedbackStatus(int feedbackId) {
        webDAO.setFeedbackStatus(feedbackId);
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
    public void addNewDeliver(DeliverVO newDeliver) {
        webDAO.addNewDeliver(newDeliver);
    }

    @Override
    public void updateDeliver(DeliverVO deliver) {
        webDAO.updateDeliver(deliver);
    }
    
    @Override
    public boolean deleteDeliver(int deliverId) {
        return webDAO.deleteDeliver(deliverId);
    }
    
    @Override
    public void setDeliverPassword(int deliverId, String password) {
        webDAO.setDeliverPassword(deliverId, password);
    }
    
    @Override
    public List<UserVO> getUserList(String keyword, String from, String to, int offset, int size, String sortColumn, String sort) {
        return webDAO.getUserList(keyword, from, to, offset, size, sortColumn, sort);
    }
    
    @Override
    public int countAllUser(String keyword, String from, String to) {
        return webDAO.countAllUser(keyword, from, to);
    }

    @Override
    public List<GoodsVO> getGoodsList(String keyword, int shopKindId, int shopId, int offset, int size, String sortColumn, String sort) {
        return webDAO.getGoodsList(keyword, shopKindId, shopId, offset, size, sortColumn, sort);
    }

    @Override
    public int countAllGoods(String keyword, int shopKindId, int shopId) {
        return webDAO.countAllGoods(keyword, shopKindId, shopId);
    }

    @Override
    public void addNewGoods(GoodsVO newGoods) {
        webDAO.addNewGoods(newGoods);
    }

    @Override
    public void updateGoods(GoodsVO goods) {
        webDAO.updateGoods(goods);
    }

    @Override
    public boolean deleteGoods(int id) {
        return webDAO.deleteGoods(id);
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
    public void changeGoodsStatus(int id, String status) {
        webDAO.changeGoodsStatus(id, status);
    }    

    @Override
    public List<ActivityNoticeVO> getActivityNoticeList(int offset, int size, String sortColumn, String sort) {
        return webDAO.getActivityNoticeList(offset, size, sortColumn, sort);
    }

    @Override
    public ActivityNoticeVO getActivityNotice(int id) {
        return webDAO.getActivityNotice(id);
    }
    
    @Override
    public int countAllActivityNotice() {
        return webDAO.countAllActivityNotice();
    }

    @Override
    public void addNewActivityNotice(ActivityNoticeVO newNotice) {
        webDAO.addNewActivityNotice(newNotice);
    }

    @Override
    public void updateActivityNotice(ActivityNoticeVO notice) {
        webDAO.updateActivityNotice(notice);
    }

    @Override
    public boolean deleteActivityNotice(int id) {
        return webDAO.deleteActivityNotice(id);
    }

    @Override
    public void setActivityNoticeStatus(int id) {
        webDAO.setActivityNoticeStatus(id);
    }

    @Override
    public List<ActivityVO> getActivityListByStatus() {
        return webDAO.getActivityListByStatus();
    }

    @Override
    public List<UserCouponVO> getUserCouponList(String couponName, String userTelNo, String couponStatus, int offset, int size, String sortColumn, String sort) {
        return webDAO.getUserCouponList(couponName, userTelNo, couponStatus, offset, size, sortColumn, sort);
    }

    @Override
    public int countAllUserCoupon(String couponName, String userTelNo, String couponStatus) {
        return webDAO.countAllUserCoupon(couponName, userTelNo, couponStatus);
    }
    
    
}
