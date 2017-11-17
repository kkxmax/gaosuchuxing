<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${not empty orderTime}">
    <div class="col-xs-8 bt_cart_inner">
        <c:choose>
            <c:when test="${order.orderQty > 0}">
                <div class="ic_cart" onclick="onShowCart('${order.id}');">
                    <span class="add-badge badge">${order.orderQty}</span>
            </c:when>
            <c:otherwise>
                <div class="ic_cart">
                    <span class="add-badge badge hide">0</span>
            </c:otherwise>
        </c:choose>        
        </div>
        <div class="price">
            <c:choose>
                <c:when test="${order.orderQty > 0}">
                    <fmt:formatNumber pattern="#0.##" var="fmtCartAmount" value="${order.orderAmount}" />
                    <p style="font-size: 15px; margin-bottom: 0px" id="amount-info">共￥${fmtCartAmount}</p>
                    <p style="font-size: 11px" id="shipping-fee-info">另需配送费￥${shop.shippingFee}</p>
                </c:when>
                <c:otherwise>
                    <p style="font-size: 15px; margin-bottom: 0px; color: #b3b3b3; margin-top: 10px" id="amount-info">购物车是空的</p>
                    <p style="font-size: 11px" id="shipping-fee-info"></p>
                </c:otherwise>
            </c:choose>            
        </div>
    </div>

    <c:choose>
        <c:when test="${order.orderAmount > 0}">
            <c:if test="${order.orderAmount >= shop.startFee}">
                <div class="col-xs-4 init-div oper" onclick="onGoOrderSubmit('${order.id}')" id="btn-order-submit">
                    提交订单
                </div>
            </c:if>

            <c:if test="${order.orderAmount < shop.startFee}">
                <div class="col-xs-4 init-div des" id="btn-order-submit">
                    <c:if test="${order.orderAmount == 0}">
                        ${shop.startFee}元起送
                    </c:if>
                    <c:if test="${order.orderAmount > 0}">
                        <fmt:formatNumber pattern="#0.##" var="fmtDiffAmount" value="${shop.startFee - order.orderAmount}" />
                        差${fmtDiffAmount}元起送
                    </c:if>    
                </div>
            </c:if>
        </c:when>

        <c:otherwise>
            <div class="col-xs-4 init-div des" id="btn-order-submit">
                ${shop.startFee}元起送    
            </div>
        </c:otherwise>
    </c:choose>    

</c:if>    