<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<input type="hidden" id="shop-id" value="${shop.id}">
<div class="delivery container-fluid container-lf-space main-body init-div-horizontal">
    <div class="diancan">
        <div class="waimai top_desc"> 
            <c:if test="${pharmacy != null}">
                <form action="searchPharmacy" method="POST" id="searchForm">
                    <div class="row init-div" style="background-color: #3e6dbb; padding: 10px">
                        <div style="border-radius: 17px !important; background-color: #abc0df">
                            <div style="background: url('<c:url value="/assets/img/medicine_icon_serch@2x.png"/>') no-repeat; width: 11px; height: 11px; float: left; margin: 7px 10px; background-size: contain"></div>
                            <input class="input_search" type="text" placeholder="输入您想要的商品" style="width: 80%; background: transparent; border: none; height: 26px" name="searchKey">
                            <input type="hidden" name="shopId" value="${shop.id}">
                            <input type="hidden" name="stationId" value="${stationId}">
                            <input type="hidden" name="startLat" value="${startLat}">
                            <input type="hidden" name="startLng" value="${startLng}">
                            <input type="hidden" name="endLat" value="${endLat}">
                            <input type="hidden" name="endLng" value="${endLng}">
                            <input type="hidden" name="page" value="${page}">
                            <input type="hidden" name="stationPage" value="${stationPage}">  
                            <fmt:parseNumber var = "fmtLength" type = "number" value = "${historyLength}" />
                            <input type="hidden" name="historyLength" value="${fmtLength+1}">
                        </div>
                    </div>
                </form>        
            </c:if>
            
            <div style="color: white">
                <div class="list_row">
                    <div class="list_row_internal" style="color: white; border-bottom-style: dashed">
                        <div class="col-xs-4 init-div">
                            <div class="row_img">
                                <img src="/${shop.imagePath}">
                            </div>
                        </div>
                        <div class="col-xs-8 init-div" style="padding-top: 5px">
                            <div class="row init-div title">${shop.name}</div>
                            <div class="row init-div">
                                <div class="col-xs-6 init-div">起送 ￥${shop.startFee}</div>
                                <div class="col-xs-6 init-div">配送 ￥${shop.shippingFee}</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="padding: 7px 4px; background-color: white; font-size: 10px; background-color: #3e6dbb">
                    <div class="bell" style="background: url('<c:url value="/assets/img/foodshop_icon_inform@2x.png"/>'); background-size: contain"></div>
                    上午：9：00 —下午：7：00下单，其他时间不提供下单，请谅解！
                </div>
            </div>
        </div>
        <div class="tabbable-line">
            <ul class="nav nav-tabs" style="border-bottom: 2px solid #dfdfe1;">
                <li class="text-center active" style="width: 24%;">
                    <a href="#tab_1_1" data-toggle="tab">商品</a>
                </li>
            </ul>
        </div>
        
        <div class="col-xs-3 init-div left_bar"></div>
                    
        <div class="col-xs-9 init-div right_bar" id="content" style="overflow: auto;">
        </div>
    </div>
</div>

<div class="bt_cart"></div>

<!-- Modal -->
<div class="modal fade-in-up" id="dlg-cart" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog cart_modal">
        <div class="modal-content">
            <div class="modal-body cart_detail">
                <div class="row init-div" style="border-left: solid 4px #3e6dbb; background-color: #efefef; padding: 5px 5px 5px 0px">
                    <div style="float: left; margin-left: 10px">已选商品</div>
                    <div style="float: right; background: url('<c:url value="/assets/img/foodshop_shopcart_btn_delete@2x.png"/>') no-repeat; padding-left: 30px; height: 17px; background-size: contain" onclick="onDeleteCart();">清空购物车</div>
                </div>
                <div id="cart-container"></div>
            </div>
        </div>
    </div>
</div>
                

<div class="modal fade" id="goods_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog my_modal" style="margin-top: 150px;">
        <div class="btn_close" data-dismiss="modal"></div>
        <div class="modal-content" style="width: 300px;">
            <img src="<c:url value="/assets/img/bg_top_1.png"/>" style="width: 100%" id="goodsImage">
            <div class="modal-body">
                <div class="row init-div" style="border-bottom: solid 1px #dedede">
                    <p style="font-size: 15px; font-weight: bold" id="goodsName"></p>
                    <p style="color: #ff3c64; font-size: 14px" id="goodsPrice"></p>
                </div>
                <div class="row init-div" style="padding-top: 10px">
                    <p style="color: #939393;">商品信息</p>
                    <p id="goodsDescription"></p>
                </div>
                <input type="hidden" id="goodsId"/>
                <div class="row init-div" style="float: right; padding-bottom: 10px" id="addCart">
                    <img src="<c:url value="/assets/img/foodshop_btn_addcart@2x.png"/>" style="width: 33px; height: 28px" onclick="onAddCart();">
                </div>
            </div>
        </div>
    </div>
</div>                