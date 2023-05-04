<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
    <div style="clear: both;"></div>
    <div id="container">
        <i><spring:message code="page.user.role"/> </i><br><br>
        <form:form action="${pageContext.request.contextPath}/register" modelAttribute="member" method="POST">
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
                        <form:input path="username" type="email" placeholder="อีเมล"/>
                        <form:errors path="username" cssClass="error"/>
                    </td>
                </tr>
                <tr>
                    <td><label><spring:message code="bean.user.password"/>:&nbsp;</label></td>
                    <td>
                        <form:password path="password"  placeholder="รหัสผ่าน"/>
                        <form:errors path="password" cssClass="error"/>
                    </td>
                </tr>
                <tr>
                    <td><label><spring:message code="bean.user.passwordconfirm"/>:&nbsp;</label></td>
                    <td>
                        <form:password path="login.confirmPassword"  placeholder="ยืนยันรหัสผ่าน"/>
                        <form:errors path="login.confirmPassword" cssClass="error"/>
                    </td>
                </tr>
                <tr>
                    <td><label></label></td>
                    <td>
                        <button type="submit" class="save">
                            <spring:message code="page.register.title"/>
                        </button>
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
