<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- BEGIN CONTENT -->
<div class="delivery container-fluid container-lf-space main-body init-div-horizontal shop" style="background: #f5f5f5;padding: 0px 15px; margin-bottom: 60px;">
    <div class="row init-div-horizontal">
        <div class="col-xs-12 init-div-horizontal">
            <div class="row init-div-horizontal hr" style="height: 10px"></div>

            <div class="row init-div-horizontal order-info-title-layout">

                <div class="col-xs-12 init-div-horizontal order-info-content-layout" style="padding-left: 10px">
                    <div class="col-xs-1 init-div-horizontal">
                        <img src="<c:url value="/assets/img/submitorder_icon_location@2x.png"/>" height="20px">
                    </div>
                    <div class="col-xs-11 init-div-horizontal" style="border-bottom: 1px solid #ddd;">
                        <div class="col-xs-4 init-div-horizontal">
                            <p style="font-size: 15px;"><b>收货地址</b></p>
                        </div>
                        <div class="col-xs-8 init-div-horizontal">
                            <p class="" style="font-size: 15px;">${shop.stationName}</p>
                        </div>
                    </div>
                </div>

                <div class="col-xs-12 init-div-horizontal order-info-content-layout" style="padding-left: 10px">
                    <div class="col-xs-1 init-div-horizontal">
                        <img src="<c:url value="/assets/img/submitorder_icon_person@2x.png"/>" height="20px">
                    </div>
                    <div class="col-xs-11 init-div-horizontal" style="border-bottom: 1px solid #ddd;">
                        <div class="col-xs-4 init-div-horizontal">
                            <p style="font-size: 15px;"><b>联系方式</b></p>
                        </div>
                        <div class="col-xs-8 init-div-horizontal">
                        </div>
                    </div>
                </div>

                <div class="col-xs-12 init-div-horizontal order-info-content-layout" style="padding-left: 10px">
                    <div class="col-xs-1 init-div-horizontal">
                        <img src="<c:url value="/assets/img/submitorder_icon_time@2x.png"/>" height="20px">
                    </div>
                    <div class="col-xs-11 init-div-horizontal">
                        <div class="col-xs-4 init-div-horizontal">
                            <p style="font-size: 15px;"><b>送达时间</b></p>
                        </div>
                        <div class="col-xs-8 init-div-horizontal">
                            <p class="" style="font-size: 15px;">中午12：00</p>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row init-div-horizontal hr" style="height: 5px"></div>

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
                        <textarea id="txt_opinion" class="form-control autosizeme" rows="4"
                                  onkeyup="javascript:doingInputText(this.value);"
                                  placeholder="" maxlength="100" data-autosize-on="true" style="overflow-y: hidden; resize: horizontal; height: 94px;"></textarea>
                        <span id="textarea_type_cnt" style="position: absolute; right: 25px; bottom: 20px; color: #aaa;">0/50</span>
                    </div>
                </div>
            </div>

            <div class="row init-div-horizontal hr" style="height:5px"></div>

            <div class="row init-div-horizontal" style="padding: 10px 12px;background: white;">
                <div class="col-xs-1 init-div-horizontal">
                    <img src="<c:url value="/assets/img/ic_product_02.png"/>" style="width: 100%;border-radius: 50% !important" />
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
                                <p class="init-div-vertical text-right" style="margin-right: 10px;font-size: 15px;margin-top: 5px">￥${goods.price}</p>
                            </div>
                        </div>
                    </div>
                </div>    
                
                <c:set var="index" value="${index+1}"/>
            </c:forEach>

            <div class="row init-div-horizontal"  style="background: url('<c:url value="/assets/img/back_dot_body2.png"/>'); height: 267px;">
                <div style="float: left;background: url('<c:url value="/assets/img/back_dot_left2.png"/>');background-repeat: no-repeat;width: 14px;height: 267px"></div>
                <div style="float: right;background: url('<c:url value="/assets/img/back_dot_right2.png"/>');background-repeat: no-repeat;width: 10px;height: 267px;"></div>
                <div style="margin: 45px 10px 0 20px;">
                    <div class="col-xs-12 init-div-horizontal">
                        <div class="col-xs-6 init-div-horizontal">
                            <p style="font-size: 15px;">配送费</p>
                        </div>
                        <div class="col-xs-6 init-div-horizontal text-right" style=''>
                            <p style="font-size: 15px;margin-right: 10px">￥${order.shippingFee}</p>
                        </div>
                    </div>
                    <div class="col-xs-12 init-div-horizontal" style="margin-top: 65px">
                        <div class="col-xs-6 init-div-horizontal">
                            <p style="font-size: 15px;color: red">优惠券</p>
                        </div>
                        <div class="col-xs-6 init-div-horizontal text-right" style=''>
                            <p style="font-size: 15px;margin-right: 10px;color: red">-￥${couponAmount}</p>
                        </div>
                    </div>
                    <div class="col-xs-12 init-div-horizontal" style="margin-top: 45px">
                        <div class="col-xs-6 init-div-horizontal">
                        </div>
                        <div class="col-xs-6 init-div-horizontal">
                            <div class="col-xs-8 init-div-horizontal">
                                <p class='text-right' style="font-size: 16px;margin-top: 3px">实付:</p>
                            </div>
                            <div class="col-xs-4 init-div-horizontal text-right" style=''>
                                <p style="font-size: 19px;margin-right: 10px;color:red;font-weight: bold">￥${order.orderAmount - couponAmount}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row init-div-horizontal hr" style="height: 8px"></div>

            <div class="row init-div-horizontal hr" style="height: 8px"></div>

            <div class="row init-div-horizontal hr" style="height: 8px"></div>

        </div>
    </div>
</div>
<!-- END CONTENT -->

<div class="bt_cart">
    <div class="col-xs-9 bt_cart_inner">
        <div class="ic_cart">
            <span class="add-badge badge">${order.qty}</span>
        </div>
        <div class="price">
            <p style="font-size: 15px; margin-bottom: 0px">共￥${order.orderAmount}</p>
            <p style="font-size: 11px">另需配送费￥${order.shippingFee}</p>
        </div>
    </div>
    <div class="col-xs-3 init-div oper" data-toggle="modal" data-target="#myModal" style="font-size: 15px" onclick="location.href='4-7.html'">
        结算
    </div>
</div>