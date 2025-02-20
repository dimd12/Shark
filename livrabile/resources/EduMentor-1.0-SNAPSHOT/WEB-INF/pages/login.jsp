<%--
    Document   : login
    Created on : Feb 5, 2025, 10:08:27 AM
    Author     : adrian
--%>

<%-- Taglib directive for JSTL core tags, used for conditional logic --%>
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
    <title>LogIn</title>

    <style>
        html, body {
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            background-color: #f8f9fa; /* Light background */
            color: #4b0082; /* Dark purple text */
            font-family: 'Arial', sans-serif;
        }

        .form-container {
            max-width: 400px;
            margin: auto;
            margin-top: 50px;
            padding: 20px;
            background-color: #e6e6fa; /* Lavender background */
            border-radius: 10px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
        }

        .form-container h2 {
            text-align: center;
            color: #4b0082; /* Purple text */
            margin-bottom: 20px;
        }

        .form-control {
            border-color: #4b0082; /* Purple border */
        }

        .form-control:focus {
            box-shadow: 0 0 5px #9370db; /* Medium purple glow */
        }

        .btn-primary {
            background-color: #9370db; /* Medium purple buttons */
            color: white;
            border: none;
        }

        .btn-primary:hover {
            background-color: #ba55d3; /* Bright purple on hover */
        }

        .toggle-link {
            color: #4b0082; /* Purple text for toggle link */
            cursor: pointer;
            text-decoration: underline;
        }

        .footer {
            background-color: #e6e6fa; /* Lavender background */
            color: #4b0082; /* Purple text */
            text-align: center;
            padding: 20px 0;
            margin-top: auto;
        }

        .error-message {
            color: red;
            text-align: center;
            margin-bottom: 20px;
        }
    </style>

</head>
<body>
<%-- Include the no-login navigation sidebar--%>
<%@include file="common/navbarsidebarnologin.jspf" %>

<div class="form-container">
    <%-- Display error message if any --%>
    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>

    <%-- Login Form --%>
    <div id="loginForm">
        <h2>Login</h2>
        <%-- Form to submit login credentials --%>
        <form action="cmsloginserv" method="POST">
            <%-- Input field for username --%>
            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text" class="form-control" id="uname" name="username" placeholder="Enter your username">
            </div>
            <%-- Input field for password --%>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="pass" name="password" placeholder="Enter your password">
            </div>
            <%-- Login button --%>
            <button type="submit" class="btn btn-primary w-100">Log In</button>
            <%-- Toggle link to sign up form --%>
            <p class="text-center mt-3">Don't have an account?
                <span class="toggle-link" onclick="toggleForms()">Sign Up</span>
            </p>
        </form>
    </div>

    <%-- Sign-Up Form --%>
    <div id="signUpForm" style="display:none;">
        <h2>Sign Up</h2>
        <%-- Form to submit signup information --%>
        <form id="signUpMainForm" action="cmssignupserv" method="POST" onsubmit="return validateSignUpForm()">
            <%-- Input field for username --%>
            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text" class="form-control" id="username" name="username" placeholder="Enter your username">
            </div>
            <%-- Input field for email address --%>
            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="Enter your email">
            </div>
            <%-- Input field for first name --%>
            <div class="mb-3">
                <label for="firstName" class="form-label">First Name</label>
                <input type="text" class="form-control" id="firstName" name="firstName" placeholder="Enter your first name">
            </div>
            <%-- Input field for last name --%>
            <div class="mb-3">
                <label for="lastName" class="form-label">Last Name</label>
                <input type="text" class="form-control" id="lastName" name="lastName" placeholder="Enter your last name">
            </div>
            <%-- Input field for password --%>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Create a password">
            </div>
            <%-- Signup button --%>
            <button type="submit" class="btn btn-primary w-100 mt-3">Sign Up</button>
            <%-- Toggle link to login form --%>
            <p class="text-center mt-3">Already have an account?
                <span class="toggle-link" onclick="toggleForms()">Log In</span>
            </p>
        </form>
    </div>
</div>

<script>
    // Function to toggle between login and signup forms
    function toggleForms() {
        // Get the login and signup form elements
        const loginForm = document.getElementById('loginForm');
        const signUpForm = document.getElementById('signUpForm');

        // If the login form is hidden, show it and hide the signup form, otherwise do the opposite
        if (loginForm.style.display === 'none') {
            loginForm.style.display = 'block';
            signUpForm.style.display = 'none';
        } else {
            loginForm.style.display = 'none';
            signUpForm.style.display = 'block';
        }
    }

    // Function to validate the signup form
    function validateSignUpForm() {
        // Get the values from the signup form
        const username = document.getElementById('username').value;
        const email = document.getElementById('email').value;
        const firstName = document.getElementById('firstName').value;
        const lastName = document.getElementById('lastName').value;
        const password = document.getElementById('password').value;

        // Regular expression to validate email format
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        // Check if any of the required fields are empty
        if (!username || !email || !firstName || !lastName || !password) {
            alert('Please fill in all fields.');
            return false;
        }

        // Check if the username is less than 4 characters
        if (username.length < 4) {
            alert('Username must be at least 4 characters long.');
            return false;
        }

        // Check if the email is valid
        if (!emailPattern.test(email)) {
            alert('Please enter a valid email address.');
            return false;
        }

        // If all validations pass, return true
        return true;
    }
</script>

<%-- Include common footer elements --%>
<%@include file="common/footer.jspf" %>
<%-- Include common footer scripts --%>
<%@include file="common/footerscripts.jspf" %>
</body>
</html>
