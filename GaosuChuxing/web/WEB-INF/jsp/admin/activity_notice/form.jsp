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
            <a href="<c:url value="/admin/activityNoticeList"/>">活动预告</a>
        </li>
    </ul>
</div>
<!-- END PAGE HEADER-->

<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet box">
            <div class="portlet-body form">
                <form:form commandName="activityNoticeForm" action="${pageContext.request.contextPath}/admin/activityNoticeFormPost/${url}" cssClass="form-horizontal" method="post" enctype="multipart/form-data">
                    <div class="form-body">
                        <div class="alert alert-danger display-hide">
                            <button class="close" data-close="alert"></button>
                            你有一些表单错误。 请检查下面。
                        </div>
                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                活动预告标题
                                <span class="required" aria-required="true"> *</span>
                            </label>
                            
                            <form:hidden path="id" readonly="true" />

                            <div class="col-md-5">
                                <form:input type="text" path="name" cssClass="form-control validation-control" placeholder="输入活动预告标题"/>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">活动名称<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <form:select cssClass="form-control validation-control" path="activityId">
                                    <form:option value="0">--请选择活动名称--</form:option>
                                    <c:forEach var="activity" items="${activityList}">
                                        <form:option value="${activity.id}">${activity.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">预告内容</label>

                            <div class="col-md-5">
                                <form:textarea path="description" cssClass="form-control" rows="3"/>
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">活动图片<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 250px; height: 130px;">
                                        <c:if test="${not empty activityNoticeForm.imagePath}">
                                            <img src="/${activityNoticeForm.imagePath}" alt="Icon"/>
                                        </c:if>
                                        <c:if test="${empty activityNoticeForm.imagePath}">
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
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                分享链接标题
                            </label>

                            <div class="col-md-5">
                                <form:input type="text" path="shareTitle" cssClass="form-control" placeholder="输入分享链接标题" maxlength="255" />
                            </div>
                        </div>             
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                分享链接文案
                            </label>

                            <div class="col-md-5">
                                <form:textarea path="shareContent" cssClass="form-control" rows="3" maxlength="255"/>
                            </div>
                        </div>    
                            
                    </div>
                    
                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-offset-2 col-md-5">                                
                                <button type="submit" class="btn purple"> 确定 <i class="fa fa-save"></i></button>
                                <a href="<c:url value="/admin/activityNoticeList"/>" class="btn default"> 取消 <i class="fa fa-repeat"></i></a>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>    
</div>
