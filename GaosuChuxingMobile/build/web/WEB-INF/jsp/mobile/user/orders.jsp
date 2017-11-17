<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="delivery container-fluid container-lf-space main-body init-div-horizontal">
    <div class="row init-div-horizontal">
        <div class="col-xs-12 init-div-horizontal">
            <div class="tabbable-line">
                <ul class="nav nav-tabs" style="border-bottom: 2px solid #dfdfe1; padding-left: 10px; padding-right: 10px;">
                    <li class="text-center active" style="width: 24%;">
                        <a href="#tab_1_1" data-toggle="tab" class="tab-order" id="btn_1_1">全部</a>
                    </li>
                    <li class="text-center" style="width: 24%;">
                        <a href="#tab_1_2" data-toggle="tab" class="tab-order" id="btn_1_2">待配送</a>
                    </li>
                    <li class="text-center" style="width: 24%;">
                        <a href="#tab_1_3" data-toggle="tab" class="tab-order" id="btn_1_3">配送中</a>
                    </li>
                    <li class="text-center" style="width: 24%;">
                        <a href="#tab_1_4" data-toggle="tab" class="tab-order" id="btn_1_4">已完成</a>
                    </li>
                </ul>
                <input type="hidden" id="tab-info" />
                <div class="tab-content" style="padding: 0;"></div>
            </div>
        </div>
    </div>
</div>

<div class="navbar navbar-default navbar-fixed-bottom">
    <div class="btn-group btn-group-justified" role="group">
        <a href="<c:url value="/user/home"/>" class="btn menu-btn menu-index">
            <img width="30%" >
            <p>首页</p>
        </a>
        <a href="javascript:;" class="btn menu-btn menu-order active">
            <img width="30%">
            <p >订单</p>
        </a>
        <a href="<c:url value="/user/notification"/>" class="btn menu-btn menu-news">
            <img width="30%">
            <c:if test="${LOGIN_USER.badge > 0}">
                <span class="badge user-badge">${LOGIN_USER.badge}</span>
            </c:if>
            <c:if test="${LOGIN_USER.badge == 0}">
                <span class="badge user-badge hide"></span>
            </c:if>
            <p>消息</p>
        </a>
        <a href="<c:url value="/user/profile"/>" class="btn menu-btn menu-profile">
            <img width="30%">
            <p>我的</p>
        </a>
    </div>
</div>