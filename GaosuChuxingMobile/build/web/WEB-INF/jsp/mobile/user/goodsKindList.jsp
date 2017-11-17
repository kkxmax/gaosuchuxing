<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<input type="hidden" id="active-goodskind" value="${activeGoodsKind}" />
<c:set var="index" value="${0}" />       
<c:forEach var="goodsKind" items="${goodsKinds}">
    <c:if test="${index == 0}">
        <div class="list_row goods-kind-tab active" id="tab_${goodsKind.id}">     
        <c:set var="activeGoodsKind" value="${goodsKind.id}" />    
    </c:if>

    <c:if test="${index != 0}">
        <div class="list_row goods-kind-tab" id="tab_${goodsKind.id}">
    </c:if>

        <div class="list_row_internal">
            ${goodsKind.name}
            <c:choose>
                <c:when test="${goodsKind.qty > 0}">
                    <span class="badge" id="goodskind-badge-${goodsKind.id}">${goodsKind.qty}</span>
                </c:when>
                <c:otherwise>
                    <span class="badge hide" id="goodskind-badge-${goodsKind.id}">0</span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <c:set var="index" value="${index + 1}" />        
</c:forEach>