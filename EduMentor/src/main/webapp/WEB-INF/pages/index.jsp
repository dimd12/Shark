<%-- 
    Document   : index
    Created on : Feb 2, 2025, 11:20:14 AM
    Author     : adrian
--%>

<%-- Taglib directive for JSTL core tags, used for iteration and conditional logic --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Page directive to set content type and character encoding --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%-- Include common head elements (e.g., meta tags, title) --%>
    <%@include file="common/head.jspf" %>
    <%-- Include common CSS styles --%>
    <%@include file="common/styles.jspf" %>
    <title>EduMentor</title>
</head>
<body>
<%-- Include navigation sidebar structure --%>
<%@include file="common/navbarsidebarnologin.jspf" %>
<%-- Main heading for the page --%>
<h1>Hello World!</h1>

<%-- Include common footer elements --%>
<%@include file="common/footer.jspf" %>

<%-- Include common footer scripts --%>
<%@include file="common/footerscripts.jspf" %>
</body>
</html>
