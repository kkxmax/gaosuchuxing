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

var validateSelectOptions = {
    errorElement: 'span', //default input error message container
    errorClass: 'help-block help-block-error', // default input error message class
    focusInvalid: false, // do not focus the last invalid input
    ignore: "",  // validate all fields including form hidden input
    messages: {required: "不允许为空"},
    rules: {
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

var handleValidateGoodskindForm = function() {
    var form1 = $('#goodskind-form');

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
            goodsKindName: { required: "不允许为空" },
            shopkind_id: { required: "不允许为空" }
        },
        rules: {
            goodsKindName: {
                minlength: 1,
                required: true
            },
            shopkind_id: {
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
            if ($('#goodskind-id').val() == '')
                _url = "addNewGoodsKind";
            else
                _url = "updateGoodsKind";
            
            $.ajax({
                url: _url,
                type: 'POST',
                data: {goodsKindName: $('#goodsKindName').val(), shopKindId: $('#shopkind_id').val(), goodsKindId: $('#goodskind-id').val()},
                success: function(_result) { 
                    if (_result != null && _result == 'success') {                        
                        $('#goodskind-form-dlg').modal("hide");
                        $('#goodsKindName').val('');
                        $("#shopkind_id option[value='']").prop("selected", true);
                        $('#goodskind-id').val('');
                        $('#goodskind-list').DataTable().ajax.reload();
                    } else {
                        alert('商品分类重复');
                    }
                },
                error: function() {
                    alert('商品分类重复');
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

var validationGoodsKindName = function() {
    var element = $('#goodsKindName');    
    element.validate(validateOptions);
}

var validationShopkindId = function() {
    var element = $('#shopkind_id');    
    element.validate(validateSelectOptions);
}

var goodskindList = function () {

    var table = $('#goodskind-list');

    // begin first table
    table.dataTable({
        "searching": false,
        "lengthChange": false,
        "serverSide": true,
        "ajax": {
            url: 'getGoodsKindList',
            type: 'POST',
            data: {name: $('#name').val(), shopKindId: $('#shopKindId').val()}
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
                "targets": [3]
            }
        ]   
    });

    var tableWrapper = jQuery('#goodskind-list_wrapper');

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
    $('.search-option').change(function() {
        $('#searchForm').submit();
    });
    
    handleValidateGoodskindForm();
    
    validationGoodsKindName();
    validationShopkindId();
    
    $('#goodsKindName').on('blur', function() {
        $(this).valid();
    });
    
    $('#shopkind_id').on('blur', function() {
        $(this).valid();
    });
    
    goodskindList();
});

function onDeleteGoodsKind(_goodsKindId) {
    confirmYesNoMessage('请确认是否删除', function(_msg) {
        if (_msg == true) {
            $.ajax({
                cache: false,
                url: 'deleteGoodsKind',
                type: 'POST',
                data: {goodsKindId: _goodsKindId},
                success: function (_result) {
                    if (_result != null && _result == 'success') {
                        $('#goodskind-list').DataTable().ajax.reload();
                    } else {
                        showMessage('该商品分类已关联门店类型，不能删除。');
                    }
                }
            });
        }
    });
}

function onAddNewGoodsKind() {
    var validator = $( "#goodskind-form" ).validate();
    validator.resetForm();
    
    $('#goodskind-form input').each(function() {
        $(this).closest('.form-group').removeClass('has-error');
    });
    
    $('#goodskind-form select').each(function() {
        $(this).closest('.form-group').removeClass('has-error');
    });
    
    $('#goodsKindName').val('');
    $("#shopkind_id option[value='']").prop("selected", true);
    $('#goodskind-id').val('');
    $('#goodskind-form-title').text('新增商品分类');
    $('#goodskind-form-dlg').modal({show: true});
}

function onEditGoodsKind(_goodsKindId) {
    var validator = $( "#goodskind-form" ).validate();
    validator.resetForm();
    
    $('#goodskind-form input').each(function() {
        $(this).closest('.form-group').removeClass('has-error');
    });
    
    $('#goodskind-form select').each(function() {
        $(this).closest('.form-group').removeClass('has-error');
    });
    
    $('#goodsKindName').val('');
    $("#shopkind_id option[value='']").prop("selected", true);
    $('#goodskind-id').val(_goodsKindId);
    $('#goodskind-form-title').text('编辑商品分类');
    
    $.ajax({
        url: 'getGoodsKind',
        cache: false,
        data: {goodsKindId: _goodsKindId},
        success: function (_json) {
            if (_json != null) {
                 var goodsKind = eval(_json);
                 $('#goodsKindName').val(goodsKind.name);
                 $("#shopkind_id option[value='" + goodsKind.shopKindId + "']").prop("selected", true);
                 $('#goodskind-form-dlg').modal({show: true});
            }
        }
    })
}
