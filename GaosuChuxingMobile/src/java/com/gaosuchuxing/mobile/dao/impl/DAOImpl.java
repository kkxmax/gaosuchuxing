/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.dao.impl;

import com.gaosuchuxing.mobile.domain.ActivityVO;
import com.gaosuchuxing.mobile.common.Constant;
import com.gaosuchuxing.mobile.common.SMSRequest;
import com.gaosuchuxing.mobile.dao.WebDAO;
import com.gaosuchuxing.mobile.domain.ActivityNoticeVO;
import com.gaosuchuxing.mobile.domain.CouponVO;
import com.gaosuchuxing.mobile.domain.DeliverVO;
import com.gaosuchuxing.mobile.domain.DistrictVO;
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
import com.gaosuchuxing.mobile.util.CommonUtil;
import com.gaosuchuxing.mobile.util.DateUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;


public class DAOImpl extends JdbcDaoSupport implements WebDAO {
    
    @Override
    public List<StationVO> getStationListByNear(ArrayList<String> adcodes) {
        String query = "SELECT stations.* " +
                       "FROM stations INNER JOIN districts ON stations.district_id = districts.id ";
        String where = "", order = "", limit = "";
        
        if (adcodes != null && !adcodes.isEmpty()) {            
            Object[] arr = adcodes.toArray();
            String params = StringUtils.join(arr, "','");
            params = "'" + params + "'";
            
            where = "WHERE SUBSTR(districts.code,1,3) IN (" + params + ") ";
        }
                
        order = " ORDER BY stations.longitude, stations.latitude ";
                
        query += where + order;
        
        try {
            return getJdbcTemplate().query(query, new SingleStationRowMapper());
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
                where = "WHERE (districts.code = ? OR district_parents.code = ?) ";
            else
                where += "AND (districts.code = ? OR district_parents.code = ?) ";
            
            args.add(adcode);
            args.add(adcode);
        }
        
        if (!CommonUtil.isEmptyStr(status)) {
            if (where.isEmpty())
                where = "WHERE stations.status = ? ";
            else
                where += "AND stations.status = ? ";
            
            args.add(status);
        }
        
//        if (!CommonUtil.isEmptyStr(sortColumn)) 
//            order = " ORDER BY " + sortColumn + " " + sort + " ";
//        else 
//            order = " ORDER BY stations.reg_date DESC ";

        order = " ORDER BY stations.longitude, stations.latitude ";
                
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
                where = "WHERE (districts.code = ? OR district_parents.code = ?) ";
            else
                where += "AND (districts.code = ? OR district_parents.code = ?) ";
            
            args.add(adcode);
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
    public List<StationVO> getStationByDeliver(int deliverId) {
        try {
            return getJdbcTemplate().query("SELECT * FROM stations WHERE deliver_id = ?", new Object[] {deliverId}, new SingleStationRowMapper());
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
    public ShopKindVO getShopKindByName(String shopKindName) {        
        try {
            return (ShopKindVO) getJdbcTemplate().queryForObject("SELECT * FROM shop_kinds WHERE name = ?", new Object[] {shopKindName}, new ShopKindRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<ShopVO> getShopList(String type, String keyword, int stationId, int shopKindId, int districtId, int offset, int size, String sortColumn, String sort) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT shops.*, stations.name station_name, shop_kinds.name shop_kind_name, districts.name district_name, districts.parent_id, district_parents.name district_parent_name, stations.deliver_id " +
                       "FROM shops INNER JOIN stations ON shops.station_id = stations.id " +
                       "INNER JOIN shop_kinds ON shops.shop_kind_id = shop_kinds.id " +
                       "INNER JOIN districts ON shops.district_id = districts.id " +
                       "INNER JOIN (SELECT * FROM districts WHERE parent_id = 0) district_parents ON districts.parent_id = district_parents.id ";
        String where = "", order = "", limit = "";
        
        if (!CommonUtil.isEmptyStr(type)) {            
            where += "WHERE shops.type = ? ";
            args.add(type);
        }
                
        if (!CommonUtil.isEmptyStr(keyword)) {
            if (where.isEmpty())
                where += "WHERE shops.name LIKE ? ";
            else
                where += "AND shops.name LIKE ? ";
            args.add("%" + keyword + "%");
        }
        
        if (stationId != -1) {
            if (where.isEmpty())
                where += "WHERE shops.station_id = ? ";            
            else
                where += "AND shops.station_id = ? ";            
            args.add(stationId);
        }
        
        if (shopKindId != -1) {
            if (where.isEmpty())
                where += "WHERE shops.shop_kind_id = ? ";
            else
                where += "AND shops.shop_kind_id = ? ";
            args.add(shopKindId);
        }
        
        if (districtId != -1) {
            if (where.isEmpty())
                where += "WHERE shops.district_id = ? ";
            else
                where += "AND shops.district_id = ? ";
            args.add(districtId);
        }
            
        
        if (!CommonUtil.isEmptyStr(sortColumn)) 
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        else 
            order = " ORDER BY shops.reg_date DESC ";
                
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
        
        if (!CommonUtil.isEmptyStr(type)) {            
            where += "WHERE shops.type = ? ";
            args.add(type);
        }
                
        if (!CommonUtil.isEmptyStr(keyword)) {
            if (where.isEmpty())
                where += "WHERE shops.name LIKE ? ";
            else
                where += "AND shops.name LIKE ? ";
            args.add("%" + keyword + "%");
        }
        
        if (stationId != -1) {
            if (where.isEmpty())
                where += "WHERE shops.station_id = ? ";            
            else
                where += "AND shops.station_id = ? ";            
            args.add(stationId);
        }
        
        if (shopKindId != -1) {
            if (where.isEmpty())
                where += "WHERE shops.shop_kind_id = ? ";
            else
                where += "AND shops.shop_kind_id = ? ";
            args.add(shopKindId);
        }
        
        if (districtId != -1) {
            if (where.isEmpty())
                where += "WHERE shops.district_id = ? ";
            else
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
        String query = "SELECT shops.*, stations.name station_name, shop_kinds.name shop_kind_name, " +
                       "districts.name district_name, districts.parent_id, district_parents.name district_parent_name, stations.deliver_id " +
                       "FROM shops INNER JOIN stations ON shops.station_id = stations.id " +
                       "INNER JOIN shop_kinds ON shops.shop_kind_id = shop_kinds.id " +
                       "INNER JOIN districts ON shops.district_id = districts.id " +
                       "INNER JOIN (SELECT * FROM districts WHERE parent_id = 0) district_parents ON districts.parent_id = district_parents.id " +
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
        String query = "SELECT shops.*, stations.name station_name, shop_kinds.name shop_kind_name, districts.name district_name, districts.parent_id, district_parents.name district_parent_name, stations.deliver_id " +
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
    public List<OrderVO> getOrderList(int state, int deliverId, String orderNum, String userName, String deliverName, String orderStatus, String from, String to, int offset, int size, String sortColumn, String sort) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT orders.*, users.name user_name, users.user_id user_tel_no, " +
                        "shops.name shop_name, shops.address shop_address, stations.name station_name, " +
                        "delivers.name deliver_name, delivers.tel_no deliver_tel_no " +
                        "FROM orders " +
                        "INNER JOIN users ON orders.user_id = users.id " +
                        "INNER JOIN shops ON orders.shop_id = shops.id " +
                        "INNER JOIN stations ON shops.station_id = stations.id " +
                        "INNER JOIN delivers ON orders.deliver_id = delivers.id  ";
        String where = "", order = "", limit = "";
        
        if (state != -1) {
            if (state > 0) {
                where = " WHERE orders.state = TRUE ";
            } else {
                where = " WHERE orders.state = FALSE ";
            }
        }
        
        if (deliverId != -1) {
            if (where.isEmpty())
                where = " WHERE orders.deliver_id = ? ";
            else
                where += " AND orders.deliver_id = ? ";
            args.add(deliverId);
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
    public List<OrderVO> getOrderListByDeliver(int deliverId, String orderStatus) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT orders.id, shops.name shop_name, shops.image_path shop_image_path, orders.order_status, SUM(order_details.qty) order_qty, SUM(order_details.qty * order_details.price) order_amount, MAX(orders.pay_date) pay_date " +
                        "FROM orders " +
                        "INNER JOIN order_details ON orders.id = order_details.order_id " +
                        "INNER JOIN shops ON orders.shop_id = shops.id ";
        String where = " WHERE orders.state = TRUE ", order = "", group = " GROUP BY orders.id, shops.name, shops.image_path, orders.order_status ";
        
        if (deliverId != -1) {
            if (where.isEmpty())
                where = " WHERE orders.deliver_id = ? ";
            else
                where += " AND orders.deliver_id = ? ";
            args.add(deliverId);
        }
                
        if (!CommonUtil.isEmptyStr(orderStatus)) {
            if (where.isEmpty())
                where = " WHERE orders.order_status = ? ";
            else
                where += " AND orders.order_status = ? ";
            args.add(orderStatus);
        }
                
        order = " ORDER BY orders.pay_date DESC ";
                
        query += where + group + order;
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new OrderByDeliverRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public List<OrderVO> getOrderListByUser(boolean state, int userId, String orderStatus, int stationId) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT orders.id, shops.name shop_name, shops.image_path shop_image_path, orders.order_status, shops.id shop_id, " +
                        "SUM(order_details.qty) order_qty, " +
                        "SUM(order_details.qty * " + (state? "order_details.price" : "goods.price") + ") order_amount, " +
                        "MAX(orders.pay_date) pay_date, MAX(orders.reg_date) reg_date " +
                        "FROM orders " +
                        "INNER JOIN order_details ON orders.id = order_details.order_id " +
                        (state? "": "INNER JOIN goods ON order_details.goods_id = goods.id ") +
                        "INNER JOIN shops ON orders.shop_id = shops.id ";
        String where = " WHERE orders.state = ? ", order = "", group = " GROUP BY orders.id, shops.name, shops.image_path, orders.order_status, shops.id ";
        
//        if (!state) {
        group += " HAVING SUM(order_details.qty) > 0 ";
//        }
        
        args.add(state);
        
        if (userId != -1) {
            if (where.isEmpty())
                where = " WHERE orders.user_id = ? ";
            else
                where += " AND orders.user_id = ? ";
            args.add(userId);
        }
                
        if (!CommonUtil.isEmptyStr(orderStatus)) {
            if (where.isEmpty())
                where = " WHERE orders.order_status = ? ";
            else
                where += " AND orders.order_status = ? ";
            args.add(orderStatus);
        }
        
        if (stationId != -1) {
            if (where.isEmpty())
                where = " WHERE shops.station_id = ? ";
            else
                where += " AND shops.station_id = ? ";
            args.add(stationId);
        }
                
        order = " ORDER BY IFNULL(orders.pay_date, orders.reg_date) DESC ";
                
        query += where + group + order;
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new OrderByUserRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public int countAllOrder(int state, int deliverId, String orderNum, String userName, String deliverName, String orderStatus, String from, String to) {
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
        
        if (deliverId != -1) {
            if (where.isEmpty())
                where = " WHERE orders.deliver_id = ? ";
            else
                where += " AND orders.deliver_id = ? ";
            args.add(deliverId);
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
    public OrderVO getOrder(int orderId) {
        String query = "SELECT orders.*, users.name user_name, users.user_id user_tel_no, " +
                        "shops.name shop_name, shops.address shop_address, stations.name station_name, " +
                        "delivers.name deliver_name, delivers.tel_no deliver_tel_no " +
                        "FROM orders " +
                        "INNER JOIN users ON orders.user_id = users.id " +
                        "INNER JOIN shops ON orders.shop_id = shops.id " +
                        "INNER JOIN stations ON shops.station_id = stations.id " +
                        "INNER JOIN delivers ON orders.deliver_id = delivers.id  " +
                        "WHERE orders.id = ? ";
        
        try {
            return (OrderVO) getJdbcTemplate().queryForObject(query, new Object[] {orderId}, new OrderRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public void setOrderStatus(int orderId, String status) {
        String sub = "";
        Object[] args;
        
        if (status.equals(Constant.ORDER_STATUS.SHIPPING)) {
            sub = ", shipping_date = ? ";
            args = new Object[] {status, new Date(), orderId};
        } else {
            sub = ", delivery_date = ? ";
            args = new Object[] {status, new Date(), orderId};
        }
            
        try {
            getJdbcTemplate().update("UPDATE orders SET order_status = ?" + sub + " WHERE id = ?", args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        OrderVO order = getOrder(orderId);
        if (order != null) {
            ShopVO shop = getShop(order.getShopId());
            if (shop != null) {
                String station = shop.getStationName();
                String title = "您的订单已经生成";
                String desc;
                if (order.getOrderStatus().equals(Constant.ORDER_STATUS.SHIPPING)) {
                    title = "您的订单正在配送";
                    desc = "您于" + DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm") + " 在" + station + "服务区的订单已取餐成功，正在为您配送的路上，请耐心等待";
                } else {
                    title = "您的订单已完成";
                    desc = "您于" + DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm") + " 在" + station + "服务区的订单已完成配送，祝您用餐愉快";
                }    
                addNotification(order.getUserId(), 0, 0, orderId, title, desc);
            }
            
            if (order.getOrderStatus().equals(Constant.ORDER_STATUS.SHIPPING)) {
                UserVO user = getUser(order.getUserId());
                if (user != null && !CommonUtil.isEmptyStr(user.getUserId())) 
                    SMSRequest.sendSMS(user.getUserId(), SMSRequest.SHIPPING_TEMPLATE_ID, null, null);
            }
        }
    }
    
    @Override
    public List<OrderDetailVO> getOrderDetailList(int orderId) {
        String query = "SELECT order_details.*, goods.num goods_num, goods.name goods_name, goods.image_path " +
                        "FROM order_details " +
                        "INNER JOIN orders ON order_details.order_id = orders.id " +
                        "INNER JOIN goods ON order_details.goods_id = goods.id " +
                        "WHERE order_details.order_id = ? ";
        
        try {
            return getJdbcTemplate().query(query, new Object[] {orderId}, new OrderDetailRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public List<OrderDetailVO> getOrderDetailListByGoods(int orderId, boolean isPay) {
        String query = "SELECT order_details.order_id, order_details.goods_id, " + 
                        ((isPay)? "goods.price":  "order_details.price") + ", goods.name goods_name, goods.image_path, SUM(order_details.qty) qty " +
                        "FROM order_details " +
                        "INNER JOIN goods ON order_details.goods_id = goods.id " +
                        "WHERE order_details.order_id = ? " + 
                        "GROUP BY order_details.order_id, order_details.goods_id, " + ((isPay)? "goods.price":  "order_details.price") + ", goods.name, goods.image_path " +
                        "HAVING SUM(order_details.qty) > 0 ";
                        
        
        try {
            return getJdbcTemplate().query(query, new Object[] {orderId}, new OrderDetailByGoodsRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
        
    @Override
    public List<OrderDetailVO> getOrderDetailListByPay(int orderId) {
        String query = "SELECT SUM(order_details.qty) qty, goods.price " +
                        "FROM order_details " +
                        "INNER JOIN goods ON order_details.goods_id = goods.id " +
                        "WHERE order_details.order_id = ? " +
                        "GROUP BY goods.price ";
        
        try {
            return getJdbcTemplate().query(query, new Object[] {orderId}, new OrderDetailByPayRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public List<OrderCouponVO> getOrderCouponList(int orderId, int status) {
        Object[] args = new Object[] {orderId};
        String subConditions = "";
        
        String query = "SELECT order_coupons.* " +
                        "FROM order_coupons " +
                        "INNER JOIN orders ON order_coupons.order_id = orders.id " +
                        "WHERE order_coupons.order_id = ?";
        if (status != -1) {
            if (status > 0)
                subConditions = " AND status = 1 ";
            else
                subConditions = " AND status = 0 ";            
        }
        
        query += subConditions;
        
        try {
            return getJdbcTemplate().query(query, args, new OrderCouponRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public List<GoodsVO> getGoodsListByShop(String keyword, int shopKindId, int shopId, int goodsKindId, int userId, int orderId, int offset, int size, String sortColumn, String sort) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT goods.id, goods.name, goods.description, goods.price, goods.image_path, SUM(detail.qty) qty " +
                       "FROM goods INNER JOIN shops ON goods.shop_id = shops.id " +
                       "INNER JOIN goods_kinds ON goods.goods_kind_id = goods_kinds.id " +
                       "INNER JOIN shop_kinds ON shops.shop_kind_id = shop_kinds.id " + 
                       "LEFT JOIN ( " +
                       "SELECT order_details.order_id, order_details.goods_id, order_details.qty FROM orders, order_details " +
                       "WHERE orders.id = order_details.order_id AND orders.state = false AND orders.user_id = ? AND orders.shop_id = ? " +
                       ") detail ON goods.id = detail.goods_id " ;
        String where = "WHERE goods.status = ? ", group = "GROUP BY goods.id, goods.name, goods.description, goods.price, goods.image_path ", order = "", limit = "";
        
        args.add(userId);
        args.add(shopId);
        
        args.add(Constant.GOODS_STATUS.SET_ON);
        
        if (!CommonUtil.isEmptyStr(keyword)) {
            if (where.isEmpty())
                where = "WHERE (goods.name LIKE ? ) ";
            else
                where += "AND (goods.name LIKE ? ) ";
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
        
        if (goodsKindId != -1) {
            if (where.isEmpty())
                where = "WHERE goods.goods_kind_id = ? ";
            else
                where += "AND goods.goods_kind_id = ? ";
            
            args.add(goodsKindId);
        }
        
        if (orderId != -1) {
            if (where.isEmpty())
                where = "WHERE detail.order_id = ? ";
            else
                where += "AND detail.order_id = ? ";
            
            args.add(orderId);
        }
        
        if (!CommonUtil.isEmptyStr(sortColumn)) 
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        else 
            order = " ORDER BY goods.reg_date DESC ";
                
        if (offset != -1 && size != -1) 
            limit = " LIMIT " + offset + ", " + size + " ";
                
        query += where + group + order + limit;
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new GoodsByOrderRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public int countAllGoodsByShop(String keyword, int shopKindId, int shopId, int goodsKindId) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT COUNT(*) " +
                       "FROM goods INNER JOIN shops ON goods.shop_id = shops.id " +
                       "INNER JOIN goods_kinds ON goods.goods_kind_id = goods_kinds.id " +
                       "INNER JOIN shop_kinds ON shops.shop_kind_id = shop_kinds.id ";
        String where = "WHERE goods.status = ? ";
        
        args.add(Constant.GOODS_STATUS.SET_ON);
        
        if (!CommonUtil.isEmptyStr(keyword)) {
            if (where.isEmpty())
                where = "WHERE (goods.name LIKE ? ) ";
            else
                where += "AND (goods.name LIKE ? ) ";
            args.add("%" + keyword + "%");
        }
        
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
        
        if (goodsKindId != -1) {
            if (where.isEmpty())
                where = "WHERE goods.goods_kind_id = ? ";
            else
                where += "AND goods.goods_kind_id = ? ";
            
            args.add(goodsKindId);
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
    public List<GoodsVO> getGoodsListByOrder(String keyword, int goodsKindId, int userId, int orderId, int offset, int size, String sortColumn, String sort) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT goods.id, goods.name, goods.description, goods.price, goods.image_path, SUM(order_details.qty) qty " +
                       "FROM orders " +
                       "INNER JOIN order_details ON orders.id = order_details.order_id " +
                       "INNER JOIN goods ON goods.id = order_details.goods_id " ;
        String where = "", group = "GROUP BY goods.id, goods.name, goods.description, goods.price, goods.image_path HAVING SUM(order_details.qty) > 0 ", order = "", limit = "";
                
        if (!CommonUtil.isEmptyStr(keyword)) {
            if (where.isEmpty())
                where = "WHERE (goods.name LIKE ? ) ";
            else
                where += "AND (goods.name LIKE ? ) ";
            args.add("%" + keyword + "%");
        }
        
        if (goodsKindId != -1) {
            if (where.isEmpty())
                where = "WHERE goods.goods_kind_id = ? ";
            else
                where += "AND goods.goods_kind_id = ? ";
            
            args.add(goodsKindId);
        }
        
        if (orderId != -1) {
            if (where.isEmpty())
                where = "WHERE orders.id = ? ";
            else
                where += "AND orders.id = ? ";
            
            args.add(orderId);
        }
        
        if (!CommonUtil.isEmptyStr(sortColumn)) 
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        else 
            order = " ORDER BY orders.reg_date DESC ";
                
        if (offset != -1 && size != -1) 
            limit = " LIMIT " + offset + ", " + size + " ";
                
        query += where + group + order + limit;
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new GoodsByOrderRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
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
    public GoodsVO getGoodsByName(String name) {
        String query = "SELECT goods.*, shops.name shop_name, goods_kinds.name goods_kind_name, shop_kinds.name shop_kind_name, shops.shop_kind_id " +
                       "FROM goods INNER JOIN shops ON goods.shop_id = shops.id " +
                       "INNER JOIN goods_kinds ON goods.goods_kind_id = goods_kinds.id " +
                       "INNER JOIN shop_kinds ON shops.shop_kind_id = shop_kinds.id " + 
                       "WHERE goods.name = ? ";
        
        try {
            return (GoodsVO) getJdbcTemplate().queryForObject(query, new Object[] {name}, new GoodsRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public List<GoodsKindVO> getGoodsKindListByShopId(int shopId, int userId) {
        String query = "SELECT goods_kinds.id, goods_kinds.shop_kind_id, goods_kinds.name, SUM(order_details.qty) qty " +
                        "FROM shops " +
                        "INNER JOIN goods ON shops.id = goods.shop_id " +
                        "INNER JOIN goods_kinds ON goods.goods_kind_id = goods_kinds.id " +
                        "LEFT JOIN orders ON orders.shop_id = orders.shop_id AND orders.user_id = ? AND state = FALSE " +
                        "LEFT JOIN order_details ON goods.id = order_details.goods_id AND orders.id = order_details.order_id " +
                        "WHERE shops.id = ? " + 
                        "GROUP BY goods_kinds.id, goods_kinds.shop_kind_id, goods_kinds.name ";
        try {
            return getJdbcTemplate().query(query, new Object[] {userId, shopId}, new GoodsKindByShopRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
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
    public void updateDeliverAccessToken(int deliverId, String accessToken) {
        String query = "UPDATE delivers SET access_token=? WHERE id=?";
        Object[] args = new Object[] {accessToken,deliverId};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
    public List<NotificationVO> getNotificationList(int userId, int deliverId, int offset, int size, String sortColumn, String sort) {
        ArrayList<Object> args = new ArrayList<Object>();
        String query = "SELECT * FROM notifications ";
        String where = "", order = "", limit = "";
        
        if (userId != -1) {
            if (where.isEmpty())
                where += " WHERE (user_id = ?) ";
            else
                where += " AND (user_id = ?) ";
            args.add(userId);
        }
        
        if (deliverId != -1) {
            if (where.isEmpty())
                where += " WHERE (deliver_id = ?) ";
            else
                where += " AND (deliver_id = ?) ";
            
            args.add(deliverId);
        }
        
        if (!CommonUtil.isEmptyStr(sortColumn)) 
            order = " ORDER BY " + sortColumn + " " + sort + " ";
        else 
            order = " ORDER BY id DESC ";
                
        if (offset != -1 && size != -1) 
            limit = " LIMIT " + offset + ", " + size + " ";
        
        query += where + order + limit;
        
        try {
            return getJdbcTemplate().query(query, args.toArray(), new NotificationRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public void addNewNotification(NotificationVO notification) {
        String query = "INSERT INTO notifications (user_id,deliver_id,activity_notice_id,order_id,description,reg_date) VALUES (?,?,?,?,?,?)";
        Object[] args = new Object[] {notification.getUserId(),notification.getDeliverId(),notification.getActivityNoticeId(),notification.getOrderId(),notification.getDescription(),new Date()};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void setNotificationStatus(int notificationId, int userId,  int deliverId) {
        String query = "UPDATE notifications SET status=1 WHERE id=?";
                
        Object[] args = new Object[] {notificationId};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
//        if (userId != -1)
//            query = "SELECT COUNT(*) FROM notifications WHERE (user_id = ? OR IFNULL(activity_notice_id, 0) <> 0) AND status = 0";
        
//        if (deliverId != -1)
//            query = "SELECT COUNT(*) FROM notifications WHERE (deliver_id = ? OR IFNULL(activity_notice_id, 0) <> 0) AND status = 0";
        
        query = "SELECT COUNT(*) FROM notifications WHERE (user_id = ? OR deliver_id = ?) AND status = 0";
        
        int badge = 0;
        
        try {
            badge = getJdbcTemplate().queryForObject(query, new Object[] {userId, deliverId}, Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        if (userId != -1) {
            try {
                getJdbcTemplate().update("UPDATE users SET badge = ? WHERE id = ?", new Object[]{badge, userId});
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        if (deliverId != -1) {
            try {
                getJdbcTemplate().update("UPDATE delivers SET badge = ? WHERE id = ?", new Object[]{badge, deliverId});
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public ActivityNoticeVO getActivityNotice(int id) {
        Object[] args = new Object[] {id};
        String query = "SELECT a.*, b.start_date, b.end_date " +
                       "FROM activity_notices a " +
                       "INNER JOIN activities b ON a.activity_id = b.id " +
                       "WHERE a.id = ?";
        try {
            return (ActivityNoticeVO) getJdbcTemplate().queryForObject(query, args, new ActivityNoticeRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public ActivityVO getActivity(int id) {
        Object[] args = new Object[] {id};
        String query = "SELECT * FROM activities WHERE id = ? ";
        try {
            return (ActivityVO) getJdbcTemplate().queryForObject(query, args, new ActivityRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public void addFeedback(int userId, int deliverId, String feedback) {
        String query = "INSERT INTO feedbacks (user_id, deliver_id,comment,reg_date) VALUES (?,?,?,?)";
        Object[] args = new Object[] {userId,deliverId,feedback,new Date()};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getOrderCountByDeliver(int deliverId) {
        String query = "SELECT SUM(order_details.qty) " +
                       "FROM orders " +
                        "INNER JOIN order_details ON orders.id = order_details.order_id " +
                        "WHERE orders.deliver_id = ? AND orders.order_status = '已完成'";
                
        try {
            return getJdbcTemplate().queryForObject(query, new Object[]{deliverId}, Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public GoodsVO getGoodsByOrder(int goodsId, int shopId, int userId) {
        String query = "SELECT goods.id, goods.name, goods.price, goods.description, goods.image_path, SUM(detail.qty) qty " +
                       "FROM goods " +
                       "LEFT JOIN ( " +
                       "SELECT order_details.goods_id, order_details.qty FROM orders, order_details " +
                       "WHERE orders.id = order_details.order_id AND orders.state = false AND orders.user_id = ? AND orders.shop_id = ? " +
                       ") detail ON goods.id = detail.goods_id " +
                       "WHERE goods.id = ? " +
                       "GROUP BY goods.id, goods.name, goods.price, goods.description, goods.image_path";
        
        try {
            return (GoodsVO) getJdbcTemplate().queryForObject(query, new Object[]{userId, shopId, goodsId}, new GoodsByOrderRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public UserVO getUserByWxOpenId(String wxOpenId) {
        String query = "SELECT * FROM users WHERE wx_openid = ? ";
        
        try {
            return (UserVO) getJdbcTemplate().queryForObject(query, new Object[]{wxOpenId}, new UserRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public UserVO getUser(int userId) {
        String query = "SELECT * FROM users WHERE id = ? ";
        
        try {
            return (UserVO) getJdbcTemplate().queryForObject(query, new Object[]{userId}, new UserRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public UserVO getUserById(String userId) {
        String query = "SELECT * FROM users WHERE user_id = ? ";
        
        try {
            return (UserVO) getJdbcTemplate().queryForObject(query, new Object[]{userId}, new UserRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public void addUser(String wxOpenId, String name, String imgUrl) {
        String query = "INSERT INTO users (name,wx_openid,reg_date,avatar_path) VALUES (?,?,?,?)";
        Object[] args = new Object[] {name,wxOpenId,new Date(),imgUrl};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void activeUser(String wxOpenId, String telNo) {
        String query = "UPDATE users SET status = TRUE, user_id = ? WHERE wx_openid=?";
        Object[] args = new Object[] {telNo,wxOpenId };
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void updateUserImgUrl(String wxOpenId, String nick, String imgUrl) {
        String query = "UPDATE users SET name=?, avatar_path = ? WHERE wx_openid=?";
        Object[] args = new Object[] {nick,imgUrl,wxOpenId };
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void disableUserIsNew(String wxOpenId) {
        String query = "UPDATE users SET is_new = false WHERE wx_openid=?";
        Object[] args = new Object[] {wxOpenId };
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int hasOrder(int userId, int shopId) {
        String query = "SELECT id FROM orders WHERE user_id = ? AND shop_id = ? AND state = false";
        try {
            return getJdbcTemplate().queryForObject(query, new Object[] {userId,shopId}, Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public String addNewOrder(int userId, int shopId, double shippingFee, int deliverId) {
        String orderNum = DateUtil.formatDate(new Date(), "YYMMddHHmmssSSS");
        String query = "INSERT INTO orders (order_num,user_id,shop_id,shipping_fee,deliver_id,reg_date) VALUES(?,?,?,?,?,?)";
        Object[] args = new Object[] {orderNum,userId,shopId,shippingFee,deliverId,new Date()};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return orderNum;
    }

    @Override
    public OrderVO getOrderInfo(int orderId, String orderNum, String searchKey) {
        String subConditions = "";
        Object[] args = new Object[] {orderId,orderNum};
        if (!CommonUtil.isEmptyStr(searchKey)) {
            subConditions = " AND goods.name LIKE ? ";
            args = new Object[] {orderId,orderNum,"%"+searchKey+"%"};
        }
        String query = "SELECT orders.id, orders.order_num, orders.user_id, orders.shop_id, shops.shipping_fee, orders.order_status, "
                    + "orders.description, orders.deliver_id, orders.pay_date, orders.shipping_date, orders.delivery_date, orders.state, "
                    + "SUM(order_details.qty) qty, "
                    + "IFNULL(SUM(order_details.qty*goods.price), 0) amount, "
                    + "SUM(order_coupons.amount) coupon_amount "
                    + "FROM orders "
                    + "INNER JOIN shops ON orders.shop_id = shops.id "
                    + "LEFT JOIN order_details ON orders.id = order_details.order_id "  
                    + "LEFT JOIN order_coupons ON orders.id = order_coupons.order_id "
                    + "LEFT JOIN goods ON goods.id = order_details.goods_id "
                    + "WHERE (orders.id = ? OR orders.order_num = ?) " + subConditions 
                    + "GROUP BY orders.id, orders.order_num, orders.user_id, orders.shop_id, orders.shipping_fee, orders.order_status, "
                    + "orders.description, orders.deliver_id, orders.pay_date, orders.shipping_date, orders.delivery_date, orders.state ";
        
        try {
            return (OrderVO) getJdbcTemplate().queryForObject(query, args, new OrderInfoRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public String addOrderDetail(int orderId, int goodsId, double price, int qty) {
        String query = "";
        Object[] args;
        String orderDetailId = DateUtil.formatDate(new Date(), "YYMMddHHmmssSSS");
        
        if (qty < 0) {
            query = "SELECT SUM(qty) FROM order_details WHERE order_id = ?";
            double sum  = 0;
            try {
                sum = getJdbcTemplate().queryForObject(query, new Object[] {orderId}, Double.class);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (sum <= 0)
                return null;
        }
                
        query = "INSERT INTO order_details (id,order_id,goods_id,price,qty,reg_date) VALUES(?,?,?,?,?,?)";
        args = new Object[] {orderDetailId,orderId,goodsId,price,qty,new Date()};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return orderDetailId;
    }

    @Override
    public void addNewOrderCoupon(int orderId, int userCouponId, double amount, int qty) {
        String query = "DELETE FROM order_coupons WHERE order_id = ?";
        Object[] args = new Object[] {orderId};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        query = "INSERT INTO order_coupons (order_id,user_coupon_id,amount,reg_date) VALUES(?,?,?,?)";
        args = new Object[] {orderId,userCouponId,amount,new Date()};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void settleOrder(int orderId, String description, Date predictTime) {
        double amount = 0;
        
        try {
            amount = getJdbcTemplate().queryForObject("SELECT SUM(a.qty * b.price) FROM order_details a, goods b WHERE a.goods_id = b.id AND a.order_id = ?", new Object[] {orderId}, Double.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        int userCouponId =0;
        
        try {
            userCouponId = getJdbcTemplate().queryForObject("SELECT user_coupon_id FROM order_coupons WHERE order_id = ?", new Object[] {orderId}, Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        String query = "UPDATE orders a JOIN shops b ON a.shop_id = b.id " +
                       "JOIN stations c ON b.station_id = c.id " + 
                       "SET a.state = true, a.pay_date = ?, a.description = ?, a.order_amount = ?, a.predict_date = ?, a.shipping_fee = b.shipping_fee, a.deliver_id = c.deliver_id " + 
                       "WHERE a.id = ?";
        Object[] args = new Object[] {new Date(),description,amount,predictTime,orderId};
        
        try {
            getJdbcTemplate().update(query, args);
            getJdbcTemplate().update("UPDATE order_coupons SET status = 1 WHERE order_id = ?", new Object[] {orderId});
            getJdbcTemplate().update("UPDATE user_coupons SET used_date = ?, status = 1 WHERE id = ?", new Object[] {new Date(), userCouponId});
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        OrderVO order = getOrder(orderId);
        if (order != null) {
            ShopVO shop = getShop(order.getShopId());
            if (shop != null) {
                int deliverId = shop.getDeliverId();
                String station = shop.getStationName();
                String title = "您的订单已经生成";
                String desc = "您于" + DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm") + " 在" + station + "服务区的订单已生成，我们正在安排骑手为您取餐";
                addNotification(order.getUserId(), 0, 0, orderId, title, desc);
                
                title = "新订单";
                desc = "你有个有新订单，于" + DateUtil.formatDate(order.getPayDate()) + shop.getName() + "点，" + station + "服务区，（单号：" + 
                        order.getOrderNum() + "），请妥善安排配货 " + DateUtil.formatDate(order.getPayDate(), "HH:mm");
                addNotification(0, deliverId, 0, orderId, title, desc);
            }
        }
    }

    @Override
    public void makeNewOrderDetail(int orderId) {
        OrderDetailVO old = null;
        List<OrderDetailVO> oldDetails = null;
        
        String query = "SELECT order_id, goods_id, price, IFNULL(SUM(qty), 0) qty "
                        + "FROM order_details WHERE order_id = ? "
                        + "GROUP BY order_id, goods_id, price "
                        + "HAVING IFNULL(SUM(qty), 0) > 0 ";
        
        try {
            oldDetails = getJdbcTemplate().query(query, new Object[] {orderId}, new SingleOrderDetailRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        query = "SELECT order_id, goods_id, price, qty FROM order_details WHERE order_id = ? LIMIT 1";
        
        try {
            old = (OrderDetailVO) getJdbcTemplate().queryForObject(query, new Object[] {orderId}, new SingleOrderDetailRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        if (old != null) {
//            String timestamp = old.getId();
//                String pattern = DateUtil.formatDate(DateUtil.parseDateTime(timestamp, "YYMMddHHmmssSSS"), "YYMMdd");
            ArrayList<OrderDetailVO> newDetails = null;
            
            if (oldDetails != null) {
                newDetails = new ArrayList<OrderDetailVO>();
                
                for (OrderDetailVO detail: oldDetails) {
                    OrderDetailVO newDetail = new OrderDetailVO();                    
                    newDetail.setId(String.valueOf(System.currentTimeMillis()));
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                    newDetail.setOrderId(detail.getOrderId());
                    newDetail.setGoodsId(detail.getGoodsId());
                    newDetail.setPrice(detail.getPrice());
                    newDetail.setQty(detail.getQty());
                    newDetails.add(newDetail);
                }                
            }
            
            try {
                getJdbcTemplate().update("DELETE FROM order_details WHERE order_id = ?", new Object[] {orderId});
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            if (newDetails != null) {
                Connection transaction = null;
                boolean autoCommit = true;
                
                try {
                    transaction = getConnection();
                    autoCommit = transaction.getAutoCommit();
                    transaction.setAutoCommit(false);            
                    PreparedStatement stmt = null;
                    query = "INSERT INTO order_details (id,order_id,goods_id,price,qty,reg_date) VALUES(?,?,?,?,?,?)";
                    
                    for (OrderDetailVO newDetail: newDetails) {
                        if (stmt != null)
                            stmt.close();
                        
                        stmt = transaction.prepareStatement(query);
                        stmt.setString(1, newDetail.getId());
                        stmt.setInt(2, newDetail.getOrderId());
                        stmt.setInt(3, newDetail.getGoodsId());
                        stmt.setDouble(4, newDetail.getPrice());
                        stmt.setInt(5, newDetail.getQty());
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
    }

    @Override
    public void deleteOrderDetail(int orderId, String searchKey) {
        String query;
        Object[] args;
        if (CommonUtil.isEmptyStr(searchKey)) {
            query = "DELETE FROM order_details WHERE order_id = ?";
            args = new Object[] {orderId};
        } else {
            query = "DELETE order_details FROM order_details " +
                    "INNER JOIN goods ON order_details.goods_id = goods.id " +
                    "WHERE order_id = ? AND goods.name LIKE ?";
            args = new Object[] {orderId, "%" + searchKey + "%"};
        }
        try {
            getJdbcTemplate().update(query, args);
            
            if (CommonUtil.isEmptyStr(searchKey)) {
                query = "DELETE FROM order_coupons WHERE order_id = ?";
                args = new Object[] {orderId};
            }
            
            getJdbcTemplate().update(query, args);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<CouponVO> getBaseLoginCouponList() {
        try {
            return getJdbcTemplate().query("SELECT * FROM coupons WHERE type = 0 AND SUBSTR(num,3,2) = '01' ORDER BY num", new CouponRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public List<CouponVO> getBaseShareCouponList() {
        try {
            return getJdbcTemplate().query("SELECT * FROM coupons WHERE type = 0 AND SUBSTR(num,3,2) = '02' ORDER BY num", new CouponRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public CouponVO getCoupon(int id) {
        try {
            return (CouponVO) getJdbcTemplate().queryForObject("SELECT * FROM coupons WHERE id=?", new Object[]{id}, new CouponRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public List<UserCouponVO> getUserCouponList(int userId) {
        String query = "SELECT user_coupons.coupon_id, user_coupons.user_id, coupons.kind coupon_kind, coupons.num coupon_num, coupons.price, coupons.full_value, " +
//                        "SUM(qty) qty, SUM(IFNULL(qty,0) * IFNULL(coupons.price,0)) amount, " +
                        "qty, IFNULL(qty,0) * IFNULL(coupons.price,0) amount, " +
//                        "MIN(user_coupons.valid_from_date) valid_from_date, MAX(user_coupons.valid_to_date) valid_to_date " +
                        "valid_from_date, valid_to_date " +
                        "FROM user_coupons INNER JOIN coupons ON user_coupons.coupon_id = coupons.id " +
                        "WHERE user_coupons.user_id = ? AND user_coupons.status = 0 AND user_coupons.valid_to_date >= CURRENT_DATE() " +
//                        "GROUP BY user_coupons.coupon_id, user_coupons.user_id, coupons.kind, coupons.num, coupons.price, coupons.full_value " +
                        "ORDER BY coupons.num, valid_to_date ";
        try {
            return getJdbcTemplate().query(query, new Object[]{userId}, new UserCouponRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public UserCouponVO getUserCoupon(int id) {
        String query = "SELECT user_coupons.*, coupons.price " +
                        "FROM user_coupons INNER JOIN coupons ON user_coupons.coupon_id = coupons.id " +
                        "WHERE user_coupons.id = ? ";
        try {
            return (UserCouponVO) getJdbcTemplate().queryForObject(query, new Object[]{id}, new SingleUserCouponRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public UserCouponVO getUnusedUserCoupon(int userId, int couponId) {
        String query = "SELECT user_coupons.*, coupons.price " +
                        "FROM user_coupons INNER JOIN coupons ON user_coupons.coupon_id = coupons.id " +
                        "WHERE user_coupons.user_id = ? AND user_coupons.coupon_id =? AND status = 0 AND user_coupons.valid_to_date >= CURRENT_DATE() " + 
                        "ORDER BY user_coupons.valid_from_date LIMIT 1 ";
        try {
            return (UserCouponVO) getJdbcTemplate().queryForObject(query, new Object[]{userId, couponId}, new SingleUserCouponRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public void addWelcomeCoupon(ArrayList<UserCouponVO> welcomes) {
        if (welcomes != null) {
            Connection transaction = null;
            boolean autoCommit = true;

            try {
                transaction = getConnection();
                autoCommit = transaction.getAutoCommit();
                transaction.setAutoCommit(false);            
                PreparedStatement stmt = null;
                String query = "INSERT INTO user_coupons (coupon_id,user_id,qty,valid_from_date,valid_to_date,reg_date) VALUES(?,?,?,?,?,?)";

                for (UserCouponVO coupon: welcomes) {
                    if (stmt != null)
                        stmt.close();

                    stmt = transaction.prepareStatement(query);
                    stmt.setInt(1, coupon.getCouponId());
                    stmt.setInt(2, coupon.getUserId());
                    stmt.setInt(3, coupon.getQty());
                    stmt.setDate(4, new java.sql.Date(coupon.getValidFromDate().getTime()));
                    stmt.setDate(5, new java.sql.Date(coupon.getValidToDate().getTime()));
                    stmt.setTimestamp(6, new Timestamp(new Date().getTime()));

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
    public void addNewUserCoupon(int userId, int couponId, int qty, int noticeId) {
        String query = "INSERT INTO user_coupons (coupon_id,user_id,qty,valid_from_date,valid_to_date,reg_date) VALUES(?,?,?,?,?,?) ";
                
        Date validFromDate = new Date();
        Date validToDate = DateUtil.getValidToDate(validFromDate);
        
        if (noticeId > 0) {
            ActivityNoticeVO notice = getActivityNotice(noticeId);
            
            if (notice != null) {
                validFromDate = notice.getStartDate();
                validToDate = notice.getEndDate();
            }
        }
        
        Object[] args = new Object[] {couponId, userId, qty, validFromDate, validToDate, new Date()};
        
        try {
            getJdbcTemplate().update(query, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getConsumeUserCouponIdByUser(int userId, int couponId) {
        String query = "SELECT id FROM user_coupons "
                        + "WHERE user_id = ? AND coupon_id = ? AND status = 0 AND valid_to_date >= CURRENT_DATE() "
                        + "ORDER BY valid_from_date LIMIT 1";
        
        try {
            return getJdbcTemplate().queryForObject(query, new Object[]{userId,couponId}, Integer.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return -1;
    }
    
    

    @Override
    public boolean consumeCoupon(int userId, int userCouponId, int orderId) {
        String query = "SELECT IFNULL(coupons.price,0)*IFNULL(user_coupons.qty,0) FROM coupons, user_coupons WHERE user_coupons.coupon_id = coupons.id AND user_coupons.id = ? ";
        Object[] args = new Object[] {userCouponId};
        double amount = 0;
        
        try {
            amount = getJdbcTemplate().queryForObject(query, new Object[]{userCouponId}, Double.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        if (amount > 0) {
            query = "UPDATE user_coupons SET status = 1,used_date = ? WHERE id =?";
            args = new Object[] {new Date(),userCouponId};
            
//            try {
//                getJdbcTemplate().update(query, args);
//                query = "INSERT INTO order_coupons (order_id,amount,reg_date) VALUES(?,?,?)";
//                args = new Object[] {orderId, amount, new Date()};
//                getJdbcTemplate().update(query, args);
//                return true;
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
            
        }
        
        return false;
        
    }

    @Override
    public void addNewShareCoupon(ArrayList<ShareCouponVO> shares, int orderId) {
        if (shares != null) {
            Connection transaction = null;
            boolean autoCommit = true;
            
            String shareNum = shares.get(0).getNum();

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
                
                getJdbcTemplate().update("UPDATE orders SET share_num = ? WHERE id = ?", new Object[] {shareNum, orderId});

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
    public void setShareCoupon(long id, int userId) {
        try {
            getJdbcTemplate().update("UPDATE share_coupons SET status = 1, user_id = ? WHERE id = ?", new Object[]{userId, id});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    } 

    @Override
    public List<CouponVO> getBaseFullTypeCouponList() {
        try {
            return getJdbcTemplate().query("SELECT * FROM coupons WHERE type = 0 AND SUBSTR(num,1,2) = '01' ORDER BY num", new CouponRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public List<CouponVO> getBaseUnlimitedCouponList() {
        try {
            return getJdbcTemplate().query("SELECT * FROM coupons WHERE type = 0 AND SUBSTR(num,1,2) = '02' ORDER BY num", new CouponRowMapper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    @Override
    public void addNewCoupon(CouponVO coupon) {
        String query = "INSERT INTO (num,name,kind,price,full_value,reg_date,type) VALUES (?,?,?,?,?,?,?)";
        Object[] args = new Object[] {coupon.getNum(),coupon.getName(),coupon.getKind(),coupon.getPrice(),coupon.getFullValue(),new Date()};
        
        try {
            getJdbcTemplate().update(query, args);
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
    public void addNotification(int userId, int deliverId, int activityNoticeId, int orderId, String title, String description) {
        String query = "INSERT INTO notifications (user_id, deliver_id, activity_notice_id, order_id, title, description, reg_date) " + 
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        Object[] args = new Object[] {
            userId, deliverId, activityNoticeId, orderId, title, description, new Date()
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
    public void deleteOrderCoupon(int orderId) {
        try {
            getJdbcTemplate().update("DELETE FROM order_coupons WHERE order_id = ? ", new Object[] {orderId});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    
    /*
     *  RowMapper
     */    
    
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
    
    private class SingleStationRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            StationVO station = new StationVO();
            station.setId(rs.getInt("id"));
            station.setName(rs.getString("name"));
            station.setDistrictId(rs.getInt("district_id"));
            station.setDeliverId(rs.getInt("deliver_id"));
            station.setStatus(rs.getString("status"));
            station.setLatitude(rs.getDouble("latitude"));
            station.setLongitude(rs.getDouble("longitude"));
            station.setRegDate(new Date(rs.getTimestamp("reg_date").getTime()));
            station.setImagePath(rs.getString("image_path"));
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
    
    private class GoodsKindByShopRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            GoodsKindVO goodsKind = new GoodsKindVO();
            goodsKind.setId(rs.getInt("id"));
            goodsKind.setShopKindId(rs.getInt("shop_kind_id"));
            goodsKind.setName(rs.getString("name"));
            goodsKind.setQty(rs.getInt("qty"));
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
            shop.setDeliverId(rs.getInt("deliver_id"));
            return shop;
        }        
    }
    
    private class OrderInfoRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            OrderVO order = new OrderVO();
            order.setId(rs.getInt("id"));
            order.setOrderNum(rs.getString("order_num"));
            order.setUserId(rs.getInt("user_id"));
            order.setShopId(rs.getInt("shop_id"));
            order.setOrderAmount(rs.getDouble("amount"));
            order.setOrderQty(rs.getInt("qty"));
            order.setCouponAmount(rs.getDouble("coupon_amount"));
            order.setShippingFee(rs.getDouble("shipping_fee"));
            order.setOrderStatus(rs.getString("order_status"));
            order.setDescription(rs.getString("description"));            
            order.setDeliverId(rs.getInt("deliver_id"));
            order.setPayDate(DateUtil.timestampToDate(rs.getTimestamp("pay_date")));
            order.setShippingDate(DateUtil.timestampToDate(rs.getTimestamp("shipping_date")));
            order.setDeliveryDate(DateUtil.timestampToDate(rs.getTimestamp("delivery_date")));
            order.setState(rs.getBoolean("state"));
            return order;
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
            order.setShippingFee(rs.getDouble("shipping_fee"));
            order.setOrderStatus(rs.getString("order_status"));
            order.setDescription(rs.getString("description"));            
            order.setRegDate(DateUtil.timestampToDate(rs.getTimestamp("reg_date")));
            order.setPayDate(DateUtil.timestampToDate(rs.getTimestamp("pay_date")));
            order.setShippingDate(DateUtil.timestampToDate(rs.getTimestamp("shipping_date")));
            order.setDeliveryDate(DateUtil.timestampToDate(rs.getTimestamp("delivery_date")));
            order.setState(rs.getBoolean("state"));
            order.setUserName(rs.getString("user_name"));
            order.setUserTelNo(rs.getString("user_tel_no"));
            order.setShopName(rs.getString("shop_name"));
            order.setShopAddress(rs.getString("shop_address"));
            order.setStationName(rs.getString("station_name"));
            order.setDeliverName(rs.getString("deliver_name"));
            order.setDeliverTelNo(rs.getString("deliver_tel_no"));
            order.setShareNum(rs.getString("share_num"));
            order.setPredictDate(DateUtil.timestampToDate(rs.getTimestamp("predict_date")));
            return order;
        }
    }  
    
    private class OrderByDeliverRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            OrderVO order = new OrderVO();
            order.setId(rs.getInt("id"));
            order.setOrderAmount(rs.getDouble("order_amount"));
            order.setOrderQty(rs.getInt("order_qty"));
            order.setOrderStatus(rs.getString("order_status"));
            order.setPayDate(DateUtil.timestampToDate(rs.getTimestamp("pay_date")));
            order.setShopName(rs.getString("shop_name"));
            order.setShopImagePath(rs.getString("shop_image_path"));
            return order;
        }
    }  
    
    private class OrderByUserRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            OrderVO order = new OrderVO();
            order.setId(rs.getInt("id"));
            order.setOrderAmount(rs.getDouble("order_amount"));
            order.setOrderQty(rs.getInt("order_qty"));
            order.setOrderStatus(rs.getString("order_status"));
            order.setPayDate(DateUtil.timestampToDate(rs.getTimestamp("pay_date")));
            order.setRegDate(DateUtil.timestampToDate(rs.getTimestamp("reg_date")));
            order.setShopName(rs.getString("shop_name"));
            order.setShopImagePath(rs.getString("shop_image_path"));
            order.setShopId(rs.getInt("shop_id"));
            return order;
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
    
    private class GoodsByOrderRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            GoodsVO goods = new GoodsVO();
            goods.setId(rs.getInt("id"));
            goods.setName(rs.getString("name"));
            goods.setPrice(rs.getDouble("price"));
            goods.setDescription(rs.getString("description"));
            goods.setImagePath(rs.getString("image_path"));
            goods.setQty(rs.getInt("qty"));            
            return goods;
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
            deliver.setAccessToken(rs.getString("access_token"));
            return deliver;
        }
        
    }

    private class SingleOrderDetailRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            OrderDetailVO orderDetail = new OrderDetailVO();  
            orderDetail.setOrderId(rs.getInt("order_id"));
            orderDetail.setGoodsId(rs.getInt("goods_id"));
            orderDetail.setPrice(rs.getDouble("price"));
            orderDetail.setQty(rs.getInt("qty"));
            return orderDetail;
        }
    }
    
    private class OrderDetailByPayRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            OrderDetailVO orderDetail = new OrderDetailVO();  
            orderDetail.setPrice(rs.getDouble("price"));
            orderDetail.setQty(rs.getInt("qty"));
            return orderDetail;
        }
    }

    private class OrderDetailRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            OrderDetailVO orderDetail = new OrderDetailVO();
            orderDetail.setId(rs.getString("id"));            
            orderDetail.setOrderId(rs.getInt("order_id"));
            orderDetail.setGoodsId(rs.getInt("goods_id"));
            orderDetail.setPrice(rs.getDouble("price"));
            orderDetail.setQty(rs.getInt("qty"));            
            orderDetail.setRegDate(DateUtil.timestampToDate(rs.getTimestamp("reg_date")));
            orderDetail.setGoodsNum(rs.getString("goods_num"));
            orderDetail.setGoodsName(rs.getString("goods_name"));
            orderDetail.setGoodsImagePath(rs.getString("image_path"));
            return orderDetail;
        }
    }
    
    private class OrderDetailByGoodsRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            OrderDetailVO orderDetail = new OrderDetailVO();
            orderDetail.setOrderId(rs.getInt("order_id"));
            orderDetail.setGoodsId(rs.getInt("goods_id"));
            orderDetail.setPrice(rs.getDouble("price"));
            orderDetail.setQty(rs.getInt("qty"));
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
            orderCoupon.setStatus(rs.getBoolean("status"));
            orderCoupon.setUserCouponId(rs.getInt("user_coupon_id"));
            orderCoupon.setRegDate(DateUtil.timestampToDate(rs.getTimestamp("reg_date")));
            return orderCoupon;
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
            notice.setStartDate(rs.getTimestamp("start_date"));
            notice.setEndDate(rs.getTimestamp("end_date"));
            return notice;
        }
        
    }
    
    private class ActivityRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            ActivityVO activity = new ActivityVO();
            activity.setId(rs.getInt("id"));
            activity.setName(rs.getString("name"));
            activity.setImagePath(rs.getString("image_path"));
            activity.setRegDate(rs.getTimestamp("reg_date"));
            activity.setStartDate(rs.getTimestamp("start_date"));
            activity.setEndDate(rs.getTimestamp("end_date"));
            activity.setShareNum(rs.getString("share_num"));
            activity.setCouponQty(rs.getInt("coupon_qty"));
            return activity;
        }
        
    }
    
    private class NotificationRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            NotificationVO notification = new NotificationVO();
            notification.setId(rs.getInt("id"));
            notification.setUserId(rs.getInt("user_id"));
            notification.setDeliverId(rs.getInt("deliver_id"));
            notification.setActivityNoticeId(rs.getInt("activity_notice_id"));
            notification.setActivityId(rs.getInt("activity_id"));
            notification.setOrderId(rs.getInt("order_id"));
            notification.setTitle(rs.getString("title"));
            notification.setDescription(rs.getString("description"));
            notification.setStatus(rs.getBoolean("status"));
            notification.setRegDate(rs.getTimestamp("reg_date"));
            notification.setNum(rs.getString("num"));
            return notification;
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
    
    private class UserCouponRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            UserCouponVO user_coupon = new UserCouponVO();
//            user_coupon.setId(rs.getInt("id"));
            user_coupon.setCouponId(rs.getInt("coupon_id"));
            user_coupon.setUserId(rs.getInt("user_id"));
            user_coupon.setQty(rs.getInt("qty"));
            user_coupon.setValidFromDate(rs.getTimestamp("valid_from_date"));
            user_coupon.setValidToDate(rs.getTimestamp("valid_to_date"));
//            user_coupon.setStatus(rs.getBoolean("status"));
//            user_coupon.setUsedDate(rs.getTimestamp("used_date"));
//            user_coupon.setRegDate(rs.getTimestamp("reg_date"));
//            user_coupon.setUserTelNo(rs.getString("user_tel_no"));
            user_coupon.setCouponKind(rs.getString("coupon_kind"));
            user_coupon.setCouponNum(rs.getString("coupon_num"));
            user_coupon.setAmount(rs.getDouble("amount"));
            user_coupon.setFullValue(rs.getDouble("full_value"));
            user_coupon.setPrice(rs.getDouble("price"));
            return user_coupon;
        }
        
    }
    
    private class SingleUserCouponRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            UserCouponVO user_coupon = new UserCouponVO();
            user_coupon.setId(rs.getInt("id"));
            user_coupon.setCouponId(rs.getInt("coupon_id"));
            user_coupon.setUserId(rs.getInt("user_id"));
            user_coupon.setQty(rs.getInt("qty"));
            user_coupon.setPrice(rs.getDouble("price"));
            user_coupon.setValidFromDate(rs.getTimestamp("valid_from_date"));
            user_coupon.setValidToDate(rs.getTimestamp("valid_to_date"));
            user_coupon.setStatus(rs.getBoolean("status"));
            user_coupon.setUsedDate(rs.getTimestamp("used_date"));
            user_coupon.setRegDate(rs.getTimestamp("reg_date"));
            return user_coupon;
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
            return coupon;
        }
        
    }
    
    private class ShareCouponRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            ShareCouponVO coupon = new ShareCouponVO();
            coupon.setId(rs.getLong("id"));
            coupon.setNum(rs.getString("num"));
            coupon.setCouponId(rs.getInt("coupon_id"));
            coupon.setStatus(rs.getBoolean("status"));
            coupon.setUserId(rs.getInt("user_id"));
            return coupon;
        }
        
    }
}
