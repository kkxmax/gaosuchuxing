/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.domain;

import com.gaosuchuxing.mobile.util.CommonUtil;
import java.util.Date;

public class UserCouponVO {
    private int id;
    private int couponId;
    private int qty;
    private String couponName;
    private int userId;
    private String userTelNo;
    private Date validFromDate;
    private Date validToDate;
    private boolean status;    
    private Date regDate;
    private Date usedDate;    
    private String couponNum;    
    private double amount;    
    private double fullValue;    
    private double price;
    private String couponKind;

    public String getCouponKind() {
        return couponKind;
    }

    public void setCouponKind(String couponKind) {
        this.couponKind = couponKind;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public double getFullValue() {
        return fullValue;
    }

    public void setFullValue(double fullValue) {
        this.fullValue = fullValue;
    }

    
    public String getCouponType() {
//        if (CommonUtil.isEmptyStr(couponNum)) {
//            return "";
//        } else {
//            if (couponNum.substring(0, 2).equals("01"))
//                return "full_type";
//            else
//                return "unlimited_type";
//        }
        if (fullValue > 0)
            return "full_type";
        else
            return "unlimited_type";
            
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(String couponNum) {
        this.couponNum = couponNum;
    }

    public String getUserTelNo() {
        return userTelNo;
    }

    public void setUserTelNo(String userTelNo) {
        this.userTelNo = userTelNo;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }


    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public Date getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Date getValidToDate() {
        return validToDate;
    }

    public void setValidToDate(Date validToDate) {
        this.validToDate = validToDate;
    }

    public Date getValidFromDate() {
        return validFromDate;
    }

    public void setValidFromDate(Date validFromDate) {
        this.validFromDate = validFromDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
