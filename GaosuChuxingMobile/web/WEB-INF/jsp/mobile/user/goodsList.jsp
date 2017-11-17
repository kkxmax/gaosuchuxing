<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${goodsList != null}">
    <div style="padding: 10px 10px 0px 10px; font-size: 12px">
        <div style="border-left: solid 2px #3271c0; width: 5px; height: 13px; float: left; margin-top: 1px"></div>
        ${goodsKindName}
    </div>
    <div class="list_row" id="rightBar">
        <c:forEach var="goods" items="${goodsList}">
            <div class="list_row_internal">
                <div class="col-xs-4 init-div" style="padding-top: 5px" onclick="onGoodsModal('${goods.id}')">
                    <div class="row_img">
                        <img src="/${goods.imagePath}">
                    </div>
                </div>
                <div class="col-xs-8 init-div" style="padding-top: 5px">
                    <div class="row init-div title" style="font-size: 12px;">${goods.name}</div>
                    <div class="row init-div des" style="font-size: 9px;">${goods.description}</div>
                    <div class="row init-div">
                        <fmt:formatNumber pattern="#0.00" var="fmtPrice" value="${goods.price}" />
                        <div class="col-xs-6 init-div" style="color: #ff3c64">￥${fmtPrice}</div>
                        <c:if test="${not empty orderTime}">
                            <div class="col-xs-6 init-div cart_oper">
                                <c:choose>
                                    <c:when test="${goods.qty > 0}">
                                        <div class="oper-group" id="btn-group-${goods.id}">
                                            <div class="btn_minus" id="btn-minus-${goods.id}"></div>
                                            <div class="oper_amount" id="oper-amount-${goods.id}">${goods.qty}</div>
                                            <div class="btn_plus" id="btn-plus-${goods.id}"></div>
                                        </div>
                                        <div class="btn_addcart hide" id="btn-addcart-${goods.id}"></div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="oper-group hide" id="btn-group-${goods.id}">
                                            <div class="btn_minus" id="btn-minus-${goods.id}"></div>
                                            <div class="oper_amount" id="oper-amount-${goods.id}">0</div>
                                            <div class="btn_plus" id="btn-plus-${goods.id}"></div>
                                        </div>
                                        <div class="btn_addcart" id="btn-addcart-${goods.id}"></div>
                                    </c:otherwise>  
                                </c:choose>    
                            </div>
                        </c:if>
                        
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</c:if>
