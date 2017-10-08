<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="delivery container-fluid container-lf-space main-body init-div-horizontal">
    <div class="serv_detail">
        <div class="top_bg">
            <img src="/${station.imagePath}">
        </div>
        
        <c:forEach var="shop" items="${shops}">
            <div class="list_row">
                <div class="list_row_internal">
                    <div class="col-xs-5 init-div">
                        <div class="row_img">
                            <img src="/${shop.imagePath}">
                        </div>
                    </div>
                    <div class="col-xs-7 init-div">
                        <p class="title">${shop.name}</p>
                        <p class="des">${shop.description}</p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
