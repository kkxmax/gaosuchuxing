/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//var appId, timestamp, nonceStr, signature;

var imgUrl = location.protocol+'//'+location.hostname+(location.port ? ':'+location.port: '') + $('#adv-icon-url').val();
//var shared = false;
var shareUrl = $("#shareUrl").val();

var len = $('#historyLength').val();

$(function() {
    wxConfigInfo();

//    if (window.history && window.history.pushState) {

//        window.history.pushState('forward', null, '#forward');

//        $(window).on('popstate', function() {
////            if ($('#submitPage').val() == cartPage)
////                location.href = 'cart?stationId=' + $('#stationId').val() + "&startLat=" + $('#startLat').val() + "&startLng=" + $('#startLng').val() + "&endLat=" + $('#endLat').val() + "&endLng=" + $('#endLng').val() + "&page=" + $('#page').val();
////            else if ($('#submitPage').val() == shopPage)
////                location.href = 'shop?shopId=' + $('#backShopId').val() + "&startLat=" + $('#startLat').val() + "&startLng=" + $('#startLng').val() + "&endLat=" + $('#endLat').val() + "&endLng=" + $('#endLng').val() + "&page=" + $('#page').val()  + "&stationPage=" + $('#stationPage').val() + "&stationId=" + $('#stationId').val();
////            else
////                location.href = 'searchPharmacy?shopId=' + $('#backShopId').val() + "&searchKey=" + $('#backSearchKey').val() + "&startLat=" + $('#startLat').val() + "&startLng=" + $('#startLng').val() + "&endLat=" + $('#endLat').val() + "&endLng=" + $('#endLng').val() + "&page=" + $('#page').val() + "&stationPage=" + $('#stationPage').val() + "&stationId=" + $('#stationId').val();
////              alert('back');  
////            history.go(-(history.length - 1));
//            if (!isNaN(len)) {
//                len = parseInt(len);
//                history.go(-len);
//            } else {
//                history.go(-1);
//            }
//        });
//    }

    
    
});

function onNavigation() {
    $('#navi-dlg').modal();
}

function onGoOrder() {
    var form = $('<form action="orders" method="get">' +
                 '</form>');
    $('body').append(form);
}

function onShare() {
    $('#share-modal').modal();
    
//    if ($('#orderId').val() == '') {
//        showMessage("您不能创建优惠券");
//        return;
//    }
//        
//    $.ajax({
//        url: "couponShare",
//        data: {orderId: $('#orderId').val()},
//        cache: false,
//        success: function(data) {
//            if (data != null) {
//                if (data == 'fail') {
//                    $('#share-modal').modal();
////                    alert('你已经创建了一个优惠券');
//                    return;
//                }
//                try {
//                    var json = eval(data)
//
//                    wx.onMenuShareTimeline({
//                        title: "领红包，享美食 高点高速出行", // 分享标题
//                        link: json.shareUrl, // 分享链接
//                        imgUrl: imgUrl, // 分享图标
//                        trigger: function (res) {
//                            // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
//    //                            alert('用户点击发送给朋友');
//                        },
//                        success: function (res) {
//    //                            alert('已分享');
//                        },
//                        cancel: function (res) {
//    //                            alert('已取消');
//                        },
//                        fail: function (res) {
//                            alert(JSON.stringify(res));
//                        }
//                    });
//
//                    wx.onMenuShareAppMessage({
//                        title: "领红包，享美食 高点高速出行", // 分享标题
//                        desc: "赶快来抢红包吧！", // 分享描述
//                        link: json.shareUrl, // 分享链接
//                        imgUrl: imgUrl, // 分享图标
//                        type: "link", // 分享类型,music、video或link，不填默认为link
//                        dataUrl: "", // 如果type是music或video，则要提供数据链接，默认为空
//                        success: function () { 
//                            // 用户确认分享后执行的回调函数
//                        },
//                        cancel: function () { 
//                            // 用户取消分享后执行的回调函数
//                        }
//                    });  
//                    $('#share-modal').modal();
////                    alert("创建共享优惠券，所以分享您的优惠券");
//                    
//                } catch (e) {
//                    alert("您不能创建优惠券");
//                }
//            }
//        }
//    });
}

function wxConfigInfo() {
    var pathUrl = location.href;
    if (pathUrl.indexOf('#')!== -1)
        pathUrl = location.href.split('#')[0];
    
    $.ajax({
        url: "wxConfigInfo",        
        cache: false,
//        data: {url: $('#url').val()},
        data: {url: pathUrl},
        success: function(_data) {
            try {
                var json = eval(_data);
                var appId = json.appId;
                var timestamp = json.timestamp;
                var nonceStr = json.nonceStr;
                var signature = json.signature;
				
//                alert(JSON.stringify(json));
                
                wx.config({
                    debug: false,
                    appId: appId,
                    timestamp: timestamp,
                    nonceStr: nonceStr,
                    signature: signature,
                    jsApiList: ['checkJsApi', 'onMenuShareTimeline', 'onMenuShareAppMessage']
                });
                
                wx.ready(function () {
                    wx.checkJsApi({
                        jsApiList: [
                            'getNetworkType',
                            'previewImage'
                        ],
                        success: function (res) {
//                            alert(JSON.stringify(res));
                        }
                    });
                    
                    wx.onMenuShareTimeline({
                        title: "【美味高速】领红包 享美食 高速路上的外卖", // 分享标题
                        link: shareUrl, // 分享链接
                        imgUrl: imgUrl, // 分享图标
                        trigger: function (res) {
                            // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
    //                            alert('用户点击发送给朋友');
                        },
                        success: function (res) {
    //                            alert('已分享');
//                            showInformation("已分享");
                        },
                        cancel: function (res) {
    //                            alert('已取消');
//                            showInformation("已取消");
                        },
                        fail: function (res) {
                            alert(JSON.stringify(res));
                        }
                    });

                    wx.onMenuShareAppMessage({
                        title: "【美味高速】领红包 享美食 高速路上的外卖", // 分享标题
                        desc: "赶快来抢红包吧！", // 分享描述
                        link: shareUrl, // 分享链接
                        imgUrl: imgUrl, // 分享图标
                        type: "link", // 分享类型,music、video或link，不填默认为link
                        dataUrl: "", // 如果type是music或video，则要提供数据链接，默认为空
                        success: function () { 
                            // 用户确认分享后执行的回调函数
//                            showInformation("已分享");
                        },
                        cancel: function () { 
                            // 用户取消分享后执行的回调函数
//                            showInformation("已取消");
                        }
                    });
                    
                });    
                
                wx.error(function(res){
                    alert(JSON.stringify(res));
                    console.log(res);
                });
                
            } catch (e) {
                
            }
        }
    });
}

function onGoGaodeMap(myLng, myLat, stationLng, stationLat, stationName) {
//    $.ajax({
//        url: "goGaodeMap",
//        success: function() {
    location.href = "http://uri.amap.com/navigation?from=" + myLng + "," + myLat + ",我的位置&to=" + stationLng + "," + stationLat + "," + stationName + "&mode=car&policy=1&coordinate=gaode&callnative=1";
//        }
//    });
}

function onGoBaiduMap(myLat,myLng,stationLat,stationLng,stationName,region) {
//    $.ajax({
//        url: "goBaiduMap",
//        success: function() {
    location.href = "http://api.map.baidu.com/direction?origin=latlng:" + myLat + "," + myLng + "|name:我的位置&destination=latlng:" + stationLat + "," + stationLng + "|name:" + stationName + "&region=" + region + "&mode=driving&output=html&coord_type=gcj02&src=高速出行";
//        }
//    });
}



