<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="delivery container-fluid container-lf-space main-body init-div-horizontal shop" style="background: #f5f5f5;padding: 15px; margin-bottom: 0;">
    <div class="row init-div-horizontal">
        <div class="col-xs-12 init-div-horizontal">
            <!--<div class="row init-div-horizontal hr" style="height: 10px"></div>-->
            <div class="row init-div-horizontal" style="padding: 20px 12px 15px 12px;background: white;">
                <div class="col-xs-3 init-div-horizontal" onclick="onGoShop('${shop.id}')">
                    <img src="/${shop.imagePath}" style="width: 100%;" />
                </div>
                <div class="col-xs-9 init-div-horizontal" style="padding-left: 15px;">
                    <div class="col-xs-9 init-div-horizontal" style="margin-top: 5px">
                        <p class="init-div-vertical order-detail-title">${shop.name}</p>
                    </div>
                    <div class="col-xs-3 init-div-horizontal">
                        <c:choose>
                            <c:when test="${order.orderStatus == '已完成'}">
                                <p class="text-right" style="margin-top: 8px;color:#B7A6A6;font-size: 14px;">已完成</p>
                            </c:when>                    
                            <c:otherwise>
                                <p class="text-right" style="margin-top: 8px;color:#5981C7;font-size: 14px;">
                                    ${order.orderStatus}
                                </p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-xs-12 init-div-horizontal" style="margin-top: 10px">
                        <div class="col-xs-6 init-div-horizontal">
                            <p style="font-size: 14px;">起送 ￥${shop.startFee}</p>
                        </div>
                        <div class="col-xs-6 init-div-horizontal">
                            <p style="font-size: 14px;">配送 ￥${shop.shippingFee}</p>
                        </div>
                    </div>
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
                
                    
                    
            <div class="row init-div-horizontal"  style="background: url('<c:url value="/assets/img/back_dot_body.png"/>'); height: 185px;">
                <div style="float: left;background: url('<c:url value="/assets/img/back_dot_left.png"/>');background-repeat: no-repeat;width: 10px;height: 185px"></div>
                <div style="float: right;background: url('<c:url value="/assets/img/back_dot_right.png"/>');background-repeat: no-repeat;width: 10px;height: 185px;"></div>
                <div style="margin: 30px 10px 0 20px;">
                    <div class="col-xs-12 init-div-horizontal">
                        <div class="col-xs-6 init-div-horizontal">
                            <p style="font-size: 15px;">配送费</p>
                        </div>
                        <div class="col-xs-6 init-div-horizontal text-right" style=''>
                            <p style="font-size: 15px;margin-right: 10px">￥${order.shippingFee}</p>
                        </div>
                    </div>
                    
                    <div class="col-xs-12 init-div-horizontal" style="margin-top: 30px">
                        <div class="col-xs-6 init-div-horizontal">
                            <p style="font-size: 15px;color: red">优惠券</p>
                        </div>
                        <div class="col-xs-6 init-div-horizontal text-right" style=''>
                            <p style="font-size: 15px;margin-right: 10px;color: red">-￥${couponAmount}</p>
                        </div>
                    </div>

                    <div class="col-xs-12 init-div-horizontal" style="margin-top: 30px">
                        <p class='text-right' style="font-size: 16px; margin-top: 3px; margin-right: 3px;">
                            小计:
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
                    <div class="col-xs-11 init-div-horizontal" style="border-bottom: 1px solid #ddd;">
                        <textarea class="form-control" style="height: 80px" readonly="readonly" id="txt_opinion">${order.description}</textarea>
                        <span id="textarea_type_cnt" style="position: absolute; right: 10px; bottom: 5px; color: #aaa;">0/50</span>
                    </div>
                </div>
            </div>

            <div class="row init-div-horizontal hr" style="height: 8px"></div>
            
            <div class="row init-div-horizontal" style="padding:10px 15px 5px 15px;background: white;">
                <div class="col-xs-12 init-div-horizontal">
                    <div class="col-xs-1 init-div-horizontal">
                        <img src="<c:url value="/assets/img/orderdetails_icon_logistics@2x.png"/>" height="20px"></i>
                    </div>
                    <div class="col-xs-11 init-div-horizontal">
                        <p style="font-size: 15px;"><b>配送信息</b></p>
                    </div>
                </div>
                <div class="col-xs-12"  style="border-bottom: 1px solid #ddd;"></div>

                <div class="col-xs-12 init-div-horizontal" style="margin-top: 10px">
                    <div class="col-xs-1 init-div-horizontal">
                    </div>
                    <div class="col-xs-11 init-div-horizontal" style="border-bottom: 1px solid #ddd;">
                        <div class="col-xs-6 init-div-horizontal">
                            <p style="font-size: 15px;"><b>预订送达时间</b></p>
                        </div>
                        <div class="col-xs-6 init-div-horizontal">
                            <p class="text-right" style="font-size: 15px;"><b>${predictTime}</b></p>
                        </div>
                    </div>
                </div>

                <div class="col-xs-12 init-div-horizontal" style="margin-top: 10px">
                    <div class="col-xs-1 init-div-horizontal">
                    </div>
                    <div class="col-xs-11 init-div-horizontal" style="border-bottom: 1px solid #ddd;">
                        <div class="col-xs-6 init-div-horizontal">
                            <p style="font-size: 15px;"><b>收货地址</b></p>
                        </div>
                        <div class="col-xs-6 init-div-horizontal">
                            <p class="text-right" style="font-size: 15px;"><b>${shop.stationName}</b></p>
                        </div>
                    </div>
                </div>

                <div class="col-xs-12 init-div-horizontal" style="margin-top: 10px">
                    <div class="col-xs-1 init-div-horizontal">
                    </div>
                    <div class="col-xs-11 init-div-horizontal" style="border-bottom: 1px solid #ddd;">
                        <div class="col-xs-6 init-div-horizontal">
                            <p style="font-size: 15px;"><b>配送方式</b></p>
                        </div>
                        <div class="col-xs-6 init-div-horizontal">
                            <p class="text-right" style="font-size: 15px;"><b>高点专送</b></p>
                        </div>
                    </div>
                </div>

                <div class="col-xs-12 init-div-horizontal" style="margin-top: 10px">
                    <div class="col-xs-1 init-div-horizontal">
                    </div>
                    <div class="col-xs-11 init-div-horizontal" style="">
                        <div class="col-xs-4 init-div-horizontal">
                            <p style="font-size: 15px;"><b>配送骑手</b></p>
                        </div>
                        <div class="col-xs-8 init-div-horizontal">
                            <div class="col-xs-7 init-div-horizontal text-right">
                                <div class="col-xs-2 init-div-horizontal text-right">
                                    <img src="<c:url value="/assets/img/porfile_icon_contact_tel@2x.png"/>" width="11px;" style="margin-top: 3px; margin-right: 2px;">
                                </div>
                                <div class="col-xs-10 init-div-horizontal ">
                                    <p class="text-right" style="font-size: 14px;margin-top: 1px"><a href="tel://${deliver.telNo}"><b>${deliver.telNo}</b></a></p>
                                </div>
                            </div>
                            <div class="col-xs-5 init-div-horizontal">
                                <p class="text-right" style="font-size: 14px;"><b>${deliver.name}</b></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="row init-div-horizontal hr" style="height: 8px"></div>
            
            <div class="row init-div-horizontal" style="padding:10px 15px 5px 15px;background: white;">
                <div class="col-xs-12 init-div-horizontal">
                    <div class="col-xs-1 init-div-horizontal">
                        <img src="<c:url value="/assets/img/orderdetails_icon_order@2x.png"/>" height="20px">
                    </div>
                    <div class="col-xs-11 init-div-horizontal">
                        <p style="font-size: 15px;"><b>订单信息</b></p>
                    </div>
                </div>
                <div class="col-xs-12"  style="border-bottom: 1px solid #ddd;"></div>

                <div class="col-xs-12 init-div-horizontal" style="margin-top: 10px">
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

                <div class="col-xs-12 init-div-horizontal" style="margin-top: 10px">
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
                                <p class="text-right" style="font-size: 14px;"><b>微信支付</b></p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xs-12 init-div-horizontal" style="margin-top: 10px">
                    <div class="col-xs-1 init-div-horizontal">
                    </div>
                    <div class="col-xs-11 init-div-horizontal">
                        <div class="col-xs-4 init-div-horizontal">
                            <p style="font-size: 15px;"><b>支付时间</b></p>
                        </div>
                        <div class="col-xs-8 init-div-horizontal">
                            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" var="fmtPayDate" value="${order.payDate}" />
                            <p class="text-right" style="font-size: 15px;"><b>${fmtPayDate}</b></p>
                        </div>
                    </div>
                </div>
            </div>
                        
        </div>
    </div>
</div>
