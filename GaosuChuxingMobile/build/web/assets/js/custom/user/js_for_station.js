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
//        $(window).on('popstate', function(e) {
////            alert('Back button was pressed.');
//            if ($('#page').val() == homePage)
//                location.href = 'home';
//            else
//                location.href = 'searchRoute?startLat=' + $('#startLat').val() + "&startLng=" + $('#startLng').val() + "&endLat=" + $('#endLat').val() + "&endLng=" + $('#endLng').val();
//        });
//
//    }
})

function onGoCart(_stationId) {
    var form = $('<form action="cart" method="get">' +
        '<input type="hidden" name="stationId" value="' + _stationId + '" />' +
        '<input type="hidden" name="startLat" value="' + $("#startLat").val() + '" />' +
        '<input type="hidden" name="startLng" value="' + $("#startLng").val() + '" />' +
        '<input type="hidden" name="endLat" value="' + $("#endLat").val() + '" />' +
        '<input type="hidden" name="endLng" value="' + $("#endLng").val() + '" />' +
        '<input type="hidden" name="page" value="' + $("#page").val() + '" />' +
        '<input type="hidden" name="stationPage" value="' + stationPage + '" />' +
        '<input type="hidden" name="historyLength" value="' + getHistoryLength() + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
}

function onNotSupport() {
    $('#myModal').modal();
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
        '<input type="hidden" name="stationPage" value="' + stationPage + '" />' +
        '<input type="hidden" name="backShopId" value="' + _shopId + '" />' +
        '<input type="hidden" name="historyLength" value="' + getHistoryLength() + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
}
