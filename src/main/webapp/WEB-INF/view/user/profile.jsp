<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title>${title}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>

<div id="header">
    <h1>${title}</h1>
</div>
<div class="container">
    <jsp:include page="/WEB-INF/view/layouts/nav.jsp"/>
    <div style="clear: both;"></div>
    <div id="container">
        <i><spring:message code="page.user.role"/> </i><br><br>
        <form:form action="${pageContext.request.contextPath}/member/profile" modelAttribute="member" method="POST">
            <form:hidden path="id"/>
            <table>
                <colgroup>
                    <col style="width: 160px;">
                    <col style="width: auto;">
                </colgroup>
                <tbody>
                <tr>
                    <td><label><spring:message code="bean.user.username"/>:&nbsp;</label></td>
                    <td>
                        <form:input path="username" readonly="true"/>
                    </td>
                </tr>
                <tr>
                    <td><label><spring:message code="bean.user.firstname"/>:&nbsp;</label></td>
                    <td>
                        <form:input path="firstName"/>
                        <form:errors path="firstName" cssClass="error"/>
                    </td>
                </tr>
                <tr>
                    <td><label><spring:message code="bean.user.lastname"/>:&nbsp;</label></td>
                    <td>
                        <form:input path="lastName"/>
                        <form:errors path="lastName" cssClass="error"/>
                    </td>
                </tr>
                <tr>
                    <td><label><spring:message code="bean.user.address"/>:&nbsp;</label></td>
                    <td>
                        <form:textarea path="address" cols="40" rows="5"></form:textarea>
                        <form:errors path="address" cssClass="error"/>
                    </td>
                </tr>
                <tr>
                    <td><label></label></td>
                    <td>
                        <button type="submit" class="save"><spring:message code="page.btn.save"/> </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </form:form>
        <p>
            <a href="${pageContext.request.contextPath}">&lt;&lt; <spring:message code="page.navigate.back"/></a>
        </p>
    </div>
</div>

<jsp:include page="/WEB-INF/view/layouts/footer.jsp"/>
</body>
</html>
