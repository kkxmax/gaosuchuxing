<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<input type="hidden" id="token" value="${token}" />
       
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
                            <p class="" style="font-size: 15px;" id="userInfo">${user.name} ${user.userId}</p>
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
                            <p class="" style="font-size: 15px;">${deliveryTime} (到达)</p>
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
                                  placeholder="" maxlength="50" data-autosize-on="true" style="overflow-y: hidden; resize: horizontal; height: 94px;"></textarea>
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
                                <p class="init-div-vertical text-right" style="margin-right: 20px;font-size: 15px;margin-top: 5px">￥${goods.price * goods.qty}</p>
                            </div>
                        </div>
                    </div>
                </div>    
                
                <c:set var="index" value="${index+1}"/>
            </c:forEach>

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
                        <div class="col-xs-6 init-div-horizontal text-right" style=''>
                            <c:choose>
                                <c:when test="${useCoupon == true}">
                                    <p style="font-size: 15px;margin-right: 10px;color: red">-￥${couponAmount}</p>
                                </c:when>    
                                <c:otherwise>
                                    <p style="font-size: 15px;margin-right: 10px;color: red" onclick="onGoUserCoupon('${token}', '${order.id}');">使用</p>
                                </c:otherwise>    
                            </c:choose>
                            
                        </div>
                    </div>
                    <div class="col-xs-12 init-div-horizontal" style="margin-top: 20px">
                        <p class='text-right' style="font-size: 16px; margin-top: 3px; margin-right: 5px;">
                            实付:
                            <span style="font-size: 19px; color:red;font-weight: bold">￥${order.orderAmount + order.shippingFee - couponAmount}</span>
                        </p>
                    </div>
                </div>
            </div>

            <div class="row init-div-horizontal hr" style="height: 8px"></div>

        </div>
    </div>
</div>
<!-- END CONTENT -->

<div class="bt_cart">
    <div class="col-xs-8 bt_cart_inner">
        <div class="ic_cart">
            <span class="add-badge badge">${order.orderQty}</span>
        </div>
        <div class="price">
            <p style="font-size: 15px; margin-bottom: 0px">共￥${order.orderAmount}</p>
            <p style="font-size: 11px">另需配送费￥${order.shippingFee}</p>
        </div>
    </div>
    <div class="col-xs-4 init-div oper" style="font-size: 15px" onclick="onSubmit('${order.id}');">
        结算
    </div>
</div>  
        
<div class="modal fade" id="signup-dlg" tabindex="-1" aria-hidden="false" style="display: none;margin-top: 10%;margin-left: 8%;margin-right: 8%">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <div class="row init-div-horizontal">
                    <div class="col-xs-12 init-div-horizontal">
                        <div class="form-group form-md-line-input" style="margin-bottom: 0px">
                            <div class="input-group" style="border: 1px solid #111;border-radius: 5% !important;">
                                <span class="input-group-addon">
                                    <img src="<c:url value="/assets/img/regsiter_icon_tel@2x.png"/>" style="width:130%">
                                </span>
                                <span class="input-group-addon" style="    padding-left: 0px;padding-right: 10px;min-width: 3px;">
                                    <img style="width: 2px;background-color: #111;height: 23px;">
                                </span>
                                <input type="text" class="form-control" style="font-size: 12px; padding-top: 10px;" placeholder="请输入手机号" id="telno" >
                            </div>
                        </div>
                        <div class="form-group form-md-line-input">
                            <div class="input-group" style="border: 1px solid #111;border-radius: 5% !important;">
                                <span class="input-group-addon">
                                    <img src="<c:url value="/assets/img/regsiter_icon_num@2x.png"/>" style="width:130%">
                                </span>
                                <span class="input-group-addon" style=" padding-left: 0px;padding-right: 10px;min-width: 3px;">
                                    <img style="width: 2px;background-color: #111;height: 23px;">
                                </span>
                                <input type="text" class="form-control" placeholder="请输入验证吗" style="width: 50%; font-size: 14px; padding-top: 10px; " id="code">
                                <span class="input-group-addon" style="padding-left: 0px;padding-right: 5px;min-width: 3px;float: left">
                                    <img style="width: 2px;background-color: #111;height: 23px;">
                                </span>
                                <span class="form-control" style="width: 38%; font-size: 12px; padding-top: 10px;" onclick="onVerify();" >发送验证吗</span>
                            </div>
                        </div>
                        <div class="row init-div-horizontal">
                            <div class="col-xs-12 init-div" onclick="onSignup();">
                                <img src="<c:url value="/assets/img/blue_button.png"/>" style="width: 100%" >
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
</div>        