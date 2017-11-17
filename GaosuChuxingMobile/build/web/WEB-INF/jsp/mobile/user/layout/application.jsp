<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en" class="">
    <!-- BEGIN HEAD -->
    <head>
        <meta charset="utf-8"/>
        <title><tiles:getAsString name="title" />${TITLE}</title>
        <meta name=renderer content=webkit />		
        <meta http-equiv="X-UA-Compatible" content="chrome=1" />
        <meta content="width=device-width, initial-scale=1, user-scalable=no" name="viewport"/>
        <meta name="format-detection" content="telephone=yes" />

        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="<c:url value="/assets/plugins/mobiscroll/css/mobiscroll_002.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/plugins/mobiscroll/css/mobiscroll.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/plugins/mobiscroll/css/mobiscroll_003.css"/>" rel="stylesheet" type="text/css"/>
        
        <link href="<c:url value="/assets/plugins/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/plugins/simple-line-icons/simple-line-icons.min.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>        
        <!-- END GLOBAL MANDATORY STYLES -->

        <!-- BEGIN THEME STYLES -->
        <link href="<c:url value="/assets/css/components.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/layout.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/themes/default.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/custom.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/timeline.css"/>" rel="stylesheet" type="text/css"/>
        <!-- END THEME STYLES -->
        
        <link rel="shortcut icon" href="<c:url value="/favicon.ico"/>"/>

    </head>
    <!-- END HEAD -->
    
    <!-- BEGIN BODY -->
    <body>
        <!-- BEGIN HEADER -->
        <%--<tiles:insertAttribute name="header"/>--%>
        <!-- END HEADER -->
        
        <input type="hidden" id="page" value="${page}"/>
        <input type="hidden" id="startLat" value="${startLat}"/>
        <input type="hidden" id="startLng" value="${startLng}"/>
        <input type="hidden" id="endLat" value="${endLat}"/>
        <input type="hidden" id="endLng" value="${endLng}"/>
        <input type="hidden" id="stationId" value="${stationId}"/>
        <input type="hidden" id="backShopId" value="${backShopId}"/>
        <input type="hidden" id="orderPage" value="${orderPage}"/>
        <input type="hidden" id="stationPage" value="${stationPage}"/>
        <input type="hidden" id="submitPage" value="${submitPage}"/>
        <input type="hidden" id="backSearchKey" value="${backSearchKey}"/>
        <input type="hidden" id="historyLength" value="${historyLength}" />

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
        <input type="hidden" id="adv-icon-url" value="<c:url value="/assets/img/adv_icon.png"/>" />
        <input type="hidden" id="share-coupon-url" value="<c:url value="/advertise"/>" />
        <input type="hidden" id="wxauth-url" value="<c:url value="/user/wxAuthorize"/>" />
        <input type="hidden" id="home-url" value="<c:url value="/user/home"/>" />
        <input type="hidden" id="get-badge-url" value="<c:url value="/user/getBadge"/>" />
        <input type="hidden" id="change-page-url" value="<c:url value="/user/changePage"/>" />
        <input type="hidden" id="takeadv-url" value="<c:url value="/user/takeActivityAdv"/>" />
        <input type="hidden" id="notification-url" value="<c:url value="/user/notification"/>" />
        <input type="hidden" id="profile-url" value="<c:url value="/user/profile"/>" />
        <input type="hidden" id="share-img-url" value="<c:url value="/assets/img/round_logo.jpg"/>" />
        <input type="hidden" id="wxconfig-url" value="<c:url value="/user/wxConfigInfo"/>" />
                
        <!-- Modal -->
        <div class="modal fade bs-modal-sm" id="confirm-yes-no-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm" style="margin-top: 45%;">
                <div class="modal-content">
                    <div class="modal-header" style="border-bottom: 0;">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h3 class="modal-title" style="color: #3598dc;"><i class="fa fa-question-circle" style="font-size: 26px;"></i></h3>
                    </div>
                    <div class="modal-body">
                        <h4 class="modal-title" id="confirm-yes-no-msg" style="font-family: Microsoft YaHei;"></h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn blue" id="button-yes"> 确认 </button>
                        <button type="button" class="btn default" data-dismiss="modal"> 取消 </button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
        
        <div class="modal fade bs-modal-sm" id="confirm-message-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm" style="margin-top: 45%;">
                <div class="modal-content">
                    <div class="modal-header" style="border-bottom: 0;">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h3 class="modal-title" style="color: #dfba49;"><i class="fa fa-warning" style="font-size: 26px;"></i></h3>
                    </div>
                    <div class="modal-body">
                        <h4 class="modal-title" id="confirm-message-msg" style="font-family: Microsoft YaHei;"></h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn blue" data-dismiss="modal"> 是 </button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->
        
        <div class="modal fade bs-modal-sm" id="information-message-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm" style="margin-top: 45%;">
                <div class="modal-content">
                    <div class="modal-header" style="border-bottom: 0;">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h3 class="modal-title" style="color: #3598dc;"><i class="fa fa-info-circle" style="font-size: 26px;"></i></h3>
                    </div>
                    <div class="modal-body">
                        <h4 class="modal-title" id="information-message-msg" style="font-family: Microsoft YaHei;"></h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn blue" data-dismiss="modal"> 是 </button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->
        
        <div class="modal fade bs-modal-sm" id="loading-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog modal-sm" style="margin-top: 55%;">
                <div class="modal-content">
                    <div class="modal-body">
                        <img src="<c:url value="/assets/img/loading-spinner-grey.gif"/>" alt class="loading">
                        <span style="font-family: Microsoft YaHei; font-size: 14px; vertical-align: middle;">&nbsp;&nbsp;加载中.....</span>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->

        <script src="<c:url value="/assets/js/jquery.min.js"/>" type="text/javascript"></script>        
        <script src="<c:url value="/assets/plugins/mobiscroll/js/mobiscroll_002.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/plugins/mobiscroll/js/mobiscroll_004.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/plugins/mobiscroll/js/mobiscroll.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/plugins/mobiscroll/js/mobiscroll_003.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/plugins/mobiscroll/js/mobiscroll_005.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/js/jquery.number.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/js/bootstrap.min.js"/>" type="text/javascript"></script>
        <script src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js" type="text/javascript"></script>
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
