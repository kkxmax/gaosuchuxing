<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${empty notifications}">
    <div class="delivery container-fluid container-lf-space main-body init-div-horizontal" style="margin-bottom: 0;">
        <div class="row init-div-horizontal hr" style="height: 100px; background-color: #fff;"></div>
        <div class="row init-div-horizontal">
            <div class="col-xs-12">
                <center><img src="<c:url value="/assets/img/news_icon_none@2x.png"/>" width="25%"></center>
            </div>
        </div>
        <div class="row init-div-horizontal hr" style="height: 40px; background-color: #fff;"></div>

        <div class="row init-div-horizontal">
            <div class="col-xs-12 init-div-horizontal">
                <center><p style="font-size: 17px;color:#B7B4B4;font-weight: bold;">暂无任何消息</p></center>
            </div>
        </div>
    </div>
</c:if>

<c:if test="${not empty notifications}">
    <div class="delivery container-fluid container-lf-space main-body init-div-horizontal">
        <c:set var="index" value="${0}"/>
        <c:forEach var="notification" items="${notifications}">
            <c:if test="${index > 0}">
                <div class="row init-div-horizontal hr"></div>
            </c:if>

            <div class="row init-div-horizontal">
                <div class="col-xs-12 init-div-horizontal">
                    <c:if test="${notification.orderId == 0}">
                        <div class="row init-div-horizontal" style="padding: 20px 15px 15px 20px;" onclick="onGoActivityNotice('${notification.id}', '${notification.activityNoticeId}')">
                    </c:if>
                    <c:if test="${notification.orderId > 0}">
                        <div class="row init-div-horizontal" style="padding: 20px 15px 15px 20px;" onclick="onGotoOrder('${notification.id}', '${notification.orderId}')">
                    </c:if>

                        <div class="col-xs-2 init-div-horizontal">
                            <c:if test="${notification.orderId == 0}">
                                <img src="<c:url value="/assets/img/ic_privacy_02.png"/>" style="width: 100%;" />
                            </c:if>
                            <c:if test="${notification.orderId > 0}">
                                <img src="<c:url value="/assets/img/ic_privacy_01.png"/>" style="width: 100%;" />
                            </c:if>                            
                            <c:if test="${notification.status != true}">
                                <span class="badge badge-danger custom-badge-circle">&nbsp;&nbsp;</span>
                            </c:if>

                        </div>
                        <div class="col-xs-10 init-div-horizontal" style="padding-left: 15px;">
                            <p class="init-div-vertical privacy-list-title">${notification.title}</p>
                            <p class="init-div-vertical privacy-list-content">${notification.description}</p>
                            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" var="fmtRegDate" value="${notification.regDate}" />
                            <p class="init-div-vertical privacy-list-date">${fmtRegDate}</p>
                        </div>
                    </div>
                </div>
            </div>   

            <c:set var="index" value="${index+1}"/>
        </c:forEach>
    </div>
</c:if>
                
<div class="navbar navbar-default navbar-fixed-bottom">
    <div class="btn-group btn-group-justified" role="group">
        <a href="<c:url value="/user/home"/>" class="btn menu-btn menu-index">
            <img width="30%" >
            <p>首页</p>
        </a>
        <a href="<c:url value="/user/orders"/>" class="btn menu-btn menu-order">
            <img width="30%">
            <p >订单</p>
        </a>
        <a href="<c:url value="/user/notification"/>" class="btn menu-btn menu-news active">
            <img width="30%">
            <c:if test="${LOGIN_USER.badge > 0}">
                <span class="badge">${LOGIN_USER.badge}</span>
            </c:if>
            <c:if test="${LOGIN_USER.badge == 0}">
                <span class="badge hide"></span>
            </c:if>
            <p>消息</p>
        </a>
        <a href="<c:url value="/user/profile"/>" class="btn menu-btn menu-profile">
            <img width="30%">
            <p>我的</p>
        </a>
    </div>
</div>                