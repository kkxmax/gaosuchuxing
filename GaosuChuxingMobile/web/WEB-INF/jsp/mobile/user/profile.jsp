<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="delivery container-fluid container-lf-space main-body init-div-horizontal">
    <div class="row init-div-horizontal">
        <div class="col-xs-12 init-div-horizontal" style="height: 250px">
            <div class="row init-div-horizontal">
                <img src="<c:url value="/assets/img/porfile_bg@2x.png"/>" width="100%">
            </div>
            <div class="row init-div-horizontal">
                <center><img class="radius-image-view" src="${LOGIN_USER.avatarPath}" width="25%"></center>
            </div>
            <div class="row init-div-horizontal">
                <center><p class="phone-title">${LOGIN_USER.name}</p></center>
            </div>
            <div class="row init-div-horizontal">
                <center><p class="phone-number">${LOGIN_USER.userId}</p></center>
            </div>
        </div>
    </div>
    <div class="row init-div-horizontal hr" style="height: 3px;"></div>

    <div class="row init-div-horizontal margin-top-20 margin-bottom-20 profile-content" onclick="location.href='userCoupon'">
        <div class="col-xs-11 init-div-horizontal">
            <img src="<c:url value="/assets/img/porfile_icon_sale@2x.png"/>" style="width: 14px; vertical-align: sub; margin: 0 10px 0 20px;" />
            我的优惠
        </div>
        <div class="col-xs-1 init-div-horizontal">
            <img src="<c:url value="/assets/img/porfile_btn_right@2x.png"/>" style="width: 8px; vertical-align: sub;" />
        </div>
    </div>
    <div class="row init-div-horizontal hr"></div>

    <div class="row init-div-horizontal margin-top-20 margin-bottom-20 profile-content" onclick="location.href='feedback';">
        <div class="col-xs-11 init-div-horizontal">
            <img src="<c:url value="/assets/img/porfile_icon_opinion@2x.png"/>" style="width: 14px; vertical-align: sub; margin: 0 10px 0 20px;" />
            意见反馈
        </div>
        <div class="col-xs-1 init-div-horizontal">
            <img src="<c:url value="/assets/img/porfile_btn_right@2x.png"/>" style="width: 8px; vertical-align: sub;" />
        </div>
    </div>
    <div class="row init-div-horizontal hr"></div>

    <div class="row init-div-horizontal margin-top-20 margin-bottom-20 profile-content" onclick="location.href='about';">
        <div class="col-xs-11 init-div-horizontal">
            <img src="<c:url value="/assets/img/porfile_icon_us@2x.png"/>" style="width: 14px; vertical-align: sub; margin: 0 10px 0 20px;" />
            关于我们
        </div>
        <div class="col-xs-1 init-div-horizontal">
            <img src="<c:url value="/assets/img/porfile_btn_right@2x.png"/>" style="width: 8px; vertical-align: sub;" />
        </div>
    </div>
    <div class="row init-div-horizontal hr"></div>

    <div class="row init-div-horizontal margin-top-20 margin-bottom-20 profile-content" onclick="javascript:$('#contact-modal').modal();">
        <div class="col-xs-11 init-div-horizontal">
            <img src="<c:url value="/assets/img/porfile_icon_contact@2x.png"/>" style="width: 14px; vertical-align: sub; margin: 0 10px 0 20px;" />
            联系我们
        </div>
        <div class="col-xs-1 init-div-horizontal">
            <img src="<c:url value="/assets/img/porfile_btn_right@2x.png"/>" style="width: 8px; vertical-align: sub;" />
        </div>
    </div>
</div>
                        
<div class="navbar navbar-default navbar-fixed-bottom">
    <div class="btn-group btn-group-justified" role="group">
        <a href="<c:url value="/user/home"/>" class="btn menu-btn menu-index">
            <img width="30%" >
            <p>首页</p>
        </a>
        <a href="<c:url value="/user/orders"/>" class="btn menu-btn menu-order">
            <img width="30%">
            <p >订单</p>
        </a>
        <a href="<c:url value="/user/notification"/>" class="btn menu-btn menu-news">
            <img width="30%">
            <%--<c:if test="${LOGIN_USER.badge > 0}">--%>
                <!--<span class="badge user-badge">${LOGIN_USER.badge}</span>-->
            <%--</c:if>--%>
            <%--<c:if test="${LOGIN_USER.badge == 0}">--%>
                <span class="badge user-badge hide"></span>
            <%--</c:if>--%>
            <p>消息</p>
        </a>
        <a href="javascript:;" class="btn menu-btn menu-profile active">
            <img width="30%">
            <p>我的</p>
        </a>
    </div>
</div>         

<div class="modal fade bs-modal-lg" id="contact-modal" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;margin-top: 50%;margin-left: 5%;margin-right: 5%">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="custom-modal-body" style="">
                <div class="col-xs-12 init-div">
                    <div class="col-xs-2 init-div">
                        <img src="<c:url value="/assets/img/porfile_icon_contact_tel@2x.png"/>" width="60%">
                    </div>
                    <div class="col-xs-9 init-div">
                        <p class="custom-phone-number">18518390515</p>
                    </div>
                </div>
            </div>
            <div class="custom-modal-footer">
                <div class="col-xs-12 init-div">
                    <div class="col-xs-6 init-div" style="border-right: 1px solid #EFEFEF;height: 48px">
                        <center>
                            <a href="tel://18518390515"><label class="modal-button" type="button" style="color:#3D6DBB">立即拨打</label></a>
                        </center>
                    </div>
                    <div class="col-xs-6 init-div" style="height: 48px">
                        <center><label class="modal-button" type="button" data-dismiss="modal">取消</label></center>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>            