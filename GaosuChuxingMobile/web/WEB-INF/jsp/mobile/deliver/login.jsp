<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en" class="">
    <!-- BEGIN HEAD -->
    <head>
        <meta charset="utf-8"/>
        <title>登录</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport"/>

        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="<c:url value="/assets/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
        <!-- END GLOBAL MANDATORY STYLES -->

        <!-- BEGIN THEME STYLES -->
        <link href="<c:url value="/assets/css/components.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/layout.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/themes/default.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/assets/css/custom.css"/>" rel="stylesheet" type="text/css"/>
        <!-- END THEME STYLES -->

    </head>
    <!-- END HEAD -->

    <!-- BEGIN BODY -->
    <body style="background-color: #f1f5f8;">
        <!-- BEGIN HEADER -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation" style="border: 0;">
            <div class="navbar-header" style="width: 100%;">
                <img src="<c:url value="/assets/img/bg_login_navheader.png"/>" style="width: 100%; height: 50px;" />
                <a class="navbar-brand" style="position: absolute; top: 0px; color: #fff;" href="javascript:history.go(-1);">
                    <span class="glyphicon glyphicon-chevron-left"></span></a>
            </div>
        </nav>
        <!-- END HEADER -->

        <!-- BEGIN CONTENT -->
        <div class="delivery container-fluid container-lf-space main-body init-div-horizontal" style="margin-bottom: 0; margin-top: 50px;">
            <div class="row init-div-horizontal">
                <img src="<c:url value="/assets/img/bg_login.png"/>" style="width: 100%;" />
            </div>
            <form:form commandName="loginForm" action="login" method="post" onsubmit="return onSubmit();">
                <div class="row init-div" style="padding: 0 45px; margin-top: -25%; background-color: #f1f5f8;">
                    <div class="col-xs-12 init-div img-shadow" style="background-color: #fff; padding-top: 20px; border-radius: 3% !important;">
                        <div class="row" style="padding: 15px;">
                            <div class="col-xs-12 form-group form-md-line-input margin-top-20" style="margin-bottom: 0;">
                                <div class="input-group">
                                    <span class="input-group-addon">
                                        <img src="<c:url value="/assets/img/tab_icon_profile_pre@2x.png"/>" style="width: 100%;" />
                                    </span>
                                    <form:input type="text" path="userId" cssClass="form-control" placeholder="账号" />
                                </div>
                            </div>
                            <div class="col-xs-12 form-group form-md-line-input">
                                <div class="input-group">
                                    <span class="input-group-addon">
                                        <img src="<c:url value="/assets/img/my_icon5@2x.png"/>" style="width: 100%;" />
                                    </span>
                                    <form:input type="password" path="password" cssClass="form-control" placeholder="密码" />
                                    <form:hidden path="digest" />
                                </div>
                            </div>
                            <div class="col-xs-12 form-group form-md-line-input margin-top-10">
                                <button type="submit" class="btn btn-transparent btn-circle btn-sm col-xs-12 init-div-horizontal btn-green"> 登录 </button>
                            </div>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>
        <!-- END CONTENT -->

        <script src="<c:url value="/assets/js/jquery.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/js/bootstrap.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/assets/js/md5.js"/>" type="text/javascript"></script>
        
        <script>
            $(function() {
                <c:if test="${not empty ERROR_MESSAGE}">
                    alert('${ERROR_MESSAGE}');
                </c:if>
            });
            
            function onSubmit() {
                if ($('#userId').val() == '') {
                    alert('账号不能为空');
                    return false;
                } else if ($('#password').val() == '') {
                    alert('密码不能为空');
                    return false;
                } else {
                    $('#digest').val(getDigest($('#password').val()));
                    return true;
                }
            }
        </script>
    </body> 
    <!-- END BODY -->

</html>
