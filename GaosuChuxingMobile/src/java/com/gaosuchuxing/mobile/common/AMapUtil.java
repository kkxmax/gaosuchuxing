/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gaosuchuxing.mobile.domain.PointVO;
import java.util.ArrayList;
import java.util.Vector;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.util.StringUtils;

public class AMapUtil {
    public static final String KEY = "1364e09da176c927b047f228c8cb2792";
    public static final String DRIVING_REST_API = "http://restapi.amap.com/v3/direction/driving?";
    
    public static Vector getPointInfoByDriving(double startLng, double startLat, double endLng, double endLat) {
        try {
            HttpClient client = new HttpClient();
            
            if (Constant.DEV_MODE)
                client.getHostConfiguration().setProxy("10.70.250.242", 8080);
            
            String url = DRIVING_REST_API + "key=" + KEY;
            url += "&origin=" + startLng + "," + startLat;
            url += "&destination=" + endLng + "," + endLat;
            url += "&extensions=all";
            url += "&strategy=0";
            
            GetMethod get = new GetMethod(url);
            
            int responseCode = client.executeMethod(get);
            String responseBody = get.getResponseBodyAsString();
            
            if(responseCode == 200) {
                com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(responseBody);
                String info = json.getString("info");
                String status = json.getString("status");
                
                if (status.equals("1") && info.equals("OK")) {
                    String route = json.getString("route");
                    json = com.alibaba.fastjson.JSONObject.parseObject(route);
                    
                    json = (JSONObject) json.getJSONArray("paths").get(0);
                        
                    JSONArray steps = json.getJSONArray("steps");
                    
                    ArrayList<PointVO> pointList = new ArrayList<PointVO>();
                    ArrayList<String> adcodeList = new ArrayList<String>();
                    
                    for (Object obj: steps) {
                        String polyline = ((JSONObject)obj).getString("polyline");
//                        String[] points = StringUtils.split(polyline, ";");
                        String[] points = polyline.split(";", -1);
                        
                        json = (JSONObject) ((JSONObject)obj).getJSONArray("cities").get(0);
                        json = (JSONObject) json.getJSONArray("districts").get(0);
                        String adcode = json.getString("adcode");
                        adcode = adcode.substring(0, 3);
                        
                        if (!adcodeList.contains(adcode))
                            adcodeList.add(adcode);
                        
                        for (String point: points) {
                            String[] lngLat = StringUtils.split(point, ",");
                            PointVO pointVO = new PointVO(lngLat[0], lngLat[1]);
                            pointList.add(pointVO);
                        }
                    }
                    
                    Vector vector = new Vector();
                    vector.add(pointList);
                    vector.add(adcodeList);
                    
                    return vector;
                }
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public static JSONObject getDrivingInfo(double startLng, double startLat, double endLng, double endLat) {
        try {
            HttpClient client = new HttpClient();
            
            if (Constant.DEV_MODE)
                client.getHostConfiguration().setProxy("10.70.250.242", 8080);
            
            String url = DRIVING_REST_API + "key=" + KEY;
            url += "&origin=" + startLng + "," + startLat;
            url += "&destination=" + endLng + "," + endLat;
            url += "&extensions=all";
            url += "&strategy=0";
            
            GetMethod get = new GetMethod(url);
            
            int responseCode = client.executeMethod(get);
            String responseBody = get.getResponseBodyAsString();
            
            if(responseCode == 200) {
                com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(responseBody);
                String info = json.getString("info");
                String status = json.getString("status");
                
                if (status.equals("1") && info.equals("OK")) {
                    String route = json.getString("route");
                    json = com.alibaba.fastjson.JSONObject.parseObject(route);
                    
                    json = (JSONObject) json.getJSONArray("paths").get(0);
                    
                    long distance = json.getLong("distance");
                    long duration = json.getLong("duration");
                    
                    JSONObject obj = new JSONObject();
                    obj.put("distance", getDistanceInfo(distance));
                    obj.put("duration", getDurationInfo(duration));
                    
                    return obj;
                    
                }
            }    
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;    
    }
    
    public static String getDurationInfo(long duration) {
        long hour, min;
        if (duration >= 60 * 60) { //hour
            hour = duration / 3600;
            min = (duration - hour*3600) / 60;
            return hour + "小时" + min + "分钟";
        } else if (duration >= 60) { //min
            min = duration / 60;
            return min + "分钟";
        } else {
            return duration + "秒钟";
        }
    }
    
    public static String getDistanceInfo(long distance) {
        long km;
        if (distance > 1000) { //km
            km = distance / 1000;
            return km + "公里";
        } else {
            return distance + "米";
        }
    }
}
