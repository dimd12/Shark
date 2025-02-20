<%--
    Document   : CMS Profile Edit
    Created on : Feb 7, 2025, 4:07:51 PM
    Author     : adrian
--%>
<%-- Taglib directive for JSTL core tags, used for iteration and conditional logic --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Page directive to set content type and character encoding --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%-- Include common head and shared styles --%>
    <%@include file="../common/head.jspf"%>
    <%@include file="../common/styles.jspf"%>

    <title>Edit Profile</title>

    <%-- Custom styles specific to the Edit Profile page --%>
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

        /* Styles specific to the profile edit form */
        .profile-edit-form {
            max-width: 600px; /* Restrict form width for better readability */
            margin: 20px auto; /* Center align form */
            padding: 20px;
            background-color: #ffffff; /* White background for the form */
            border: 1px solid #ccc;
            border-radius: 8px; /* Rounded corners */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Subtle shadow for the form */
        }

        .profile-edit-form label {
            font-weight: bold; /* Bold font for labels */
            display: block; /* Ensure labels occupy a full line */
            margin-bottom: 5px;
            color: #4b0082; /* Consistent purple color for form labels */
        }

        .profile-edit-form input,
        .profile-edit-form textarea,
        .profile-edit-form button {
            width: 100%; /* Stretch inputs and buttons across the container */
            margin: 5px 0 15px 0; /* Add spacing between controls */
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px; /* Rounded inputs */
            font-size: 1rem;
        }

        .profile-edit-form input[readonly] {
            background-color: #f8f9fa; /* Slight gray background for readonly inputs */
        }

        .profile-edit-form button {
            background-color: #9370db; /* Medium purple for buttons */
            color: white; /* Bright text for visibility */
            font-weight: bold;
            cursor: pointer;
            border: none;
        }

        .profile-edit-form button:hover {
            background-color: #ba55d3; /* Lighter purple for hover */
        }

        /* Style for cancel button */
        .profile-edit-form button[type="button"] {
            background-color: transparent; /* Transparent for cancel */
            color: #4b0082; /* Purple border for cancel button */
            border: 1px solid #4b0082;
        }

        .profile-edit-form button[type="button"]:hover {
            background-color: #9370db; /* Purple hover */
            color: white;
        }

        /* Styling for the page heading */
        .profile-edit-header {
            text-align: center;
            font-size: 2rem;
            color: #4b0082; /* Bold purple heading */
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<%-- Include navigation sidebar and login structure --%>
<%@include file="../common/navbarsidebarlogin.jspf"%>

<div class="container content">
    <!-- Page header -->
    <h1 class="profile-edit-header">Edit Profile</h1>

    <!-- Profile Edit Form -->
    <form id="profileEditForm" class="profile-edit-form" action="cmsprofileeditserv" method="post" onsubmit="return validateForm()">
        <%-- Hidden input to hold the user ID --%>
        <input type="hidden" name="userId" value="${user.userId}" />

        <div>
            <label for="username" class="form-label">Username:</label>
            <%-- Username input, made readonly as it's typically not editable --%>
            <input type="text" class="form-control" id="username" name="username" value="${user.username}" readonly />
        </div>

        <div>
            <label for="firstName" class="form-label">First Name:</label>
            <%-- Input field for first name, marked as required --%>
            <input type="text" class="form-control" id="firstName" name="firstName" value="${user.firstName}" required />
        </div>

        <div>
            <label for="lastName" class="form-label">Last Name:</label>
            <%-- Input field for last name, marked as required --%>
            <input type="text" class="form-control" id="lastName" name="lastName" value="${user.lastName}" required />
        </div>

        <div>
            <label for="email" class="form-label">Email:</label>
            <%-- Input field for email, marked as required --%>
            <input type="email" class="form-control" id="email" name="email" value="${user.email}" required />
        </div>

        <div>
            <label for="bio" class="form-label">Bio:</label>
            <%-- Textarea for the user's bio --%>
            <textarea type="text" id="bio" class="form-control" name="bio" rows="4" cols="50">${user.bio}</textarea>
        </div>

        <div>
            <label for="profilePictureUrl" class="form-label">Profile Picture URL:</label>
            <%-- Input field for profile picture URL --%>
            <input type="url" class="form-control" id="profilePictureUrl" name="profilePictureUrl" value="${user.profilePictureUrl}" />
            <%-- Display current profile picture --%>
            <img src="${user.profilePictureUrl}" alt="Profile Picture" width="250" height="250"/>
        </div>

        <div>
            <%-- Submit button to update the profile --%>
            <button type="submit">Update Profile</button>
            <%-- Cancel button that redirects to the profile page --%>
            <button type="button" onclick="window.location.href='profile.html'">Cancel</button>
        </div>
    </form>
    <br />
    <br />
    <%-- Form to delete the user account --%>
    <form action="cmsprofiledeleteserv" method="post" onsubmit="return confirm('Are you sure you want to delete your account? This action cannot be undone.');">
        <%-- Submit button to delete the account --%>
        <button type="submit" style="background-color: red; color: white; border: none; padding: 10px 20px; cursor: pointer;">
            Delete Account
        </button>
    </form>

</div>

<%-- Include common footer and footer scripts --%>
<%@include file="../common/footer.jspf"%>
<%@include file="../common/footerscripts.jspf"%>

<script>
    // Function to validate the profile edit form
    function validateForm() {
        // Get the values from the input fields
        const username = document.getElementById("username").value;
        const email = document.getElementById("email").value;
        const firstName = document.getElementById("firstName").value;
        const lastName = document.getElementById("lastName").value;
        const bio = document.getElementById("bio").value;
        const profilePictureUrl = document.getElementById("profilePictureUrl").value;

        // Regular expression to validate email format
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        // Check if any of the required fields are empty
        if (!username || !email || !firstName || !lastName) {
            alert("Please fill out all fields.");
            return false;
        }

        // Check if the username is less than 4 characters
        if(username.length < 4) {
            alert("Username must be at least 4 characters long.");
            return false;
        }

        // Check if the email is valid
        if(!emailPattern.test(email)) {
            alert("Please enter a valid email address.");
            return false;
        }
        // If all validations pass, return true
        return true;
    }
</script>

</body>
</html>
