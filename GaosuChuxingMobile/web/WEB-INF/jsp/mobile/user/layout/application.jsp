<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en" class="">
    <!-- BEGIN HEAD -->
    <head>
        <meta charset="utf-8"/>
        <title><tiles:getAsString name="title" />${TITLE}</title>
        <meta name=renderer content=webkit>		
        <meta http-equiv="X-UA-Compatible" content="chrome=1">
        <meta content="width=device-width, initial-scale=1" name="viewport"/>

        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="<c:url value="/assets/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
        <!-- END GLOBAL MANDATORY STYLES -->

        <!-- BEGIN THEME STYLES -->
        <link href="<c:url value="/assets/css/components.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/layout.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/themes/default.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/custom.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/timeline.css"/>" rel="stylesheet" type="text/css"/>
        <!-- END THEME STYLES -->

    </head>
    <!-- END HEAD -->
    
    <!-- BEGIN BODY -->
    <body>
        <!-- BEGIN HEADER -->
        <%--<tiles:insertAttribute name="header"/>--%>
        <!-- END HEADER -->

        <!-- BEGIN CONTENT -->
        <tiles:insertAttribute name="body"/>
        <!-- END CONTENT -->
        
        <input type="hidden" id="get-station-list-url" value="<c:url value="/user/getStationList"/>" />
        <input type="hidden" id="get-marker-image-url" value="<c:url value="/assets/img/index_icon_show@1x.png"/>" />
        <input type="hidden" id="get-start-marker-image-url" value="<c:url value="/assets/img/index_icon_start@1x.png"/>" />
        <input type="hidden" id="get-end-marker-image-url" value="<c:url value="/assets/img/index_icon_destination@1x.png"/>" />
        <input type="hidden" id="get-slider-image-url" value="<c:url value="/assets/img/index2_icon_slider@2x.png"/>" />
        <input type="hidden" id="get-slider-image2-url" value="<c:url value="/assets/img/index2_icon_slider2@2x.png"/>" />
        <input type="hidden" id="get-map-icon-red-url" value="<c:url value="/assets/img/map_icon_red.png"/>" />
        <input type="hidden" id="get-map-icon-grey-url" value="<c:url value="/assets/img/map_icon_grey.png"/>" />
        <input type="hidden" id="get-btn-addcart-icon-url" value="<c:url value="/assets/img/foodshop_btn_addcart@2x.png"/>" />

        <script src="<c:url value="/assets/js/jquery.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/js/bootstrap.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/js/custom/user/application.js"/>" type="text/javascript"></script>
        
        <!-- begin custom scripts -->
        <tiles:importAttribute name="javascripts"/>
        <c:forEach var="script" items="${javascripts}">
            <script src="<c:url value="${script}"/>"></script>
        </c:forEach>
        <!-- end custom scripts -->
    </body> 
    <!-- END BODY -->
</html>
