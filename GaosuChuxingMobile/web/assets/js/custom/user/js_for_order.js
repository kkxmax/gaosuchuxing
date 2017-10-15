/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function() {
    onLoadOrderContent($('#orderId').val());
})

function onLoadOrderContent(_orderId) {
    $("#content").empty();
    $.ajax({
        url: "getOrderContent",
        data: {orderId: _orderId},
        cache: false,
        dataType: "html",
        success: function(result) {
            $("#content").html(result);            
        }
    });
}