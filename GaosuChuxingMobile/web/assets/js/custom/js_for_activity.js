/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
})

function onTakeAdv(_id, _shareNum) {
    var url = $('#takeadv-url').val();
    $.ajax({
       url: url,
       cache: false,
       data: {id: _id, shareNum: _shareNum},
       success: function(data) {
           if (data != null) {
               if (data == 'error') {
                   $('#success-msg').addClass('hide');
                   $('#success-msg').hide();
                   $('#fail-msg').removeClass('hide');
                   $('#fail-msg').show();
                   $('#alert-modal').modal();
               } else {
                   try {
                       var json = eval(data);
                       $('#success-msg').removeClass('hide');
                        $('#success-msg').show();
                        $('#fail-msg').addClass('hide');
                        $('#fail-msg').hide();
                        $('#alert-modal').modal();
                   } catch (e) {
//                       alert(data);
                        showMessage(data);
                   }
               }
           }
       }
    });
}