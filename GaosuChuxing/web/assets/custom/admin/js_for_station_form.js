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

validateSelectOptions = {
    errorElement: 'span', //default input error message container
    errorClass: 'help-block help-block-error', // default input error message class
    focusInvalid: false, // do not focus the last invalid input
    ignore: "",  // validate all fields including form hidden input
    messages: {required: "不允许为空", min: "不允许为空"},
    rules: {
        required: true,
        min: 1,
        number: true
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

var handleValidateStationForm = function() {
    var form1 = $('#stationForm');
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
            status: { required: "不允许为空" },
            deliverId: { required: "不允许为空", min: "不允许为空" }
        },
        rules: {
            name: {
                minlength: 1,
                required: true 
            },             
            status: {
                required: true
            },
            deliverId: {
                required: true,
                min: 1,
                number: true
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

            if ($('#districtId').val() == null || $('#districtId').val() == '') {
                showMessage('请选择所属城市');
            } else if ($('#img_file').val() == '' && $('#id').val() == '0') {
                showMessage('请选择服务区图片');
            } else {
                $.ajax({
                    url: $('#validate-station-name-url').val(),
                    type: 'POST',
                    data: {stationId: $('#id').val(), stataionName: $('#name').val()},
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
                            showMessage('服务区名称重复');
                        }
                    },
                    error: function() {
                        showMessage('服务区名称重复');
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

var validationStatus = function() {
    var element = $('#status');    
    element.validate(validateSelectOptions);
}

var validationDeliverId = function() {
    var element = $('#deliverId');    
    element.validate(validateSelectOptions);
}

$(function() {
    handleValidateStationForm();
    
    validationName();
    validationStatus();
    validationDeliverId();
    
    $('.validation-control').on('blur', function() {
        $(this).valid();
    });
    
    $('#districtParentId').live('change', function() {
        $.ajax({
            cache: false,
            url: $('#get-district-list-url').val(),
            data: {parentId: $(this).val()},
            dataType: "html",
            success: function (_html) {
                $('.district-opt').empty().append(_html);
            }
        });
    });
    
//    $('#sel-district').live('change', function() {
//        $('#districtId').val($(this).val());
//    });
});

