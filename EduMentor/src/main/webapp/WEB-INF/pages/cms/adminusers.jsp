<%--
  Created by IntelliJ IDEA.
  User: adrian
  Date: 2/13/2025
  Time: 10:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%-- Taglib directive for JSTL core tags, used for iteration --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Page directive to set content type and character encoding --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%-- Include common head elements (e.g., meta tags, title) --%>
    <%@include file="../common/head.jspf"%>
    <%-- Include common CSS styles --%>
    <%@include file="../common/styles.jspf"%>
    <title>User administration</title>
    <style>
        .navbar {
            background-color: #e6e6fa;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
        }

        .navbar-brand, .navbar-nav .nav-link {
            color: #4b0082 !important;
        }

        .navbar-brand:hover, .navbar-nav .nav-link:hover {
            color: #9370db !important;
        }
    </style>
</head>
<body>
<%-- Include common navigation bar and sidebar login elements --%>
<%@include file="../common/navbarsidebarlogin.jspf"%>

<div class="container mt-4">
    <h1>User Administration</h1>
    <form id="search-form" class="d-flex mb-3" role="search" action="#" method="get">
        <input class="form-control me-2" type="search" placeholder="Search Users" aria-label="Search" id="search-input" name="search-input">
        <button class="btn btn-outline-success" type="submit" id="search-button">Search</button>
    </form>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">Nr</th>
            <th scope="col">Username</th>
            <th scope="col">Email</th>
            <th scope="col">Name</th>
            <th scope="col">Profile Picture</th>
            <th scope="col">Bio</th>
            <th scope="col">Role</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tbody>
        <%-- Iterate through the userList provided by the servlet --%>
        <c:forEach items="${userList}" var="user">
            <tr>
                <th scope="row">${user.userId}</th>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${user.firstName} ${user.lastName}</td>
                <td>
                        <%-- Display user's profile picture --%>
                    <img src="${user.profilePictureUrl}" alt="Image" width="100" height="100" />
                </td>
                <td>${user.bio}</td>
                <td>${user.roleId.roleName}</td>
                <td>
                        <%-- Link to delete the user, passing the user ID as a parameter --%>
                    <a href="delete-user.html?id=${user.userId}">
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
