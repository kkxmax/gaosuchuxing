/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function() {
//    if (window.history && window.history.pushState) {
//
//        window.history.pushState('forward', null, '#forward');
//
//        $(window).on('popstate', function() {
//            location.href = 'station?stationId=' + $('#stationId').val() + "&startLat=" + $('#startLat').val() + "&startLng=" + $('#startLng').val() + "&endLat=" + $('#endLat').val() + "&endLng=" + $('#endLng').val() + "&page=" + $('#page').val();
//        });
//
//    }

    onLoadCartList($("#station-id").val());
});

window.addEventListener('pagehide', function(e) {
    var $body = $(document.body);
    $body.children().remove();

    // wait for this callback to finish executing and then...
    setTimeout(function() {
        $body.append("<script type='text/javascript'>window.location.reload();<\/script>");
    });
});

function onLoadCartList(_stationId) {
    $('#content').empty();
    $.ajax({
        url: "getCartList",
        cache: false,
        data: {stationId: _stationId},
        dataType: "html",
        success: function(result) {
            $('#content').html(result);
        }
    });
}

function onGoOrderSubmit(_orderId) {
//    alert('submit');
    
    var form = $('<form action="orderSubmit" method="get">' +
        '<input type="hidden" name="orderId" value="' + _orderId + '" />' +
        '<input type="hidden" name="stationId" value="' + $('#stationId').val() + '" />' +
        '<input type="hidden" name="startLat" value="' + $("#startLat").val() + '" />' +
        '<input type="hidden" name="startLng" value="' + $("#startLng").val() + '" />' +
        '<input type="hidden" name="endLat" value="' + $("#endLat").val() + '" />' +
        '<input type="hidden" name="endLng" value="' + $("#endLng").val() + '" />' +
        '<input type="hidden" name="page" value="' + $("#page").val() + '" />' +
        '<input type="hidden" name="stationPage" value="' + $('#stationPage').val() + '" />' +
        '<input type="hidden" name="submitPage" value="' + cartPage + '" />' +
        '<input type="hidden" name="historyLength" value="' + getHistoryLength() + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
    
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
        '<input type="hidden" name="stationPage" value="' + $("#stationPage").val() + '" />' +
        '<input type="hidden" name="backShopId" value="' + _shopId + '" />' +
        '<input type="hidden" name="historyLength" value="' + getHistoryLength() + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
}
