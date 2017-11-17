<%@page contentType="text/html" pageEncoding="UTF-8"%><%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<select id="shopId" name="shopId" class="form-control validation-control select2me">
    <c:forEach var="shop" items="${shopList}">
        <option value="${shop.id}">${shop.name}</option>
    </c:forEach>
</select>

