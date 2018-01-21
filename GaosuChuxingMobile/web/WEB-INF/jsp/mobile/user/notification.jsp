<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div id="content"></div>
                
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
            <%--<c:if test="${LOGIN_USER.badge > 0}">--%>
                <!--<span class="badge user-badge">${LOGIN_USER.badge}</span>-->
            <%--</c:if>--%>
            <%--<c:if test="${LOGIN_USER.badge == 0}">--%>
                <span class="badge user-badge hide"></span>
            <%--</c:if>--%>
            <p>消息</p>
        </a>
        <a href="<c:url value="/user/profile"/>" class="btn menu-btn menu-profile">
            <img width="30%">
            <p>我的</p>
        </a>
    </div>
</div>                