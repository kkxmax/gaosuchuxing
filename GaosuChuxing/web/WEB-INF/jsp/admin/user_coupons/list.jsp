<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- BEGIN PAGE HEADER-->
<h3 class="page-title">
    优惠券列表
</h3>
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i>
            <a href="<c:url value="/admin/index"/>">首页</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="javascript:;">优惠券管理</a>
        </li>
    </ul>
</div>
<!-- END PAGE HEADER-->

<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet box">
            <div class="portlet-body form">
                <form:form commandName="searchForm" action="userCouponList" cssClass="form-horizontal" method="post">                    
                    <div class="form-group">
                        <div class="col-md-2" align="right">
                            <form:select cssClass="form-control search-key" cssStyle="width:150px;" path="searchKey">
                                <form:option value="couponName">活动名称</form:option>
                                <form:option value="userTelNo">用户手机号</form:option>
                            </form:select>
                        </div>

                        <div class="col-md-6">
                            <form:input type="text" path="keyword" cssClass="form-control" placeholder="输入活动名称"/>
                        </div>
                    </div>
                        
                    <div class="form-group">
                        <label class="col-md-2 control-label">状态</label>

                        <div class="col-md-6">
                            <form:select path="userCouponStatus" cssClass="form-control search-option">
                                <form:option value="">--请选择状态--</form:option>
                                <form:option value="未使用">未使用</form:option>
                                <form:option value="已使用">已使用</form:option>
                                <form:option value="已过期">已过期</form:option>
                            </form:select>
                        </div>
                    </div>    

                    <div class="form-group">
                        <div class="col-md-offset-2 col-md-6">
                            <button type="submit" class="btn yellow"> 查询 <i class="fa fa-search"></i></button>
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
                <table id="usercoupon-list" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>优惠券编号</th>
                            <th>优惠券金额</th>
                            <th>活动名称</th>
                            <th>用户手机号</th>
                            <th>有效期</th>
                            <th>状态</th>
                            <th>增送时间</th>
                            <th>使用时间</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<!-- END PAGE CONTENT-->