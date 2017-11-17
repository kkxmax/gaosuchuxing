/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.form.search;


public class StationSearchForm {    
    private String keyword;
    private String status;    
    private String districtId;    
    private String districtParentId;
    
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
