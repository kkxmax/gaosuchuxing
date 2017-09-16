<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<div class="page-header -i navbar navbar-fixed-top">
    <!-- BEGIN HEADER INNER -->
    <div class="page-header-inner">
        <!-- BEGIN LOGO -->
        <div class="page-logo">
            <a href="<c:url value="/admin/index"/>">
                <img src="<c:url value="/assets/admin/pages/img/logo-sm.png"/>" alt="logo" class="logo-default"/>
            </a>
            <div class="menu-toggler sidebar-toggler hide">
                <!-- DOC: Remove the above "hide" to enable the sidebar toggler button on header -->
            </div>
        </div>
        <!-- END LOGO -->
        <!-- BEGIN RESPONSIVE MENU TOGGLER -->
        <a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">
        </a>
        <!-- END RESPONSIVE MENU TOGGLER -->
        <!-- BEGIN TOP NAVIGATION MENU -->
        <div class="top-menu">
            <ul class="nav navbar-nav pull-right">
                
                <li class="dropdown dropdown-user">
                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                        <img alt="" class="img-circle" src="<c:url value="/assets/admin/pages/img/icon_phone2.png"/>"
                        <span class="username username-hide-on-mobile">
                            ${LOGIN_USER.name} </span>
                        <!--<i class="fa fa-angle-down"></i>-->
                    </a>
<!--                    <ul class="dropdown-menu dropdown-menu-default">
                        <li>
                            <a href="<c:url value="/admin/updateUserPassword"/>" style="font-family: Microsoft YaHei;">
                                <i class="icon-lock"></i> 修改密码 </a>
                        </li>
                        <li>
                            <a href="<c:url value="/admin/logout"/>" style="font-family: Microsoft YaHei;">
                                <i class="fa fa-power-off"></i> 注销 </a>
                        </li>
                    </ul>-->
                </li>
                <!-- END USER LOGIN DROPDOWN -->
                <!-- BEGIN QUICK SIDEBAR TOGGLER -->
                <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
                <li class="dropdown dropdown-quick-sidebar-toggler">
                    <a href="<c:url value="/admin/logout"/>" class="dropdown-toggle" title="注销">
                        <i class="icon-logout"></i>
                    </a>
                </li>
                <!-- END QUICK SIDEBAR TOGGLER -->
            </ul>
        </div>
        <!-- END TOP NAVIGATION MENU -->
    </div>
    <!-- END HEADER INNER -->
</div>