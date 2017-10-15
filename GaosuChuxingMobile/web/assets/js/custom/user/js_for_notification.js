/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function() {
//    $('body').css("background-color", "#fff");
})

function onGoActivityNotice(_notificationId, _activityNoticeId) {
    setNotificationStatus(_notificationId);
    
    var form = $('<form action="activityNotice" method="post">' +
                '<input type="hidden" name="activityNoticeId" value="' + _activityNoticeId + '" />' +
                '</form>');
    $('body').append(form);

    form.submit();
}

function onGotoOrder(_notificationId, _orderId) {
    setNotificationStatus(_notificationId);
    onGoOrder(_orderId);
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

