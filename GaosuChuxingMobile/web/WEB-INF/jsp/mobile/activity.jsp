<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<input type="hidden" id="type" value="" />
<input type="hidden" id="shareUrl" value="${shareUrl}"/>
<input type="hidden" id="shareTitle" value="${activity.shareTitle}"/>
<input type="hidden" id="shareContent" value="${activity.shareContent}"/>

<div class="delivery container-fluid container-lf-space main-body init-div-horizontal" style="margin-bottom: 0;">
    <div class="row init-div-horizontal">
        <div class="col-xs-12 init-div-horizontal">
            <div class="row init-div-horizontal" style="padding: 15px 20px 10px 20px;">
                <img class="img-shadow" src="/${activity.imagePath}" style="width: 100%;" />
            </div>
            <div class="row init-div-horizontal privacy-detail-date" style="padding: 0 20px;">
                <img src="<c:url value="/assets/img/news_icon_time@2x.png"/>" style="width: 17px; vertical-align: sub; margin-right: 10px;" />
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" var="fmtRegDate" value="${activity.regDate}" />
                ${fmtRegDate}
            </div>
            <div class="row init-div-horizontal" style="padding: 20px;">
                <p class="init-div-vertical privacy-detail-title">${activity.name}</p>
                <p class="init-div-vertical privacy-detail-content">
                </p>
            </div>
            <c:if test="${not empty shared}">
                <div class="row init-div-horizontal" style="min-height: 0px; padding-bottom: 20px;">
                    <div class="col-xs-12 init-div-horizontal">
                        <center><img class="story-img-button" src="<c:url value="/assets/img/story_button.png"/>" onclick="onTakeAdv('${activity.id}', '${activity.shareNum}');"></center>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>
                
<div class="modal fade" id="alert-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog my_modal" style="margin-top: 45%;">
        <div class="modal-content" style="width: 300px; padding: 25px;">
            <img src="<c:url value="/assets/img/adv_success_msg.png"/>" style="width: 100%; padding: 10px;" id="success-msg">
            <img src="<c:url value="/assets/img/adv_fail_msg.png"/>" style="width: 100%; padding: 10px;" id="fail-msg" class="hide">
            <div class="modal-body">
                <div class="row init-div">
                    <div class="col-xs-12 init-div-horizontal">
                        <center><img class="story-img-button" style="width: 55%;" src="<c:url value="/assets/img/hao_btn.png"/>" data-dismiss="modal"></center>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>