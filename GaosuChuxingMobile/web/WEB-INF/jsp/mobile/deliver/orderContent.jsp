<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="delivery container-fluid container-lf-space main-body init-div-horizontal shop" style="background: #f5f5f5;padding: 0px 15px; margin-bottom: 60px;">
    <div class="row init-div-horizontal">
        <div class="col-xs-12 init-div-horizontal">
            <div class="row init-div-horizontal hr" style="height: 10px"></div>
            <div class="row init-div-horizontal" style="padding: 20px 12px 15px 12px;background: white;">
                <div class="col-xs-3 init-div-horizontal">
                    <img src="/${shop.imagePath}" style="width: 100%;" />
                </div>
                <div class="col-xs-6 init-div-horizontal" style="padding-left: 15px;">
                    <div class="col-xs-12 init-div-horizontal" style="margin-top: 5px">
                        <p class="init-div-vertical order-detail-title">${shop.name}</p>
                    </div>
                    <div class="col-xs-12 init-div-horizontal" style="margin-top: 10px">
                        <p class="order-detail-content">
                            ${shop.districtParentName}${shop.districtName}${shop.address}
                        </p>
                    </div>
                </div>
                <div class="col-xs-3 init-div-horizontal">
                    <c:if test="${order.orderStatus == '已完成'}">
                        <p class="text-right shop-list-state-complete" style="margin-top: 7px;">已完成</p>
                    </c:if>                    
                    <c:if test="${order.orderStatus != '已完成'}">
                        <p class="text-right shop-list-state-sending" style="margin-top: 7px;">
                            ${order.orderStatus}
                        </p>
                    </c:if>
                </div>
            </div>
            <div class="row init-div-horizontal hr" style="height:5px"></div>

            <div class="row init-div-horizontal" style="padding: 10px 12px;background: white;">
                <div class="col-xs-1 init-div-horizontal">
                    <img src="/${shop.imagePath}" style="width: 100%;border-radius: 50% !important" />
                </div>
                <div class="col-xs-10">
                    <p class="init-div-vertical" style="font-size: 15px;font-weight: bold;margin-top: 3px">${shop.name}</p>
                </div>
            </div>
            <div class="row init-div-horizontal hr"></div>
            
            <c:set var="index" value="${0}"/>
            <c:forEach var="goods" items="${goodsList}">
                <c:if test="${index > 0}">
                    <div class="row init-div-horizontal" style="background-color: white !important;height: 5px"></div>
                </c:if>
                    
                <div class="row init-div-horizontal" style="background-color: #f5f5f5;padding: 5px 0px 0px 12px;">
                    <div class="col-xs-12 init-div-horizontal">
                        <div class="col-xs-2 init-div-horizontal">
                            <img src="/${goods.goodsImagePath}" style="width: 80%;" />
                        </div>
                        <div class="col-xs-6 init-div-horizontal">
                            <div class="col-xs-12 init-div-horizontal" style="margin-top: 3px">
                                <p class="init-div-vertical" style="font-size: 15px;font-weight: bold">${goods.goodsName}</p>
                            </div>
                            <div class="col-xs-12 init-div-horizontal" style="margin-top: 5px">
                                <div class="col-xs-6 init-div-horizontal">
                                    <p class="init-div-vertical" style="font-size: 15px;color:#B7A6A6">×${goods.qty}</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-4 init-div-horizontal">
                            <div class="col-xs-12 init-div-horizontal">
                                <fmt:formatNumber pattern="#0.##" var="fmtGoodsAmount" value="${goods.price * goods.qty}" />
                                <p class="init-div-vertical text-right" style="margin-right: 10px;font-size: 15px;margin-top: 5px">￥${fmtGoodsAmount}</p>
                            </div>
                        </div>
                    </div>
                </div>    
                
                <c:set var="index" value="${index+1}"/>
            </c:forEach>
            
                <div class="row init-div-horizontal"  style="background: url('<c:url value="/assets/img/back_dot_body2.png"/>'); height: 358px;">
                <div style="float: left;background: url('<c:url value="/assets/img/back_dot_left2.png"/>');background-repeat: no-repeat;width: 14px;height: 358px"></div>
                <div style="float: right;background: url('<c:url value="/assets/img/back_dot_right2.png"/>');background-repeat: no-repeat;width: 10px;height: 358px;"></div>
                <div style="margin: 45px 10px 0 20px;">
                    <div class="col-xs-12 init-div-horizontal">
                        <div class="col-xs-6 init-div-horizontal">
                            <p style="font-size: 15px">合计</p>
                        </div>
                        <div class="col-xs-6 init-div-horizontal text-right" style=''>
                            <p style="font-size: 15px;margin-right: 10px">合计: ￥${order.orderAmount}</p>
                        </div>
                    </div>

                    <div class="col-xs-12 init-div-horizontal" style="margin-top: 65px">
                        <div class="col-xs-6 init-div-horizontal">
                            <p style="font-size: 15px;">配送费</p>
                        </div>
                        <div class="col-xs-6 init-div-horizontal text-right" style=''>
                            <p style="font-size: 15px;margin-right: 10px">￥${order.shippingFee}</p>
                        </div>
                    </div>
                    
                    <div class="col-xs-12 init-div-horizontal" style="margin-top: 50px">
                        <div class="col-xs-6 init-div-horizontal">
                            <p style="font-size: 15px;color: red">优惠券</p>
                        </div>
                        <div class="col-xs-6 init-div-horizontal text-right" style=''>
                            <p style="font-size: 15px;margin-right: 10px;color: red">-￥${couponAmount}</p>
                        </div>
                    </div>

                    <div class="col-xs-12 init-div-horizontal" style="margin-top: 50px">
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

            <div class="row init-div-horizontal hr" style="height: 8px"></div>

            <div class="row init-div-horizontal" style="padding:10px 15px 5px 15px; background: white;">
                <div class="col-xs-12 init-div-horizontal">
                    <div class="col-xs-1 init-div-horizontal">
                        <img src="<c:url value="/assets/img/orderdetails_icon_message@2x.png"/>" height="20px"></i>
                    </div>
                    <div class="col-xs-11 init-div-horizontal">
                        <p class="init-div-vertical" style="font-size: 15px;"><b>买家留言</b></p>
                    </div>
                </div>
                <div class="col-xs-12 init-div-horizontal" style="margin-top: 10px">
                    <div class="col-xs-1 init-div-horizontal">
                    </div>
                    <div class="col-xs-11 init-div-horizontal" style="">
                        ${order.description}
                    </div>
                </div>
            </div>
                    
            <div class="row init-div-horizontal" style="padding:10px 15px 5px 15px; background: white;">
                <div class="col-xs-12 init-div-horizontal">
                    <div class="col-xs-1 init-div-horizontal">
                        <img src="<c:url value="/assets/img/submitorder_icon_time@2x.png"/>" height="20px"></i>
                    </div>
                    <div class="col-xs-5 init-div-horizontal">
                        <p class="init-div-vertical" style="font-size: 15px;"><b>预订送达时间</b></p>
                    </div>
                    <div class="col-xs-5 init-div-horizontal" style="">
                        <p class="text-right" style="font-size: 15px;"><b>${predictTime}</b></p>
                    </div>
                </div>
                
            </div>        

            <div class="row init-div-horizontal hr" style="height: 8px"></div>

            <div class="row init-div-horizontal" style="padding:10px 15px 5px 15px; background: white;">
                <div class="col-xs-12 init-div-horizontal">
                    <div class="col-xs-1 init-div-horizontal">
                        <img src="<c:url value="/assets/img/tab_icon_profile_set@2x.png"/>" height="20px"></i>
                    </div>
                    <div class="col-xs-11 init-div-horizontal">
                        <p class="init-div-vertical" style="font-size: 15px;">
                            <b>${order.userName}</b>
                            <a href="tel://${order.userTelNo}" style="margin-left: 5px;"><b>${order.userTelNo}</b></a>
                        </p>
                    </div>
                </div>
                <div class="col-xs-12 init-div-horizontal" style="margin-top: 10px">
                    <div class="col-xs-1 init-div-horizontal">
                    </div>
                    <div class="col-xs-11 init-div-horizontal" style="">
                        ${shop.stationName}
                    </div>
                </div>
            </div>

            <div class="row init-div-horizontal hr" style="height: 8px"></div>

            <div class="row init-div-horizontal order-info-title-layout">
                <div class="col-xs-12 init-div-horizontal" style="padding: 0 15px;">
                    <div class="col-xs-1 init-div-horizontal">
                        <img src="<c:url value="/assets/img/orderdetails_icon_order@2x.png"/>" height="20px">
                    </div>
                    <div class="col-xs-11 init-div-horizontal">
                        <p style="font-size: 15px;"><b>订单信息</b></p>
                    </div>
                </div>
                <div class="col-xs-12"  style="border-bottom: 1px solid #ddd;"></div>

                <div class="col-xs-12 init-div-horizontal order-info-content-layout">
                    <div class="col-xs-1 init-div-horizontal">
                    </div>
                    <div class="col-xs-11 init-div-horizontal" style="border-bottom: 1px solid #ddd;">
                        <div class="col-xs-6 init-div-horizontal">
                            <p style="font-size: 15px;"><b>订单号</b></p>
                        </div>
                        <div class="col-xs-6 init-div-horizontal">
                            <p class="text-right" style="font-size: 15px;"><b>${order.orderNum}</b></p>
                        </div>
                    </div>
                </div>

                <div class="col-xs-12 init-div-horizontal order-info-content-layout">
                    <div class="col-xs-1 init-div-horizontal">
                    </div>
                    <div class="col-xs-11 init-div-horizontal" style="border-bottom: 1px solid #ddd;">
                        <div class="col-xs-6 init-div-horizontal">
                            <p style="font-size: 15px;"><b>支付方式</b></p>
                        </div>
                        <div class="col-xs-6 init-div-horizontal">
                            <div class="col-xs-4 init-div-horizontal"></div>
                            <div class="col-xs-2 init-div-horizontal text-right">
                                <img src="<c:url value="/assets/img/orderdetails_icon_pay@2x.png"/>" width="70%">
                            </div>
                            <div class="col-xs-6 init-div-horizontal">
                                <p class="text-right" style="font-size: 15px;"><b>微信支付</b></p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xs-12 init-div-horizontal order-info-content-layout">
                    <div class="col-xs-1 init-div-horizontal">
                    </div>
                    <div class="col-xs-11 init-div-horizontal">
                        <div class="col-xs-6 init-div-horizontal">
                            <p style="font-size: 15px;"><b>支付时间</b></p>
                        </div>
                        <div class="col-xs-6 init-div-horizontal">
                            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" var="fmtPayDate" value="${order.payDate}" />
                            <p class="text-right" style="font-size: 15px;"><b>${fmtPayDate}</b></p>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row init-div-horizontal hr" style="height: 8px"></div>
            
            <c:if test="${order.orderStatus != '待配送'}">
                <div class="row init-div-horizontal order-info-title-layout">
                    <div class="col-xs-12 init-div-horizontal" style="padding: 0 15px;">
                        <div class="col-xs-1 init-div-horizontal">
                            <img src="<c:url value="/assets/img/orderdetails_icon_logistics@2x.png"/>" height="20px">
                        </div>
                        <div class="col-xs-11 init-div-horizontal">
                            <p style="font-size: 15px;"><b>配送信息</b></p>
                        </div>
                    </div>
                    <div class="col-xs-12"  style="border-bottom: 1px solid #ddd;"></div>

                    <div class="col-xs-12 init-div-horizontal order-info-content-layout">
                        <div class="col-xs-1 init-div-horizontal">
                        </div>
                        <div class="col-xs-11 init-div-horizontal" style="border-bottom: 1px solid #ddd;">
                            <div class="col-xs-6 init-div-horizontal">
                                <p style="font-size: 15px;"><b>配送时间</b></p>
                            </div>
                            <div class="col-xs-6 init-div-horizontal">
                                <div class="col-xs-6 init-div-horizontal"></div>
                                <div class="col-xs-6 init-div-horizontal">
                                    <fmt:formatDate pattern="HH:mm" var="fmtShippingDate" value="${order.shippingDate}" />
                                    <p class="text-right" style="font-size: 15px;">${fmtShippingDate}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <c:if test="${order.orderStatus == '已完成'}">
                        <div class="col-xs-12 init-div-horizontal order-info-content-layout">
                            <div class="col-xs-1 init-div-horizontal">
                            </div>
                            <div class="col-xs-11 init-div-horizontal">
                                <div class="col-xs-6 init-div-horizontal">
                                    <p style="font-size: 15px;"><b>送达时间</b></p>
                                </div>
                                <div class="col-xs-6 init-div-horizontal">
                                    <fmt:formatDate pattern="HH:mm" var="fmtDeliveryDate" value="${order.deliveryDate}" />
                                    <p class="text-right" style="font-size: 15px;">${fmtDeliveryDate}</p>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
            </c:if>

        </div>
    </div>
</div>
