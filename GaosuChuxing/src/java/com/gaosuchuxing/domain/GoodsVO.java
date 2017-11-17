/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.domain;

import java.util.Date;

public class GoodsVO {
    private int id;
    private String num;
    private int shopKindId;    
    private int shopId;    
    private String name;
    private int goodsKindId;    
    private double price;
    private String description;    
    private String imagePath;    
    private String status;    
    private Date regDate;
    private String shopName;
    private String goodsKindName;
    private String shopKindName;

    public String getShopKindName() {
        return shopKindName;
    }

    public void setShopKindName(String shopKindName) {
        this.shopKindName = shopKindName;
    }

    public String getGoodsKindName() {
        return goodsKindName;
    }

    public void setGoodsKindName(String goodsKindName) {
        this.goodsKindName = goodsKindName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public int getGoodsKindId() {
        return goodsKindId;
    }

    public void setGoodsKindId(int goodsKindId) {
        this.goodsKindId = goodsKindId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
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

    public int getShopKindId() {
        return shopKindId;
    }

    public void setShopKindId(int shopKindId) {
        this.shopKindId = shopKindId;
    }    
}
