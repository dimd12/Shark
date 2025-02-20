package com.edumentor.servlets;

import com.edumentor.models.Role;
import com.edumentor.models.User;
import com.edumentor.services.UserServiceIntf;
import com.edumentor.services.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Servlet to handle user signup.
 * Creates a new user account and redirects to the login page upon successful signup.
 *
 * @author adrian
 */
@WebServlet(name = "cmssignupserv", urlPatterns = {"/cmssignupserv"})
public class cmssignupserv extends HttpServlet {

    UserServiceIntf userService = UserServiceImpl.getInstance();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * <p>This method retrieves user information from the request, validates the input parameters (username, email, first name, last name, and password),
     * creates a new {@link User} object, and saves it to the database using {@link UserServiceIntf}.
     * If any error occurs during the process, it sets an appropriate message in the request and forwards it back to the login page.</p>
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the request parameters
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");

        // Email regex pattern
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        // Validate the request parameters
        if(username == null || username.isEmpty() ||
                email == null || email.isEmpty() ||
                firstName == null || firstName.isEmpty() ||
                lastName == null || lastName.isEmpty() ||
                password == null || password.isEmpty()) {
            // If any of the required parameters is missing, set an error message and forward back to the login page
            request.setAttribute("message", "Please fill all the fields");
            request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }

        // If the username length is less than 4, set an error message and forward back to the login page
        if (username.length() < 4) {
            request.setAttribute("message", "Username must be at least 4 characters long");
            request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }

        // If the email is not valid, set an error message and forward back to the login page
        if (!Pattern.matches(emailPattern, email)) {
            request.setAttribute("message", "Please enter a valid email address");
            request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }

        // Create a new user object
        User user = new User();
        // Set the user properties
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        // Assign a default profile picture
        user.setProfilePictureUrl("https://th.bing.com/th/id/OIP.hGSCbXlcOjL_9mmzerqAbQHaHa?rs=1&pid=ImgDetMain");
        user.setBio(null);
        // Assign a default user role ID of 2
        user.setRoleId(new Role(2));

        try {
            // Save the user to the database
            userService.save(user);
            // Redirect to the login page
            response.sendRedirect("login.html");
            // Catch any exceptions that occur during the process
        } catch (Exception ex) {
            // If any exception occurs, set an error message and forward back to the login page
            request.setAttribute("message", "Error saving user: " + ex.getMessage());
            request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
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
        return "Servlet to handle user signup";
    }// </editor-fold>

}
