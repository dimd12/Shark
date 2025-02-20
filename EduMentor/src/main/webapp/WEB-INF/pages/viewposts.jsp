<%--
  Created by IntelliJ IDEA.
  User: adrian
  Date: 2/19/2025
  Time: 11:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%-- Taglib directive for JSTL core tags, used for iteration --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Page directive to set content type and character encoding --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%-- Include common head elements (e.g., meta tags, title) --%>
    <%@include file="common/head.jspf"%>
    <%-- Include common CSS styles --%>
    <%@include file="common/styles.jspf"%>
    <title>Posts</title>
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

        /* Style for the container holding question cards */
        .posts-container {
            max-width: 800px;
            margin: 20px auto; /* Center the container */
            padding: 20px;
        }

        /* Style for individual question cards */
        .post-card {
            background-color: #ffffff;
            border: 1px solid #ddd;
            border-radius: 8px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
            transition: transform 0.2s ease-in-out; /* Add transition for hover effect */
        }

        .post-card:hover {
            transform: translateY(-5px); /* Lift card slightly on hover */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Enhance shadow on hover */
        }

        /* Style for question images */
        .post-image {
            width: 100%;
            border-top-left-radius: 8px;
            border-top-right-radius: 8px;
            max-height: 200px; /* Limit image height for consistency */
            object-fit: cover; /* Ensure images fill the space without distortion */
        }

        /* Style for the card body */
        .card-body {
            padding: 15px;
        }

        /* Style for question titles */
        .card-title {
            color: #4b0082; /* Purple title */
            font-size: 1.5rem;
            margin-bottom: 10px;
        }

        /* Style for question details */
        .card-text {
            color: #333;
            margin-bottom: 15px;
        }

        /* Style for metadata (posted by, date) */
        .card-meta {
            font-size: 0.9rem;
            color: #777;
        }

        /* Style for the "See all" button */
        .btn-primary {
            background-color: #9370db; /* Medium purple button */
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none; /* Remove underline from the link */
            display: inline-block; /* Ensure proper spacing */
        }

        .btn-primary:hover {
            background-color: #ba55d3; /* Lighter purple on hover */
        }
    </style>
</head>
<body>
<%-- Include common navbar and sidebar with no login elements --%>
<%@include file="common/navbarsidebarnologin.jspf"%>

<div class="container post-container">

    <form id="search-form" class="d-flex mb-3" role="search" action="#" method="get">
        <input class="form-control me-2" type="search" placeholder="Search Posts" aria-label="Search" id="search-input" name="search-input">
        <button class="btn btn-outline-success" type="submit" id="search-button">Search</button>
    </form>
    <%-- Iterate through the postList provided by the servlet --%>
    <c:forEach items="${postList}" var="post">
        <%-- Card to display post information --%>
        <div class="post-card">
                <%-- Embedded iframe to display video content --%>
            <iframe width="560" height="315"
                    src="${post.videoUrl}"
                    title="YouTube video player"
                    frameborder="0"
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                    allowfullscreen>
            </iframe>
                <%-- Card body --%>
            <div class="card-body">
                    <%-- Display post title --%>
                <h5 class="card-title">${post.title}</h5>
                    <%-- Display a truncated version of the post description --%>
                <p class="card-text">
                        ${post.description.length() > 100 ? post.description.substring(0, 100).concat("...") : post.description}
                </p>
                    <%-- Display post metadata (author and date) --%>
                <p class="card-meta">Posted by ${post.userId.username} on ${post.dateCreated}</p>
                    <%-- Display category information --%>
                <p><small>${post.categoryId.categoryName}</small></p>
                    <%-- Link to view the full post details --%>
                <a href="/view-post.html?id=${post.postId}" class="btn btn-primary">See all</a>
            </div>
        </div>
    </c:forEach>
</div>


<%-- Include common footer elements --%>
<%@include file="common/footer.jspf"%>
<%-- Include common footer scripts --%>
<%@include file="common/footerscripts.jspf"%>
</body>
</html>
