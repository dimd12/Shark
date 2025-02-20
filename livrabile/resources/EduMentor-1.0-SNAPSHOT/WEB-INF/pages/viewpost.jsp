<%--
  Created by IntelliJ IDEA.
  User: adrian
  Date: 2/19/2025
  Time: 11:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%-- Taglib directive for JSTL core tags, used for iteration and conditional logic --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Page directive to set content type and character encoding --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <%-- Include common head elements (e.g., meta tags, title) --%>
  <%@include file="common/head.jspf"%>
  <%-- Include common CSS styles --%>
  <%@include file="common/styles.jspf"%>
  <title>Post</title>
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
    .post-video {
      width: 100%;
      border-top-left-radius: 8px;
      border-top-right-radius: 8px;
      max-height: 400px; /* Limit image height for consistency */
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

    /* Style for the response area */
    .review-container {
      margin-top: 20px;
      padding: 15px;
      background-color: #f8f8f8;
      border: 1px solid #ddd;
      border-radius: 8px;
    }

    .review-textarea {
      width: 100%;
      padding: 10px;
      margin-bottom: 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
      resize: vertical;
    }

    .rating-input {
      width: 100%;
      padding: 10px;
      margin-bottom: 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }

    .response-submit-button {
      background-color: #5cb85c;
      color: white;
      padding: 10px 15px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }

    .response-submit-button:hover {
      background-color: #4cae4c;
    }

    /* Style for reviews container */
    .reviews-container {
      margin-top: 20px;
    }

    /* Style for individual review cards */
    .review-card {
      background-color: #ffffff;
      border: 1px solid #ddd;
      border-radius: 8px;
      margin-bottom: 20px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
    }

    .review-body {
      padding: 15px;
    }
  </style>
</head>
<body>
<%-- Include common navbar and sidebar with no login elements --%>
<%@include file="common/navbarsidebarnologin.jspf"%>

<div class="container posts-container">
  <%-- Post Card --%>
  <div class="post-card">
    <%-- Conditional rendering for video URL --%>
    <c:if test="${not empty post.videoUrl}">
      <%-- Embedded iframe to display video content --%>
      <iframe width="100%" height="400"
              src="${post.videoUrl}"
              title="YouTube video player"
              frameborder="0"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
              allowfullscreen
              class="post-video">
      </iframe>
    </c:if>
    <%-- Card body --%>
    <div class="card-body">
      <%-- Card Title --%>
      <h5 class="card-title">${post.title}</h5>
      <%-- Card Text --%>
      <p class="card-text">
        ${post.description}
      </p>
      <%-- Meta information --%>
      <p class="card-meta">Posted by ${post.userId.username} on ${post.dateCreated}</p>
      <%-- Category information --%>
      <p><small>${post.categoryId.categoryName}</small></p>
    </div>
  </div>

  <%-- Container for reviews --%>
  <div class="container reviews-container">
    <%-- Iterate through the reviewList provided by the servlet --%>
    <c:forEach items="${reviewList}" var="review">
      <%-- Review Card --%>
      <div class="review-card">
          <%-- Review Body --%>
        <div class="review-body">
            <%-- Display rating --%>
          <h5 class="card-title">${review.rating} Stars</h5>
            <%-- Display review message --%>
          <p class="card-text">${review.reviewMessage}</p>
            <%-- Meta information about the review --%>
          <p class="card-meta">Reviewed by ${review.userId.username} on ${review.dateSent}</p>
        </div>
      </div>
    </c:forEach>
  </div>

</div>

<%-- Include common footer elements --%>
<%@include file="common/footer.jspf"%>
<%-- Include common footer scripts --%>
<%@include file="common/footerscripts.jspf"%>
</body>
</html>
