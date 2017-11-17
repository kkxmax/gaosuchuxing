<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${order.orderStatus != '已完成'}">
    <c:if test="${order.orderStatus == '待配送'}">
        <div class="navbar navbar-default navbar-fixed-bottom" style="height: 55px;">
            <div class="col-xs-12 init-div-horizontal" style="padding: 0 20px;">
                <button class="btn btn-circle btn-sm col-xs-12 init-div-horizontal btn-green" onclick="onShipping('${order.id}')"
                        style="background-color: #3e6dbb; color: #fff; font-size: 15px; border: 1px solid #3e6dbb; width: 80px; position: absolute; right: 10px; top: 12px;">
                    配送
                </button>
            </div>
        </div>
    </c:if>

    <c:if test="${order.orderStatus != '待配送'}">
        <div class="navbar navbar-default navbar-fixed-bottom" style="height: 55px;">
            <div class="col-xs-12 init-div-horizontal" style="padding: 0 20px;">
                <button class="btn btn-circle btn-sm col-xs-12 init-div-horizontal btn-green" onclick="onDelivery('${order.id}')"
                        style="background-color: #3e6dbb; color: #fff; font-size: 15px; border: 1px solid #3e6dbb; width: 80px; position: absolute; right: 10px; top: 12px;">
                    送达
                </button>
            </div>
        </div>
    </c:if>    
</c:if>
