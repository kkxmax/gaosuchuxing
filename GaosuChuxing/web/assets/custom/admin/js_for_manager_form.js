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

var validatePasswordOptions = {
    errorElement: 'span', //default input error message container
    errorClass: 'help-block help-block-error', // default input error message class
    focusInvalid: false, // do not focus the last invalid input
    ignore: "",  // validate all fields including form hidden input
    messages: {required: "不允许为空", minlength: "密码6-20字符"},
    rules: {
        minlength: 6,
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

var validateConfirmPasswordOptions = {
    errorElement: 'span', //default input error message container
    errorClass: 'help-block help-block-error', // default input error message class
    focusInvalid: false, // do not focus the last invalid input
    ignore: "",  // validate all fields including form hidden input
    messages: {required: "不允许为空", minlength: "密码6-20字符", equalTo: "密码与确认密码，两次输入的必须一致"},
    rules: {
        minlength: 6,
        required: true,
        equalTo: "#password"
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

var handleValidateManagerForm = function() {
    var form1 = $('#managerForm');
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
            managerId: { required: "不允许为空" },
            name: { required: "不允许为空" },
            password: { required: "不允许为空", minlength: "密码6-20字符"},
            confirmPassword: { required: "不允许为空", minlength: "密码6-20字符", equalTo: "密码与确认密码，两次输入的必须一致" },
            groupId: { required: "不允许为空" }
        },
        rules: {
            managerId: {
                minlength: 1,
                required: true
            },
            name: {
                minlength: 1,
                required: true 
            },
            password: {
                minlength: 6,
                required: true
            },
            confirmPassword: {
                minlength: 6,
                required: true,
                equalTo: '#password'
            },                    
            groupId: {
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
            
//            if ($('#password').val() != $('#confirmPassword').val()) {
//                showMessage('密码与确认密码，两次输入的必须一致');
//                return;
//            }
            if ($('#id').val() == '0') {
                $.ajax({
                    url: $('#validate-manager-id-url').val(),
                    type: 'POST',
                    data: {managerId: $('#managerId').val()},
                    success: function(_result) { 
                        if (_result != null && _result == 'success') {                        
//                            form.submit();

                            $.ajax({
                                url: $('#validate-manager-name-url').val(),
                                data: {managerId: $('#id').val(), managerName: $('#name').val()},
                                success: function(result) {
                                    if (result != null && result == 'success') {                        
                                        form.submit();
                                    } else {
                                        showMessage('姓名不能相同');
                                    }      
                                }
                            });
                        } else {
                            showMessage('账号重复');
                        }
                    },
                    error: function() {
                        showMessage('账号重复');
                    }
                });
            } else {
//                form.submit();
                $.ajax({
                    url: $('#validate-manager-name-url').val(),
                    data: {managerId: $('#id').val(), managerName: $('#name').val()},
                    success: function(result) {
                        if (result != null && result == 'success') {                        
                            form.submit();
                        } else {
                            showMessage('姓名不能相同');
                        }      
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
        form1.validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input 
    });
}

var validationManagerId = function() {
    var element = $('#managerId');    
    element.validate(validateOptions);
}

var validationName = function() {
    var element = $('#name');    
    element.validate(validateOptions);
}

var validationPassword = function() {
    var element = $('#password');    
    element.validate(validatePasswordOptions);
}

var validationConfirmPassword = function() {
    var element = $('#confirmPassword');    
    element.validate(validateConfirmPasswordOptions);
}

var validationGroupId = function() {
    var element = $('#groupId');    
    element.validate(validateOptions);
}

$(function() {
    handleValidateManagerForm();
    
    validationManagerId();
    validationName();
    validationPassword();
    validationConfirmPassword();
    validationGroupId();
    
    $('.validation-control').on('blur', function() {
        $(this).valid();
    });
});

