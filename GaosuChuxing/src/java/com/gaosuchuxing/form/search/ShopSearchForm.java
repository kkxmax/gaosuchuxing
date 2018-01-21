/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.form.search;


public class ShopSearchForm {    
    private String keyword;
    private String shopKindId;    
    private String districtId;    
    private String districtParentId;    
    private String stationId;    
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    
    public String getDistrictParentId() {
        return districtParentId;
    }
    
    public void setDistrictParentId(String districtParentId) {
        this.districtParentId = districtParentId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getShopKindId() {
        return shopKindId;
    }

    public void setShopKindId(String shopKindId) {
        this.shopKindId = shopKindId;
    }


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
