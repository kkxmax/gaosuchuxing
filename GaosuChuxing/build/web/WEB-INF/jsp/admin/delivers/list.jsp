<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- BEGIN PAGE HEADER-->
<h3 class="page-title">
    配送员列表
</h3>
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i>
            <a href="<c:url value="/admin/index"/>">首页</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="<c:url value="/admin/deliverList"/>">配送员管理</a>
            <i class="fa fa-angle-right"></i>
        </li>
    </ul>
</div>
<!-- END PAGE HEADER-->

<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
<div class="modal fade" id="deliver-password-change-dlg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" style="border-bottom: 0;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>   
                <h4 class="modal-title">重置密码</h4>
            </div>
            <div class="modal-body">
                <div class="scroller form form-horizontal" style="height:100px" data-always-visible="1" data-rail-visible1="1">
                    <div class="row form-group">
                        <label class="col-md-3 control-label">密码<span class="required" aria-required="true"> *</span></label>
                        <div class="col-md-8">
                            <input type="password" id="deliver-change-new-password" autoComplete="Off" class="form-control" placeholder="输入密码" maxlength="20" />
                        </div>
                    </div>
                    <div class="row form-group">
                        <label class="col-md-3 control-label">确认密码<span class="required" aria-required="true"> *</span></label>
                        <div class="col-md-8">
                            <input type="password" id="deliver-change-confirm-password" autoComplete="Off" class="form-control" placeholder="输入确认密码" maxlength="20" />
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="modal-footer"  style="border-top: 0;">
                <button type="button" class="btn blue" id="deliver-password-change-btn-submit"> 确认 </button>
                <button type="button" class="btn default" data-dismiss="modal"> 取消 </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->

<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet box">
            <div class="portlet-body form">
                <form:form commandName="searchForm" action="deliverList" cssClass="form-horizontal"  method="post">
                    <div class="form-group">
                        <label class="col-md-2 control-label">关键词</label>

                        <div class="col-md-4">
                            <form:input type="text" path="keyword" cssClass="form-control" placeholder="输入关键词"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-md-offset-2 col-md-4">
                            <button type="submit" class="btn yellow"> 查询 <i class="fa fa-search"></i></button>
                            <a href="deliverForm/0" class="btn green"> 新增 <i class="fa fa-plus"></i></a>
                        </div>
                    </div>    
                </form:form>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="portlet box">
            <div class="portlet-body">
                <table id="deliver-list" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>序号</th>
                            <th>账号</th>
                            <th>姓名</th>
                            <th>联系电话</th>
                            <th>新增时间</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<!-- END PAGE CONTENT-->