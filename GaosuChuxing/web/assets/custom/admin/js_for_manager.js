/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var managerList = function () {

    var table = $('#manager-list');

    // begin first table
    table.dataTable({
        "searching": false,
        "lengthChange": false,
        "serverSide": true,
        "ajax": {
            url: 'getManagerList',
            type: 'POST',
            data: {keyword: $('#keyword').val(), groupId: $('#groupId').val()}
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
                "width": "220px",
                "targets": [5]
            }
        ],
            "order": [
                [4, "desc"]
            ] // set first column as a default sort by asc
    });

    var tableWrapper = jQuery('#manager-list_wrapper');

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
    
    managerList();
});

function onDeleteManager(_managerId) {
    confirmYesNoMessage('请确认是否删除', function(_msg) {
        if (_msg == true) {
            $.ajax({
                cache: false,
                url: 'deleteManager',
                type: 'POST',
                data: {managerId: _managerId},
                success: function (_result) {
                    if (_result != null && _result == 'success') {
                        $('#manager-list').DataTable().ajax.reload();
                    } else {
                        showMessage('无法删除');
                    }
                }
            });
        }
    });
}

function onChangePassword(_managerId) {
    $('#manager-password-change-dlg').modal({show: true});
    
    $('#manager-password-change-btn-submit').unbind('click').click(function() {
        var newPwd = $('#manager-change-new-password').val();
        var confirmPwd = $('#manager-change-confirm-password').val();
        
        if (newPwd == '' || confirmPwd == '') {
            alert('不允许为空');
        } else if (newPwd.length < 6 || confirmPwd.length < 6)  {
            alert('密码6-20字符');
        } else if (newPwd != confirmPwd) {
            alert('密码与确认密码，两次输入的必须一致');
        } else {
            $.ajax({
                cache: false,
                url: 'changeManagerPassword',
                data: {managerId: _managerId, password: newPwd},
                success: function(_result) {
                    $('#manager-password-change-dlg').modal('hide');
                    $('#manager-change-new-password').val('');
                    $('#manager-change-confirm-password').val('');
                },
                error: function() {
                    $('#manager-password-change-dlg').modal('hide');
                    $('#manager-change-new-password').val('');
                    $('#manager-change-confirm-password').val('');
                    showMessage('修改失败');
                }
            });
        }
        
    });
}