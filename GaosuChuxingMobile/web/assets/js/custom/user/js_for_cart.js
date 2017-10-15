/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function onGoOrderSubmit(_orderId) {
//    alert('submit');
    var form = $('<form action="orderSubmit" method="post">' +
        '<input type="hidden" name="orderId" value="' + _orderId + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
    
}
