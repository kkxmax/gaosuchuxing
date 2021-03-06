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
                <form:form commandName="goodsForm" action="${pageContext.request.contextPath}/admin/goodsFormPost/${url}" cssClass="form-horizontal" method="post" enctype="multipart/form-data">
                    <div class="form-body">
                        <div class="alert alert-danger display-hide">
                            <button class="close" data-close="alert"></button>
                            你有一些表单错误。 请检查下面。
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
                            <label class="col-md-2 control-label">所属门店<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5 shop-opt">
                                <form:select cssClass="form-control validation-control select2me" path="shopId">
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
                            
                            <form:hidden path="id" readonly="true" />

                            <div class="col-md-5">
                                <form:input type="text" path="name" cssClass="form-control validation-control" placeholder="输入商品名称" maxlength="15" />
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">商品分类<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5 goods-kind-opt">
                                <form:select cssClass="form-control validation-control" path="goodsKindId">
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
                                <form:input type="text" path="price" cssClass="form-control validation-control mask_decimal" placeholder="输入单价" maxlength="6" />
                            </div>
                            <label class="col-md-1 control-label" style="text-align: left;">元</label>
                        </div>                        
                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">商品介绍<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <form:textarea path="description" cssClass="form-control" rows="3" maxlength="30"/>
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">商品图片<span class="required" aria-required="true"> *</span></label>

                            <div class="col-md-5">
                                <c:choose>
                                    <c:when test="${not empty goodsForm.imagePath}">
                                        <img src="/${goodsForm.imagePath}" alt="Icon" class="img-responsive" id="img_preivew" style="width: 100%"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<c:url value="/assets/admin/pages/img/none_img.png"/>" alt="Icon" class="img-responsive" id="img_preivew" style="width: 100%"/>
                                    </c:otherwise>    
                                </c:choose>
                                
                                <div style="margin-top: 10px;">
                                    <span class="btn btn-file btn-default">
                                        <span class="fileinput-new">
                                        选择照片 </span>
                                        <input type="file" id="img_file" name="file">
                                    </span>
                                </div>
                            </div>
                            
                            <div class="col-md-4">
                                <input type="hidden" id="x1" name="x1" />
                                <input type="hidden" id="y1" name="y1" />
                                <input type="hidden" id="x2" name="x2" />
                                <input type="hidden" id="y2" name="y2" />
                                <input type="hidden" id="sw" name="sw" />
                                <input type="hidden" id="sh" name="sh" />
                                <table class="table table-light">
                                    <tr>
                                        <td align="right">
                                            <label class="control-label">宽 :</label>
                                        </td>
                                        <td>
                                            <input type="text" id="w" name="w" class="form-control btn-sm" readonly/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right">
                                            <label class="control-label">高 :</label>
                                        </td>
                                        <td>
                                            <input type="text" id="h" name="h" class="form-control btn-sm" readonly/>
                                        </td>
                                    </tr>
                                </table>
                            </div>                            
                        </div>
                    </div>
                    
                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-offset-2 col-md-5">                                
                                <button type="submit" class="btn purple"> 确定 <i class="fa fa-save"></i></button>
                                <a href="<c:url value="/admin/goodsList"/>" class="btn default"> 取消 <i class="fa fa-repeat"></i></a>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>    
</div>