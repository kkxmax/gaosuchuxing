<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="row init-div-horizontal"  style="background: url('<c:url value="/assets/img/back_dot_body3.png"/>');">
    <div style="float: left;background: url('<c:url value="/assets/img/back_dot_left.png"/>');background-repeat: no-repeat;width: 14px; height: 200px;"></div>
    <div style="float: right;background: url('<c:url value="/assets/img/back_dot_right.png"/>');background-repeat: no-repeat;width: 10px; height: 200px;"></div>
    <div style="margin: 25px 10px 0 20px;">
        <div class="col-xs-12 init-div-horizontal">
            <div class="col-xs-6 init-div-horizontal">
                <p style="font-size: 15px;">配送费</p>
            </div>
            <div class="col-xs-6 init-div-horizontal text-right" style=''>
                <p style="font-size: 15px;margin-right: 10px">￥${order.shippingFee}</p>
            </div>
        </div>
        <div class="col-xs-12 init-div-horizontal" style="margin-top: 40px">
            <div class="col-xs-6 init-div-horizontal">
                <p style="font-size: 15px;color: red">优惠券</p>
            </div>
            <div class="col-xs-6 init-div-horizontal text-right">
                <c:choose>
                    <c:when test="${useCoupon == true}">
                        <p style="font-size: 15px;margin-right: 10px;color: red">-￥${couponAmount}</p>
                    </c:when>    
                    <c:otherwise>
                        <p style="font-size: 15px;margin-right: 10px;color: red" onclick="onGoUserCoupon('${order.id}');">使用</p>
                    </c:otherwise>    
                </c:choose>
            </div>
        </div>
        <div class="col-xs-12 init-div-horizontal" style="margin-top: 20px">                        
            <p class='text-right' style="font-size: 16px; margin-top: 3px; margin-right: 5px;">
                实付:
                <c:choose>
                    <c:when test="${order.orderAmount + order.shippingFee - couponAmount > 0}">
                        <fmt:formatNumber pattern="#0.##" var="fmtAmount" value="${order.orderAmount + order.shippingFee - couponAmount}" />
                        <span style="font-size: 19px; color:red;font-weight: bold">￥${fmtAmount}</span>
                    </c:when>
                    <c:otherwise>
                        <span style="font-size: 19px; color:red;font-weight: bold">￥0</span>
                    </c:otherwise>
                </c:choose>
            </p>
        </div>
    </div>
</div>
