<%@page contentType="text/html" pageEncoding="UTF-8"%><%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<select id="districtId" name="districtId" class="form-control">
    <c:forEach var="district" items="${districts}">
        <option value="${district.id}" data-code="${district.code}">${district.name}</option>
    </c:forEach>
</select>
