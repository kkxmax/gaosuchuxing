/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.util;

import com.gaosuchuxing.common.Constant;
import com.gaosuchuxing.domain.ActivityVO;
import com.gaosuchuxing.domain.CouponVO;
import com.gaosuchuxing.domain.OrderVO;
import com.gaosuchuxing.domain.ShareCouponVO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;

public class TaskSchedule {    
    private Timer mActivityTimer;
    private Timer mOrderTimer;
    private Timer mCleanTimer;
    private Calendar calendar;
    
    ArrayList<ActivityVO> activityList = new ArrayList<ActivityVO>();
        
    public void startTask() { 
        if (mActivityTimer == null)
            mActivityTimer = new Timer();
        
        getActiviyList();
        
        mActivityTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (activityList != null && !activityList.isEmpty()) {
                    Date now = new Date();
                    for (ActivityVO activity: activityList) {
                        if (activity.getStatus().equals(Constant.ACTIVITY_STATUS.READY) && (activity.getStartDate().before(now) || activity.getStartDate().equals(now))) {
                            addNotification(activity.getId(), activity.getName(), "");
                            activity.setStatus(Constant.ACTIVITY_STATUS.IN_PROGRESS);
                        }
                        
                        if (activity.getEndDate().before(now)) {
                            setActivityStop(activity.getId());
                            activity.setStopFlag(true);
                        }
                    }
                }    
                
                reloadActivityList();
            }
        }, 0, 1000 * 1);
        
        if (mOrderTimer == null)
            mOrderTimer = new Timer();
        
        mOrderTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ArrayList<OrderVO> delayedList = getDelayedOrderList();
                if (delayedList != null && !delayedList.isEmpty()) {
                    for (OrderVO delayed: delayedList) {
                        if (DateUtil.isDelayedDelivery(delayed.getPayDate())) {
                            setOrderStatus(delayed.getId());
                        }
                    }                    
                }
            }
        }, 1000, 60 * 1000 * 10);
        
        if (mCleanTimer == null)
            mCleanTimer = new Timer();
        
        calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        
        calendar.set(yy, mm, dd, 21, 0, 1);
        
        mCleanTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Date now = new Date();
                if (now.after(calendar.getTime())) {
                    setCleanOrder();
                    calendar.add(Calendar.HOUR_OF_DAY, 24);
                }
            }
        }, 1000, 1000);
    }
    
    public void stopTask() {
        if (mActivityTimer != null) {
            mActivityTimer.cancel();
        }
        
        if (mOrderTimer != null) {
            mOrderTimer.cancel();
        }
        
        if (mCleanTimer != null)
            mCleanTimer.cancel();
        
        mActivityTimer = null;
        mOrderTimer = null;
        mCleanTimer = null;
    }
    
    private void reloadActivityList() {
        try {
            if (activityList != null) {
                for (Iterator<ActivityVO> iterator = activityList.iterator(); iterator.hasNext(); ) {
                    ActivityVO info = iterator.next();
                    if (info.getStopFlag())
                        iterator.remove();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void refreshActiviyList() {
        if (activityList != null) {
            activityList.clear();
        
            getActiviyList();
        }
    }
    
    public void getActiviyList() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM activities WHERE status = '未开始'";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constant.JdbcUrl, Constant.DbUser, Constant.DbPwd);
            
            stmt = con.createStatement();
            
            rs = stmt.executeQuery(query);
            
            activityList.clear();
            
            while(rs.next()) {
                ActivityVO activity = new ActivityVO();
                activity.setId(rs.getInt("id"));
                activity.setName(rs.getString("name"));
                activity.setStartDate(rs.getTimestamp("start_date"));
                activity.setEndDate(rs.getTimestamp("end_date"));
                activity.setStatus(rs.getString("status"));
                activityList.add(activity);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
        
    public void setActivityStop(int activityId) {
        String query = "";
        
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constant.JdbcUrl, Constant.DbUser, Constant.DbPwd);
            
            if (stmt != null)
                stmt.close();
            query = "UPDATE activities SET status = '已结束' WHERE id= ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, activityId);
            stmt.executeUpdate();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }    
        }    
    }
    
    public void addNotification(int activityId, String title, String desc) {
        String num = getShareCoupon(activityId);
        
        String query = "";
        
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        ArrayList<Integer> userIdList = new ArrayList<Integer>();
        ArrayList<Integer> deliverIdList = new ArrayList<Integer>();
        
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constant.JdbcUrl, Constant.DbUser, Constant.DbPwd);
            
            query = "SELECT id FROM users";                        
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            
            if (rs != null) {
                while (rs.next()) {
                    int userId = rs.getInt("id");
                    userIdList.add(userId);
                }
            }
            
            if (stmt != null)
                stmt.close();
            
            if (rs != null)
                rs.close();
            
            query = "SELECT id FROM delivers";                        
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            
            if (rs != null) {
                while (rs.next()) {
                    int deliverId = rs.getInt("id");
                    deliverIdList.add(deliverId);
                }
            }
            
            con.setAutoCommit(false);
            
            if (userIdList != null) {
                query = "INSERT INTO notifications (user_id,activity_id,title,description,reg_date,shared,num) " + 
                        "VALUES (?,?,?,?,?,?,?)";
                for (Integer userId: userIdList) {
                    if (stmt != null)
                        stmt.close();
                    
                    stmt = con.prepareStatement(query);
                    stmt.setInt(1, userId);
                    stmt.setInt(2, activityId);
                    stmt.setString(3, title);
                    stmt.setString(4, desc);
                    stmt.setTimestamp(5, new Timestamp(new Date().getTime()));
                    stmt.setBoolean(6, true);    
                    stmt.setString(7, num);    
                    stmt.executeUpdate();
                }
                
                con.commit();
            }
            
            if (deliverIdList != null) {
                query = "INSERT INTO notifications (deliver_id,activity_id,title,description,reg_date,shared,num) " + 
                        "VALUES (?,?,?,?,?,?,?)";
                for (Integer deliverId: deliverIdList) {
                    if (stmt != null)
                        stmt.close();
                    
                    stmt = con.prepareStatement(query);
                    stmt.setInt(1, deliverId);
                    stmt.setInt(2, activityId);
                    stmt.setString(3, title);
                    stmt.setString(4, desc);
                    stmt.setTimestamp(5, new Timestamp(new Date().getTime()));
                    stmt.setBoolean(6, true);    
                    stmt.setString(7, num);    
                    stmt.executeUpdate();
                }
                
                con.commit();
            }
            
            if (stmt != null)
                stmt.close();
            query = "UPDATE users SET badge = IFNULL(badge, 0) + 1";
            stmt = con.prepareStatement(query);
            stmt.executeUpdate();            
            
            if (stmt != null)
                stmt.close();
            query = "UPDATE delivers SET badge = IFNULL(badge, 0) + 1";
            stmt = con.prepareStatement(query);
            stmt.executeUpdate();            
            
            if (stmt != null)
                stmt.close();
            query = "UPDATE activities SET status = '进行中', share_num = ? WHERE id= ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, num);
            stmt.setInt(2, activityId);
            stmt.executeUpdate();
            
            con.commit();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }        
        
    }
    
    private ArrayList<OrderVO> getDelayedOrderList() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        ArrayList<OrderVO> delayed = new ArrayList<OrderVO>();
        
        String query = "SELECT id, pay_date FROM orders " +
                       "WHERE order_status <> '已完成' AND state = 1 AND pay_date IS NOT NULL ";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constant.JdbcUrl, Constant.DbUser, Constant.DbPwd);
            
            stmt = con.createStatement();
            
            rs = stmt.executeQuery(query);
            
            while(rs.next()) {
                OrderVO order = new OrderVO();
                order.setId(rs.getInt("id"));
                order.setPayDate(rs.getTimestamp("pay_date"));
                delayed.add(order);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        return delayed;
    }
    
    private void setCleanOrder() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        ArrayList<Integer> cleans = new ArrayList<Integer>();
        
        String query = "SELECT id FROM orders WHERE state = 0 ";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constant.JdbcUrl, Constant.DbUser, Constant.DbPwd);
            
            stmt = con.createStatement();
            
            rs = stmt.executeQuery(query);
            
            while(rs.next()) {
                cleans.add(rs.getInt("id"));
            }
            
            rs.close();
            
            if (cleans != null && !cleans.isEmpty()) {
                String args = StringUtils.join(cleans.toArray(), ",");
                
                if (stmt != null)
                    stmt.close();
                stmt = con.createStatement();
                stmt.executeUpdate("DELETE FROM order_coupons WHERE order_id IN (" + args + ")");
                
                if (stmt != null)
                    stmt.close();
                stmt = con.createStatement();
                stmt.executeUpdate("DELETE FROM order_details WHERE order_id IN (" + args + ")");
                
                if (stmt != null)
                    stmt.close();
                stmt = con.createStatement();
                stmt.executeUpdate("DELETE FROM orders WHERE id IN (" + args + ")");
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        
    }
    
    public void setOrderStatus(int orderId) {
        String query = "UPDATE orders SET order_status = '已完成', delivery_date = ?, shipping_date = IFNULL(shipping_date, ?) WHERE id = ?";
        
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constant.JdbcUrl, Constant.DbUser, Constant.DbPwd);
            
            stmt = con.prepareStatement(query);
            stmt.setTimestamp(1, new Timestamp(new Date().getTime()));
            stmt.setTimestamp(2, new Timestamp(new Date().getTime()));
            stmt.setInt(3, orderId);
            
            stmt.executeUpdate();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public String getShareCoupon(int activityId) {
        ArrayList<CouponVO> coupons = new ArrayList<CouponVO>();
        ArrayList<ShareCouponVO> shares = new ArrayList<ShareCouponVO>();
        String num = DateUtil.formatDate(new Date(), "YYMMddHHmmssSSS");
        
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constant.JdbcUrl, Constant.DbUser, Constant.DbPwd);
            
            String query = "SELECT d.*, b.coupon_qty, c.rate, b.start_date, b.end_date " +
                        "FROM activities b " +
                        "INNER JOIN activity_details c ON b.id = c.activity_id " +
                        "INNER JOIN coupons d ON c.coupon_num = d.num " +
                        "WHERE b.id = ? ";
            
            stmt = con.prepareStatement(query);
            stmt.setInt(1, activityId);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
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
                coupons.add(coupon);
            }
            
            int qty, total;
            for (CouponVO coupon: coupons) {
                total = coupon.getCouponQty();
                double rate = coupon.getRate();
                qty = (int)(total * (rate/100));

                for (int i = 0; i < qty; i++) {
                    ShareCouponVO share = new ShareCouponVO();
                    share.setNum(num);
                    share.setCouponId(coupon.getId());
                    shares.add(share);
                }
            }
            
            con.setAutoCommit(false);            
            query = "INSERT INTO share_coupons (num,coupon_id) VALUES(?,?)";

            for (ShareCouponVO share: shares) {
                if (stmt != null)
                    stmt.close();

                stmt = con.prepareStatement(query);
                stmt.setString(1, share.getNum());
                stmt.setInt(2, share.getCouponId());

                stmt.execute();
            }

            con.commit();

            if (stmt != null)
                stmt.close();
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return num;
        
    }
}
