<%@page contentType="text/html" pageEncoding="UTF-8"%><%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<select id="goodsKindId" name="goodsKindId" class="form-control">
    <c:forEach var="goodsKind" items="${goodsKindList}">
        <option value="${goodsKind.id}">${goodsKind.name}</option>
    </c:forEach>
</select>

