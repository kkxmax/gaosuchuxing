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
            <a href="javascript:;">门店管理</a>            
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="<c:url value="/admin/shopList/${type}"/>">${subTitle}</a>
        </li>
    </ul>
</div>
<!-- END PAGE HEADER-->

<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet box">
            <div class="portlet-body form">
                <form:form commandName="shopForm" action="${pageContext.request.contextPath}/admin/shopFormPost/${url}" cssClass="form-horizontal" method="post" enctype="multipart/form-data">
                    <form:hidden path="type" value="${type}" readonly="true" />
                    
                    <div class="form-body">
                        <div class="alert alert-danger display-hide">
                            <button class="close" data-close="alert"></button>
                            你有一些表单错误。 请检查下面。
                        </div>
                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">所属服务区<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <form:select cssClass="form-control validation-control select2me" path="stationId" data-placeholder="--请选所属服务区--">
                                    <form:option value="0">--请选所属服务区--</form:option>
                                    <c:forEach var="station" items="${stationList}">
                                        <form:option value="${station.id}">${station.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                店辅名称
                                <span class="required" aria-required="true"> *</span>
                            </label>
                            
                            <form:hidden path="id" readonly="true" />

                            <div class="col-md-5">
                                <form:input type="text" path="name" cssClass="form-control validation-control" placeholder="输入店辅名称" maxlength="15" />
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">门店类型<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <form:select cssClass="form-control validation-control" path="shopKindId">
                                    <form:option value="0">--请选择门店类型--</form:option>
                                    <c:forEach var="shopKind" items="${shopKindList}">
                                        <form:option value="${shopKind.id}">${shopKind.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>  
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">所属城市<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-2">
                                <form:select path="districtParentId" cssClass="form-control">
                                    <form:option value="-2">--选择省份--</form:option>
                                    <c:forEach var="parentDistrict" items="${parentDistrictList}">
                                        <form:option value="${parentDistrict.id}">${parentDistrict.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>

                            <div class="col-md-2 district-opt">
                                <c:if test="${url == '0'}">
                                    <form:select path="districtId" cssClass="form-control"></form:select>
                                </c:if>
                                <c:if test="${url != '0'}">
                                    <form:select path="districtId" cssClass="form-control">
                                        <c:forEach var="district" items="${districtList}">
                                            <form:option value="${district.id}">${district.name}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </c:if>
                            </div>
                        </div>    
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label"></label>

                            <div class="col-md-5">
                                <form:input type="text" path="address" cssClass="form-control" placeholder="详细地址（详细到门牌）" maxlength="50" />
                            </div>
                        </div>    
                        
                        <c:if test="${shopForm.type == 'company'}">
                            <div class="form-group">
                                <label class="col-md-2 control-label">
                                    起送费
                                    <span class="required" aria-required="true"> *</span>
                                </label>

                                <div class="col-md-5">
                                    <form:input type="text" path="startFee" cssClass="form-control validation-control mask_decimal" placeholder="输入起送费" maxlength="6" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">
                                    配送费
                                    <span class="required" aria-required="true"> *</span>
                                </label>

                                <div class="col-md-5">
                                    <form:input type="text" path="shippingFee" cssClass="form-control validation-control mask_decimal" placeholder="输入配送费" maxlength="6" />
                                </div>
                            </div>    
                        </c:if>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">店辅简介<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <form:textarea path="description" cssClass="form-control validation-control" rows="3" maxlength="40"/>
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">店辅封面<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 250px; height: 130px;">
                                        <c:if test="${not empty showForm.imagePath}">
                                            <img src="/${showForm.imagePath}" alt="Icon"/>
                                        </c:if>
                                        <c:if test="${empty showForm.imagePath}">
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
                                <a href="<c:url value="/admin/shopList/${type}"/>" class="btn default"> 取消 <i class="fa fa-repeat"></i></a>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>    
</div>