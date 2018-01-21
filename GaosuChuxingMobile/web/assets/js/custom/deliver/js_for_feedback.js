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

    $('#txt_opinion').on("change input paste keyup", function() {
        doingInputText($(this).val());
    });
})

function doingInputText(str) {
    var cnt = str.trim().length;
    $('#textarea_type_cnt').html(cnt + "/100");
}

function onSubmit() {
    if ($('#txt_opinion').val() == '') {
        showMessage("输入意见反馈");        
    } else if ($('#txt_opinion').val().length < 10) {
        showMessage("内容输入限于10-100字符以内");      
    } else {
        $.ajax({
            url: "addFeedback",
            type: "POST",
            data: {feedback: $('#txt_opinion').val()},
            success: function(result) {                
                showInformation("意见反馈提交成功");
                $('#txt_opinion').val("");
                $('#textarea_type_cnt').html("0/100");
            }
        });
    }
}
