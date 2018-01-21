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

$(function() {
    $(".mask_integer").inputmask({
        "mask": "9",
        "repeat": 10,
        "greedy": false
    }); // ~ mask "9" or mask "99" or ... mask "9999999999"
        
    $(".mask_decimal").inputmask('decimal', {
        rightAlign: false
    }); //disables the right alignment of the decimal input
    
    checkFeedback();
});

function checkFeedback() {
     $.ajax({
        url: $('#check-feedback-url').val(),
        success: function (data) {
            if (data != null && data == "ok") {
                $('.badge').removeClass("hide");
                $('.badge').show();
            } else {
                $('.badge').addClass("hide");
                $('.badge').hide();
            }
        },
        complete: function(){
            setTimeout(checkFeedback, 1000 * 3);
        }, 
        error: function () {
            clearTimeout(checkFeedback);
        }
        
    });
}
