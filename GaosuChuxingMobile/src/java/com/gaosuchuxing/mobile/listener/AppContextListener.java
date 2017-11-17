/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.mobile.listener;

import com.gaosuchuxing.mobile.common.Constant;
import com.gaosuchuxing.mobile.util.CommonUtil;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String flag  = sce.getServletContext().getInitParameter("NoOrderTime");
        if (!CommonUtil.isEmptyStr(flag))
            Constant.NO_ORDER_TIME = Constant.NO_ORDER_TIME || Integer.valueOf(flag) > 0;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
    
}
