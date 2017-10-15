/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function consumeCoupon(_id, _orderId) {
    confirmYesNoMessage("你使用优惠券吗？", function(msg){
        if (msg) {
            $.ajax({
                url: "consumeCoupon",
                data: {id: _id, orderId: _orderId},
                success: function(_result) {
                    if (_result != null && _result == 'success') {
                        var form = $('<form action="orderSubmit" method="post">' +
                             '<input type="hidden" name="orderId" value="' + _orderId + '" />' +
                             '</form>');
                         $('body').append(form);

                         form.submit();
                    } else {
                        alert('优惠券失败');
                    }
                }
             });
        }
    });
}

function onUnLimitedCoupon(_id, _orderId) {
    consumeCoupon(_id, _orderId);
}

function onFullCoupon(_couponId, _orderId) {
    $.ajax({
        url: "validCoupon",
        data: {couponId: _couponId, orderId: _orderId},
        success: function(_result) {
            if (_result != null && _result != 'error') {
                if (isNaN(_result)) {
                    alert('优惠券不能使用');
                } else {
                    consumeCoupon(_result, _orderId);
                }
                
            } else {
                alert('优惠券不能使用');
            }    
        }
    });
}
