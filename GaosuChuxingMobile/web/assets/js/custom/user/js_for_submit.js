/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var appid;
var timestamp;
var noncestr;
var package;
var signtype;
var paysign;

$(function() {
   $('body').css("background-color", "#f5f5f5");
});

function doingInputText(str) {
    var cnt = str.length;
    $('#textarea_type_cnt').html(cnt + "/50");
}

function onGoUserCoupon(_token, _orderId) {
    if (_token == '')
        return;
    
    var form = $('<form action="useCoupon" method="post">' +
        '<input type="hidden" name="token" value="' + _token + '" />' +
        '<input type="hidden" name="orderId" value="' + _orderId + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
}

function onBridgeReady(){
    WeixinJSBridge.invoke(
       'getBrandWCPayRequest', {
           "appId":appid,     //公众号名称，由商户传入     
           "timeStamp":timestamp,         //时间戳，自1970年以来的秒数     
           "nonceStr":noncestr, //随机串     
           "package":package,     
           "signType":signtype,         //微信签名方式：     
           "paySign":paysign //微信签名 
       },
       function(res){     
            if(res.err_msg == "get_brand_wcpay_request:ok" ) {
               alert("支付成功"); 
            } else if(res.err_msg == "get_brand_wcpay_request:cancel"){
                alert('支付取消');
            } else if(res.err_msg == "get_brand_wcpay_request:fail" ){
                alert('支付失败');   
            } //使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
        }
    ); 
}

function onSubmit(_orderId) {
    $.ajax({
       url: "validUser",
       cache: false,
       success: function(_valid) {
           if (_valid != null && _valid == 'success') {
                var form = $('<form action="settleOrder" method="post">' +
                     '<input type="hidden" name="orderId" value="' + _orderId + '" />' +
                     '</form>');
                 $('body').append(form);
                 form.submit();
                    
//                $.ajax({
//                    url: "wxPrepay",
//                    cache: false,
//                    data: {orderId: _orderId},
//                    success: function(_data) {
//                        if (_data != null && _data != 'error') {
//                            try {
//                                var json = eval(_data);
//                                appid = json.appid;
//                                timestamp = json.timestamp;
//                                noncestr = json.noncestr;
//                                package = "prepay_id=" + json.prepayid;
//                                signtype = "MD5";
//                                paysign = json.sign;
//                                
//                                if (typeof WeixinJSBridge == "undefined"){
//                                    if( document.addEventListener ) {
//                                        document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
//                                    } else if (document.attachEvent) {
//                                        document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
//                                        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
//                                    }
//                                } else {
//                                    onBridgeReady();
//                                }
//                                
//                            } catch (e) {
//                                alert('json parse error');
//                            }
//                        } else {
//                            alert('prepay error');
//                        }
//                    }
//                })
           } else {
               $('#signup-dlg').modal({show: true});
           }
       }
    });
}

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