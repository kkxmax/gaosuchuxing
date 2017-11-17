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

function getUserBadge() {
    $.ajax({
        url: "getBadge",
        success: function (data) {
            if (data != null && data != "redirect" && data != "forceLogout") {
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
                location.href = $('#logout-url').val();
            } else if (data == "forceLogout") {
                $('#forceLogout-modal').modal();
                $('#btnForceLogout').unbind('click').click(function() {
                    location.href = $('#logout-url').val();
                });
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

$(function() {
    getUserBadge(); 
    
    
});