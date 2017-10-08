/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.util;

import java.security.MessageDigest;

public class Md5Util {
    public static String getMd5(String s) {
        if (CommonUtil.isEmptyStr(s))
            return "";
        
        MessageDigest messagedigest = null;
        byte abyte0[];
        int i;
        StringBuffer stringbuffer;
        try {
            messagedigest = MessageDigest.getInstance("MD5");
            messagedigest.reset();
            messagedigest.update(s.getBytes("UTF-8"));
        }
        // Misplaced declaration of an exception variable
        catch (Exception e) {
            e.printStackTrace();
        }

        abyte0 = messagedigest.digest();
        stringbuffer = new StringBuffer();
        i = 0;
        do {
            if (i >= abyte0.length)
                return stringbuffer.toString();
            if (Integer.toHexString(0xff & abyte0[i]).length() == 1)
                stringbuffer.append("0").append(Integer.toHexString(0xff & abyte0[i]));
            else
                stringbuffer.append(Integer.toHexString(0xff & abyte0[i]));
            i++;
        } while (true);
    }
}
