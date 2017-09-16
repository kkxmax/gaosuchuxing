/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.delegate;

import com.gaosuchuxing.domain.DeliverVO;
import com.gaosuchuxing.domain.DistrictVO;
import com.gaosuchuxing.domain.FeedbackVO;
import com.gaosuchuxing.domain.ManagerVO;
import com.gaosuchuxing.domain.GroupVO;
import com.gaosuchuxing.domain.PermissionVO;
import com.gaosuchuxing.domain.RoleVO;
import com.gaosuchuxing.domain.StationVO;
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
  
    public List<StationVO> getStationList(String keyword, int districtId, String status, int offset, int size, String sortColumn, String sort) {
        return webService.getStationList(keyword, districtId, status, offset, size, sortColumn, sort);
    }

    public int countAllStation(String keyword, int districtId, String status) {
        return webService.countAllStation(keyword, districtId, status);
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
    
    public DeliverVO getDeliverById(String id) {
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
    
    public void deleteDeliver(String deliverId) {
        webService.deleteDeliver(deliverId);
    }
    
    public void setDeliverPassword(String deliverId, String password) {
        webService.setDeliverPassword(deliverId, password);
    }
    
    public List<UserVO> getUserList(String keyword, String from, String to, int offset, int size, String sortColumn, String sort) {
        return webService.getUserList(keyword, from, to, offset, size, sortColumn, sort);
    }
    
    public int countAllUser(String keyword, String from, String to) {
        return webService.countAllUser(keyword, from, to);
    }
}
