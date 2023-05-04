<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>
    <h1>Access Denied</h1>
    <hr>
    <div class="container">
        <h2 style="text-align: center;"><spring:message code="page.access-denied.title" /></h2>
        <div style="text-align:center">
            <a href="${pageContext.request.contextPath}/"><spring:message code="page.access-denied.back" /></a>
        </div>
    </div>
    <jsp:include page="/WEB-INF/view/layouts/footer.jsp"/>
</body>
</html>
