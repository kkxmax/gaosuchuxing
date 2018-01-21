<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="delivery container-fluid container-lf-space main-body init-div-horizontal">
    <div class="waimai">
        <div class="top_bg"><img src="/${station.imagePath}"></div>
        <div class="border"></div>
        <div class="row init-div" style="padding: 10px 0px">
            <div class="col-xs-3 init-div" onclick="onGoTakeout('${station.id}')">
                <div class="row init-div menu_symbol">
                    <img src="<c:url value="/assets/img/home_menu_food@2x.png"/>">
                </div>
                <div class="row init-div menu_title">外卖美食</div>
            </div>
            <div class="col-xs-3 init-div" onclick="onGoStationInfo('${station.id}')">
                <div class="row init-div menu_symbol">
                    <img src="<c:url value="/assets/img/home_menu_rest@2x.png"/>">
                </div>
                <div class="row init-div menu_title" style="max-width: 67px">服务区信息</div>
            </div>
            <div class="col-xs-3 init-div" onclick="onNotSupport()">
                <div class="row init-div menu_symbol">
                    <img src="<c:url value="/assets/img/home_menu_medicine1@2x.png"/>">
                </div>
                <div class="row init-div menu_title">自驾旅行</div>
            </div>
            <div class="col-xs-3 init-div" onclick="onNotSupport()">
                <div class="row init-div menu_symbol">
                    <img src="<c:url value="/assets/img/home_menu_rescue@2x.png"/>">
                </div>
                <div class="row init-div menu_title">救援维修</div>
            </div>
        </div>
        <div class="border"></div>
        <div style="padding: 7px 4px; background-color: white; font-size: 10px; color: #fe4059;">
            <div class="bell"></div>
            上午：9：00 —下午：7：00下单，其他时间不提供下单，请谅解！
        </div>
        <div class="border"></div>
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
                            <div class="col-xs-4 init-div" style="font-size: 12px;">起送 ￥${shop.startFee}</div>
                            <div class="col-xs-4 init-div" style="font-size: 12px;">配送 ￥${shop.shippingFee}</div>
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

<c:if test="${not empty orderTime}">
    <div onclick="onGoCart('${station.id}');" style="background: url('<c:url value="/assets/img/home_shopcart@2x.png"/>') no-repeat; width: 38px; height: 38px; position: fixed; bottom: 75px; right: 10px; background-size: 38px 38px"></div>
</c:if>

<div class="navbar navbar-default navbar-fixed-bottom">
    <div class="btn-group btn-group-justified" role="group">
        <a href="javascript:;" class="btn menu-btn menu-index active">
            <img width="30%" >
            <p>首页</p>
        </a>
        <a href="<c:url value="/user/order"/>" class="btn menu-btn menu-order">
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

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog my_modal">
        <div class="modal-content" style="border-radius: 5px !important; width: 300px">
            <div class="modal-body">
                <div class="row init-div" style="width: 100px; margin: auto; padding-bottom: 40px">
                    <img src="<c:url value="/assets/img/home_rescue_mask_disabled@2x.png"/>" style="width: 100px; ">
                </div>
                <div class="row init-div">
                    <p style="color: #356eb5; font-size: 16px; text-align: center">暂未开放，敬请期待</p>
                </div>
            </div>
        </div>
    </div>
</div>