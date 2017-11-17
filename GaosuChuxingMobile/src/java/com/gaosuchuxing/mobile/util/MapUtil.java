/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.util;

import com.gaosuchuxing.mobile.domain.StepVO;
import com.gaosuchuxing.mobile.domain.StationVO;

public class MapUtil {
    public static final double RANGE = 1000;
    
    
    public static double getHeightOfTriangleWithBottomA(double a, double b, double c) {
        if (a==0)
            return 0;
    
        double p = (a+b+c)/2f;
        return Math.sqrt(p*(p-a)*(p-b)*(p-c))*2f/a;
    }
    
    public static double getAngleOfTriangleWithFacingA(double a, double b, double c) {
        if (b==0 || c==0)
            return 90;
    
        double cosA = (b*b+c*c-a*a)/(2*b*c);
        return Math.acos(cosA);
    }
    
    public static boolean checkStation(StationVO station, StepVO step, int i, int j) {        
//        double a = getDistance(step.getSlat(), step.getElat(), step.getSlng(), step.getElng());
//        double b = getDistance(step.getSlat(), station.getLatitude(), step.getSlng(), station.getLongitude());
//        double c = getDistance(station.getLatitude(), step.getElat(), station.getLongitude(), step.getElng());

        double dist = calcLineDistance(step.getELng(), step.getELat(), station.getLongitude(), station.getLatitude());

        if (dist <= 100) {
//            System.out.println("i=" + i + ", station=" + station.getId() + ", dist=" + dist);
            double leftAndRight = calcLeftAndRightAngle(station, step);
            if (leftAndRight < 0)
                return true;
            else
                return false;
        }
        
        double a = calcLineDistance(step.getSLng(), step.getSLat(), step.getELng(), step.getELat());
        double b = calcLineDistance(step.getSLng(), step.getSLat(), station.getLongitude(), station.getLatitude());
        double c = calcLineDistance(station.getLongitude(), station.getLatitude(), step.getELng(), step.getELat());

        String abc = "a=" + a + ", b=" + b + ", c=" + c;

//        System.out.println("a=" + a + ", b=" + b + ", c=" + c);
                
        double height = getHeightOfTriangleWithBottomA(a, b, c);
        
        String h = "height=" + height;
        
//        System.out.println("height=" + height);
        
        double angleB = getAngleOfTriangleWithFacingA(b, a, c);
        double angleC = getAngleOfTriangleWithFacingA(c, a, b);
        
        String angle = "angleB=" + angleB + ", angleC=" + angleC;
        
//        System.out.println("angleB=" + angleB + ",  angleC=" + angleC);
//        if (angleB <= Math.PI/2 && angleC <= Math.PI/2)
//            System.out.println("i=" + i + ", station=" + station.getId() + ", " + abc + ", " + h + ", " + angle);
        
//        if (station.getId() == 97)
//            System.out.println("i=" + i + ", station=" + station.getId() + ", " + abc + ", " + h + ", " + angle);
        
        if (height <= 100 && angleB<= Math.PI/2 && angleC <= Math.PI/2) {
//            System.out.println("i=" + i + ", station=" + station.getId() + ", " + abc + ", " + h + ", " + angle);
            
            
            double leftAndRight = calcLeftAndRightAngle(station, step);
            
//            System.out.println("leftAndRight=" + leftAndRight);
            if (leftAndRight < 0)
                return true;
            else
                return false;
        } else {        
            return false;
        }

//        if (angleB <= Math.PI/2 && angleC <= Math.PI/2) {
//            if (height <= 1000)
//                return true;
//            else if (angleB <= 0.1 && angleC <= 0.1)
//                return true;
//            else 
//                return false;
//        } else  {
//            return false;
//        }
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
    
    public static double calcLineDistance(double startLng, double startLat, double endLng, double endLat) {
//        if ((start == null) || (end == null)) {
//            throw new IllegalArgumentException("非法坐标值，不能为null");
//        }
        double d1 = 0.01745329251994329D;
//        double d2 = start.longitude;
        double d2 = startLng;
//        double d3 = start.latitude;
        double d3 = startLat;
//        double d4 = end.longitude;
        double d4 = endLng;
//        double d5 = end.latitude;
        double d5 = endLat;
        d2 *= d1;
        d3 *= d1;
        d4 *= d1;
        d5 *= d1;
        double d6 = Math.sin(d2);
        double d7 = Math.sin(d3);
        double d8 = Math.cos(d2);
        double d9 = Math.cos(d3);
        double d10 = Math.sin(d4);
        double d11 = Math.sin(d5);
        double d12 = Math.cos(d4);
        double d13 = Math.cos(d5);
        double[] arrayOfDouble1 = new double[3];
        double[] arrayOfDouble2 = new double[3];
        arrayOfDouble1[0] = (d9 * d8);
        arrayOfDouble1[1] = (d9 * d6);
        arrayOfDouble1[2] = d7;
        arrayOfDouble2[0] = (d13 * d12);
        arrayOfDouble2[1] = (d13 * d10);
        arrayOfDouble2[2] = d11;
        double d14 = Math.sqrt((arrayOfDouble1[0] - arrayOfDouble2[0]) * (arrayOfDouble1[0] - arrayOfDouble2[0])
                + (arrayOfDouble1[1] - arrayOfDouble2[1]) * (arrayOfDouble1[1] - arrayOfDouble2[1])
                + (arrayOfDouble1[2] - arrayOfDouble2[2]) * (arrayOfDouble1[2] - arrayOfDouble2[2]));

        return (Math.asin(d14 / 2.0D) * 12742001.579854401D);
    }
    
    // return < 0 ? right: left
    public static double calcLeftAndRightAngle(StationVO station, StepVO step) {
        double x1 = step.getELng() - step.getSLng();
        double y1 = step.getELat() - step.getSLat();
        double x = station.getLongitude()- step.getSLng();
        double y = station.getLatitude()- step.getSLat();
        
        double angle = (x1*y - y1*x) / Math.sqrt(x1*x1 + y1*y1);
        
        return angle;
    }
}
