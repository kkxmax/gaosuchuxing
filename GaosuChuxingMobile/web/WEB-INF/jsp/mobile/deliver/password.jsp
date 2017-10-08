<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="delivery container-fluid container-lf-space main-body init-div-horizontal" style="margin-bottom: 0;">
    <div class="row init-div-horizontal margin-top-10 margin-bottom-10 profile-content">
        <div class="col-xs-3 init-div-horizontal" style="padding-left: 20px;">
            <p class="init-div-vertical update-pwd-title">旧密码</p>
        </div>
        <div class="col-xs-9 init-div-horizontal">
            <input type="password" class="update-pwd-content" placeholder="输入旧密码" style="border: none; border-color: transparent; outline: none;" id="currentPwd" maxlength="20" />
        </div>
    </div>
    <div class="row init-div-horizontal hr"></div>

    <div class="row init-div-horizontal margin-top-10 margin-bottom-10 profile-content">
        <div class="col-xs-3 init-div-horizontal" style="padding-left: 20px;">
            <p class="init-div-vertical update-pwd-title">新密码</p>
        </div>
        <div class="col-xs-9 init-div-horizontal">
            <input type="password" class="update-pwd-content" placeholder="输入新密码" style="border: none; border-color: transparent; outline: none;" id="newPwd" maxlength="20" />
        </div>
    </div>
    <div class="row init-div-horizontal hr"></div>

    <div class="row init-div-horizontal margin-top-10 margin-bottom-10 profile-content">
        <div class="col-xs-3 init-div-horizontal" style="padding-left: 20px;">
            <p class="init-div-vertical update-pwd-title">确认密码</p>
        </div>
        <div class="col-xs-9 init-div-horizontal">
            <input type="password" class="update-pwd-content" placeholder="再次输入密码" style="border: none; border-color: transparent; outline: none;" id="confirmPwd" maxlength="20" />
        </div>
    </div>
    <div class="row init-div-horizontal hr" style="height: 60px; background-color: #f5f5f5;"></div>

    <div class="row init-div-horizontal" style="background: #f5f5f5;">
        <div class="col-xs-12 init-div-horizontal" style="padding: 0 20px;">
            <button class="btn btn-transparent btn-circle btn-sm col-xs-12 init-div-horizontal btn-green" onclick="onSubmit()"> 完成 </button>
        </div>
    </div>

</div>

<div class="modal fade bs-modal-sm" id="password-success-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog modal-sm" style="margin-top: 200px;">
        <div class="modal-content">
            <div class="modal-header" style="border-bottom: 0;">
                <h3 class="modal-title" style="color: #3598dc;"><i class="fa fa-info-circle" style="font-size: 26px;"></i></h3>
            </div>
            <div class="modal-body">
                <h4 class="modal-title" style="font-family: Microsoft YaHei;">密码修改成功, 重新登录账户</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn blue" id="btn-ok"> 是 </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->