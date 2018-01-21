/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var stationList = function () {

    var table = $('#station-list');

    // begin first table
    table.dataTable({
        "searching": false,
        "lengthChange": false,
        "serverSide": true,
        "ajax": {
            url: 'getStationList',
            type: 'POST',
            data: {keyword: $('#keyword').val(), status: $('#status').val(), districtId: $('#districtId').val()}
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
                "width": "170px",
                "targets": [2]
            },
            {
                "orderable": false,
                "width": "170px",
                "targets": [7]
            }
        ],
            "order": [
                [6, "desc"]
            ] // set first column as a default sort by asc
    });

    var tableWrapper = jQuery('#station-list_wrapper');

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
    
    stationList();
    
    $('#districtParentId').live('change', function() {
//        $.ajax({
//            cache: false,
//            url: $('#get-district-list-url').val(),
//            data: {parentId: $(this).val()},
//            dataType: "html",
//            success: function (_html) {
//                $('.district-opt').empty().append(_html);
//                $('#searchForm').submit();
//            }
//        });
        $('#searchForm').submit();
    });
    
    $('#districtId').live('change', function() {
        $('#searchForm').submit();
    });
});

function onDeleteStation(_stationId) {
    confirmYesNoMessage('请确认是否删除', function(_msg) {
        
        if (_msg == true) {
            $.ajax({
                cache: false,
                url: 'deleteStation',
                type: 'POST',
                data: {stationId: _stationId},
                success: function (_result) {
                    if (_result != null && _result == 'success') {
                        $('#station-list').DataTable().ajax.reload();
                    } else {
                        showMessage('该服务区下有餐饮店不可删除');
                    }
                }
            });
        }
    });
}