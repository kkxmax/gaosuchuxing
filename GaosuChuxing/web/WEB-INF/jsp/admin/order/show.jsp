<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- BEGIN PAGE HEADER-->
<h3 class="page-title">
    ${title}
    <small>
        ${small}         
        <c:if test="${orderForm.orderStatus == '待配送'}">
            <span class="label label-warning">${orderForm.orderStatus}</span>
        </c:if>
        
        <c:if test="${orderForm.orderStatus == '配送中'}">
            <span class="label label-info">${orderForm.orderStatus}</span>
        </c:if>
            
        <c:if test="${orderForm.orderStatus == '已完成'}">
            <span class="label label-success">${orderForm.orderStatus}</span>
        </c:if>    
    </small>
    
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
                <form:form commandName="orderForm" cssClass="form-horizontal">
                    <div class="form-body">                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">订单号：</label>

                            <div class="col-md-4">
                                <label class="form-control">${orderForm.orderNum}</label>
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">餐饮店名称：</label>

                            <div class="col-md-4">
                                <label class="form-control">${orderForm.shopName}</label>
                            </div>
                        </div>
                            
                        <div class="form-group">   
                            <label class="col-md-1 control-label"></label>
                            
                            <div class="col-md-10">
                                <div class="table-scrollable">
                                    <table class="table table-striped table-bordered table-hover">
                                        <thead>
                                            <th>商品编号</th>
                                            <th>商品名称</th>
                                            <th>图片</th>
                                            <th>单价</th>
                                            <th>数量</th>
                                            <th>订单金额</th>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="orderDetail" items="${orderDetailList}">
                                                <tr>
                                                    <td>${orderDetail.goodsNum}</td>
                                                    <td>${orderDetail.goodsName}</td>
                                                    <td><img style='height: 40px; max-width: 90px;' src='${orderDetail.goodsImagePath}'></img></td>
                                                    <td>${orderDetail.price}</td>
                                                    <td>${orderDetail.price}</td>
                                                    <td><fmt:formatNumber pattern="#0.00" value="${orderDetail.price * orderDetail.price}" /></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>    
                            </div>
                        </div>
                            
                        <h3 class="form-selection">&nbsp;</h3>    
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">商品数量：</label>

                            <div class="col-md-4">
                                <label class="form-control">${totalQty}</label>
                            </div>
                        </div> 
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">合计：</label>

                            <div class="col-md-4">
                                <label class="form-control">${orderDetailAmount}</label>
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">优惠券：</label>

                            <div class="col-md-4">
                                <label class="form-control" style="color: red;">-${orderCouponAmount}</label>
                            </div>
                        </div> 
                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">实付金额：</label>

                            <div class="col-md-4">
                                <label class="form-control">${totalAmount}</label>
                            </div>
                        </div>
                            
                        <h3 class="form-selection">&nbsp;</h3>    
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">买家留言：</label>

                            <div class="col-md-4">
                                <label class="form-control">${orderForm.description}</label>
                            </div>
                        </div>
                            
                        <h3 class="form-selection">&nbsp;</h3>    
                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">收货地址：</label>

                            <div class="col-md-4">
                                <label class="form-control">${orderForm.shopAddress} ${orderForm.stationName}</label>
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">收货人：</label>

                            <div class="col-md-4">
                                <label class="form-control">${orderForm.userName}</label>
                            </div>
                        </div>   
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">联系电话：</label>

                            <div class="col-md-4">
                                <label class="form-control">${orderForm.userTelNo}</label>
                            </div>
                        </div>    
                            
                        <h3 class="form-selection">&nbsp;</h3>    
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">配送员：</label>

                            <div class="col-md-4">
                                <label class="form-control">${orderForm.deliverName}</label>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">联系电话：</label>

                            <div class="col-md-4">
                                <label class="form-control">${orderForm.deliverTelNo}</label>
                            </div>
                        </div>
                            
                        <h3 class="form-selection">&nbsp;</h3>    
                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">支付时间：</label>

                            <div class="col-md-4">
                                <label class="form-control">
                                    <c:if test="${orderForm.payDate != null}">
                                        <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${orderForm.payDate}" />
                                    </c:if>
                                </label>
                            </div>
                        </div>
                            
                        <div class="form-group">
                            <label class="col-md-2 control-label">配送时间：</label>

                            <div class="col-md-4">
                                <label class="form-control">
                                    <c:if test="${orderForm.payDate != null}">
                                        <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${orderForm.shippingDate}" />
                                    </c:if>
                                </label>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-md-2 control-label">送达时间：</label>

                            <div class="col-md-4">
                                <label class="form-control">
                                    <c:if test="${orderForm.payDate != null}">
                                        <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${orderForm.deliveryDate}" />
                                    </c:if>
                                </label>
                            </div>
                        </div>    
                            
                    </div>
                    
                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-offset-2 col-md-5">
                                <a href="<c:url value="/admin/orderList"/>" class="btn default"> 取消 <i class="fa fa-repeat"></i></a>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>    
</div>