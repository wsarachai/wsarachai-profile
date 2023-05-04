<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>

<div id="header">
    <h1>${title}</h1>
</div>

<div class="container">
    <jsp:include page="/WEB-INF/view/layouts/nav.jsp"/>

    <button onclick="window.location.href='${pageContext.request.contextPath}/system/member/create'; return false;"
            class="add-button">
        <spring:message code="page.user.create"/>
    </button>

    <table class="table-bordered">
        <thead>
        <tr>
            <th><spring:message code="bean.user.username"/> </th>
            <th><spring:message code="bean.user.firstname"/></th>
            <th><spring:message code="bean.user.lastname"/> </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${members}">
            <tr>
                <td><a href="${pageContext.request.contextPath}/system/member/${user.id}/update">${user.username}</a></td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="/WEB-INF/view/layouts/footer.jsp"/>

</body>
</html>
