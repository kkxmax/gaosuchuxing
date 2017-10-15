<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
                    下单时间：上午11:00之前，下午5:00之前，其他时间不提供下单， 请谅解！
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
        <div class="col-xs-3 init-div left_bar">
            <input type="hidden" id="shop-id" value="${shop.id}">
            <c:set var="index" value="${0}" />       
            <c:forEach var="goodsKind" items="${goodsKinds}">
                <c:if test="${index == 0}">
                    <div class="list_row goods-kind-tab active" id="tab_${goodsKind.id}">     
                    <c:set var="activeGoodsKind" value="${goodsKind.id}" />    
                </c:if>
                
                <c:if test="${index != 0}">
                    <div class="list_row goods-kind-tab" id="tab_${goodsKind.id}">
                </c:if>
                
                    <div class="list_row_internal">
                        ${goodsKind.name}
                        <c:if test="${goodsKind.qty > 0}">
                            <span class="badge" id="goodskind-badge-${goodsKind.id}">${goodsKind.qty}</span>
                        </c:if>
                        <c:if test="${goodsKind.qty == 0}">
                            <span class="badge hide" id="goodskind-badge-${goodsKind.id}">0</span>
                        </c:if>    
                    </div>
                </div>
                <c:set var="index" value="${index + 1}" />        
            </c:forEach>
        </div>
            
        <input type="hidden" id="active-goodskind" value="${activeGoodsKind}" />    
                    
        <div class="col-xs-9 init-div right_bar" id="content">            
            <c:if test="${goodsList != null}">
                <div style="padding: 10px 10px 0px 10px; font-size: 12px">
                    <div style="border-left: solid 2px #3271c0; width: 5px; height: 13px; float: left; margin-top: 1px"></div>
                    ${goodsKindName}
                </div>
                <div class="list_row">
                    <c:forEach var="goods" items="${goodsList}">
                        <div class="list_row_internal">
                            <div class="col-xs-4 init-div" style="padding-top: 5px" onclick="onGoodsModal('${goods.id}')">
                                <div class="row_img">
                                    <img src="/${goods.imagePath}">
                                </div>
                            </div>
                            <div class="col-xs-8 init-div" style="padding-top: 5px">
                                <div class="row init-div title">${goods.name}</div>
                                <div class="row init-div des">${goods.description}</div>
                                <div class="row init-div">
                                    <div class="col-xs-6 init-div" style="color: #ff3c64" id="price-${goods.id}">￥${goods.price}</div>
                                    <div class="col-xs-6 init-div cart_oper">
                                        <c:if test="${goods.qty > 0}">
                                            <div class="oper-group" id="btn-group-${goods.id}">
                                                <div class="btn_minus" id="btn-minus-${goods.id}"></div>
                                                <div class="oper_amount" id="oper-amount-${goods.id}">${goods.qty}</div>
                                                <div class="btn_plus" id="btn-plus-${goods.id}"></div>
                                            </div>
                                            <div class="btn_addcart hide" id="btn-addcart-${goods.id}"></div>
                                        </c:if>
                                        <c:if test="${goods.qty == 0}">
                                            <div class="oper-group hide" id="btn-group-${goods.id}">
                                                <div class="btn_minus" id="btn-minus-${goods.id}"></div>
                                                <div class="oper_amount" id="oper-amount-${goods.id}">0</div>
                                                <div class="btn_plus" id="btn-plus-${goods.id}"></div>
                                            </div>
                                            <div class="btn_addcart" id="btn-addcart-${goods.id}"></div>
                                        </c:if>    
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </div>
</div>

<div class="bt_cart">
    <div class="col-xs-8 bt_cart_inner">
                    
        <c:if test="${order.orderQty > 0}">
            <div class="ic_cart" onclick="onShowCart('${order.id}');">
                <span class="add-badge badge">${order.orderQty}</span>
        </c:if>
        <c:if test="${order.orderQty == 0}">
            <div class="ic_cart">
                <span class="add-badge badge hide">0</span>
        </c:if>
        </div>
        <div class="price">
            <c:if test="${order.orderQty > 0}">
                <p style="font-size: 15px; margin-bottom: 0px" id="amount-info">共￥${order.orderAmount}</p>
                <p style="font-size: 11px" id="shipping-fee-info">另需配送费￥${order.shippingFee}</p>
            </c:if>
            <c:if test="${order.orderQty == 0}">
                <p style="font-size: 15px; margin-bottom: 0px; color: #b3b3b3; margin-top: 10px" id="amount-info">购物车是空的</p>
                <p style="font-size: 11px" id="shipping-fee-info"></p>
            </c:if>
        </div>
    </div>
    
    <c:if test="${order.orderAmount > 0}">
        <c:if test="${order.orderAmount >= shop.startFee}">
            <div class="col-xs-4 init-div oper" onclick="onGoOrderSubmit('${order.id}')" id="btn-order-submit">
                提交订单
            </div>
        </c:if>

        <c:if test="${order.orderAmount < shop.startFee}">
            <div class="col-xs-4 init-div des" id="btn-order-submit">
                <c:if test="${order.orderAmount == 0}">
                    ${shop.startFee}元起送
                </c:if>
                <c:if test="${order.orderAmount > 0}">
                    差${shop.startFee - order.orderAmount}元起送
                </c:if>    
            </div>
        </c:if>
    </c:if>
    
    <c:if test="${order.orderAmount == 0}">
        <div class="col-xs-4 init-div des" id="btn-order-submit">
            ${shop.startFee}元起送    
        </div>
    </c:if>
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