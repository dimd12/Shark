<%--
  Created by IntelliJ IDEA.
  User: adrian
  Date: 2/12/2025
  Time: 11:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%-- Import JSTL core tag library for iterating through data --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Set content type to handle UTF-8 characters --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%-- Include common head elements (e.g., meta tags, title) --%>
    <%@include file="../common/head.jspf"%>
    <%-- Include common CSS styles --%>
    <%@include file="../common/styles.jspf"%>
    <title>Post administration</title>

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
<%-- Include common navbar and sidebar login elements --%>
<%@include file="../common/navbarsidebarlogin.jspf"%>

<div class="container mt-4">
    <h1>Post administration</h1>
    <form id="search-form" class="d-flex mb-3" role="search" action="#" method="get">
        <input class="form-control me-2" type="search" placeholder="Search Posts" aria-label="Search" id="search-input" name="search-input">
        <button class="btn btn-outline-success" type="submit" id="search-button">Search</button>
    </form>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">Nr</th>
            <th scope="col">Title</th>
            <th scope="col">Date</th>
            <th scope="col">Author</th>
            <th scope="col">Video</th>
            <th scope="col">Category</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tbody>
        <%-- Iterate through the postList passed from the servlet --%>
        <c:forEach items="${postList}" var="post">
            <tr>
                <td>${post.postId}</td>
                <td>${post.title}</td>
                <td>${post.dateCreated}</td>
                <td>${post.userId.username}</td>
                <td>
                        <%-- Embedded iframe to display video content --%>
                    <iframe width="320" height="180"
                            src="${post.videoUrl}"
                            frameborder="0"
                            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope"
                            allowfullscreen>
                    </iframe>
                </td>
                <td>${post.categoryId.categoryName}</td>
                <td>
                        <%-- Link to delete a post, passing postId as a parameter --%>
                    <a href="/admin/delete-post.html?postId=${post.postId}">
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
