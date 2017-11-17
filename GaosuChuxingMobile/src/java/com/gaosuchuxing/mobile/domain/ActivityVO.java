/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.domain;

import java.util.Date;

public class ActivityVO {
    
    private int id;
    private String num;
    private String name;
    private Date startDate;
    private Date endDate;
    private double activityAmount;
    private int couponQty;
    private String imagePath;
    private Date regDate;
    private String status;    
    private boolean unlimitedType;    
    private boolean fullType;    
    private String startDateStr;    
    private String endDateStr;    
    private long tmpUId;    
    private String shareNum;

    public String getShareNum() {
        return shareNum;
    }

    public void setShareNum(String shareNum) {
        this.shareNum = shareNum;
    }

    
    
    private boolean stopFlag;

    public boolean getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(boolean stopFlag) {
        this.stopFlag = stopFlag;
    }


    public long getTmpUId() {
        return tmpUId;
    }

    public void setTmpUId(long tmpUId) {
        this.tmpUId = tmpUId;
    }


    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }


    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }


    public boolean getFullType() {
        return fullType;
    }

    public void setFullType(boolean fullType) {
        this.fullType = fullType;
    }


    public boolean getUnlimitedType() {
        return unlimitedType;
    }

    public void setUnlimitedType(boolean unlimitedType) {
        this.unlimitedType = unlimitedType;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public int getCouponQty() {
        return couponQty;
    }

    public void setCouponQty(int couponQty) {
        this.couponQty = couponQty;
    }


    public double getActivityAmount() {
        return activityAmount;
    }

    public void setActivityAmount(double activityAmount) {
        this.activityAmount = activityAmount;
    }


    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
