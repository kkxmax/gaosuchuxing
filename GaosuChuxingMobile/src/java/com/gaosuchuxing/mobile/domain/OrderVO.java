/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.domain;

import java.util.Date;

public class OrderVO {
    private int id;        
    private String orderNum;
    private int userId;     
    private String userName;  
    private String userTelNo;    
    private int shopId;       
    private String shopName;
    private String stationName;   
    private double orderAmount;    
    private String goodsNum;
    private String goodsName;    
    private double couponAmount;  
    private String orderStatus;           
    private int deliverId;    
    private String deliverName;                          
    private String deliverTelNo;
    private String description;                   
    private Date regDate;    
    private Date payDate;    
    private Date shippingDate;    
    private Date deliveryDate;
    private boolean state;    
    private String shopAddress;    
    private int orderQty;    
    private String deliverAccountId;    
    private String shopImagePath;    
    private double shippingFee;   

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }


    public String getShopImagePath() {
        return shopImagePath;
    }

    public void setShopImagePath(String shopImagePath) {
        this.shopImagePath = shopImagePath;
    }

    public String getDeliverAccountId() {
        return deliverAccountId;
    }

    public void setDeliverAccountId(String deliverAccountId) {
        this.deliverAccountId = deliverAccountId;
    }


    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }


    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }


    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }


    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }


    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }


    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }


    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }


    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }


    public String getUserTelNo() {
        return userTelNo;
    }

    public void setUserTelNo(String userTelNo) {
        this.userTelNo = userTelNo;
    }

    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDeliverTelNo() {
        return deliverTelNo;
    }

    public void setDeliverTelNo(String deliverTelNo) {
        this.deliverTelNo = deliverTelNo;
    }

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
    }


    public double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(double couponAmount) {
        this.couponAmount = couponAmount;
    }


    public int getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(int deliverId) {
        this.deliverId = deliverId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }


    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
}
