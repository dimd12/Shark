package com.edumentor.servlets.cms;

import com.edumentor.models.User;
import com.edumentor.services.UserServiceIntf;
import com.edumentor.services.impl.UserServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet to handle user profile deletion.
 * Deletes the user's account and invalidates the session, redirecting to the login page.
 *
 * @author adrian
 */

@WebServlet(name = "cmsprofiledeleteserv", urlPatterns = {"/cms/cmsprofiledeleteserv"})
public class cmsprofiledeleteserv extends HttpServlet {

    UserServiceIntf userService = UserServiceImpl.getInstance();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * <p>This method retrieves the current user from the session, verifies the user's existence,
     * deletes the user's account using {@link UserServiceIntf}, invalidates the session, and redirects to the login page.</p>
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Get the current session, but do not create one if it doesn't exist
            HttpSession session = request.getSession(false);
            // If the session is null, it means the user is not logged in
            if (session == null) {
                throw new ServletException("Session not found. User may not be logged in.");
            }

            // Get the username of the current user from the session
            String currentuser = (String) session.getAttribute("CURRENTUSER");
            // If the current user is null, it means no user is logged in
            if (currentuser == null) {
                throw new ServletException("No user is logged in.");
            }

            // Get the user service implementation
            UserServiceIntf userService = UserServiceImpl.getInstance();
            // Find the user object by username
            User user = userService.findByUsername(currentuser);
            // If the user is null, it means the user is not found in the system
            if (user == null) {
                throw new ServletException("User not found in the system.");
            }

            // Delete the user's account
            userService.delete(user.getUserId());

            // Invalidate the session to log the user out
            session.invalidate();

            // Redirect to the login page
            response.sendRedirect("../login.html");
            // Catch any exceptions that occur during the process
        } catch (Exception ex) {
            // If any exception occurs, send an internal server error response
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred during account deletion: " + ex.getMessage());
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
        return "Servlet to handle user profile deletion";
    }// </editor-fold>

}
