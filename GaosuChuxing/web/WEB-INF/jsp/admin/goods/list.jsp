<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- BEGIN PAGE HEADER-->
<h3 class="page-title">
    商品列表
</h3>
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i>
            <a href="<c:url value="/admin/index"/>">首页</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="javascript:;">商品管理</a>
        </li>
    </ul>
</div>
<!-- END PAGE HEADER-->

<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet box">
            <div class="portlet-body form">
                <form:form commandName="searchForm" action="goodsList" cssClass="form-horizontal"  method="post">
                    <div class="form-group">
                        <label class="col-md-2 control-label">商品名称</label>

                        <div class="col-md-6">
                            <form:input type="text" path="keyword" cssClass="form-control" placeholder="输入商品名称"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">门店类型</label>

                        <div class="col-md-2">
                            <form:select path="shopKindId" cssClass="form-control">
                                <form:option value="">--选择门店类型--</form:option>
                                <c:forEach var="shopKind" items="${shopKindList}">
                                    <form:option value="${shopKind.id}">${shopKind.name}</form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                        
                        <label class="col-md-1 control-label" style="min-width: 90px;">所属门店</label>
                        
                        <div class="col-md-3 shop-opt">
                            <form:select path="shopId" cssClass="form-control select2me" data-placeholder="--选择所属门店--">
                                <form:option value=""></form:option>
                                <c:if test="${shopList != null}">
                                    <c:forEach var="shop" items="${shopList}">
                                        <form:option value="${shop.id}">${shop.name}</form:option>
                                    </c:forEach>
                                </c:if>
                            </form:select>
                        </div>
                    </div>    
                        
                    <div class="form-group">
                        <div class="col-md-offset-2 col-md-4">
                            <button type="submit" class="btn yellow"> 查询 <i class="fa fa-search"></i></button>
                            <a href="goodsForm/0" class="btn green"> 新增 <i class="fa fa-plus"></i></a>
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
                <table id="goods-list" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>序号</th>
                            <th>商品编号</th>
                            <th>商品名称</th>
                            <th>图片</th>
                            <th>所属门店</th>
                            <th>门店类型</th>
                            <th>商品分类</th>
                            <th>单价</th>
                            <th>状态</th>
                            <th>新增时间</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<!-- END PAGE CONTENT-->