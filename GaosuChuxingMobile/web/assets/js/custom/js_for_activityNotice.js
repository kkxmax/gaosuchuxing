/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var baseUrl = location.protocol+'//'+location.hostname+(location.port ? ':'+location.port: '');
var img_url = baseUrl + $('#share-img-url').val();
//var shared = false;
var shareUrl = $("#shareUrl").val();
var title = $('#shareTitle').val();
var content = $('#shareContent').val();

$(function(){
//    onSetPage(page);
//    if (window.history && window.history.pushState) {
//
//        window.history.pushState('forward', null, '#forward');
//
//        $(window).on('popstate', function() {
////            alert('Back button was pressed.');
//            location.href = $('#notification-url').val();
//        });
//
//    }
    wxConfigInfo();
})

function wxConfigInfo() {
    var pathUrl = location.href;
//    var pathUrl = shareUrl;
    if (pathUrl.indexOf('#')!== -1)
        pathUrl = location.href.split('#')[0];
    
    $.ajax({
        url: $('#wxconfig-url').val(),        
//        cache: false,
//        data: {url: $('#url').val()},
        data: {url: pathUrl},
        success: function(_data) {
            try {
                var json = eval(_data);
                var appId = json.appId;
                var timestamp = json.timestamp;
                var nonceStr = json.nonceStr;
                var signature = json.signature;
		var linkUrl = json.url;
                
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
//                    wx.checkJsApi({
//                        jsApiList: [
//                            'getNetworkType',
//                            'previewImage'
//                        ],
//                        success: function (res) {
//                            alert(JSON.stringify(res));
//                        }
//                    });

                    wx.onMenuShareTimeline({
                        title: title, // 分享标题
                        link: linkUrl, // 分享链接
                        imgUrl: img_url, // 分享图标
                        trigger: function (res) {
                            // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
    //                            alert('用户点击发送给朋友');
                        },
                        success: function (res) {
    //                            alert('已分享');
                        },
                        cancel: function (res) {
    //                            alert('已取消');
                        },
                        fail: function (res) {
                            alert(JSON.stringify(res));
                        }
                    });

                    wx.onMenuShareAppMessage({
                        title: title, // 分享标题
                        desc: content, // 分享描述
                        link: linkUrl, // 分享链接
                        imgUrl: img_url, // 分享图标
                        type: "link", // 分享类型,music、video或link，不填默认为link
                        dataUrl: "", // 如果type是music或video，则要提供数据链接，默认为空
                        success: function () { 
                            // 用户确认分享后执行的回调函数
                        },
                        cancel: function () { 
                            // 用户取消分享后执行的回调函数
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
    })
}