<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- BEGIN PAGE HEADER-->
<h3 class="page-title">
    ${title}
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
            <a href="javascript:;">${navTitle}</a>
        </li>
    </ul>
</div>
<!-- END PAGE HEADER-->

<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet box">
            <div class="portlet-body form">
                <form:form commandName="searchForm" action="${pageContext.request.contextPath}/admin/shopList/${type}" cssClass="form-horizontal" method="post">
                    
                    <form:hidden path="type" />
                    
                    <div class="form-group">
                        <label class="col-md-2 control-label">店辅名称</label>

                        <div class="col-md-4">
                            <form:input type="text" path="keyword" cssClass="form-control" placeholder="输入店辅名称"/>
                        </div>
                    </div>
                        
                    <div class="form-group">
                        <label class="col-md-2 control-label">所属服务区</label>

                        <div class="col-md-4">
                            <form:select path="stationId" cssClass="form-control search-option select2me" data-placeholder="--请选择所属服务区--">
                                <form:option value=""></form:option>
                                <c:forEach var="station" items="${stationList}">
                                    <form:option value="${station.id}">${station.name}</form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>    
                        
                    <div class="form-group">
                        <label class="col-md-2 control-label">门店类型</label>

                        <div class="col-md-4">
                            <form:select path="shopKindId" cssClass="form-control search-option">
                                <form:option value="">--请选择门店类型--</form:option>
                                <c:forEach var="shopKind" items="${shopKindList}">
                                    <form:option value="${shopKind.id}">${shopKind.name}</form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>     

                    <div class="form-group">
                        <label class="col-md-2 control-label">店辅地址</label>

                        <div class="col-md-2">
                            <form:select path="districtParentId" cssClass="form-control">
                                <form:option value="">--选择省份--</form:option>
                                <c:forEach var="parentDistrict" items="${parentDistrictList}">
                                    <form:option value="${parentDistrict.id}">${parentDistrict.name}</form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                                                
                        <div class="col-md-2 district-opt">
                            <form:select path="districtId" cssClass="form-control">
                                <c:if test="${districtList != null}">
                                    <c:forEach var="district" items="${districtList}">
                                        <form:option value="${district.id}">${district.name}</form:option>
                                    </c:forEach>
                                </c:if>
                            </form:select>
                        </div>
                    </div>    

                    <div class="form-group">
                        <div class="col-md-offset-2 col-md-4">
                            <button type="submit" class="btn yellow"> 查询 <i class="fa fa-search"></i></button>
                            <a href="${pageContext.request.contextPath}/admin/shopForm/0?type=${type}" class="btn green"> 新增 <i class="fa fa-plus"></i></a>
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
                <c:if test="${type == 'company'}">
                    <table id="company-shop-list" class="table table-striped table-bordered table-hover shop-list">
                        <thead>
                            <tr>
                                <th>序号</th>
                                <th>店辅名称</th>
                                <th>门店类型</th>
                                <th>所属服务区</th>
                                <th>店辅地址</th>
                                <th>起送费</th>
                                <th>配送费</th>
                                <th>排序</th>
                                <th>新增时间</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                    </table>
                </c:if>
                
                <c:if test="${type != 'company'}">
                    <table id="service-shop-list" class="table table-striped table-bordered table-hover shop-list">
                        <thead>
                            <tr>
                                <th>序号</th>
                                <th>店辅名称</th>
                                <th>门店类型</th>
                                <th>所属服务区</th>
                                <th>店辅地址</th>
                                <th>排序</th>
                                <th>新增时间</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                    </table>
                </c:if>
            </div>
        </div>
    </div>
</div>
<!-- END PAGE CONTENT-->

<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
<div class="modal fade" id="changeOrdModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog form">
        <form id="new-group-form" class="form-horizontal">
            <div class="modal-content">
                <div class="modal-header" style="border-bottom: 0;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>   
                    <h4 class="modal-title">提示</h4>
                </div>

                <div class="modal-body">
                    <div class="scroller" style="height:80px;" data-always-visible="1" data-rail-visible1="1">
                        <div class="form-group">
                            <label class="col-md-2 control-label">排序：<span class="required" aria-required="true"> *</span></label>
                            <div class="col-md-8">
                                <input type="text" id="ord" name="ord" class="form-control mask_decimal" placeholder="输入排序" maxlength="10" />
                            </div>    
                        </div>
                    </div>
                </div>

                <div class="modal-footer"  style="border-top: 0;">
                    <button type="button" class="btn blue" id="btnChangeOrdSubmit"> 确定 </button>
                    <button type="button" class="btn default" data-dismiss="modal"> 取消 </button>
                </div>
            </div>
        </form>            
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->

