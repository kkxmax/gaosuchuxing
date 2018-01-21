<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="unlimitedVal" value="${0}"/>
<c:set var="fullVal" value="${0}"/>
<div class="table-scrollable">
    <table class="table table-striped table-bordered table-hover" id="${type}Detail-list">
        <thead>
            <c:if test="${type == 'full'}">
                <th>订单金额满减</th>
            </c:if>
            <th>优惠券金额（元）</th>
            <th>数量</th>
            <th>中奖率（%）</th>
            <th></th>
        </thead>
        <tbody>
            <c:if test="${tmpList != null}">
                <c:if test="${type == 'full'}">
                    <c:set var="fullVal" value="${fn:length(tmpList)}"/>
                </c:if>
                
                <c:if test="${type == 'unlimited'}">
                    <c:set var="unlimitedVal" value="${fn:length(tmpList)}"/>
                </c:if>
                
                <c:forEach var="tmp" items="${tmpList}">
                    <tr>
                        <c:if test="${type == 'full'}">
                            <td><input type="text" class="form-control input-small" readonly value="${tmp.fullAmount}" /></td>
                        </c:if>
                        <td><input type="text" class="form-control input-small" readonly value="${tmp.couponAmount}" /></td>
                        <td><input type="text" class="form-control input-small" readonly value="${tmp.qty}" /></td>
                        <fmt:formatNumber type="NUMBER" var="fmtRate" value="${tmp.rate}" maxFractionDigits="0" />
                        <td><input type="text" class="form-control input-small" readonly value="${fmtRate}" id="rate_${tmp.id}" /></td>
                        <td><a href="javascript:;" class="btn btn-xs default" style="margin-top: 7px;" onclick="deleteDetail('${tmp.id}', '${type}')"> 删除 <i class="fa fa-times"></i></a></td>
                    </tr>
                </c:forEach>
            </c:if>
        </tbody>
        <tfoot>
            <tr>
                <c:if test="${type == 'full'}">
                    <td><input type="text" id="${type}FullAmount" class="mask_decimal form-control input-small" placeholder="输入订单金额满减" maxlength="6" /></td>
                </c:if>
                <td><input type="text" id="${type}CouponAmount" class="mask_decimal form-control input-small ${type}Calc" placeholder="输入优惠券金额" maxlength="6" /></td>
                <td><input type="text" id="${type}Qty" class="mask_integer form-control input-small ${type}Calc" placeholder="输入数量" maxlength="6" /></td>
                <td><input type="text" id="${type}Rate" class="form-control input-small" readonly /></td>
                <td><a href="javascript:;" class="btn btn-xs green" style="margin-top: 7px;" onclick="addDetail('${type}')"> 添加 <i class="fa fa-plus"></i></a></td>
            </tr>
        </tfoot>
    </table>
</div>
            
<c:if test="${type == 'unlimited'}">
    <input id="unlimitedVal" type="hidden" value="${unlimitedVal}" />
</c:if>

<c:if test="${type == 'full'}">
    <input id="fullVal" type="hidden" value="${fullVal}" />            
</c:if>
