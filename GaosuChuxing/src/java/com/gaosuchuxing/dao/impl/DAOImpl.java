/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.dao.impl;

import com.gaosuchuxing.common.Constant;
import com.gaosuchuxing.dao.WebDAO;
import com.gaosuchuxing.domain.ActivityDetailVO;
import com.gaosuchuxing.domain.ActivityNoticeVO;
import com.gaosuchuxing.domain.ActivityVO;
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
import com.gaosuchuxing.listener.AppContextListener;
import com.gaosuchuxing.util.CommonUtil;
import com.gaosuchuxing.util.DateUtil;
import com.gaosuchuxing.util.NumberUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class DAOImpl extends JdbcDaoSupport implements WebDAO {
    
    /*********************************
     * hok start
     *********************************/
    @Override
    public ManagerVO getMangerLogin(String adminId) {
        Object[] args = new Object[] {adminId};
        String query = "SELECT managers.*, groups.name group_name FROM managers LEFT JOIN groups ON managers.group_id = groups.id WHERE managers.manager_id = ?";
        try {
            return (ManagerVO) getJdbcTemplate().queryForObject(query, args, new ManagerRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public ManagerVO getMangerByName(String name) {
        Object[] args = new Object[] {name};
        String query = "SELECT managers.*, groups.name group_name FROM managers LEFT JOIN groups ON managers.group_id = groups.id WHERE managers.name = ?";
        try {
            return (ManagerVO) getJdbcTemplate().queryForObject(query, args, new ManagerRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public boolean isDeliverOfStation(int deliverId) {
        try {
            return getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM stations WHERE stations.deliver_id = ?", new Object[]{deliverId}, Integer.class) > 0;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return false;
    }

    @Override
    public List<ManagerVO> getManagerList(String keyword, int groupId, int offset, int size, String sortColumn, String sort) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT managers.*, groups.name group_name FROM managers LEFT JOIN groups ON managers.group_id = groups.id ";
        String where = "", order = "", limit = "";
        
        if (!CommonUtil.isEmptyStr(keyword)) {
            where = "WHERE (managers.manager_id LIKE ? OR managers.name LIKE ?) ";
            args.add("%" + keyword + "%");
            args.add("%" + keyword + "%");
        }
        
        if (groupId != -1) {
            if (where.isEmpty())
                where = "WHERE managers.group_id = ? ";
            else
                where += "AND managers.group_id = ? ";
            
            args.add(groupId);
        }
        
        if (!CommonUtil.isEmptyStr(sortColumn)) 
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        else 
            order = " ORDER BY managers.reg_date DESC ";
                
        if (offset != -1 && size != -1) 
            limit = " LIMIT " + offset + ", " + size + " ";
                
        query += where + order + limit;
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new ManagerRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public int countAllManager(String keyword, int groupId) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT COUNT(*) group_name FROM managers LEFT JOIN groups ON managers.group_id = groups.id ";
        String where = "";
        
        if (!CommonUtil.isEmptyStr(keyword)) {
            where = "WHERE (managers.manager_id LIKE ? OR managers.name LIKE ?) ";
            args.add("%" + keyword + "%");
            args.add("%" + keyword + "%");
        }
        
        if (groupId != -1) {
            if (where.isEmpty())
                where = "WHERE managers.group_id = ? ";
            else
                where += "AND managers.group_id = ? ";
            
            args.add(groupId);
        }
                
        query += where;
        
        try {
            return getJdbcTemplate().queryForObject(query, args.toArray(), Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public ManagerVO getManager(int id) {
        Object[] args = new Object[] {id};
        String query = "SELECT managers.*, groups.name group_name FROM managers LEFT JOIN groups ON managers.group_id = groups.id WHERE managers.id = ?";
        try {
            return (ManagerVO) getJdbcTemplate().queryForObject(query, args, new ManagerRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public void addNewManager(ManagerVO manager) {
        String query = "INSERT INTO managers (manager_id,name,password,group_id,reg_date) VALUES(?,?,?,?,?)";
        Object[] args = new Object[] {
            manager.getManagerId(),manager.getName(),manager.getPassword(),manager.getGroupId(),new Date()
        };
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateManager(ManagerVO manager) {
        String query = "UPDATE managers SET name=?,group_id=? WHERE id=?";
        Object[] args = new Object[] {
            manager.getName(),manager.getGroupId(),manager.getId()
        };
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteManager(int id) {
        String query = "DELETE FROM managers WHERE id=?";
        Object[] args = new Object[] {id};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void setPassword(int id, String password) {
        String query = "UPDATE managers SET password=? WHERE id=?";
        Object[] args = new Object[] {password,id};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<GroupVO> getGroupList(String keyword, String sortColumn, String sort) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT * FROM groups ";
        String where = "", order = "";
        
        if (!CommonUtil.isEmptyStr(keyword)) {
            where = "WHERE (name LIKE ?) ";
            args.add("%" + keyword + "%");
        }
        
        if (!CommonUtil.isEmptyStr(sortColumn)) 
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        else 
            order = " ORDER BY reg_date DESC ";
                
        query += where + order;
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new GroupRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public boolean addNewGroup(GroupVO newGroup) {
        String query = "INSERT INTO groups (name,reg_date) VALUES(?,?)";
        Object[] args = new Object[] {
            newGroup.getName(),new Date()
        };
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }

    @Override
    public boolean updateGroup(GroupVO group) {
        return true;
    }

    @Override
    public boolean deleteGroup(int groupId) {
        String query = "DELETE FROM groups WHERE id=?";
        Object[] args = new Object[] {groupId};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }

    @Override
    public void setRole(int groupId, List<RoleVO> roles) {
        String query = "DELETE FROM roles WHERE group_id=?";
        Object[] args = new Object[] {groupId};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Connection transaction = null;
        boolean autoCommit = true;
        try {
            transaction = getConnection();
            autoCommit = transaction.getAutoCommit();
            transaction.setAutoCommit(false);            
            PreparedStatement stmt = null;
            query = "INSERT INTO roles (group_id,permission_id,status,reg_date) VALUES(?,?,?,?)";
            
            for (RoleVO role: roles) {
                if (stmt != null)
                    stmt.close();
                stmt = transaction.prepareStatement(query);
                stmt.setInt(1, groupId);
                stmt.setInt(2, role.getPermissionId());
                stmt.setBoolean(3, role.getStatus());
                stmt.setDate(4, new java.sql.Date(new Date().getTime()));
                stmt.execute();
            }
            transaction.commit();

            if (stmt != null)
                stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(DAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        } finally {
            if (transaction != null) {
                try {
                    transaction.setAutoCommit(autoCommit);
                } catch (SQLException ex) {
                    Logger.getLogger(DAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }
    
    @Override
    public List<RoleVO> getRoleList(int groupId) {
        try {
            return getJdbcTemplate().query("SELECT roles.*, permissions.name permission_name, permissions.parent_id FROM roles LEFT JOIN permissions ON roles.permission_id = permissions.id WHERE group_id = ?", new Object[] {groupId}, new RoleRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public boolean checkPermission(int managerId, String permissionName) {
        ManagerVO manager = getManager(managerId);
        
        if (manager == null) {
            return false;
        } else {
            int groupId = manager.getGroupId();
            RoleVO role = null;
            
            try {
                role = (RoleVO) getJdbcTemplate().queryForObject("SELECT roles.*, permissions.name permission_name, permissions.parent_id FROM roles LEFT JOIN permissions ON roles.permission_id = permissions.id WHERE group_id = ? AND permissions.name = ?", new Object[] {groupId, permissionName}, new RoleRowMapper());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            if (role == null) {
                return false;
            } else {
                return role.getStatus();
            }
        }
    }

    @Override
    public List<PermissionVO> getPermissionList() {
        try {
            return getJdbcTemplate().query("SELECT * FROM permissions ORDER BY ord", new PermissionRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public List<StationVO> getStationList(String keyword, int districtId, String adcode, String status, int offset, int size, String sortColumn, String sort) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT stations.*, delivers.name deliver_name, districts.name district_name, districts.parent_id, district_parents.name district_parent_name " +
                       "FROM stations INNER JOIN delivers ON stations.deliver_id = delivers.id " +
                       "INNER JOIN districts ON stations.district_id = districts.id " +
                       "INNER JOIN (SELECT * FROM districts WHERE parent_id = 0) district_parents ON districts.parent_id = district_parents.id ";
        String where = "", order = "", limit = "";
        
        if (!CommonUtil.isEmptyStr(keyword)) {
            where = "WHERE (stations.name LIKE ? OR delivers.name LIKE ?) ";
            args.add("%" + keyword + "%");
            args.add("%" + keyword + "%");
        }
        
        if (districtId != -1) {
            if (where.isEmpty())
                where = "WHERE stations.district_id = ? ";
            else
                where += "AND stations.district_id = ? ";
            
            args.add(districtId);
        }
        
        if (!CommonUtil.isEmptyStr(adcode)) {
            if (where.isEmpty())
                where = "WHERE districts.code = ? ";
            else
                where += "AND districts.code = ? ";
            
            args.add(adcode);
        }
        
        if (!CommonUtil.isEmptyStr(status)) {
            if (where.isEmpty())
                where = "WHERE stations.status = ? ";
            else
                where += "AND stations.status = ? ";
            
            args.add(status);
        }
        
        if (!CommonUtil.isEmptyStr(sortColumn)) 
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        else 
            order = " ORDER BY stations.reg_date DESC ";
                
        if (offset != -1 && size != -1) 
            limit = " LIMIT " + offset + ", " + size + " ";
                
        query += where + order + limit;
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new StationRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public int countAllStation(String keyword, int districtId, String adcode, String status) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT COUNT(*) " +
                       "FROM stations INNER JOIN delivers ON stations.deliver_id = delivers.id " +
                       "INNER JOIN districts ON stations.district_id = districts.id " +
                       "INNER JOIN (SELECT * FROM districts WHERE parent_id = 0) district_parents ON districts.parent_id = district_parents.id ";
        String where = "";
        
        if (!CommonUtil.isEmptyStr(keyword)) {
            where = "WHERE (stations.name LIKE ? OR delivers.name LIKE ?) ";
            args.add("%" + keyword + "%");
            args.add("%" + keyword + "%");
        }
        
        if (districtId != -1) {
            if (where.isEmpty())
                where = "WHERE stations.district_id = ? ";
            else
                where += "AND stations.district_id = ? ";
            
            args.add(districtId);
        }
        
        if (!CommonUtil.isEmptyStr(adcode)) {
            if (where.isEmpty())
                where = "WHERE districts.code = ? ";
            else
                where += "AND districts.code = ? ";
            
            args.add(adcode);
        }
        
        if (!CommonUtil.isEmptyStr(status)) {
            if (where.isEmpty())
                where = "WHERE stations.status = ? ";
            else
                where += "AND stations.status = ? ";
            
            args.add(status);
        }
                
        query += where;
        
        try {
            return getJdbcTemplate().queryForObject(query, args.toArray(), Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public StationVO getStation(int id) {
        String query = "SELECT stations.*, delivers.name deliver_name, districts.name district_name, districts.parent_id, district_parents.name district_parent_name " +
                       "FROM stations INNER JOIN delivers ON stations.deliver_id = delivers.id " +
                       "INNER JOIN districts ON stations.district_id = districts.id " +
                       "INNER JOIN (SELECT * FROM districts WHERE parent_id = 0) district_parents ON districts.parent_id = district_parents.id " + 
                       "WHERE stations.id = ? ";
        
        try {
            return (StationVO) getJdbcTemplate().queryForObject(query, new Object[] {id}, new StationRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public StationVO getStationByName(String name) {
        String query = "SELECT stations.*, delivers.name deliver_name, districts.name district_name, districts.parent_id, district_parents.name district_parent_name " +
                       "FROM stations INNER JOIN delivers ON stations.deliver_id = delivers.id " +
                       "INNER JOIN districts ON stations.district_id = districts.id " +
                       "INNER JOIN (SELECT * FROM districts WHERE parent_id = 0) district_parents ON districts.parent_id = district_parents.id " + 
                       "WHERE stations.name = ? ";
        
        try {
            return (StationVO) getJdbcTemplate().queryForObject(query, new Object[] {name}, new StationRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public void addNewStation(StationVO newStation) {
        String query = "INSERT INTO stations (name,district_id,deliver_id,status,latitude,longitude,image_path,reg_date) VALUES(?,?,?,?,?,?,?,?)";
        Object[] args = new Object[] {
            newStation.getName(),newStation.getDistrictId(),newStation.getDeliverId(),newStation.getStatus(),newStation.getLatitude(),newStation.getLongitude(),newStation.getImagePath(),new Date()
        };
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateStation(StationVO station) {
        String query;
        Object[] args;
        if (station.getLatitude() > 0 && station.getLongitude() > 0 && !CommonUtil.isEmptyStr(station.getImagePath())) {
            query = "UPDATE stations SET name=?,district_id=?,deliver_id=?,status=?,latitude=?,longitude=?,image_path=? WHERE id = ?";
            args = new Object[] {
                  station.getName(),station.getDistrictId(),station.getDeliverId(),station.getStatus(),station.getLatitude(),station.getLongitude(),station.getImagePath(),station.getId()
            };
        } else if (station.getLatitude() > 0 && station.getLongitude() > 0) {
            query = "UPDATE stations SET name=?,district_id=?,deliver_id=?,status=?,latitude=?,longitude=? WHERE id = ?";
            args = new Object[] {
                  station.getName(),station.getDistrictId(),station.getDeliverId(),station.getStatus(),station.getLatitude(),station.getLongitude(),station.getId()
            };
        } else if (!CommonUtil.isEmptyStr(station.getImagePath())) {
            query = "UPDATE stations SET name=?,district_id=?,deliver_id=?,status=?,image_path=? WHERE id = ?";
            args = new Object[] {
                  station.getName(),station.getDistrictId(),station.getDeliverId(),station.getStatus(),station.getImagePath(),station.getId()
            };
        } else {
            query = "UPDATE stations SET name=?,district_id=?,deliver_id=?,status=? WHERE id = ?";
            args = new Object[] {
                  station.getName(),station.getDistrictId(),station.getDeliverId(),station.getStatus(),station.getId()
            };
        }
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean deleteStation(int id) {
        String query = "DELETE FROM stations WHERE id = ? ";
        Object[] args = new Object[] {id};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }

    @Override
    public List<DistrictVO> getDistrictList(int parentId, boolean isParent) {
        ArrayList<Object> args = new ArrayList<Object>();
        
        String query = "SELECT * FROM districts ";
        String where = "";
        
        if (parentId != -1) {
            where = " WHERE parent_id = ? AND IFNULL(code, 0) <> '' AND LENGTH(code) = 6 ";
            args.add(parentId);
        }
        
        if (isParent) {
            if (where.isEmpty())
                where = " WHERE parent_id = 0 ";
            else
                where += " AND parent_id = 0 ";
        }
        
        query += where + " ORDER BY ord";
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new DistrictRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public List<GoodsKindVO> getGoodsKindList(int shopKindId, String name, int offset, int size, String sortColumn, String sort) {
        String where = "", order = "", limit = "";
        ArrayList<Object> args = new ArrayList<Object>();
        if (shopKindId != -1) {
            where = " WHERE goods_kinds.shop_kind_id = ?";
            args.add(shopKindId);
        }
        if (!CommonUtil.isEmptyStr(name)) {
            if (where.isEmpty())
                where = " WHERE goods_kinds.name LIKE ? ";
            else
                where = " AND goods_kinds.name LIKE ? ";
            args.add("%" + name + "%");
        }
        
        if (offset != -1 && size != -1) 
            limit = " LIMIT " + offset + ", " + size + " ";
        
        if (!CommonUtil.isEmptyStr(sortColumn)) {
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        } else {
            order = " ORDER BY goods_kinds.reg_date DESC ";
        }
        
        try {
            return getJdbcTemplate().query("SELECT goods_kinds.*, shop_kinds.name shop_kind_name FROM goods_kinds INNER JOIN shop_kinds ON goods_kinds.shop_kind_id = shop_kinds.id " + where + order + limit, args.toArray(), new GoodsKindRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @Override
    public GoodsKindVO getGoodsKind(int id) {        
        try {
            return (GoodsKindVO) getJdbcTemplate().queryForObject("SELECT goods_kinds.*, shop_kinds.name shop_kind_name FROM goods_kinds INNER JOIN shop_kinds ON goods_kinds.shop_kind_id = shop_kinds.id WHERE goods_kinds.id = ?", new Object[] {id}, new GoodsKindRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addNewGoodsKind(String name, int shopKindId) {
        String query = "INSERT INTO goods_kinds (name,shop_kind_id,reg_date) VALUES(?,?,?) ";
        Object[] args = new Object[] {name,shopKindId,new Date()};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }

    @Override
    public boolean updateGoodsKind(String name, int shopKindId, int id) {
        String query = "UPDATE goods_kinds SET name=?,shop_kind_id=? WHERE id = ? ";
        Object[] args = new Object[] {name,shopKindId,id};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }

    @Override
    public boolean deleteGoodsKind(int id) {
        String query = "DELETE FROM goods_kinds WHERE id = ? ";
        Object[] args = new Object[] {id};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }

    @Override
    public List<ShopKindVO> getShopKindList(String keyword, int offset, int size, String sortColumn, String sort) {
        String where = "", order = "", limit = "";
        Object[] args = null;
        
        if (!CommonUtil.isEmptyStr(keyword)) {
            where = " WHERE name LIKE ? ";
            args = new Object[] {"%"+keyword+"%"};
        }
        
        if (!CommonUtil.isEmptyStr(sortColumn)) {
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        }
        
        if (offset != -1 && size != -1) 
            limit = " LIMIT " + offset + ", " + size + " ";
        
        try {
            return getJdbcTemplate().query("SELECT * FROM shop_kinds " + where + order + limit, args, new ShopKindRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ShopKindVO getShopKind(int shopKindId) {        
        try {
            return (ShopKindVO) getJdbcTemplate().queryForObject("SELECT * FROM shop_kinds WHERE id = ?", new Object[] {shopKindId}, new ShopKindRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addNewShopKind(ShopKindVO newShopKind) {
        String query = "INSERT INTO shop_kinds (name,reg_date) VALUES(?,?) ";
        Object[] args = new Object[] {newShopKind.getName(),new Date()};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }

    @Override
    public boolean updateShopKind(ShopKindVO shopKind) {
        String query = "UPDATE shop_kinds SET name = ? WHERE id = ? ";
        Object[] args = new Object[] {shopKind.getName(),shopKind.getId()};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }

    @Override
    public boolean deleteShopKind(int shopKindId) {
        String query = "DELETE FROM shop_kinds WHERE id = ? ";
        Object[] args = new Object[] {shopKindId};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    

    @Override
    public List<ShopVO> getShopList(String type, String keyword, int stationId, int shopKindId, int districtId, int offset, int size, String sortColumn, String sort) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT shops.*, stations.name station_name, shop_kinds.name shop_kind_name, districts.name district_name, districts.parent_id, district_parents.name district_parent_name " +
                       "FROM shops INNER JOIN stations ON shops.station_id = stations.id " +
                       "INNER JOIN shop_kinds ON shops.shop_kind_id = shop_kinds.id " +
                       "INNER JOIN districts ON shops.district_id = districts.id " +
                       "INNER JOIN  (SELECT * FROM districts WHERE parent_id = 0) district_parents ON districts.parent_id = district_parents.id ";
        String where = " WHERE 1 = 1 ", order = "", limit = "";
        
        if (!CommonUtil.isEmptyStr(type)) {
            where += "AND shops.type = ? ";
            args.add(type);
        }
                
        if (!CommonUtil.isEmptyStr(keyword)) {
            where += "AND shops.name LIKE ? ";
            args.add("%" + keyword + "%");
        }
        
        if (stationId != -1) {
            where += "AND shops.station_id = ? ";            
            args.add(stationId);
        }
        
        if (shopKindId != -1) {
            where += "AND shops.shop_kind_id = ? ";
            args.add(shopKindId);
        }
        
        if (districtId != -1) {
            where += "AND shops.district_id = ? ";
            args.add(districtId);
        }
            
        
        if (!CommonUtil.isEmptyStr(sortColumn)) 
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        else 
            order = " ORDER BY shops.ord DESC, shops.id DESC ";
                
        if (offset != -1 && size != -1) 
            limit = " LIMIT " + offset + ", " + size + " ";
                
        query += where + order + limit;
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new ShopRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public int countAllShop(String type, String keyword, int stationId, int shopKindId, int districtId) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT COUNT(*) " +
                       "FROM shops INNER JOIN stations ON shops.station_id = stations.id " +
                       "INNER JOIN shop_kinds ON shops.shop_kind_id = shop_kinds.id " +
                       "INNER JOIN districts ON shops.district_id = districts.id " +
                       "INNER JOIN  (SELECT * FROM districts WHERE parent_id = 0) district_parents ON districts.parent_id = district_parents.id ";
        String where = "", order = "", limit = "";
        
        where = "WHERE shops.type = ? ";
        args.add(type);
        
        if (!CommonUtil.isEmptyStr(keyword)) {
            where = "AND shops.name LIKE ? ";
            args.add("%" + keyword + "%");
        }
        
        if (stationId != -1) {
            where += "AND shops.station_id = ? ";            
            args.add(stationId);
        }
        
        if (shopKindId != -1) {
            where += "AND shops.shop_kind_id = ? ";
            args.add(shopKindId);
        }
        
        if (districtId != -1) {
            where += "AND shops.district_id = ? ";
            args.add(districtId);
        }
                
        query += where;
        
        try {
            return getJdbcTemplate().queryForObject(query, args.toArray(), Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public ShopVO getShop(int shopId) {
        String query = "SELECT shops.*, stations.name station_name, shop_kinds.name shop_kind_name, districts.name district_name, districts.parent_id, district_parents.name district_parent_name " +
                       "FROM shops INNER JOIN stations ON shops.station_id = stations.id " +
                       "INNER JOIN shop_kinds ON shops.shop_kind_id = shop_kinds.id " +
                       "INNER JOIN districts ON shops.district_id = districts.id " +
                       "INNER JOIN  (SELECT * FROM districts WHERE parent_id = 0) district_parents ON districts.parent_id = district_parents.id " +
                       "WHERE shops.id = ?";
        try {
            return (ShopVO) getJdbcTemplate().queryForObject(query, new Object[]{shopId}, new ShopRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public ShopVO getShopByName(String shopName) {
        String query = "SELECT shops.*, stations.name station_name, shop_kinds.name shop_kind_name, districts.name district_name, districts.parent_id, district_parents.name district_parent_name " +
                       "FROM shops INNER JOIN stations ON shops.station_id = stations.id " +
                       "INNER JOIN shop_kinds ON shops.shop_kind_id = shop_kinds.id " +
                       "INNER JOIN districts ON shops.district_id = districts.id " +
                       "INNER JOIN  (SELECT * FROM districts WHERE parent_id = 0) district_parents ON districts.parent_id = district_parents.id " +
                       "WHERE shops.name = ?";
        try {
            return (ShopVO) getJdbcTemplate().queryForObject(query, new Object[]{shopName}, new ShopRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public int getGoodsIdForCheckGoodsNameOfShop(int shopId, String goodsName) {
        String query = "select id from goods where shop_id = ? AND name = ? AND del = 0";
        
        try {
            return getJdbcTemplate().queryForObject(query, new Object[] {shopId, goodsName}, Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }
       

    @Override
    public void addNewShop(ShopVO newShop) {
        String query = "SELECT MAX(ord) FROM shops";
        int ord = 0;
        try {
            ord = getJdbcTemplate().queryForObject(query, Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        ord++;
        
        query = "INSERT INTO shops (name,station_id,shop_kind_id,district_id,address,start_fee,shipping_fee,description,type,image_path,reg_date,ord) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] args = new Object[] {
            newShop.getName(),newShop.getStationId(),newShop.getShopKindId(),newShop.getDistrictId(),newShop.getAddress(),newShop.getStartFee(),newShop.getShippingFee(),newShop.getDescription(),newShop.getType(),newShop.getImagePath(),new Date(),ord
        };
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateShop(ShopVO shop) {
        String query;
        Object[] args;
        if (!CommonUtil.isEmptyStr(shop.getImagePath())) {
            query = "UPDATE shops SET name=?,station_id=?,shop_kind_id=?,district_id=?,address=?,start_fee=?,shipping_fee=?,description=?,type=?,image_path=? WHERE id = ?";
            args = new Object[] {
                shop.getName(),shop.getStationId(),shop.getShopKindId(),shop.getDistrictId(),shop.getAddress(),shop.getStartFee(),shop.getShippingFee(),shop.getDescription(),shop.getType(),shop.getImagePath(),shop.getId()
            };
        } else {
            query = "UPDATE shops SET name=?,station_id=?,shop_kind_id=?,district_id=?,address=?,start_fee=?,shipping_fee=?,description=?,type=? WHERE id = ?";
            args = new Object[] {
                shop.getName(),shop.getStationId(),shop.getShopKindId(),shop.getDistrictId(),shop.getAddress(),shop.getStartFee(),shop.getShippingFee(),shop.getDescription(),shop.getType(),shop.getId()
            };
        }
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean deleteShop(int shopId) {
        String query = "DELETE FROM shops WHERE id = ?";
        Object[] args = new Object[] {shopId};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }

    @Override
    public List<OrderVO> getOrderList(int state, String orderNum, String userName, String deliverName, String orderStatus, String from, String to, int offset, int size, String sortColumn, String sort) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT orders.*, users.name user_name, users.user_id user_tel_no, " +
                        "shops.name shop_name, shops.address shop_address, stations.name station_name, " +
                        "delivers.name deliver_name, delivers.tel_no deliver_tel_no, order_coupons.amount coupon_amount " +
                        "FROM orders " +
                        "INNER JOIN users ON orders.user_id = users.id " +
                        "INNER JOIN shops ON orders.shop_id = shops.id " +
                        "INNER JOIN stations ON shops.station_id = stations.id " +
                        "INNER JOIN delivers ON orders.deliver_id = delivers.id  " + 
                        "LEFT JOIN order_coupons ON orders.id = order_coupons.order_id ";
        String where = "", order = "", limit = "";
        
        if (state != -1) {
            if (state > 0) {
                where = " WHERE orders.state = TRUE ";
            } else {
                where = " WHERE orders.state = FALSE ";
            }
        }
        
        if (!CommonUtil.isEmptyStr(orderNum)) {
            if (where.isEmpty())
                where = " WHERE orders.order_num LIKE ? ";
            else
                where += " AND orders.order_num LIKE ? ";
            args.add("%" + orderNum + "%");
        }
                
        if (!CommonUtil.isEmptyStr(userName)) {
            if (where.isEmpty())
                where = " WHERE users.name LIKE ? ";
            else
                where += " AND users.name LIKE ? ";
            args.add("%" + userName + "%");
        }
        
        if (!CommonUtil.isEmptyStr(deliverName)) {
            if (where.isEmpty())
                where = " WHERE delivers.name LIKE ? ";
            else
                where += " AND delivers.name LIKE ? ";
            args.add("%" + deliverName + "%");
        }
        
        if (!CommonUtil.isEmptyStr(orderStatus)) {
            if (where.isEmpty())
                where = " WHERE orders.order_status = ? ";
            else
                where += " AND orders.order_status = ? ";
            args.add(orderStatus);
        }
        
        if (!CommonUtil.isEmptyStr(from)) {
            if (where.isEmpty())
                where = " WHERE orders.pay_date >= ? ";
            else
                where += " AND orders.pay_date >= ? ";
            args.add(from);
        }
        
        if (!CommonUtil.isEmptyStr(to)) {
            if (where.isEmpty())
                where = " WHERE orders.pay_date < ADDDATE(?, 1) ";
            else
                where += " AND orders.pay_date < ADDDATE(?, 1) ";
            args.add(to);
        }
        
        if (!CommonUtil.isEmptyStr(sortColumn)) 
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        else 
            order = " ORDER BY orders.reg_date DESC ";
                
        if (offset != -1 && size != -1) 
            limit = " LIMIT " + offset + ", " + size + " ";
                
        query += where + order + limit;
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new OrderRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public int countAllOrder(int state, String orderNum, String userName, String deliverName, String orderStatus, String from, String to) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT COUNT(*) " +
                        "FROM orders " +
                        "INNER JOIN users ON orders.user_id = users.id " +
                        "INNER JOIN shops ON orders.shop_id = shops.id " +
                        "INNER JOIN stations ON shops.station_id = stations.id " +
                        "INNER JOIN delivers ON orders.deliver_id = delivers.id  ";
        String where = "";
        
        if (state != -1) {
            if (state > 0) {
                where = " WHERE orders.state = TRUE ";
            } else {
                where = " WHERE orders.state = FALSE ";
            }
        }
        
        if (!CommonUtil.isEmptyStr(orderNum)) {
            if (where.isEmpty())
                where = " WHERE orders.order_num LIKE ? ";
            else
                where += " AND orders.order_num LIKE ? ";
            args.add("%" + orderNum + "%");
        }
                
        if (!CommonUtil.isEmptyStr(userName)) {
            if (where.isEmpty())
                where = " WHERE users.name LIKE ? ";
            else
                where += " AND users.name LIKE ? ";
            args.add("%" + userName + "%");
        }
        
        if (!CommonUtil.isEmptyStr(deliverName)) {
            if (where.isEmpty())
                where = " WHERE delivers.name LIKE ? ";
            else
                where += " AND delivers.name LIKE ? ";
            args.add("%" + deliverName + "%");
        }
        
        if (!CommonUtil.isEmptyStr(orderStatus)) {
            if (where.isEmpty())
                where = " WHERE orders.order_status = ? ";
            else
                where += " AND orders.order_status = ? ";
            args.add(orderStatus);
        }
        
        if (!CommonUtil.isEmptyStr(from)) {
            if (where.isEmpty())
                where = " WHERE orders.pay_date >= ? ";
            else
                where += " AND orders.pay_date >= ? ";
            args.add(from);
        }
        
        if (!CommonUtil.isEmptyStr(to)) {
            if (where.isEmpty())
                where = " WHERE orders.pay_date < ADDDATE(?, 1) ";
            else
                where += " AND orders.pay_date < ADDDATE(?, 1) ";
            args.add(to);
        }
                
        query += where;
        
        try {
            return getJdbcTemplate().queryForObject(query, args.toArray(), Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }
    
    @Override
    public double getOrderAmount(int state, String orderNum, String userName, String deliverName, String orderStatus, String from, String to) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT SUM(CASE WHEN IFNULL(orders.order_amount, 0) + IFNULL(orders.shipping_fee, 0) - IFNULL(order_coupons.amount, 0) > 0 THEN IFNULL(orders.order_amount, 0) + IFNULL(orders.shipping_fee, 0) - IFNULL(order_coupons.amount, 0) ELSE 0 END) " +
                        "FROM orders " +
                        "INNER JOIN users ON orders.user_id = users.id " +
                        "INNER JOIN shops ON orders.shop_id = shops.id " +
                        "INNER JOIN stations ON shops.station_id = stations.id " +
                        "INNER JOIN delivers ON orders.deliver_id = delivers.id " +
                        "LEFT JOIN order_coupons ON orders.id = order_coupons.order_id";
        String where = "";
        
        if (state != -1) {
            if (state > 0) {
                where = " WHERE orders.state = TRUE ";
            } else {
                where = " WHERE orders.state = FALSE ";
            }
        }
        
        if (!CommonUtil.isEmptyStr(orderNum)) {
            if (where.isEmpty())
                where = " WHERE orders.order_num LIKE ? ";
            else
                where += " AND orders.order_num LIKE ? ";
            args.add("%" + orderNum + "%");
        }
                
        if (!CommonUtil.isEmptyStr(userName)) {
            if (where.isEmpty())
                where = " WHERE users.name LIKE ? ";
            else
                where += " AND users.name LIKE ? ";
            args.add("%" + userName + "%");
        }
        
        if (!CommonUtil.isEmptyStr(deliverName)) {
            if (where.isEmpty())
                where = " WHERE delivers.name LIKE ? ";
            else
                where += " AND delivers.name LIKE ? ";
            args.add("%" + deliverName + "%");
        }
        
        if (!CommonUtil.isEmptyStr(orderStatus)) {
            if (where.isEmpty())
                where = " WHERE orders.order_status = ? ";
            else
                where += " AND orders.order_status = ? ";
            args.add(orderStatus);
        }
        
        if (!CommonUtil.isEmptyStr(from)) {
            if (where.isEmpty())
                where = " WHERE orders.pay_date >= ? ";
            else
                where += " AND orders.pay_date >= ? ";
            args.add(from);
        }
        
        if (!CommonUtil.isEmptyStr(to)) {
            if (where.isEmpty())
                where = " WHERE orders.pay_date < ADDDATE(?, 1) ";
            else
                where += " AND orders.pay_date < ADDDATE(?, 1) ";
            args.add(to);
        }
                
        query += where;
        
        try {
            return getJdbcTemplate().queryForObject(query, args.toArray(), Double.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public OrderVO getOrder(int orderId) {
        String query = "SELECT orders.*, users.name user_name, users.user_id user_tel_no, " +
                        "shops.name shop_name, shops.address shop_address, stations.name station_name, " +
                        "delivers.name deliver_name, delivers.tel_no deliver_tel_no, order_coupons.amount coupon_amount " +
                        "FROM orders " +
                        "INNER JOIN users ON orders.user_id = users.id " +
                        "INNER JOIN shops ON orders.shop_id = shops.id " +
                        "INNER JOIN stations ON shops.station_id = stations.id " +
                        "INNER JOIN delivers ON orders.deliver_id = delivers.id  " +
                        "LEFT JOIN order_coupons ON orders.id = order_coupons.order_id " +
                        "WHERE orders.id = ? ";
        
        try {
            return (OrderVO) getJdbcTemplate().queryForObject(query, new Object[] {orderId}, new OrderRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public List<OrderDetailVO> getOrderDetailList(int orderId) {
        String query = "SELECT order_details.order_id, order_details.goods_id, order_details.price, goods.num goods_num, goods.name goods_name, goods.image_path, " +
                        "SUM(qty) qty, SUM(qty * order_details.price) amount " +
                        "FROM order_details " +
                        "INNER JOIN orders ON order_details.order_id = orders.id " +
                        "INNER JOIN goods ON order_details.goods_id = goods.id " +
                        "WHERE order_details.order_id = ? " +
                        "GROUP BY order_details.order_id, order_details.goods_id, order_details.price, goods.num, goods.name, goods.image_path ";
        
        try {
            return getJdbcTemplate().query(query, new Object[] {orderId}, new OrderDetailRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public List<OrderCouponVO> getOrderCouponList(int orderId) {
        String query = "SELECT order_coupons.* " +
                        "FROM order_coupons " +
                        "INNER JOIN orders ON order_coupons.order_id = orders.id " +
                        "WHERE order_coupons.order_id = ? ";
        
        try {
            return getJdbcTemplate().query(query, new Object[] {orderId}, new OrderCouponRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public List<OrderVO> getDeliverSum(String deliverName, String from, String to, int offset, int size, String sortColumn, String sort) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT delivers.deliver_id deliver_account_id, delivers.name deliver_name, SUM(order_details.qty) order_qty " +
                        "FROM orders " +
                        "INNER JOIN order_details ON orders.id = order_details.order_id " +
                        "INNER JOIN delivers ON orders.deliver_id = delivers.id  ";
        String where = " WHERE orders.order_status = ? ", group = "GROUP BY delivers.deliver_id, delivers.name ",  order = "", limit = "";
        
        args.add(Constant.ORDER_STATUS.COMPLETED);
        
        if (!CommonUtil.isEmptyStr(deliverName)) {
            if (where.isEmpty())
                where = " WHERE delivers.name LIKE ? ";
            else
                where += " AND delivers.name LIKE ? ";
            args.add("%" + deliverName + "%");
        }
        
        if (!CommonUtil.isEmptyStr(from)) {
            if (where.isEmpty())
                where = " WHERE orders.delivery_date >= ? ";
            else
                where += " AND orders.delivery_date >= ? ";
            args.add(from);
        }
        
        if (!CommonUtil.isEmptyStr(to)) {
            if (where.isEmpty())
                where = " WHERE orders.delivery_date < ADDDATE(?, 1) ";
            else
                where += " AND orders.delivery_date < ADDDATE(?, 1) ";
            args.add(to);
        }
        
        if (!CommonUtil.isEmptyStr(sortColumn)) 
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        else 
            order = " ORDER BY SUM(order_details.qty) DESC ";
                
        if (offset != -1 && size != -1) 
            limit = " LIMIT " + offset + ", " + size + " ";
                
        query += where + group + order + limit;
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new DeliverSumRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public int countAllDeliverSum(String deliverName, String from, String to) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT delivers.deliver_id deliver_account_id, delivers.name deliver_name, SUM(order_details.qty) order_qty " +
                        "FROM orders " +
                        "INNER JOIN order_details ON orders.id = order_details.order_id " +
                        "INNER JOIN delivers ON orders.deliver_id = delivers.id  ";
        String where = " WHERE orders.order_status = ? ", group = "GROUP BY delivers.deliver_id, delivers.name ";
        
        args.add(Constant.ORDER_STATUS.COMPLETED);
        
        if (!CommonUtil.isEmptyStr(deliverName)) {
            if (where.isEmpty())
                where = " WHERE delivers.name LIKE ? ";
            else
                where += " AND delivers.name LIKE ? ";
            args.add("%" + deliverName + "%");
        }
        
        if (!CommonUtil.isEmptyStr(from)) {
            if (where.isEmpty())
                where = " WHERE orders.delivery_date >= ? ";
            else
                where += " AND orders.delivery_date >= ? ";
            args.add(from);
        }
        
        if (!CommonUtil.isEmptyStr(to)) {
            if (where.isEmpty())
                where = " WHERE orders.delivery_date < ADDDATE(?, 1) ";
            else
                where += " AND orders.delivery_date < ADDDATE(?, 1) ";
            args.add(to);
        }
                
        query += where + group;
        
        query = "SELECT COUNT(*) FROM (" + query  + ") a";
        
        try {
            return getJdbcTemplate().queryForObject(query, args.toArray(), Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public List<ActivityVO> getActivityList(String acitivityName, String status, int offset, int size, String sortColumn, String sort) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT * FROM activities ";
        String where = "", order = "", limit = "";
        
        if (!CommonUtil.isEmptyStr(acitivityName)) {
            if (where.isEmpty())
                where = " WHERE name LIKE ? ";
            else
                where += " AND name LIKE ? ";
            args.add("%" + acitivityName + "%");
        }
        
        if (!CommonUtil.isEmptyStr(status)) {
            if (where.isEmpty())
                where = " WHERE status = ? ";
            else
                where += " AND status = ? ";
            args.add(status);
        }
        
        if (!CommonUtil.isEmptyStr(sortColumn)) 
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        else 
            order = " ORDER BY start_date DESC ";
                
        if (offset != -1 && size != -1) 
            limit = " LIMIT " + offset + ", " + size + " ";
                
        query += where + order + limit;
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new ActivityRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public int countAllActivity(String acitivityName, String status) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT COUNT(*) FROM activities ";
        String where = "";
        
        if (!CommonUtil.isEmptyStr(acitivityName)) {
            if (where.isEmpty())
                where = " WHERE name LIKE ? ";
            else
                where += " AND name LIKE ? ";
            args.add("%" + acitivityName + "%");
        }
        
        if (!CommonUtil.isEmptyStr(status)) {
            if (where.isEmpty())
                where = " WHERE status = ? ";
            else
                where += " AND status = ? ";
            args.add(status);
        }
                
        query += where;
        
        try {
            return getJdbcTemplate().queryForObject(query, args.toArray(), Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public ActivityVO getActivity(int id) {
        try {
            return (ActivityVO) getJdbcTemplate().queryForObject("SELECT * FROM activities WHERE id = ?", new Object[] {id}, new ActivityRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public ActivityVO getActivityByName(String name) {
        try {
            return (ActivityVO) getJdbcTemplate().queryForObject("SELECT * FROM activities WHERE name = ?", new Object[] {name}, new ActivityRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public int addNewActivity(ActivityVO activity) {
        String pattern = DateUtil.formatDate(new Date(), "YYMMdd");        
        String query = "SELECT MAX(num) FROM activities WHERE num LIKE ?";
        Object[] args = new Object[] {pattern + "%"};
        
        String maxNum = null, newNum;
        
        try {
            maxNum = getJdbcTemplate().queryForObject(query, args, String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        if (CommonUtil.isEmptyStr(maxNum)) {
            newNum = pattern + "001";
        } else {
            BigDecimal bigdec = new BigDecimal(maxNum);
            BigDecimal addbig = bigdec.add(new BigDecimal(1));
            newNum = addbig.toString();
        }
        
        query = "INSERT INTO activities (num,name,start_date,end_date,activity_amount,coupon_qty,image_path,reg_date,unlimited_type,full_type,share_title,share_content) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        args = new Object[] {
            newNum,activity.getName(),activity.getStartDate(),activity.getEndDate(),activity.getActivityAmount(),
            activity.getCouponQty(),activity.getImagePath(),new Date(),activity.getUnlimitedType(),activity.getFullType(),
            activity.getShareTitle(),activity.getShareContent()
        };
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        
        AppContextListener.refreshTaskSchedule();
        
        query = "SELECT MAX(id) FROM activities";
        
        try {
            return getJdbcTemplate().queryForObject(query, Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return -1;
    }

    @Override
    public void updateActivity(ActivityVO activity) {
        String query;
        Object[] args;
        
        if (!CommonUtil.isEmptyStr(activity.getImagePath())) {
            query = "UPDATE activities SET name=?,start_date=?,end_date=?,activity_amount=?,coupon_qty=?,image_path=?,unlimited_type=?,full_type=?,share_title=?,share_content=? WHERE id=?";
            args = new Object[] {
                activity.getName(),activity.getStartDate(),activity.getEndDate(),activity.getActivityAmount(),activity.getCouponQty(),activity.getImagePath(),activity.getUnlimitedType(),activity.getFullType(),activity.getShareTitle(),activity.getShareContent(),activity.getId()
            };
        } else {
            query = "UPDATE activities SET name=?,start_date=?,end_date=?,activity_amount=?,coupon_qty=?,unlimited_type=?,full_type=?,share_title=?,share_content=? WHERE id=?";
            args = new Object[] {
                activity.getName(),activity.getStartDate(),activity.getEndDate(),activity.getActivityAmount(),activity.getCouponQty(),activity.getUnlimitedType(),activity.getFullType(),activity.getShareTitle(),activity.getShareContent(),activity.getId()
            };
        }
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        AppContextListener.refreshTaskSchedule();
    }

    @Override
    public void updateActivityAmountAndQty(int activityId, double amount, int qty) {
        String query = "UPDATE coupons a JOIN activity_details b ON a.num = b.coupon_num " +
                       "JOIN activities c ON b.activity_id = c.id " +
                       "SET a.name = c.name WHERE c.id = ?";
        try {
            getJdbcTemplate().update(query, new Object[] {activityId}); 
            getJdbcTemplate().update("UPDATE activities SET activity_amount=?,coupon_qty=? WHERE id=?", new Object[] {amount, qty, activityId});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean deleteActivity(int activityId) {
        try {
            getJdbcTemplate().update("DELETE FROM activities WHERE id=?", new Object[] {activityId});
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    @Override
    public int getMaxActivityId() {
        
        try {
            return getJdbcTemplate().queryForObject("SELECT MAX(*) FROM activities ", Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public void addTmpActivityDetail(long tmpUId, ActivityDetailVO tmp) {
//        Object[] args = new Object[] {
//            System.currentTimeMillis(),tmpUId,tmp.getFullAmount(),tmp.getCouponAmount(),tmp.getQty(),tmp.getRate(),tmp.getType()
//        };
//        try {
//            getJdbcTemplate().update(query, args);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        String type = tmp.getType();
        String pattern = "", kind = "";
        if (type.equals(Constant.ACTIVITY_TYPE.FULL)) {
            pattern = "0103";
            kind = "满" + NumberUtil.format(tmp.getFullAmount(), "#0.##") + "元";
        } else {
            pattern = "0203";
            kind = "不限" + NumberUtil.format(tmp.getCouponAmount(), "#0.##") + "元";
        }
        
        String maxNum = "", num = "";
        
        try {
            maxNum = getJdbcTemplate().queryForObject("SELECT MAX(num) FROM coupons WHERE num LIKE ?", new Object[] {pattern + "%"}, String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        if (CommonUtil.isEmptyStr(maxNum)) {
            num = pattern + "000001";
        } else {
            String str = maxNum.substring(1);
            BigDecimal bigdec = new BigDecimal(str);
            BigDecimal addbig = bigdec.add(new BigDecimal(1));
            num = "0" + addbig.toString();
        }   
            
        String query = "INSERT INTO coupons (num,name,kind,price,full_value,reg_date) VALUES(?,?,?,?,?,?)";
        Object[] args = new Object[] {num,tmp.getActivityName(),kind,tmp.getCouponAmount(),tmp.getFullAmount(),new Date()};
        try {
            getJdbcTemplate().update(query, args);
            
            query = "INSERT INTO tmp_activity_details (id,uid,coupon_num,qty,rate,type) VALUES(?,?,?,?,?,?)";        
            args = new Object[] {System.currentTimeMillis(),tmpUId,num,tmp.getQty(),tmp.getRate(),tmp.getType()};
            
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    @Override
    public void updateTmpActivityDetail(long id, double rate) {
        try {
            getJdbcTemplate().update("UPDATE tmp_activity_details SET rate = ? WHERE id = ? ", new Object[] {id, rate});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateTmpActivityDetailRate(List<ActivityDetailVO> details) {
        Connection transaction = null;
        boolean autoCommit = true;
        try {
            transaction = getConnection();
            autoCommit = transaction.getAutoCommit();
            transaction.setAutoCommit(false);            
            PreparedStatement stmt = null;
            String query = "UPDATE tmp_activity_details SET rate = ? WHERE id = ?";
            
            for (ActivityDetailVO detail: details) {
                if (stmt != null)
                    stmt.close();
                stmt = transaction.prepareStatement(query);
                stmt.setDouble(1, detail.getRate());
                stmt.setLong(2, detail.getId());
                stmt.execute();
            }
            
            transaction.commit();
            
            if (stmt != null)
                stmt.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (SQLException ex1) {
                    ex.printStackTrace();
                }
            }
            
        } finally {
            if (transaction != null) {
                try {
                    transaction.setAutoCommit(autoCommit);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
        }
    
    }

    @Override
    public void deleteTmpActivityDetail(long id, long tmpUId) {
        String couponNum = "";
        String query = "SELECT coupon_num FROM tmp_activity_details WHERE id = ? AND uid = ?";
        Object[] args = new Object[] {id, tmpUId};
        
        try {
            couponNum = getJdbcTemplate().queryForObject(query, args, String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        if (!CommonUtil.isEmptyStr(couponNum)) {
            query = "DELETE FROM coupons WHERE num = ?";
            args = new Object[] {couponNum};
            
            try {
                getJdbcTemplate().update(query, args);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        try {            
            getJdbcTemplate().update("DELETE FROM tmp_activity_details WHERE id = ? AND uid = ?", new Object[] {id, tmpUId});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteAllTmpActivityDetail(long tmpUId) {
        try {
            getJdbcTemplate().update("DELETE FROM tmp_activity_details WHERE uid = ? ", new Object[] {tmpUId});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void makeTmpActivityDetail(long tmpUId, int activityId) {
        String query = "INSERT INTO tmp_activity_details " +
                       "SELECT id, " + tmpUId + ", coupon_num, qty, rate, type FROM activity_details WHERE activity_id = " + activityId;
        try {
            getJdbcTemplate().update(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<ActivityDetailVO> getTmpActivityDetailList(long tmpUId, String type) {
//        try {
//            return getJdbcTemplate().query("SELECT * FROM tmp_activity_details WHERE uid = ? AND (? = '-1' OR type= ?) ", new Object[] {tmpUId, type, type}, new TmpActivityDetailRowMapper());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        
        String query = "SELECT a.*, b.price coupon_amount, b.full_value full_amount FROM tmp_activity_details a, coupons b WHERE a.coupon_num = b.num AND a.uid = ? AND (? = '-1' OR a.type= ?)";
        Object[] args = new Object[] {tmpUId, type, type};
        
        try {
            return getJdbcTemplate().query(query, args, new TmpActivityDetailRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ActivityDetailVO> getActivityDetail(int activityId, String type) {
        
        try {
            return getJdbcTemplate().query("SELECT a.*, b.price coupon_amount, b.full_value full_amount FROM activity_details a, coupons b WHERE a.coupon_num = b.num AND a.activity_id = ? AND (? = '-1' OR a.type = ?)", new Object[] {activityId, type, type}, new ActivityDetailRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public void addNewActivityDetail(int activityId, List<ActivityDetailVO> activityDetails) {
        String query = "DELETE FROM activity_details WHERE activity_id=?";
//        Object[] args = new Object[] {activityId};
//        
//        try {
//            getJdbcTemplate().update(query, args);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        
        Connection transaction = null;
        boolean autoCommit = true;
        try {
            transaction = getConnection();
            autoCommit = transaction.getAutoCommit();
            transaction.setAutoCommit(false);            
            PreparedStatement stmt = null;
            query = "INSERT INTO activity_details (activity_id,coupon_num,qty,rate,type,reg_date) VALUES(?,?,?,?,?,?)";
            
            for (ActivityDetailVO detail: activityDetails) {
                if (stmt != null)
                    stmt.close();
                stmt = transaction.prepareStatement(query);
                stmt.setInt(1, activityId);
                stmt.setString(2, detail.getCouponNum());
                stmt.setInt(3, detail.getQty());
                stmt.setDouble(4, detail.getRate());
                stmt.setString(5, detail.getType());
                stmt.setDate(6, new java.sql.Date(new Date().getTime()));
                stmt.execute();
            }
            transaction.commit();
            
            if (stmt != null)
                stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (SQLException ex1) {
                    ex.printStackTrace();
                }
            }
            
        } finally {
            if (transaction != null) {
                try {
                    transaction.setAutoCommit(autoCommit);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
        }
    }

    @Override
    public void updateActivityDetail(int activityId, String type, List<ActivityDetailVO> activityDetails) {
        String query = "DELETE FROM activity_details WHERE activity_id=? AND type=?";
        Object[] args = new Object[] {activityId,type};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        Connection transaction = null;
        boolean autoCommit = true;
        try {
            transaction = getConnection();
            autoCommit = transaction.getAutoCommit();
            transaction.setAutoCommit(false);            
            PreparedStatement stmt = null;
            query = "INSERT INTO activity_details (activity_id,coupon_num,qty,rate,type,reg_date) VALUES(?,?,?,?,?,?)";
            
            for (ActivityDetailVO detail: activityDetails) {
                if (stmt != null)
                    stmt.close();
                stmt = transaction.prepareStatement(query);
                stmt.setInt(1, activityId);
                stmt.setString(2, detail.getCouponNum());
                stmt.setInt(3, detail.getQty());
                stmt.setDouble(4, detail.getRate());
                stmt.setString(5, detail.getType());
                stmt.setDate(6, new java.sql.Date(new Date().getTime()));
                stmt.execute();
            }
            transaction.commit();
            transaction.setAutoCommit(autoCommit);

            if (stmt != null)
                stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            
        } finally {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    transaction.setAutoCommit(autoCommit);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
        }
    }

    @Override
    public void deleteActivityDetail(int activityId, String type) {        
        try {
            getJdbcTemplate().update("DELETE FROM activity_details WHERE activity_id=? AND (? = '-1' OR type= ?)", new Object[] {activityId, type, type});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }    

    @Override
    public void addNotification(int userId, int deliverId, int activityNoticeId, int orderId, String title, String description, String num) {
        String query = "INSERT INTO notifications (user_id, deliver_id, activity_notice_id, order_id, title, description, reg_date, num) " + 
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        Object[] args = new Object[] {
            userId, deliverId, activityNoticeId, orderId, title, description, new Date(), num
        };
    
        try {
            getJdbcTemplate().update(query, args);
            
            if (userId > 0) {
                query = "UPDATE users SET badge = IFNULL(badge, 0) + 1 WHERE id = ?";
                args = new Object[] {userId};
                getJdbcTemplate().update(query, args);
            } else if (deliverId > 0) {
                query = "UPDATE delivers SET badge = IFNULL(badge, 0) + 1 WHERE id = ?";
                args = new Object[] {deliverId};
                getJdbcTemplate().update(query, args);
            } else if (activityNoticeId > 0) {
                query = "UPDATE users SET badge = IFNULL(badge, 0) + 1";
                getJdbcTemplate().update(query);
                query = "UPDATE delivers SET badge = IFNULL(badge, 0) + 1";
                getJdbcTemplate().update(query);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<CouponVO> getActivityCouponList(int activityNoticeId) {
        String query = "SELECT d.*, b.coupon_qty, c.rate, b.start_date, b.end_date " +
                        "FROM activity_notices a " +
                        "INNER JOIN activities b ON a.activity_id = b.id " +
                        "INNER JOIN activity_details c ON b.id = c.activity_id " +
                        "INNER JOIN coupons d ON c.coupon_num = d.num " +
                        "WHERE a.id = ?";
        
        try {
            return getJdbcTemplate().query(query, new Object[] {activityNoticeId}, new CouponRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public void addNewShareCoupon(ArrayList<ShareCouponVO> shares) {
        if (shares != null) {
            Connection transaction = null;
            boolean autoCommit = true;

            try {
                transaction = getConnection();
                autoCommit = transaction.getAutoCommit();
                transaction.setAutoCommit(false);            
                PreparedStatement stmt = null;
                String query = "INSERT INTO share_coupons (num,coupon_id) VALUES(?,?)";

                for (ShareCouponVO share: shares) {
                    if (stmt != null)
                        stmt.close();

                    stmt = transaction.prepareStatement(query);
                    stmt.setString(1, share.getNum());
                    stmt.setInt(2, share.getCouponId());

                    stmt.execute();
                }

                transaction.commit();

                if (stmt != null)
                    stmt.close();

            } catch (Exception ex) {
                ex.printStackTrace();

                if (transaction != null) {
                    try {
                        transaction.rollback();
                    } catch (SQLException ex1) {
                        ex1.printStackTrace();
                    }
                }
            } finally {
                if (transaction != null) {
                    try {
                        transaction.setAutoCommit(autoCommit);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public List<ShareCouponVO> getShareCouponList(String num) {
        String query = "SELECT * FROM share_coupons WHERE num = ?";
        
        try {
            return getJdbcTemplate().query(query, new Object[]{num}, new ShareCouponRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public ShareCouponVO getShareCoupon(String id) {
        String query = "SELECT * FROM share_coupons WHERE id = ?";
        
        try {
            return (ShareCouponVO) getJdbcTemplate().queryForObject(query, new Object[]{id}, new ShareCouponRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public void setShareCoupon(long id) {
        try {
            getJdbcTemplate().update("UPDATE share_coupons SET status = 1 WHERE id = ?", new Object[]{id});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    } 
    
    @Override
    public void deleteShareCoupon(String num) {
        try {
            getJdbcTemplate().update("DELETE FROM share_coupons WHERE num = ? ", new Object[] {num});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public boolean isLimitedShareCoupon(String num, int size) {
        try {
            return getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM share_coupons WHERE num = ? AND status = 1 ", new Object[] {num}, Integer.class) >= size;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return false;
    }

    @Override
    public DeliverVO getDeliverByName(String name) {
        Object[] args = new Object[] {name};
        String query = "SELECT delivers.* FROM delivers WHERE delivers.name = ?";
        try {
            return (DeliverVO) getJdbcTemplate().queryForObject(query, args, new DeliverRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public void updateShopOrd(int shopId, int ord) {
        try {
            getJdbcTemplate().update("UPDATE shops SET ord =? WHERE id = ? ", new Object[] {ord, shopId});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    
    /*
     * Row Mapper
     */
    private class ShareCouponRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            ShareCouponVO coupon = new ShareCouponVO();
            coupon.setId(rs.getLong("id"));
            coupon.setNum(rs.getString("num"));
            coupon.setCouponId(rs.getInt("coupon_id"));
            coupon.setStatus(rs.getBoolean("status"));
            return coupon;
        }
        
    }
    
    private class CouponRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            CouponVO coupon = new CouponVO();
            coupon.setId(rs.getInt("id"));
            coupon.setNum(rs.getString("num"));
            coupon.setName(rs.getString("name"));
            coupon.setPrice(rs.getDouble("price"));
            coupon.setFullValue(rs.getDouble("full_value"));
            coupon.setRegDate(rs.getTimestamp("reg_date"));            
            coupon.setType(rs.getInt("type"));
            coupon.setKind(rs.getString("kind"));
            coupon.setCouponQty(rs.getInt("coupon_qty"));
            coupon.setRate(rs.getDouble("rate"));
            coupon.setStartDate(rs.getTimestamp("start_date"));
            coupon.setEndDate(rs.getTimestamp("end_date"));
            return coupon;
        }
        
    }
    
    private class ManagerRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            ManagerVO manager = new ManagerVO();
            manager.setId(rs.getInt("id"));
            manager.setManagerId(rs.getString("manager_id"));
            manager.setName(rs.getString("name"));
            manager.setPassword(rs.getString("password"));
            manager.setGroupId(rs.getInt("group_id"));
            manager.setRegDate(new Date(rs.getTimestamp("reg_date").getTime()));
            manager.setGroupName(rs.getString("group_name"));
            return manager;
        }        
    }
    
    private class GroupRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            GroupVO group = new GroupVO();
            group.setId(rs.getInt("id"));
            group.setName(rs.getString("name"));
            group.setRegDate(new Date(rs.getTimestamp("reg_date").getTime()));
            return group;
        }        
    }
    
    private class RoleRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            RoleVO role = new RoleVO();
            role.setId(rs.getInt("id"));
            role.setGroupId(rs.getInt("group_id"));
            role.setPermissionId(rs.getInt("permission_id"));
            role.setStatus(rs.getBoolean("status"));
            role.setRegDate(new Date(rs.getTimestamp("reg_date").getTime()));
            role.setPermissionName(rs.getString("permission_name"));
            role.setParentId(rs.getInt("parent_id"));
            return role;
        }        
    }
    
    public class PermissionRowMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            PermissionVO permission = new PermissionVO();
            permission.setId(rs.getInt("id"));
            permission.setName(rs.getString("name"));
            permission.setParentId(rs.getInt("parent_id"));
            permission.setOrd(rs.getInt("ord"));
            permission.setRegDate(new Date(rs.getTimestamp("reg_date").getTime()));
            return permission;
        }
    }  
    
    public class ManagerPermissionRowMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            PermissionVO permission = new PermissionVO();
            permission.setId(rs.getInt("id"));
            permission.setName(rs.getString("name"));
            permission.setParentId(rs.getInt("parent_id"));
            permission.setOrd(rs.getInt("ord"));
            permission.setStatus(rs.getBoolean("status"));
            permission.setRegDate(new Date(rs.getTimestamp("reg_date").getTime()));
            return permission;
        }
    }
    
    private class StationRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            StationVO station = new StationVO();
            station.setId(rs.getInt("id"));
            station.setName(rs.getString("name"));
            station.setDistrictId(rs.getInt("district_id"));
            station.setDistrictName(rs.getString("district_name"));
            station.setDeliverId(rs.getInt("deliver_id"));
            station.setDeliverName(rs.getString("deliver_name"));
            station.setStatus(rs.getString("status"));
            station.setLatitude(rs.getDouble("latitude"));
            station.setLongitude(rs.getDouble("longitude"));
            station.setRegDate(new Date(rs.getTimestamp("reg_date").getTime()));
            station.setImagePath(rs.getString("image_path"));
            station.setDistrictParentName(rs.getString("district_parent_name"));
            station.setDistrictParentId(rs.getInt("parent_id"));
            return station;
        }        
    }
    
    private class DistrictRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            DistrictVO role = new DistrictVO();
            role.setId(rs.getInt("id"));
            role.setName(rs.getString("name"));
            role.setParentId(rs.getInt("parent_id"));
            role.setCode(rs.getString("code"));
            return role;
        }        
    }
    
    private class GoodsKindRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            GoodsKindVO goodsKind = new GoodsKindVO();
            goodsKind.setId(rs.getInt("id"));
            goodsKind.setShopKindId(rs.getInt("shop_kind_id"));
            goodsKind.setName(rs.getString("name"));
            goodsKind.setShopKindName(rs.getString("shop_kind_name"));
            goodsKind.setRegDate(rs.getTimestamp("reg_date"));
            return goodsKind;
        }        
    }
    
    private class ShopKindRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            ShopKindVO shopKind = new ShopKindVO();
            shopKind.setId(rs.getInt("id"));
            shopKind.setName(rs.getString("name"));
            shopKind.setRegDate(rs.getTimestamp("reg_date"));
            return shopKind;
        }        
    }
    
    private class ShopRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            ShopVO shop = new ShopVO();
            shop.setId(rs.getInt("id"));
            shop.setName(rs.getString("name"));
            shop.setDistrictId(rs.getInt("district_id"));
            shop.setDistrictName(rs.getString("district_name"));
            shop.setStationId(rs.getInt("station_id"));
            shop.setStationName(rs.getString("station_name"));
            shop.setAddress(rs.getString("address"));
            shop.setShopKindId(rs.getInt("shop_kind_id"));
            shop.setShopKindName(rs.getString("shop_kind_name"));
            shop.setType(rs.getString("type"));
            shop.setStartFee(rs.getDouble("start_fee"));
            shop.setShippingFee(rs.getDouble("shipping_fee"));
            shop.setRegDate(new Date(rs.getTimestamp("reg_date").getTime()));
            shop.setImagePath(rs.getString("image_path"));
            shop.setDescription(rs.getString("description"));
            shop.setDistrictParentName(rs.getString("district_parent_name"));
            shop.setDistrictParentId(rs.getInt("parent_id"));
            shop.setOrd(rs.getInt("ord"));
            return shop;
        }        
    }
    
    private class OrderRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            OrderVO order = new OrderVO();
            order.setId(rs.getInt("id"));
            order.setOrderNum(rs.getString("order_num"));
            order.setUserId(rs.getInt("user_id"));
            order.setShopId(rs.getInt("shop_id"));
            order.setOrderAmount(rs.getDouble("order_amount"));
            order.setOrderStatus(rs.getString("order_status"));
            order.setDescription(rs.getString("description"));            
            order.setRegDate(DateUtil.timestampToDate(rs.getTimestamp("reg_date")));
            order.setPayDate(DateUtil.timestampToDate(rs.getTimestamp("pay_date")));
            order.setShippingDate(DateUtil.timestampToDate(rs.getTimestamp("shipping_date")));
            order.setDeliveryDate(DateUtil.timestampToDate(rs.getTimestamp("delivery_date")));
            order.setReady(rs.getBoolean("state"));
            order.setUserName(rs.getString("user_name"));
            order.setUserTelNo(rs.getString("user_tel_no"));
            order.setShopName(rs.getString("shop_name"));
            order.setShopAddress(rs.getString("shop_address"));
            order.setStationName(rs.getString("station_name"));
            order.setDeliverName(rs.getString("deliver_name"));
            order.setDeliverTelNo(rs.getString("deliver_tel_no"));
            order.setShippingFee(rs.getDouble("shipping_fee"));
            order.setPredictDate(DateUtil.timestampToDate(rs.getTimestamp("predict_date")));
            order.setCouponAmount(rs.getDouble("coupon_amount"));
            return order;
        }
    }
    
    private class OrderDetailRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            OrderDetailVO orderDetail = new OrderDetailVO();
//            orderDetail.setId(rs.getString("id"));            
            orderDetail.setOrderId(rs.getInt("order_id"));
            orderDetail.setGoodsId(rs.getInt("goods_id"));
            orderDetail.setPrice(rs.getDouble("price"));
            orderDetail.setAmount(rs.getDouble("amount"));
            orderDetail.setQty(rs.getInt("qty"));            
//            orderDetail.setRegDate(DateUtil.timestampToDate(rs.getTimestamp("reg_date")));
            orderDetail.setGoodsNum(rs.getString("goods_num"));
            orderDetail.setGoodsName(rs.getString("goods_name"));
            orderDetail.setGoodsImagePath(rs.getString("image_path"));
            return orderDetail;
        }
    }
    
    private class OrderCouponRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            OrderCouponVO orderCoupon = new OrderCouponVO();
            orderCoupon.setId(rs.getInt("id"));            
            orderCoupon.setOrderId(rs.getInt("order_id"));
            orderCoupon.setAmount(rs.getDouble("amount"));   
            orderCoupon.setRegDate(DateUtil.timestampToDate(rs.getTimestamp("reg_date")));
            return orderCoupon;
        }
    }
    
    private class DeliverSumRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            OrderVO order = new OrderVO();
            order.setDeliverAccountId(rs.getString("deliver_account_id"));
            order.setDeliverName(rs.getString("deliver_name"));
            order.setOrderQty(rs.getInt("order_qty"));
            return order;
        }
    }
    
    private class ActivityRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            ActivityVO activity = new ActivityVO();
            activity.setId(rs.getInt("id"));            
            activity.setNum(rs.getString("num"));
            activity.setName(rs.getString("name"));
            activity.setStartDate(DateUtil.timestampToDate(rs.getTimestamp("start_date")));
            activity.setEndDate(DateUtil.timestampToDate(rs.getTimestamp("end_date")));
            activity.setActivityAmount(rs.getDouble("activity_amount"));
            activity.setCouponQty(rs.getInt("coupon_qty"));      
            activity.setImagePath(rs.getString("image_path"));
            activity.setStatus(rs.getString("status"));
            activity.setUnlimitedType(rs.getBoolean("unlimited_type"));
            activity.setFullType(rs.getBoolean("full_type"));
            activity.setRegDate(DateUtil.timestampToDate(rs.getTimestamp("reg_date")));
            activity.setShareTitle(rs.getString("share_title"));
            activity.setShareContent(rs.getString("share_content"));
            return activity;
        }
    }
    
    private class ActivityDetailRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            ActivityDetailVO activity = new ActivityDetailVO();
            activity.setId(rs.getInt("id"));            
            activity.setActivityId(rs.getInt("activity_id"));            
            activity.setCouponNum(rs.getString("coupon_num"));            
            activity.setType(rs.getString("type"));
            activity.setFullAmount(rs.getDouble("full_amount"));
            activity.setCouponAmount(rs.getDouble("coupon_amount"));
            activity.setQty(rs.getInt("qty"));      
            activity.setRate(rs.getDouble("rate"));
            activity.setRegDate(DateUtil.timestampToDate(rs.getTimestamp("reg_date")));
            return activity;
        }
    }
    
    private class TmpActivityDetailRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            ActivityDetailVO activity = new ActivityDetailVO();
            activity.setId(rs.getLong("id"));            
            activity.setTmpUId(rs.getLong("uid"));            
            activity.setType(rs.getString("type"));
            activity.setCouponNum(rs.getString("coupon_num"));
            activity.setFullAmount(rs.getDouble("full_amount"));
            activity.setCouponAmount(rs.getDouble("coupon_amount"));
            activity.setQty(rs.getInt("qty"));      
            activity.setRate(rs.getDouble("rate"));
            return activity;
        }
    }
    
    /*********************************
     * jiw start
     *********************************/
    @Override
    public List<FeedbackVO> getFeedbackList(String keyword, String status, int offset, int size, String sortColumn, String sort) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT feedbacks.*, users.name user_name, delivers.name deliver_name, IFNULL(users.user_id, delivers.deliver_id) display_id "
                + "FROM feedbacks "
                + "LEFT JOIN users ON feedbacks.user_id = users.id "
                + "LEFT JOIN delivers ON feedbacks.deliver_id = delivers.id ";
        String where = "", order = "", limit = "";
        
        if (!CommonUtil.isEmptyStr(keyword)) {
            where += " WHERE comment LIKE ? ";
            args.add("%" + keyword + "%");
        }
        
        if (!CommonUtil.isEmptyStr(status)) {
            if (where.isEmpty())
                where += " WHERE status = ? ";
            else
                where += " AND status = ? ";
            
            args.add(status);
        }
        
        if (!CommonUtil.isEmptyStr(sortColumn)) 
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        else 
            order = " ORDER BY reg_date DESC ";
                
        if (offset != -1 && size != -1) 
            limit = " LIMIT " + offset + ", " + size + " ";
        
        query += where + order + limit;
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new FeedbackRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public int countAllFeedback(String keyword, String status) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT COUNT(*) FROM feedbacks ";
        String where = "";
        
        if (!CommonUtil.isEmptyStr(keyword)) {
            where += " WHERE comment LIKE ? ";
            args.add("%" + keyword + "%");
        }
        
        if (!CommonUtil.isEmptyStr(status)) {
            if (where.isEmpty())
                where += " WHERE status = ? ";
            else
                where += " AND status = ? ";
            
            args.add(status);
        }
        
        query += where;
        
        try {
            return getJdbcTemplate().queryForObject(query, args.toArray(), Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }
    
    @Override
    public void setFeedbackStatus(int feedbackId) {
        String query = "UPDATE feedbacks SET status=? WHERE id=?";
        Object[] args = new Object[] {"已处理",feedbackId};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    @Override
    public DeliverVO getDeliver(String deliverId) {
        Object[] args = new Object[] {deliverId};
        String query = "SELECT delivers.* FROM delivers WHERE delivers.deliver_id = ?";
        try {
            return (DeliverVO) getJdbcTemplate().queryForObject(query, args, new DeliverRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
        
    }

    @Override
    public DeliverVO getDeliverById(int id) {
        Object[] args = new Object[] {id};
        String query = "SELECT delivers.* FROM delivers WHERE delivers.id = ?";
        try {
            return (DeliverVO) getJdbcTemplate().queryForObject(query, args, new DeliverRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
        
    }
    
    @Override
    public List<DeliverVO> getDeliverList(String keyword, int offset, int size, String sortColumn, String sort) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT * FROM delivers ";
        String where = "", order = "", limit = "";
        
        if (!CommonUtil.isEmptyStr(keyword)) {
            where += " WHERE (deliver_id LIKE ? OR name LIKE ? OR tel_no LIKE ?) ";
            args.add("%" + keyword + "%");
            args.add("%" + keyword + "%");
            args.add("%" + keyword + "%");
        }
        
        if (!CommonUtil.isEmptyStr(sortColumn)) 
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        else 
            order = " ORDER BY reg_date DESC ";
                
        if (offset != -1 && size != -1) 
            limit = " LIMIT " + offset + ", " + size + " ";
        
        query += where + order + limit;
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new DeliverRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public int countAllDeliver(String keyword) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT COUNT(*) FROM delivers ";
        String where = "";
        
        if (!CommonUtil.isEmptyStr(keyword)) {
            where += " WHERE comment LIKE ? ";
            args.add("%" + keyword + "%");
        }
        
        query += where;
        
        try {
            return getJdbcTemplate().queryForObject(query, args.toArray(), Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }
    
    @Override
    public void addNewDeliver(DeliverVO deliver) {
        String query = "INSERT INTO delivers (deliver_id,name,tel_no,password,reg_date) VALUES(?,?,?,?,?)";
        Object[] args = new Object[] {
            deliver.getDeliverId(),deliver.getName(),deliver.getTelNo(),deliver.getPassword(),new Date()
        };
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateDeliver(DeliverVO deliver) {
        String query = "UPDATE delivers SET name=?,tel_no=? WHERE id=?";
        Object[] args = new Object[] {
            deliver.getName(),deliver.getTelNo(),deliver.getId()
        };
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public boolean deleteDeliver(int deliverId) {
        String query = "DELETE FROM delivers WHERE id=?";
        Object[] args = new Object[] {deliverId};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    @Override
    public void setDeliverPassword(int deliverId, String password) {
        String query = "UPDATE delivers SET password=? WHERE id=?";
        Object[] args = new Object[] {password,deliverId};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public List<UserVO> getUserList(String keyword, String from, String to, int offset, int size, String sortColumn, String sort) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT users.user_id, users.name, users.avatar_path, " +
                       "SUM(IFNULL(orders.order_amount, 0) - IFNULL(order_coupons.amount, 0) + IFNULL(orders.shipping_fee, 0)) order_amount, COUNT(orders.id) order_qty " +
                       "FROM users " +
                       "LEFT JOIN orders ON users.id = orders.user_id AND orders.state = 1 " + 
                       "LEFT JOIN order_coupons ON orders.id = order_coupons.order_id ";
        String where = "", group = " GROUP BY users.user_id, users.name, users.avatar_path ", order = "", limit = "";
        
        if (!CommonUtil.isEmptyStr(keyword)) {
            where += " WHERE users.user_id LIKE ? ";
            args.add("%" + keyword + "%");
        }
        
        if (!CommonUtil.isEmptyStr(from)) {
            if (where.isEmpty())
                where = "WHERE pay_date >= ? AND orders.state = true ";
            else
                where += "AND pay_date >= ? AND orders.state = true ";
            args.add(from);
        }
        
        if (!CommonUtil.isEmptyStr(to)) {
            if (where.isEmpty())
                where = "WHERE pay_date < ADDDATE(?, 1) AND orders.state = true ";
            else
                where += "AND pay_date < ADDDATE(?, 1) AND orders.state = true ";
            args.add(to);
        }
        
        if (!CommonUtil.isEmptyStr(sortColumn)) 
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        else if (!CommonUtil.isEmptyStr(from) || !CommonUtil.isEmptyStr(to))
            order = " ORDER BY COUNT(orders.id) DESC, SUM(orders.order_amount) DESC ";
        else
            order = " ORDER BY reg_date DESC ";
                
        if (offset != -1 && size != -1) 
            limit = " LIMIT " + offset + ", " + size + " ";
        
        query += where + group + order + limit;
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new UserByOrderRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public int countAllUser(String keyword, String from, String to) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT COUNT(*) FROM (";
        String subquery = "SELECT users.id " +
                       " FROM users LEFT JOIN orders ON users.id = orders.user_id AND orders.state = 1";
        String where = "", group = " GROUP BY users.id";
        
        if (!CommonUtil.isEmptyStr(keyword)) {
            where += " WHERE users.user_id LIKE ? ";
            args.add("%" + keyword + "%");
        }
        
        if (!CommonUtil.isEmptyStr(from)) {
            if (where.isEmpty())
                where = "WHERE pay_date >= ? AND orders.state = true ";
            else
                where += "AND pay_date >= ? AND orders.state = true ";
            args.add(from);
        }
        
        if (!CommonUtil.isEmptyStr(to)) {
            if (where.isEmpty())
                where = "WHERE pay_date < ADDDATE(?, 1) AND orders.state = true ";
            else
                where += "AND pay_date < ADDDATE(?, 1) AND orders.state = true ";
            args.add(to);
        }
        
        query += subquery + where + group + ") a";
        
        try {
            return getJdbcTemplate().queryForObject(query, args.toArray(), Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public List<GoodsVO> getGoodsList(String keyword, int shopKindId, int shopId, int offset, int size, String sortColumn, String sort) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT goods.*, shops.name shop_name, goods_kinds.name goods_kind_name, shop_kinds.name shop_kind_name, shops.shop_kind_id " +
                       "FROM goods INNER JOIN shops ON goods.shop_id = shops.id " +
                       "INNER JOIN goods_kinds ON goods.goods_kind_id = goods_kinds.id " +
                       "INNER JOIN shop_kinds ON shops.shop_kind_id = shop_kinds.id ";
        String where = " WHERE goods.del = 0 ", order = "", limit = "";
        
        if (!CommonUtil.isEmptyStr(keyword)) {
            where = "WHERE (goods.name LIKE ? ) ";
            args.add("%" + keyword + "%");
        }
        
        if (shopKindId != -1) {
            if (where.isEmpty())
                where = "WHERE shops.shop_kind_id = ? ";
            else
                where += "AND shops.shop_kind_id = ? ";
            
            args.add(shopKindId);
        }
        
        if (shopId != -1) {
            if (where.isEmpty())
                where = "WHERE goods.shop_id = ? ";
            else
                where += "AND goods.shop_id = ? ";
            
            args.add(shopId);
        }
        
        if (!CommonUtil.isEmptyStr(sortColumn)) 
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        else 
            order = " ORDER BY goods.reg_date DESC ";
                
        if (offset != -1 && size != -1) 
            limit = " LIMIT " + offset + ", " + size + " ";
                
        query += where + order + limit;
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new GoodsRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public int countAllGoods(String keyword, int shopKindId, int shopId) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT COUNT(*) " +
                       "FROM goods INNER JOIN shops ON goods.shop_id = shops.id " +
                       "INNER JOIN goods_kinds ON goods.goods_kind_id = goods_kinds.id " +
                       "INNER JOIN shop_kinds ON shops.shop_kind_id = shop_kinds.id ";
        String where = " WHERE goods.del = 0 ";
        
        if (!CommonUtil.isEmptyStr(keyword)) {
            where = "WHERE (goods.name LIKE ?) ";
            args.add("%" + keyword + "%");
        }
        
        if (shopKindId != -1) {
            if (where.isEmpty())
                where = "WHERE shops.shop_kind_id = ? ";
            else
                where += "AND shops.shop_kind_id = ? ";
            
            args.add(shopKindId);
        }
        
        if (shopId != -1) {
            if (where.isEmpty())
                where = "WHERE goods.shop_id = ? ";
            else
                where += "AND goods.shop_id = ? ";
            
            args.add(shopId);
        }
                
        query += where;
        
        try {
            return getJdbcTemplate().queryForObject(query, args.toArray(), Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public void addNewGoods(GoodsVO newGoods) {
        String pattern = DateUtil.formatDate(new Date(), "YYMMdd");        
        String query = "SELECT MAX(num) FROM goods WHERE num LIKE ?";
        Object[] args = new Object[] {pattern + "%"};
        
        String maxNum = null, newNum;
        
        try {
            maxNum = getJdbcTemplate().queryForObject(query, args, String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        if (CommonUtil.isEmptyStr(maxNum)) {
            newNum = pattern + "000001";
        } else {
            BigDecimal bigdec = new BigDecimal(maxNum);
            BigDecimal addbig = bigdec.add(new BigDecimal(1));
            newNum = addbig.toString();
        }
        
        query = "INSERT INTO goods (num,shop_id,name,goods_kind_id,price,description,image_path,reg_date) VALUES(?,?,?,?,?,?,?,?)";
        
         args = new Object[] {
            newNum,
            newGoods.getShopId(),
            newGoods.getName(),
            newGoods.getGoodsKindId(),
            newGoods.getPrice(),
            newGoods.getDescription(),
            newGoods.getImagePath(),
            new Date()
        };
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateGoods(GoodsVO goods) {
        String query;
        Object[] args;
        if (!CommonUtil.isEmptyStr(goods.getImagePath())) {
            query = "UPDATE goods SET shop_id=?, name=?, goods_kind_id=?, price=?,description=?,image_path=? WHERE id = ?";
            args = new Object[] {
                goods.getShopId(),
                goods.getName(),
                goods.getGoodsKindId(),
                goods.getPrice(),
                goods.getDescription(),
                goods.getImagePath(),
                goods.getId()
            };
        } else {
            query = "UPDATE goods SET shop_id=?, name=?, goods_kind_id=?, price=?, description=? WHERE id = ?";
            args = new Object[] {
                goods.getShopId(),
                goods.getName(),
                goods.getGoodsKindId(),
                goods.getPrice(),
                goods.getDescription(),
                goods.getId()
            };
        }
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean deleteGoods(int id) {
        String query = "DELETE FROM goods WHERE id = ? ";
        Object[] args = new Object[] {id};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
            query = "UPDATE goods SET del = 1 WHERE id = ?";
            
            try {
                getJdbcTemplate().update(query, args);
            } catch (Exception ex1) {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public GoodsVO getGoods(int id) {
        String query = "SELECT goods.*, shops.name shop_name, goods_kinds.name goods_kind_name, shop_kinds.name shop_kind_name, shops.shop_kind_id " +
                       "FROM goods INNER JOIN shops ON goods.shop_id = shops.id " +
                       "INNER JOIN goods_kinds ON goods.goods_kind_id = goods_kinds.id " +
                       "INNER JOIN shop_kinds ON shops.shop_kind_id = shop_kinds.id " + 
                       "WHERE goods.id = ? ";
        
        try {
            return (GoodsVO) getJdbcTemplate().queryForObject(query, new Object[] {id}, new GoodsRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public boolean checkGoodsByName(String name) {
        String query = "SELECT COUNT(*) FROM goods WHERE goods.name = ? AND del = 0";
        
        try {
            return getJdbcTemplate().queryForObject(query, new Object[] {name}, Integer.class) > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return false;
    }

    @Override
    public void changeGoodsStatus(int id, String status) {
        String query = "UPDATE goods SET status = ? WHERE id = ?";
        Object[] args = new Object[] {status, id};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<ActivityNoticeVO> getActivityNoticeList(int offset, int size, String sortColumn, String sort) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT * FROM activity_notices ";
        String where = "", order = "", limit = "";
        
        if (!CommonUtil.isEmptyStr(sortColumn)) 
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        else 
            order = " ORDER BY reg_date DESC ";
                
        if (offset != -1 && size != -1) 
            limit = " LIMIT " + offset + ", " + size + " ";
        
        query += where + order + limit;
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new ActivityNoticeRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public ActivityNoticeVO getActivityNotice(int id) {
        Object[] args = new Object[] {id};
        String query = "SELECT activity_notices.* FROM activity_notices WHERE activity_notices.id = ?";
        try {
            return (ActivityNoticeVO) getJdbcTemplate().queryForObject(query, args, new ActivityNoticeRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
        
    }
    
    @Override
    public int countAllActivityNotice() {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT COUNT(*) FROM activity_notices ";
        
        try {
            return getJdbcTemplate().queryForObject(query, args.toArray(), Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public void addNewActivityNotice(ActivityNoticeVO newNotice) {
        String query = "INSERT INTO activity_notices (activity_id,name,description,image_path,status,reg_date,share_title,share_content) VALUES(?,?,?,?,?,?,?,?)";
        Object[] args = new Object[] {
            newNotice.getActivityId(),newNotice.getName(),newNotice.getDescription(),newNotice.getImagePath(),newNotice.getStatus(),new Date(),newNotice.getShareTitle(),newNotice.getShareContent()
        };
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
//        int newId = 0;
//        
//        try {
//            newId = getJdbcTemplate().queryForObject("SELECT MAX(id) FROM activity_notices", Integer.class);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        
//        if (newId > 0) {
//            try {
//                getJdbcTemplate().update("INSERT INTO notifications (activity_notice_id, reg_date) VALUES (?,?)", new Object[] {newId, new Date()});
//                getJdbcTemplate().update("UPDATE users SET badge = IFNULL(badge,0) + 1");
//                getJdbcTemplate().update("UPDATE delivers SET badge = IFNULL(badge,0) + 1");                
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
    }

    @Override
    public void updateActivityNotice(ActivityNoticeVO notice) {
        String query = "UPDATE activity_notices SET name=?, activity_id=?, description=?, image_path=?, share_title=?, share_content=? WHERE id=?";
        Object[] args = new Object[] {
            notice.getName(),notice.getActivityId(),notice.getDescription(), notice.getImagePath(), notice.getShareTitle(), notice.getShareContent(), notice.getId()
        };
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean deleteActivityNotice(int id) {
        String query = "DELETE FROM activity_notices WHERE id=?";
        Object[] args = new Object[] {id};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }

    @Override
    public void setActivityNoticeStatus(int id) {
        String query = "UPDATE activity_notices SET status=? WHERE id=?";
        Object[] args = new Object[] {true,id};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        ActivityNoticeVO notice = getActivityNotice(id);
        String title = notice.getName();
        String desc = notice.getDescription();
        
        List<UserVO> users = null;
        List<DeliverVO> delivers = null;
        
        try {
            users = getJdbcTemplate().query("SELECT * FROM users", new UserRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        try {
            delivers = getJdbcTemplate().query("SELECT * FROM delivers", new DeliverRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Connection transaction = null;
        boolean autoCommit = true;
        
        try {
            transaction = getConnection();
            autoCommit = transaction.getAutoCommit();
            transaction.setAutoCommit(false);            
            PreparedStatement stmt = null;
            
            if (users != null && !users.isEmpty()) {
                query = "INSERT INTO notifications (user_id, activity_notice_id, title, description, reg_date) " + 
                        "VALUES (?,?,?,?,?)";
                
                for (UserVO user: users) {
                    args = new Object[] {
                        user.getId(), notice.getId(), title, desc, new Date()
                    };
                    if (stmt != null)
                        stmt.close();
                    
                    stmt = transaction.prepareStatement(query);
                    stmt.setInt(1, user.getId());
                    stmt.setInt(2, notice.getId());
                    stmt.setString(3, title);
                    stmt.setString(4, desc);
                    stmt.setTimestamp(5, new Timestamp(new Date().getTime()));
                    
                    stmt.execute();
                }
                
                transaction.commit();
                
                query = "UPDATE users SET badge = IFNULL(badge, 0) + 1";
                getJdbcTemplate().update(query);
            }
            
            if (delivers != null && !delivers.isEmpty()) {
                query = "INSERT INTO notifications (deliver_id, activity_notice_id, title, description, reg_date) " + 
                        "VALUES (?,?,?,?,?)";
                
                for (DeliverVO deliver: delivers) {
                    args = new Object[] {
                        deliver.getId(), notice.getId(), title, desc, new Date()
                    };
                    if (stmt != null)
                        stmt.close();
                    
                    stmt = transaction.prepareStatement(query);
                    stmt.setInt(1, deliver.getId());
                    stmt.setInt(2, notice.getId());
                    stmt.setString(3, title);
                    stmt.setString(4, desc);
                    stmt.setTimestamp(5, new Timestamp(new Date().getTime()));
                    
                    stmt.execute();
                }
                
                transaction.commit();
                
                query = "UPDATE delivers SET badge = IFNULL(badge, 0) + 1";
                getJdbcTemplate().update(query);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (SQLException ex1) {
                    ex.printStackTrace();
                }
            }
            
        } finally {
            if (transaction != null) {
                try {
                    transaction.setAutoCommit(autoCommit);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
        }
    }
    
    @Override
    public List<ActivityVO> getActivityListByStatus() {
        
        String query = "SELECT * FROM activities WHERE status = '未开始' ORDER BY id DESC";
        
        try {
            return getJdbcTemplate().query(query, new Activity2RowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public List<UserCouponVO> getUserCouponList(String couponName, String userTelNo, String couponStatus, int offset, int size, String sortColumn, String sort) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT user_coupons.*," +
                        "users.user_id user_tel_no, " +
                        "coupons.name coupon_name, coupons.num coupon_num, coupons.price coupon_price " +
                        "FROM user_coupons " +
                        "INNER JOIN users ON user_coupons.user_id = users.id " +
                        "INNER JOIN coupons ON user_coupons.coupon_id = coupons.id " ;
        String where = "", order = "", limit = "";
        
        if (!CommonUtil.isEmptyStr(couponName)) {
            if (where.isEmpty())
                where = " WHERE coupons.name LIKE ? ";
            else
                where += " AND coupons.name LIKE ? ";
            args.add("%" + couponName + "%");
        }
                
        if (!CommonUtil.isEmptyStr(userTelNo)) {
            if (where.isEmpty())
                where = " WHERE users.user_id LIKE ? ";
            else
                where += " AND users.user_id LIKE ? ";
            args.add("%" + userTelNo + "%");
        }
        
        if (!CommonUtil.isEmptyStr(couponStatus)) {
            if (Constant.USER_COUPON_STATUS.NOT_USED.equals(couponStatus)) {
                if (where.isEmpty())
                    where = " WHERE user_coupons.status = 0 AND user_coupons.valid_to_date >= CURRENT_DATE()";
                else
                    where += " AND user_coupons.status = 0 AND user_coupons.valid_to_date >= CURRENT_DATE() ";
                
            } else if (Constant.USER_COUPON_STATUS.USED.equals(couponStatus)) {
                if (where.isEmpty())
                    where = " WHERE user_coupons.status = 1";
                else
                    where += " AND user_coupons.status = 1";
            } else {
                if (where.isEmpty())
                    where = " WHERE user_coupons.status = 0 AND user_coupons.valid_to_date < CURRENT_DATE()";
                else
                    where += " AND user_coupons.status = 0 AND user_coupons.valid_to_date < CURRENT_DATE() ";
            }
            
        }
        
        if (!CommonUtil.isEmptyStr(sortColumn)) 
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        else 
            order = " ORDER BY user_coupons.reg_date DESC ";
                
        if (offset != -1 && size != -1) 
            limit = " LIMIT " + offset + ", " + size + " ";
                
        query += where + order + limit;
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new UserCouponRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public int countAllUserCoupon(String couponName, String userTelNo, String couponStatus) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT COUNT(*)" +
                        "FROM user_coupons " +
                        "INNER JOIN users ON user_coupons.user_id = users.id " +
                        "INNER JOIN coupons ON user_coupons.coupon_id = coupons.id " ;
        String where = "";
        
        if (!CommonUtil.isEmptyStr(couponName)) {
            if (where.isEmpty())
                where = " WHERE coupons.name LIKE ? ";
            else
                where += " AND coupons.name LIKE ? ";
            args.add("%" + couponName + "%");
        }
                
        if (!CommonUtil.isEmptyStr(userTelNo)) {
            if (where.isEmpty())
                where = " WHERE users.user_id LIKE ? ";
            else
                where += " AND users.user_id LIKE ? ";
            args.add("%" + userTelNo + "%");
        }
        
        if (!CommonUtil.isEmptyStr(couponStatus)) {
            if (Constant.USER_COUPON_STATUS.NOT_USED.equals(couponStatus)) {
                if (where.isEmpty())
                    where = " WHERE user_coupons.status = 0 AND user_coupons.valid_to_date >= CURRENT_DATE()";
                else
                    where += " AND WHERE user_coupons.status = 0 AND user_coupons.valid_to_date >= CURRENT_DATE() ";
                
            } else if (Constant.USER_COUPON_STATUS.USED.equals(couponStatus)) {
                if (where.isEmpty())
                    where = " WHERE user_coupons.status = 1";
                else
                    where += " AND WHERE user_coupons.status = 1";
            } else {
                if (where.isEmpty())
                    where = " WHERE user_coupons.status = 0 AND user_coupons.valid_to_date < CURRENT_DATE()";
                else
                    where += " AND WHERE user_coupons.status = 0 AND user_coupons.valid_to_date < CURRENT_DATE() ";
            }
            
        }
        
        query += where;
        
        try {
            return getJdbcTemplate().queryForObject(query, args.toArray(), Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean checkFeedback() {
        try {
            return getJdbcTemplate().queryForObject("SELECT COUNT(id) FROM feedbacks WHERE status = '待处理'", Integer.class) > 0;
                    
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return false;
    }

    @Override
    public List<PermissionVO> getManagerPermission(int managerId, int parentId) {
        String query = "SELECT permissions.*, roles.status " +
                        "FROM permissions " +
                        "INNER JOIN roles ON permissions.id = roles.permission_id " +
                        "INNER JOIN managers ON managers.group_id =roles.group_id " +
                        "WHERE managers.id = ? AND " + ((parentId==-1)? "IFNULL(permissions.parent_id,-1) = ?": "permissions.parent_id=?" ) + 
                        " ORDER BY permissions.ord ";
        try {
            return getJdbcTemplate().query(query, new Object[] {managerId,parentId}, new ManagerPermissionRowMapper());
                    
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    
    
    /*
     * Row mapper
     */
    private class FeedbackRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            FeedbackVO feedback = new FeedbackVO();
            feedback.setId(rs.getInt("id"));
            feedback.setUserId(rs.getInt("user_id"));
            feedback.setDeliverId(rs.getInt("deliver_id"));
            feedback.setDeliverName(rs.getString("deliver_name"));
            feedback.setUserName(rs.getString("user_name"));
            feedback.setDisplayId(rs.getString("display_id"));
            feedback.setComment(rs.getString("comment"));
            feedback.setStatus(rs.getString("status"));
            feedback.setRegDate(rs.getTimestamp("reg_date"));
            return feedback;
        }
        
    }
    
    private class DeliverRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            DeliverVO deliver = new DeliverVO();
            deliver.setId(rs.getInt("id"));
            deliver.setDeliverId(rs.getString("deliver_id"));
            deliver.setName(rs.getString("name"));
            deliver.setTelNo(rs.getString("tel_no"));
            deliver.setPassword(rs.getString("password"));
            deliver.setRegDate(rs.getTimestamp("reg_date"));
            deliver.setBadge(rs.getInt("badge"));
            return deliver;
        }
        
    }

    private class UserRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            UserVO user = new UserVO();
            user.setId(rs.getInt("id"));
            user.setUserId(rs.getString("user_id"));
            user.setName(rs.getString("name"));
            user.setWxOpenId(rs.getString("wx_openid"));
            user.setAvatarPath(rs.getString("avatar_path"));
            user.setRegDate(rs.getTimestamp("reg_date"));
            user.setStatus(rs.getBoolean("status"));
            user.setIsNew(rs.getBoolean("is_new"));
            user.setBadge(rs.getInt("badge"));
            return user;
        }
        
    }
    
    private class UserByOrderRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            UserVO user = new UserVO();
            user.setUserId(rs.getString("user_id"));
            user.setName(rs.getString("name"));
            user.setAvatarPath(rs.getString("avatar_path"));
            user.setOrderAmount(rs.getDouble("order_amount"));
            user.setOrderCount(rs.getInt("order_qty"));
            return user;
        }
        
    }
    
    private class SingleGoodsRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            GoodsVO goods = new GoodsVO();
            goods.setId(rs.getInt("id"));
            goods.setNum(rs.getString("num"));
            goods.setShopId(rs.getInt("shop_id"));
            goods.setName(rs.getString("name"));
            goods.setGoodsKindId(rs.getInt("goods_kind_id"));
            goods.setPrice(rs.getDouble("price"));
            goods.setDescription(rs.getString("description"));
            goods.setImagePath(rs.getString("image_path"));
            goods.setStatus(rs.getString("status"));
            goods.setRegDate(rs.getTimestamp("reg_date"));
            
            return goods;
        }
        
    }
    
    private class GoodsRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            GoodsVO goods = new GoodsVO();
            goods.setId(rs.getInt("id"));
            goods.setNum(rs.getString("num"));
            goods.setShopId(rs.getInt("shop_id"));
            goods.setShopKindId(rs.getInt("shop_kind_id"));
            goods.setName(rs.getString("name"));
            goods.setGoodsKindId(rs.getInt("goods_kind_id"));
            goods.setPrice(rs.getDouble("price"));
            goods.setDescription(rs.getString("description"));
            goods.setImagePath(rs.getString("image_path"));
            goods.setStatus(rs.getString("status"));
            goods.setRegDate(rs.getTimestamp("reg_date"));
            goods.setShopName(rs.getString("shop_name"));
            goods.setGoodsKindName(rs.getString("goods_kind_name"));
            goods.setShopKindName(rs.getString("shop_kind_name"));
            
            return goods;
        }
        
    }
    
    private class ActivityNoticeRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            ActivityNoticeVO notice = new ActivityNoticeVO();
            notice.setId(rs.getInt("id"));
            notice.setActivityId(rs.getInt("activity_id"));
            notice.setName(rs.getString("name"));
            notice.setDescription(rs.getString("description"));
            notice.setImagePath(rs.getString("image_path"));
            notice.setStatus(rs.getBoolean("status"));
            notice.setRegDate(rs.getTimestamp("reg_date"));
            notice.setShareTitle(rs.getString("share_title"));
            notice.setShareContent(rs.getString("share_content"));
            return notice;
        }
        
    }
    
    
    private class Activity2RowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            ActivityVO activity = new ActivityVO();
            activity.setId(rs.getInt("id"));
            activity.setName(rs.getString("name"));
            activity.setStartDate(rs.getTimestamp("start_date"));
            activity.setEndDate(rs.getTimestamp("end_date"));
            activity.setActivityAmount(rs.getDouble("activity_amount"));
            activity.setCouponQty(rs.getInt("coupon_qty"));
            activity.setImagePath(rs.getString("image_path"));
            activity.setRegDate(rs.getTimestamp("reg_date"));
            return activity;
        }
        
    }
    
    private class UserCouponRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            UserCouponVO user_coupon = new UserCouponVO();
            user_coupon.setId(rs.getInt("id"));
            user_coupon.setCouponId(rs.getInt("coupon_id"));
            user_coupon.setUserId(rs.getInt("user_id"));
            user_coupon.setQty(rs.getInt("qty"));
            user_coupon.setValidFromDate(rs.getTimestamp("valid_from_date"));
            user_coupon.setValidToDate(rs.getTimestamp("valid_to_date"));
            user_coupon.setStatus(rs.getBoolean("status"));
            user_coupon.setUsedDate(rs.getTimestamp("used_date"));
            user_coupon.setRegDate(rs.getTimestamp("reg_date"));
            user_coupon.setUserTelNo(rs.getString("user_tel_no"));
            user_coupon.setCouponName(rs.getString("coupon_name"));
            user_coupon.setCouponNum(rs.getString("coupon_num"));
            user_coupon.setCouponPrice(rs.getDouble("coupon_price"));
            return user_coupon;
        }
        
    }
}
