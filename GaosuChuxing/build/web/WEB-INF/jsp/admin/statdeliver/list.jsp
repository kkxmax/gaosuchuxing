<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- BEGIN PAGE HEADER-->
<h3 class="page-title">
    配送员统计列表
</h3>
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i>
            <a href="<c:url value="/admin/index"/>">首页</a>
            <i class="fa fa-angle-right"></i>
        </li>
        <li>
            <a href="javascript:;">配送员统计</a>
        </li>
    </ul>
</div>
<!-- END PAGE HEADER-->

<!-- BEGIN PAGE CONTENT-->
<div class="row">
    <div class="col-md-12">
        <div class="portlet box">
            <div class="portlet-body form">
                <form:form commandName="searchForm" action="statDeliver" cssClass="form-horizontal" method="post">                    
                    <div class="form-group">
                        <label class="col-md-2 control-label">配送员</label>

                        <div class="col-md-6">
                            <form:input type="text" path="keyword" cssClass="form-control" placeholder="输入配送员"/>
                        </div>
                    </div>
                        
                    <div class="form-group">
                        <label class="col-md-2 control-label">送达时间</label>

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
                <table id="statdeliver-list" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>序号</th>
                            <th>账号</th>
                            <th>姓名</th>
                            <th>配送数量</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<!-- END PAGE CONTENT-->