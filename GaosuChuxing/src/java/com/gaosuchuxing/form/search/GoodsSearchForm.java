/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.form.search;


public class GoodsSearchForm {
    private String keyword;
    private String shopId;    
    private String shopKindId;    
    
    public String getShopKindId() {
        return shopKindId;
    }

    public void setShopKindId(String shopKindId) {
        this.shopKindId = shopKindId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
