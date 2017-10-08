/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function() {
    onLoadOrderList("");
    
    $(".tab-order").click(function(){
        var id = $(this).attr("id");
        id = id.replace('btn', '');
        $("#tab-info").val(id);

        $('.tab-content > div').each(function() {
            var tab = $(this);
            tab.removeClass("active");
        });

        $("#tab" + id).addClass("active");
    });
});

function onSetActive(_id) {
    $('.tab-content > div').each(function() {
        var tab = $(this);
        tab.removeClass("active");
        $("#" + _id).addClass("active");
    });
}

function onLoadOrderList(_tabInfo) {
    $(".tab-content").empty();
    $.ajax({
        url: "getOrderList",
        data: {tabInfo: _tabInfo},
        cache: false,
        dataType: "html",
        success: function(result) {
            $(".tab-content").html(result);            
        }
    });
}

function setOrderStatus(_orderId, _status) {
    var tabInfo = $("#tab-info").val();
    $.ajax({
        url: "setOrderStatus",
        data: {orderId: _orderId, status: _status},
        cache: false,
        success: function(result) {
            onLoadOrderList(tabInfo);
        }
    });
}

function onShipping(_orderId) {
    confirmYesNoMessage('确定要配送了？', function(_msg) {
        setOrderStatus(_orderId, "配送中");
    });
}

function onDelivery(_orderId) {
    confirmYesNoMessage('确定要已送达？', function(_msg) {
        setOrderStatus(_orderId, "已完成");
    });
}

