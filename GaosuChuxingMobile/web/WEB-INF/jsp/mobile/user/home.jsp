<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input type="hidden" id="welcomeId" value="${loginUserId}" />

<div>
    <div id="map" class="map" style="position: fixed; width: 100%; height: calc(100% - 248px); bottom: 248px;">
    </div>
    
    <a href="javascript:;" onclick="gotoCurrentPosition();">
        <img src="<c:url value="/assets/img/index2_position@2x.png"/>" style="position: fixed; left: 10px; right: 0; bottom: 300px; width: 40px; height: 40px;" >
    </a>
    
    <div class="map" style="position: fixed; left: 0; right: 0; bottom: 68px;">
        <div class="list_row">
            <div class="list_row_internal">
                <div class="s_start"></div>
                <div id="user-address">
                    <input type="text" id="search-start-address" placeholder="输入起点" style="border: none; border-color: transparent; outline: none; width: 80%;">
                </div>
            </div>
        </div>
        <div class="list_row">
            <div class="list_row_internal">
                <div class="s_end"></div>
                <div><input type="text" id="search-end-address" placeholder="输入终点" style="border: none; border-color: transparent; outline: none; width: 80%;"></div>
            </div>
        </div>
        <div class="list_row">
            <div style="padding-top: 15px">
                <div id="btn-search-route" class="btn_search_line hide" onclick="searchRoute()"></div>
            </div>
        </div>
    </div>
    
</div>
<!-- END CONTENT -->

<div class="navbar navbar-default navbar-fixed-bottom">
    <div class="btn-group btn-group-justified" role="group">
        <a href="javascript:;" class="btn menu-btn menu-index active">
            <img width="30%" >
            <p>首页</p>
        </a>
        <a href="<c:url value="/user/orders"/>" class="btn menu-btn menu-order">
            <img width="30%">
            <p >订单</p>
        </a>
        <a href="<c:url value="/user/notification"/>" class="btn menu-btn menu-news">
            <img width="30%">
            <c:if test="${LOGIN_USER.badge > 0}">
                <span class="badge">${LOGIN_USER.badge}</span>
            </c:if>
            <c:if test="${LOGIN_USER.badge == 0}">
                <span class="badge hide"></span>
            </c:if>
            <p>消息</p>
        </a>
        <a href="<c:url value="/user/profile"/>" class="btn menu-btn menu-profile">
            <img width="30%">
            <p>我的</p>
        </a>
    </div>
</div>

<input type="hidden" id="user-position-error" value="" />

<div class="modal fade" id="welcome-modal" tabindex="-1" aria-hidden="false">
    <div class="modal-dialog" style="margin-top: 25%" onclick="$('#welcome-modal').modal('hide');">
        <img src="<c:url value="/assets/img/welcome.png"/>" style="width: 100%" >

<!--        <div class="row init-div" style="margin-top: -60%;position: absolute;width: 100%">
            <div class="col-xs-12 init-div-horizontal">

                <div class="row init-div">
                    <div class="col-xs-12 init-div-horizontal">
                        <center><p style="color: white;font-weight: bold;font-size: 16px;">有惊喜哦</p></center>
                    </div>
                </div>

                <div class="row init-div" style="text-align: center">
                    <div class="col-xs-12 init-div-horizontal">
                        <p style="margin-top: 16%;font-size: 9px;color: white;">
                            感谢您的关注，特赠送给您优惠券已<br>
                            放到个人中心，敬请查收，祝您路涂<br>
                            用餐愉快
                        </p>
                    </div>
                </div>

                <div class="row init-div">
                    <div class="col-xs-12 init-div-horizontal text-center" style="margin-top: 5%">
                        <div class="col-xs-3"></div>
                        <div class="col-xs-6 init-div-horizontal">
                            <img src="<c:url value="/assets/img/white_button.png"/>" width="80%" onclick="$('#welcome-modal').modal('hide');">
                        </div>
                        <div class="col-xs-2"></div>
                    </div>
                </div>

            </div>
        </div>-->
    </div>
</div>