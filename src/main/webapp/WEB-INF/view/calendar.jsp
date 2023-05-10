<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title><spring:message code="page.home" /></title>
        <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
    </head>
    <body>
        <h1><spring:message code="page.home" /></h1>
        <div class="container">
            <div>
                <iframe src="https://calendar.google.com/calendar/embed?height=600&wkst=2&bgcolor=%23039BE5&ctz=Asia%2FBangkok&showNav=1&showPrint=0&showCalendars=1&hl=en&src=c2FyYWNoYWlpQGdtYWlsLmNvbQ&src=dGgudGgjaG9saWRheUBncm91cC52LmNhbGVuZGFyLmdvb2dsZS5jb20&color=%23E67C73&color=%230B8043" style="border:solid 1px #777" width="800" height="600" frameborder="0" scrolling="no"></iframe>
            </div>
        </div>
        <jsp:include page="/WEB-INF/view/layouts/footer.jsp"/>
    </body>
</html>