<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- BEGIN PAGE HEADER-->
<h3 class="page-title">
    ${title}
    <c:if test="${url != '0'}">
        <small>${small}</small>
    </c:if>
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

<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet box">
            <div class="portlet-body form">
                <form:form commandName="deliverForm" action="${pageContext.request.contextPath}/admin/deliverForm/${url}" cssClass="form-horizontal" method="post">
                    <div class="form-body">
                        <div class="alert alert-danger display-hide">
                            <button class="close" data-close="alert"></button>
                            你有一些表单错误。 请检查下面。
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                账号
                                <span class="required" aria-required="true"> *</span>
                            </label>
                            
                            <form:hidden path="id" readonly="true" />

                            <div class="col-md-5">
                                <c:if test="${url != '0'}">
                                    <form:input type="text" path="deliverId" cssClass="form-control" placeholder="输入账号" readonly="true"/>
                                </c:if>
                                <c:if test="${url == '0'}">
                                    <form:input type="text" path="deliverId" cssClass="form-control validation-control" placeholder="输入账号(建议手机号为准)" maxlength="20" />
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label">姓名<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <form:input type="text" path="name" autoComplete="Off" cssClass="form-control validation-control" placeholder="输入姓名" maxlength="20" />
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">联系电话<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <form:input type="text" path="telNo" autoComplete="Off" cssClass="form-control validation-control" placeholder="输入联系电话" maxlength="11" />
                            </div>
                        </div>
                        
                        <c:if test="${url == '0'}">
                            <div class="form-group">
                                <label class="col-md-2 control-label">密码<span class="required" aria-required="true"> *</span></label>

                                <div class="col-md-5">
                                    <form:input type="password" path="password" autoComplete="Off" cssClass="form-control validation-control" placeholder="输入密码" maxlength="20" />
                                </div>
                            </div>
                                
                            <div class="form-group">
                                <label class="col-md-2 control-label">确认密码<span class="required" aria-required="true"> *</span></label>

                                <div class="col-md-5">
                                    <%--<form:input type="password" path="password" autoComplete="Off" cssClass="form-control" placeholder="输入确认密码"/>--%>
                                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control validation-control" placeholder="输入确认密码" autoComplete="Off" maxlength="20" />
                                </div>
                            </div>    
                        </c:if>
                        
                    </div>
                    
                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-offset-2 col-md-5">                                
                                <button type="submit" class="btn purple"> 确定 <i class="fa fa-save"></i></button>
                                <a href="<c:url value="/admin/deliverList"/>" class="btn default"> 取消 <i class="fa fa-repeat"></i></a>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>    
</div>