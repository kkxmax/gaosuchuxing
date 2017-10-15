<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<input type="hidden" id="shareNum" value="${shareNum}" />

<div class="delivery container-fluid container-lf-space main-body init-div-horizontal shop" style="background-color: transparent; margin-bottom: 0;">
    <div class="row init-div-horizontal">
        <div class="col-xs-12 init-div-horizontal">

            <div class="row init-div-horizontal" style="background: #E66371;min-height: 0px">
                <div class="col-xs-12 init-div-horizontal">
                    <img class="story-img-back" src="<c:url value="/assets/img/story_img.png"/>">
                </div>
            </div>

            <div class="row init-div-horizontal" style="background: #E66371;min-height: 0px">
                <div class="col-xs-12 init-div-horizontal">
                    <center><img class="story-img-button" src="<c:url value="/assets/img/story_button.png"/>" onclick="onTakeAdv();"></center>
                </div>
            </div>

            <div class="row init-div-horizontal" style="background: #E66371;min-height: 0px">
                <div class="col-xs-12 init-div-horizontal" style="margin-top:5%">
                    <div class="col-xs-4 init-div-horizontal story-img-border-left-bottom"></div>
                    <div class="col-xs-4 init-div-horizontal">
                        <center><p style="font-size: 16px;font-weight: bold;color:#000;">活动规则</p></center>
                    </div>
                    <div class="col-xs-4 init-div-horizontal story-img-border-right-bottom"></div>
                </div>
            </div>

            <div class="row init-div-horizontal" style="background: #E66371;min-height: 0px">
                <div class="col-xs-12 init-div-horizontal">

                    <div class="row init-div-horizontal">
                        <div class="col-xs-12 init-div-horizontal">
                            <p class="story-descript-font">1.优惠券新老用户同享</p>
                        </div>
                    </div>

                    <div class="row init-div-horizontal">
                        <div class="col-xs-12 init-div-horizontal">
                            <p class="story-descript-font">2.每单只能只用一张优惠券，不可叠加使用</p>
                        </div>
                    </div>

                    <div class="row init-div-horizontal">
                        <div class="col-xs-12 init-div-horizontal">
                            <p class="story-descript-font">3.优惠券仅限在订单支付时使用，不可提现等其他用途</p>
                        </div>
                    </div>

                    <div class="row init-div-horizontal">
                        <div class="col-xs-12 init-div-horizontal">
                            <p class="story-descript-font">4.请在优惠券有效日期内使用，过期将会自动清空</p>
                        </div>
                    </div>

                    <div class="row init-div-horizontal">
                        <div class="col-xs-12 init-div-horizontal">
                            <p class="story-descript-font">5.领取优惠券皆在公众号-高速出行-我的优惠里查看红包详情</p>
                        </div>
                    </div>

                </div>
            </div>

            <div class="row init-div-horizontal" style="background: #E66371;min-height: 0px">
                <div class="col-xs-12 init-div-horizontal" style="margin-top:10%">
                    <center><img src="<c:url value="/assets/img/picture_code.png"/>" style="width: 40%"></center>
                </div>
                <div class="col-xs-12 init-div-horizontal" style="padding-bottom: 20px;">
                    <center><p style="font-size:15;color: #000"></p>长摁识别二维码关注</center>
                </div>
            </div>

        </div>
    </div>
</div>

<!-- modal -->
<div class="modal fade" id="login-modal" tabindex="-1" aria-hidden="false" style="display: none;margin-top: 10%;margin-left: 8%;margin-right: 8%">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <div class="row init-div-horizontal" style="border-bottom: 1px solid #DDD">
                    <div class="col-xs-12 init-div-horizontal">
                        <div class="col-xs-1 init-div-horizontal">
                            <img src="<c:url value="/assets/img/pay_icon_love@2x.png"/>" style="width: 80%">
                        </div>
                        <div class="col-xs-11 init-div-horizontal">
                            <p style="font-size: 15px">温馨提示：输入手机好抢属于你的红包吧！</p>
                        </div>
                    </div>
                </div>
                <div class="row init-div-horizontal" style="margin-top: 8%">
                    <div class="col-xs-12 init-div-horizontal">
                        <div class="form-group form-md-line-input" style="margin-bottom: 0px">
                            <div class="input-group" style="border: 1px solid #111;border-radius: 5% !important;">
                                <span class="input-group-addon">
                                    <img src="<c:url value="/assets/img/regsiter_icon_tel@2x.png"/>" style="width:130%">
                                </span>
                                <span class="input-group-addon" style="    padding-left: 0px;padding-right: 10px;min-width: 3px;">
                                    <img style="width: 2px;background-color: #111;height: 23px;">
                                </span>
                                <input type="text" class="form-control" style="font-size: 14px; padding-top: 10px;" placeholder="请输入手机号" id="telno">
                            </div>
                        </div>
                        <div class="form-group form-md-line-input">
                            <div class="input-group" style="border: 1px solid #111;border-radius: 5% !important;">
                                <span class="input-group-addon">
                                    <img src="<c:url value="/assets/img/regsiter_icon_num@2x.png"/>" style="width:130%">
                                </span>
                                <span class="input-group-addon" style="padding-left: 0px;padding-right: 10px;min-width: 3px;">
                                    <img style="width: 2px;background-color: #111;height: 23px;">
                                </span>
                                <input type="text" class="form-control" placeholder="请输入验证吗" style="width: 50%; font-size: 14px; padding-top: 10px;" id="code">
                                <span class="input-group-addon" style="padding-left: 0px;padding-right: 5px;min-width: 3px;float: left">
                                    <img style="width: 2px;background-color: #111;height: 23px;">
                                </span>
                                <span class="form-control" style="width: 40%; text-align: right; font-size: 12px; padding-top: 10px;" onclick="onVerify();">发送验证吗</span>
                            </div>
                        </div>
                        <div class="row init-div-horizontal">
                            <div class="col-xs-12 init-div" onclick="onAdv();">
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
                            
<div class="modal fade" id="coupon-modal" tabindex="-1" aria-hidden="false" style="display: none;margin-top: 10%;margin-left: 8%;margin-right: 8%">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <div class="row init-div-horizontal" style="border-bottom: 1px solid #DDD">
                    <div class="col-xs-12 init-div-horizontal">
                        <div class="col-xs-1 init-div-horizontal">
                            <img src="<c:url value="/assets/img/pay_icon_love@2x.png"/>" style="width: 80%">
                        </div>
                        <div class="col-xs-11 init-div-horizontal">
                            <p style="font-size: 15px">关注****公众号查看更多优惠并使用</p>
                        </div>
                    </div>
                </div>
                <div class="row init-div-horizontal" style="margin-top: 8%">
                    <div class="col-xs-12 init-div-horizontal">
                        <div class="row init-div-horizontal"  style="background: url('<c:url value="/uploads/img/radius_back_body.png"/>'); height: 130px;">
                            <div style="background: url('<c:url value="/assets/img/blue_radius_back.png"/>');float:left;width: 55px;height: 130px;background-repeat: no-repeat" id="red-coupon-bg"></div>
                            <div style="background: url('<c:url value="/assets/img/red_radius_back.png"/>');float:left;width: 55px;height: 130px;background-repeat: no-repeat" id="blue-coupon-bg" class="hide"></div>
                            <div style="background: url('<c:url value="/assets/img/radius_right.png"/>');float:right;width: 10px;height: 130px;background-repeat: no-repeat"></div>
                            <div style="margin: 0px 15px 0 60px;">
                                <div class="col-xs-12 init-div-horizontal" style="margin-top: 10px">
                                    <p style="font-size:18px;color: #3E6DBB;font-weight: bold;" id="coupon-kind"></p>
<!--                                        <p style="font-size:18px;color: #FF3E59;font-weight: bold;" id="blue-coupon-name" class="hide"></p>-->
                                </div>
                                <div class="col-xs-12 init-div-horizontal" style="margin-top: 5px">
                                    <p style="color: #3E6DBB;font-weight: bold;" id="red-coupon-amount"><font>￥</font>&nbsp;<font style="font-size: 23px" id="red-amount"></font></p>
                                    <p style="color: #FF3E59;font-weight: bold;" id="blue-coupon-amount" class="hide"><font>￥</font>&nbsp;<font style="font-size: 23px" id="blue-amount"></font></p>
                                </div>
                                <div class="col-xs-12 init-div-horizontal" style="">
                                    <p style="font-size:15px;color: #3E6DBB;font-weight: bold;" id="coupon-detail">全品类优惠券，所有高点服务区可用</p>
                                    <!--<p style="font-size:15px;color: #FF3E59;font-weight: bold;" id="blue-coupon-detail" class="hide">全品类优惠券，所有高点服务区可用</p>-->
                                </div>
                            </div>
                        </div>
                        <div class="row init-div-horizontal" style="margin-top: 6%">
                            <div class="col-xs-12 init-div-horizontal">
                                <center><p style="font-size: 15px">红包已放入您的账户 : <span id="telno"></span></p></center>
                            </div>
                        </div>
                        <div class="row init-div-horizontal" style="margin-top: 4%">
                            <div class="col-xs-12 init-div-horizontal">
                                <center><img src="<c:url value="/assets/img/picture_code.png"/>" style="width: 40%"></center>
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