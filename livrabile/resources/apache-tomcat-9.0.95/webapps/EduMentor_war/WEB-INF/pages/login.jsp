<%-- 
    Document   : login
    Created on : Feb 5, 2025, 10:08:27 AM
    Author     : adima
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="common/head.jspf" %>
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
        </style>

    </head>
    <body>
        <%@include file="common/navbarsidebarnologin.jspf" %>

        <div class="form-container">
            <!-- Login Form -->
            <div id="loginForm">
                <h2>Login</h2>
                <form action="cmsloginserv" method="POST">
                    <div class="mb-3">
                        <label for="loginUsername" class="form-label">Username</label>
                        <input type="text" class="form-control" id="uname" name="username" placeholder="Enter your username">
                    </div>
                    <div class="mb-3">
                        <label for="loginPassword" class="form-label">Password</label>
                        <input type="password" class="form-control" id="pass" name="password" placeholder="Enter your password">
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Log In</button>
                    <p class="text-center mt-3">Don't have an account? 
                        <span class="toggle-link" onclick="toggleForms()">Sign Up</span>
                    </p>
                </form>
            </div>

            <!-- Sign-Up Form -->
            <div id="signUpForm" style="display:none;">
                <h2>Sign Up</h2>
                <form id="signUpMainForm">
                    <div class="mb-3">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" class="form-control" id="username" placeholder="Enter your username">
                    </div>
                    <div class="mb-3">
                        <label for="emailSignUp" class="form-label">Email address</label>
                        <input type="email" class="form-control" id="emailSignUp" placeholder="Enter your email">
                    </div>
                    <div class="mb-3">
                        <label for="firstName" class="form-label">First Name</label>
                        <input type="text" class="form-control" id="firstName" placeholder="Enter your first name">
                    </div>
                    <div class="mb-3">
                        <label for="lastName" class="form-label">Last Name</label>
                        <input type="text" class="form-control" id="lastName" placeholder="Enter your last name">
                    </div>
                    <div class="mb-3">
                        <label for="passwordSignUp" class="form-label">Password</label>
                        <input type="password" class="form-control" id="passwordSignUp" placeholder="Create a password">
                    </div>
                    <div class="mb-3">
                        <label for="dobSignUp" class="form-label">Date of Birth</label>
                        <input type="date" class="form-control" id="dobSignUp">
                    </div>
                    <!-- User Type Selection -->
                    <div class="">
                        <label for="" class="">Would you like to be:</label>
                        <select class="form-control" id="userType">
                            <option value="mentee">Mentee</option>
                            <option value="mentor">Mentor</option>
                        </select>
                    </div>
                    <button type="button" class="btn btn-primary w-100 mt-3" onclick="handleSignUp()">Sign Up</button>
                    <p class="text-center mt-3">Already have an account? 
                        <span class="toggle-link" onclick="toggleForms()">Log In</span>
                    </p>
                </form>
            </div>

            <!-- Mentor Additional Form -->
            <div id="mentorForm" style="display:none;">
                <h2>Mentor Application</h2>
                <form id="mentorApplicationForm">
                    <div class="mb-3">
                        <label for="schoolName" class="form-label">School Name</label>
                        <input type="text" class="form-control" id="schoolName" placeholder="Enter your school name">
                    </div>
                    <div class="mb-3">
                        <label for="schoolResults" class="form-label">School Results</label>
                        <textarea class="form-control" id="schoolResults" rows="3" placeholder="Provide your school results"></textarea>
                    </div>
                    <div class="mb-3">
                        <label for="olympiadResults" class="form-label">Olympiad Results</label>
                        <textarea class="form-control" id="olympiadResults" rows="3" placeholder="Provide your olympiad results (if any)"></textarea>
                    </div>
                    <div class="mb-3">
                        <label for="cvUpload" class="form-label">Upload CV</label>
                        <input type="file" class="form-control" id="cvUpload">
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Submit Application</button>
                </form>
            </div>
        </div>


        <script>
            function toggleForms() {
                const loginForm = document.getElementById('loginForm');
                const signUpForm = document.getElementById('signUpForm');
                const mentorForm = document.getElementById('mentorForm');

                // Reset all forms and hide mentor form
                mentorForm.style.display = 'none';

                if (loginForm.style.display === 'none') {
                    loginForm.style.display = 'block';
                    signUpForm.style.display = 'none';
                } else {
                    loginForm.style.display = 'none';
                    signUpForm.style.display = 'block';
                }
            }

            function handleSignUp() {
                const userType = document.getElementById('userType').value;

                if (userType === 'mentor') {
                    // Show mentor application form
                    document.getElementById('signUpForm').style.display = 'none';
                    document.getElementById('mentorForm').style.display = 'block';
                } else {
                    // Proceed with mentee sign-up process (e.g., send data to server)
                    alert('Mentee account created successfully!');
                }
            }
        </script>

        <%@include file="common/footer.jspf" %>
        <%@include file="common/footerscripts.jspf" %>
    </body>
</html>
