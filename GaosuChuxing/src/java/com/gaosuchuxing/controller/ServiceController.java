/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.controller;

import com.gaosuchuxing.common.Constant;
import com.gaosuchuxing.delegate.WebDelegate;
import com.gaosuchuxing.domain.StationVO;
import com.gaosuchuxing.util.NumberUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
@RequestMapping("/service")
public class ServiceController extends MultiActionController {
    
    private WebDelegate webDelegate;

    public WebDelegate getWebDelegate() {
        return webDelegate;
    }

    public void setWebDelegate(WebDelegate webDelegate) {
        this.webDelegate = webDelegate;
    }
    
    @RequestMapping(value = "/getStationList") 
    public String getStationList(HttpServletRequest request, HttpServletResponse response) {
//        double lng = NumberUtil.strToDouble(request.getParameter("lng"));
//        double lat = NumberUtil.strToDouble(request.getParameter("lat"));
        String adcode = request.getParameter("adcode");
        boolean searchBeijing = NumberUtil.strToInt(request.getParameter("beijing")) > 0;
        
        if (searchBeijing)
            adcode = Constant.BEIJING_ADCODE;
        
        List<StationVO> stations = webDelegate.getStationList(null, -1, adcode, null, -1, -1, null, null);
        
        return null;
    }

} 
