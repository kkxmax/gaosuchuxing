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
            <a href="<c:url value="/admin/goodsList"/>">商品管理</a>
        </li>
    </ul>
</div>
<!-- END PAGE HEADER-->

<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet box">
            <div class="portlet-body form">
                <form:form commandName="goodsForm" cssClass="form-horizontal" method="post">
                    <div class="form-body">
                        <div class="form-group">
                            <label class="col-md-2 control-label">门店类型<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <form:select cssClass="form-control validation-control" path="shopKindId" disabled="true">
                                    <form:option value="0">--请选择门店类型--</form:option>
                                    <c:forEach var="shopKind" items="${shopKindList}">
                                        <form:option value="${shopKind.id}">${shopKind.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">所属门店<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <form:select cssClass="form-control validation-control" path="shopId" disabled="true">
                                    <form:option value="0">--请选择所属门店--</form:option>
                                    <c:forEach var="shop" items="${shopList}">
                                        <form:option value="${shop.id}">${shop.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                商品名称
                                <span class="required" aria-required="true"> *</span>
                            </label>
                            
                            <div class="col-md-5">
                                <form:input type="text" path="name" cssClass="form-control validation-control" placeholder="输入商品名称" maxlength="15" readonly="true"/>
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">商品分类<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <form:select cssClass="form-control validation-control" path="goodsKindId" disabled="true">
                                    <form:option value="0">--请选择商品分类--</form:option>
                                    <c:forEach var="goodsKind" items="${goodsKindList}">
                                        <form:option value="${goodsKind.id}">${goodsKind.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">
                                单价
                                <span class="required" aria-required="true"> *</span>
                            </label>
                            
                            <div class="col-md-4">
                                <form:input type="text" path="price" cssClass="form-control validation-control" placeholder="输入单价" maxlength="6" readonly="true"/>
                            </div>
                            <label class="col-md-1 control-label" style="text-align: left;">元</label>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">商品介绍<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <form:textarea path="description" cssClass="form-control" rows="3" maxlength="15" readonly="true"/>
                            </div>
                        </div>    
                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">商品图片<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <img src="/${goodsForm.imagePath}" alt="Icon" class="img-responsive" />
                            </div>
                        </div>                            
                    </div>
                    
                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-offset-2 col-md-5">
                                <a href="<c:url value="/admin/goodsList"/>" class="btn default"> 取消 <i class="fa fa-repeat"></i></a>
                            </div>
                        </div>
                    </div>
                            
                </form:form>
            </div>
        </div>
    </div>    
</div>