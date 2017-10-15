<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:choose>
    <c:when test="${coupons == null}">
        <div class="delivery container-fluid container-lf-space main-body init-div-horizontal" style="margin-bottom: 0;">

            <div class="row init-div-horizontal hr" style="height: 80px; background-color: #fff;"></div>
            <div class="row init-div-horizontal">
                <div class="col-xs-12">
                    <center><img src="<c:url value="/assets/img/sale_icon_none@2x.png"/>" width="28%"></center>
                </div>
            </div>
            <div class="row init-div-horizontal hr" style="height: 40px; background-color: #fff;"></div>
            
            <div class="row init-div-horizontal">
                <div class="col-xs-12 init-div-horizontal">
                    <center><p style="font-size: 17px;color:#B7B4B4;font-weight: bold;">暂无任何优惠券</p></center>
                </div>
            </div>
        </div>
    </c:when>
    
    <c:otherwise>
        <div class="delivery container-fluid container-lf-space main-body init-div-horizontal shop" style="padding: 10px 15px;">
            <div class="row init-div-horizontal">                
                <div class="col-xs-12 init-div-horizontal">
                    <c:set var="index" value="${0}" />
                    <c:forEach var="coupon" items="${coupons}">
                        <fmt:formatDate pattern="yyyy-MM-dd" var="fmtValidFrom" value="${coupon.validFromDate}" />
                        <fmt:formatDate pattern="yyyy-MM-dd" var="fmtValidTo" value="${coupon.validToDate}" />
                        <fmt:formatNumber pattern="0.##" var="fmtAmount" value="${coupon.amount}" />
                        
                        <c:choose>
                            <c:when test="${coupon.couponType == 'full_type'}">
                                <c:if test="${not empty useCoupon}">
                                    <div class="row init-div-horizontal"  style="background: url('<c:url value="/assets/img/radius_back_body.png"/>'); height: 130px; margin-top: 20px;" onclick="onFullCoupon('${coupon.couponId}', '${orderId}')">
                                </c:if>
                                <c:if test="${empty useCoupon}">
                                    <div class="row init-div-horizontal"  style="background: url('<c:url value="/assets/img/radius_back_body.png"/>'); height: 130px; margin-top: 20px;">
                                </c:if>
                                    <div style="background: url('<c:url value="/assets/img/blue_radius_back.png"/>');float:left;width: 55px;height: 130px;background-repeat: no-repeat"></div>
                                    <div style="background: url('<c:url value="/assets/img/radius_right.png"/>');float:right;width: 10px;height: 130px;background-repeat: no-repeat"></div>

                                    <div style="margin: 0px 15px 0 60px;">
                                        <div class="col-xs-12 init-div-horizontal" style="margin-top: 10px">
                                            <div class="col-xs-5 init-div-horizontal">
                                                <p class="custom-text-over-status" style="font-size:18px;color: #3E6DBB;font-weight: bold;">${coupon.couponName}可用</p>    
                                            </div>
                                            <div class="col-xs-7 init-div-horizontal text-right">                                            
                                                <p class="custom-text-over-status" style="font-size:15px;color: #B7A6A6">${fmtValidFrom} - ${fmtValidTo}</p>
                                            </div>
                                        </div>
                                        <div class="col-xs-12 init-div-horizontal" style="margin-top: 5px">
                                            <p style="color: #3E6DBB;font-weight: bold;"><font>￥</font>&nbsp;<font style="font-size: 25px">${fmtAmount}</font></p>
                                        </div>
                                        <div class="col-xs-12 init-div-horizontal" style="">
                                            <p class="custom-text-over-status" style="font-size:15px;color: #3E6DBB;">全品类优惠券，所有高点服务区可用</p>
                                        </div>
                                    </div>
                            </c:when>
                                
                            <c:otherwise>
                                <c:if test="${not empty useCoupon}">
                                    <div class="row init-div-horizontal"  style="background: url('<c:url value="/assets/img/radius_back_body.png"/>'); height: 130px; margin-top: 20px;" onclick="onUnLimitedCoupon('${coupon.id}', '${orderId}');">
                                </c:if>
                                <c:if test="${empty useCoupon}">
                                    <div class="row init-div-horizontal"  style="background: url('<c:url value="/assets/img/radius_back_body.png"/>'); height: 130px; margin-top: 20px;">
                                </c:if>
                                    <div style="background: url('<c:url value="/assets/img/red_radius_back.png"/>');float:left;width: 55px;height: 130px;background-repeat: no-repeat"></div>
                                    <div style="background: url('<c:url value="/assets/img/radius_right.png"/>');float:right;width: 10px;height: 130px;background-repeat: no-repeat"></div>
                                    <div style="margin: 0px 15px 0 60px;">
                                        <div class="col-xs-12 init-div-horizontal" style="margin-top: 10px">
                                            <div class="col-xs-5 init-div-horizontal">
                                                <p class="custom-text-over-status" style="font-size:18px;color: #FF3E59;font-weight: bold;">满0元可用</p>
                                            </div>
                                            <div class="col-xs-7 init-div-horizontal text-right">
                                                <p class="custom-text-over-status" style="font-size:15px;color: #B7A6A6">${fmtValidFrom} - ${fmtValidTo}</p>
                                            </div>
                                        </div>
                                        <div class="col-xs-12 init-div-horizontal" style="margin-top: 5px">                                        
                                            <p style="color: #FF3E59;font-weight: bold;"><font>￥</font>&nbsp;<font style="font-size: 25px">${fmtAmount}</font></p>
                                        </div>
                                        <div class="col-xs-12 init-div-horizontal" style="">
                                            <p class="custom-text-over-status" style="font-size:15px;color: #FF3E59;">全品类优惠券，所有高点服务区可用</p>
                                        </div>
                                    </div>
                            </c:otherwise>
                        </c:choose>    
                        </div>        
                                    
                        <c:set var="index" value="${index+1}" />
                    </c:forEach>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>
                    
                    

