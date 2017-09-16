/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.domain;

import java.util.Date;

public class ShopVO {
    private int id;        
    private String name;
    private int stationId;     
    private int districtId;       
    private double startFee;    
    private double shippingFee;
    private Date regDate;    
    private String type;    
    private String districtName;    
    private String stationName;    
    private int districtParentId;    
    private String imagePath;    
    private String districtParentName;    
    private int shopKindId;    
    private String shopingKindName;    
    private String description;
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getShopingKindName() {
        return shopingKindName;
    }

    public void setShopingKindName(String shopingKindName) {
        this.shopingKindName = shopingKindName;
    }


    public int getShopKindId() {
        return shopKindId;
    }

    public void setShopKindId(int shopKindId) {
        this.shopKindId = shopKindId;
    }


    public String getDistrictParentName() {
        return districtParentName;
    }

    public void setDistrictParentName(String districtParentName) {
        this.districtParentName = districtParentName;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public int getDistrictParentId() {
        return districtParentId;
    }

    public void setDistrictParentId(int districtParentId) {
        this.districtParentId = districtParentId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }


    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }


    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }


    public double getStartFee() {
        return startFee;
    }

    public void setStartFee(double startFee) {
        this.startFee = startFee;
    }


    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
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

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }
}
