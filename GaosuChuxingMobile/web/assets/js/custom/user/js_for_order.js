/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function() {
    $('body').css("background-color", "#f5f5f5");
    
    onLoadOrderContent($('#orderId').val());
    
//    if (window.history && window.history.pushState) {
//
//        window.history.pushState('forward', null, '#forward');
//
//        $(window).on('popstate', function() {
//            if ($('#orderPage').val() == notificationPage)
//                location.href = 'notification';
//            else
//                location.href = 'orders';
//        });
//
//    }
})

function onLoadOrderContent(_orderId) {
    $("#content").empty();
    $.ajax({
        url: "getOrderContent",
        data: {orderId: _orderId},
        cache: false,
        dataType: "html",
        success: function(result) {
            $("#content").html(result);        
            var cnt = $('#txt_opinion').val().length;
            $('#textarea_type_cnt').html(cnt + "/50");
        }
    });
}

function onGoShop(_shopId) {    
    var form = $('<form action="shop" method="get">' +
        '<input type="hidden" name="shopId" value="' + _shopId + '" />' +
        '<input type="hidden" name="stationId" value="' + $("#stationId").val() + '" />' +
        '<input type="hidden" name="startLat" value="' + $("#startLat").val() + '" />' +
        '<input type="hidden" name="startLng" value="' + $("#startLng").val() + '" />' +
        '<input type="hidden" name="endLat" value="' + $("#endLat").val() + '" />' +
        '<input type="hidden" name="endLng" value="' + $("#endLng").val() + '" />' +
        '<input type="hidden" name="page" value="' + $("#page").val() + '" />' +
        '<input type="hidden" name="stationPage" value="' + $('#stationPage').val() + '" />' +
        '<input type="hidden" name="backShopId" value="' + _shopId + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
}