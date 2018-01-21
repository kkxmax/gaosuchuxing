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
//   setTimeout(onValidOrder, 10);

//    if (window.history && window.history.pushState) {
//
//        window.history.pushState('forward', null, '#forward');
//
//        $(window).on('popstate', function() {
//            if ($('#submitPage').val() == cartPage)
//                location.href = 'cart?stationId=' + $('#stationId').val() + "&startLat=" + $('#startLat').val() + "&startLng=" + $('#startLng').val() + "&endLat=" + $('#endLat').val() + "&endLng=" + $('#endLng').val() + "&page=" + $('#page').val();
//            else if ($('#submitPage').val() == shopPage)
//                location.href = 'shop?shopId=' + $('#backShopId').val() + "&startLat=" + $('#startLat').val() + "&startLng=" + $('#startLng').val() + "&endLat=" + $('#endLat').val() + "&endLng=" + $('#endLng').val() + "&page=" + $('#page').val()  + "&stationPage=" + $('#stationPage').val() + "&stationId=" + $('#stationId').val();
//            else
//                location.href = 'searchPharmacy?shopId=' + $('#backShopId').val() + "&searchKey=" + $('#backSearchKey').val() + "&startLat=" + $('#startLat').val() + "&startLng=" + $('#startLng').val() + "&endLat=" + $('#endLat').val() + "&endLng=" + $('#endLng').val() + "&page=" + $('#page').val() + "&stationPage=" + $('#stationPage').val() + "&stationId=" + $('#stationId').val();
//        });
//
//    }
    
    doingInputText($('#txt_opinion').val());
    
    $('#txt_opinion').on("change input paste keyup", function() {
        doingInputText($(this).val());
    });
    
//    onLoadContent();

    $('.predictTimepicker').on('click', function() {
        $('#timepicker-modal').modal();
    });
        
    $('.time-line').on('click', function(){
        $('.modal-body > div').each(function() {
            var div = $(this);
            div.removeClass('active');            
        });
        
        $(this).addClass('active');
        $('#selectTime').val($(this).text().trim());
    });
    
    $('#btn-predict-time').on('click', function() {
        var now = new Date();
        var after = now.setHours(now.getHours() + 1);
        
        var seltime = $('#selectTime').val();
        var seltimes = seltime.split(":");
        
        var sel = new Date().setHours(seltimes[0], seltimes[1], 0);
        
        if (sel < after && seltime + "前" != $('#predictTime').text()) {
            showMessage("预订送达时间必须大于当前时间+1小时");
        } else {
            $('#predictTime').text($('#selectTime').val() + "前");
            $('#timepicker-modal').modal('hide');
            $('#prevTime').val($('#selectTime').val());
        }        
    });
    
    $('#btn-predict-cancel').on('click', function() {
        $('.modal-body > div').each(function() {
            var div = $(this);
            div.removeClass('active');            
            if (div.text().trim() == $('#prevTime').val())
                div.addClass('active');
        });
        $('#timepicker-modal').modal('hide');
    });

//    $('#predictTime').mobiscroll().time({
//        theme: 'android-ics light', //皮肤样式
//        display: 'modal', //显示方式 
//        mode: 'mixed', //日期选择模式
//        timeFormat: 'HH:ii前',
//        timeWheels: 'HHii',
//        lang: 'zh',
//        minDate: new Date(new Date().setHours(10, 0, 0, 0)),
//        maxDate: new Date(new Date().setHours(22, 0, 0, 0)),
//        stepMinute: 30,
//        onSelect: function(valueText, instance) {
////            alert(valueText);
//            var org = $('#predict-time').val();
//            if (org != '') {
//                org = org.replace("前", "");
//                var orgTimes = org.split(":");
//                
//                var val = valueText.replace("前", "");
//                var valTimes = val.split(":");
//                
//                var orgDate = new Date().setHours(orgTimes[0], orgTimes[1], 0);
//                var valDate = new Date().setHours(valTimes[0], valTimes[1], 0);
//                
//                if (orgDate > valDate) {
//                    showMessage("预订送达时间必须大于当前时间+1小时");
//                    $('#predictTime').val($('#predict-time').val());
//                }
//            } else {
//                showMessage("预订送达时间错误");
//                $('#predictTime').val(org);
//            }
//        }
//    });
    
});

function onLoadContent() {
    $('#content').empty();
    $.ajax({
        url: "submitContent",
        cache: false,
        data: {orderId: $('#orderId').val()},
        dataType: "html",
        success: function(result) {
            $('#content').html(result);
        }
    });
}

function doingInputText(str) {
    var cnt = str.trim().length;
    $('#textarea_type_cnt').html(cnt + "/50");
}

function onGoUserCoupon(_orderId) {
    
    var form = $('<form action="useCoupon" method="get">' +
        '<input type="hidden" name="orderId" value="' + _orderId + '" />' +
        '<input type="hidden" name="opinion" value="' + $('#txt_opinion').val() + '" />' +
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
        '<input type="hidden" name="predictTime" value="' + $('#predictTime').text() + '" />' +
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
        '<input type="hidden" name="stationPage" value="' + $("stationPage").val() + '" />' +
        '<input type="hidden" name="backShopId" value="' + _shopId + '" />' +
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
//           alert(JSON.stringify(res));
            if(res.err_msg == "get_brand_wcpay_request:ok" ) {
//               alert("支付成功"); 
                var form = $('<form action="settleOrder" method="get">' +
                    '<input type="hidden" name="orderId" value="' + $('#orderId').val() + '" />' +
                    '<input type="hidden" name="desc" value="' + $('#txt_opinion').val() + '" />' +
                    '<input type="hidden" name="stationId" value="' + $('#stationId').val() + '" />' +
                    '<input type="hidden" name="startLat" value="' + $("#startLat").val() + '" />' +
                    '<input type="hidden" name="startLng" value="' + $("#startLng").val() + '" />' +
                    '<input type="hidden" name="endLat" value="' + $("#endLat").val() + '" />' +
                    '<input type="hidden" name="endLng" value="' + $("#endLng").val() + '" />' +
                    '<input type="hidden" name="page" value="' + $("#page").val() + '" />' +
                    '<input type="hidden" name="stationPage" value="' + $('#stationPage').val() + '" />' +
                    '<input type="hidden" name="submitPage" value="' + $('#submitPage').val() + '" />' +
                    '<input type="hidden" name="backShopId" value="' + $('#backShopId').val() + '" />' +
                    '<input type="hidden" name="backSearchKey" value="' + $('#searchKey').val() + '" />' +
                    '<input type="hidden" name="historyLength" value="' + getHistoryLength() + '" />' +
                    '<input type="hidden" name="predictTime" value="' + $('#predictTime').text() + '" />' +
                    '</form>');
                 $('body').append(form);
                 form.submit();
            } else if(res.err_msg == "get_brand_wcpay_request:cancel"){
                showMessage('支付取消');
            } else if(res.err_msg == "get_brand_wcpay_request:fail" ){
                showMessage('支付失败');   
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
               
                $.ajax({
                     url: 'validOrder',
                     cache: false,
                     data: {orderId: _orderId},
                     success: function(_result) {
                        if (_result != null && _result == 'success') {
                            
                            $.ajax({
                                url: "wxPrepay",
                                cache: false,
                                data: {orderId: _orderId},
                                success: function(_data) {
                                    if (_data != null && _data != 'error' && _data != 'noPay') {
                                        try {
                                            var json = eval(_data); 
                                            appid = json.appid;
                                            timestamp = json.timestamp;
                                            noncestr = json.noncestr;
                                            package = "prepay_id=" + json.prepayid;
                                            signtype = "MD5";
                                            paysign = json.sign;

//                                            alert(JSON.stringify(json));
//                                            var form = $('<form action="settleOrder" method="get">' +
//                                                '<input type="hidden" name="orderId" value="' + $('#orderId').val() + '" />' +
//                                                '<input type="hidden" name="desc" value="' + $('#txt_opinion').val() + '" />' +
//                                                '<input type="hidden" name="stationId" value="' + $('#stationId').val() + '" />' +
//                                                '<input type="hidden" name="startLat" value="' + $("#startLat").val() + '" />' +
//                                                '<input type="hidden" name="startLng" value="' + $("#startLng").val() + '" />' +
//                                                '<input type="hidden" name="endLat" value="' + $("#endLat").val() + '" />' +
//                                                '<input type="hidden" name="endLng" value="' + $("#endLng").val() + '" />' +
//                                                '<input type="hidden" name="page" value="' + $("#page").val() + '" />' +
//                                                '<input type="hidden" name="stationPage" value="' + $('#stationPage').val() + '" />' +
//                                                '<input type="hidden" name="submitPage" value="' + $('#submitPage').val() + '" />' +
//                                                '<input type="hidden" name="backShopId" value="' + $('#backShopId').val() + '" />' +
//                                                '<input type="hidden" name="backSearchKey" value="' + $('#searchKey').val() + '" />' +
//                                                '<input type="hidden" name="historyLength" value="' + getHistoryLength() + '" />' +
//                                                '<input type="hidden" name="predictTime" value="' + $('#predictTime').text() + '" />' +
//                                                '</form>');
//                                            $('body').append(form);
//                                            form.submit();

                                            if (typeof WeixinJSBridge == "undefined"){
                                                if( document.addEventListener ) {
                                                    document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
                                                } else if (document.attachEvent) {
                                                    document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
                                                    document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
                                                }
                                            } else {
                                                onBridgeReady();
                                            }

                                        } catch (e) {
                                            alert('json parse error');
                                        }
                                    } else if (_data == 'noPay') {
                                        var form = $('<form action="settleOrder" method="get">' +
                                                '<input type="hidden" name="orderId" value="' + $('#orderId').val() + '" />' +
                                                '<input type="hidden" name="desc" value="' + $('#txt_opinion').val() + '" />' +
                                                '<input type="hidden" name="stationId" value="' + $('#stationId').val() + '" />' +
                                                '<input type="hidden" name="startLat" value="' + $("#startLat").val() + '" />' +
                                                '<input type="hidden" name="startLng" value="' + $("#startLng").val() + '" />' +
                                                '<input type="hidden" name="endLat" value="' + $("#endLat").val() + '" />' +
                                                '<input type="hidden" name="endLng" value="' + $("#endLng").val() + '" />' +
                                                '<input type="hidden" name="page" value="' + $("#page").val() + '" />' +
                                                '<input type="hidden" name="stationPage" value="' + $('#stationPage').val() + '" />' +
                                                '<input type="hidden" name="submitPage" value="' + $('#submitPage').val() + '" />' +
                                                '<input type="hidden" name="backSearchKey" value="' + $('#backSearchKey').val() + '" />' +
                                                '<input type="hidden" name="backShopId" value="' + $('#backShopId').val() + '" />' +
                                                '<input type="hidden" name="historyLength" value="' + getHistoryLength() + '" />' +
                                                '<input type="hidden" name="predictTime" value="' + $('#predictTime').text() + '" />' +
                                                '</form>');
                                        $('body').append(form);
                                        form.submit();
                                    } else {
                                        alert('prepay error');
                                    }
                                }
                            });
                            
                        } else {
                            showMessage('订单无效');
                        }
                    }
                }); 
                
           } else {
               $('#signup-dlg').modal({show: true});
           }
       }
    });
}

function onValidOrder() {
    var orderId = $('#orderId').val();
    
    if (orderId == '') {
        location.href = $('#home-url').val();
        return;
    }
    
    $.ajax({
       url: 'validOrder',
       cache: false,
       data: {orderId: orderId},
       success: function(_result) {
           if (_result == null && _result != 'success')
               location.href = $('#home-url').val();
       }
    });
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
       url: 'verify',
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
//        alert('请输入手机号');
        showMessage('请输入手机号');
        return;
    } else if ($('#code').val()=='') {
//        alert('请输入验证码');
        showMessage('请输入验证码');
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
                    showInformation('注册成功');
                } catch (e) {
                    showMessage(_data);
                }
            } 
        }
    }); 
}