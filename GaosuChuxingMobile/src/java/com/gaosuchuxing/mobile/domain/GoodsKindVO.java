/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.domain;

import java.util.Date;

public class GoodsKindVO {
    private int id;
    private String name;
    private Date regDate;    
    private int ShopKindId;    
    private String shopKindName;    
    private int qty;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }


    public String getShopKindName() {
        return shopKindName;
    }

    public void setShopKindName(String shopKindName) {
        this.shopKindName = shopKindName;
    }


    public int getShopKindId() {
        return ShopKindId;
    }

    public void setShopKindId(int ShopKindId) {
        this.ShopKindId = ShopKindId;
    }

    
    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
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
