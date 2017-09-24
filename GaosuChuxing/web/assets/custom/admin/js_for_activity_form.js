/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var validateOptions = {
    errorElement: 'span', //default input error message container
    errorClass: 'help-block help-block-error', // default input error message class
    focusInvalid: false, // do not focus the last invalid input
    ignore: "",  // validate all fields including form hidden input
    messages: {required: "不允许为空"},
    rules: {
        minlength: 1,
        required: true
    },
    errorPlacement: function (error, element) { // render error placement for each input type
        if (element.parent(".input-group").size() > 0) {
            error.insertAfter(element.parent(".input-group"));
        } else if (element.attr("data-error-container")) { 
            error.appendTo(element.attr("data-error-container"));
        } else if (element.parents('.radio-list').size() > 0) { 
            error.appendTo(element.parents('.radio-list').attr("data-error-container"));
        } else if (element.parents('.radio-inline').size() > 0) { 
            error.appendTo(element.parents('.radio-inline').attr("data-error-container"));
        } else if (element.parents('.checkbox-list').size() > 0) {
            error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
        } else if (element.parents('.checkbox-inline').size() > 0) { 
            error.appendTo(element.parents('.checkbox-inline').attr("data-error-container"));
        } else {
            error.insertAfter(element); // for other inputs, just perform default behavior
        }
    },
    highlight: function (element) { // hightlight error inputs
        $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
    },
    unhighlight: function (element) { // revert the change done by hightlight
        $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
    },
    success: function (label) {
        label.closest('.form-group').removeClass('has-error'); // set success class to the control group
    } 
};

var handleValidateActivityForm = function() {
    var form1 = $('#activityForm');
    var error1 = $('.alert-danger', form1);
    var success1 = $('.alert-success', form1);

    //IMPORTANT: update CKEDITOR textarea with actual content before submit
    form1.on('submit', function() {
        for(var instanceName in CKEDITOR.instances) {
            CKEDITOR.instances[instanceName].updateElement();
        }
    })
    
    form1.validate({
        errorElement: 'span', //default input error message container
        errorClass: 'help-block help-block-error', // default input error message class
        focusInvalid: false, // do not focus the last invalid input
        ignore: "",  // validate all fields including form hidden input
        messages: {
            select_multi: {
                maxlength: jQuery.validator.format("Max {0} items allowed for selection"),
                minlength: jQuery.validator.format("At least {0} items must be selected")
            },            
            name: { required: "不允许为空" },
            startDateStr: { required: "不允许为空" },
            endDateStr: { required: "不允许为空" }
        },
        rules: {
            name: {
                minlength: 1,
                required: true 
            },             
            startDateStr: {
                required: true
            },
            endDateStr: {
                required: true
            }
        },

        errorPlacement: function (error, element) { // render error placement for each input type
            if (element.parent(".input-group").size() > 0) {
                error.insertAfter(element.parent(".input-group"));
            } else if (element.attr("data-error-container")) { 
                error.appendTo(element.attr("data-error-container"));
            } else if (element.parents('.radio-list').size() > 0) { 
                error.appendTo(element.parents('.radio-list').attr("data-error-container"));
            } else if (element.parents('.radio-inline').size() > 0) { 
                error.appendTo(element.parents('.radio-inline').attr("data-error-container"));
            } else if (element.parents('.checkbox-list').size() > 0) {
                error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
            } else if (element.parents('.checkbox-inline').size() > 0) { 
                error.appendTo(element.parents('.checkbox-inline').attr("data-error-container"));
            } else {
                error.insertAfter(element); // for other inputs, just perform default behavior
            }
        },

        invalidHandler: function (event, validator) { //display error alert on form submit              
            success1.hide();
            error1.show();
            Metronic.scrollTo(error1, -200);
        },

        highlight: function (element) { // hightlight error inputs
            $(element)
                .closest('.form-group').addClass('has-error'); // set error class to the control group
        },

        unhighlight: function (element) { // revert the change done by hightlight
            $(element)
                .closest('.form-group').removeClass('has-error'); // set error class to the control group
        },

        success: function (label) {
            label
                .closest('.form-group').removeClass('has-error'); // set success class to the control group
        },

        submitHandler: function (form) {
            success1.show();
            error1.hide();

            if (!$('#unlimitedType').is(':checked') && !$('#fullType').is(':checked')) {
                showMessage('请选择类型');
            } else if ($('#unlimitedType').is(':checked') && (isNaN($('#unlimitedVal').val()) || parseInt($('#unlimitedVal').val()) == 0)) {        
                showMessage('请添加不限消费优惠券');
            } else if ($('#fullType').is(':checked') && (isNaN($('#fullVal').val()) || parseInt($('#fullVal').val()) == 0)) {        
                showMessage('请添加满减优惠券');    
            } else if ($('#img_file').val() == '' && $('#id').val() == '0') {
                showMessage('请选择服务区图片');
            } else {
                $.ajax({
                    url: $('#validate-activity-name-url').val(),
                    type: 'POST',
                    data: {activityId: $('#id').val(), activityName: $('#name').val()},
                    success: function(_result) { 
                        if (_result != null && _result == 'success') {
                            if ($('#img_file').val() != '') {
                                var file = $('#img_file')[0].files[0];
                                var fileName = file.name;
                                var ext = fileName.split('.')[fileName.split('.').length - 1].toLowerCase();
                                if (file.size > 1024 * 1024 * 1024) {
                                    showMessage('图片不超过1M');
                                } else if (ext != 'jpg' && ext != 'jpeg' && ext != 'png') {
                                    showMessage('图片格式错误，要求是jpg、jpeg、png格式');
                                } else {
                                    form.submit();
                                }
                                
//                                var fd = new FormData();
//                                var file = $('#img_file')[0].files[0];
//                                fd.append('file', file);
//                                
//                                $.ajax({
//                                    url: $('#validate-station-file-url').val(),
//                                    type: "POST",
//                                    data: fd,
//                                    processData: false,
//                                    contentType: false,
//                                    enctype: 'multipart/form-data',
//                                    success: function(_response) {
//                                        // .. do something
//                                        if (_response == 'success')
//                                            form.submit();
//                                        else if (_response == 'invalidSize')
//                                            showMessage('图片不超过1M');
//                                        else
//                                            showMessage('图片格式错误，要求是jpg、jpeg、png格式');
//                                    },
//                                    error: function() {
//                                        showMessage('图片格式错误，要求是jpg、jpeg、png格式');
//                                    }
//                                });
                            } else {
                                form.submit();
                            }
                        } else {
                            showMessage('活动名称重复');
                        }
                    },
                    error: function() {
                        showMessage('活动名称重复');
                    }
                });
            }
            
        }


    });

//        $('.date-picker').datepicker({
//            rtl: Metronic.isRTL(),
//            autoclose: true
//        });

    $('.date-picker .form-control').change(function() {
        form1.validate().element($(this)); //revalidate the chosen dropdown value and show error or success 
        //message for the input 
    });
}

var validationName = function() {
    var element = $('#name');    
    element.validate(validateOptions);
}

var validationStartDateStr = function() {
    var element = $('#startDateStr');    
    element.validate(validateOptions);
}

var validationEndDateStr = function() {
    var element = $('#endDateStr');    
    element.validate(validateOptions);
}

$(function() {
    handleValidateActivityForm();
    
    validationName();
    validationStartDateStr();
    validationEndDateStr();
    
    $('.validation-control').on('blur', function() {
        $(this).valid();
    });
    
    $('#unlimitedType').on('ifChanged', function() {
        if ($(this).is(':checked')) {
            $('#unlimited-type-details').show();
        } else {
            $('#unlimited-type-details').hide();
        }    
    });
    
    $('#fullType').on('ifChanged', function() {
        if ($(this).is(':checked')) {
            $('#full-type-details').show();
        } else {
            $('#full-type-details').hide();
        }    
    });
    
    if (!$('#unlimitedType').is(':checked'))
        $('#unlimited-type-details').hide();
    
    if (!$('#fullType').is(':checked'))
        $('#full-type-details').hide();
    
    onLoadUnlimitedDetails();
    onLoadFullDetails();
});

function setCellRate(el, totalQty, qty) {
    var rate = 0;
    if (totalQty != 0)
        rate = (qty / totalQty) * 100;
        
    el.val(Math.round(rate));
    
//    var id = el.attr('id').replace("rate_", "");
//    
//    $.ajax({
//        cache: false,
//        url: $('#update-tmp-activity-detail-url').val(),
//        data: {id: id, rate: rate},
//        success: function(_result){}
//    });
}

function getTotalQty(e1, e2) {
    var totalQty = 0;
    e1.each(function() {
        var qty = 0;
        if (!isNaN($(this).find("td:eq(1) input[type='text']").val()) && $(this).find("td:eq(1) input[type='text']").val() != '')
            qty = parseInt($(this).find("td:eq(1) input[type='text']").val());
        
        totalQty += qty;
    });
    e2.each(function() {
        var qty = 0;
        if (!isNaN($(this).find("td:eq(2) input[type='text']").val()) && $(this).find("td:eq(2) input[type='text']").val() != '')
            qty = parseInt($(this).find("td:eq(2) input[type='text']").val());
        
        totalQty += qty;
    });
    
    return totalQty;
}

function getTotalAmount(e1, e2) {
    var totalAmount = 0;
    e1.each(function() {
        var amount = 0, qty = 0;
        if (!isNaN($(this).find("td:eq(0) input[type='text']").val()) && $(this).find("td:eq(0) input[type='text']").val() != '')
            amount = parseInt($(this).find("td:eq(0) input[type='text']").val());
        
        if (!isNaN($(this).find("td:eq(1) input[type='text']").val()) && $(this).find("td:eq(1) input[type='text']").val() != '')
            qty = parseInt($(this).find("td:eq(1) input[type='text']").val());
        
        totalAmount += qty*amount;
    });
    
    e2.each(function() {
        var amount = 0, qty = 0;
        if (!isNaN($(this).find("td:eq(1) input[type='text']").val()) && $(this).find("td:eq(1) input[type='text']").val() != '')
            amount = parseInt($(this).find("td:eq(1) input[type='text']").val());
        
        if (!isNaN($(this).find("td:eq(2) input[type='text']").val()) && $(this).find("td:eq(2) input[type='text']").val() != '')
            qty = parseInt($(this).find("td:eq(2) input[type='text']").val());
        
        totalAmount += qty*amount;
    });
    
    return totalAmount;
}

function calcUnLimitedRate() {
    var amount = 0;
    var qty = 0;
    var rate = 0;
    
    if (!isNaN($('#unlimitedCouponAmount').val()) && $('#unlimitedCouponAmount').val() != '')
        amount = parseFloat($('#unlimitedCouponAmount').val());
    
    if (!isNaN($('#unlimitedQty').val()) && $('#unlimitedQty').val() != '')
        qty = parseInt($('#unlimitedQty').val() == ''? '0': $('#unlimitedQty').val());
    
    var e1 = $('#unlimitedDetail-list tbody tr'), e2 = $('#fullDetail-list tbody tr');
    var totalQty = getTotalQty(e1, e2), totalAmount = getTotalAmount(e1, e2);
    
    totalQty = qty + totalQty;
    totalAmount = totalAmount + qty*amount;
    
    $('#activityAmount').val(totalAmount);
    $('#couponQty').val(totalQty);
    
    if (totalQty != 0)
        rate = (qty / totalQty) * 100;
       
//    rate = rate.toPrecision(4);
    
    $('#unlimitedRate').val(Math.round(rate));
    
    $('#unlimitedDetail-list tbody tr').each (function() {
        var _qty = 0;
        if (!isNaN($(this).find("td:eq(1) input[type='text']").val()) && $(this).find("td:eq(1) input[type='text']").val() != '')
            _qty = parseInt($(this).find("td:eq(1) input[type='text']").val());
        
        var el = $(this).find("td:eq(2) input[type='text']");
        
        setCellRate(el, totalQty, _qty);
    });
}

function calcFullRate() {
    var qty = 0;
    var rate = 0;
    var amount = 0;
                
    if (!isNaN($('#fullCouponAmount').val()) && $('#fullCouponAmount').val() != '')
        amount = parseFloat($('#fullCouponAmount').val());
    
    if (!isNaN($('#fullQty').val()) && $('#fullQty').val() != '')
        qty = parseInt($('#fullQty').val());
    
    var e1 = $('#unlimitedDetail-list tbody tr'), e2 = $('#fullDetail-list tbody tr');
    var totalQty = getTotalQty(e1, e2), totalAmount = getTotalAmount(e1, e2);
    
    totalQty = qty + totalQty;
    totalAmount = totalAmount + qty*amount;
    
    $('#activityAmount').val(totalAmount);
    $('#couponQty').val(totalQty);
    
    if (totalQty != 0)
        rate = (qty / totalQty) * 100;
    
    $('#fullRate').val(Math.round(rate));
    
    $('#fullDetail-list tbody tr').each (function() {
        var _qty = 0;
        if (!isNaN($(this).find("td:eq(1) input[type='text']").val()) && $(this).find("td:eq(1) input[type='text']").val() != '')
            _qty = parseInt($(this).find("td:eq(1) input[type='text']").val());
        
        var el = $(this).find("td:eq(3) input[type='text']");
        
        setCellRate(el, totalQty, _qty);
    });
}

function onLoadUnlimitedDetails() {
    var _url = $('#get-tmp-activity-detail-url').val() + "/unlimited?tmpUId=" + $('#tmpUId').val();
    $('#unlimited-type-details').empty();
    $.ajax({
        url: _url,
        cache: false,
        dataType: "html",
        success: function(result) {
            $('#unlimited-type-details').html(result);
            
            $('.unlimitedCalc').change(function() {
                calcUnLimitedRate();
            });
        }
    });
}

function onLoadFullDetails() {
    var _url = $('#get-tmp-activity-detail-url').val() + "/full?tmpUId=" + $('#tmpUId').val();
    $('#full-type-details').empty();
    $.ajax({
        url: _url,
        cache: false,
        dataType: "html",
        success: function(result) {
            $('#full-type-details').html(result);
            
            $('.fullCalc').change(function() {
                calcFullRate();
            });
        }
    });
}

function addDetail(_type) {
    if (_type == 'full') {
        if ($('#fullFullAmount').val() == '' || isNaN($('#fullFullAmount').val()) || parseFloat($('#fullFullAmount').val()) <= 0) {
            showMessage("输入订单金额满减");
            return;
        }   
        
        if ($('#fullCouponAmount').val() == '' || isNaN($('#fullCouponAmount').val()) || parseFloat($('#fullCouponAmount').val()) <= 0) {
            showMessage("输入优惠券金额");
            return;
        }

        if ($('#fullQty').val() == '' || isNaN($('#fullQty').val()) || parseInt($('#fullQty').val()) <= 0) {
            showMessage("输入数量");
            return;
        }

        $.ajax({
            cashe: false,
            url: $('#add-tmp-activity-detail-url').val(),
            data: {fullAmount: $('#fullFullAmount').val(), couponAmount: $('#fullCouponAmount').val(), qty: $('#fullQty').val(), type: _type, rate: $('#fullRate').val(), tmpUId: $('#tmpUId').val()},
            success: function(_result) {
                if (_result == 'success') {
                    onLoadFullDetails();
                    onLoadUnlimitedDetails();
                    
                    $.ajax({
                        cache: false,
                        url: $('#get-activity-amount-url').val(),
                        data: {tmpUId: $('#tmpUId').val()},
                        success: function(_amount){
                            $('#activityAmount').val(_amount)
                        }
                    });
                    
                    $.ajax({
                        cache: false,
                        url: $('#get-coupon-qty-url').val(),
                        data: {tmpUId: $('#tmpUId').val()},
                        success: function(_qty){
                            $('#couponQty').val(_qty)
                        }
                    });
                }
            }
        });
    } else {
        if ($('#unlimitedCouponAmount').val() == '' || isNaN($('#unlimitedCouponAmount').val()) || parseFloat($('#unlimitedCouponAmount').val()) <= 0) {
            showMessage("输入优惠券金额");
            return;
        }

        if ($('#unlimitedQty').val() == '' || isNaN($('#unlimitedQty').val()) || parseInt($('#unlimitedQty').val()) <= 0) {
            showMessage("输入数量");
            return;
        }        

        $.ajax({
            cashe: false,
            url: $('#add-tmp-activity-detail-url').val(),
            data: {couponAmount: $('#unlimitedCouponAmount').val(), qty: $('#unlimitedQty').val(), type: _type, rate: $('#unlimitedRate').val(), tmpUId: $('#tmpUId').val()},
            success: function(_result) {
                if (_result == 'success') {
                    onLoadUnlimitedDetails();
                    onLoadFullDetails();
                    
                    $.ajax({
                        cache: false,
                        url: $('#get-activity-amount-url').val(),
                        data: {tmpUId: $('#tmpUId').val()},
                        success: function(_amount){
                            $('#activityAmount').val(_amount)
                        }
                    });
                    
                    $.ajax({
                        cache: false,
                        url: $('#get-coupon-qty-url').val(),
                        data: {tmpUId: $('#tmpUId').val()},
                        success: function(_qty){
                            $('#couponQty').val(_qty)
                        }
                    });
                }
            }
        });
    }
    
    
}

function deleteDetail(_tmpId, _type) {
    confirmYesNoMessage("请确认是否删除", function(_msg){
       if (_msg) {
           $.ajax({
                cashe: false,
                url: $('#delete-tmp-activity-detail-url').val(),
                data: {id: _tmpId, tmpUId: $('#tmpUId').val()},
                success: function(_result) {
                    if (_result == 'success') {
//                        if (_type == 'full')
                            onLoadFullDetails();
//                        else
                            onLoadUnlimitedDetails();
                        
                        $.ajax({
                        cache: false,
                            url: $('#get-activity-amount-url').val(),
                            data: {tmpUId: $('#tmpUId').val()},
                            success: function(_amount){
                                $('#activityAmount').val(_amount)
                            }
                        });

                        $.ajax({
                            cache: false,
                            url: $('#get-coupon-qty-url').val(),
                            data: {tmpUId: $('#tmpUId').val()},
                            success: function(_qty){
                                $('#couponQty').val(_qty)
                            }
                        });
                    }
                }
            });
       } 
    });
}