/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var goodsList = function () {

    var table = $('#goods-list');

    // begin first table
    table.dataTable({
        "searching": false,
        "lengthChange": false,
        "serverSide": true,
        "ajax": {
            url: 'getGoodsList',
            type: 'POST',
            data: {keyword: $('#keyword').val(), shopKindId: $('#shopKindId').val(), shopId: $('#shopId').val()}
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
                "targets": [3]
            },
            {
                "orderable": false,
                "width": "220px",
                "targets": [10]
            }
        ],
            "order": [
                [9, "desc"]
            ] // set first column as a default sort by asc
    });

    var tableWrapper = jQuery('#goods-list_wrapper');

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

    goodsList();
    
    $('#shopKindId').live('change', function() {
        $.ajax({
            cache: false,
            url: $('#get-shop-list-by-shop-kind-url').val(),
            data: {shopKindId: $(this).val()},
            dataType: "html",
            success: function (_html) {
                $('.shop-opt').empty().append(_html);
                $('#searchForm').submit();
            }
        });
    });
    
    $('#shopId').live('change', function() {
        $('#searchForm').submit();
    });
});

function onDeleteGoods(_goodsId) {
    confirmYesNoMessage('请确认是否删除', function(_msg) {
        
        if (_msg == true) {
            $.ajax({
                cache: false,
                url: 'deleteGoods',
                type: 'POST',
                data: {goodsId: _goodsId},
                success: function (_result) {
                    if (_result != null && _result == 'success') {
                        $('#goods-list').DataTable().ajax.reload();
                    } else {
                        showMessage('无法删除');
                    }
                }
            });
        }
    });
}

function onSetOnGoodsStatus(_goodsId) {
    confirmYesNoMessage('确定要上架？', function(_msg) {
       if (_msg == true)  {
           $.ajax({
                cache: false,
                url: 'setOnGoodsStatus',
                type: 'POST',
                data: {goodsId: _goodsId},
                success: function (_result) {
                    if (_result != null && _result == 'success') {
                        $('#goods-list').DataTable().ajax.reload();
                    } else {
                        showMessage('无法处理');
                    }
                }
            });
       }
    }); 
}

function onSetOffGoodsStatus(_goodsId) {
    confirmYesNoMessage('确定要下架？', function(_msg) {
       if (_msg == true)  {
           $.ajax({
                cache: false,
                url: 'setOffGoodsStatus',
                type: 'POST',
                data: {goodsId: _goodsId},
                success: function (_result) {
                    if (_result != null && _result == 'success') {
                        $('#goods-list').DataTable().ajax.reload();
                    } else {
                        showMessage('无法处理');
                    }
                }
            });
       }
    }); 
}

