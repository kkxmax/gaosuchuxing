<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="delivery container-fluid container-lf-space main-body init-div-horizontal">
    <div class="row init-div" style="padding-top: 50px">
        <div style="text-align: center">
            <img src="<c:url value="/assets/img/pay_icon_sucess@2x.png"/>" style="width: 120px;height: 110px;">
        </div>
    </div>
    <div style="text-align: center; padding-top: 20px; /*background: url('<c:url value="/assets/img/adv_button.png"/>') no-repeat; background-size: contain; background-position: right; margin-right: 10px;*/">
        <p style="font-size: 17px; font-weight: bold">支付成功</p>
        <p style="font-size: 14px">在<a href="<c:url value="/user/orders"/>">订单</a>中心查看订单进度</p>
        
        <a href="javascript:;" onclick="onShare();">
            <img src="<c:url value="/assets/img/adv_button.png"/>" style="position: fixed; right: 25px; top: 175px; width: 60px; height: 60px;" >
        </a>
    </div>
    <!--<div style="width: 20%; height: 85px; float: right; ">-->
        <!--<img src="../../assets/img/QQ图片20170927183403.png" style="width: 100%; height: 100%">-->
    <!--</div>-->

    <div class="row init-div" style="">
        <div style="width: 80%; margin: auto">
            <button type="button" class="btn btn-primary" style="width: 100%; border-radius: 17px !important; background-color: #3e6dbb" onclick="onNavigation()">导航到服务区</button>
        </div>
    </div>
</div>
        
<!-- Modal -->
<div class="modal fade" id="navi-dlg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog my_modal">
        <div class="modal-content" style="border-radius: 5px !important; width: 300px">
            <div class="modal-body" style="padding: 0px !important;">
                <!--<div class="row init-div" style="width: 100px; margin: auto; padding-bottom: 40px">-->
                <!--<img src="../../assets/img/home_rescue_mask_disabled@2x.png" style="width: 100px; ">-->
                <!--</div>-->
                <!--<div class="row init-div">-->
                <!--<p style="color: #356eb5; font-size: 16px; text-align: center">暂未开放，敬请期待</p>-->
                <!--</div>-->
                <div class="modal-header" style="color: #ff3f69; background: url('<c:url value="/assets/img/pay_icon_love@2x.png"/>') no-repeat; background-position: 10px 16px; padding-left: 30px; background-size: 17px 15px">
                    <span style="font-weight: bold">温馨提示：</span><span>请选择您手机上已有的地图导航</span>
                </div>
                <div class="modal-body init-div">
                    <div class="row init-div">
                        <div class="col-xs-6 init-div">
                            <div style="text-align: center; width: 80px; margin: auto; margin-top: 75px; margin-bottom: 15px; ">
                                <a class="no_underline" href="javascript:;" style="color: black; background: url('<c:url value="/assets/img/pay_icon_mapgaode@2x.png"/>') no-repeat; background-size: 50px 50px; padding-top: 60px; ">高德地图</a>
                            </div>
                        </div>
                        <div class="col-xs-6 init-div">
                            <div style="text-align: center; width: 80px; margin: auto; margin-top: 75px; margin-bottom: 15px; ">
                                <a class="no_underline" href="javascript:;" style="color: black; background: url('<c:url value="/assets/img/pay_icon_mapbaidu@2x.png"/>') no-repeat; background-size: 50px 50px; padding-top: 60px; ">高德地图</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer" style="text-align: center; padding: 2px">
                    <button type="button" class="btn btn-default" data-dismiss="modal" style="width: 100%; border: none">取消</button>
                </div>
            </div>
        </div>
    </div>
</div>        