/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var _noChange = false;

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

var handleValidateGroupForm = function() {
    var form1 = $('#new-group-form');

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
            groupName: { required: "不允许为空" }
        },
        rules: {
            groupName: {
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
            $.ajax({
                url: "addNewGroup",
                type: 'POST',
                data: {groupName: $('#groupName').val()},
                success: function(_result) { 
                    if (_result != null && _result == 'success') {                        
                        $('#add-group-dlg').modal("hide");
                        $('#groupName').val('');
                        $('#group-list').DataTable().ajax.reload();
                    } else {
//                        showMessage('角色名称重复');
                        alert('角色名称重复');
                    }
                },
                error: function() {
                    alert('角色名称重复');
//                    showMessage('角色名称重复');
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

var validationGroupName = function() {
    var element = $('#groupName');    
    element.validate(validateOptions);
}

var groupList = function () {

    var table = $('#group-list');

    // begin first table
    table.dataTable({
        "searching": false,
        "lengthChange": false,
        "ajax": {
            url: 'getGroupList',
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
            },
            {
                "orderable": false,
                "targets": [3]
            }
        ]   
    });

    var tableWrapper = jQuery('#group-list_wrapper');

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
    handleValidateGroupForm();
    
    validationGroupName();
    
    $('#groupName').on('blur', function() {
        $(this).valid();
    });
    
    groupList();
        
    $('#permission_10').on('ifChanged', function() {    
        if (_noChange)
            return;
        
        if($(this).is(':checked')) {
            $('#permission_11').iCheck('check');
            $('#permission_12').iCheck('check');
            $('#permission_13').iCheck('check');
        } else {
            $('#permission_11').iCheck('uncheck');
            $('#permission_12').iCheck('uncheck');
            $('#permission_13').iCheck('uncheck');
        }
    });
    
    $('.sub-permissions').on('ifChanged', function() {
        if ($(this).is(':checked')) {
            _noChange = true;
            $('#permission_10').iCheck('check');
            _noChange = false;
        } else {
            if (!$('#permission_11').is(':checked') && !$('#permission_12').is(':checked') && !$('#permission_13').is(':checked')) {
                _noChange = true;
                $('#permission_10').iCheck('uncheck');
                _noChange = false;
            }    
        }
    });
});

function onSetRole(_groupId) {
    $.ajax({
        cache: false,
        url: 'getRole',
        data: {groupId: _groupId},
        success: function(_result) {
            if (_result != null) {
                $('#role-permission-dlg').modal({show: true});
                
                for (var i = 0; i < permissionOpt.length; i++) {
                    $('#permission_' + permissionOpt[i]).iCheck('uncheck');
                }
                
                var roles = eval(_result);
                for (var i = 0; i < roles.roles.length; i++) {
                    var role = roles.roles[i];
//                    $('#permission_' + role.permissionId).prop('checked', role.status);
                    if (role.status == true)
                        $('#permission_' + role.permissionId).iCheck('check');
                    else
                        $('#permission_' + role.permissionId).iCheck('uncheck');
                }
                
                $('#role-permission-btn-submit').unbind('click').click(function() {
                    var params = {};
                    for (var i = 0; i < permissionOpt.length; i++) {
                        var columnName = "permission_" + permissionOpt[i];                        
                        params[columnName] = $('#' + columnName).is(':checked');
                    }
                    
//                    console.log(params);
                    
                    $.ajax({
                        cache: false,
                        url: 'setRole?groupId=' + _groupId,
                        data: params,
                        success: function(__result) {
                            $('#role-permission-dlg').modal('hide');
                        }
                    })
                });
            }
        }
    })
}

function onDeleteGroup(_groupId) {
    confirmYesNoMessage('请确认是否删除', function(_msg) {
        if (_msg == true) {
            $.ajax({
                cache: false,
                url: 'deleteGroup',
                type: 'POST',
                data: {groupId: _groupId},
                success: function (_result) {
                    if (_result != null && _result == 'success') {
                        $('#group-list').DataTable().ajax.reload();
                    } else {
                        showMessage('无法删除');
                    }
                }
            });
        }
    });
}

function onAddNewGroup() {
    var validator = $( "#new-group-form" ).validate();
    validator.resetForm();
    
    $('#new-group-form input').each(function() {
        $(this).closest('.form-group').removeClass('has-error');
    });
    
    $('#groupName').val('');
    $('#add-group-dlg').modal({show: true});
}
