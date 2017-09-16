<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- BEGIN PAGE HEADER-->
<h3 class="page-title">
    服务区列表
</h3>
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i>
            <a href="<c:url value="/admin/index"/>">首页</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="javascript:;">服务区管理</a>
        </li>
    </ul>
</div>
<!-- END PAGE HEADER-->

<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet box">
            <div class="portlet-body form">
                <form:form commandName="searchForm" action="stationList" cssClass="form-horizontal"  method="post">
                    <div class="form-group">
                        <label class="col-md-2 control-label">关键词</label>

                        <div class="col-md-4">
                            <form:input type="text" path="keyword" cssClass="form-control" placeholder="输入服务区名称, 配送员..."/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">城市</label>

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
                        <label class="col-md-2 control-label">服务区状态</label>

                        <div class="col-md-4">
                            <form:select path="status" cssClass="form-control search-option">
                                <form:option value="">--请选择服务区状态--</form:option>
                                <form:option value="未开放">未开放</form:option>
                                <form:option value="已开放">已开放</form:option>
                            </form:select>
                        </div>
                    </div>    

                    <div class="form-group">
                        <div class="col-md-offset-2 col-md-4">
                            <button type="submit" class="btn yellow"> 查询 <i class="fa fa-search"></i></button>
                            <a href="stationForm/0" class="btn green"> 新增 <i class="fa fa-plus"></i></a>
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
                <table id="station-list" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>序号</th>
                            <th>服务区名称</th>
                            <th>图片</th>
                            <th>城市</th>
                            <th>配送员</th>
                            <th>服务状态</th>
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