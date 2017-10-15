/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function() {
//    $('#coupon-modal').modal();
})

function onVerify() {
    if ($('#telno').val()=='') {
        alert('请输入手机号');
        return;
    }
    
    $.ajax({
       url: 'verify',
       cache: false,
       data: {telno: $('#telno').val()},
       success: function(_result) {
           if (_result != null && _result != '')
               alert(_result);
       }
    });
}

function onSignup() {
    if ($('#telno').val()=='') {
        alert('请输入手机号');
        return;
    } else if ($('#code').val()=='') {
        alert('请输入验证吗');
        return;
    }
    
    $.ajax({
        url: 'signup',
        cache: false,
        type: 'POST',
        data: {telno: $('#telno').val(), code: $('#code').val()},
        success: function(_data) {
            if (_data != null && _data != '') {
                try {
                    var json = eval(_data);
                    $('#userInfo').text(json.name + " " + json.telno);
                    $('#signup-dlg').modal('hide');
                    alert('注册成功');
                } catch (e) {
                    alert(_data);
                }
            } 
        }
    });
}

function onTake() {
    $.ajax({
       url: "validUser",
       cache: false,
       success: function(_valid) {
           if (_valid != null && _valid == 'success') {
               onTakeAdv();
           } else {
               $('#signup-dlg').modal({show: true});
           }
        }       
    })    
}

function onTakeAdv() {
    $.ajax({
        url: "user/takeAdv",
        cache: false,
        data: {shareNum: $('#shareNum').val()},
        success: function(_data) {
            if (_data != null) {
                try {
                    var json = eval(_data);
                    var type = json.type;
                    var kind = json.kind;
                    var price = json.price;
                    
                    $('#coupon-kind').text(kind + "可用");
                    if (type == "full_type") {
                        $('#blue-amount').text(price);
                        $('blue-coupon-bg').removeClass('hide');
                        $('blue-coupon-bg').show();
                        $('red-coupon-bg').addClass('hide');
                        $('red-coupon-bg').hide();
                    } else {
                        $('#red-amount').text(price);
                        $('blue-coupon-bg').addClass('hide');
                        $('blue-coupon-bg').hide();
                        $('red-coupon-bg').removeClass('hide');
                        $('red-coupon-bg').show();
                    }
                    $('#coupon-modal').modal();
                } catch (e) {
                    alert(_data);
                }
            }
        }
    })
}
