/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function(){
//    if (window.history && window.history.pushState) {
//
//        window.history.pushState('forward', null, '#forward');
//
//        $(window).on('popstate', function() {
////            alert('Back button was pressed.');
//            location.href = 'profile';
//        });
//
//    }
})

function onSubmit() {
    if ($('#currentPwd').val() == '') {
        showMessage("输入旧密码");        
    } else if ($('#newPwd').val() == '') {
        showMessage("输入新密码");   
    } else if ($('#confirmPwd').val() == '') {
        showMessage("输入确认密码");   
    } else if ($('#newPwd').val().length < 6) {
        showMessage("密码由6-20数字和字母组成");   
    } else if ($('#newPwd').val() != $('#confirmPwd').val()) {
        showMessage("两次密码不一致");   
    } else {
        $.ajax({
            url: "setPassword",
            type: "POST",
            data: {currentPwd: getDigest($('#currentPwd').val()), newPwd: getDigest($('#newPwd').val())},
            success: function(result) {
                if (result != null && result == "success") {
                    $('#password-success-modal').modal({show: true});
                    $('#btn-ok').unbind('click').click(function() {
                        location.href = "logout";
                    });
                } else if (result != null) {
                    showMessage(result);
                } else {
                    showMessage("unknow error");
                }
            }
        });
    }
}
