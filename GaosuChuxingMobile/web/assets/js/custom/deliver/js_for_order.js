/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function() {
    onLoadOrderContent($('#orderId').val());
    onLoadOrderFooter($('#orderId').val());
    
//    if (window.history && window.history.pushState) {
//
//        window.history.pushState('forward', null, '#forward');
//
//        $(window).on('popstate', function() {
////            alert('Back button was pressed.');
//            if ($('#orderPage').val() == "homePage")
//                location.href = 'home'; 
//            else
//                location.href = 'notification';
//        });
//
//    }
})

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

function setOrderStatus(_orderId, _status) {
    $.ajax({
        url: "setOrderStatus",
        data: {orderId: _orderId, status: _status},
        cache: false,
        success: function(result) {
            onLoadOrderContent(_orderId);
            onLoadOrderFooter(_orderId);
        }
    });
}

function onLoadOrderContent(_orderId) {
    $("#content").empty();
    $.ajax({
        url: "getOrderContent",
        data: {orderId: _orderId},
        cache: false,
        dataType: "html",
        success: function(result) {
            $("#content").html(result);            
        }
    });
}

function onLoadOrderFooter(_orderId) {
    $("#footer").empty();
    $.ajax({
        url: "getOrderFooter",
        data: {orderId: _orderId},
        cache: false,
        dataType: "html",
        success: function(result) {
            $("#footer").html(result);            
        }
    });
}

