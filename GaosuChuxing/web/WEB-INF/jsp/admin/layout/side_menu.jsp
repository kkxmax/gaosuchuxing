<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<div class="page-sidebar-wrapper">
    <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
    <!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
    <div class="page-sidebar navbar-collapse collapse">
        <!-- BEGIN SIDEBAR MENU -->
        <!-- DOC: Apply "page-sidebar-menu-light" class right after "page-sidebar-menu" to enable light sidebar menu style(without borders) -->
        <!-- DOC: Apply "page-sidebar-menu-hover-submenu" class right after "page-sidebar-menu" to enable hoverable(hover vs accordion) sub menu mode -->
        <!-- DOC: Apply "page-sidebar-menu-closed" class right after "page-sidebar-menu" to collapse("page-sidebar-closed" class must be applied to the body element) the sidebar sub menu mode -->
        <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
        <!-- DOC: Set data-keep-expand="true" to keep the submenues expanded -->
        <!-- DOC: Set data-auto-speed="200" to adjust the sub menu slide up/down speed -->
        <ul class="page-sidebar-menu" data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
            <!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->
            <li class="sidebar-toggler-wrapper">
                <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                <div class="sidebar-toggler">
                </div>
                <!-- END SIDEBAR TOGGLER BUTTON -->
            </li>
            <li>&nbsp;</li>
            
            <!-- START HOME -->
            <c:if test="${navTitle == '首页'}">
                <li class="start active">
                    <a href="<c:url value="/admin/index"/>">
                        <i class="icon-home"></i>
                        <span class="title">首页</span>
                        <span class="selected"></span>
                    </a>
                </li>
            </c:if>
                
            <c:if test="${navTitle != '首页'}">
                <li class="start">
                    <a href="<c:url value="/admin/index"/>">
                        <i class="icon-home"></i>
                        <span class="title">首页</span>
                    </a>
                </li>
            </c:if>                
            <!-- END HOME -->
            
            <!-- START USER -->
            <c:if test="${navTitle == '用户管理'}">
                <li class="start active">
                    <a href="<c:url value="/admin/userList"/>">
                        <i class="icon-users"></i>
                        <span class="title">用户管理</span>
                        <span class="selected"></span>
                    </a>
                </li>
            </c:if>
                
            <c:if test="${navTitle != '用户管理'}">
                <li class="start">
                    <a href="<c:url value="/admin/userList"/>">
                        <i class="icon-users"></i>
                        <span class="title">用户管理</span>
                    </a>
                </li>
            </c:if>                
            <!-- END USER -->
            
            <!-- START DELIVERY -->
            <c:if test="${navTitle == '配送员管理'}">
                <li class="start active">
                    <a href="<c:url value="/admin/deliverList"/>">
                        <i class="fa fa-taxi"></i>
                        <span class="title">配送员管理</span>
                        <span class="selected"></span>
                    </a>
                </li>
            </c:if>
                
            <c:if test="${navTitle != '配送员管理'}">
                <li class="start">
                    <a href="<c:url value="/admin/deliverList"/>">
                        <i class="fa fa-taxi"></i>
                        <span class="title">配送员管理</span>
                    </a>
                </li>
            </c:if>                
            <!-- END DELIVERY -->
            
            <!-- START AREA -->
            <c:if test="${navTitle == '服务区管理'}">
                <li class="start active">
                    <a href="<c:url value="/admin/stationList"/>">
                        <i class="icon-pointer"></i>
                        <span class="title">服务区管理</span>
                        <span class="selected"></span>
                    </a>
                </li>
            </c:if>
                
            <c:if test="${navTitle != '服务区管理'}">
                <li class="start">
                    <a href="<c:url value="/admin/stationList"/>">
                        <i class="icon-pointer"></i>
                        <span class="title">服务区管理</span>
                    </a>
                </li>
            </c:if>                
            <!-- END AREA -->
            
            <!-- START SHOP -->
            <c:if test="${navTitle == '门店管理'}">
                <li class="active open">
                    <a href="javascript:;">
                        <i class="fa fa-cutlery"></i>
                        <span class="title">门店管理</span>
                        <span class="selected"></span>
                        <span class="arrow open"></span>
                    </a>
                    <ul class="sub-menu">
                        <c:if test="${subTitle == '公司门店'}">
                            <li class="active">
                                <a href="<c:url value="/admin/shopList/company"/>">
                                    公司门店
                                </a>
                            </li>
                            
                            <li>
                                <a href="<c:url value="/admin/shopList/service"/>">
                                    服务区门店
                                </a>
                            </li>
                            <li>
                                <a href="<c:url value="/admin/shopKindList"/>">
                                    门店类型
                                </a>
                            </li>
                            
                        </c:if>
                        
                        <c:if test="${subTitle == '服务区门店'}">
                            <li>
                                <a href="<c:url value="/admin/shopList/company"/>">
                                    公司门店
                                </a>
                            </li>
                            
                            <li class="active">
                                <a href="<c:url value="/admin/shopList/service"/>">
                                    服务区门店
                                </a>
                            </li>
                            <li>
                                <a href="<c:url value="/admin/shopKindList"/>">
                                    门店类型
                                </a>
                            </li>
                        </c:if>
                            
                        <c:if test="${subTitle == '门店类型'}">
                            <li>
                                <a href="<c:url value="/admin/shopList/company"/>">
                                    公司门店
                                </a>
                            </li>
                            
                            <li>
                                <a href="<c:url value="/admin/shopList/service"/>">
                                    服务区门店
                                </a>
                            </li>
                            <li class="active">
                                <a href="<c:url value="/admin/shopKindList"/>">
                                    门店类型
                                </a>
                            </li>
                        </c:if>    
                    </ul>
                </li>
            </c:if>    
            
            <c:if test="${navTitle != '门店管理'}">
                <li>
                    <a href="javascript:;">
                        <i class="fa fa-cutlery"></i>
                        <span class="title">门店管理</span>
                        <span class="arrow"></span>
                    </a>
                    <ul class="sub-menu">
                        <li>
                            <a href="<c:url value="/admin/shopList/company"/>">
                                公司门店
                            </a>
                        </li>

                        <li>
                            <a href="<c:url value="/admin/shopList/service"/>">
                                服务区门店
                            </a>
                        </li>
                        <li>
                            <a href="<c:url value="/admin/shopKindList"/>">
                                门店类型
                            </a>
                        </li>
                    </ul>
                </li>
            </c:if>
            <!-- END SHOP -->
            
            <!-- START GOODS -->
            <c:if test="${navTitle == '商品管理'}">
                <li class="start active">
                    <a href="<c:url value="/admin/goodsList"/>">
                        <i class="icon-present"></i>
                        <span class="title">商品管理</span>
                        <span class="selected"></span>
                    </a>
                </li>
            </c:if>
                
            <c:if test="${navTitle != '商品管理'}">
                <li class="start">
                    <a href="<c:url value="/admin/goodsList"/>">
                        <i class="icon-present"></i>  
                        <span class="title">商品管理</span>
                    </a>
                </li>
            </c:if>                
            <!-- END GOODS -->
            
            <!-- START GOODS KIND -->
            <c:if test="${navTitle == '商品分类'}">
                <li class="start active">
                    <a href="<c:url value="/admin/goodsKindList"/>">
                        <i class="icon-layers"></i>
                        <span class="title">商品分类</span>
                        <span class="selected"></span>
                    </a>
                </li>
            </c:if>
                
            <c:if test="${navTitle != '商品分类'}">
                <li class="start">
                    <a href="<c:url value="/admin/goodsKindList"/>">
                        <i class="icon-layers"></i>  
                        <span class="title">商品分类</span>
                    </a>
                </li>
            </c:if>                
            <!-- END GOODS KIND -->
            
            <!-- START ORDERS -->
            <c:if test="${navTitle == '订单管理'}">
                <li class="start active">
                    <a href="<c:url value="/admin/orderList"/>">
                        <i class="icon-basket-loaded"></i>
                        <span class="title">订单管理</span>
                        <span class="selected"></span>
                    </a>
                </li>
            </c:if>
                
            <c:if test="${navTitle != '订单管理'}">
                <li class="start">
                    <a href="<c:url value="/admin/orderList"/>">
                        <i class="icon-basket-loaded"></i>  
                        <span class="title">订单管理</span>
                    </a>
                </li>
            </c:if>                
            <!-- END ORDERS -->
            
            <!-- START STAT DELIVER -->
            <c:if test="${navTitle == '配送员统计'}">
                <li class="start active">
                    <a href="<c:url value="/admin/statDeliver"/>">
                        <i class="icon-book-open"></i>
                        <span class="title">配送员统计</span>
                        <span class="selected"></span>
                    </a>
                </li>
            </c:if>
                
            <c:if test="${navTitle != '配送员统计'}">
                <li class="start">
                    <a href="<c:url value="/admin/statDeliver"/>">
                        <i class="icon-book-open"></i>  
                        <span class="title">配送员统计</span>
                    </a>
                </li>
            </c:if>                
            <!-- END STAT DELIVER -->
            
            <!-- START ACTIVITY NOTIFY -->
            <c:if test="${navTitle == '活动预告'}">
                <li class="start active">
                    <a href="<c:url value="/admin/activityNoticeList"/>">
                        <i class="icon-volume-2"></i>
                        <span class="title">活动预告</span>
                        <span class="selected"></span>
                    </a>
                </li>
            </c:if>
                
            <c:if test="${navTitle != '活动预告'}">
                <li class="start">
                    <a href="<c:url value="/admin/activityNoticeList"/>">
                        <i class="icon-volume-2"></i>  
                        <span class="title">活动预告</span>
                    </a>
                </li>
            </c:if>                
            <!-- END ACTIVITY NOTIFY -->
            
            <!-- START ACTIVITY -->
            <c:if test="${navTitle == '活动管理'}">
                <li class="start active">
                    <a href="<c:url value="/admin/activityList"/>">
                        <i class="icon-calculator"></i>
                        <span class="title">活动管理</span>
                        <span class="selected"></span>
                    </a>
                </li>
            </c:if>
                
            <c:if test="${navTitle != '活动管理'}">
                <li class="start">
                    <a href="<c:url value="/admin/activityList"/>">
                        <i class="icon-calculator"></i>  
                        <span class="title">活动管理</span>
                    </a>
                </li>
            </c:if>                
            <!-- END ACTIVITY -->
            
            <!-- START COUPON -->
            <c:if test="${navTitle == '优惠券管理'}">
                <li class="start active">
                    <a href="<c:url value="/admin/userCouponList"/>">
                        <i class="icon-credit-card"></i>
                        <span class="title">优惠券管理</span>
                        <span class="selected"></span>
                    </a>
                </li>
            </c:if>
                
            <c:if test="${navTitle != '优惠券管理'}">
                <li class="start">
                    <a href="<c:url value="/admin/userCouponList"/>">
                        <i class="icon-credit-card"></i>  
                        <span class="title">优惠券管理</span>
                    </a>
                </li>
            </c:if>                
            <!-- END COUPON -->
            
            <!-- START FEEDBACK -->
            <c:if test="${navTitle == '意见反馈'}">
                <li class="start active">
                    <a href="<c:url value="/admin/feedbackList"/>">
                        <i class="icon-feed"></i>
                        <span class="title">意见反馈</span>
                        <span class="selected"></span>
                    </a>
                </li>
            </c:if>
                
            <c:if test="${navTitle != '意见反馈'}">
                <li class="start">
                    <a href="<c:url value="/admin/feedbackList"/>">
                        <i class="icon-feed"></i>  
                        <span class="title">意见反馈</span>
                    </a>
                </li>
            </c:if>                
            <!-- END FEEDBACK -->
            
            <!-- START SYSTEM -->
            <c:if test="${navTitle == '系统管理'}">
                <li class="active open">
                    <a href="javascript:;">
                        <i class="icon-settings"></i>
                        <span class="title">系统管理</span>
                        <span class="selected"></span>
                        <span class="arrow open"></span>
                    </a>
                    <ul class="sub-menu">
                        <c:if test="${subTitle == '运营人员管理'}">
                            <li class="active">
                                <a href="<c:url value="/admin/managerList"/>">
                                    运营人员管理
                                </a>
                            </li>
                            <li>
                                <a href="<c:url value="/admin/groupList"/>">
                                    角色管理
                                </a>
                            </li>
                            <li>
                                <a href="<c:url value="/admin/passwordForm"/>">
                                    修改密码
                                </a>
                            </li>
                        </c:if>
                        
                        <c:if test="${subTitle == '角色管理'}">
                            <li>
                                <a href="<c:url value="/admin/managerList"/>">
                                    运营人员管理
                                </a>
                            </li>
                            <li class="active">
                                <a href="<c:url value="/admin/groupList"/>">
                                    角色管理
                                </a>
                            </li>
                            <li>
                                <a href="<c:url value="/admin/passwordForm"/>">
                                    修改密码
                                </a>
                            </li>
                        </c:if>
                        <c:if test="${subTitle == '修改密码'}">
                            <li>
                                <a href="<c:url value="/admin/managerList"/>">
                                    运营人员管理
                                </a>
                            </li>
                            <li>
                                <a href="<c:url value="/admin/groupList"/>">
                                    角色管理
                                </a>
                            </li>
                            <li class="active">
                                <a href="<c:url value="/admin/passwordForm"/>">
                                    修改密码
                                </a>
                            </li>
                        </c:if>    
                    </ul>
                </li>
            </c:if>    
            
            <c:if test="${navTitle != '系统管理'}">
                <li>
                    <a href="javascript:;">
                        <i class="icon-settings"></i>
                        <span class="title">系统管理</span>
                        <span class="arrow"></span>
                    </a>
                    <ul class="sub-menu">
                        <li>
                            <a href="<c:url value="/admin/managerList"/>">
                                运营人员管理
                            </a>
                        </li>
                        <li>
                            <a href="<c:url value="/admin/groupList"/>">
                                角色管理
                            </a>
                        </li>
                        <li>
                            <a href="<c:url value="/admin/passwordForm"/>">
                                修改密码
                            </a>
                        </li>
                    </ul>
                </li>
            </c:if>
            <!-- END SYSTEM -->
            
        </ul>
        <!-- END SIDEBAR MENU -->
    </div>
</div>
