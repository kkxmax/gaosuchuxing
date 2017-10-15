<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="delivery container-fluid container-lf-space main-body init-div-horizontal">
    <div class="waimai">
        <c:forEach var="shop" items="${shops}">
            <div class="list_row" onclick="onGoShop('${shop.id}')">
                <div class="list_row_internal">
                    <div class="col-xs-4 init-div">
                        <div class="row_img">
                            <img src="/${shop.imagePath}">
                        </div>
                    </div>
                    <div class="col-xs-8 init-div" style="padding-top: 5px">
                        <div class="row init-div title">${shop.name}</div>
                        <div class="row init-div">
                            <div class="col-xs-4 init-div">起送 ￥${shop.startFee}</div>
                            <div class="col-xs-4 init-div">配送 ￥${shop.shippingFee}</div>
                            <div class="col-xs-4 init-div">
                                <div class="btn_transfer"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<!-- END CONTENT -->

<div class="navbar navbar-default navbar-fixed-bottom">
    <div class="btn-group btn-group-justified" role="group">
        <a href="javascript:;" class="btn menu-btn menu-index active">
            <img width="30%" >
            <p>首页</p>
        </a>
        <a href="<c:url value="/user/order"/>" class="btn menu-btn menu-order ">
            <img width="30%">
            <p >订单</p>
        </a>
        <a href="<c:url value="/user/notification"/>" class="btn menu-btn menu-news">
            <img width="30%">
            <p>消息</p>
        </a>
        <a href="<c:url value="/user/profile"/>" class="btn menu-btn menu-profile">
            <img width="30%">
            <p>我的</p>
            <i class="order-numbers" id="carNum"></i>
        </a>
    </div>
</div>