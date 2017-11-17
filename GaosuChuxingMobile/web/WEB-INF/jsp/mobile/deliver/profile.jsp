<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="delivery container-fluid container-lf-space main-body init-div-horizontal">
    <div class="row init-div-horizontal">
        <div class="col-xs-12 init-div-horizontal">
            <div class="row init-div-horizontal" style="padding: 20px 15px 15px 20px;">
                <div class="col-xs-3 init-div-horizontal">
                    <c:if test="${not empty imgUrl}">
                        <img class="img-profile" src="${imgUrl}" style="width: 80%;" />
                    </c:if>
                    <c:if test="${empty imgUrl}">
                        <img class="img-profile" src="<c:url value="/assets/img/icon_phone2.png"/>" style="width: 80%;" />
                    </c:if>    
                </div>
                <div class="col-xs-9 init-div-horizontal" style="margin-top: 16px;">
                    <p class="init-div-vertical profile-title">${deliver.name}</p>
                    <p class="init-div-vertical profile-phone" style="margin-top: 5px;">${deliver.deliverId}</p>
                </div>
            </div>
            <!--<div style="position: absolute; top: 10px; right: 20%;">
                <img class="img-profile" src="../../assets/img/send_count_bg.png" style="width: 63px;" />
                <p class="init-div-vertical send-count-title">配送数量</p>
                <p class="init-div-vertical send-count-content">999</p>
            </div>-->
            <img class="img-profile" src="<c:url value="/assets/img/send_count_bg.png"/>" style="width: 63px; position: absolute; top: 0px; left: 77%;" />
            <p class="init-div-vertical send-count-title">
                配送数量<br/>
                <label class="send-count-content">${deliveryQty}</label>
            </p>
        </div>
    </div>
    <div class="row init-div-horizontal hr"></div>

    <div class="row init-div-horizontal">
        <div class="col-xs-12 init-div-horizontal margin-top-10 margin-bottom-10 profile-phone">
            <img src="<c:url value="/assets/img/my_icon1_grey@2x.png"/>" style="width: 14px; vertical-align: sub; margin: 0 10px 0 18px;" />
            ${deliver.telNo}
        </div>
    </div>
    <div class="row init-div-horizontal">
        <div class="col-xs-12 init-div-horizontal margin-bottom-10 profile-phone">
            <img src="<c:url value="/assets/img/my_icon3_grey@2x.png"/>" style="width: 14px; vertical-align: sub; margin: 0 10px 0 18px;" />
            ${stationName}
        </div>
    </div>
    <div class="row init-div-horizontal hr" style="height: 8px;"></div>

    <div class="row init-div-horizontal margin-top-20 margin-bottom-20 profile-content">
        <div class="col-xs-11 init-div-horizontal" onclick="location.href = '<c:url value="/deliver/password"/>'">
            <img src="<c:url value="/assets/img/my_icon4@2x.png"/>" style="width: 14px; vertical-align: sub; margin: 0 10px 0 20px;" />
            修改密码
        </div>
        <div class="col-xs-1 init-div-horizontal">
            <img src="<c:url value="/assets/img/porfile_btn_right@2x.png"/>" style="width: 8px; vertical-align: sub;" />
        </div>
    </div>
    <div class="row init-div-horizontal hr"></div>

    <div class="row init-div-horizontal margin-top-20 margin-bottom-20 profile-content">
        <div class="col-xs-11 init-div-horizontal" onclick="location.href = '<c:url value="/deliver/feedback"/>'">
            <img src="<c:url value="/assets/img/porfile_icon_opinion@2x.png"/>" style="width: 14px; vertical-align: sub; margin: 0 10px 0 20px;" />
            意见反馈
        </div>
        <div class="col-xs-1 init-div-horizontal">
            <img src="<c:url value="/assets/img/porfile_btn_right@2x.png"/>" style="width: 8px; vertical-align: sub;" />
        </div>
    </div>
    <div class="row init-div-horizontal hr"></div>

    <div class="row init-div-horizontal margin-top-20 margin-bottom-20 profile-content">
        <div class="col-xs-11 init-div-horizontal" onclick="location.href = '<c:url value="/deliver/about"/>'">
            <img src="<c:url value="/assets/img/porfile_icon_us@2x.png"/>" style="width: 14px; vertical-align: sub; margin: 0 10px 0 20px;" />
            关于我们
        </div>
        <div class="col-xs-1 init-div-horizontal">
        <img src="<c:url value="/assets/img/porfile_btn_right@2x.png"/>" style="width: 8px; vertical-align: sub;" />
        </div>
    </div>
    <div class="row init-div-horizontal hr"></div>

</div>
<!-- END CONTENT -->

<div class="navbar navbar-default navbar-fixed-bottom">
    <div class="btn-group btn-group-justified" role="group">
        <a href="<c:url value="/deliver/home"/>" class="btn menu-btn menu-order">
            <img width="30%" >
            <p>配送订单</p>
        </a>
        <a href="<c:url value="/deliver/notification"/>" class="btn menu-btn menu-news">
            <img width="30%">
            <c:if test="${LOGIN_DELIVER.badge > 0}">
                <span class="badge user-badge">${LOGIN_USER.badge}</span>
            </c:if>
            <c:if test="${LOGIN_DELIVER.badge == 0}">
                <span class="badge user-badge hide"></span>
            </c:if>
            <p>消息</p>
        </a>
        <a href="javascript:;" class="btn menu-btn menu-profile active">
            <img width="30%">
            <p>个人中心</p>
        </a>
    </div>
</div>