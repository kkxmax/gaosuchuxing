<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div id="content">
</div>

<div class="navbar navbar-default navbar-fixed-bottom">
    <div class="btn-group btn-group-justified" role="group">
        <a href="<c:url value="/deliver/home"/>" class="btn menu-btn menu-order">
            <img width="30%" >
            <p>配送订单</p>
        </a>
        <a href="<c:url value="/deliver/notification"/>" class="btn menu-btn menu-news active">
            <img width="30%">
            <%--<c:if test="${LOGIN_DELIVER.badge > 0}">--%>
                <!--<span class="badge user-badge">${LOGIN_USER.badge}</span>-->
            <%--</c:if>--%>
            <%--<c:if test="${LOGIN_DELIVER.badge == 0}">--%>
                <span class="badge user-badge hide"></span>
            <%--</c:if>--%>
            <p>消息</p>
        </a>
        <a href="<c:url value="/deliver/profile"/>" class="btn menu-btn menu-profile">
            <img width="30%">
            <p>个人中心</p>
        </a>
    </div>
</div>