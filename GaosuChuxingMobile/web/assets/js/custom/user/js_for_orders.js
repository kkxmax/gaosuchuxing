/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function() {
    $('body').css("background-color", "#f5f5f5");
   
    onLoadOrderList("");
    
    $(".tab-order").click(function(){
        var id = $(this).attr("id");
        id = id.replace('btn', '');
        $("#tab-info").val(id);

        $('.tab-content > div').each(function() {
            var tab = $(this);
            tab.removeClass("active");
        });

        $("#tab" + id).addClass("active");
    });
});

function onLoadOrderList(_tabInfo) {
    $(".tab-content").empty();
    $.ajax({
        url: "getOrderList",
        data: {tabInfo: _tabInfo},
        cache: false,
        dataType: "html",
        success: function(result) {
            $(".tab-content").html(result);            
        }
    });
}

function onGoOrder(_orderId) {
    var form = $('<form action="order" method="post">' +
                '<input type="hidden" name="orderId" value="' + _orderId + '" />' +
                '</form>');
    $('body').append(form);

    form.submit();
}