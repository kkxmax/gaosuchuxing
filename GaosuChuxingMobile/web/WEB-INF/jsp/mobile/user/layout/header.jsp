<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="row init-div-horizontal">
            <div class="col-xs-4 init-div-horizontal text-left">
                <p class="navbar-text page-indicate">
                    <a href="${CLOSE_URL}" style="text-decoration: none; font-size: 15px; color: white; margin-top: 15px;">
                        <img src="<c:url value="/assets/img/page_indicate.png"/>" style="width: 12px; margin-top: -2px;" />
                        返回 关闭
                    </a>
                </p>
            </div>
            <div class="col-xs-4 init-div-horizontal text-center">
                <p class="navbar-text page-title">${TITLE}</p>
            </div>
            <div class="col-xs-4 init-div-horizontal text-right">
                <img class="page-indicate-right" src="<c:url value="/assets/img/page_indicate_right.png"/>" />
            </div>
        </div>
    </div>
</nav>
