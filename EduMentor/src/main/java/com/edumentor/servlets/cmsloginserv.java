package com.edumentor.servlets;

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
 * Servlet to handle user login to the CMS.
 * Authenticates user credentials and redirects to the CMS index page upon successful login.
 *
 * @author adrian
 */
@WebServlet(name = "cmsloginserv", urlPatterns = {"/cmsloginserv"})
public class cmsloginserv extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * <p>This method retrieves the username and password from the request, validates the input fields,
     * authenticates the user using {@link UserServiceIntf}, sets the username in the session,
     * and redirects to the CMS index page. If authentication fails or an error occurs, it sets an error message
     * in the request and forwards it back to the login page.</p>
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the username and password from the request parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String path;

        try{
            // Validate the input fields
            validateFields(username, password);
            // Authenticate the user
            autentificateUser(username, password);
            // Create a new session
            HttpSession session = request.getSession(true);
            // Set the username as an attribute in the session
            session.setAttribute("CURRENTUSER", username);

            // Set the path to the CMS index page
            path = "/cms/index.html";
            // Redirect to the CMS index page
            response.sendRedirect(path);

            // Catch any exceptions that occur during the process
        } catch(Exception ex){
            // Set the error message as an attribute in the request
            request.setAttribute("errorMessage", ex);
            // Set the path to the login page
            path = "WEB-INF/pages/login.jsp";
            // Forward the request to the login page
            request.getRequestDispatcher(path).forward(request, response);
        }

    }

    /**
     * Validates the input fields (username and password).
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @throws Exception if the username or password length is less than 4.
     */
    private void validateFields(String username, String password) throws Exception{
        // If the username or password length is less than 4, throw an exception
        if (username.length() < 4 || password.length() < 4){
            throw new Exception("Values must include at least 4 characters");
        }
    }

    /**
     * Authenticates the user by checking the username and password against the database.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @throws Exception if the login information is incorrect.
     */
    private void autentificateUser(String username, String password) throws Exception {
        // Get the user service implementation
        UserServiceIntf userService = UserServiceImpl.getInstance();

        // Find the user object by username
        User user = userService.findByUsername(username);

        // If the user is null or the password does not match, throw an exception
        if(user == null || !user.getPassword().equals(password)) {
            throw new Exception("Login info wrong");
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
        return "Servlet to handle user login to the CMS";
    }// </editor-fold>

}
