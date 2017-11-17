<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- BEGIN PAGE HEADER-->
<h3 class="page-title">
    ${title}
    <small>
        ${small}
        <c:if test="${activityForm.status == '未开始'}">
            <span class="label label-warning">${activityForm.status}</span>
        </c:if>
        
        <c:if test="${activityForm.status == '进行中'}">
            <span class="label label-info">${activityForm.status}</span>
        </c:if>
            
        <c:if test="${activityForm.status == '已结束'}">
            <span class="label label-success">${activityForm.status}</span>
        </c:if>
    </small>
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
                <form:form commandName="activityForm" cssClass="form-horizontal">
                    <div class="form-body">
                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                活动名称
                                <span class="required" aria-required="true"> *</span>
                            </label>
                            
                            <form:hidden path="id" readonly="true" />

                            <div class="col-md-5">
                                <form:input type="text" path="name" cssClass="form-control" placeholder="输入活动名称" maxlength="15" readonly="true"/>
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                活动开始日期
                                <span class="required" aria-required="true"> *</span>
                            </label>
                            
                            <div class="col-md-5">
                                <fmt:formatDate pattern="yyyy-MM-dd" var="fmtStartDate" value="${activityForm.startDate}" />
                                <form:input type="text" path="startDate" cssClass="form-control" readonly="true" value="${fmtStartDate}" />
                            </div>
                        </div> 
                                    
                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                活动结束日期
                                <span class="required" aria-required="true"> *</span>
                            </label>
                            
                            <div class="col-md-5">
                                <fmt:formatDate pattern="yyyy-MM-dd" var="fmtEndDate" value="${activityForm.endDate}" />
                                <form:input type="text" path="startDate" cssClass="form-control" readonly="true" value="${fmtEndDate}" />
                            </div>
                        </div>        
                                    
                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                活动金额
                                <span class="required" aria-required="true"> *</span>
                            </label>

                            <div class="col-md-5">
                                <fmt:formatNumber pattern="#0.00" var="fmtActivityAmount" value="${activityForm.activityAmount}" />
                                <form:input type="text" path="activityAmount" cssClass="form-control" readonly="true" value="${fmtActivityAmount}"/>
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

                            <div class="col-md-6">
                                <div class="row">
                                    <div class="input-group">
                                        <div class="icheck-inline">
                                            <label><form:checkbox cssClass="icheck" path="unlimitedType" data-checkbox="icheckbox_square-grey" disabled="true"/> 不限消费优惠券 </label>
                                        </div>
                                    </div>
                                </div>
                                
                                <div id="unlimited-type-details" class="row" style="margin-bottom: 10px;">
                                    <div class="table-scrollable">
                                        <table class="table table-striped table-bordered table-hover">
                                            <thead>
                                                <th>优惠券金额（元）</th>
                                                <th>数量</th>
                                                <th>中奖率（%）</th>
                                            </thead>
                                            <tbody>
                                                <c:if test="${unlimitedDetails != null}">
                                                    <c:forEach var="tmp" items="${unlimitedDetails}">
                                                        <tr>
                                                            <td>
                                                                <fmt:formatNumber pattern="#0.00" var="fmtCouponAmount" value="${tmp.couponAmount}" />
                                                                <input type="text" class="form-control input-small" readonly value="${fmtCouponAmount}" />
                                                            </td>
                                                            <td>          
                                                                <input type="text" class="form-control input-small" readonly value="${tmp.qty}" />                                                                
                                                            </td>
                                                            <td>
                                                                <fmt:formatNumber pattern="#0.##" var="fmtRate" value="${tmp.rate}" />
                                                                <input type="text" class="form-control input-small" readonly value="${fmtRate}" />
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>         
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label"></label>

                            <div class="col-md-8">
                                <div class="row">
                                    <div class="input-group">
                                        <div class="icheck-inline">
                                            <label><form:checkbox cssClass="icheck" path="fullType" data-checkbox="icheckbox_square-grey" disabled="true"/> 满减优惠券 </label>
                                        </div>
                                    </div>
                                </div>
                                
                                <div id="full-type-details" class="row" style="margin-bottom: 10px;">
                                    <div class="table-scrollable">
                                        <table class="table table-striped table-bordered table-hover">
                                            <thead>
                                                <th>订单金额满减</th>
                                                <th>优惠券金额（元）</th>
                                                <th>数量</th>
                                                <th>中奖率（%）</th>
                                            </thead>
                                            <tbody>
                                                <c:if test="${fullDetails != null}">
                                                    <c:forEach var="tmp" items="${fullDetails}">
                                                        <tr>
                                                            <td>
                                                                <fmt:formatNumber pattern="#0.00" var="fmtFullAmount" value="${tmp.fullAmount}" />
                                                                <input type="text" class="form-control input-small" readonly value="${fmtFullAmount}" />
                                                            </td>
                                                            <td>
                                                                <fmt:formatNumber pattern="#0.00" var="fmtCouponAmount" value="${tmp.couponAmount}" />
                                                                <input type="text" class="form-control input-small" readonly value="${fmtCouponAmount}" />
                                                            </td>
                                                            <td>          
                                                                <input type="text" class="form-control input-small" readonly value="${tmp.qty}" />                                                                
                                                            </td>
                                                            <td>
                                                                <fmt:formatNumber pattern="#0.##" var="fmtRate" value="${tmp.rate}" />
                                                                <input type="text" class="form-control input-small" readonly value="${fmtRate}" />
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>        
                            </div>
                        </div>    
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">活动图片<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <img src="/${activityForm.imagePath}" alt="Icon" class="img-responsive" />
                            </div>
                        </div>    
                            
                    </div>
                    
                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-offset-2 col-md-5">
                                <a href="<c:url value="/admin/activityList"/>" class="btn default"> 取消 <i class="fa fa-repeat"></i></a>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>    
</div>