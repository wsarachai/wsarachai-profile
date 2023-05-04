<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <div style="clear: both;"></div>
    <div id="container">
        <i><spring:message code="page.user.role"/></i><br><br>
        <form:form action="${pageContext.request.contextPath}/system/member/save" modelAttribute="member" method="POST">
            <form:hidden path="id"/>
            <table>
                <colgroup>
                    <col style="width: 160px;">
                    <col style="width: auto;">
                </colgroup>
                <tbody>
                <tr>
                    <td><label><spring:message code="bean.user.username"/> :&nbsp;</label></td>
                    <td>
                        <form:input path="username" readonly="${disabled}"/>
                        <form:errors path="username" cssClass="error"/>
                    </td>
                </tr>
                <tr>
                    <td><label><spring:message code="bean.user.firstname"/> :&nbsp;</label></td>
                    <td>
                        <form:input path="firstName"/>
                        <form:errors path="firstName" cssClass="error"/>
                    </td>
                </tr>
                <tr>
                    <td><label><spring:message code="bean.user.lastname"/> :&nbsp;</label></td>
                    <td>
                        <form:input path="lastName"/>
                        <form:errors path="lastName" cssClass="error"/>
                    </td>
                </tr>
                <tr>
                    <td><label><spring:message code="bean.user.validFrom"/> :&nbsp;</label></td>
                    <td>
                        <form:input type="date" path="validFrom"/>
                        <form:errors path="validFrom" cssClass="error"/>
                    </td>
                </tr>
                <tr>
                    <td><label><spring:message code="bean.user.expiredDate"/> :&nbsp;</label></td>
                    <td>
                        <form:input type="date"  path="expiredDate"/>
                        <form:errors path="expiredDate" cssClass="error"/>
                    </td>
                </tr>
                <security:authorize access="hasRole('ADMIN')">
                    <tr>
                        <td><label>Role:&nbsp;</label></td>
                        <td class="field-group">
                            <ul>
                                <form:checkboxes path="authorities"
                                                 items="${authorities}"
                                                 element="div"/>
                                <form:errors path="authorities" cssClass="error"/>
                            </ul>
                        </td>
                    </tr>
                </security:authorize>
                <tr>
                    <td><label><spring:message code="bean.user.address"/> :&nbsp;</label></td>
                    <td>
                        <form:textarea path="address" cols="50" rows="5"></form:textarea>
                        <form:errors path="address" cssClass="error"/>
                    </td>
                </tr>
                <tr>
                    <td><label></label></td>
                    <td>
                        <button type="submit" class="save-button">
                            <spring:message code="page.btn.save"/>
                        </button>
                        <button onclick="if((confirm('คุณแน่ใจหรือว่าต้องการลบผู้ใช้นี้ ?'))) { window.location.href='${pageContext.request.contextPath}/system/member/${member.id}/delete'; return false; }"
                                class="cancel-button">
                            <spring:message code="page.btn.delete"/>
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </form:form>
        <p>
            <c:url var="backLink" value="/system/member/list"/>
            <a href="${backLink}">&lt;&lt; <spring:message code="page.navigate.back"/></a>
        </p>
    </div>
</div>

<jsp:include page="/WEB-INF/view/layouts/footer.jsp"/>

</body>
</html>
