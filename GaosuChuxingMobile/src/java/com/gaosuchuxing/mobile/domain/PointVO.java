/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.domain;

import com.gaosuchuxing.mobile.util.NumberUtil;

public class PointVO {
    
    private double lng;    
    private double lat;
    
    public PointVO(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }
    
    public PointVO(String lng, String lat) {
        this(NumberUtil.strToDouble(lng), NumberUtil.strToDouble(lat));
    }
    
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }


    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

}
