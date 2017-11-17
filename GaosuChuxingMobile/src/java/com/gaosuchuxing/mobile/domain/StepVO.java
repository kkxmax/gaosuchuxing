/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.domain;

public class StepVO {
    
    private double sLng;    
    private double sLat;    
    private double eLng;    
    private double eLat;    

    public StepVO() {}
    
    public StepVO(double sLng, double sLat, double eLng, double eLat) {
        this.sLng = sLng;
        this.sLat = sLat;
        this.eLng = eLng;
        this.eLat = eLat;
    }

    public double getELat() {
        return eLat;
    }

    public void setELat(double eLat) {
        this.eLat = eLat;
    }


    public double getELng() {
        return eLng;
    }

    public void setELng(double eLng) {
        this.eLng = eLng;
    }


    public double getSLat() {
        return sLat;
    }

    public void setSLat(double sLat) {
        this.sLat = sLat;
    }


    public double getSLng() {
        return sLng;
    }

    public void setSLng(double sLng) {
        this.sLng = sLng;
    }

    
}
