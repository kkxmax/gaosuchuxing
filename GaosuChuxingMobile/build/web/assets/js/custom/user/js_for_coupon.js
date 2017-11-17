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
//            if ($('#page').val() == homePage)
//                location.href = "home"
//            else
//                location.href = 'profile';
//        });
//
//    }
})

function consumeCoupon(_id) {
    confirmYesNoMessage("你使用优惠券吗？", function(msg){
        if (msg) {
            $.ajax({
                url: "consumeCoupon",
                data: {id: _id, orderId: $('#orderId').val()},
                success: function(_result) {
                    if (_result != null && _result == 'success') {
                        var form = $('<form action="orderSubmit" method="get">' +
                            '<input type="hidden" name="orderId" value="' + $('#orderId').val() + '" />' +
                            '<input type="hidden" name="fromCoupon" value="1" />' +
                            '<input type="hidden" name="opinion" value="' + $('#opinion').val() + '" />' +
                            '<input type="hidden" name="stationId" value="' + $('#stationId').val() + '" />' +
                            '<input type="hidden" name="startLat" value="' + $("#startLat").val() + '" />' +
                            '<input type="hidden" name="startLng" value="' + $("#startLng").val() + '" />' +
                            '<input type="hidden" name="endLat" value="' + $("#endLat").val() + '" />' +
                            '<input type="hidden" name="endLng" value="' + $("#endLng").val() + '" />' +
                            '<input type="hidden" name="page" value="' + $("#page").val() + '" />' +
                            '<input type="hidden" name="stationPage" value="' + $('#stationPage').val() + '" />' +
                            '<input type="hidden" name="submitPage" value="' + $('#submitPage').val() + '" />' +
                            '<input type="hidden" name="backShopId" value="' + $('#backShopId').val() + '" />' +
                            '<input type="hidden" name="backSearchKey" value="' + $('#backSearchKey').val() + '" />' +
                            '<input type="hidden" name="historyLength" value="' + getHistoryLength() + '" />' +
                            '<input type="hidden" name="predictTime" value="' + $('#predictTime').val() + '" />' +
                            '</form>');
                         $('body').append(form);

                         form.submit();
//                        window.history.back();
                    } else {
                        showMessage('优惠券失败');
                    }
                }
             });
        }
    });
}

function onUnLimitedCoupon(_id) {
    consumeCoupon(_id);
}

function onFullCoupon(_couponId) {
    $.ajax({
        url: "validCoupon",
        data: {couponId: _couponId, orderId: $('#orderId').val()},
        success: function(_result) {
            if (_result != null && _result != 'error') {
                if (isNaN(_result)) {
                    showMessage('优惠券不能使用');
                } else {
                    consumeCoupon(_couponId, $('#orderId').val());
                }
                
            } else {
                showMessage('优惠券不能使用');
            }    
        }
    });
}
