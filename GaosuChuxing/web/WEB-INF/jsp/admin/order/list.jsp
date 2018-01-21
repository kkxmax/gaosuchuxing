<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- BEGIN PAGE HEADER-->
<h3 class="page-title">
    订单管理
</h3>
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i>
            <a href="<c:url value="/admin/index"/>">首页</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="javascript:;">订单管理</a>
        </li>
    </ul>
</div>
<!-- END PAGE HEADER-->

<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet box">
            <div class="portlet-body form">
                <form:form commandName="searchForm" action="orderList" cssClass="form-horizontal" method="post">                    
                    <div class="form-group">
                        <div class="col-md-2" align="right">
                            <form:select cssClass="form-control search-key" cssStyle="width:100px;" path="searchKey">
                                <form:option value="orderNum">订单号</form:option>
                                <form:option value="userName">收货人</form:option>
                                <form:option value="deliverName">配送员</form:option>
                            </form:select>
                        </div>

                        <div class="col-md-6">
                            <form:input type="text" path="keyword" cssClass="form-control" placeholder="输入订单号"/>
                        </div>
                    </div>
                        
                    <div class="form-group">
                        <label class="col-md-2 control-label">支付时间</label>

                        <div class="col-md-3">
                            <div class="input-group date date-picker" data-date-format="yyyy-mm-dd">
                                <form:input type="text" path="from" cssClass="form-control" />
                                <span class="input-group-btn">
                                    <button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
                                </span>
                            </div>
                        </div>

                        <div class="col-md-3">
                            <div class="input-group date date-picker" data-date-format="yyyy-mm-dd">
                                <form:input type="text" path="to" cssClass="form-control" />
                                <span class="input-group-btn">
                                    <button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
                                </span>
                            </div>
                        </div>
                    </div>    
                        
                    <div class="form-group">
                        <label class="col-md-2 control-label">订单状态</label>

                        <div class="col-md-6">
                            <form:select path="orderStatus" cssClass="form-control search-option">
                                <form:option value="">--请选择订单状态--</form:option>
                                <form:option value="待配送">待配送</form:option>
                                <form:option value="配送中">配送中</form:option>
                                <form:option value="已完成">已完成</form:option>
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
                <div class="row">
                    <div style="font-size: 14px; margin-left: 20px; font-weight: 900;">合计: ¥${totalAmount}</div>
                </div>
                <table id="order-list" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>序号</th>
                            <th>订单号</th>
                            <th>收货人</th>
                            <th>联系电话</th>
                            <th>实付金额</th>
                            <th>订单状态</th>
                            <th>配送员</th>
                            <th>支付时间</th>
                            <th>配送时间</th>
                            <th>送达时间</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<!-- END PAGE CONTENT-->