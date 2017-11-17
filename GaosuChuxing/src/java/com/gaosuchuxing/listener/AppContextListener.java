/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.listener;

import com.gaosuchuxing.common.Constant;
import com.gaosuchuxing.util.TaskSchedule;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {
    private static TaskSchedule mTaskSchedule;
            
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (mTaskSchedule == null)
            mTaskSchedule = new TaskSchedule();
        
        Constant.JdbcUrl = sce.getServletContext().getInitParameter("JdbcUrl");
        Constant.DbUser = sce.getServletContext().getInitParameter("DbUser");
        Constant.DbPwd = sce.getServletContext().getInitParameter("DbPwd");
        
        mTaskSchedule.startTask();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (mTaskSchedule != null)
            mTaskSchedule.stopTask();
        
        mTaskSchedule = null;
    }
    
    public static void refreshTaskSchedule() {
        if (mTaskSchedule != null)
            mTaskSchedule.refreshActiviyList();
    }
    
}
