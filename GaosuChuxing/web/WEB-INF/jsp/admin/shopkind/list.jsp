<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- BEGIN PAGE HEADER-->
<h3 class="page-title">
    门店类型列表
</h3>
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i>
            <a href="<c:url value="/admin/index"/>">首页</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="<c:url value="javascript:;"/>">门店管理</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="javascript:;">门店类型</a>
        </li>
    </ul>
</div>
<!-- END PAGE HEADER-->

<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet box">            
            <div class="portlet-body">
                <div class="table-toolbar">
                    <div class="row">
                        <div class="col-md-4">
                            <a href="javascript:;" class="btn green" onclick="onAddNewShopKind()"> 新增 <i class="fa fa-plus"></i></a>
                        </div>
                    </div>    
                </div>
                
                <table id="shopkind-list" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>序号</th>
                            <th>门店类型</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<!-- END PAGE CONTENT-->

<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
<div class="modal fade" id="shopkind-form-dlg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog form">
        <form id="shopkind-form" class="form-horizontal">
            <div class="modal-content">
                <div class="modal-header" style="border-bottom: 0;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>   
                    <h4 class="modal-title" id="shopkind-form-title"></h4>
                </div>

                <div class="modal-body">
                    <div class="scroller" style="height:80px;" data-always-visible="1" data-rail-visible1="1">
                        <input type="hidden" id="shopkind-id" />
                        <div class="form-group">
                            <label class="col-md-2 control-label">门店类型<span class="required" aria-required="true"> *</span></label>
                            <div class="col-md-8">
                                <input type="text" id="shopKindName" name="shopKindName" class="form-control" placeholder="输入门店类型" maxlength="6" />
                            </div>    
                        </div>
                    </div>
                </div>

                <div class="modal-footer"  style="border-top: 0;">
                    <button type="submit" class="btn blue" id="shopkind-form-btn-submit"> 确定 </button>
                    <button type="button" class="btn default" data-dismiss="modal"> 取消 </button>
                </div>
            </div>
        </form>            
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->