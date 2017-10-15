<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="delivery container-fluid container-lf-space main-body init-div-horizontal" style="margin-bottom: 0;">
    <div class="row init-div-horizontal">
        <div class="col-md-12" style="padding: 20px 15px;">
            <textarea id="txt_opinion" class="form-control autosizeme" rows="4" 
                      onkeyup="javascript:doingInputText(this.value);"
                      placeholder="请详细描述您遇到的问题或者要反馈的建议" maxlength="150" data-autosize-on="true" style="overflow-y: hidden; resize: horizontal; height: 94px;"></textarea>
            <span id="textarea_type_cnt" style="position: absolute; right: 25px; bottom: 20px; color: #aaa;">0/150</span>
        </div>
    </div>
    <div class="row init-div-horizontal hr" style="height: 60px; background-color: #fff;"></div>

    <div class="row init-div-horizontal">
        <div class="col-xs-12 init-div-horizontal" style="padding: 0 20px;">
            <button class="btn btn-transparent btn-circle btn-sm col-xs-12 init-div-horizontal btn-green" onclick="onSubmit()"> 提交 </button>
        </div>
    </div>

</div>
