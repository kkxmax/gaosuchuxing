<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- BEGIN PAGE HEADER-->
<h3 class="page-title">
    ${LOGIN_USER.name}
    <small>修改密码</small>
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
            <a href="javascript:;">修改密码</a>
        </li>
    </ul>
</div>
<!-- END PAGE HEADER-->

<div class="row">
    <div class="col-md-12">
        <div class="portlet box">
            <div class="portlet-body form">
                <form:form commandName="passwordForm" action="passwordForm" cssClass="form-horizontal" method="post">
                    <div class="form-body">
<!--                        <div class="alert alert-danger display-hide">
                            <button class="close" data-close="alert"></button>
                            你有一些表单错误。 请检查下面。
                        </div>-->

                        <form:input type="hidden" path="managerId" />    

                        <div class="form-group">
                            <label class="col-md-2 control-label">旧密码<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <form:input type="password" path="currentPassword" autocomplete="off" cssClass="form-control validation-control" placeholder="输入旧密码" maxlength="20" />
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">新密码<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <form:input type="password" path="newPassword" autocomplete="off" cssClass="form-control validation-control" placeholder="输入新密码" maxlength="20" />
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">确认密码<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <form:input type="password" path="confirmPassword" autocomplete="off" cssClass="form-control validation-control" placeholder="输入确认密码" maxlength="20" />
                            </div>
                        </div>                            
                    </div>
                    
                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-offset-2 col-md-5">                                
                                <button type="submit" class="btn purple"> 确定 <i class="fa fa-save"></i></button>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>    
</div>