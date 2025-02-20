<%--
  Created by IntelliJ IDEA.
  User: adrian
  Date: 2/19/2025
  Time: 11:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%-- Page directive to set content type and character encoding --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Taglib directive for JSTL core tags, used for iteration --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <%-- Include common head elements (e.g., meta tags, title) --%>
  <%@include file="common/head.jspf"%>
  <%-- Include common CSS styles --%>
  <%@include file="common/styles.jspf"%>
  <title>Questions</title>
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
    .questions-container {
      max-width: 800px;
      margin: 20px auto; /* Center the container */
      padding: 20px;
    }

    /* Style for individual question cards */
    .question-card {
      background-color: #ffffff;
      border: 1px solid #ddd;
      border-radius: 8px;
      margin-bottom: 20px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
      transition: transform 0.2s ease-in-out; /* Add transition for hover effect */
    }

    .question-card:hover {
      transform: translateY(-5px); /* Lift card slightly on hover */
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Enhance shadow on hover */
    }

    /* Style for question images */
    .question-image {
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
<%-- Include common navbar and sidebar login elements --%>
<%@include file="common/navbarsidebarlogin.jspf"%>

<div class="container questions-container">
  <%-- Iterate through the questionList provided by the servlet --%>
  <c:forEach items="${questionList}" var="question">
    <%-- Question Card --%>
    <div class="question-card">
        <%-- Conditional rendering for question image --%>
      <c:if test="${not empty question.imageUrl}">
        <%-- Display Question image --%>
        <img src="${question.imageUrl}" class="question-image" alt="Question Image">
      </c:if>
        <%-- Card body --%>
      <div class="card-body">
          <%-- Display Question title --%>
        <h5 class="card-title">${question.title}</h5>
          <%-- Display a truncated version of the question details --%>
        <p class="card-text">
            ${question.details.length() > 100 ? question.details.substring(0, 100).concat("...") : question.details}
        </p>
          <%-- Display Question metadata (author and date) --%>
        <p class="card-meta">Posted by ${question.userId.username} on ${question.dateCreated}</p>
          <%-- Display question Category Name --%>
        <p><small>${question.categoryId.categoryName}</small></p>
          <%-- Link to view the full question details --%>
        <a href="/view-question.html?id=${question.questionId}" class="btn btn-primary">See all</a>
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
