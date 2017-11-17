<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
            <a href="<c:url value="/admin/activityList"/>">活动管理</a>
        </li>
    </ul>
</div>
<!-- END PAGE HEADER-->

<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet box">
            <div class="portlet-body form">
                <form:form commandName="activityForm" action="${pageContext.request.contextPath}/admin/activityFormPost/${url}" cssClass="form-horizontal" method="post" enctype="multipart/form-data">
                    <div class="form-body">
                        <div class="alert alert-danger display-hide">
                            <button class="close" data-close="alert"></button>
                            你有一些表单错误。 请检查下面。
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                活动名称
                                <span class="required" aria-required="true"> *</span>
                            </label>
                            
                            <form:hidden path="id" readonly="true" />
                            <form:hidden path="tmpUId" readonly="true" />

                            <div class="col-md-5">
                                <form:input type="text" path="name" cssClass="form-control validation-control" placeholder="输入活动名称" maxlength="15" />
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                活动开始日期
                                <span class="required" aria-required="true"> *</span>
                            </label>
                            
                            <fmt:formatDate pattern="yyyy-MM-dd" var="fmtStartDate" value="${activityForm.startDate}" />
                            <div class="col-md-5">
                                <div class="input-group date date-picker" data-date-format="yyyy-mm-dd">
                                    <form:input type="text" path="startDateStr" cssClass="form-control validation-control" value="${fmtStartDate}" />
                                    <span class="input-group-btn">
                                        <button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
                                    </span>
                                </div>
                            </div>
                        </div> 
                                    
                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                活动结束日期
                                <span class="required" aria-required="true"> *</span>
                            </label>
                            
                            <fmt:formatDate pattern="yyyy-MM-dd" var="fmtEndDate" value="${activityForm.endDate}" />
                            <div class="col-md-5">
                                <div class="input-group date date-picker" data-date-format="yyyy-mm-dd" data-date-start-date="+0d">                                    
                                    <form:input type="text" path="endDateStr" cssClass="form-control validation-control" value="${fmtEndDate}" />
                                    <span class="input-group-btn">
                                        <button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
                                    </span>
                                </div>
                            </div>
                        </div>        
                                    
                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                活动金额
                                <span class="required" aria-required="true"> *</span>
                            </label>

                            <div class="col-md-5">
                                <fmt:formatNumber pattern="#0.##" var="fmtActivityAmount" value="${activityForm.activityAmount}" />
                                <form:input type="text" path="activityAmount" cssClass="form-control" placeholder="输入活动金额" readonly="true" value="${fmtActivityAmount}" />
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                优惠券数量
                                <span class="required" aria-required="true"> *</span>
                            </label>

                            <div class="col-md-5">
                                <form:input type="text" path="couponQty" cssClass="form-control" placeholder="输入优惠券数量" readonly="true" />
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                类型
                                <span class="required" aria-required="true"> *</span>
                            </label>

                            <div class="col-md-7">
                                <div class="row">
                                    <div class="input-group">
                                        <div class="icheck-inline">
                                            <label><form:checkbox cssClass="icheck" path="unlimitedType" id="unlimitedType"/> 不限消费优惠券 </label>
                                        </div>
                                    </div>
                                </div>    
                                        
                                <div id="unlimited-type-details" class="row" style="margin-bottom: 10px;">
                                </div>       
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label"></label>

                            <div class="col-md-9">
                                <div class="row">
                                    <div class="input-group">
                                        <div class="icheck-inline">
                                            <label><form:checkbox cssClass="icheck" path="fullType" id="fullType"/> 满减优惠券 </label>
                                        </div>
                                    </div>
                                </div>
                                    
                                <div id="full-type-details" class="row" style="margin-bottom: 10px;">
                                </div>    
                            </div>
                                    
                        </div>    
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">活动图片<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 250px; height: 130px;">
                                        <c:if test="${not empty activityForm.imagePath}">
                                            <img src="/${activityForm.imagePath}" alt="Icon"/>
                                        </c:if>
                                        <c:if test="${empty activityForm.imagePath}">
                                            <img src="<c:url value="/assets/admin/pages/img/no-image.png"/>" alt="Icon"/>
                                        </c:if>
                                    </div>
                                    <div>
                                        <span class="btn btn-file btn-default">
                                            <span class="fileinput-new">
                                            选择照片 </span>
                                            <span class="fileinput-exists">
                                            更改 </span>
                                            <input type="file" id="img_file" name="file">
                                        </span>
                                        <a href="javascript:;" class="btn fileinput-exists btn-danger" data-dismiss="fileinput">
                                        消除 </a>
                                    </div>
                                </div>
                            </div>
                        </div>    
                            
                    </div>
                    
                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-offset-2 col-md-5">                                
                                <button type="submit" class="btn purple"> 确定 <i class="fa fa-save"></i></button>
                                <a href="<c:url value="/admin/activityFormCancel"/>/${activityForm.tmpUId}" class="btn default"> 取消 <i class="fa fa-repeat"></i></a>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>    
</div>