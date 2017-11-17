<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="index" value="${0}" />
<c:forEach var="order" items="${orders}">
    <c:if test="${index > 0}">
        <div class="row init-div-horizontal hr"></div>
    </c:if>

    <div class="row init-div-horizontal" style="padding: 20px 12px 15px 12px;">
        <div class="col-xs-3 init-div-horizontal">
            <img src="/${order.shopImagePath}" style="width: 100%;" />
        </div>
        <div class="col-xs-6 init-div-horizontal" style="padding-left: 15px;">
            <p class="init-div-vertical shop-list-title">${order.shopName}</p>
            <p class="init-div-vertical shop-list-content">含${order.orderQty}件商品</p>
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" var="fmtRegDate" value="${order.regDate}" />
            <p class="init-div-vertical shop-list-date">${fmtRegDate}</p>
            <fmt:formatNumber pattern="#0.00" var="fmtOrderAmount" value="${order.orderAmount}" />
            <p class="init-div-vertical stop-list-price">￥${fmtOrderAmount}</p>
        </div>
        <div class="col-xs-3 init-div-horizontal">
            <p class="init-div-vertical shop-list-state-sending text-right" onclick="onGoShop('${order.shopId}')">
                <img src="<c:url value="/assets/img/porfile_btn_right@2x.png"/>" style="width: 8px; vertical-align: sub;" />
            </p>
            <br/><br/>
            <label class="btn btn-transparent btn-circle btn-sm col-xs-12 init-div-horizontal shop-list-state-label-sending" onclick="onGoOrderSubmit('${order.id}');"> 去结算 </label>
        </div>
    </div>

    <c:set var="index" value="${index+1}" />
</c:forEach>