/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.controller;

import com.gaosuchuxing.common.Constant;
import com.gaosuchuxing.delegate.WebDelegate;
import com.gaosuchuxing.domain.ActivityDetailVO;
import com.gaosuchuxing.domain.ActivityNoticeVO;
import com.gaosuchuxing.domain.ActivityVO;
import com.gaosuchuxing.domain.DeliverVO;
import com.gaosuchuxing.domain.DistrictVO;
import com.gaosuchuxing.domain.FeedbackVO;
import com.gaosuchuxing.domain.GoodsKindVO;
import com.gaosuchuxing.domain.GoodsVO;
import com.gaosuchuxing.domain.ManagerVO;
import com.gaosuchuxing.domain.GroupVO;
import com.gaosuchuxing.domain.OrderCouponVO;
import com.gaosuchuxing.domain.OrderDetailVO;
import com.gaosuchuxing.domain.OrderVO;
import com.gaosuchuxing.domain.PermissionVO;
import com.gaosuchuxing.domain.RoleVO;
import com.gaosuchuxing.domain.ShopKindVO;
import com.gaosuchuxing.domain.ShopVO;
import com.gaosuchuxing.domain.StationVO;
import com.gaosuchuxing.domain.UserCouponVO;
import com.gaosuchuxing.domain.UserVO;
import com.gaosuchuxing.form.LoginForm;
import com.gaosuchuxing.form.PasswordForm;
import com.gaosuchuxing.form.search.ActivitySearchForm;
import com.gaosuchuxing.form.search.DeliverSearchForm;
import com.gaosuchuxing.form.search.FeedbackSearchForm;
import com.gaosuchuxing.form.search.GoodsKindSearchForm;
import com.gaosuchuxing.form.search.GoodsSearchForm;
import com.gaosuchuxing.form.search.ManagerSearchForm;
import com.gaosuchuxing.form.search.OrderSearchForm;
import com.gaosuchuxing.form.search.ShopSearchForm;
import com.gaosuchuxing.form.search.StationSearchForm;
import com.gaosuchuxing.form.search.UserCouponSearchForm;
import com.gaosuchuxing.form.search.UserSearchForm;
import com.gaosuchuxing.util.CommonUtil;
import com.gaosuchuxing.util.DateUtil;
import com.gaosuchuxing.util.Md5Util;
import com.gaosuchuxing.util.NumberUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import static java.util.Date.from;
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
                String tag = "<a href='stationShow/" + station.getId() + "' class='btn btn-xs purple'> 查看 <i class='fa fa-file-text'></i></a>";
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
    
    @RequestMapping (value="/shopList/{type}")
    public ModelAndView shopList(@PathVariable("type") String type, HttpServletRequest request, HttpServletResponse response, ShopSearchForm searchForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        
        request.setAttribute("navTitle", "门店管理");
        if (type.equals(Constant.SHOP_KIND.COMPANY)) {
            request.setAttribute("subTitle", "公司门店");
            request.setAttribute("title", "公司门店列表");
        } else {
            request.setAttribute("subTitle", "服务区门店");
            request.setAttribute("title", "服务区门店列表");
        }
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminShopLayout");
        
        searchForm.setKeyword(CommonUtil.charsetEncoding(searchForm.getKeyword()));
        
        List<DistrictVO> parentDistrictList = webDelegate.getDistrictList(-1, true);
        
        mav.addObject("searchForm", searchForm);
        mav.addObject("parentDistrictList", parentDistrictList);
        
        if (!CommonUtil.isEmptyStr(searchForm.getDistrictParentId())) {
            List<DistrictVO> districtList = webDelegate.getDistrictList(NumberUtil.strToInt(searchForm.getDistrictParentId()), false);
            mav.addObject("districtList", districtList);
        }
        
        List<StationVO> stationList = webDelegate.getStationList(null, -1, null, -1, -1, null, null);
        mav.addObject("stationList", stationList);
        
        List<ShopKindVO> shopKindList = webDelegate.getShopKindList();
        mav.addObject("shopKindList", shopKindList);
        
        mav.addObject("type", type);
        
        return mav;
    }
    
    @RequestMapping(value="/getShopList", method = RequestMethod.POST)
    public String getShopList(HttpServletRequest request, HttpServletResponse response) {
        int draw  = NumberUtil.strToInt(request.getParameter("draw"));
        int offset = NumberUtil.strToInt(request.getParameter("start"));
        int limit = NumberUtil.strToInt(request.getParameter("length"));
        
        String type = request.getParameter("type");
        
        String sort = request.getParameter("order[0][dir]");
        int sortColumnId = NumberUtil.strToInt(request.getParameter("order[0][column]"));
        
        String sortColumn = "";
        if (sortColumnId > 0) {
            if (type.equals(Constant.SHOP_KIND.COMPANY))
                sortColumn = Constant.COMPANY_SHOP_COLUMNS[sortColumnId - 1];
            else
                sortColumn = Constant.SERVICE_SHOP_COLUMNS[sortColumnId - 1];
        }
        String keyword = request.getParameter("keyword");
        int districtId = NumberUtil.strToInt(request.getParameter("districtId"));
        int shopKindId = NumberUtil.strToInt(request.getParameter("shopKindId"));
        int stationId = NumberUtil.strToInt(request.getParameter("stationId"));
        
        int recordTotal = webDelegate.countAllShop(type, keyword, stationId, shopKindId, districtId);
        List<ShopVO> shops = webDelegate.getShopList(type, keyword, stationId, shopKindId, districtId, offset, limit, sortColumn, sort);
                
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("draw", draw);
        jsonResult.put("recordsTotal", recordTotal);
        jsonResult.put("recordsFiltered", recordTotal);
        JSONArray datas = new JSONArray();
        
        if (shops != null) {
            int num = offset;
            
            for (ShopVO shop: shops) {
                JSONObject item = new JSONObject();
                item.put("DT_RowId", shop.getId());
                item.put("0", ++num);
                item.put("1", shop.getName());
                item.put("2", shop.getShopKindName());
                item.put("3", shop.getStationName());
                item.put("4", shop.getDistrictName());
                                
                if (type.equals(Constant.SHOP_KIND.COMPANY)) {
                    item.put("5", shop.getStartFee());
                    item.put("6", shop.getShippingFee());
                    item.put("7", DateUtil.formatDateTime(shop.getRegDate()));
                    String tag = "<a href='" + request.getContextPath() + "/admin/shopShow/" + shop.getId() + "' class='btn btn-xs purple'> 查看 <i class='fa fa-file-text'></i></a>";
                    tag += "<a href='" + request.getContextPath() + "/admin/shopForm/" + shop.getId() + "' class='btn btn-xs blue'> 编辑 <i class='fa fa-edit'></i></a>";
                    tag += "<a href='javascript:;' onclick=onDeleteShop('" + shop.getId() + "') class='btn btn-xs grey-cascade'> 删除 <i class='fa fa-trash-o'></i></a>";                
                    item.put("8", tag);
                } else {
                    item.put("5", DateUtil.formatDateTime(shop.getRegDate()));
                    String tag = "<a href='" + request.getContextPath() + "/admin/shopShow/" + shop.getId() + "' class='btn btn-xs purple'> 查看 <i class='fa fa-file-text'></i></a>";
                    tag += "<a href='" + request.getContextPath() + "/admin/shopForm/" + shop.getId() + "' class='btn btn-xs blue'> 编辑 <i class='fa fa-edit'></i></a>";
                    tag += "<a href='javascript:;' onclick=onDeleteShop('" + shop.getId() + "') class='btn btn-xs grey-cascade'> 删除 <i class='fa fa-trash-o'></i></a>";
                    item.put("6", tag);
                }
                
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
    
    @RequestMapping(value = "/validateShopName")
    public String validateShopName(HttpServletRequest request, HttpServletResponse response) {
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));
        String shopName = request.getParameter("shopName");
        
        try {
            ShopVO shop = webDelegate.getShopByName(shopName);
            if (shop == null || shop.getId() == shopId)
                response.getOutputStream().write("success".getBytes());
            else
                response.getOutputStream().write("error".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "/deleteShop")
    public String deleteShop(HttpServletRequest request, HttpServletResponse response) {
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));
        
        try {
            if (webDelegate.deleteShop(shopId)) {
                response.getOutputStream().write("success".getBytes());
            } else {
                response.getOutputStream().write("error".getBytes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping (value="/shopForm/{id}")
    public ModelAndView shopForm(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response, ShopVO shopForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
//        if (!CommonUtil.checkAdminPermission(request)) {
//            return new ModelAndView("redirect:/admin/logout");
//        }

        String type = request.getParameter("type");
        
        request.setAttribute("navTitle", "门店管理");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminShopFormLayout");
        
        String url = "0";
        
        String title;        
                
        String small = "";        

        if (!id.equals("0")) {
            url = id;
            shopForm = webDelegate.getShop(NumberUtil.strToInt(id));
            title = shopForm.getName();
            type = shopForm.getType();
            if (type.equals(Constant.SHOP_KIND.COMPANY))
                small = "新增公司门店";
            else
                small = "新增服务区门店";
            List<DistrictVO> districtList = webDelegate.getDistrictList(shopForm.getDistrictParentId(), false);
            mav.addObject("districtList", districtList);
            
        } else {
            if (type.equals(Constant.SHOP_KIND.COMPANY))
                title = "新增公司门店";
            else
                title = "新增服务区门店";
        }
        
        if (type.equals(Constant.SHOP_KIND.COMPANY))
            request.setAttribute("subTitle", "公司门店");
        else
            request.setAttribute("subTitle", "服务区门店");

        mav.addObject("shopForm", shopForm);
        mav.addObject("url", url);
        mav.addObject("type", type);
        mav.addObject("title", title);
        mav.addObject("small", small);
        
        List<DistrictVO> parentDistrictList = webDelegate.getDistrictList(-1, true);
        mav.addObject("parentDistrictList", parentDistrictList);
        
        List<DeliverVO> deliverList = webDelegate.getDeliverList(null, -1, -1, null, null);
        mav.addObject("deliverList", deliverList);
        
        List<StationVO> stationList = webDelegate.getStationList(null, -1, null, -1, -1, null, null);
        mav.addObject("stationList", stationList);
        
        List<ShopKindVO> shopKindList = webDelegate.getShopKindList();
        mav.addObject("shopKindList", shopKindList);
        
        return mav;
    }
    
    @RequestMapping (value="/shopShow/{id}")
    public ModelAndView shopShow(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response, ShopVO shopForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
//        if (!CommonUtil.checkAdminPermission(request)) {
//            return new ModelAndView("redirect:/admin/logout");
//        }
        
        
        request.setAttribute("navTitle", "门店管理");        
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminShopShowLayout");        
        
        String title;        
        String small = "";        
        shopForm = webDelegate.getShop(NumberUtil.strToInt(id));
        String type = shopForm.getType();
        
        title = shopForm.getName();
        if (type.equals(Constant.SHOP_KIND.COMPANY))
            small = "查看服务区店辅";
        else
            small = "查看服务区店辅";
        List<DistrictVO> districtList = webDelegate.getDistrictList(shopForm.getDistrictParentId(), false);
        mav.addObject("districtList", districtList);
        
        if (type.equals(Constant.SHOP_KIND.COMPANY))
            request.setAttribute("subTitle", "公司门店");
        else
            request.setAttribute("subTitle", "服务区门店");

        mav.addObject("shopForm", shopForm);
        mav.addObject("url", id);
        mav.addObject("type", type);
        mav.addObject("title", title);
        mav.addObject("small", small);
        
        List<DistrictVO> parentDistrictList = webDelegate.getDistrictList(-1, true);
        mav.addObject("parentDistrictList", parentDistrictList);
        
        List<DeliverVO> deliverList = webDelegate.getDeliverList(null, -1, -1, null, null);
        mav.addObject("deliverList", deliverList);
        
        List<StationVO> stationList = webDelegate.getStationList(null, -1, null, -1, -1, null, null);
        mav.addObject("stationList", stationList);
        
        List<ShopKindVO> shopKindList = webDelegate.getShopKindList();
        mav.addObject("shopKindList", shopKindList);
        
        return mav;
    }
    
    @RequestMapping (value="/shopFormPost/{id}", method = RequestMethod.POST)
    public ModelAndView shopFormPost(@PathVariable("id") String id, @RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response, ShopVO shopForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }       
        
        shopForm.setName(CommonUtil.charsetEncoding(shopForm.getName()));
        shopForm.setAddress(CommonUtil.charsetEncoding(shopForm.getAddress()));
        shopForm.setDescription(CommonUtil.charsetEncoding(shopForm.getDescription()));
                
        if (file != null && !file.getOriginalFilename().isEmpty() && file.getSize() > 0) {
            String fileName = CommonUtil.charsetEncoding(file.getOriginalFilename());
            String ext = FilenameUtils.getExtension(fileName);
            String uploadName = "shop_" + System.currentTimeMillis() + "." + ext;
            FileOutputStream out = null;
            try {                    
                File uploadFile = new File(CommonUtil.getShopImageDir(request), uploadName);
                out = new FileOutputStream(uploadFile);
                out.write(file.getBytes());
                out.flush();
                shopForm.setImagePath(Constant.IMAGE_DIR + "/" + Constant.SHOP_IMAGE_DIR + "/" + uploadName);
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
            webDelegate.addNewShop(shopForm);
        else
            webDelegate.updateShop(shopForm);
        
        return new ModelAndView("redirect:/admin/shopList/" + shopForm.getType());
    } 
    
    public ModelAndView shopKindList(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "门店管理");
        request.setAttribute("subTitle", "门店类型");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminShopKindLayout");
                
        return mav;
    }
    
    @RequestMapping(value="/getShopKindList", method = RequestMethod.POST)
    public String getShopKindList(HttpServletRequest request, HttpServletResponse response) {
        int draw  = NumberUtil.strToInt(request.getParameter("draw"));
        int offset = NumberUtil.strToInt(request.getParameter("start"));
        int limit = NumberUtil.strToInt(request.getParameter("length"));
        
        String sort = request.getParameter("order[0][dir]");
        int sortColumnId = NumberUtil.strToInt(request.getParameter("order[0][column]"));
        
        String sortColumn = "";
        if (sortColumnId > 1)
            sortColumn = Constant.SHOP_KIND_COLUMNS[sortColumnId - 1];
        
        if (sortColumn.isEmpty()) {
            sort = "DESC";
            sortColumn = "reg_date";
        }    
        
//        String keyword = request.getParameter("keyword");
                
        List<ShopKindVO> shopKinds = webDelegate.getShopKindList(null, offset, limit, sortColumn, sort);
        List<ShopKindVO> totalKinds = webDelegate.getShopKindList(null, -1, -1, null, null);
        int recordTotal = (totalKinds != null)? totalKinds.size(): 0;
                
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("draw", draw);
        jsonResult.put("recordsTotal", recordTotal);
        jsonResult.put("recordsFiltered", recordTotal);
        JSONArray datas = new JSONArray();
        
        if (shopKinds != null) {
            int num = offset;
            
            for (ShopKindVO shopKind: shopKinds) {
                JSONObject item = new JSONObject();
                item.put("DT_RowId", shopKind.getId());
                item.put("0", ++num);
                item.put("1", shopKind.getName());    
                String tag = "<a href=\"javascript:;\" class='btn btn-xs blue' onclick=\"onEditShopKind('" + shopKind.getId() + "')\"> 编辑 <i class='fa fa-edit'></i></a> ";
                tag += "<a href='javascript:;' onclick=\"onDeleteShopKind('" + shopKind.getId() + "')\" class='btn btn-xs grey-cascade'> 删除 <i class='fa fa-trash-o'></i></a>";
                item.put("2", tag);
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
    
    @RequestMapping(value = "/deleteShopKind")
    public String deleteShopKind(HttpServletRequest request, HttpServletResponse response) {
        int shopKindId = NumberUtil.strToInt(request.getParameter("shopKindId"));
        
        try {
            if (webDelegate.deleteShopKind(shopKindId)) {
                response.getOutputStream().write("success".getBytes());
            } else {
                response.getOutputStream().write("error".getBytes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "/addNewShopKind")
    public String addNewShopKind(HttpServletRequest request, HttpServletResponse response) {
        String shopKindName = request.getParameter("shopKindName");        
        ShopKindVO newShopKind = new ShopKindVO();
        newShopKind.setName(shopKindName);
        try {
            if (webDelegate.addNewShopKind(newShopKind))
                response.getOutputStream().write("success".getBytes());
            else
                response.getOutputStream().write("error".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "/getShopKind")
    public String getShopKind(HttpServletRequest request, HttpServletResponse response) {
        int shopKindId = NumberUtil.strToInt(request.getParameter("shopKindId"));
        
        ShopKindVO shopKind = webDelegate.getShopKind(shopKindId);
        
        JSONObject jsonObj = new JSONObject();  
        jsonObj.put("name", shopKind.getName());
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            jsonObj.writeJSONString(response.getWriter());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @RequestMapping(value = "/updateShopKind")
    public String updateShopKinds(HttpServletRequest request, HttpServletResponse response) {
        int shopKindId = NumberUtil.strToInt(request.getParameter("shopKindId"));
        String shopKindName = request.getParameter("shopKindName");
        ShopKindVO shopKind = new ShopKindVO();
        shopKind.setId(shopKindId);
        shopKind.setName(shopKindName);
        
        try {
            if (webDelegate.updateShopKind(shopKind))
                response.getOutputStream().write("success".getBytes());
            else
                response.getOutputStream().write("error".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public ModelAndView goodsKindList(HttpServletRequest request, HttpServletResponse response, GoodsKindSearchForm searchForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "商品分类");
        request.setAttribute("subTitle", "");
        
        searchForm.setName(CommonUtil.charsetEncoding(searchForm.getName()));
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminGoodsKindLayout");
        mav.addObject("searchForm", searchForm);
        
        List<ShopKindVO> shopKindList = webDelegate.getShopKindList();
        mav.addObject("shopKindList", shopKindList);
        
        return mav;
    }
    
    @RequestMapping(value="/getGoodsKindList", method = RequestMethod.POST)
    public String getGoodsKindList(HttpServletRequest request, HttpServletResponse response) {
        int draw  = NumberUtil.strToInt(request.getParameter("draw"));
        int offset = NumberUtil.strToInt(request.getParameter("start"));
        int limit = NumberUtil.strToInt(request.getParameter("length"));
        
        String sort = request.getParameter("order[0][dir]");
        int sortColumnId = NumberUtil.strToInt(request.getParameter("order[0][column]"));
        
        String sortColumn = "";
        if (sortColumnId > 1)
            sortColumn = Constant.GOODS_KIND_COLUMNS[sortColumnId - 1];
        
        String name = request.getParameter("name");
        int shopKindId = NumberUtil.strToInt(request.getParameter("shopKindId"));
                
        List<GoodsKindVO> goodsKinds = webDelegate.getGoodsKindList(shopKindId, name, offset, limit, sortColumn, sort);
        List<GoodsKindVO> totalKinds = webDelegate.getGoodsKindList(-1, null, -1, -1, null, null);
        int recordTotal = (totalKinds != null)? totalKinds.size(): 0;
                
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("draw", draw);
        jsonResult.put("recordsTotal", recordTotal);
        jsonResult.put("recordsFiltered", recordTotal);
        JSONArray datas = new JSONArray();
        
        if (goodsKinds != null) {
            int num = offset;
            
            for (GoodsKindVO goodsKind: goodsKinds) {
                JSONObject item = new JSONObject();
                item.put("DT_RowId", goodsKind.getId());
                item.put("0", ++num);
                item.put("1", goodsKind.getName());    
                item.put("2", goodsKind.getShopKindName());    
                String tag = "<a href=\"javascript:;\" class='btn btn-xs blue' onclick=\"onEditGoodsKind('" + goodsKind.getId() + "')\"> 编辑 <i class='fa fa-edit'></i></a> ";
                tag += "<a href='javascript:;' onclick=\"onDeleteGoodsKind('" + goodsKind.getId() + "')\" class='btn btn-xs grey-cascade'> 删除 <i class='fa fa-trash-o'></i></a>";
                item.put("3", tag);
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
    
    @RequestMapping(value = "/deleteGoodsKind")
    public String deleteGoodsKind(HttpServletRequest request, HttpServletResponse response) {
        int goodsKindId = NumberUtil.strToInt(request.getParameter("goodsKindId"));
        
        try {
            if (webDelegate.deleteGoodsKind(goodsKindId)) {
                response.getOutputStream().write("success".getBytes());
            } else {
                response.getOutputStream().write("error".getBytes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "/addNewGoodsKind")
    public String addNewGoodsKind(HttpServletRequest request, HttpServletResponse response) {
        String goodsKindName = request.getParameter("goodsKindName");  
        int shopKindId = NumberUtil.strToInt(request.getParameter("shopKindId"));
        
        try {
            if (webDelegate.addNewGoodsKind(goodsKindName, shopKindId))
                response.getOutputStream().write("success".getBytes());
            else
                response.getOutputStream().write("error".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "/getGoodsKind")
    public String getGoodsKind(HttpServletRequest request, HttpServletResponse response) {
        int goodsKindId = NumberUtil.strToInt(request.getParameter("goodsKindId"));
        
        GoodsKindVO goodsKind = webDelegate.getGoodsKind(goodsKindId);
        
        JSONObject jsonObj = new JSONObject();  
        jsonObj.put("name", goodsKind.getName());
        jsonObj.put("shopKindId", goodsKind.getShopKindId());
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            jsonObj.writeJSONString(response.getWriter());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @RequestMapping(value = "/updateGoodsKind")
    public String updateGoodsKind(HttpServletRequest request, HttpServletResponse response) {
        int shopKindId = NumberUtil.strToInt(request.getParameter("shopKindId"));
        int goodsKindId = NumberUtil.strToInt(request.getParameter("goodsKindId"));
        String goodsKindName = request.getParameter("goodsKindName");
        
        try {
            if (webDelegate.updateGoodsKind(goodsKindName, shopKindId, goodsKindId))
                response.getOutputStream().write("success".getBytes());
            else
                response.getOutputStream().write("error".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public ModelAndView orderList(HttpServletRequest request, HttpServletResponse response, OrderSearchForm searchForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "订单管理");
        request.setAttribute("subTitle", "");
                
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminOrderLayout");
                
        searchForm.setKeyword(CommonUtil.charsetEncoding(searchForm.getKeyword()));
        searchForm.setOrderStatus(CommonUtil.charsetEncoding(searchForm.getOrderStatus()));
        
        if (request.getParameter("searchDeliver") != null) {
            searchForm.setSearchKey("deliverName");
            searchForm.setKeyword(request.getParameter("searchDeliver"));
        }
                
        mav.addObject("searchForm", searchForm);
        
        return mav;
    }
    
    @RequestMapping(value="/getOrderList", method = RequestMethod.POST)
    public String getOrderList(HttpServletRequest request, HttpServletResponse response) {
        int draw  = NumberUtil.strToInt(request.getParameter("draw"));
        int offset = NumberUtil.strToInt(request.getParameter("start"));
        int limit = NumberUtil.strToInt(request.getParameter("length"));
        
        String sort = request.getParameter("order[0][dir]");
        int sortColumnId = NumberUtil.strToInt(request.getParameter("order[0][column]"));
        
        String sortColumn = "";
        if (sortColumnId > 1)
            sortColumn = Constant.ORDER_COLUMNS[sortColumnId - 1];
        
        
        String orderNum = null;
        String userName = null;
        String deliverName = null;
        
        String searchKey = request.getParameter("searchKey");
        if (searchKey.equals("orderNum"))
            orderNum = request.getParameter("keyword");
        else if (searchKey.equals("userName"))
            userName = request.getParameter("keyword");
        else
            deliverName = request.getParameter("keyword");
        
        String orderStatus = request.getParameter("orderStatus");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        
        int recordTotal = webDelegate.countAllOrder(1, orderNum, userName, deliverName, orderStatus, from, to);
        List<OrderVO> orders = webDelegate.getOrderList(1, orderNum, userName, deliverName, orderStatus, from, to, offset, limit, sortColumn, sort);
                
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("draw", draw);
        jsonResult.put("recordsTotal", recordTotal);
        jsonResult.put("recordsFiltered", recordTotal);
        JSONArray datas = new JSONArray();
        
        if (orders != null) {
            int num = offset;
            
            for (OrderVO order: orders) {
                JSONObject item = new JSONObject();
                item.put("DT_RowId", order.getId());
                item.put("0", ++num);
                item.put("1", order.getOrderNum());
                item.put("2", order.getUserName());
                item.put("3", order.getUserTelNo());
                item.put("4", NumberUtil.format(order.getOrderAmount()));
                item.put("5", order.getOrderStatus());
                item.put("6", order.getDeliverName());
                item.put("7", DateUtil.formatDateTime(order.getPayDate()));
                item.put("8", DateUtil.formatDateTime(order.getShippingDate()));
                item.put("9", DateUtil.formatDateTime(order.getDeliveryDate()));
                String tag = "<a href='orderShow/" + order.getId() + "' class='btn btn-xs purple'> 查看 <i class='fa fa-file-text'></i></a>";
                item.put("10", tag);
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
    
    @RequestMapping (value="/orderShow/{id}")
    public ModelAndView orderShow(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response, OrderVO orderForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "订单管理");
        request.setAttribute("subTitle", "");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminOrderShowLayout");
        
        orderForm = webDelegate.getOrder(NumberUtil.strToInt(id));
        String title = orderForm.getOrderNum();
        String small = "订单详情";     
        
        List<OrderDetailVO> orderDetailList = webDelegate.getOrderDetailList(orderForm.getId());
        List<OrderCouponVO> orderCouponList = webDelegate.getOrderCouponList(orderForm.getId());
        
        double orderDetailAmount = 0;
        double orderCouponAmount = 0;
        int totalQty = 0;
        
        if (orderDetailList != null) {
            for (OrderDetailVO orderDetail: orderDetailList) {
                orderDetailAmount += orderDetail.getPrice() * orderDetail.getQty();
                totalQty += orderDetail.getQty();
            }
        }
        
        if (orderCouponList != null) {
            for (OrderCouponVO orderCoupon: orderCouponList) {
                orderCouponAmount += orderCoupon.getAmount();
            }
        }
        
        mav.addObject("orderForm", orderForm);
        mav.addObject("orderDetailAmount", NumberUtil.format(orderDetailAmount));
        mav.addObject("orderCouponAmount", NumberUtil.format(orderCouponAmount));
        mav.addObject("orderDetailList", orderDetailList);
        mav.addObject("totalQty", totalQty);
        mav.addObject("totalAmount", NumberUtil.format(orderDetailAmount - orderCouponAmount));
        mav.addObject("title", title);
        mav.addObject("small", small);
                
        return mav;
    }
    
    public ModelAndView statDeliver(HttpServletRequest request, HttpServletResponse response, OrderSearchForm searchForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "配送员统计");
        request.setAttribute("subTitle", "");
                
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminStatDeliverLayout");
                
        searchForm.setKeyword(CommonUtil.charsetEncoding(searchForm.getKeyword()));
                
        mav.addObject("searchForm", searchForm);
        
        return mav;
    }
    
    @RequestMapping(value="/getStatDeliver", method = RequestMethod.POST)
    public String getStatDeliver(HttpServletRequest request, HttpServletResponse response) {
        int draw  = NumberUtil.strToInt(request.getParameter("draw"));
        int offset = NumberUtil.strToInt(request.getParameter("start"));
        int limit = NumberUtil.strToInt(request.getParameter("length"));
        
        String sort = request.getParameter("order[0][dir]");
        int sortColumnId = NumberUtil.strToInt(request.getParameter("order[0][column]"));
        
        String sortColumn = "";
        if (sortColumnId > 1)
            sortColumn = Constant.STAT_DELIVER_COLUMNS[sortColumnId - 1];
        
        String deliverName = request.getParameter("deliverName");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        
        int recordTotal = webDelegate.countAllDeliverSum(deliverName, from, to);
        List<OrderVO> orders = webDelegate.getDeliverSum(deliverName, from, to, offset, limit, sortColumn, sort);
                
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("draw", draw);
        jsonResult.put("recordsTotal", recordTotal);
        jsonResult.put("recordsFiltered", recordTotal);
        JSONArray datas = new JSONArray();
        
        if (orders != null) {
            int num = offset;
            
            for (OrderVO order: orders) {
                JSONObject item = new JSONObject();
                item.put("DT_RowId", order.getId());
                item.put("0", ++num);
                item.put("1", order.getDeliverAccountId());
                item.put("2", order.getDeliverName());
                item.put("3", order.getOrderQty());
                String tag = "<a href='orderList?searchDeliver=" + order.getDeliverName() + "' class='btn btn-xs purple'> 查看订单 <i class='fa fa-external-link'></i></a>";
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
    
    public ModelAndView activityList(HttpServletRequest request, HttpServletResponse response, ActivitySearchForm searchForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "活动管理");
        request.setAttribute("subTitle", "");
                
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminActivityLayout");
                
        searchForm.setKeyword(CommonUtil.charsetEncoding(searchForm.getKeyword()));
        searchForm.setStatus(CommonUtil.charsetEncoding(searchForm.getStatus()));
                
        mav.addObject("searchForm", searchForm);
        
        return mav;
    }
    
    @RequestMapping(value="/getActivityList", method = RequestMethod.POST)
    public String getActivityList(HttpServletRequest request, HttpServletResponse response) {
        int draw  = NumberUtil.strToInt(request.getParameter("draw"));
        int offset = NumberUtil.strToInt(request.getParameter("start"));
        int limit = NumberUtil.strToInt(request.getParameter("length"));
        
        String sort = request.getParameter("order[0][dir]");
        int sortColumnId = NumberUtil.strToInt(request.getParameter("order[0][column]"));
        
        String sortColumn = "";
        if (sortColumnId > 1)
            sortColumn = Constant.ACTIVITY_COLUMNS[sortColumnId - 1];
        
        String activityName = request.getParameter("keyword");
        String status = request.getParameter("status");
        
        int recordTotal = webDelegate.countAllActivity(activityName, status);
        List<ActivityVO> activities = webDelegate.getActivityList(activityName, status, offset, limit, sortColumn, sort);
                
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("draw", draw);
        jsonResult.put("recordsTotal", recordTotal);
        jsonResult.put("recordsFiltered", recordTotal);
        JSONArray datas = new JSONArray();
        
        if (activities != null) {
            int num = offset;
            
            for (ActivityVO activity: activities) {
                JSONObject item = new JSONObject();
                item.put("DT_RowId", activity.getId());
                item.put("0", activity.getNum());
                item.put("1", activity.getName());
                item.put("2", NumberUtil.format(activity.getActivityAmount()));
                item.put("3", activity.getCouponQty());
                item.put("4", DateUtil.formatDate(activity.getStartDate()) + " 至 " + DateUtil.formatDate(activity.getEndDate()));
                item.put("5", activity.getStatus());
                item.put("6", DateUtil.formatDate(activity.getRegDate()));
                String tag = "<a href='activityShow/" + activity.getId() + "' class='btn btn-xs purple'> 查看 <i class='fa fa-file-text'></i></a>";
                if (activity.getStatus().equals(Constant.ACTIVITY_STATUS.READY)) {
                    tag += "<a href='activityForm/" + activity.getId() + "' class='btn btn-xs blue'> 编辑 <i class='fa fa-edit'></i></a>";
                    tag += "<a href='javascript:;' onclick=onDeleteActivity('" + activity.getId() + "') class='btn btn-xs grey-cascade'> 删除 <i class='fa fa-trash-o'></i></a>";
                }
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
    
    @RequestMapping(value = "/deleteActivity")
    public String deleteActivity(HttpServletRequest request, HttpServletResponse response) {
        int activityId = NumberUtil.strToInt(request.getParameter("activityId"));
        
        try {
            if (webDelegate.deleteActivity(activityId)) {
                response.getOutputStream().write("success".getBytes());
            } else {
                response.getOutputStream().write("error".getBytes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping (value="/activityForm/{id}")
    public ModelAndView activityForm(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response, ActivityVO activityForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
//        if (!CommonUtil.checkAdminPermission(request)) {
//            return new ModelAndView("redirect:/admin/logout");
//        }
        
        request.setAttribute("navTitle", "活动管理");
        request.setAttribute("subTitle", "");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminActivityFormLayout");
        
        String url = "0";
        String title = "新增活动";
        String small = "";        

        if (!id.equals("0")) {
            url = id;
            activityForm = webDelegate.getActivity(NumberUtil.strToInt(id));
            title = activityForm.getName();
            small = "编辑活动";
        }
        
        activityForm.setTmpUId(System.currentTimeMillis());
        
        webDelegate.makeTmpActivityDetail(activityForm.getTmpUId(), activityForm.getId());

        mav.addObject("activityForm", activityForm);
        mav.addObject("url", url);
        mav.addObject("title", title);
        mav.addObject("small", small);
        
        return mav;
    }
    
    @RequestMapping (value="/activityShow/{id}")
    public ModelAndView activityShow(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response, ActivityVO activityForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
//        if (!CommonUtil.checkAdminPermission(request)) {
//            return new ModelAndView("redirect:/admin/logout");
//        }
        
        request.setAttribute("navTitle", "活动管理");
        request.setAttribute("subTitle", "");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminActivityShowLayout");
        
        activityForm = webDelegate.getActivity(NumberUtil.strToInt(id));
        String title = activityForm.getName();
        String small = "活动详情";    

        mav.addObject("activityForm", activityForm);
        mav.addObject("title", title);
        mav.addObject("small", small);
        
        List<ActivityDetailVO> unlimitedDetails = webDelegate.getActivityDetail(activityForm.getId(), Constant.ACTIVITY_TYPE.UNLIMITED);
        mav.addObject("unlimitedDetails", unlimitedDetails);
        List<ActivityDetailVO> fullDetails = webDelegate.getActivityDetail(activityForm.getId(), Constant.ACTIVITY_TYPE.FULL);
        mav.addObject("fullDetails", fullDetails);
        
        return mav;
    }
    
    @RequestMapping (value="/activityFormPost/{id}", method = RequestMethod.POST)
    public ModelAndView activityFormPost(@PathVariable("id") String id, @RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response, ActivityVO activityForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }       
        
        activityForm.setName(CommonUtil.charsetEncoding(activityForm.getName()));
        activityForm.setStartDate(DateUtil.parseDate(activityForm.getStartDateStr()));
        activityForm.setEndDate(DateUtil.parseDate(activityForm.getEndDateStr()));
        
                
        if (file != null && !file.getOriginalFilename().isEmpty() && file.getSize() > 0) {
            String fileName = CommonUtil.charsetEncoding(file.getOriginalFilename());
            String ext = FilenameUtils.getExtension(fileName);
            String uploadName = "activity_" + System.currentTimeMillis() + "." + ext;
            FileOutputStream out = null;
            try {                    
                File uploadFile = new File(CommonUtil.getActivityImageDir(request), uploadName);
                out = new FileOutputStream(uploadFile);
                out.write(file.getBytes());
                out.flush();
                activityForm.setImagePath(Constant.IMAGE_DIR + "/" + Constant.ACTIVITY_IMAGE_DIR + "/" + uploadName);
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
                        
        if (id.equals("0")) {
            int activityId = webDelegate.addNewActivity(activityForm);
            activityForm.setId(activityId);
        } else {
            webDelegate.updateActivity(activityForm);
        }
                
        webDelegate.deleteActivityDetail(activityForm.getId(), Constant.ACTIVITY_TYPE.ALL);
        
        if (activityForm.getUnlimitedType()) {
            List<ActivityDetailVO> tmpAll = webDelegate.getTmpActivityDetailList(activityForm.getTmpUId(), Constant.ACTIVITY_TYPE.UNLIMITED);

            if (tmpAll != null) {
                for (ActivityDetailVO tmp: tmpAll) {
                    tmp.setActivityId(activityForm.getId());
                }
            }

            webDelegate.addNewActivityDetail(activityForm.getId(), tmpAll);
        }
        
        if (activityForm.getFullType()) {
            List<ActivityDetailVO> tmpAll = webDelegate.getTmpActivityDetailList(activityForm.getTmpUId(), Constant.ACTIVITY_TYPE.FULL);

            if (tmpAll != null) {
                for (ActivityDetailVO tmp: tmpAll) {
                    tmp.setActivityId(activityForm.getId());
                }
            }

            webDelegate.addNewActivityDetail(activityForm.getId(), tmpAll);
        }
        
        webDelegate.deleteAllTmpActivityDetail(activityForm.getTmpUId());
        
        List<ActivityDetailVO> details = webDelegate.getActivityDetail(activityForm.getId(), Constant.ACTIVITY_TYPE.ALL);
        
        if (details != null) {
            double activityAmount = 0;
            int couponQty = 0;
            
            for (ActivityDetailVO detail: details) {
                activityAmount += detail.getQty() * detail.getCouponAmount();
                couponQty += detail.getQty();
            }
            
            webDelegate.updateActivityAmountAndQty(activityForm.getId(), activityAmount, couponQty);
        }

        return new ModelAndView("redirect:/admin/activityList");
    }
    
    @RequestMapping (value="/activityFormCancel/{id}")
    public ModelAndView activityFormCancel(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {        
        webDelegate.deleteAllTmpActivityDetail(NumberUtil.strToLong(id));

        return new ModelAndView("redirect:/admin/activityList");
    }
        
    @RequestMapping(value = "/getTmpActivityDetail/{type}")
    public ModelAndView getTmpActivityDetail(@PathVariable("type") String type, HttpServletRequest request, HttpServletResponse response) {
        long tmpUId = NumberUtil.strToLong(request.getParameter("tmpUId"));
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("activityDetailsLayout");
        List<ActivityDetailVO> tmpList = webDelegate.getTmpActivityDetailList(tmpUId, type);
        mav.addObject("tmpList", tmpList);
        mav.addObject("type", type);
         
        return mav;
    }
    
    @RequestMapping(value = "/addTmpActivityDetail")
    public String addTmpActivityDetail(HttpServletRequest request, HttpServletResponse response) {        
        long tmpUId = NumberUtil.strToLong(request.getParameter("tmpUId"));
        
        double fullAmount = NumberUtil.strToDouble(request.getParameter("fullAmount"), 0);
        double couponAmount = NumberUtil.strToDouble(request.getParameter("couponAmount"), 0);
        int qty = NumberUtil.strToInt(request.getParameter("qty"), 0);
        double rate = NumberUtil.strToDouble(request.getParameter("rate"), 0);
        String type = request.getParameter("type");
        
        List<ActivityDetailVO> details = webDelegate.getTmpActivityDetailList(tmpUId, Constant.ACTIVITY_TYPE.ALL);
        
        ActivityDetailVO tmp = new ActivityDetailVO();
        tmp.setTmpUId(tmpUId);
        tmp.setFullAmount(fullAmount);
        tmp.setCouponAmount(couponAmount);
        tmp.setQty(qty);
        tmp.setRate(rate);
        tmp.setType(type);
        
        webDelegate.addTmpActivityDetail(tmpUId, tmp);
        int totalQty =  qty;    
        
        if (details != null) {
            for (ActivityDetailVO detail: details) {
                totalQty += detail.getQty();
            }
            
            for (ActivityDetailVO detail: details) {
                if (totalQty != 0) {
                    double newRate = (((double)detail.getQty()) / totalQty) * 100;
                    detail.setRate(newRate);
                }    
            }
            
            webDelegate.updateTmpActivityDetailRate(details);
        }
        
         try {
            response.getOutputStream().write("success".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
         
        return null;
    }
    
    @RequestMapping(value = "/updateTmpActivityDetail")
    public String updateTmpActivityDetail(HttpServletRequest request, HttpServletResponse response) {        
        double rate = NumberUtil.strToLong(request.getParameter("rate"));
        long id = NumberUtil.strToLong(request.getParameter("id"));
        
         try {
            webDelegate.updateTmpActivityDetail(id, rate);
            response.getOutputStream().write("success".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
         
        return null;
    }
    
    @RequestMapping(value = "/getActivityAmount")
    public String getActivityAmount(HttpServletRequest request, HttpServletResponse response) {        
        long tmpUId = NumberUtil.strToLong(request.getParameter("tmpUId"));
        double activityAmount = 0;
        List<ActivityDetailVO> details = webDelegate.getTmpActivityDetailList(tmpUId, Constant.ACTIVITY_TYPE.ALL);        
        if (details != null) {
            for (ActivityDetailVO detail: details) {
                activityAmount += detail.getCouponAmount() * detail.getQty();
            }
        }
        try {
            response.getOutputStream().write(String.valueOf(activityAmount).getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
         
        return null;
    }
    
    @RequestMapping(value = "/getCouponQty")
    public String getCouponQty(HttpServletRequest request, HttpServletResponse response) {        
        long tmpUId = NumberUtil.strToLong(request.getParameter("tmpUId"));
        int qty = 0;
        List<ActivityDetailVO> details = webDelegate.getTmpActivityDetailList(tmpUId, Constant.ACTIVITY_TYPE.ALL);        
        if (details != null) {
            for (ActivityDetailVO detail: details) {
                qty += detail.getQty();
            }
        }
        try {
            response.getOutputStream().write(String.valueOf(qty).getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
         
        return null;
    }
    
    @RequestMapping(value = "/deleteTmpActivityDetail")
    public String deleteTmpActivityDetail(HttpServletRequest request, HttpServletResponse response) {        
        long tmpUId = NumberUtil.strToLong(request.getParameter("tmpUId"));
        long id = NumberUtil.strToLong(request.getParameter("id"));        
        webDelegate.deleteTmpActivityDetail(id, tmpUId);        
        List<ActivityDetailVO> details = webDelegate.getTmpActivityDetailList(tmpUId, Constant.ACTIVITY_TYPE.ALL);
        
        int totalQty =  0;        
        if (details != null) {
            for (ActivityDetailVO detail: details) {
                totalQty += detail.getQty();
            }
            
            for (ActivityDetailVO detail: details) {
                if (totalQty != 0) {
                    double rate = (((double)detail.getQty()) / totalQty) * 100;
                    detail.setRate(rate);
                }    
            }
            
            webDelegate.updateTmpActivityDetailRate(details);
        }
        
        try {
            response.getOutputStream().write("success".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
         
        return null;
    }
    
    @RequestMapping(value = "/validateActivityName")
    public String validateActivityName(HttpServletRequest request, HttpServletResponse response) {
        int activityId = NumberUtil.strToInt(request.getParameter("activityId"));
        String activityName = request.getParameter("activityName");
        
        try {
            ActivityVO activity = webDelegate.getActivityByName(activityName);
            if (activity == null || activity.getId() == activityId)
                response.getOutputStream().write("success".getBytes());
            else
                response.getOutputStream().write("error".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
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
                item.put("0", feedback.getDisplayId());
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
                tag += "<a href='javascript:;' onclick=onDeleteDeliver('" + deliver.getId()+ "') class='btn btn-xs grey-cascade'> 删除 <i class='fa fa-trash-o'></i></a> ";
                tag += "<a href='javascript:;' onclick=onChangePassword('" + deliver.getId()+ "') class='btn btn-xs purple'> 重置密码 <i class='fa fa-key'></i></a>";
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
            webDelegate.setDeliverPassword(NumberUtil.strToInt(deliverId), Md5Util.getMd5(password));
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
            if (webDelegate.deleteDeliver(NumberUtil.strToInt(deliverId))) {
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
                deliverForm = webDelegate.getDeliverById(NumberUtil.strToInt(id));
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
    
    public ModelAndView goodsList(HttpServletRequest request, HttpServletResponse response, GoodsSearchForm searchForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "商品管理");
        request.setAttribute("subTitle", "");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminGoodsLayout");
        
        searchForm.setKeyword(CommonUtil.charsetEncoding(searchForm.getKeyword()));
                
        List<ShopKindVO> shopKindList = webDelegate.getShopKindList();
        List<ShopVO> shopList = null;
        
        if (!CommonUtil.isEmptyStr(searchForm.getShopKindId()))
            shopList = webDelegate.getShopList(NumberUtil.strToInt(searchForm.getShopKindId()));
        
        mav.addObject("searchForm", searchForm);
        mav.addObject("shopList", shopList);
        mav.addObject("shopKindList", shopKindList);
        
        return mav;
    }
    
    @RequestMapping(value="/getGoodsList", method = RequestMethod.POST)
    public String getGoodsList(HttpServletRequest request, HttpServletResponse response) {
        int draw  = NumberUtil.strToInt(request.getParameter("draw"));
        int offset = NumberUtil.strToInt(request.getParameter("start"));
        int limit = NumberUtil.strToInt(request.getParameter("length"));
        
        String sort = request.getParameter("order[0][dir]");
        int sortColumnId = NumberUtil.strToInt(request.getParameter("order[0][column]"));
        
        String sortColumn = "";
        if (sortColumnId > 2)
            sortColumn = Constant.GOODS_COLUMNS[sortColumnId - 1];
        else 
            sortColumn = Constant.GOODS_COLUMNS[sortColumnId];
        
        String keyword = request.getParameter("keyword");
        int shopId = NumberUtil.strToInt(request.getParameter("shopId"));
        int shopKindId = NumberUtil.strToInt(request.getParameter("shopKindId"));
        
        int recordTotal = webDelegate.countAllGoods(keyword, shopKindId, shopId);
        List<GoodsVO> goodsList = webDelegate.getGoodsList(keyword, shopKindId, shopId, offset, limit, sortColumn, sort);
                
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("draw", draw);
        jsonResult.put("recordsTotal", recordTotal);
        jsonResult.put("recordsFiltered", recordTotal);
        JSONArray datas = new JSONArray();
        
        if (goodsList != null) {
            int num = offset;
            
            for (GoodsVO goods: goodsList) {
                JSONObject item = new JSONObject();
                item.put("DT_RowId", goods.getId());
                item.put("0", ++num);
                item.put("1", goods.getNum());
                item.put("2", goods.getName());
                item.put("3", "<img style='height: 40px; max-width: 90px;' src='/" + goods.getImagePath() + "'></img>");
                item.put("4", goods.getShopName());
                item.put("5", goods.getShopKindName());
                item.put("6", goods.getGoodsKindName());
                item.put("7", goods.getPrice());
                item.put("8", goods.getStatus());
                item.put("9", DateUtil.formatDateTime(goods.getRegDate()));
                String tag = "<a href='goodsShow/" + goods.getId() + "' class='btn btn-xs purple'> 查看 <i class='fa fa-file-text'></i></a>";
                if (Constant.GOODS_STATUS.SET_OFF.equals(goods.getStatus())) {
                    tag += "<a href='goodsForm/" + goods.getId() + "' class='btn btn-xs blue'> 编辑 <i class='fa fa-edit'></i></a>";
                    tag += "<a href='javascript:;' onclick=onDeleteGoods('" + goods.getId() + "') class='btn btn-xs grey-cascade'> 删除 <i class='fa fa-trash-o'></i></a>";
                    tag += "<a href='javascript:;' onclick=onSetOnGoodsStatus('" + goods.getId() + "') class='btn btn-xs green'> 上架 <i class='fa fa-arrow-circle-up'></i></a>";
                } else {
                    tag += "<a href='javascript:;' onclick=onSetOffGoodsStatus('" + goods.getId() + "') class='btn btn-xs green'> 下架 <i class='fa fa-arrow-circle-down'></i></a>";
                }
                
                item.put("10", tag);
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
    
    @RequestMapping(value = "/validateGoodsName")
    public String validateGoodsName(HttpServletRequest request, HttpServletResponse response) {
        int goodsId = NumberUtil.strToInt(request.getParameter("goodsId"));
        String goodsName = request.getParameter("goodsName");
        
        try {
            GoodsVO goods = webDelegate.getGoodsByName(goodsName);
            if (goods == null || goods.getId() == goodsId)
                response.getOutputStream().write("success".getBytes());
            else
                response.getOutputStream().write("error".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    @RequestMapping (value="/goodsForm/{id}")
    public ModelAndView goodsForm(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response, GoodsVO goodsForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "商品管理");
        request.setAttribute("subTitle", "");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminGoodsFormLayout");
        
        String url = "0";
        String title = "新增商品";
        String small = "";        

        if (!id.equals("0")) {
            url = id;
            goodsForm = webDelegate.getGoods(NumberUtil.strToInt(id));
            title = goodsForm.getName();
            small = "编辑商品";     
            List<ShopVO> shopList = webDelegate.getShopList(goodsForm.getShopKindId());
            mav.addObject("shopList", shopList);   
            List<GoodsKindVO> goodsKindList = webDelegate.getGoodsKindList(goodsForm.getShopKindId());
            mav.addObject("goodsKindList", goodsKindList);
        }

        mav.addObject("goodsForm", goodsForm);
        mav.addObject("url", url);
        mav.addObject("title", title);
        mav.addObject("small", small);
        
        List<ShopKindVO> shopKindList = webDelegate.getShopKindList();
        mav.addObject("shopKindList", shopKindList);
                
        return mav;
    }
    
    @RequestMapping (value="/goodsFormPost/{id}", method = RequestMethod.POST)
    public ModelAndView goodsFormPost(@PathVariable("id") String id, @RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response, GoodsVO goodsForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }       
        
        goodsForm.setName(CommonUtil.charsetEncoding(goodsForm.getName()));
        goodsForm.setDescription(CommonUtil.charsetEncoding(goodsForm.getDescription()));
                
        if (file != null && !file.getOriginalFilename().isEmpty() && file.getSize() > 0) {
            String fileName = CommonUtil.charsetEncoding(file.getOriginalFilename());
            String ext = FilenameUtils.getExtension(fileName);
            String uploadName = "goods_" + System.currentTimeMillis() + "." + ext;
            FileOutputStream out = null;
            try {                    
                File uploadFile = new File(CommonUtil.getGoodsImageDir(request), uploadName);
                out = new FileOutputStream(uploadFile);
                out.write(file.getBytes());
                out.flush();
                goodsForm.setImagePath(Constant.IMAGE_DIR + "/" + Constant.GOODS_IMAGE_DIR + "/" + uploadName);
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
            webDelegate.addNewGoods(goodsForm);
        else
            webDelegate.updateGoods(goodsForm);

        return new ModelAndView("redirect:/admin/goodsList");
    }
    
    @RequestMapping (value="/goodsShow/{id}")
    public ModelAndView goodsShow(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response, GoodsVO goodsForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "商品管理");
        request.setAttribute("subTitle", "");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminGoodsShowLayout");
        
        goodsForm = webDelegate.getGoods(NumberUtil.strToInt(id));
        String title = goodsForm.getName();
        String small = "商品详情";     
        List<ShopVO> shopList = webDelegate.getShopList(null, null, -1, goodsForm.getShopKindId(), -1, -1, -1, null, null);
        mav.addObject("shopList", shopList);   
        List<GoodsKindVO> goodsKindList = webDelegate.getGoodsKindList(goodsForm.getShopKindId());
        mav.addObject("goodsKindList", goodsKindList);

        mav.addObject("goodsForm", goodsForm);
        mav.addObject("title", title);
        mav.addObject("small", small);
        
        List<ShopKindVO> shopKindList = webDelegate.getShopKindList();
        mav.addObject("shopKindList", shopKindList);
                
        return mav;
    }
    
    @RequestMapping(value = "/deleteGoods")
    public String deleteGoods(HttpServletRequest request, HttpServletResponse response) {
        int goodsId = NumberUtil.strToInt(request.getParameter("goodsId"));
        
        try {
            if (webDelegate.deleteGoods(goodsId)) {
                response.getOutputStream().write("success".getBytes());
            } else {
                response.getOutputStream().write("error".getBytes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "/getShopListByShopKind")
    public ModelAndView getShopListByShopKind(HttpServletRequest request, HttpServletResponse response) {
        int shopKindId = NumberUtil.strToInt(request.getParameter("shopKindId"));        
        List<ShopVO> shopList = webDelegate.getShopList(shopKindId);        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("shopListLayout");
        
        mav.addObject("shopList", shopList);
        
        return mav;
    }
    
    @RequestMapping(value="/setOnGoodsStatus", method = RequestMethod.POST)
    public String setOnGoodsStatus(HttpServletRequest request, HttpServletResponse response) {
        int goodsId = NumberUtil.strToInt(request.getParameter("goodsId"));
        try {
            webDelegate.changeGoodsStatus(goodsId, Constant.GOODS_STATUS.SET_ON);
            response.getOutputStream().write("success".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/setOffGoodsStatus", method = RequestMethod.POST)
    public String setOffGoodsStatus(HttpServletRequest request, HttpServletResponse response) {
        int goodsId = NumberUtil.strToInt(request.getParameter("goodsId"));
        try {
            webDelegate.changeGoodsStatus(goodsId, Constant.GOODS_STATUS.SET_OFF);
            response.getOutputStream().write("success".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "/getGoodsKindListByShopKind")
    public ModelAndView getGoodsKindListByShopKind(HttpServletRequest request, HttpServletResponse response) {
        int shopKindId = NumberUtil.strToInt(request.getParameter("shopKindId"));        
        List<GoodsKindVO> goodsKindList = webDelegate.getGoodsKindList(shopKindId);        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("goodsKindListLayout");
        
        mav.addObject("goodsKindList", goodsKindList);
        
        return mav;
    }

    public ModelAndView activityNoticeList(HttpServletRequest request, HttpServletResponse response) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "活动预告");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminActivityNoticeLayout");
        
        return mav;
    }

    @RequestMapping(value="/getActivityNoticeList", method = RequestMethod.POST)
    public String getActivityNoticeList(HttpServletRequest request, HttpServletResponse response) {
        int draw  = NumberUtil.strToInt(request.getParameter("draw"));
        int offset = NumberUtil.strToInt(request.getParameter("start"));
        int limit = NumberUtil.strToInt(request.getParameter("length"));
        
        String sort = request.getParameter("order[0][dir]");
        int sortColumnId = NumberUtil.strToInt(request.getParameter("order[0][column]"));
        
        String sortColumn = "";
        
        sortColumn = Constant.ACTIVITY_NOTICE_COLUMNS[sortColumnId];
        
        int recordTotal = webDelegate.countAllActivityNotice();
        List<ActivityNoticeVO> activityNoticeList = webDelegate.getActivityNoticeList(offset, limit, sortColumn, sort);
                
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("draw", draw);
        jsonResult.put("recordsTotal", recordTotal);
        jsonResult.put("recordsFiltered", recordTotal);
        JSONArray datas = new JSONArray();
        
        if (activityNoticeList != null) {
            int num = offset;
            
            for (ActivityNoticeVO activityNotice: activityNoticeList) {
                JSONObject item = new JSONObject();
                item.put("DT_RowId", activityNotice.getId());
                item.put("0", ++num);
                item.put("1", activityNotice.getName());
                
                if (!activityNotice.getStatus()) {
                    item.put("2", Constant.ACTIVITY_NOTICE_STATUS.PUBLISH_OFF);
                } else{
                    item.put("2", Constant.ACTIVITY_NOTICE_STATUS.PUBLISH_ON);
                }
                
                item.put("3", DateUtil.formatDateTime(activityNotice.getRegDate()));
                String tag = "<a href='activityNoticeShow/" + activityNotice.getId() + "' class='btn btn-xs purple'> 查看 <i class='fa fa-file-text'></i></a>";
                if (!activityNotice.getStatus()) {
                    tag += "<a href='activityNoticeForm/" + activityNotice.getId() + "' class='btn btn-xs blue'> 编辑 <i class='fa fa-edit'></i></a> ";
                    tag += "<a href='javascript:;' onclick=onPublishActivityNotice('" + activityNotice.getId() + "') class='btn btn-xs green'> 发布 <i class='fa fa-bullhorn'></i></a>";
                    tag += "<a href='javascript:;' onclick=onDeleteActivityNotice('" + activityNotice.getId() + "') class='btn btn-xs grey-cascade'> 删除 <i class='fa fa-trash-o'></i></a> ";
                }
                else {
                    tag += "<a href='javascript:;' onclick=onDeleteActivityNotice('" + activityNotice.getId() + "') class='btn btn-xs grey-cascade'> 删除 <i class='fa fa-trash-o'></i></a> ";
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
    
    @RequestMapping (value="/activityNoticeForm/{id}")
    public ModelAndView activityNoticeForm(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response, ActivityNoticeVO activityNoticeForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "活动预告");
        request.setAttribute("subTitle", "");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminActivityNoticeFormLayout");
        
        String url = "0";
        String title = "新增活动预告";
        String small = "";        

        if (!id.equals("0")) {
            url = id;
            activityNoticeForm = webDelegate.getActivityNotice(NumberUtil.strToInt(id));
            title = activityNoticeForm.getName();
            small = "编辑活动预告";     
        }

        List<ActivityVO> activityList = webDelegate.getActivityListByStatus();
        mav.addObject("activityList", activityList);   

        mav.addObject("activityNoticeForm", activityNoticeForm);
        mav.addObject("url", url);
        mav.addObject("title", title);
        mav.addObject("small", small);
        
        return mav;
    }
    
    @RequestMapping (value="/activityNoticeFormPost/{id}", method = RequestMethod.POST)
    public ModelAndView activityNoticeFormPost(@PathVariable("id") String id, @RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response, ActivityNoticeVO activityNoticeForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }       
        
        activityNoticeForm.setName(CommonUtil.charsetEncoding(activityNoticeForm.getName()));
        activityNoticeForm.setDescription(CommonUtil.charsetEncoding(activityNoticeForm.getDescription()));
                
        if (file != null && !file.getOriginalFilename().isEmpty() && file.getSize() > 0) {
            String fileName = CommonUtil.charsetEncoding(file.getOriginalFilename());
            String ext = FilenameUtils.getExtension(fileName);
            String uploadName = "activityNotice_" + System.currentTimeMillis() + "." + ext;
            FileOutputStream out = null;
            try {                    
                File uploadFile = new File(CommonUtil.getActivityImageDir(request), uploadName);
                out = new FileOutputStream(uploadFile);
                out.write(file.getBytes());
                out.flush();
                activityNoticeForm.setImagePath(Constant.IMAGE_DIR + "/" + Constant.ACTIVITY_IMAGE_DIR + "/" + uploadName);
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
            webDelegate.addNewActivityNotice(activityNoticeForm);
        else
            webDelegate.updateActivityNotice(activityNoticeForm);

        return new ModelAndView("redirect:/admin/activityNoticeList");
    }
    
    @RequestMapping (value="/activityNoticeShow/{id}")
    public ModelAndView activityNoticeShow(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response, ActivityNoticeVO activityNoticeForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "活动预告");
        request.setAttribute("subTitle", "");
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminActivityNoticeShowLayout");
        
        activityNoticeForm = webDelegate.getActivityNotice(NumberUtil.strToInt(id));
        String title = activityNoticeForm.getName();
        String small = "活动预告详情";     
        List<ActivityVO> activityList = webDelegate.getActivityListByStatus();
        mav.addObject("activityList", activityList);   
        
        mav.addObject("activityNoticeForm", activityNoticeForm);
        mav.addObject("title", title);
        mav.addObject("small", small);
        
        return mav;
    }
    
    @RequestMapping(value = "/deleteActivityNotice")
    public String deleteActivityNotice(HttpServletRequest request, HttpServletResponse response) {
        int activityNoticeId = NumberUtil.strToInt(request.getParameter("activityNoticeId"));
        
        try {
            if (webDelegate.deleteActivityNotice(activityNoticeId)) {
                response.getOutputStream().write("success".getBytes());
            } else {
                response.getOutputStream().write("error".getBytes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "/publishActivityNotice")
    public String publishActivityNotice(HttpServletRequest request, HttpServletResponse response) {
        int activityNoticeId = NumberUtil.strToInt(request.getParameter("activityNoticeId"));
        try {
            webDelegate.setActivityNoticeStatus(activityNoticeId);
            response.getOutputStream().write("success".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public ModelAndView userCouponList(HttpServletRequest request, HttpServletResponse response, UserCouponSearchForm searchForm) {
        if (!CommonUtil.isLogin(request)) {
            return new ModelAndView("redirect:/admin/logout");
        }
        
        request.setAttribute("navTitle", "优惠券管理");
//        request.setAttribute("subTitle", "");
                
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminUserCouponLayout");
                
        searchForm.setKeyword(CommonUtil.charsetEncoding(searchForm.getKeyword()));
        searchForm.setUserCouponStatus(CommonUtil.charsetEncoding(searchForm.getUserCouponStatus()));
        
                
        mav.addObject("searchForm", searchForm);
        
        return mav;
    }
    
    @RequestMapping(value="/getUserCouponList", method = RequestMethod.POST)
    public String getUserCouponList(HttpServletRequest request, HttpServletResponse response) {
        int draw  = NumberUtil.strToInt(request.getParameter("draw"));
        int offset = NumberUtil.strToInt(request.getParameter("start"));
        int limit = NumberUtil.strToInt(request.getParameter("length"));
        
        String sort = request.getParameter("order[0][dir]");
        int sortColumnId = NumberUtil.strToInt(request.getParameter("order[0][column]"));
        
        String sortColumn = "";
        if (sortColumnId > 3)
            sortColumn = Constant.USER_COUPON_COLUMNS[sortColumnId - 1];
        else if (sortColumnId > 2)
            sortColumn = Constant.USER_COUPON_COLUMNS[sortColumnId - 1];
        else 
            sortColumn = Constant.USER_COUPON_COLUMNS[sortColumnId];
        
        
        
        String couponName = null;
        String userTelNo = null;
        
        String searchKey = request.getParameter("searchKey");
        if (searchKey.equals("couponName"))
            couponName = request.getParameter("keyword");
        else
            userTelNo = request.getParameter("keyword");
        
        String userCouponStatus = request.getParameter("userCouponStatus");
        int recordTotal = webDelegate.countAllUserCoupon(couponName, userTelNo, userCouponStatus);
        List<UserCouponVO> userCouponList = webDelegate.getUserCouponList(couponName, userTelNo, userCouponStatus, offset, limit, sortColumn, sort);
                
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("draw", draw);
        jsonResult.put("recordsTotal", recordTotal);
        jsonResult.put("recordsFiltered", recordTotal);
        JSONArray datas = new JSONArray();
        
        if (userCouponList != null) {
//            int num = offset;
            
            for (UserCouponVO userCoupon: userCouponList) {
                JSONObject item = new JSONObject();
                item.put("DT_RowId", userCoupon.getId());
//                item.put("0", ++num);
                item.put("0", userCoupon.getCouponId());
                item.put("1", userCoupon.getQty());
                item.put("2", userCoupon.getCouponName());
                item.put("3", userCoupon.getUserTelNo());
                String validDateTime = DateUtil.formatDate(userCoupon.getValidFromDate());
                validDateTime += " 至 ";
                validDateTime += DateUtil.formatDate(userCoupon.getValidToDate());
                item.put("4", validDateTime);
                if (!userCoupon.getStatus()) {
                    if (userCoupon.getValidToDate().compareTo(new Date()) >= 0) {
                        item.put("5", Constant.USER_COUPON_STATUS.NOT_USED);
                    } else {
                        item.put("5", Constant.USER_COUPON_STATUS.EXPIRED);
                    }
                } else {
                    item.put("5", Constant.USER_COUPON_STATUS.USED);
                }
                item.put("6", DateUtil.formatDateTime(userCoupon.getRegDate()));
                item.put("7", DateUtil.formatDateTime(userCoupon.getUsedDate()));
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