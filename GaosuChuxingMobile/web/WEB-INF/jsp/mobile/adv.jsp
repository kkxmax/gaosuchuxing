<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en" class="">
    <!-- BEGIN HEAD -->
    <head>
        <meta charset="utf-8"/>
        <title>红包新玩法</title>
        <meta name=renderer content=webkit>		
        <meta http-equiv="X-UA-Compatible" content="chrome=1">
        <meta content="width=device-width, initial-scale=1, user-scalable=no" name="viewport"/>

        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="<c:url value="/assets/plugins/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/plugins/simple-line-icons/simple-line-icons.min.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
        <!-- END GLOBAL MANDATORY STYLES -->

        <!-- BEGIN THEME STYLES -->
        <link href="<c:url value="/assets/css/components.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/layout.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/themes/default.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/custom.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/timeline.css"/>" rel="stylesheet" type="text/css"/>
        <!-- END THEME STYLES -->

    </head>
    <!-- END HEAD -->
    
    <!-- BEGIN BODY -->
    <body>
        <!-- BEGIN HEADER -->
        <%--<tiles:insertAttribute name="header"/>--%>
        <!-- END HEADER -->

        <!-- BEGIN CONTENT -->
        <input type="hidden" id="shareNum" value="${shareNum}" />
        <input type="hidden" id="noticeId" value="${noticeId}" />
        <input type="hidden" id="take-adv-url" value="<c:url value="/user/takeAdv"/>" />
        <input type="hidden" id="signup-url" value="<c:url value="/user/signup"/>" />
        <input type="hidden" id="validUser-url" value="<c:url value="/user/validUser"/>" />
        <input type="hidden" id="verify-url" value="<c:url value="/user/verify"/>" />

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
                            <center><img class="story-img-button" src="<c:url value="/assets/img/story_button.png"/>" onclick="onTake();"></center>
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
                            <center><img src="<c:url value="/assets/img/picture_code.jpg"/>" style="width: 40%"></center>
                        </div>
                        <div class="col-xs-12 init-div-horizontal" style="padding-bottom: 20px;">
                            <center><p style="font-size:15;color: #000"></p>长摁识别二维码关注</center>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <!-- modal -->
        <div class="modal fade" id="signup-dlg" tabindex="-1" aria-hidden="false" style="display: none;margin-top: 10%;margin-left: 8%;margin-right: 8%">
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
                                        <input type="text" class="form-control" style="font-size: 14px; padding-top: 10px;" placeholder="请输入手机号" id="telno" maxlength="11">
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
                                        <input type="text" class="form-control" placeholder="请输入验证码" style="width: 50%; font-size: 14px; padding-top: 10px;" id="code">
                                        <span class="input-group-addon" style="padding-left: 0px;padding-right: 5px;min-width: 3px;float: left">
                                            <img style="width: 2px;background-color: #111;height: 23px;">
                                        </span>
                                        <span class="form-control" style="width: 40%; text-align: right; font-size: 12px; padding-top: 10px;" onclick="onVerify();">发送验证码</span>
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
                                    <p style="font-size: 15px">美味高速</p>
                                </div>
                            </div>
                        </div>
                        <div class="row init-div-horizontal" style="margin-top: 8%">
                            <div class="col-xs-12 init-div-horizontal">
                                <div class="row init-div-horizontal"  style="background: url('<c:url value="/assets/img/radius_back_body.png"/>'); height: 130px;">
                                    <div style="background: url('<c:url value="/assets/img/blue_radius_back.png"/>');float:left;width: 55px;height: 130px;background-repeat: no-repeat" id="blue-coupon-bg"></div>
                                    <div style="background: url('<c:url value="/assets/img/red_radius_back.png"/>');float:left;width: 55px;height: 130px;background-repeat: no-repeat" id="red-coupon-bg" class="hide"></div>
                                    <div style="background: url('<c:url value="/assets/img/radius_right.png"/>');float:right;width: 10px;height: 130px;background-repeat: no-repeat"></div>
                                    <div style="margin: 0px 15px 0 60px;">
                                        <div class="col-xs-12 init-div-horizontal" style="margin-top: 10px">
                                            <p style="font-size:14px;color: #3E6DBB;font-weight: bold;" id="coupon-kind"></p>
                                                <!--<p style="font-size:18px;color: #FF3E59;font-weight: bold;" id="blue-coupon-name" class="hide"></p>-->
                                        </div>
                                        <div class="col-xs-12 init-div-horizontal" style="margin-top: 5px">
                                            <p style="color: #3E6DBB;font-weight: bold;" id="blue-coupon-amount"><font>￥</font>&nbsp;<font style="font-size: 20px" id="blue-amount"></font></p>
                                            <p style="color: #FF3E59;font-weight: bold;" id="red-coupon-amount" class="hide"><font>￥</font>&nbsp;<font style="font-size: 20px" id="red-amount"></font></p>
                                        </div>
                                        <div class="col-xs-12 init-div-horizontal" style="margin-top: 5px">
                                            <p style="font-size:10px;color: #3E6DBB;font-weight: bold;" id="coupon-detail">全品类优惠券，所有高点服务区可用</p>
                                            <!--<p style="font-size:15px;color: #FF3E59;font-weight: bold;" id="blue-coupon-detail" class="hide">全品类优惠券，所有高点服务区可用</p>-->
                                        </div>
                                    </div>
                                </div>
                                <div class="row init-div-horizontal" style="margin-top: 6%">
                                    <div class="col-xs-12 init-div-horizontal">
                                        <center><p style="font-size: 14px">红包已放入您的账户 : <span id="userId"></span></p></center>
                                    </div>
                                </div>
                                <div class="row init-div-horizontal" style="margin-top: 4%">
                                    <div class="col-xs-12 init-div-horizontal">
                                        <center><img src="<c:url value="/assets/img/picture_code.jpg"/>" style="width: 40%"></center>
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
        <!-- END CONTENT -->
        
        <input type="hidden" id="share-coupon-url" value="<c:url value="/advertise"/>" />        
        <input type="hidden" id="wxauth-url" value="<c:url value="/user/wxAuthorize"/>" />
        
        <!-- Modal -->
        <div class="modal fade bs-modal-sm" id="confirm-yes-no-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm" style="margin-top: 45%;">
                <div class="modal-content">
                    <div class="modal-header" style="border-bottom: 0;">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h3 class="modal-title" style="color: #3598dc;"><i class="fa fa-question-circle" style="font-size: 26px;"></i></h3>
                    </div>
                    <div class="modal-body">
                        <h4 class="modal-title" id="confirm-yes-no-msg" style="font-family: Microsoft YaHei;"></h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn blue" id="button-yes"> 确认 </button>
                        <button type="button" class="btn default" data-dismiss="modal"> 取消 </button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
        
        <div class="modal fade bs-modal-sm" id="confirm-message-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm" style="margin-top: 45%;">
                <div class="modal-content">
                    <div class="modal-header" style="border-bottom: 0;">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h3 class="modal-title" style="color: #dfba49;"><i class="fa fa-warning" style="font-size: 26px;"></i></h3>
                    </div>
                    <div class="modal-body">
                        <h4 class="modal-title" id="confirm-message-msg" style="font-family: Microsoft YaHei;"></h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn blue" data-dismiss="modal"> 是 </button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->
        
        <div class="modal fade bs-modal-sm" id="information-message-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm" style="margin-top: 45%;">
                <div class="modal-content">
                    <div class="modal-header" style="border-bottom: 0;">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h3 class="modal-title" style="color: #3598dc;"><i class="fa fa-info-circle" style="font-size: 26px;"></i></h3>
                    </div>
                    <div class="modal-body">
                        <h4 class="modal-title" id="information-message-msg" style="font-family: Microsoft YaHei;"></h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn blue" data-dismiss="modal"> 是 </button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->

        <script src="<c:url value="/assets/js/jquery.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/js/bootstrap.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/js/custom/js_for_adv.js"/>" type="text/javascript"></script>
        
    </body> 
    <!-- END BODY -->
</html>


               