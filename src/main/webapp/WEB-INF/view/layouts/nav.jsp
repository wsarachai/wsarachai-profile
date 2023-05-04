<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form:form action="${pageContext.request.contextPath}/logout" method="POST" name="frmLogout"></form:form>
<nav>
    <ul>
        <li><a href="${pageContext.request.contextPath}"><spring:message code="page.home" /></a></li>
        <security:authorize access="hasRole('ADMIN')">
            <li><a href="${pageContext.request.contextPath}/system/member/list"><spring:message code="page.user"/> </a></li>
        </security:authorize>
        <security:authorize access="!isAuthenticated()">
            <li><a href="${pageContext.request.contextPath}/login"><spring:message code="page.login"/> </a></li>
        </security:authorize>
        <security:authorize access="isAuthenticated()">
            <li>ชื่อผู้ใช้:
                <a href="${pageContext.request.contextPath}/member/profile">
                    <security:authentication property="principal.username" />
                </a>
            </li>
            <li><a href="#" onclick="javascript: frmLogout.submit();"><spring:message code="page.login.logout"/> </a></li>
        </security:authorize>
    </ul>
</nav>
<c:if test="${errors != null}" >
    <p class="error center">${errors}</p>
</c:if>
<c:if test="${status != null}" >
    <p class="info center">${status}</p>
</c:if>
<hr>