/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var homePage = "homePage"; //for page
var searchRoutePage = "searchRoutePage"; //for page

var stationPage = "stationPage"; //for stationPage
var takeoutPage = "takeoutPage"; //for stationPage
var pharmacyPage = "pharmacyPage"; //for stationPage

var cartPage = "cartPage"; //for submitPage
var shopPage = "shopPage"; //for submitPage
var searchPharmacyPage = "searchPharmacyPage"; //for submitPage

var notificationPage = "notificationPage"; //for orderPage
var orderListPage = "orderListPage"; //for orderPage

var rootUrl = location.protocol+'//'+location.hostname+(location.port ? ':'+location.port: '');

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

function onGoTakeout(_stationId) {
    var form = $('<form action="takeout" method="get">' +
        '<input type="hidden" name="stationId" value="' + _stationId + '" />' +
        '<input type="hidden" name="startLat" value="' + $("#startLat").val() + '" />' +
        '<input type="hidden" name="startLng" value="' + $("#startLng").val() + '" />' +
        '<input type="hidden" name="endLat" value="' + $("#endLat").val() + '" />' +
        '<input type="hidden" name="endLng" value="' + $("#endLng").val() + '" />' +
        '<input type="hidden" name="page" value="' + $("#page").val() + '" />' +
        '<input type="hidden" name="stationPage" value="' + takeoutPage + '" />' +
        '<input type="hidden" name="historyLength" value="' + getHistoryLength() + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
}

function onGoPharmacy(_stationId) {
    var form = $('<form action="pharmacy" method="get">' +
        '<input type="hidden" name="stationId" value="' + _stationId + '" />' +
        '<input type="hidden" name="startLat" value="' + $("#startLat").val() + '" />' +
        '<input type="hidden" name="startLng" value="' + $("#startLng").val() + '" />' +
        '<input type="hidden" name="endLat" value="' + $("#endLat").val() + '" />' +
        '<input type="hidden" name="endLng" value="' + $("#endLng").val() + '" />' +
        '<input type="hidden" name="page" value="' + $("#page").val() + '" />' +
        '<input type="hidden" name="stationPage" value="' + pharmacyPage + '" />' +
        '<input type="hidden" name="historyLength" value="' + getHistoryLength() + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
}

function onGoStationInfo(_stationId) {
    var form = $('<form action="stationInfo" method="get">' +
        '<input type="hidden" name="stationId" value="' + _stationId + '" />' +
        '<input type="hidden" name="startLat" value="' + $("#startLat").val() + '" />' +
        '<input type="hidden" name="startLng" value="' + $("#startLng").val() + '" />' +
        '<input type="hidden" name="endLat" value="' + $("#endLat").val() + '" />' +
        '<input type="hidden" name="endLng" value="' + $("#endLng").val() + '" />' +
        '<input type="hidden" name="page" value="' + $("#page").val() + '" />' +
        '<input type="hidden" name="stationPage" value="' + stationPage + '" />' +        
        '</form>');
    $('body').append(form);

    form.submit();
}

function isJson(str) {
    try {
        JSON.parse(str);
    } catch (e) {
        return false;
    }
    return true;
}

function getUserBadge() {
    $.ajax({
//        url: "getBadge",
        url: $('#get-badge-url').val(),
        success: function (data) {
            if (data != null && data != "redirect") {
                if (!isNaN(data)) {
                    var badge = parseInt(data);
                    if (badge == 0) {
                        $(".user-badge").addClass("hide");
                        $(".user-badge").text("");
                    } else {
                        $(".user-badge").removeClass("hide");
                        $(".user-badge").text(badge);
                    }
                }
            } else if (data == "redirect") {
                location.href = $('#wxauth-url').val();
            }
        },
        complete: function(){
            setTimeout(getUserBadge, 1000 * 5);
        }, 
        error: function () {
            clearTimeout(getUserBadge);
        }
        
    });
}

function getHistoryLength() {
    if (!isNaN($('#historyLength').val()))
        return parseInt($('#historyLength').val()) + 1;
    else
        return "";
}

$(function() {
    getUserBadge();
});

//function shareFriend() {
//    WeixinJSBridge.invoke('sendAppMessage',{
//                            "appid": "wx043d494f2a4940b5",
//                            "img_url": shareImgUrl,
////                            "img_width": "640",
////                            "img_height": "640",
//                            "link": rootUrl + "/" + location.pathname,
//                            "desc": shareDesc,
//                            "title": shareTitle
//                            }, function(res) {
//                            _report('send_msg', res.err_msg);
//                            })
//}
//function shareTimeline() {
//    WeixinJSBridge.invoke('shareTimeline',{
//                            "img_url": shareImgUrl,
////                            "img_width": "640",
////                            "img_height": "640",
//                            "link": rootUrl + "/" + location.pathname,
//                            "desc": shareDesc,
//                            "title": shareTitle
//                            }, function(res) {
//                            _report('timeline', res.err_msg);
//                            });
//}

//document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
//    // 发送给好友
//    WeixinJSBridge.on('menu:share:appmessage', function(argv){
//        shareFriend();
//    });
//    // 分享到朋友圈
//    WeixinJSBridge.on('menu:share:timeline', function(argv){
//        shareTimeline();
//    });
////        // 分享到微博
////        WeixinJSBridge.on('menu:share:weibo', function(argv){
////            shareWeibo();
////            });
//}, false);

//function onBridgeReady() {
//    WeixinJSBridge.on('menu:share:appmessage', function(argv){
//        shareFriend();
//    });
//    
//    WeixinJSBridge.on('menu:share:timeline', function(argv){
//        shareTimeline();
//    });    
//    
//}
//
//if (typeof WeixinJSBridge == "undefined"){
//    if( document.addEventListener ) {
//        document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
//    } else if (document.attachEvent) {
//        document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
//        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
//    }
//} else {
//    onBridgeReady();
//}



