<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${tabInfo == 'tab_1_1'}">
    <div class="tab-pane active" id="tab_1_1">
</c:if>
<c:if test="${tabInfo != 'tab_1_1'}">
    <div class="tab-pane" id="tab_1_1">
</c:if>
    <c:if test="${allOrders != null}">
        <c:set var="index" value="${0}" />
        <c:forEach var="order" items="${allOrders}">
            <c:if test="${index > 0}">
                <div class="row init-div-horizontal hr"></div>
            </c:if>

            <div class="row init-div-horizontal" style="padding: 20px 12px 15px 12px;">
                <div class="col-xs-3 init-div-horizontal" onclick="onGoOrder('${order.id}')">
                    <img src="/${order.shopImagePath}" style="width: 100%;" />
                </div>
                <div class="col-xs-6 init-div-horizontal" style="padding-left: 15px;" onclick="onGoOrder('${order.id}')">
                    <p class="init-div-vertical shop-list-title">${order.shopName}</p>
                    <p class="init-div-vertical shop-list-content">${order.orderQty}件商品</p>
                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm" var="fmtPayDate" value="${order.payDate}" />
                    <p class="init-div-vertical shop-list-date">${fmtPayDate}</p>
                    <c:choose>
                        <c:when test="${order.orderAmount - order.couponAmount + order.shippingFee > 0}" >
                            <fmt:formatNumber pattern="#0.00" var="fmtOrderAmount" value="${order.orderAmount - order.couponAmount + order.shippingFee}" />
                            <p class="init-div-vertical stop-list-price">￥${fmtOrderAmount}</p>
                        </c:when>
                        <c:otherwise>
                            <p class="init-div-vertical stop-list-price">￥0</p>
                        </c:otherwise>    
                    </c:choose>
                </div>
                <div class="col-xs-3 init-div-horizontal">
                    <c:if test="${order.orderStatus == '已完成'}">
                        <p class="init-div-vertical shop-list-state-complete text-center">已完成</p>
                    </c:if>                    
                    <c:if test="${order.orderStatus != '已完成'}">
                        <p class="init-div-vertical shop-list-state-sending text-center">${order.orderStatus}</p>
                    </c:if>
                    <br/><br/>
                    <c:if test="${order.orderStatus == '待配送'}">
                        <label class="btn btn-transparent btn-circle btn-sm col-xs-12 init-div-horizontal shop-list-state-label-sending" onclick="onShipping('${order.id}')"> 配送 </label>
                    </c:if>
                    <c:if test="${order.orderStatus == '配送中'}">
                        <label class="btn btn-transparent btn-circle btn-sm col-xs-12 init-div-horizontal shop-list-state-label-sending" onclick="onDelivery('${order.id}')"> 送达 </label>
                    </c:if> 

                </div>
            </div>

            <c:set var="index" value="${index + 1}" />
        </c:forEach>
    </c:if>
</div>

<c:if test="${tabInfo == 'tab_1_2'}">
    <div class="tab-pane active" id="tab_1_2">
</c:if>
<c:if test="${tabInfo != 'tab_1_2'}">
    <div class="tab-pane" id="tab_1_2">
</c:if>
    <c:if test="${waitingOrders != null}">
        <c:set var="index" value="${0}" />
        <c:forEach var="order" items="${waitingOrders}">
            <c:if test="${index > 0}">
                <div class="row init-div-horizontal hr"></div>
            </c:if>

            <div class="row init-div-horizontal" style="padding: 20px 12px 15px 12px;">
                <div class="col-xs-3 init-div-horizontal" onclick="onGoOrder('${order.id}')">
                    <img src="/${order.shopImagePath}" style="width: 100%;" />
                </div>
                <div class="col-xs-6 init-div-horizontal" style="padding-left: 15px;" onclick="onGoOrder('${order.id}')">
                    <p class="init-div-vertical shop-list-title">${order.shopName}</p>
                    <p class="init-div-vertical shop-list-content">${order.orderQty}件商品</p>
                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm" var="fmtPayDate" value="${order.payDate}" />
                    <p class="init-div-vertical shop-list-date">${fmtPayDate}</p>
                    <c:choose>
                        <c:when test="${order.orderAmount - order.couponAmount + order.shippingFee > 0}" >
                            <fmt:formatNumber pattern="#0.00" var="fmtOrderAmount" value="${order.orderAmount - order.couponAmount + order.shippingFee}" />
                            <p class="init-div-vertical stop-list-price">￥${fmtOrderAmount}</p>
                        </c:when>
                        <c:otherwise>
                            <p class="init-div-vertical stop-list-price">￥0</p>
                        </c:otherwise>    
                    </c:choose>
                </div>
                <div class="col-xs-3 init-div-horizontal">
                    <c:if test="${order.orderStatus == '已完成'}">
                        <p class="init-div-vertical shop-list-state-complete text-center">已完成</p>
                    </c:if>                    
                    <c:if test="${order.orderStatus != '已完成'}">
                        <p class="init-div-vertical shop-list-state-sending text-center">${order.orderStatus}</p>
                    </c:if>
                    <br/><br/>
                    <c:if test="${order.orderStatus == '待配送'}">
                        <label class="btn btn-transparent btn-circle btn-sm col-xs-12 init-div-horizontal shop-list-state-label-sending" onclick="onShipping('${order.id}')"> 配送 </label>
                    </c:if>
                    <c:if test="${order.orderStatus == '配送中'}">
                        <label class="btn btn-transparent btn-circle btn-sm col-xs-12 init-div-horizontal shop-list-state-label-sending" onclick="onDelivery('${order.id}')"> 送达 </label>
                    </c:if> 

                </div>
            </div>

            <c:set var="index" value="${index + 1}" />
        </c:forEach>
    </c:if>
</div>

<c:if test="${tabInfo == 'tab_1_3'}">
    <div class="tab-pane active" id="tab_1_3">
</c:if>
<c:if test="${tabInfo != 'tab_1_3'}">
    <div class="tab-pane" id="tab_1_3">
</c:if>
    <c:if test="${shippingOrders != null}">
        <c:set var="index" value="${0}" />
        <c:forEach var="order" items="${shippingOrders}">
            <c:if test="${index > 0}">
                <div class="row init-div-horizontal hr"></div>
            </c:if>

            <div class="row init-div-horizontal" style="padding: 20px 12px 15px 12px;">
                <div class="col-xs-3 init-div-horizontal" onclick="onGoOrder('${order.id}')">
                    <img src="/${order.shopImagePath}" style="width: 100%;" />
                </div>
                <div class="col-xs-6 init-div-horizontal" style="padding-left: 15px;" onclick="onGoOrder('${order.id}')">
                    <p class="init-div-vertical shop-list-title">${order.shopName}</p>
                    <p class="init-div-vertical shop-list-content">${order.orderQty}件商品</p>
                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm" var="fmtPayDate" value="${order.payDate}" />
                    <p class="init-div-vertical shop-list-date">${fmtPayDate}</p>
                    <c:choose>
                        <c:when test="${order.orderAmount - order.couponAmount + order.shippingFee > 0}" >
                            <fmt:formatNumber pattern="#0.00" var="fmtOrderAmount" value="${order.orderAmount - order.couponAmount + order.shippingFee}" />
                            <p class="init-div-vertical stop-list-price">￥${fmtOrderAmount}</p>
                        </c:when>
                        <c:otherwise>
                            <p class="init-div-vertical stop-list-price">￥0</p>
                        </c:otherwise>    
                    </c:choose>
                </div>
                <div class="col-xs-3 init-div-horizontal">
                    <c:if test="${order.orderStatus == '已完成'}">
                        <p class="init-div-vertical shop-list-state-complete text-center">已完成</p>
                    </c:if>                    
                    <c:if test="${order.orderStatus != '已完成'}">
                        <p class="init-div-vertical shop-list-state-sending text-center">${order.orderStatus}</p>
                    </c:if>
                    <br/><br/>
                    <c:if test="${order.orderStatus == '待配送'}">
                        <label class="btn btn-transparent btn-circle btn-sm col-xs-12 init-div-horizontal shop-list-state-label-sending" onclick="onShipping('${order.id}')"> 配送 </label>
                    </c:if>
                    <c:if test="${order.orderStatus == '配送中'}">
                        <label class="btn btn-transparent btn-circle btn-sm col-xs-12 init-div-horizontal shop-list-state-label-sending" onclick="onDelivery('${order.id}')"> 送达 </label>
                    </c:if> 

                </div>
            </div>

            <c:set var="index" value="${index + 1}" />
        </c:forEach>
    </c:if>
</div>

<c:if test="${tabInfo == 'tab_1_4'}">
    <div class="tab-pane active" id="tab_1_4">
</c:if>
<c:if test="${tabInfo != 'tab_1_4'}">
    <div class="tab-pane" id="tab_1_4">
</c:if>
    <c:if test="${completedOrders != null}">
        <c:set var="index" value="${0}" />
        <c:forEach var="order" items="${completedOrders}">
            <c:if test="${index > 0}">
                <div class="row init-div-horizontal hr"></div>
            </c:if>

            <div class="row init-div-horizontal" style="padding: 20px 12px 15px 12px;">
                <div class="col-xs-3 init-div-horizontal" onclick="onGoOrder('${order.id}')">
                    <img src="/${order.shopImagePath}" style="width: 100%;" />
                </div>
                <div class="col-xs-6 init-div-horizontal" style="padding-left: 15px;" onclick="onGoOrder('${order.id}')">
                    <p class="init-div-vertical shop-list-title">${order.shopName}</p>
                    <p class="init-div-vertical shop-list-content">${order.orderQty}件商品</p>
                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm" var="fmtPayDate" value="${order.payDate}" />
                    <p class="init-div-vertical shop-list-date">${fmtPayDate}</p>
                    <c:choose>
                        <c:when test="${order.orderAmount - order.couponAmount + order.shippingFee > 0}" >
                            <fmt:formatNumber pattern="#0.00" var="fmtOrderAmount" value="${order.orderAmount - order.couponAmount + order.shippingFee}" />
                            <p class="init-div-vertical stop-list-price">￥${fmtOrderAmount}</p>
                        </c:when>
                        <c:otherwise>
                            <p class="init-div-vertical stop-list-price">￥0</p>
                        </c:otherwise>    
                    </c:choose>
                </div>
                <div class="col-xs-3 init-div-horizontal">
                    <c:if test="${order.orderStatus == '已完成'}">
                        <p class="init-div-vertical shop-list-state-complete text-center">已完成</p>
                    </c:if>                    
                    <c:if test="${order.orderStatus != '已完成'}">
                        <p class="init-div-vertical shop-list-state-sending text-center">${order.orderStatus}</p>
                    </c:if>
                    <br/><br/>
                    <c:if test="${order.orderStatus == '待配送'}">
                        <label class="btn btn-transparent btn-circle btn-sm col-xs-12 init-div-horizontal shop-list-state-label-sending" onclick="onShipping('${order.id}')"> 配送 </label>
                    </c:if>
                    <c:if test="${order.orderStatus == '配送中'}">
                        <label class="btn btn-transparent btn-circle btn-sm col-xs-12 init-div-horizontal shop-list-state-label-sending" onclick="onDelivery('${order.id}')"> 送达 </label>
                    </c:if> 

                </div>
            </div>

            <c:set var="index" value="${index + 1}" />
        </c:forEach>
    </c:if>
</div>
