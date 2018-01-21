<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en" class="">
    <!-- BEGIN HEAD -->
    <head>
        <meta charset="utf-8"/>
        <title><tiles:getAsString name="title" />${TITLE}</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1, user-scalable=no" name="viewport"/>

        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="<c:url value="/assets/plugins/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/plugins/simple-line-icons/simple-line-icons.min.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
        <!-- END GLOBAL MANDATORY STYLES -->

        <!-- BEGIN THEME STYLES -->
        <link href="<c:url value="/assets/css/components.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/layout.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/themes/default.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/custom.css"/>" rel="stylesheet" type="text/css"/>
        <!-- END THEME STYLES -->
        
        <link rel="shortcut icon" href="<c:url value="/favicon.ico"/>"/>
    </head>
    <!-- END HEAD -->
    
    <!-- BEGIN BODY -->
    <body style="background-color: #f5f5f5;">
        
        <!-- BEGIN CONTENT -->
        <tiles:insertAttribute name="body"/>
        <!-- END CONTENT -->
        
        <input type="hidden" id="orderPage" value="${orderPage}"/>
        
        <input type="hidden" id="logout-url" value="<c:url value="/deliver/logout"/>" />
        <input type="hidden" id="takeadv-url" value="<c:url value="/user/takeActivityAdv"/>" />
        <input type="hidden" id="notification-url" value="<c:url value="/deliver/notification"/>" />
        <input type="hidden" id="profile-url" value="<c:url value="/deliver/profile"/>" />
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
        
        <div class="modal fade bs-modal-sm" id="forceLogout-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog modal-sm" style="margin-top: 45%">
                <div class="modal-content">
                    <div class="modal-header" style="border-bottom: 0;">
                        <h3 class="modal-title" style="font-family: Microsoft YaHei; color: #F3565D;"><i class="fa fa-warning" style="font-size: 26px;"></i> 安全警告</h3>
                    </div>
                    <div class="modal-body">
                        <h4 class="modal-title" style="font-family: Microsoft YaHei;">该账号在其他设备上登录</h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn blue" id="btnForceLogout"> 是 </button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->
        
        <script src="<c:url value="/assets/js/jquery.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/js/bootstrap.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/js/custom/deliver/application.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/js/md5.js"/>" type="text/javascript"></script>
        
        <!-- begin custom scripts -->
        <tiles:importAttribute name="javascripts"/>
        <c:forEach var="script" items="${javascripts}">
            <script src="<c:url value="${script}"/>"></script>
        </c:forEach>
        <!-- end custom scripts -->
    </body> 
    <!-- END BODY -->
</html>
