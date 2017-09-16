/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.service.impl;

import com.gaosuchuxing.dao.WebDAO;
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
    public DeliverVO getDeliverById(String id) {
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
    public void deleteDeliver(String deliverId) {
        webDAO.deleteDeliver(deliverId);
    }
    
    @Override
    public void setDeliverPassword(String deliverId, String password) {
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
}
