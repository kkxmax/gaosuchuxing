<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

<input type="hidden" id="user-position-error" value="" />