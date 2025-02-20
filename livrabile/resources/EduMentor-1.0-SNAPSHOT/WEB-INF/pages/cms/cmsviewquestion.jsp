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
  <%@include file="../common/head.jspf"%>
  <%-- Include common CSS styles --%>
  <%@include file="../common/styles.jspf"%>
  <title>Question</title>
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

    /* Style for the response area */
    .response-container {
      margin-top: 20px;
      padding: 15px;
      background-color: #f8f8f8;
      border: 1px solid #ddd;
      border-radius: 8px;
    }

    .response-textarea {
      width: 100%;
      padding: 10px;
      margin-bottom: 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
      resize: vertical;
    }

    .response-image-input {
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
  </style>
</head>
<body>
<%-- Include common navbar and sidebar login elements --%>
<%@include file="../common/navbarsidebarlogin.jspf"%>

<div class="container questions-container">
  <%-- Question Card --%>
  <div class="question-card">
    <%-- Conditional rendering for question image --%>
    <c:if test="${not empty question.imageUrl}">
      <%-- Question image --%>
      <img src="${question.imageUrl}" class="question-image" alt="Question Image">
    </c:if>
    <%-- Card body --%>
    <div class="card-body">
      <%-- Question Title --%>
      <h5 class="card-title">${question.title}</h5>
      <%-- Question details --%>
      <p class="card-text">
        ${question.details}
      </p>
      <%-- Metadata (posted by, date) --%>
      <p class="card-meta">Posted by ${question.userId.username} on ${question.dateCreated}</p>
      <%-- Display Category Name --%>
      <p><small>${question.categoryId.categoryName}</small></p>
    </div>
  </div>

  <%-- Response Container --%>
  <div class="response-container">
    <%-- Form to submit a response/answer --%>
    <form action="cmsquestionanswerserv" method="post">
      <%-- Hidden input for the question ID --%>
      <input type="hidden" name="questionId" value="${question.questionId}">

      <%-- Label and textarea for the response --%>
      <label for="response">Your Response:</label>
      <textarea id="response" name="response" class="response-textarea" rows="4" placeholder="Enter your response here"></textarea>

      <%-- Label and input for the image URL (optional) --%>
      <label for="imageUrl">Image URL (Optional):</label>
      <input type="url" id="imageUrl" name="imageUrl" class="response-image-input" placeholder="Enter image URL">

      <%-- Submit button --%>
      <button type="submit" class="response-submit-button">Submit Response</button>
    </form>
  </div>

  <br>

  <%-- Container for answers --%>
  <div class="container answer-container">
    <%-- Card to display answers --%>
    <div class="question-card">
      <%-- Iterate through the list of answers --%>
      <c:forEach items="${answerList}" var="answer">
        <%-- Conditional rendering for answer image --%>
        <c:if test="${not empty answer.imageUrl}">
          <%-- Answer image --%>
          <img src="${answer.imageUrl}" class="question-image" alt="Question Image">
        </c:if>
        <%-- Card body --%>
        <div class="card-body">
            <%-- Answer Response --%>
          <h5 class="card-title">${answer.response}</h5>
            <%-- Display metadata - author and date of the answer --%>
          <p class="card-meta">Answered by ${answer.userId.username} on ${answer.dateCreated}</p>
        </div>
      </c:forEach>
    </div>

  </div>


  <%-- Include common footer elements --%>
  <%@include file="../common/footer.jspf"%>
  <%-- Include common footer scripts --%>
  <%@include file="../common/footerscripts.jspf"%>
</body>
</html>
