/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.domain;

public class ShareCouponVO {
    
    private long id;
    private String num;    
    private int couponId;    
    private boolean status;    
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }


    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
