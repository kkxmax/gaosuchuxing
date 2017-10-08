/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function() {
    $('.goods-kind-tab').click(function() {
        $('.left_bar > div').each (function() {
            var tab = $(this);
            tab.removeClass('active');
        });
        
        $(this).addClass('active');
        var shopId = $('#shop-id').val();
        var goodsKindId = $(this).attr("id").replace('tab_', '');
        
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
    });
    
    $(".btn_plus").click(function() {
        var id = $(this).attr("id");
        id = id.replace("btn-plus-", "");
        var amount = parseInt($("#oper-amount-" + id).text());
        amount++;
        $("#oper-amount-" + id).text(amount);
    });

    $('.btn_addcart').click(function(){
        var id = $(this).attr("id");
        id = id.replace("btn-addcart-", "");

        $('#btn-group-' + id).removeClass("hide");
        $('#btn-group-' + id).show();
        $('#oper-amount-' + id).text("1");
        $(this).addClass("hide");
        $(this).hide();
    });
});

function onLoadGoodsList(_goodsKindId, _shopId) {
    $('#content').empty();
    $.ajax({
        url: "getGoodsList",
        cache: false,
        data: {goodsKindId: _goodsKindId, shopId: _shopId},
        dataType: "html",
        success: function(result) {
            $('#content').html(result);
            
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
            });

            $(".btn_plus").click(function() {
                var id = $(this).attr("id");
                id = id.replace("btn-plus-", "");
                var amount = parseInt($("#oper-amount-" + id).text());
                amount++;
                $("#oper-amount-" + id).text(amount);
            });

            $('.btn_addcart').click(function(){
                var id = $(this).attr("id");
                id = id.replace("btn-addcart-", "");

                $('#btn-group-' + id).removeClass("hide");
                $('#btn-group-' + id).show();
                $('#oper-amount-' + id).text("1");
                $(this).addClass("hide");
                $(this).hide();
            });
        }
    });
}

function onGoodsModal(_goodsId) {
    $.ajax({
        url: "getGoodsInfo",
        cache: false,
        data: {goodsId: _goodsId},
        success: function(data) {
            if (data != null && data != "") {
                var json = eval(data);
                $('#goodsImage').attr("src", "/" + json.imagePath);
                $('#goodsName').text(json.name);
                $('#goodsPrice').text("￥"+json.price);
                $('#goodsDescription').text(json.description);
                $('#goodsId').val(json.id);
                if (json.qty > 0) {
                    $('#addCart').empty();
    
                    $('#addCart').append("<div class=\"btn_minus\" id=\"btn-minus\" onclick=\"onCartMinus();\"></div>");
                    $('#addCart').append("<div class=\"oper_amount\" id=\"oper-amount\">1</div>");
                    $('#addCart').append("<div class=\"btn_plus\" id=\"btn-plus\" onclick=\"onCartPlus();\"></div>");
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
}

function onCartMinus() {
    var goodsId = $('#goodsId').val();
    var amount = parseInt($("#oper-amount").text());
    if (amount == 1) {
        $('#addCart').empty();
        $('#addCart').append("<img style=\"width: 33px; height: 28px\" onclick=\"onAddCart();\" id=\"newCart\">");
        $("#newCart").attr("src", $('#get-btn-addcart-icon-url').val());
    } else {
        amount--;
        $("#oper-amount").text(amount);
    }
}

function onCartPlus() {
    var goodsId = $('#goodsId').val();
    var amount = parseInt($("#oper-amount").text());
    amount++;
    $("#oper-amount").text(amount);
}




