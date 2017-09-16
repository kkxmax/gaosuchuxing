<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- BEGIN PAGE HEADER-->
<h3 class="page-title">
    ${title}
    <small>${small}</small>
</h3>
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i>
            <a href="<c:url value="/admin/index"/>">首页</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="<c:url value="/admin/stationList"/>">服务区管理</a>
        </li>
    </ul>
</div>
<!-- END PAGE HEADER-->

<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet box">
            <div class="portlet-body form">
                <form:form commandName="stationForm" cssClass="form-horizontal" method="post">
                    <div class="form-body">
                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                服务区名称
                                <span class="required" aria-required="true"> *</span>
                            </label>

                            <div class="col-md-5">
                                <form:input type="text" path="name" cssClass="form-control validation-control" placeholder="输入服务区名称" maxlength="15" readonly="true" />
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">服务区图片<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <img src="/${orderForm.imagePath}" alt="Icon" class="img-responsive" />
                            </div>
                        </div>    

                        <div class="form-group">
                            <label class="col-md-2 control-label">所属城市<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-2">
                                <form:select path="districtParentId" cssClass="form-control" disabled="true">
                                    <form:option value="-2">--选择省份--</form:option>
                                    <c:forEach var="parentDistrict" items="${parentDistrictList}">
                                        <form:option value="${parentDistrict.id}">${parentDistrict.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>

                            <div class="col-md-2 district-opt">
                                <form:select path="districtId" cssClass="form-control" disabled="true">
                                    <c:forEach var="district" items="${districtList}">
                                        <form:option value="${district.id}">${district.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">服务区位置<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">服务区状态<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <form:select cssClass="form-control validation-control" path="status" disabled="true">
                                    <form:option value="">--请选择服务区状态--</form:option>
                                    <form:option value="未开放">未开放</form:option>
                                    <form:option value="已开放">已开放</form:option>
                                </form:select>
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">配送员<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <form:select cssClass="form-control validation-control" path="deliverId" disabled="true">
                                    <form:option value="0">--请选择配送员--</form:option>
                                    <c:forEach var="deliver" items="${deliverList}">
                                        <form:option value="${deliver.id}">${deliver.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>    
                            
                    </div>
                    
                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-offset-2 col-md-5">
                                <a href="<c:url value="/admin/stationList"/>" class="btn default"> 取消 <i class="fa fa-repeat"></i></a>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>    
</div>