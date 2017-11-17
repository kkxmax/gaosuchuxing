<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- BEGIN CONTENT -->
<div class="delivery container-fluid container-lf-space main-body init-div-horizontal" style="margin-bottom: 0;">
    <!--<img class="img-shadow" src="/uploads/img/bg_map.png" style="width: 100%;" />-->
    <div id="map" style="position: fixed; width: 100%; height: 100%;"></div>

    <div id="animate_part" class="row init-div-horizontal" style="position: absolute; bottom: 0; width: 100%; height: 30%; padding-top: 20px">
        <!--<img id="arrow_up" src="../../assets/img/index2_icon_slider2@2x.png" style="position: absolute; width: 40px; right: 22px; top: -20px;" />-->
        <img id="arrow_up" pos="bottom" src="<c:url value="/assets/img/index2_icon_slider@2x.png"/>" style="width: 40px; float: right; margin-right: 20px; position: absolute; top: 0px; right: 0px; z-index: 1000" />
        <div class="col-xs-12 init-div-horizontal map-bg" style="height: 100%; overflow-y: auto;">
            <div class="row init-div-horizontal margin-top-20">
                <div style="text-align: right; margin-right: 20px; margin-top: 10px">
                    <span style="font-size: 12px" id="time-msg"></span>
                    <br>
                    <span style="font-size: 12px" id="distance-msg"></span>
                </div>
                <div style="margin-left: 20px; margin-right: 10px;" class="hide" id="routeNotify">途径<span style="color: #f82545" id="station-total-count"></span>个高速服务区，已开放服务区<span style="color: #f82545" id="station-count"></span>个</div>
            </div>
            <div class="row init-div-horizontal" style="margin: 10px 20px; border: 1px dashed #4270c5;"></div>

            <div class="timeline" id="timeline"></div>
        </div>
    </div>

</div>
<!-- END CONTENT -->

<input type="hidden" id="start-lng" value="${startLng}">
<input type="hidden" id="start-lat" value="${startLat}">
<input type="hidden" id="end-lng" value="${endLng}">
<input type="hidden" id="end-lat" value="${endLat}">
<input type="hidden" id="start-address" value="${startAddress}">
<input type="hidden" id="end-address" value="${endAddress}">