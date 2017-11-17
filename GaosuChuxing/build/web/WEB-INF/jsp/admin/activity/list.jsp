<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- BEGIN PAGE HEADER-->
<h3 class="page-title">
    活动管理
</h3>
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i>
            <a href="<c:url value="/admin/index"/>">首页</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="javascript:;">活动管理</a>
        </li>
    </ul>
</div>
<!-- END PAGE HEADER-->

<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet box">
            <div class="portlet-body form">
                <form:form commandName="searchForm" action="activityList" cssClass="form-horizontal" method="post">
                    <div class="form-group">
                        <label class="col-md-2 control-label">活动名称</label>

                        <div class="col-md-4">
                            <form:input type="text" path="keyword" cssClass="form-control" placeholder="输入活动名称"/>
                        </div>
                    </div>
                        
                    <div class="form-group">
                        <label class="col-md-2 control-label">状态</label>

                        <div class="col-md-4">
                            <form:select path="status" cssClass="form-control search-option">
                                <form:option value="">--请选择状态--</form:option>
                                <form:option value="未开始">未开始</form:option>
                                <form:option value="进行中">进行中</form:option>
                                <form:option value="已结束">已结束</form:option>
                            </form:select>
                        </div>
                    </div>    

                    <div class="form-group">
                        <div class="col-md-offset-2 col-md-4">
                            <button type="submit" class="btn yellow"> 查询 <i class="fa fa-search"></i></button>
                            <a href="activityForm/0" class="btn green"> 新增 <i class="fa fa-plus"></i></a>
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
                <table id="activity-list" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>活动编号</th>
                            <th>活动名称</th>
                            <th>活动金额</th>
                            <th>优惠券数量</th>
                            <th>活动时间</th>
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