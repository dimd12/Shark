<%--
  Created by IntelliJ IDEA.
  User: adrian
  Date: 2/12/25
  Time: 10:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%-- Taglib directive for JSTL core tags, used for iteration and conditional logic --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Page directive to set content type and character encoding --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%-- Include common head elements (e.g., meta tags, title) --%>
    <%@include file="../common/head.jspf"%>
    <%-- Include common CSS styles --%>
    <%@include file="../common/styles.jspf"%>
    <title>Question administration</title>

    <style>
        .navbar {
            background-color: #e6e6fa; /* Lavender background as per shared styles */
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); /* Retain the original shadow */
        }

        .navbar-brand, .navbar-nav .nav-link {
            color: #4b0082 !important; /* Retain purple text for links */
        }

        .navbar-brand:hover, .navbar-nav .nav-link:hover {
            color: #9370db !important; /* Lighter purple on hover */
        }
    </style>
</head>
<body>
<%-- Include common navigation bar and sidebar login elements --%>
<%@include file="../common/navbarsidebarlogin.jspf"%>
<div class="container mt-4">
    <h1>Question Administration</h1>
    <form id="search-form" class="d-flex mb-3" role="search" action="#" method="get">
        <input class="form-control me-2" type="search" placeholder="Search Questions" aria-label="Search" id="search-input" name="search-input">
        <button class="btn btn-outline-success" type="submit" id="search-button">Search</button>
    </form>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">Nr</th>
            <th scope="col">Title</th>
            <th scope="col">Details</th>
            <th scope="col">Date</th>
            <th scope="col">Author</th>
            <th scope="col">Image</th>
            <th scope="col">Category</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tbody>
        <%-- Iterate through the questionList provided by the servlet --%>
        <c:forEach items="${questionList}" var="question">
            <tr>
                <th scope="row">${question.questionId}</th>
                <td>${question.title}</td>
                <td>${question.details}</td>
                <td>${question.dateCreated}</td>
                <td>${question.userId.username} ${question.userId.firstName}</td>
                <td>
                        <%-- Display the question image --%>
                    <img src="${question.imageUrl}" alt="Image" width="100" height="100" />
                </td>
                <td>${question.categoryId.categoryName}</td>
                <td>
                        <%-- Link to delete the question, passing the question ID as a parameter --%>
                    <a href="delete-question.html?id=${question.questionId}">
                        <button style="background-color: red; color: white; border: none; font-size: 14px; padding: 8px 16px; border-radius: 8px;">Delete</button>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%-- Include common footer elements --%>
<%@include file="../common/footer.jspf"%>
<%-- Include common footer scripts --%>
<%@include file="../common/footerscripts.jspf"%>
</body>
</html>
