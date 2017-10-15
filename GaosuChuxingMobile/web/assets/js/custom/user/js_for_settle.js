/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function onNavigation() {
    $('#navi-dlg').modal();
}

function onGoOrder() {
    var form = $('<form action="orders" method="post">' +
                 '</form>');
    $('body').append(form);
}

function onShare() {
    $.ajax({
        url: "couponShare",
        cache: false,
        success: function(_shareUrl) {
            if (_shareUrl != null) {
                location.href = _shareUrl;
            }
        }
    })
}