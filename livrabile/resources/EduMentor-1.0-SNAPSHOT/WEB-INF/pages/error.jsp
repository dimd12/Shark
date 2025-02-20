<%--
  Created by IntelliJ IDEA.
  User: adrian
  Date: 2/8/25
  Time: 5:15 PM
--%>
<%-- Taglib directive for JSTL core tags, used for iteration and conditional logic --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Page directive to set content type and character encoding --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%-- Include common head elements (e.g., meta tags, title) --%>
    <%@ include file="common/head.jspf" %>
    <%-- Include common CSS styles --%>
    <%@ include file="common/styles.jspf" %>
    <title>404 - Page Not Found</title>
</head>
<body>
<%-- Include navigation sidebar without login structure --%>
<%@ include file="common/navbarsidebarnologin.jspf"%>

<h1>404 - Page Not Found</h1>

<%-- Include common footer elements --%>
<%@ include file="common/footer.jspf"%>
<%-- Include common footer scripts --%>
<%@ include file="common/footerscripts.jspf"%>
</body>
</html>
