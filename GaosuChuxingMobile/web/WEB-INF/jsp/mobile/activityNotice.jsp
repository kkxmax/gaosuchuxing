<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<input type="hidden" id="type" value="" />
<input type="hidden" id="shareUrl" value="${shareUrl}"/>
<input type="hidden" id="shareTitle" value="${activityNotice.shareTitle}"/>
<input type="hidden" id="shareContent" value="${activityNotice.shareContent}"/>

<div class="delivery container-fluid container-lf-space main-body init-div-horizontal" style="margin-bottom: 0;">
    <div class="row init-div-horizontal">
        <div class="col-xs-12 init-div-horizontal">
            <div class="row init-div-horizontal" style="padding: 15px 20px 10px 20px;">
                <img class="img-shadow" src="/${activityNotice.imagePath}" style="width: 100%;" />
            </div>
            <div class="row init-div-horizontal privacy-detail-date" style="padding: 0 20px;">
                <img src="<c:url value="/assets/img/news_icon_time@2x.png"/>" style="width: 17px; vertical-align: sub; margin-right: 10px;" />
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" var="fmtRegDate" value="${activityNotice.regDate}" />
                ${fmtRegDate}
            </div>
            <div class="row init-div-horizontal" style="padding: 20px;">
                <p class="init-div-vertical privacy-detail-title">${activityNotice.name}</p>
                <p class="init-div-vertical privacy-detail-content">
                    ${fn:replace(activityNotice.description, '\\r\\n', '<br/>')}
                </p>
            </div>
        </div>
    </div>
</div>