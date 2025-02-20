<%--
  Created by IntelliJ IDEA.
  User: adrian
  Date: 2/18/2025
  Time: 9:07 PM
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
    <title>Add post</title>
    <%-- Include common CSS styles --%>
    <%@include file="../common/styles.jspf"%>
    <style>
        /* Ensure navbar style remains unaffected */
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
        /* Styles specific to the add post form */
        .add-post-form {
            max-width: 600px; /* Restrict form width for better readability */
            margin: 20px auto; /* Center align form */
            padding: 20px;
            background-color: #ffffff; /* White background for the form */
            border: 1px solid #ccc;
            border-radius: 8px; /* Rounded corners */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Subtle shadow for the form */
        }

        .add-post-form label {
            font-weight: bold; /* Bold font for labels */
            display: block; /* Ensure labels occupy a full line */
            margin-bottom: 5px;
            color: #4b0082; /* Consistent purple color for form labels */
        }

        .add-post-form input,
        .add-post-form select,
        .add-post-form button {
            width: 100%; /* Stretch inputs and buttons across the container */
            margin: 5px 0 15px 0; /* Add spacing between controls */
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px; /* Rounded inputs */
            font-size: 1rem;
        }

        .add-post-form button {
            background-color: #9370db; /* Medium purple for buttons */
            color: white; /* Bright text for visibility */
            font-weight: bold;
            cursor: pointer;
            border: none;
        }

        .add-post-form button:hover {
            background-color: #ba55d3; /* Lighter purple for hover */
        }

        /* Styling for the page heading */
        .add-post-header {
            text-align: center;
            font-size: 2rem;
            color: #4b0082; /* Bold purple heading */
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<%-- Include common navbar and sidebar login elements --%>
<%@include file="../common/navbarsidebarlogin.jspf"%>

<div class="container content">
    <!-- Page header -->
    <h1 class="add-post-header">Add a Post</h1>

    <!-- Add Post Form -->
    <form id="addPostMainForm" class="add-post-form" action="cmsaddquestionserv" method="POST" onsubmit="return validateAddQuestionForm()">
        <div class="mb-3">
            <label for="title" class="form-label">Title</label>
            <input type="text" class="form-control" id="title" name="title" placeholder="Enter the title">
        </div>
        <div class="mb-3">
            <label for="details" class="form-label">Details</label>
            <input type="text" class="form-control" id="details" name="details" placeholder="Enter the question's details">
        </div>
        <div>
            <label for="imageUrl" class="form-label">Image Link</label>
            <input type="url" class="form-control" id="imageUrl" name="imageUrl" placeholder="Enter the image link (optional)">
        </div>
        <div class="mb-3">
            <label for="category">Choose a category</label>
            <select id="category" name="category">
                <%-- Iterate through the categoryList provided by the servlet --%>
                <c:forEach items="${categoryList}" var="category">
                    <option value="${category.categoryId}">${category.categoryName}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary w-100 mt-3">Post</button>
    </form>
</div>

<%-- Include common footer elements --%>
<%@include file="../common/footer.jspf"%>
<%-- Include common footer scripts --%>
<%@include file="../common/footerscripts.jspf"%>

<script>
    // Function to validate the add question form
    function validateAddQuestionForm() {
        // Get the values from the input fields
        var title = document.getElementById("title").value;
        var details = document.getElementById("details").value;
        var imageUrl = document.getElementById("imageUrl").value;
        var category = document.getElementById("category").value;
        // If any of the required fields are empty, display an alert and return false
        if (title == "" || category == "") {
            alert("Please fill in all the fields");
            return false;
        }

        // If all required fields are filled, return true
        return true;
    }
</script>
</body>
</html>
