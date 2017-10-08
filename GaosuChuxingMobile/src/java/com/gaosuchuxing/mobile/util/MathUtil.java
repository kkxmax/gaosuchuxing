/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.util;

import com.gaosuchuxing.mobile.domain.StepVO;
import com.gaosuchuxing.mobile.domain.StationVO;

public class MathUtil {
    
    public static double getHeightOfTriangleWithBottomA(double a, double b, double c) {
        if (a==0)
            return 0;
    
        double p = (a+b+c)/2.f;
        return Math.sqrt(p*(p-a)*(p-b)*(p-c))*2.f/a;
    }
    
    public static double getAngleOfTriangleWithFacingA(double a, double b, double c) {
        if (b==0 || c==0)
            return 90;
    
        double cosA = (b*b+c*c-a*a)/(2*b*c);
        return Math.acos(cosA);
    }
    
    public static boolean checkStation(StationVO station, StepVO step) {        
        double a = getDistance(step.getSlat(), step.getElat(), step.getSlng(), step.getElng());
        double b = getDistance(step.getSlat(), station.getLatitude(), step.getSlng(), station.getLongitude());
        double c = getDistance(station.getLatitude(), step.getElat(), station.getLongitude(), step.getElng());
//        double a = getDistance(step.sLat, step.sLng, step.eLat, step.eLng);
//        double b = getDistance(step.sLat, station.getLatitude(), step.sLng, station.getLongitude());
//        double c = getDistance(station.getLatitude(), step.eLat, station.getLongitude(), step.eLng);

//        System.out.println("a=" + a + ", b=" + b + ", c=" + c);
                
        double height = getHeightOfTriangleWithBottomA(a, b, c);
        
//        System.out.println("height=" + height);
        
        double angleB = getAngleOfTriangleWithFacingA(b, a, c);
        double angleC = getAngleOfTriangleWithFacingA(c, a, b);
        
//        System.out.println("angleB=" + angleB + ", angleC=" + angleC);
        
        if (height < 1000 && angleB <= Math.PI/2 && angleC <= Math.PI/2) {
            return true;
        } else {        
            return false;
        }
    }
    
    public static double getDistance(double x1, double x2, double y1, double y2) {        
//        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));

        int R = 6371000; // metres
        double φ1 = Math.toRadians(x1);
        double φ2 = Math.toRadians(x2);
        double Δφ = Math.toRadians((x2-x1));
        double Δλ = Math.toRadians((y2-y1));

        double a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
                Math.cos(φ1) * Math.cos(φ2) *
                Math.sin(Δλ/2) * Math.sin(Δλ/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double d = R * c;
        return d;
    }
}
