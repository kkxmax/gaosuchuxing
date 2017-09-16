/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.controller;

import com.gaosuchuxing.common.Constant;
import com.gaosuchuxing.delegate.WebDelegate;
import com.gaosuchuxing.domain.DeliverVO;
import com.gaosuchuxing.domain.DistrictVO;
import com.gaosuchuxing.domain.FeedbackVO;
import com.gaosuchuxing.domain.ManagerVO;
import com.gaosuchuxing.domain.GroupVO;
import com.gaosuchuxing.domain.PermissionVO;
import com.gaosuchuxing.domain.RoleVO;
import com.gaosuchuxing.domain.StationVO;
import com.gaosuchuxing.domain.UserVO;
import com.gaosuchuxing.form.LoginForm;
import com.gaosuchuxing.form.PasswordForm;
import com.gaosuchuxing.form.search.DeliverSearchForm;
import com.gaosuchuxing.form.search.FeedbackSearchForm;
import com.gaosuchuxing.form.search.ManagerSearchForm;
import com.gaosuchuxing.form.search.StationSearchForm;
import com.gaosuchuxing.form.search.UserSearchForm;
import com.gaosuchuxing.util.CommonUtil;
import com.gaosuchuxing.util.DateUtil;
import com.gaosuchuxing.util.Md5Util;
import com.gaosuchuxing.util.NumberUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController extends MultiActionController {
    
    private WebDelegate webDelegate;

    /**
     * Get the value of webDelegate
     *
     * @return the value of webDelegate
     */
    public WebDelegate getWebDelegate() {
        return webDelegate;
    }

    /**
     * Set the value of webDelegate
     *
     * @param webDelegate new value of webDelegate
     */
    public void setWebDelegate(WebDelegate webDelegate) {
        this.webDelegate = webDelegate;
    }
    
    /*********************************
     * hok start
     *********************************/
    
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, LoginForm loginForm) {
        ModelAndView mav = new ModelAndView();
        
        if (request.getMethod().equals("GET")) {
            mav.setViewName("adminLoginLayout");
            mav.addObject("loginForm", loginForm);
        } else {
            String userId = loginForm.getUserId();
            HttpSession session = request.getSession(true);
            if (session != null) {
                session.removeAttribute(Constant.SESSION_INFO.LOGIN_USER);
            } else {
                mav.setViewName("adminLoginLayout");
                mav.addObject("loginForm", new LoginForm());
                return mav;
            }
            
            if (userId == null || userId.isEmpty()) {
                request.setAttribute(Constant.ATTRIBUTE.ERROR_MESSAGE, "账号不能为空");
                mav.setViewName("adminLoginLayout");
                mav.addObject("loginForm", loginForm);
                return mav;
            }

            try {
                ManagerVO loginUser = webDelegate.getManagerLogin(loginForm.getUserId());
                
                if (loginUser != null && loginUser.getPassword().equals(loginForm.getDigest())) {
                    
                    session.setAttribute(Constant.SESSION_INFO.LOGIN_USER, loginUser);
                    request.setAttribute("navTitle", "首页");
                    mav.setViewName("adminHomeLayout");
                    
                } else {
                    if (loginUser == null)
                        request.setAttribute(Constant.ATTRIBUTE.ERROR_MESSAGE, "该账号不存在");
                    else
                        request.setAttribute(Constant.ATTRIBUTE.ERROR_MESSAGE, "密码不正确");
                    
                    mav.setViewName("adminLoginLayout");
                    mav.addObject("loginForm", loginForm);
                    return mav;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute(Constant.ATTRIBUTE.ERROR_MESSAGE, "账号或密码有错误");
                mav.setViewName("adminLoginLayout");
                mav.addObject("loginForm", loginForm);
                return mav;
            }
        }
        
        return mav;
    }

    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "首页");
        return new ModelAndView("adminHomeLayout");
    } 
    
    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        request.getSession().removeAttribute(Constant.SESSION_INFO.LOGIN_USER);
        mav.setViewName("adminLoginLayout");
        mav.addObject("loginForm", new LoginForm());
        return mav;
    }
    
    public ModelAndView managerList(HttpServletRequest request, HttpServletResponse response, ManagerSearchForm searchForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "系统管理");
        request.setAttribute("subTitle", "运营人员管理");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminManagerLayout");
        
        searchForm.setKeyword(CommonUtil.charsetEncoding(searchForm.getKeyword()));
        
        List<GroupVO> groupList = webDelegate.getGroupList(null, null, null);
        
        mav.addObject("searchForm", searchForm);
        mav.addObject("groupList", groupList);
        
        return mav;
    }
    
    @RequestMapping(value="/getManagerList", method = RequestMethod.POST)
    public String getManagerList(HttpServletRequest request, HttpServletResponse response) {
        int draw  = NumberUtil.strToInt(request.getParameter("draw"));
        int offset = NumberUtil.strToInt(request.getParameter("start"));
        int limit = NumberUtil.strToInt(request.getParameter("length"));
        
        String sort = request.getParameter("order[0][dir]");
        int sortColumnId = NumberUtil.strToInt(request.getParameter("order[0][column]"));
        
        String sortColumn = "";
        if (sortColumnId > 1)
            sortColumn = Constant.MANAGER_COLUMNS[sortColumnId - 1];
        
        String keyword = request.getParameter("keyword");
        int groupId = NumberUtil.strToInt(request.getParameter("groupId"));
        
        int recordTotal = webDelegate.countAllManager(keyword, groupId);
        List<ManagerVO> managers = webDelegate.getManagerList(keyword, groupId, offset, limit, sortColumn, sort);
                
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("draw", draw);
        jsonResult.put("recordsTotal", recordTotal);
        jsonResult.put("recordsFiltered", recordTotal);
        JSONArray datas = new JSONArray();
        
        if (managers != null) {
            int num = offset;
            
            for (ManagerVO manager: managers) {
                JSONObject item = new JSONObject();
                item.put("DT_RowId", manager.getId());
                item.put("0", ++num);
                item.put("1", manager.getManagerId());
                item.put("2", manager.getName());
                item.put("3", manager.getGroupName());
                item.put("4", DateUtil.formatDateTime(manager.getRegDate()));
                String tag = "<a href='managerForm/" + manager.getId() + "' class='btn btn-xs blue'> 编辑 <i class='fa fa-edit'></i></a> ";
                tag += "<a href='javascript:;' onclick=onDeleteManager('" + manager.getId() + "') class='btn btn-xs grey-cascade'> 删除 <i class='fa fa-trash-o'></i></a> ";
                tag += "<a href='javascript:;' onclick=onChangePassword('" + manager.getId() + "') class='btn btn-xs purple'> 重置密码 <i class='fa fa-key'></i></a>";
                item.put("5", tag);
                datas.add(item);
            }
        }
        
        jsonResult.put("data", datas);
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            jsonResult.writeJSONString(response.getWriter());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @RequestMapping(value = "/validateManagerId")
    public String validateManagerId(HttpServletRequest request, HttpServletResponse response) {
        String managerId = request.getParameter("managerId");
        
        try {
            ManagerVO manager = webDelegate.getManagerLogin(managerId);
            if (manager == null)
                response.getOutputStream().write("success".getBytes());
            else
                response.getOutputStream().write("error".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "/deleteManager")
    public String deleteManager(HttpServletRequest request, HttpServletResponse response) {
        int managerId = NumberUtil.strToInt(request.getParameter("managerId"));
        
        try {
            int count = webDelegate.countAllManager(null, -1);
            if (count > 1) {
                webDelegate.deleteManager(managerId);
                response.getOutputStream().write("success".getBytes());
            } else {
                response.getOutputStream().write("error".getBytes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "/changeManagerPassword")
    public String changeManagerPassword(HttpServletRequest request, HttpServletResponse response) {
        int managerId = NumberUtil.strToInt(request.getParameter("managerId"));
        String password = request.getParameter("password");
        try {
            webDelegate.setPassword(managerId, Md5Util.getMd5(password));
            response.getOutputStream().write("success".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping (value="/managerForm/{id}")
    public ModelAndView managerForm(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response, ManagerVO managerForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
//        if (!CommonUtil.checkAdminPermission(request)) {
//            return new ModelAndView("redirect:/admin/logout");
//        }
        
        request.setAttribute("navTitle", "系统管理");
        request.setAttribute("subTitle", "运营人员管理");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminManagerFormLayout");
        
        List<GroupVO> groupList = webDelegate.getGroupList(null, null, null);
        mav.addObject("groupList", groupList);
        
        if (request.getMethod().equals("GET")) {
            String url = "0";
            String title = "新增运营人员";
            String small = "";        
            
            if (!id.equals("0")) {
                url = id;
                managerForm = webDelegate.getManager(NumberUtil.strToInt(id));
                title = managerForm.getManagerId();
                small = "编辑运营人员";        
            }
            
            mav.addObject("managerForm", managerForm);
            mav.addObject("url", url);
            mav.addObject("title", title);
            mav.addObject("small", small);
            
        } else {
            managerForm.setManagerId(CommonUtil.charsetEncoding(managerForm.getManagerId()));
            managerForm.setName(CommonUtil.charsetEncoding(managerForm.getName()));
            
            if (!CommonUtil.isEmptyStr(managerForm.getPassword()))
                managerForm.setPassword(Md5Util.getMd5(managerForm.getPassword()));
                        
            if (id.equals("0"))
                webDelegate.addNewManager(managerForm);
            else
                webDelegate.updateManager(managerForm);
            
            return new ModelAndView("redirect:/admin/managerList");
        }
        
        return mav;
    }
    
    public ModelAndView groupList(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "系统管理");
        request.setAttribute("subTitle", "角色管理");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminGroupLayout");
        
        List<PermissionVO> permissionList = webDelegate.getPermissionList();
        List<GroupVO> groupList = webDelegate.getGroupList(null, null, null);
        
        mav.addObject("permissionList", permissionList);
        mav.addObject("groupList", groupList);
        
        return mav;
    }
    
    @RequestMapping(value="/getGroupList", method = RequestMethod.POST)
    public String getGroupList(HttpServletRequest request, HttpServletResponse response) {
        int draw  = NumberUtil.strToInt(request.getParameter("draw"));
        int offset = NumberUtil.strToInt(request.getParameter("start"));
        int limit = NumberUtil.strToInt(request.getParameter("length"));
        
        String sort = request.getParameter("order[0][dir]");
        int sortColumnId = NumberUtil.strToInt(request.getParameter("order[0][column]"));
        
        String sortColumn = "";
        if (sortColumnId > 1)
            sortColumn = Constant.GROUP_COLUMNS[sortColumnId - 1];
        
//        String keyword = request.getParameter("keyword");
//        int groupId = NumberUtil.strToInt(request.getParameter("groupId"));
                
        List<GroupVO> groups = webDelegate.getGroupList(null, sortColumn, sort);
        int recordTotal = (groups != null)? groups.size(): 0;
                
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("draw", draw);
        jsonResult.put("recordsTotal", recordTotal);
        jsonResult.put("recordsFiltered", recordTotal);
        JSONArray datas = new JSONArray();
        
        if (groups != null) {
            int num = 0;
            
            for (GroupVO group: groups) {
                JSONObject item = new JSONObject();
                item.put("DT_RowId", group.getId());
                item.put("0", ++num);
                item.put("1", group.getName());                
                item.put("2", "<a href=\"javascript:;\" class='btn btn-xs blue' onclick=\"onSetRole('" + group.getId() + "')\"> 操作设置 <i class='fa fa-edit'></i></a>");
                item.put("3", "<a href='javascript:;' onclick=\"onDeleteGroup('" + group.getId() + "')\" class='btn btn-xs grey-cascade'> 删除 <i class='fa fa-trash-o'></i></a>");
                datas.add(item);
            }
        }
        
        jsonResult.put("data", datas);
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            jsonResult.writeJSONString(response.getWriter());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @RequestMapping(value = "/deleteGroup")
    public String deleteGroup(HttpServletRequest request, HttpServletResponse response) {
        int groupId = NumberUtil.strToInt(request.getParameter("groupId"));
        
        try {
            if (webDelegate.deleteGroup(groupId)) {
                response.getOutputStream().write("success".getBytes());
            } else {
                response.getOutputStream().write("error".getBytes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "/setRole")
    public String setRole(HttpServletRequest request, HttpServletResponse response) {
        int groupId = NumberUtil.strToInt(request.getParameter("groupId"));
        
        Map<String,String[]> map = request.getParameterMap();
        
        List<PermissionVO> permissions = webDelegate.getPermissionList();
        
        ArrayList<RoleVO> roles = new ArrayList<RoleVO>();
        
        for (PermissionVO permission: permissions) {
            String key = "permission_" + permission.getId();
            if (map.containsKey(key)) {
                RoleVO role = new RoleVO();
                role.setPermissionId(permission.getId());
                boolean status = Boolean.valueOf(request.getParameter(key));
                role.setStatus(status);
                roles.add(role);
            }
        }   
        
        try {
            webDelegate.setRole(groupId, roles);
            response.getOutputStream().write("success".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "/getRole")
    public String getRole(HttpServletRequest request, HttpServletResponse response) {
        int groupId = NumberUtil.strToInt(request.getParameter("groupId"));
        
        List<RoleVO> roles = webDelegate.getRoleList(groupId);
        
        JSONObject jsonObj = new JSONObject();        
        JSONArray items = new JSONArray();
        
        for (RoleVO role: roles) {
            JSONObject item = new JSONObject();
            item.put("permissionId", role.getPermissionId());
            item.put("status", role.getStatus());
            item.put("permissionName", role.getPermissionName());
            item.put("parentId", role.getParentId());
            items.add(item);
        }
        
        jsonObj.put("roles", items);
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            jsonObj.writeJSONString(response.getWriter());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @RequestMapping(value = "/addNewGroup")
    public String addNewGroup(HttpServletRequest request, HttpServletResponse response) {
        String groupName = request.getParameter("groupName");
        GroupVO newGroup = new GroupVO();
        newGroup.setName(groupName);
        try {
            if (webDelegate.addNewGroup(newGroup)) {
                response.getOutputStream().write("success".getBytes());
            } else {
                response.getOutputStream().write("error".getBytes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping (value="/passwordForm")
    public ModelAndView passwordForm(HttpServletRequest request, HttpServletResponse response, PasswordForm passwordForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
                
//        if (!CommonUtil.checkAdminPermission(request)) {
//            return new ModelAndView("redirect:/admin/logout");
//        }

        HttpSession session = request.getSession(false);
        ManagerVO loginManager = (ManagerVO) session.getAttribute(Constant.SESSION_INFO.LOGIN_USER);
        
        request.setAttribute("navTitle", "系统管理");
        request.setAttribute("subTitle", "修改密码");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminPasswordFormLayout");
        
        if (request.getMethod().equals("GET")) {        
            passwordForm.setManagerId(loginManager.getId());
            mav.addObject("passwordForm", passwordForm);            
        } else {
            String digest = Md5Util.getMd5(passwordForm.getNewPassword());
            
            webDelegate.setPassword(passwordForm.getManagerId(), digest);
            
            return new ModelAndView("redirect:/admin/passwordForm");
        }
        
        return mav;
    }
    
    @RequestMapping(value = "/validatePassword")
    public String validatePassword(HttpServletRequest request, HttpServletResponse response) {
        String password = request.getParameter("currentPassword");
        int managerId = NumberUtil.strToInt(request.getParameter("managerId"));
        
        ManagerVO manager = webDelegate.getManager(managerId);
        
        try {
            
            if (manager != null && manager.getPassword().equals(password)) {
                response.getOutputStream().write("success".getBytes());
            } else {
                response.getOutputStream().write("error".getBytes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public ModelAndView stationList(HttpServletRequest request, HttpServletResponse response, StationSearchForm searchForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "服务区管理");
        request.setAttribute("subTitle", "");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminStationLayout");
        
        searchForm.setKeyword(CommonUtil.charsetEncoding(searchForm.getKeyword()));
        searchForm.setStatus(CommonUtil.charsetEncoding(searchForm.getStatus()));
        
        List<DistrictVO> parentDistrictList = webDelegate.getDistrictList(-1, true);
        
        mav.addObject("searchForm", searchForm);
        mav.addObject("parentDistrictList", parentDistrictList);
        
        if (!CommonUtil.isEmptyStr(searchForm.getDistrictParentId())) {
            List<DistrictVO> districtList = webDelegate.getDistrictList(NumberUtil.strToInt(searchForm.getDistrictParentId()), false);
            mav.addObject("districtList", districtList);
        }
        
        return mav;
    }
    
    @RequestMapping(value="/getStationList", method = RequestMethod.POST)
    public String getStationList(HttpServletRequest request, HttpServletResponse response) {
        int draw  = NumberUtil.strToInt(request.getParameter("draw"));
        int offset = NumberUtil.strToInt(request.getParameter("start"));
        int limit = NumberUtil.strToInt(request.getParameter("length"));
        
        String sort = request.getParameter("order[0][dir]");
        int sortColumnId = NumberUtil.strToInt(request.getParameter("order[0][column]"));
        
        String sortColumn = "";
        if (sortColumnId > 2)
            sortColumn = Constant.STATION_COLUMNS[sortColumnId - 2];
        else if (sortColumnId > 1)
            sortColumn = Constant.STATION_COLUMNS[sortColumnId - 1];
        
        String keyword = request.getParameter("keyword");
        int districtId = NumberUtil.strToInt(request.getParameter("districtId"));
        String status = request.getParameter("status");
        
        int recordTotal = webDelegate.countAllStation(keyword, districtId, status);
        List<StationVO> stations = webDelegate.getStationList(keyword, districtId, status, offset, limit, sortColumn, sort);
                
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("draw", draw);
        jsonResult.put("recordsTotal", recordTotal);
        jsonResult.put("recordsFiltered", recordTotal);
        JSONArray datas = new JSONArray();
        
        if (stations != null) {
            int num = offset;
            
            for (StationVO station: stations) {
                JSONObject item = new JSONObject();
                item.put("DT_RowId", station.getId());
                item.put("0", ++num);
                item.put("1", station.getName());
                item.put("2", "<img style='height: 40px; max-width: 90px;' src='/" + station.getImagePath() + "'></img>");
                item.put("3", station.getDistrictName());
                item.put("4", station.getDeliverName());
                item.put("5", station.getStatus());
                item.put("6", DateUtil.formatDateTime(station.getRegDate()));
                String tag = "<a href='stationShow/" + station.getId() + "' onclick=onChangePassword('" + station.getId() + "') class='btn btn-xs purple'> 查看 <i class='fa fa-file-text'></i></a>";
                tag += "<a href='stationForm/" + station.getId() + "' class='btn btn-xs blue'> 编辑 <i class='fa fa-edit'></i></a>";
                tag += "<a href='javascript:;' onclick=onDeleteStation('" + station.getId() + "') class='btn btn-xs grey-cascade'> 删除 <i class='fa fa-trash-o'></i></a>";
                
                item.put("7", tag);
                datas.add(item);
            }
        }
        
        jsonResult.put("data", datas);
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            jsonResult.writeJSONString(response.getWriter());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @RequestMapping(value = "/validateStationName")
    public String validateStationName(HttpServletRequest request, HttpServletResponse response) {
        int stationId = NumberUtil.strToInt(request.getParameter("stationId"));
        String stationName = request.getParameter("stationName");
        
        try {
            StationVO station = webDelegate.getStationByName(stationName);
            if (station == null || station.getId() == stationId)
                response.getOutputStream().write("success".getBytes());
            else
                response.getOutputStream().write("error".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
//    @RequestMapping(value = "/validateStationFile", method = RequestMethod.POST)
//    public String validateStationFile(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
//        String fileName = CommonUtil.charsetEncoding(file.getOriginalFilename());
//        String ext = FilenameUtils.getExtension(fileName);
//        
//        try {
//            if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("png")) {
//                if (file.getSize() > 1024 * 1204 * 1024) {
//                    response.getOutputStream().write("invalidSize".getBytes());
//                } else {
//                    response.getOutputStream().write("success".getBytes());
//                }
//            } else {
//                response.getOutputStream().write("invalidFile".getBytes());
//            }    
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
    
    @RequestMapping(value = "/deleteStation")
    public String deleteStation(HttpServletRequest request, HttpServletResponse response) {
        int stationId = NumberUtil.strToInt(request.getParameter("stationId"));
        
        try {
            if (webDelegate.deleteStation(stationId)) {
                response.getOutputStream().write("success".getBytes());
            } else {
                response.getOutputStream().write("error".getBytes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "/getDistrictList")
    public ModelAndView getDistrictList(HttpServletRequest request, HttpServletResponse response) {
        int parentId = NumberUtil.strToInt(request.getParameter("parentId"));        
        List<DistrictVO> districts = webDelegate.getDistrictList(parentId, false);
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("districtListLayout");
        
        mav.addObject("districts", districts);
        
        return mav;
    }
    
    @RequestMapping (value="/stationForm/{id}")
    public ModelAndView stationForm(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response, StationVO stationForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
//        if (!CommonUtil.checkAdminPermission(request)) {
//            return new ModelAndView("redirect:/admin/logout");
//        }
        
        request.setAttribute("navTitle", "服务区管理");
        request.setAttribute("subTitle", "");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminStationFormLayout");
        
        String url = "0";
        String title = "新增服务区";
        String small = "";        

        if (!id.equals("0")) {
            url = id;
            stationForm = webDelegate.getStation(NumberUtil.strToInt(id));
            title = stationForm.getName();
            small = "编辑服务区";        
            List<DistrictVO> districtList = webDelegate.getDistrictList(stationForm.getDistrictParentId(), false);
            mav.addObject("districtList", districtList);
        }

        mav.addObject("stationForm", stationForm);
        mav.addObject("url", url);
        mav.addObject("title", title);
        mav.addObject("small", small);
        
        List<DistrictVO> parentDistrictList = webDelegate.getDistrictList(-1, true);
        mav.addObject("parentDistrictList", parentDistrictList);
        
        List<DeliverVO> deliverList = webDelegate.getDeliverList(null, -1, -1, null, null);
        mav.addObject("deliverList", deliverList);
        
        return mav;
    }
    
    @RequestMapping (value="/stationShow/{id}")
    public ModelAndView stationShow(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response, StationVO stationForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
//        if (!CommonUtil.checkAdminPermission(request)) {
//            return new ModelAndView("redirect:/admin/logout");
//        }
        
        request.setAttribute("navTitle", "服务区管理");
        request.setAttribute("subTitle", "");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminStationShowLayout");
        
        stationForm = webDelegate.getStation(NumberUtil.strToInt(id));
        String title = stationForm.getName();
        String small = "服务区详情";        
        List<DistrictVO> districtList = webDelegate.getDistrictList(stationForm.getDistrictParentId(), false);
        mav.addObject("districtList", districtList);

        mav.addObject("stationForm", stationForm);
        mav.addObject("title", title);
        mav.addObject("small", small);
        
        List<DistrictVO> parentDistrictList = webDelegate.getDistrictList(-1, true);
        mav.addObject("parentDistrictList", parentDistrictList);
        
        List<DeliverVO> deliverList = webDelegate.getDeliverList(null, -1, -1, null, null);
        mav.addObject("deliverList", deliverList);
        
        return mav;
    }
    
    @RequestMapping (value="/stationFormPost/{id}", method = RequestMethod.POST)
    public ModelAndView stationFormPost(@PathVariable("id") String id, @RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response, StationVO stationForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }       
        
        stationForm.setName(CommonUtil.charsetEncoding(stationForm.getName()));
        stationForm.setStatus(CommonUtil.charsetEncoding(stationForm.getStatus()));
                
        if (file != null && !file.getOriginalFilename().isEmpty() && file.getSize() > 0) {
            String fileName = CommonUtil.charsetEncoding(file.getOriginalFilename());
            String ext = FilenameUtils.getExtension(fileName);
            String uploadName = "station_" + System.currentTimeMillis() + "." + ext;
            FileOutputStream out = null;
            try {                    
                File uploadFile = new File(CommonUtil.getStationImageDir(request), uploadName);
                out = new FileOutputStream(uploadFile);
                out.write(file.getBytes());
                out.flush();
                stationForm.setImagePath(Constant.IMAGE_DIR + "/" + Constant.STATION_IMAGE_DIR + "/" + uploadName);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
                        
        if (id.equals("0"))
            webDelegate.addNewStation(stationForm);
        else
            webDelegate.updateStation(stationForm);

        return new ModelAndView("redirect:/admin/stationList");
    }
    
    
    
    /*********************************
     * jiw start
     *********************************/
    
    public ModelAndView feedbackList(HttpServletRequest request, HttpServletResponse response, FeedbackSearchForm searchForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "意见反馈");
//        request.setAttribute("subTitle", "");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminFeedbackLayout");
        
        searchForm.setKeyword(CommonUtil.charsetEncoding(searchForm.getKeyword()));
        searchForm.setStatus(CommonUtil.charsetEncoding(searchForm.getStatus()));
        
        mav.addObject("searchForm", searchForm);
        
        return mav;
    }
    
    @RequestMapping(value="/getFeedbackList", method = RequestMethod.POST)
    public String getFeedbackList(HttpServletRequest request, HttpServletResponse response) {
        int draw  = NumberUtil.strToInt(request.getParameter("draw"));
        int offset = NumberUtil.strToInt(request.getParameter("start"));
        int limit = NumberUtil.strToInt(request.getParameter("length"));
        
        String sort = request.getParameter("order[0][dir]");
        int sortColumnId = NumberUtil.strToInt(request.getParameter("order[0][column]"));
        
        String sortColumn = "";
        sortColumn = Constant.FEEDBACK_COLUMNS[sortColumnId];
        
        String keyword = request.getParameter("keyword");
        String status = request.getParameter("status");
        
        int recordTotal = webDelegate.countAllFeedback(keyword, status);
        List<FeedbackVO> feedbacks = webDelegate.getFeedbackList(keyword, status, offset, limit, sortColumn, sort);
                
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("draw", draw);
        jsonResult.put("recordsTotal", recordTotal);
        jsonResult.put("recordsFiltered", recordTotal);
        JSONArray datas = new JSONArray();
        
        if (feedbacks != null) {            
            for (FeedbackVO feedback: feedbacks) {
                JSONObject item = new JSONObject();
                item.put("DT_RowId", feedback.getId());
                item.put("0", feedback.getUserId());
                item.put("1", feedback.getComment());
                item.put("2", DateUtil.formatDateTime(feedback.getRegDate()));
                item.put("3", feedback.getStatus());                
                String tag = "";
                if (!feedback.getStatus().equals("已处理")) {
                    tag = "<a href='javascript:;' onclick=onProcessFeedback('" + feedback.getId() + "') class='btn btn-xs blue'> 处理 <i class='fa fa-check'></i></a>";
                }
                item.put("4", tag);
                datas.add(item);
            }
        }
        
        jsonResult.put("data", datas);
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            jsonResult.writeJSONString(response.getWriter());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @RequestMapping(value="/setFeedbackStatus", method = RequestMethod.POST)
    public String setFeedbackStatus(HttpServletRequest request, HttpServletResponse response) {
        int feedbackId = NumberUtil.strToInt(request.getParameter("feedbackId"));
        try {
            webDelegate.setFeedbackStatus(feedbackId);
            response.getOutputStream().write("success".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "/validateDeliverId")
    public String validateDeliverId(HttpServletRequest request, HttpServletResponse response) {
        String deliverId = request.getParameter("deliverId");
        
        try {
            DeliverVO deliver = webDelegate.getDeliver(deliverId);
            if (deliver == null)
                response.getOutputStream().write("success".getBytes());
            else
                response.getOutputStream().write("error".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public ModelAndView deliverList(HttpServletRequest request, HttpServletResponse response, DeliverSearchForm searchForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "配送员管理");
//        request.setAttribute("subTitle", "");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminDeliverLayout");
        
        searchForm.setKeyword(CommonUtil.charsetEncoding(searchForm.getKeyword()));
        
        mav.addObject("searchForm", searchForm);
        
        return mav;
    }
    
    @RequestMapping(value="/getDeliverList", method = RequestMethod.POST)
    public String getDeliverList(HttpServletRequest request, HttpServletResponse response) {
        int draw  = NumberUtil.strToInt(request.getParameter("draw"));
        int offset = NumberUtil.strToInt(request.getParameter("start"));
        int limit = NumberUtil.strToInt(request.getParameter("length"));
        
        String sort = request.getParameter("order[0][dir]");
        int sortColumnId = NumberUtil.strToInt(request.getParameter("order[0][column]"));
        
        String sortColumn = "";
        sortColumn = Constant.DELIVER_COLUMNS[sortColumnId - 1];
        
        String keyword = request.getParameter("keyword");
        
        int recordTotal = webDelegate.countAllDeliver(keyword);
        List<DeliverVO> delivers = webDelegate.getDeliverList(keyword, offset, limit, sortColumn, sort);
                
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("draw", draw);
        jsonResult.put("recordsTotal", recordTotal);
        jsonResult.put("recordsFiltered", recordTotal);
        JSONArray datas = new JSONArray();
        
        if (delivers != null) {            
            int num = offset;
            for (DeliverVO deliver: delivers) {
                JSONObject item = new JSONObject();
                item.put("DT_RowId", deliver.getId());
                item.put("0", ++num);
                item.put("1", deliver.getDeliverId());
                item.put("2", deliver.getName());
                item.put("3", deliver.getTelNo());
                item.put("4", DateUtil.formatDateTime(deliver.getRegDate()));                
                String tag = "<a href='deliverForm/" + deliver.getId() + "' class='btn btn-xs blue'> 编辑 <i class='fa fa-edit'></i></a> ";
                tag += "<a href='javascript:;' onclick=onDeleteDeliver('" + deliver.getDeliverId()+ "') class='btn btn-xs grey-cascade'> 删除 <i class='fa fa-trash-o'></i></a> ";
                tag += "<a href='javascript:;' onclick=onChangePassword('" + deliver.getDeliverId()+ "') class='btn btn-xs purple'> 重置密码 <i class='fa fa-key'></i></a>";
                item.put("5", tag);
                datas.add(item);
            }
        }
        
        jsonResult.put("data", datas);
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            jsonResult.writeJSONString(response.getWriter());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @RequestMapping(value = "/changeDeliverPassword")
    public String changeDeliverPassword(HttpServletRequest request, HttpServletResponse response) {
        String deliverId = request.getParameter("deliverId");
        String password = request.getParameter("password");
        try {
            webDelegate.setDeliverPassword(deliverId, Md5Util.getMd5(password));
            response.getOutputStream().write("success".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "/deleteDeliver")
    public String deleteDeliver(HttpServletRequest request, HttpServletResponse response) {
        String deliverId = request.getParameter("deliverId");
        
        try {
            int count = webDelegate.countAllDeliver(null);
            if (count > 1) {
                webDelegate.deleteDeliver(deliverId);
                response.getOutputStream().write("success".getBytes());
            } else {
                response.getOutputStream().write("error".getBytes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping (value="/deliverForm/{id}")
    public ModelAndView deliverForm(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response, DeliverVO deliverForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
//        if (!CommonUtil.checkAdminPermission(request)) {
//            return new ModelAndView("redirect:/admin/logout");
//        }
        
        request.setAttribute("navTitle", "配送员管理");
//        request.setAttribute("subTitle", "运营人员管理");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminDeliverFormLayout");
        
        if (request.getMethod().equals("GET")) {
            String url = "0";
            String title = "新增配送员";
            String small = "";        
            
            if (!id.equals("0")) {
                url = id;
                deliverForm = webDelegate.getDeliverById(id);
                title = deliverForm.getDeliverId();
                small = "编辑运营人员";        
            }
            
            mav.addObject("deliverForm", deliverForm);
            mav.addObject("url", url);
            mav.addObject("title", title);
            mav.addObject("small", small);
            
        } else {
            deliverForm.setDeliverId(CommonUtil.charsetEncoding(deliverForm.getDeliverId()));
            deliverForm.setName(CommonUtil.charsetEncoding(deliverForm.getName()));
            
            if (!CommonUtil.isEmptyStr(deliverForm.getPassword()))
                deliverForm.setPassword(Md5Util.getMd5(deliverForm.getPassword()));
                        
            if (id.equals("0"))
                webDelegate.addNewDeliver(deliverForm);
            else
                webDelegate.updateDeliver(deliverForm);
            
            return new ModelAndView("redirect:/admin/deliverList");
        }
        
        return mav;
    }
    
    public ModelAndView userList(HttpServletRequest request, HttpServletResponse response, UserSearchForm searchForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "用户管理");
//        request.setAttribute("subTitle", "");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminUserLayout");
        
        searchForm.setKeyword(CommonUtil.charsetEncoding(searchForm.getKeyword()));
        
        mav.addObject("searchForm", searchForm);
        
        return mav;
    }
    
    @RequestMapping(value="/getUserList", method = RequestMethod.POST)
    public String getUserList(HttpServletRequest request, HttpServletResponse response) {
        int draw  = NumberUtil.strToInt(request.getParameter("draw"));
        int offset = NumberUtil.strToInt(request.getParameter("start"));
        int limit = NumberUtil.strToInt(request.getParameter("length"));
        
        String sort = request.getParameter("order[0][dir]");
        int sortColumnId = NumberUtil.strToInt(request.getParameter("order[0][column]"));
        
        String sortColumn = "";
        
        if (sortColumnId > 2)
            sortColumn = Constant.USER_COLUMNS[sortColumnId - 1];
        else 
            sortColumn = Constant.USER_COLUMNS[sortColumnId];
        
        String keyword = request.getParameter("keyword");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        
        int recordTotal = webDelegate.countAllUser(keyword, from, to);
        List<UserVO> users = webDelegate.getUserList(keyword, from, to, offset, limit, sortColumn, sort);
                
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("draw", draw);
        jsonResult.put("recordsTotal", recordTotal);
        jsonResult.put("recordsFiltered", recordTotal);
        JSONArray datas = new JSONArray();
        
        if (users != null) {            
            for (UserVO user: users) {
                JSONObject item = new JSONObject();
                item.put("DT_RowId", user.getId());
                item.put("0", user.getUserId());
                item.put("1", user.getName());
                item.put("2", user.getAvatarPath());                                
                item.put("3", user.getOrderCount());
                item.put("4", user.getOrderAmount());
                
                datas.add(item);
            }
        }
        
        jsonResult.put("data", datas);
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            jsonResult.writeJSONString(response.getWriter());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
}
