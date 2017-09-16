<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-CN">
<!--<![endif]-->
    <!-- BEGIN HEAD -->
    <head>
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
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN PAGE LEVEL STYLES -->
        <link href="<c:url value="/assets/global/plugins/select2/select2.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/admin/pages/css/login3.css"/>" rel="stylesheet" type="text/css"/>
        <!-- END PAGE LEVEL SCRIPTS -->
        <!-- BEGIN THEME STYLES -->
        <link href="<c:url value="/assets/global/css/components.css"/>" id="style_components" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/global/css/plugins.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/admin/layout/css/layout.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/admin/layout/css/themes/darkblue.css"/>" rel="stylesheet" type="text/css" id="style_color"/>
        <link href="<c:url value="/assets/admin/layout/css/custom.css"/>" rel="stylesheet" type="text/css"/>
        <!-- END THEME STYLES -->
        <link rel="shortcut icon" href="<c:url value="/favicon.ico"/>"/>
    </head>
    <!-- END HEAD -->
    
    <!-- BEGIN BODY -->
    <body class="login">
        <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
        <div class="menu-toggler sidebar-toggler">
        </div>
        <!-- END SIDEBAR TOGGLER BUTTON -->
        <!-- BEGIN LOGO -->
        <div class="logo">
            <a href="<c:url value="/admin/index"/>">
                <img src="<c:url value="/assets/admin/pages/img/logo.png"/>" alt=""/>
            </a>
        </div>
        <!-- END LOGO -->
        
        <!-- BEGIN LOGIN -->
        <div class="content">
            <!-- BEGIN LOGIN FORM -->
            <!--<form class="login-form" action="index.html" method="post">-->
            <form:form commandName="loginForm" cssClass="login-form" action="login" method="post">
                <h3 class="form-title" style="font-family: Microsoft YaHei; color: #2355d2; text-align: center;">登入到您的帐户</h3>
		<div class="alert alert-danger display-hide" style="padding: 5px;">
                    <button class="close" data-close="alert" style="margin-top: 5px;"></button>
                    <span id="message_tag" style="font-family: Microsoft YaHei;">
                        账号或密码不能为空
                    </span>
		</div>
		<div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">账号</label>
                    <div class="input-icon">
                        <i class="fa fa-user"></i>
                        <!--<input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Username" name="username"/>-->
                        <form:input cssClass="form-control placeholder-no-fix" type="text" autoComplete="Off" placeholder="账号" path="userId"/>
                    </div>
		</div>
		<div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9">密码</label>
                    <div class="input-icon">
                        <i class="fa fa-lock"></i>
                        <!--<input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="Password" name="password"/>-->
                        <form:input cssClass="form-control placeholder-no-fix" type="password" autoComplete="Off" placeholder="密码" path="password"/>
                    </div>
		</div>
		<div class="form-actions">
                    <label class="checkbox">
                    <input type="checkbox" name="remember" value="1"/> 记住密码 </label>
                    <button type="submit" class="btn green-haze pull-right">
                    登录 <i class="m-icon-swapright m-icon-white"></i>
                    </button>
		</div>
            </form:form>
            <!-- END LOGIN FORM -->
        </div>
        <div class="copyright">
            2017 © HANAYONGHE
        </div>
        <!-- END LOGIN -->
        <!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
        <!-- BEGIN CORE PLUGINS -->
        <!--[if lt IE 9]>
        <script src="<c:url value="/assets/global/plugins/respond.min.js"/>"></script>
        <script src="<c:url value="/assets/global/plugins/excanvas.min.js"/>"></script> 
        <![endif]-->
        <script src="<c:url value="/assets/global/plugins/jquery.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/global/plugins/jquery-migrate.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/global/plugins/bootstrap/js/bootstrap.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/global/plugins/jquery.blockui.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/global/plugins/jquery.cokie.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/global/plugins/uniform/jquery.uniform.min.js"/>" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <script src="<c:url value="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js"/>" type="text/javascript"></script>
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <script src="<c:url value="/assets/global/scripts/metronic.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/admin/layout/scripts/layout.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/admin/layout/scripts/demo.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/admin/pages/scripts/login.js"/>" type="text/javascript"></script>
        <!-- END PAGE LEVEL SCRIPTS -->
        <script>
            jQuery(document).ready(function() {
                Metronic.init(); // init metronic core components
                Layout.init(); // init current layout
                Login.init();
                Demo.init();
                
                <c:if test="${empty ERROR_MESSAGE}">
                    $('.alert').hide();
                </c:if>
                <c:if test="${not empty ERROR_MESSAGE}">
                    $('#message_tag').html('${ERROR_MESSAGE}');
                    $('.alert').show();
                </c:if>
            });
        </script>
        <!-- END JAVASCRIPTS -->
    </body>
    <!-- END BODY -->
</html>
