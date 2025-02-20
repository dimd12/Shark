package com.edumentor.servlets.cms;

import com.edumentor.models.User;
import com.edumentor.services.UserServiceIntf;
import com.edumentor.services.impl.UserServiceImpl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet to handle user profile editing.
 * Allows users to update their first name, last name, email, bio, and profile picture URL.
 *
 * @author adrian
 */

@WebServlet(name = "cmsprofileeditserv", urlPatterns = {"/cms/cmsprofileeditserv"})
public class cmsprofileeditserv extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * <p>This method retrieves user information from the session, verifies the user's existence,
     * updates the user's profile information based on the request parameters, and saves the updated user information
     * using {@link UserServiceIntf}. If any error occurs during the process, it sets an appropriate message in the request
     * and forwards it back to the profile page.</p>
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get the current session, but do not create one if it doesn't exist
            HttpSession session = request.getSession(false);
            // If the session is null or the user is not logged in, forward to the error page
            if (session == null || session.getAttribute("CURRENTUSER") == null) {
                request.setAttribute("errorMessage", "You must be logged in to edit your profile.");
                request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
                return;
            }

            // Get the username of the current user from the session
            String currentUser = (String) session.getAttribute("CURRENTUSER");
            // Get the user service implementation
            UserServiceIntf userService = UserServiceImpl.getInstance();
            // Find the user object by username
            User user = userService.findByUsername(currentUser);

            // If the user is null, throw an exception
            if (user == null) {
                throw new Exception("User not found.");
            }

            // Get the request parameters
            String newFirstName = request.getParameter("firstName");
            String newLastName = request.getParameter("lastName");
            String newEmail = request.getParameter("email");
            String newBio = request.getParameter("bio");
            String newProfilePictureUrl = request.getParameter("profilePictureUrl");

            // Update the user's email if it has been changed
            if(newEmail == null || newEmail.isEmpty()){
                newEmail = user.getEmail();
            } else if (!newEmail.equals(user.getEmail())) {
                user.setEmail(newEmail);
            }

            // Update the user's first name if it has been changed
            if(newFirstName == null || newFirstName.isEmpty()){
                newFirstName = user.getFirstName();
            } else if (!newFirstName.equals(user.getFirstName())) {
                user.setFirstName(newFirstName);
            }

            // Update the user's last name if it has been changed
            if(newLastName == null || newLastName.isEmpty()){
                newLastName = user.getLastName();
            } else if (!newLastName.equals(user.getLastName())) {
                user.setLastName(newLastName);
            }

            // Update the user's bio
            user.setBio(newBio);

            // Update the user's profile picture URL if it has been changed
            if(newProfilePictureUrl == null || newProfilePictureUrl.isEmpty()){
                // If the user does not provide a url to their profile picture, assign a default image
                newProfilePictureUrl = ("https://th.bing.com/th/id/OIP.hGSCbXlcOjL_9mmzerqAbQHaHa?rs=1&pid=ImgDetMain");
                user.setProfilePictureUrl(newProfilePictureUrl);
            } else if (!newProfilePictureUrl.equals(user.getProfilePictureUrl())) {
                user.setProfilePictureUrl(newProfilePictureUrl);
            }

            try {
                // Update the user in the database
                userService.update(user);
                // Redirect to the profile page
                response.sendRedirect("profile.html");
                // Catch any exceptions that occur during the process
            } catch (Exception ex) {
                // If any exception occurs, set an error message and forward back to the profile page
                request.setAttribute("message", "Error saving user: " + ex.getMessage());
                request.getRequestDispatcher("WEB-INF/pages/profile.jsp").forward(request, response);
            }
            // Catch any exceptions that occur during the process
        } catch (Exception ex) {
            // Redirect to the login page
            response.sendRedirect("../login.html");
        }
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet to handle user profile editing. Allows users to update their first name, last name, email, bio, and profile picture URL.";
    }
}    // </editor-fold>
