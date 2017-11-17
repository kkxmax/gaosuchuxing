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

var handleValidateShopkindForm = function() {
    var form1 = $('#shopkind-form');

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
            shopKindName: { required: "不允许为空" }
        },
        rules: {
            shopKindName: {
                minlength: 1,
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
            var _url;
            if ($('#shopkind-id').val() == '')
                _url = "addNewShopKind";
            else
                _url = "updateShopKind";
            
            $.ajax({
                url: _url,
                type: 'POST',
                data: {shopKindName: $('#shopKindName').val(), shopKindId: $('#shopkind-id').val()},
                success: function(_result) { 
                    if (_result != null && _result == 'success') {                        
                        $('#shopkind-form-dlg').modal("hide");
                        $('#shopKindName').val('');
                        $('#shopkind-id').val('');
                        $('#shopkind-list').DataTable().ajax.reload();
                    } else {
                        alert('门店类型重复');
                    }
                },
                error: function() {
                    alert('门店类型重复');
                }
            });
            
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

var validationShopKindName = function() {
    var element = $('#shopKindName');    
    element.validate(validateOptions);
}

var shopkindList = function () {

    var table = $('#shopkind-list');

    // begin first table
    table.dataTable({
        "searching": false,
        "lengthChange": false,
        "serverSide": true,
        "ajax": {
            url: 'getShopKindList',
            type: 'POST'
        },
        "lengthMenu": [
            [5, 15, 20, -1],
            [5, 15, 20, "All"] // change per page values here
        ],
        // set the initial value
        "pageLength": 20,            
        "pagingType": "bootstrap_full_number",
        "language": {
            "search": "搜查: ",
            "lengthMenu": "  _MENU_ 记录",
            "emptyTable": "没有可显示的内容",
            "zeroRecords": "没有可显示的内容",
            "info": "显示 _START_ 至 _END_ 的 _TOTAL_ 项",
            "infoEmpty": "显示 0 至 0 的 0 项",
            "processing":     "处理中...",
            "loadingRecords": "加载...",
            "paginate": {
                "previous":"上一页",
                "next": "下一个",
                "last": "持续",
                "first": "第一"
            }
        },
        "autoWidth": false,
        "columnDefs": [
            {
                "orderable": false,
                "targets": [0]
            },
            {
                "orderable": false,
                "targets": [2]
            }
        ]   
    });

    var tableWrapper = jQuery('#shopkind-list_wrapper');

    table.find('.group-checkable').change(function () {
        var set = jQuery(this).attr("data-set");
        var checked = jQuery(this).is(":checked");
        jQuery(set).each(function () {
            if (checked) {
                $(this).attr("checked", true);
                $(this).parents('tr').addClass("active");
            } else {
                $(this).attr("checked", false);
                $(this).parents('tr').removeClass("active");
            }
        });
        jQuery.uniform.update(set);
    });

    table.on('change', 'tbody tr .checkboxes', function () {
        $(this).parents('tr').toggleClass("active");
    });

    tableWrapper.find('.dataTables_length select').addClass("form-control input-xsmall input-inline"); // modify table per page dropdown
}

$(function() {
    handleValidateShopkindForm();
    
    validationShopKindName();
    
    $('#shopKindName').on('blur', function() {
        $(this).valid();
    });
    
    shopkindList();
});

function onDeleteShopKind(_shopKindId) {
    confirmYesNoMessage('请确认是否删除', function(_msg) {
        if (_msg == true) {
            $.ajax({
                cache: false,
                url: 'deleteShopKind',
                type: 'POST',
                data: {shopKindId: _shopKindId},
                success: function (_result) {
                    if (_result != null && _result == 'success') {
                        $('#shopkind-list').DataTable().ajax.reload();
                    } else {
                        showMessage('该门店类型关联了门店，不能删除。');
                    }
                }
            });
        }
    });
}

function onAddNewShopKind() {
    var validator = $("#shopkind-form" ).validate();
    validator.resetForm();
    
    $('#shopkind-form input').each(function() {
        $(this).closest('.form-group').removeClass('has-error');
    });
    
    $('#shopKindName').val('');
    $('#shopkind-id').val('');
    $('#shopkind-form-title').text('新增门店类型');
    $('#shopkind-form-dlg').modal({show: true});
}

function onEditShopKind(_shopKindId) {
    var validator = $( "#shopkind-form" ).validate();
    validator.resetForm();
    
    $('#shopkind-form input').each(function() {
        $(this).closest('.form-group').removeClass('has-error');
    });
    
    $('#shopKindName').val('');
    $('#shopkind-id').val(_shopKindId);
    $('#shopkind-form-title').text('编辑门店类型');
    
    $.ajax({
        url: 'getShopKind',
        cache: false,
        data: {shopKindId: _shopKindId},
        success: function (_json) {
            if (_json != null) {
                 var shopKind = eval(_json);
                 $('#shopKindName').val(shopKind.name);
                 $('#shopkind-form-dlg').modal({show: true});
            }
        }
    })
}
