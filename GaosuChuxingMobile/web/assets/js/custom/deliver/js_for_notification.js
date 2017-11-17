/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function() {
    $('body').css("background-color", "#fff");
    
//    if (window.history && window.history.pushState) {
//
//        window.history.pushState('forward', null, '#forward');
//
//        $(window).on('popstate', function() {
////            alert('Back button was pressed.');
//            location.href = 'notification';
//        });
//
//    }

    onLoadNotificationList();
});

window.addEventListener('pagehide', function(e) {
    var $body = $(document.body);
    $body.children().remove();

    // wait for this callback to finish executing and then...
    setTimeout(function() {
        $body.append("<script type='text/javascript'>window.location.reload();<\/script>");
    });
});

function onLoadNotificationList() {
    $('#content').empty();
    $.ajax({
        url: "getNotificationList",
        cache: false,
        dataType: "html",
        success: function(result) {
            $('#content').html(result);
        }
    });
}

function onGoActivityNotice(_notificationId, _activityNoticeId) {
    setNotificationStatus(_notificationId);
    
    var form = $('<form action="activityNotice" method="get">' +
                '<input type="hidden" name="activityNoticeId" value="' + _activityNoticeId + '" />' +
                '</form>');
    $('body').append(form);

    form.submit();
}

function onGoActivity(_notificationId, _activityId) {
    setNotificationStatus(_notificationId);
    
    var form = $('<form action="activity" method="get">' +
                '<input type="hidden" name="activityId" value="' + _activityId + '" />' +
                '</form>');
    $('body').append(form);

    form.submit();
}

function onGotoOrder(_notificationId, _orderId) {
    setNotificationStatus(_notificationId);
    onGoOrder(_orderId);
}

function onGoOrder(_orderId) {
    var form = $('<form action="order" method="get">' +
                '<input type="hidden" name="orderId" value="' + _orderId + '" />' +
                '</form>');
    $('body').append(form);

    form.submit();
}

function setNotificationStatus(_notificationId) {
    $.ajax({
        url: "setNotificationStatus",
        data: {notificationId: _notificationId},
        cache: false,
        success: function(result) {
        }
    });
}

