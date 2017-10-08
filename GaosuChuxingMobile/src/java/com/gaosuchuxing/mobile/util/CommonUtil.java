/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.util;

import com.gaosuchuxing.mobile.common.Constant;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CommonUtil {
    public static int getBitVal(int n, int pos) {
        return (n >> pos) & 1;
    }

    public static int findBitVal(int bit) {
        return (int)Math.pow(2, bit);
    }
    
    public static int setBitVal(int n, int bit) {
        return n |= (1 << bit);
    }
    
    public static int unSetBitVal(int n, int bit) {
        return n &= ~(1 << bit);
    }
    
    public static ArrayList<String> getUniqArrayList(ArrayList<String> arrlist) {
        ArrayList<String> result = new ArrayList<String>();
        if (arrlist != null) {
            HashSet<String> set = new HashSet<String>();
            for (String item: arrlist) {
                if (!set.contains(item)) {
                    result.add(item);
                    set.add(item);
                }
            }
        }
        return result;
    }
    
    public static String charsetEncoding(String str) {
        if (str == null || str.isEmpty())
            return "";
        
        try {
            return new String(str.trim().getBytes("8859_1"), "utf-8");
        } catch (Exception ex) {}   
        
        return null;
    }
        
    public static boolean isEmptyStr(String str) {
        if (str == null)
            return true;
        else
            return str.isEmpty();
    }
    
    public static File getServerRoot(HttpServletRequest request) {
        File serverRoot = new File(request.getSession().getServletContext().getRealPath("/")).getParentFile();
        return serverRoot;
    }
    
//    public static File getImageDir(HttpServletRequest request) {
//        File imageDir = new File(getServerRoot(request), Constant.IMAGE_DIR);
//
//        if (!imageDir.exists())
//            imageDir.mkdir();
//        
//        return imageDir;
//    }
//    
//    public static File getAvatarImageDir(HttpServletRequest request) {
//        File imageDir = new File(getImageDir(request), Constant.AVATAR_IMAGE_DIR);
//        
//        if (!imageDir.exists())
//            imageDir.mkdir();
//        
//        return imageDir;
//    }
//    
//    public static File getGoodsImageDir(HttpServletRequest request) {
//        File imageDir = new File(getImageDir(request), Constant.GOODS_IMAGE_DIR);
//        
//        if (!imageDir.exists())
//            imageDir.mkdir();
//        
//        return imageDir;
//    }
//    
//    public static File getStationImageDir(HttpServletRequest request) {
//        File imageDir = new File(getImageDir(request), Constant.STATION_IMAGE_DIR);
//        
//        if (!imageDir.exists())
//            imageDir.mkdir();
//        
//        return imageDir;
//    }
//    
//    public static File getShopImageDir(HttpServletRequest request) {
//        File imageDir = new File(getImageDir(request), Constant.STATION_IMAGE_DIR);
//        
//        if (!imageDir.exists())
//            imageDir.mkdir();
//        
//        return imageDir;
//    }
//    
//    public static File getActivityImageDir(HttpServletRequest request) {
//        File imageDir = new File(getImageDir(request), Constant.ACTIVITY_IMAGE_DIR);
//        
//        if (!imageDir.exists())
//            imageDir.mkdir();
//        
//        return imageDir;
//    }
    
    public static boolean isLoginDeliver(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        
        if (session == null)
            return false;
        
        return session.getAttribute(Constant.SESSION_INFO.LOGIN_DELIVER) != null;
    }
    
    public static boolean isLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        
        if (session == null)
            return false;
        
        return session.getAttribute(Constant.SESSION_INFO.LOGIN_USER) != null;
    }
    
    public static void sendMessageResponse(HttpServletResponse response, String message) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        
        try {
            response.getOutputStream().write(message.getBytes("utf-8"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void sendMessageResponse(HttpServletResponse response) {
        sendMessageResponse(response, "success");
    }
}
