/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.addEventListener('pagehide', function(e) {
    var $body = $(document.body);
    $body.children().remove();

    // wait for this callback to finish executing and then...
    setTimeout(function() {
        $body.append("<script type='text/javascript'>window.location.reload();<\/script>");
    });
});

$(function() {    
    $('.goods-kind-tab').click(function() {
        $('.left_bar > div').each(function() {
            var tab = $(this);
            tab.removeClass('active');
        });
        
        $(this).addClass('active');
        var shopId = $('#shop-id').val();
        var goodsKindId = $(this).attr("id").replace('tab_', '');
        
        $('#active-goodskind').val(goodsKindId);
        
        onLoadGoodsList(goodsKindId, shopId);
    });
    
    $('.btn_minus').click(function() {
        var id = $(this).attr("id");
        id = id.replace("btn-minus-", "");
        var amount = parseInt($("#oper-amount-" + id).text());
        
        if (amount == 1) {
            $("#btn-group-" + id).hide();
            $("#btn-group-" + id).addClass("hide");
            $("#btn-addcart-" + id).removeClass("hide");
            $("#btn-addcart-" + id).show();    
        } else {
            amount--;
            $("#oper-amount-" + id).text(amount);   
        }       
        
        setOrder($('#shop-id').val(), id, -1);
        
    });
    
    $(".btn_plus").click(function() {
        var id = $(this).attr("id");
        id = id.replace("btn-plus-", "");
        var amount = parseInt($("#oper-amount-" + id).text());
        amount++;
        $("#oper-amount-" + id).text(amount);
        
        setOrder($('#shop-id').val(), id, 1);
    });

    $('.btn_addcart').click(function(){
        var id = $(this).attr("id");
        id = id.replace("btn-addcart-", "");        
        $('#btn-group-' + id).removeClass("hide");
        $('#btn-group-' + id).show();
        $('#oper-amount-' + id).text("1");
        $(this).addClass("hide");
        $(this).hide();
        
        $('.add-badge').removeClass("hide");        
        $('.add-badge').show();
        
        setOrder($('#shop-id').val(), id, 1);
    });
    
//    if (window.history && window.history.pushState) {
//
//        window.history.pushState('forward', null, '#forward');
//
//        $(window).on('popstate', function() {
//            if ($('#stationPage').val() == stationPage)
//                location.href = 'station?stationId=' + $('#stationId').val() + "&startLat=" + $('#startLat').val() + "&startLng=" + $('#startLng').val() + "&endLat=" + $('#endLat').val() + "&endLng=" + $('#endLng').val() + "&page=" + $('#page').val();
//            else if ($('#stationPage').val() == takeoutPage)
//                location.href = 'takeout?stationId=' + $('#stationId').val() + "&startLat=" + $('#startLat').val() + "&startLng=" + $('#startLng').val() + "&endLat=" + $('#endLat').val() + "&endLng=" + $('#endLng').val() + "&page=" + $('#page').val();
//            else
//                location.href = 'pharmacy?stationId=' + $('#stationId').val() + "&startLat=" + $('#startLat').val() + "&startLng=" + $('#startLng').val() + "&endLat=" + $('#endLat').val() + "&endLng=" + $('#endLng').val() + "&page=" + $('#page').val();
//        });
//
//    }

    onLoadGoodsKindList($('#shop-id').val());
    onLoadFooter($('#shop-id').val());
       
    var container_height = $(document).height();
    var top_height = $('.diancan').height();
    var bottom_height = $('.bt_cart').height();
    
    var left_bar_height = container_height - top_height - bottom_height;
    
    $('.left_bar').height(left_bar_height);
    $('.left_bar').css('background-color', '#f4f4f4');
});

function onLoadGoodsKindList(_shopId) {
    $('.left_bar').empty();
    $.ajax({
        url: "getGoodsKindList",
        cache: false,
        data: {shopId: _shopId},
        dataType: "html",
        success: function(result) {
            $('.left_bar').html(result);
            
            $('.goods-kind-tab').click(function() {
                $('.left_bar > div').each (function() {
                    var tab = $(this);
                    tab.removeClass('active');
                });

                $(this).addClass('active');
                var shopId = $('#shop-id').val();
                var goodsKindId = $(this).attr("id").replace('tab_', '');

                $('#active-goodskind').val(goodsKindId);

                onLoadGoodsList(goodsKindId, shopId);
            });
            
            onLoadGoodsList($('#active-goodskind').val(), _shopId);
        }
    });
}

function onLoadFooter(_shopId) {
    $('.bt_cart').empty();
    $.ajax({
        url: "shopFooter",
        cache: false,
        data: {shopId: _shopId},
        dataType: "html",
        success: function(result) {
            $('.bt_cart').html(result);
        }
    });
}

function onLoadGoodsList(_goodsKindId, _shopId) {
    $('#content').empty();
    $.ajax({
        url: "getGoodsList",
        cache: false,
        data: {goodsKindId: _goodsKindId, shopId: _shopId},
        dataType: "html",
        success: function(result) {
            $('#content').html(result);
            
            var right_bar_height = $('#content').height();
            
            var container_height = $(document).height();
            var top_height = $('.diancan').height();
            var bottom_height = $('.bt_cart').height();
            var left_bar_height = container_height - top_height - bottom_height;
            
            if (right_bar_height > left_bar_height)
                left_bar_height = right_bar_height;
    
            $('.left_bar').height(left_bar_height);
            $('.left_bar').css('background-color', '#f4f4f4');
            
            $('.btn_minus').click(function() {
                var id = $(this).attr("id");
                id = id.replace("btn-minus-", "");
                var amount = parseInt($("#oper-amount-" + id).text());

                if (amount == 1) {
                    $("#btn-group-" + id).hide();
                    $("#btn-group-" + id).addClass("hide");
                    $("#btn-addcart-" + id).removeClass("hide");
                    $("#btn-addcart-" + id).show();    
                } else {
                    amount--;
                    $("#oper-amount-" + id).text(amount);   
                }       

                setOrder($('#shop-id').val(), id, -1);

            });

            $(".btn_plus").click(function() {
                var id = $(this).attr("id");
                id = id.replace("btn-plus-", "");
                var amount = parseInt($("#oper-amount-" + id).text());
                amount++;
                $("#oper-amount-" + id).text(amount);

                setOrder($('#shop-id').val(), id, 1);
            });

            $('.btn_addcart').click(function(){
                var id = $(this).attr("id");
                id = id.replace("btn-addcart-", "");        
                $('#btn-group-' + id).removeClass("hide");
                $('#btn-group-' + id).show();
                $('#oper-amount-' + id).text("1");
                $(this).addClass("hide");
                $(this).hide();

                $('.add-badge').removeClass("hide");        
                $('.add-badge').show();

                setOrder($('#shop-id').val(), id, 1);
            });
        }
    });
}

function onGoodsModal(_goodsId) {
    $.ajax({
        url: "getGoodsInfo",
        cache: false,
        data: {goodsId: _goodsId, shopId: $('#shop-id').val()},
        success: function(data) {
            if (data != null && data != "") {
                var json = eval(data);
                $('#goodsImage').attr("src", "/" + json.imagePath);
                $('#goodsName').text(json.name);
                $('#goodsPrice').text("￥"+json.price);
                $('#goodsDescription').text(json.description);
                $('#goodsId').val(json.id);
                $('#addCart').empty();
                if (json.isOrdertime) {
                    if (json.qty > 0) {
                        $('#addCart').append("<div class=\"btn_minus\" id=\"btn-minus\" onclick=\"onCartMinus();\"></div>");
                        $('#addCart').append("<div class=\"oper_amount\" id=\"oper-amount\">" + json.qty + "</div>");
                        $('#addCart').append("<div class=\"btn_plus\" id=\"btn-plus\" onclick=\"onCartPlus();\"></div>");
                    } else {
                        $('#addCart').append("<img style=\"width: 33px; height: 28px\" onclick=\"onAddCart();\" id=\"newCart\">");
                        $("#newCart").attr("src", $('#get-btn-addcart-icon-url').val());
                    }
                }
                $('#goods_modal').modal({show:true});
            }
        }
    });
    
}

function onAddCart() {
    var goodsId = $('#goodsId').val();
//    alert($('#goodsId').val());
    $('#addCart').empty();
    
    $('#addCart').append("<div class=\"btn_minus\" id=\"btn-minus\" onclick=\"onCartMinus();\"></div>");
    $('#addCart').append("<div class=\"oper_amount\" id=\"oper-amount\">1</div>");
    $('#addCart').append("<div class=\"btn_plus\" id=\"btn-plus\" onclick=\"onCartPlus();\"></div>");
    
    $('#btn-group-' + goodsId).removeClass("hide");
    $('#btn-group-' + goodsId).show();
    $('#oper-amount-' + goodsId).text("1");
    $('#btn-addcart-' + goodsId).addClass("hide");
    $('#btn-addcart-' + goodsId).hide();
    
    $('.add-badge').removeClass("hide");        
    $('.add-badge').show();    

    setOrder($('#shop-id').val(), goodsId, 1);
    
}

function onCartMinus() {
    var goodsId = $('#goodsId').val();
    var amount = parseInt($("#oper-amount").text());
    
    if (amount == 1) {
        $('#addCart').empty();
        $('#addCart').append("<img style=\"width: 33px; height: 28px\" onclick=\"onAddCart();\" id=\"newCart\">");
        $("#newCart").attr("src", $('#get-btn-addcart-icon-url').val());     
        
        $("#btn-group-" + goodsId).hide();
        $("#btn-group-" + goodsId).addClass("hide");
        $("#btn-addcart-" + goodsId).removeClass("hide");
        $("#btn-addcart-" + goodsId).show();
        
    } else {
        amount--;
        
        $("#oper-amount").text(amount);
        
        $("#oper-amount-" + goodsId).text(amount);
    }
    
    setOrder($('#shop-id').val(), goodsId, -1);
}

function onCartPlus() {
    var goodsId = $('#goodsId').val();
    var amount = parseInt($("#oper-amount").text());
    amount++;
    $("#oper-amount").text(amount);
        
    $("#oper-amount-" + goodsId).text(amount);
    
    setOrder($('#shop-id').val(), goodsId, 1);
}

function setOrder(_shopId, _goodsId, _qty) {
    $.ajax({
        url: "setOrder",
        cache: false,
        data: {shopId: _shopId, goodsId: _goodsId, qty: _qty},
        success: function(data) {
            if (data != null && data != '') {
                var json = eval(data);
                
                var startFee = json.startFee;
                var amount = json.amount;
                var qty = json.qty;
                var shippingFee = json.shippingFee;
                
                if (amount >= startFee) {
                    $('#btn-order-submit').attr("onclick", "onGoOrderSubmit('" + json.id + "');");
                    $('#btn-order-submit').text("提交订单");
                    $('#btn-order-submit').removeClass("des");
                    $('#btn-order-submit').addClass("oper");
                } else if (amount == 0) {
                    $('#btn-order-submit').removeAttr("onclick");
                    $('#btn-order-submit').text(startFee + "元起送");        
                    $('#btn-order-submit').removeClass("oper");
                    $('#btn-order-submit').addClass("des");
                } else {
                    $('#btn-order-submit').removeClass("oper");
                    $('#btn-order-submit').addClass("des");
                    $('#btn-order-submit').removeAttr("onclick");
                    $('#btn-order-submit').text("差" + $.number((startFee - amount), 2) + "元起送");
                }
                
                if (qty > 0) {
                    $('.add-badge').text(qty);
                    $('.add-bage').removeClass("hide");
                    $('.add-badge').show();
                    $('#amount-info').text('共￥' + $.number(amount,2));
                    $('#shipping-fee-info').text('另需配送费￥' + shippingFee);
                    $('#amount-info').css("margin-top", "0");
                    $('.ic_cart').attr("onclick", "onShowCart('" + json.id + "');");
                } else {
                    $('.add-badge').hide();
                    $('.add-bage').addClass("hide");

                    $('#shipping-fee-info').text("");

                    $("#amount-info").css("margin-top", "10px");
                    $("#amount-info").text("购物车是空的");
                    
                    $('.ic_cart').removeAttr("onclick");
                }
                
                $.ajax({
                   url: "getGoodsKindInfo",
                   cache: false,
                   data: {shopId: _shopId},
                   success: function(_result) {
                       if (_result != null && _result != '') {
                           var _json = eval(_result);
                           var goodsKinds = _json.goodsKind;
                           for (var i = 0; i < goodsKinds.length; i++) {
                               var goodsKind = goodsKinds[i];
                               
                               if (goodsKind.qty > 0) {
                                   $('#goodskind-badge-' + goodsKind.id).text(goodsKind.qty);
                                   $('#goodskind-badge-' + goodsKind.id).removeClass('hide');
                                   $('#goodskind-badge-' + goodsKind.id).show();
                               } else {
                                   $('#goodskind-badge-' + goodsKind.id).text(goodsKind.qty);
                                   $('#goodskind-badge-' + goodsKind.id).addClass('hide');
                                   $('#goodskind-badge-' + goodsKind.id).hide();
                               }
                           }
                       }
                   }
                });
            }
        }
    });
}

function setCartOrder(_shopId, _goodsId, _qty) {
    $.ajax({
        url: "setOrder",
        cache: false,
        data: {shopId: _shopId, goodsId: _goodsId, qty: _qty},
        success: function(data) {
            if (data != null && data != '') {
                var json = eval(data);
                
                var startFee = json.startFee;
                var amount = json.amount;
                var qty = json.qty;
                var shippingFee = json.shippingFee;
                var orderId = json.id;
                
                if (amount >= startFee) {
                    $('#btn-order-submit').attr("onclick", "onGoOrderSubmit('" + json.id + "');");
                    $('#btn-order-submit').text("提交订单");
                    $('#btn-order-submit').removeClass("des");
                    $('#btn-order-submit').addClass("oper");
                } else if (amount == 0) {
                    $('#btn-order-submit').removeAttr("onclick");
                    $('#btn-order-submit').text(startFee + "元起送");        
                    $('#btn-order-submit').removeClass("oper");
                    $('#btn-order-submit').addClass("des");
                } else {
                    $('#btn-order-submit').removeClass("oper");
                    $('#btn-order-submit').addClass("des");
                    $('#btn-order-submit').removeAttr("onclick");
                    $('#btn-order-submit').text("差" + $.number((startFee - amount),2) + "元起送");
                }
                
                if (qty > 0) {
                    $('.add-badge').text(qty);
                    $('.add-bage').removeClass("hide");
                    $('.add-badge').show();
                    $('#amount-info').text('共￥' + $.number(amount,2));
                    $('#shipping-fee-info').text('另需配送费￥' + shippingFee);
                    $('#amount-info').css("margin-top", "0");
                    $('.ic_cart').attr("onclick", "onShowCart('" + json.id + "');");                    
                } else {
                    $('.add-badge').hide();
                    $('.add-bage').addClass("hide");

                    $('#shipping-fee-info').text("");

                    $("#amount-info").css("margin-top", "10px");
                    $("#amount-info").text("购物车是空的");
                    
                    $('.ic_cart').removeAttr("onclick");
                }
                
                $.ajax({
                   url: "getGoodsKindInfo",
                   cache: false,
                   data: {shopId: _shopId},
                   success: function(_result) {
                       if (_result != null && _result != '') {
                           var _json = eval(_result);
                           var goodsKinds = _json.goodsKind;
                           for (var i = 0; i < goodsKinds.length; i++) {
                               var goodsKind = goodsKinds[i];
                               
                               if (goodsKind.qty > 0) {
                                   $('#goodskind-badge-' + goodsKind.id).text(goodsKind.qty);
                                   $('#goodskind-badge-' + goodsKind.id).removeClass('hide');
                                   $('#goodskind-badge-' + goodsKind.id).show();
                               } else {
                                   $('#goodskind-badge-' + goodsKind.id).text(goodsKind.qty);
                                   $('#goodskind-badge-' + goodsKind.id).addClass('hide');
                                   $('#goodskind-badge-' + goodsKind.id).hide();
                               }
                           }
                       }
                   }
                });
                
                $.ajax({
                    url: "getGoodsListInfo",
                    cache: false,
                    data: {shopId: _shopId},
                    success: function(_result) {
                        if (_result != null && _result != "") {
                            var json = eval(_result);
                            var goodsList = json.goodsList;
                            for (var i = 0; i < goodsList.length; i++) {
                                var goods = goodsList[i];
                                $('#oper-amount-' + goods.id).text(goods.qty);
                                if (goods.qty > 0) {                                    
                                    $("#btn-group-" + goods.id).removeClass("hide");
                                    $("#btn-group-" + goods.id).show();
                                    $("#btn-addcart-" + goods.id).addClass("hide");
                                    $("#btn-addcart-" + goods.id).hide();
                                } else {
                                    $("#btn-group-" + goods.id).hide();
                                    $("#btn-group-" + goods.id).addClass("hide");
                                    $("#btn-addcart-" + goods.id).removeClass("hide");
                                    $("#btn-addcart-" + goods.id).show();
                                }
                            }
                        }     
                    }        
                });
                
            }
        }
    });
}


function onGoOrderSubmit(_orderId) {    
//    alert('submit');
    
    var form = $('<form action="orderSubmit" method="get">' +
        '<input type="hidden" name="orderId" value="' + _orderId + '" />' +
        '<input type="hidden" name="shopId" value="' + $('#shop-id').val() + '" />' +
        '<input type="hidden" name="stationId" value="' + $('#stationId').val() + '" />' +
        '<input type="hidden" name="startLat" value="' + $("#startLat").val() + '" />' +
        '<input type="hidden" name="startLng" value="' + $("#startLng").val() + '" />' +
        '<input type="hidden" name="endLat" value="' + $("#endLat").val() + '" />' +
        '<input type="hidden" name="endLng" value="' + $("#endLng").val() + '" />' +
        '<input type="hidden" name="page" value="' + $("#page").val() + '" />' +
        '<input type="hidden" name="stationPage" value="' + $('#stationPage').val() + '" />' +
        '<input type="hidden" name="submitPage" value="' + shopPage + '" />' +
        '<input type="hidden" name="backShopId" value="' + $('#shop-id').val() + '" />' +
        '<input type="hidden" name="historyLength" value="' + getHistoryLength() + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
    
}

function onShowCart(_orderId) {
//    alert('cart');
    $.ajax({
       url: "getGoodsListInfo",
       cache: false,
       data: {orderId: _orderId},
       success: function(_result) {
           if (_result != null && _result != "") {
                var json = eval(_result);
                var goodsList = json.goodsList;
                $('#cart-container').empty();
                for (var i = 0; i < goodsList.length; i++) {
                    var goods = goodsList[i];
                    $('#cart-container').append(
                        "<div class=\"list_row\" id=\"list-row-" + goods.id + "\">" +
                        "<div class=\"list_row_internal\">" +
                        "<input type=\"hidden\" id=\"cart-price-" + goods.id + "\" value=\"" + parseFloat(goods.price) + "\" />" +
                        "<div class=\"col-xs-6 init-div\">" + goods.name + "</div>" +
                        "<div class=\"col-xs-3 init-div\">￥" + goods.price + "</div>" +
                        "<div class=\"col-xs-3 init-div cart_oper\">" +
                        "<div class=\"btn_minus cart-minus\" id=\"cart-minus-" + goods.id + "\"></div>" +
                        "<div class=\"oper_amount\" id=\"cart-qty-" + goods.id + "\">" + goods.qty + "</div>" +
                        "<div class=\"btn_plus cart-plus\" id=\"cart-plus-" + goods.id + "\"></div>" +
                        "</div>" +
                        "</div>" +
                        "</div>"
                    );
                }
                
                if (goodsList.length > 0) {
                    $('.cart-minus').unbind('click').click(function() {
                        var goodsId = $(this).attr("id");
                        goodsId = goodsId.replace("cart-minus-", "");
                        
                        var qty = 0;
                        if (!isNaN($('#cart-qty-' + goodsId).text()))
                            qty = parseInt($('#cart-qty-' + goodsId).text());
                        
                        if (qty > 0) {
                            qty--;
                            
                            $('#cart-qty-' + goodsId).text(qty);
                            setCartOrder($('#shop-id').val(), goodsId, -1);
                            if (qty == 0) {
                                $('#list-row-' + goodsId).remove();
//                                var height = 0;
//                                $('.list_row > div').each(function() {
//                                    height += $(this).height();
//                                });
//                                $('.list_row').css("height", height);
                            }
                        } else {
                            $('#cart-qty-' + goodsId).text('0');
                        }
                            
                    });
                    $('.cart-plus').unbind('click').click(function() {
                        var goodsId = $(this).attr("id");
                        goodsId = goodsId.replace("cart-plus-", "");
                        
                        var qty = 0;
                        if (!isNaN($('#cart-qty-' + goodsId).text()))
                            qty = parseInt($('#cart-qty-' + goodsId).text());
                        
                        qty++;
                        
                        $('#cart-qty-' + goodsId).text(qty);
                        setCartOrder($('#shop-id').val(), goodsId, 1);
                        
                        $('.add-badge').removeClass("hide");        
                        $('.add-badge').show();
                    });
                    $('#dlg-cart').modal({show: true});
                }
           }           
       }
    });
}

function onDeleteCart() {
    var qty = $('.cart_modal .oper_amount');
    
    qty.each(function(i, o) {
        $(o).html('0');
    });
    
    $('#dlg-cart').modal("hide");
    
    $.ajax({
       url: "deleteAllOrder" ,
       cache: false,
       data: {shopId: $('#shop-id').val()},
       success: function(_result) {
            $('#btn-order-submit').removeAttr("onclick");
            $('#btn-order-submit').text(_result + "元起送");        
            $('#btn-order-submit').removeClass("oper");
            $('#btn-order-submit').addClass("des");
            
            $('.add-badge').hide();
            $('.add-bage').addClass("hide");

            $('#shipping-fee-info').text("");

            $("#amount-info").css("margin-top", "10px");
            $("#amount-info").text("购物车是空的");

            $('.ic_cart').removeAttr("onclick");
                
            $.ajax({
                url: "getGoodsKindInfo",
                cache: false,
                data: {shopId: $('#shop-id').val()},
                success: function(_result) {
                    if (_result != null && _result != '') {
                        var _json = eval(_result);
                        var goodsKinds = _json.goodsKind;
                        for (var i = 0; i < goodsKinds.length; i++) {
                            var goodsKind = goodsKinds[i];

                            if (goodsKind.qty > 0) {
                                $('#goodskind-badge-' + goodsKind.id).text(goodsKind.qty);
                                $('#goodskind-badge-' + goodsKind.id).removeClass('hide');
                                $('#goodskind-badge-' + goodsKind.id).show();
                            } else {
                                $('#goodskind-badge-' + goodsKind.id).text(goodsKind.qty);
                                $('#goodskind-badge-' + goodsKind.id).addClass('hide');
                                $('#goodskind-badge-' + goodsKind.id).hide();
                            }
                        }
                    }
                }
            });
            
           $.ajax({
                url: "getGoodsListInfo",
                cache: false,
                data: {shopId: $('#shop-id').val()},
                success: function(_result) {
                    if (_result != null && _result != "") {
                        var json = eval(_result);
                        var goodsList = json.goodsList;
                        for (var i = 0; i < goodsList.length; i++) {
                            var goods = goodsList[i];
                            $('#oper-amount-' + goods.id).text(goods.qty);
                            if (goods.qty > 0) {                                    
                                $("#btn-group-" + goods.id).removeClass("hide");
                                $("#btn-group-" + goods.id).show();
                                $("#btn-addcart-" + goods.id).addClass("hide");
                                $("#btn-addcart-" + goods.id).hide();
                            } else {
                                $("#btn-group-" + goods.id).hide();
                                $("#btn-group-" + goods.id).addClass("hide");
                                $("#btn-addcart-" + goods.id).removeClass("hide");
                                $("#btn-addcart-" + goods.id).show();
                            }
                        }
                    }     
                }        
            });
        }
    });
}
