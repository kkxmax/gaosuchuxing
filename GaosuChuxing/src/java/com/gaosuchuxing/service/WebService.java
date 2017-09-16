/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.service;

import com.gaosuchuxing.domain.DeliverVO;
import com.gaosuchuxing.domain.DistrictVO;
import com.gaosuchuxing.domain.FeedbackVO;
import com.gaosuchuxing.domain.ManagerVO;
import com.gaosuchuxing.domain.GroupVO;
import com.gaosuchuxing.domain.PermissionVO;
import com.gaosuchuxing.domain.RoleVO;
import com.gaosuchuxing.domain.StationVO;
import com.gaosuchuxing.domain.UserVO;
import java.util.List;

public interface WebService {
    /*********************************
     * hok start
     *********************************/
    public ManagerVO getAdminLogin(String adminId);    
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
    
    public List<StationVO> getStationList(String keyword, int districtId, String status, int offset, int size, String sortColumn, String sort);
    public int countAllStation(String keyword, int districtId, String status);
    public StationVO getStation(int id);
    public StationVO getStationByName(String stationName);
    public void addNewStation(StationVO newStation);
    public void updateStation(StationVO station);    
    public boolean deleteStation(int id);
    
    public List<DistrictVO> getDistrictList(int parentId, boolean isParent);
    
    /*********************************
     * jiw start
     *********************************/
    public List<FeedbackVO> getFeedbackList(String keyword, String status, int offset, int size, String sortColumn, String sort);
    public int countAllFeedback(String keyword, String status);
    public void setFeedbackStatus(int feedbackId);
    public DeliverVO getDeliver(String deliverId);
    public DeliverVO getDeliverById(String id);
    public List<DeliverVO> getDeliverList(String keyword, int offset, int size, String sortColumn, String sort);
    public int countAllDeliver(String keyword);
    public void addNewDeliver(DeliverVO newDeliver);
    public void updateDeliver(DeliverVO deliver);
    public void deleteDeliver(String deliverId);
    public void setDeliverPassword(String deliverId, String password);
    public List<UserVO> getUserList(String keyword, String from, String to, int offset, int size, String sortColumn, String sort);
    public int countAllUser(String keyword, String from, String to);
}
