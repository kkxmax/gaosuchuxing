/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function showMessage(_msg) {
    $('#confirm-message-msg').html(_msg);
    $('#confirm-message-modal').modal({show: true});
}

function confirmYesNoMessage(_msg, _callback) {
    $('#confirm-yes-no-msg').html(_msg);
    $('#confirm-yes-no-modal').modal({show: true});
    
    $('#button-yes').unbind('click').click(function() {
        $('#confirm-yes-no-modal').modal('hide');
        if (_callback) {
            _callback(true);
        }
    });
}

function showInformation(_msg) {
    $('#information-message-msg').html(_msg);
    $('#information-message-modal').modal({show: true});
}

function onVerify() {
    if ($('#telno').val()=='') {
        showMessage('请输入手机号');
        return;
    }
    
    if ($('#telno').val().length < 11) {
        showMessage('手机号码错误');
        return;
    }
    
    $.ajax({
       url: $('#verify-url').val(),
       cache: false,
       data: {telno: $('#telno').val()},
       success: function(_result) {
           if (_result != null && _result == 'success')
               showInformation("发送成功!");
           else    
               showMessage(_result);
       }
    });
}

function onSignup() {
    if ($('#telno').val()=='') {
        showMessage('请输入手机号');
        return;
    } else if ($('#code').val()=='') {
        showMessage('请输入验证码');
        return;
    }
    
    $.ajax({
        url: $('#signup-url').val(),
        cache: false,
        type: 'POST',
        data: {telno: $('#telno').val(), code: $('#code').val()},
        success: function(_data) {
            if (_data != null && _data != '') {
                try {
                    var json = eval(_data);
                    $('#userInfo').text(json.name + " " + json.telno);
                    $('#signup-dlg').modal('hide');
                    showInformation('注册成功');
                } catch (e) {
                    showMessage(_data);
                }
            } 
        }
    });
}

function onTake() {
    $.ajax({
       url: $("#validUser-url").val(),
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
        url: $('#take-adv-url').val(),
        cache: false,
        data: {shareNum: $('#shareNum').val(), noticeId: $('#noticeId').val()},
        success: function(_data) {
            if (_data != null) {
                try {
                    var json = eval(_data);
                    var type = json.type;
                    var kind = json.kind;
                    var price = json.price;
                    var userId = json.userId;
                                       
                    $('#userId').text(userId);
                    if (type == "full_type") {
                        $('#coupon-kind').text(kind + "可用");
                        $('#blue-amount').text(price);
                        $('#blue-coupon-bg').removeClass('hide');
                        $('#blue-coupon-bg').show();
                        $('#red-coupon-bg').addClass('hide');
                        $('#red-coupon-bg').hide();
                        $('#blue-amount').removeClass('hide');
                        $('#blue-amount').show();
                        $('#red-amount').addClass('hide');
                        $('#red-amount').hide();                        
                        $('#blue-coupon-amount').removeClass('hide');
                        $('#blue-coupon-amount').show();
                        $('#red-coupon-amount').addClass('hide');
                        $('#red-coupon-amount').hide();                        
                        $('#coupon-kind').css("color", "#3E6DBB");
                        $('#coupon-detail').css("color", "#3E6DBB");
                    } else {
                        $('#coupon-kind').text("满0元可用");
                        $('#red-amount').text(price);
                        $('#blue-coupon-bg').addClass('hide');
                        $('#blue-coupon-bg').hide();
                        $('#red-coupon-bg').removeClass('hide');
                        $('#red-coupon-bg').show();
                        $('#blue-amount').addClass('hide');
                        $('#blue-amount').hide();
                        $('#red-amount').removeClass('hide');
                        $('#red-amount').show();
                        $('#blue-coupon-amount').addClass('hide');
                        $('#blue-coupon-amount').hide();
                        $('#red-coupon-amount').removeClass('hide');
                        $('#red-coupon-amount').show();
                        $('#coupon-kind').css("color", "#FF3E59");
                        $('#coupon-detail').css("color", "#FF3E59");
                    }
                    $('#coupon-modal').modal();
                } catch (e) {
//                    alert(_data);
                    showMessage(_data);
                }
            }
        }
    })
}
