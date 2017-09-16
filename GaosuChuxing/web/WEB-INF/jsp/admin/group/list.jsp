<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- BEGIN PAGE HEADER-->
<h3 class="page-title">
    角色列表
</h3>
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i>
            <a href="<c:url value="/admin/index"/>">首页</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="<c:url value="/admin/managerList"/>">系统管理</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="javascript:;">角色管理</a>
        </li>
    </ul>
</div>
<!-- END PAGE HEADER-->

<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet box">            
            <div class="portlet-body">
                <c:if test="${not empty groupList}">
                    <c:if test="${fn:length(orderAssign.assignValues) lt 10}">
                        <div class="table-toolbar">
                            <div class="row">
                                <div class="col-md-4">
                                    <a href="javascript:;" class="btn green" onclick="onAddNewGroup()"> 新增 <i class="fa fa-plus"></i></a>
                                </div>
                            </div>    
                        </div>
                    </c:if>
                </c:if>
                
                <table id="group-list" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>序号</th>
                            <th>用户角色</th>
                            <th>功能设置</th>
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
<div class="modal fade" id="role-permission-dlg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" style="border-bottom: 0;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>   
                <h4 class="modal-title">功能设置</h4>
            </div>
            <div class="modal-body">
                <div class="scroller form form-horizontal" style="height:380px" data-always-visible="1" data-rail-visible1="1">
                    <div class="row form-group">
                        <label class="col-md-1 control-label">&nbsp;</label>
                        <div class="col-md-8">
                            <div class="input-group">
                                <div class="icheck-list">
                                    <c:forEach var="permission" items="${permissionList}">
                                        <c:if test="${permission.parentId == 0}">
                                            <label><input type="checkbox" class="icheck" id="permission_${permission.id}"> ${permission.name} </label>
                                        </c:if>
                                        <c:if test="${permission.parentId > 0}">
                                            <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="icheck sub-permissions" id="permission_${permission.id}"> ${permission.name} </label>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>    
                    </div>
                </div>
            </div>
            
            <div class="modal-footer"  style="border-top: 0;">
                <button type="button" class="btn blue" id="role-permission-btn-submit"> 确定 </button>
                <button type="button" class="btn default" data-dismiss="modal"> 取消 </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->


<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
<div class="modal fade" id="add-group-dlg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog form">
        <form id="new-group-form" class="form-horizontal">
            <div class="modal-content">
                <div class="modal-header" style="border-bottom: 0;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>   
                    <h4 class="modal-title">添加角色</h4>
                </div>

                <div class="modal-body">
                    <div class="scroller" style="height:80px;" data-always-visible="1" data-rail-visible1="1">
                        <div class="form-group">
                            <label class="col-md-2 control-label">角色名称<span class="required" aria-required="true"> *</span></label>
                            <div class="col-md-8">
                                <input type="text" id="groupName" name="groupName" class="form-control" placeholder="输入角色名称" maxlength="10" />
                            </div>    
                        </div>
                    </div>
                </div>

                <div class="modal-footer"  style="border-top: 0;">
                    <button type="submit" class="btn blue" id="add-group-btn-submit"> 确定 </button>
                    <button type="button" class="btn default" data-dismiss="modal"> 取消 </button>
                </div>
            </div>
        </form>            
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->

<script type="text/javascript">
    var permissionOpt = [];
    
    <c:forEach var="permission" items="${permissionList}">
        permissionOpt.push("${permission.id}");
    </c:forEach>
</script>
