/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.domain;

import com.gaosuchuxing.mobile.util.CommonUtil;
import java.util.Date;

public class CouponVO {
    private int id;
    private String num;
    private String name;
    private double price;
    private Date regDate;
    private double fullValue;    
    private int type;
    private String kind;
    
    public String getCouponType() {
        if (CommonUtil.isEmptyStr(num)) {
            return "";
        } else {
            if (num.substring(0, 2).equals("01"))
                return "full_type";
            else
                return "unlimited_type";
        }
            
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public double getFullValue() {
        return fullValue;
    }

    public void setFullValue(double fullValue) {
        this.fullValue = fullValue;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }


    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
