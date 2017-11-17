/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.form;

import com.gaosuchuxing.util.CommonUtil;
import com.gaosuchuxing.util.Md5Util;

public class LoginForm {
    
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDigest() {
        if (CommonUtil.isEmptyStr(password))
            return "";
        else
            return Md5Util.getMd5(password);
    }

//    public void setDigest(String digest) {
//        this.digest = digest;
//    }

}
