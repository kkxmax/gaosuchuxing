<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="zh-CN" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="zh-CN" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-CN" class="no-js">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->
    <head>
        <meta charset="utf-8"/>
        <title><tiles:getAsString name="title" /></title>
        <meta name=renderer content=webkit>		
        <meta http-equiv="X-UA-Compatible" content="chrome=1">
        <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
        <meta http-equiv="Content-type" content="text/html; charset=utf-8">
        <meta content="" name="description"/>
        <meta content="" name="author"/>
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="<c:url value="/assets/global/plugins/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/global/plugins/simple-line-icons/simple-line-icons.min.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/global/plugins/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/global/plugins/uniform/css/uniform.default.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css"/>" rel="stylesheet" type="text/css"/>
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN PAGE LEVEL STYLES -->
        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/global/plugins/bootstrap-select/bootstrap-select.min.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/global/plugins/jquery-multi-select/css/multi-select.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/global/plugins/select2/select2.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/global/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/global/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/global/plugins/bootstrap-datepicker/css/datepicker.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/global/plugins/clockface/css/clockface.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/global/plugins/bootstrap-datepicker/css/datepicker3.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/global/plugins/bootstrap-colorpicker/css/colorpicker.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/global/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/global/plugins/icheck/skins/all.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/global/plugins/fancybox/source/jquery.fancybox.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/assets/admin/pages/css/portfolio.css"/>"/>
        <!-- BEGIN PAGE LEVEL STYLES -->
        <!-- BEGIN THEME STYLES -->
        <link href="<c:url value="/assets/global/css/components.css"/>" id="style_components" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/global/css/plugins.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/admin/layout/css/layout.css"/>" rel="stylesheet" type="text/css"/>
        <link id="style_color" href="<c:url value="/assets/admin/layout/css/themes/darkblue.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/admin/layout/css/custom.css"/>" rel="stylesheet" type="text/css"/>
        <!-- END THEME STYLES -->
        <link rel="shortcut icon" href="<c:url value="/favicon.ico"/>"/>
    </head>
    <!-- END HEAD -->
    
    <body class="page-header-fixed page-quick-sidebar-over-content">
        <!-- BEGIN HEADER -->
        <tiles:insertAttribute name="header"/>      
        <!-- END HEADER -->
        
        <div class="clearfix"></div>                
        
        <!-- BEGIN CONTAINER -->
        <div class="page-container">

            <!-- BEGIN SIDEBAR -->
            <tiles:insertAttribute name="side_menu"/>
            <!-- END SIDEBAR -->
            
            <!-- BEGIN CONTENT -->
            <div class="page-content-wrapper">
                <!-- BEGIN PAGE CONTENT -->
                <div class="page-content">
                    <!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
                    <div class="modal fade bs-modal-sm" id="confirm-yes-no-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-sm">
                            <div class="modal-content">
                                <div class="modal-header" style="border-bottom: 0;">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                    <h3 class="modal-title" style="color: #3598dc;"><i class="fa fa-question-circle" style="font-size: 26px;"></i></h3>
                                </div>
                                <div class="modal-body">
                                    <h4 class="modal-title" id="confirm-yes-no-msg" style="font-family: Microsoft YaHei;"></h4>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn blue" id="button-yes"> 是 </button>
                                    <button type="button" class="btn default" data-dismiss="modal"> 否 </button>
                                </div>
                            </div>
                            <!-- /.modal-content -->
                        </div>
                        <!-- /.modal-dialog -->
                    </div>
                    <!-- /.modal -->
                    <!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
                               
                    <!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
                    <div class="modal fade bs-modal-sm" id="confirm-message-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-sm">
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
                    
                    <!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
                    <div class="modal fade bs-modal-sm" id="information-message-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-sm">
                            <div class="modal-content">
                                <div class="modal-header" style="border-bottom: 0;">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                    <h3 class="modal-title" style="color: #45B6AF;"><i class="fa fa-info-circle" style="font-size: 26px;"></i></h3>
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

                    <!-- CONTENT BODY -->
                    <tiles:insertAttribute name="body"/>
                </div>
                <!-- END PAGE CONTENT -->
            </div>
            <!-- END CONTENT -->
         </div>
         <!-- END CONTAINER -->
             
        <!-- BEGIN FOOTER -->
        <tiles:insertAttribute name="footer"/>
        <!-- END FOOTER -->
        
        <!-- BEGIN HIDDEN FEILED -->
        <input type="hidden" id="validate-manager-id-url" value="<c:url value="/admin/validateManagerId"/>" />
        <input type="hidden" id="delete-manager-url" value="<c:url value="/admin/deleteManager"/>" />
        <input type="hidden" id="validate-password-url" value="<c:url value="/admin/validatePassword"/>" />
        <input type="hidden" id="validate-deliver-id-url" value="<c:url value="/admin/validateDeliverId"/>" />
        <input type="hidden" id="validate-deliver-name-url" value="<c:url value="/admin/validateDeliverName"/>" />
        <input type="hidden" id="validate-manager-name-url" value="<c:url value="/admin/validateManagerName"/>" />
        <input type="hidden" id="get-district-list-url" value="<c:url value="/admin/getDistrictList"/>" />
        <input type="hidden" id="validate-station-name-url" value="<c:url value="/admin/validateStationName"/>" />
        <input type="hidden" id="validate-shop-name-url" value="<c:url value="/admin/validateShopName"/>" />
        <input type="hidden" id="get-shop-list-url" value="<c:url value="/admin/getShopList"/>" />
        <input type="hidden" id="delete-shop-url" value="<c:url value="/admin/deleteShop"/>" />
        <input type="hidden" id="shop-form-url" value="<c:url value="/admin/shopForm"/>" />
        <input type="hidden" id="get-shop-list-by-shop-kind-url" value="<c:url value="/admin/getShopListByShopKind"/>" />
        <input type="hidden" id="get-goods-kind-list-by-shop-kind-url" value="<c:url value="/admin/getGoodsKindListByShopKind"/>" />
        <input type="hidden" id="validate-goods-name-url" value="<c:url value="/admin/validateGoodsName"/>" />
        <input type="hidden" id="validate-activity-name-url" value="<c:url value="/admin/validateActivityName"/>" />
        <input type="hidden" id="get-tmp-activity-detail-url" value="<c:url value="/admin/getTmpActivityDetail"/>" />
        <input type="hidden" id="add-tmp-activity-detail-url" value="<c:url value="/admin/addTmpActivityDetail"/>" />
        <input type="hidden" id="delete-tmp-activity-detail-url" value="<c:url value="/admin/deleteTmpActivityDetail"/>" />
        <input type="hidden" id="update-tmp-activity-detail-url" value="<c:url value="/admin/updateTmpActivityDetail"/>" />
        <input type="hidden" id="get-activity-amount-url" value="<c:url value="/admin/getActivityAmount"/>" />
        <input type="hidden" id="get-coupon-qty-url" value="<c:url value="/admin/getCouponQty"/>" />
        <input type="hidden" id="get-station-url" value="<c:url value="/admin/getStation"/>" />
        <input type="hidden" id="check-feedback-url" value="<c:url value="/admin/checkFeedback"/>" />
        <!-- END HIDDEN FEILED -->
        
        <!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
        <!-- BEGIN CORE PLUGINS -->
        <!--[if lt IE 9]>
        <script src="<c:url value="/assets/global/plugins/respond.min.js"/>"></script>
        <script src="<c:url value="/assets/global/plugins/excanvas.min.js"/>"></script> 
        <![endif]-->
        <script src="<c:url value="/assets/global/plugins/jquery.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/global/plugins/jquery-migrate.min.js"/>" type="text/javascript"></script>
        <!-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
        <script src="<c:url value="/assets/global/plugins/jquery-ui/jquery-ui.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/global/plugins/bootstrap/js/bootstrap.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/global/plugins/jquery.blockui.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/global/plugins/jquery.cokie.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/global/plugins/uniform/jquery.uniform.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js"/>" type="text/javascript"></script>        
        <!-- END CORE PLUGINS -->
        
        <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/bootstrap-select/bootstrap-select.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/jquery-multi-select/js/jquery.multi-select.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/select2/select2.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/datatables/media/js/jquery.dataTables.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"/>"></script>        
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/jquery-validation/js/additional-methods.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/bootstrap-wysihtml5/wysihtml5-0.3.0.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/ckeditor/ckeditor.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/bootstrap-markdown/js/bootstrap-markdown.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/bootstrap-markdown/lib/markdown.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/clockface/js/clockface.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/bootstrap-daterangepicker/moment.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/icheck/icheck.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/jquery-mixitup/jquery.mixitup.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/fancybox/source/jquery.fancybox.pack.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/jquery.pulsate.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/jquery-bootpag/jquery.bootpag.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/plugins/holder.js"/>"></script>
        <!-- END PAGE LEVEL SCRIPTS -->
        
        <script type="text/javascript" src="<c:url value="/assets/global/scripts/metronic.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/admin/layout/scripts/layout.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/admin/layout/scripts/quick-sidebar.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/admin/layout/scripts/demo.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/admin/pages/scripts/table-managed.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/admin/pages/scripts/form-validation.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/admin/pages/scripts/components-pickers.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/admin/pages/scripts/form-icheck.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/admin/pages/scripts/components-dropdowns.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/admin/pages/scripts/portfolio.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/global/scripts/md5.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/assets/admin/layout/scripts/application.js"/>"></script>
        
        <script>
            jQuery(document).ready(function() {    
               Metronic.init(); // init metronic core components
               Layout.init(); // init current layout
               QuickSidebar.init(); // init quick sidebar
               Demo.init(); // init demo features
               FormiCheck.init(); // init page demo
               TableManaged.init(); 
//               FormValidation.init();
               ComponentsPickers.init();
               ComponentsDropdowns.init();
               Portfolio.init();
            });
        </script>
                
        <!-- begin custom scripts -->
        <tiles:importAttribute name="javascripts"/>
        <c:forEach var="script" items="${javascripts}">
            <script src="<c:url value="${script}"/>"></script>
        </c:forEach>
        <!-- end custom scripts -->
        
        <!-- END JAVASCRIPTS -->
    </body>
    <!-- END BODY -->
</html>
