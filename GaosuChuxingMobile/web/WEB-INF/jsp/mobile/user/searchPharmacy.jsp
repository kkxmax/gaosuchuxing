<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<input type="hidden" id="shopStartFee" value="${shop.startFee}">
<input type="hidden" id="shop-id" value="${shop.id}">
<div class="delivery container-fluid container-lf-space main-body init-div-horizontal">
    <div class="diancan">
        <div class="waimai top_desc">
            <div class="row init-div" style="padding: 10px">
                <div class="col-xs-9 init-div">
                    <div style="border-radius: 17px !important; background-color: #f5f5f5; border: solid 1px #dedede">
                        <div style="background: url('<c:url value="/assets/img/medicine_icon_serch@2x.png"/>') no-repeat; width: 11px; height: 11px; float: left; margin: 7px 10px; background-size: contain"></div>
                        <input type="text" placeholder="输入您想要的商品" style="width: 80%; background: transparent; border: none; height: 26px" value="${searchKey}" id="searchKey">
                    </div>
                </div>
                <div class="col-xs-3 init-div" onclick="onSearch();">
                    <div style="border-radius: 17px !important; background-color: #3e6dbb; color: white; text-align: center; height: 28px; padding-top: 4px; float: right; min-width: 90%">搜索</div>
                </div>
            </div>
        </div>
                        
        <div class="right_bar" style="padding: 0px 10px" id="content">
        </div>
    </div>
</div>

<div class="bt_cart">
</div>

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